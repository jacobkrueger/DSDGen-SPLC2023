package update;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;

import com.fasterxml.jackson.databind.ObjectMapper;
import app.Settings;
import model.Commit;
import ui.view.WarningDialog;
import utils.JsonManager;
import utils.commit.CommitHelper;
import utils.commit.DiffEntryMixin;
import utils.commit.PersonIdentMixin;

/**
 * Provides functionality for downloading and saving commits for a git
 * repository.
 *
 * @author Alex Mikulinski
 * @since 25.02.2019
 */
public class CommitUpdater implements Updater {
	private Repository	repository;
	private String			repositoryName;
	private String			owner;

	/**
	 * Creates a new instance of {@link CommitUpdater}.
	 *
	 * @param _repositoryPath
	 *           The path to the{@link Repository} to get the {@link Commit}s from.
	 *           <br>
	 *           See {@link CommitHelper#getRepository(String)}
	 * @param _owner
	 *           Login name of repository owner on GitHub. Used to create a link to
	 *           a commit on GitHub.
	 * @param _repositoryName
	 *           Name of repository on GitHub. Used to create a link to a commit on
	 *           GitHub.
	 * @throws IOException
	 */
	public CommitUpdater(String _repositoryPath, String _owner, String _repositoryName) throws IOException {
		this.repositoryName	= _repositoryName;
		this.repository		= CommitHelper.getRepository(_repositoryPath);
		this.owner				= _owner;
	}

	/**
	 * Stores all commits for the chosen repository to the passed path using the
	 * {@link Settings#COMMIT_FILE_NAME}.
	 *
	 * @throws IOException
	 */
	@Override
	public void update(String _path) throws IOException {
		ObjectMapper	objectMapper;
		List<Commit>	commits;

		if (this.repository == null) {
			new WarningDialog(Updater.noInternetError("commits"));
			return;
		}

		commits			= this.getCommits();

		objectMapper	= new ObjectMapper();

		objectMapper.addMixIn(DiffEntry.class, DiffEntryMixin.class);
		objectMapper.addMixIn(PersonIdent.class, PersonIdentMixin.class);

		JsonManager.write(commits, _path + Settings.COMMIT_FILE_NAME.get(), objectMapper);
	}

	/**
	 * @return A {@link List} of {@link Commit}s for this {@link #repository}.
	 * @throws IOException
	 */
	public List<Commit> getCommits() throws IOException {
		List<RevCommit>	revCommits;
		List<RevCommit>	parents;
		List<Commit>		commits;
		Commit				tempCommit;

		commits		= new ArrayList<>();
		revCommits	= this.getRevCommits();

		for (RevCommit revCommit : revCommits) {
			tempCommit	= new Commit(revCommit);
			parents		= Arrays.asList(revCommit.getParents());

			for (RevCommit parent : parents) {
				tempCommit.addParentDiff(parent, this.getDiffs(parent, revCommit));
			}

			tempCommit.setLink(
					"https://github.com/" + this.owner + "/" + this.repositoryName + "/commit/" + tempCommit.getName());

			commits.add(tempCommit);
		}

		return commits;
	}

	/**
	 * @return A {@link List} of {@link RevCommit}s for this {@link #repository}.
	 * @throws IOException
	 */
	public List<RevCommit> getRevCommits() throws IOException {
		Iterable<RevCommit>	commits;
		List<RevCommit>		revCommits;

		revCommits = new ArrayList<>();

		try (Git git = new Git(this.repository)) {
			commits = git.log().add(this.repository.resolve("refs/heads/master")).call();
			commits.forEach(revCommits::add);
		} catch (GitAPIException _e) {
			throw new IOException("Failed while getting commits for " + this.repository, _e);
		}

		return revCommits;
	}

	/**
	 * Finds the diffs between two commits.
	 *
	 * @param _oldCommit
	 *           The old {@link RevCommit}.
	 * @param _newCommit
	 *           The new {@link RevCommit}.
	 * @return A {@link List} of {@link DiffEntry DiffEntries} between passed
	 *         {@link RevCommit}s.
	 * @throws IOException
	 */
	public List<DiffEntry> getDiffs(RevCommit _oldCommit, RevCommit _newCommit) throws IOException {
		List<DiffEntry> diffs;

		try (Git git = new Git(this.repository)) {
			diffs = git.diff().setOldTree(this._prepareTreeParser(_oldCommit))
					.setNewTree(this._prepareTreeParser(_newCommit)).call();
		} catch (GitAPIException _e) {
			throw new IOException("Failed while getting diffs between " + _oldCommit + " and " + _newCommit, _e);
		}

		return diffs;
	}

	private AbstractTreeIterator _prepareTreeParser(RevCommit _commit) throws IOException {
		CanonicalTreeParser	treeParser;
		RevTree					tree;

		try (RevWalk walk = new RevWalk(this.repository)) {
			tree			= walk.parseTree(_commit.getTree().getId());

			treeParser	= new CanonicalTreeParser();
			try (ObjectReader reader = this.repository.newObjectReader()) {
				treeParser.reset(reader, tree.getId());
			}

			walk.dispose();

			return treeParser;
		}
	}
}
