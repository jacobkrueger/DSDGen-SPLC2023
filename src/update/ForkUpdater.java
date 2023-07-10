package update;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.client.GitHubClient;

import app.DSDGen;
import app.Settings;
import utils.Progress;

/**
 * Provides functionality for downloading all forks for a selected repository
 * from <a href="https://github.com">GitHub</a>.
 *
 * @author Alex Mikulinski
 * @since 18.03.2019
 */
public class ForkUpdater implements Updater {
	Repository		repository;
	GitHubClient	client;

	/**
	 * Creates a new {@link ForkUpdater} for a {@link Repository} and a
	 * {@link GitHubClient}.
	 *
	 * @param _repository
	 *           The {@link Repository} to update.
	 * @param _client
	 *           The {@link GitHubClient} to use.
	 */
	public ForkUpdater(Repository _repository, GitHubClient _client) {
		this.repository	= _repository;
		this.client			= _client;
	}

	/**
	 * Doesn't do anything.
	 */
	@Override
	public void update(String _path) throws IOException {
		// nothing
	}

	/**
	 * Clones a {@link List} of forks to the passed path. Each fork will be stored
	 * in a directory named after it's owners login. Also downloads the issues and
	 * commits for all forks.
	 *
	 * @param _forks
	 *           A {@link List} of {@link Repository}.
	 * @param _path
	 *           The target path.
	 * @throws IOException
	 */
	public void cloneForks(List<Repository> _forks, String _path) throws IOException {
		String	tempForkRepoPath;
		String	tempForkPath;
		int		forkCount;

		forkCount = _forks.size();

		try (Progress progress = new Progress(1, forkCount, "Cloning forks: ")) {
			for (Repository fork : _forks) {
				progress.show().step();
				tempForkPath		= _path + fork.getOwner().getLogin() + File.separator;
				tempForkRepoPath	= tempForkPath + Settings.REPOSITORY_DIR_NAME.get();

				// If evaluation -> check out a specific revision
				// If cloning doesn't work, there's no need to try loading issues or commits
				if (DSDGen.EVALUATION) {
					if (!GitUpdater.cloneRepo(fork.getCloneUrl(), tempForkRepoPath, DSDGen.SHA_COMMIT_FORK)) {
						continue;
					}
				} else {
					if (!GitUpdater.cloneRepo(fork.getCloneUrl(), tempForkRepoPath, null)) {
						continue;
					}
				}

				this._loadIssues(fork, tempForkPath);
				this._loadPullRequests(fork, tempForkPath);
				this._loadCommits(fork, tempForkRepoPath, tempForkPath);
			}
		}
	}

	private void _loadIssues(Repository _fork, String _path) throws IOException {
		IssueUpdater issueUpdater;

		issueUpdater = new IssueUpdater(_fork, this.client);

		issueUpdater.update(_path);
	}

	private void _loadPullRequests(Repository _fork, String _path) throws IOException {
		PullRequestUpdater pullRequestUpdater;

		pullRequestUpdater = new PullRequestUpdater(_fork, this.client);

		pullRequestUpdater.update(_path);
	}

	private void _loadCommits(Repository _fork, String _forkPath, String _path) throws IOException {
		CommitUpdater commitUpdater;

		commitUpdater = new CommitUpdater(_forkPath, _fork.getOwner().getLogin(), _fork.getName());

		commitUpdater.update(_path);
	}

}
