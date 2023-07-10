package update;

import java.io.File;
import java.io.IOException;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ResetCommand.ResetType;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.transport.FetchResult;

import app.DSDGen;
import ui.view.WarningDialog;

// TODO Is this class even needed? It only provides a method to clone a repo
// without other methods. Better write it in a git helper class or something
// like that
/**
 * Provides functionality for downloading repositories and it's forks from git.
 *
 * @author Alex Mikulinski
 * @since 24.02.2019
 */
public class GitUpdater implements Updater {
	Repository repository;

	/**
	 * Creates a new {@link GitUpdater} for a {@link Repository}.
	 *
	 * @param _repository
	 *           The {@link Repository} to update.
	 */
	public GitUpdater(Repository _repository) {
		this.repository = _repository;
	}

	/**
	 * Clones this {@link #repository} to the passed path.
	 */
	@Override
	public void update(String _path) throws IOException {
		if (this.repository == null) {
			new WarningDialog(Updater.noInternetError("the main repository"));
			return;
		}

		if (DSDGen.EVALUATION) {
			cloneRepo(this.repository.getCloneUrl(), _path, DSDGen.SHA_COMMIT_BASE);
		} else {
			cloneRepo(this.repository.getCloneUrl(), _path, null);
		}
	}

	/**
	 * Clones a repository to a specified directory.
	 *
	 * @param _url
	 *           The clone URL for the git repository.
	 * @param _targetDir
	 *           Directory to clone the repository in.
	 * @param _revision
	 *           A specific revision you want to check out. Set null if not needed.
	 * @return True if cloning was successfull.
	 * @throws IOException
	 */
	public static boolean cloneRepo(String _url, String _targetDir, String _revision) throws IOException {
		boolean	success;
		File		targetDir;
		Git		git;

		success		= true;
		targetDir	= new File(_targetDir);

		// if the repository dir already exists, there's only a pull needed
		if (targetDir.exists()) {
			pull(_url, _targetDir);
			return success;
		}

		try {
			git = Git.cloneRepository().setURI(_url).setDirectory(targetDir).call();
			if (_revision != null) {
				git.reset().setMode(ResetType.HARD).setRef(_revision).call();
			}
		} catch (TransportException _e) {
			success = false;
			System.err.println("Failed to clone " + _e.getMessage());
		} catch (GitAPIException _e) {
			throw new IOException("Failed while cloning " + _url, _e);
		}

		return success;
	}

	/**
	 * Pulls a repository to a specified directory.
	 *
	 * @param _url
	 *           The URL for the git repository.
	 * @param _targetDir
	 *           Directory to pull the repository in.
	 * @return True if pulling was successfull.
	 * @throws IOException
	 */
	public static boolean pull(String _url, String _targetDir) throws IOException {
		File	targetDir;
		Git	git;

		targetDir = new File(_targetDir);

		if (!targetDir.exists()) {
			return false;
		}

		git = Git.open(targetDir);

		try {
			git.pull().call();
		} catch (GitAPIException _e) {
			throw new IOException("Failed while pulling " + _url, _e);
		} finally {
			git.close();
		}

		return true;
	}

	/**
	 * Fetches a repository in a specified directory.
	 *
	 * @param _url
	 *           The URL for the git repository.
	 * @param _targetDir
	 *           Directory to pull the repository in.
	 * @return The {@link FetchResult}.
	 * @throws IOException
	 */
	public static FetchResult fetch(String _url, String _targetDir) throws IOException {
		FetchResult	fetchResult;
		File			targetDir;
		Git			git;

		fetchResult	= null;
		targetDir	= new File(_targetDir);

		if (!targetDir.exists()) {
			return fetchResult;
		}

		git = Git.open(targetDir);

		try {
			fetchResult = git.fetch().setCheckFetchedObjects(true).call();
		} catch (GitAPIException _e) {
			throw new IOException("Failed while pulling " + _url, _e);
		} finally {
			git.close();
		}

		return fetchResult;
	}
}
