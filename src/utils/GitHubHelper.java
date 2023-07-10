package utils;

import java.io.IOException;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.eclipse.egit.github.core.service.UserService;

import app.Settings;
import ui.view.WarningDialog;

/**
 * Provides methods for getting {@link Repository} and {@link GitHubClient}.
 *
 * @author Alex Mikulinski
 * @since 25.03.2019
 */
public class GitHubHelper {
	private Repository				repository;
	private GitHubClient				gitHubClient;

	private static GitHubHelper	instance;

	/**
	 * @return The instance of this {@link GitHubHelper}.
	 */
	public static GitHubHelper getInstance() {
		if (GitHubHelper.instance == null) {
			GitHubHelper.instance = new GitHubHelper();
		}
		return GitHubHelper.instance;
	}

	/**
	 * Returns the {@link #repository} of current {@link GitHubHelper}. If the
	 * {@link #repository} is null creates an instance of it using via
	 * {@link #changeRepository()}.
	 *
	 * @return The {@link #repository} as {@link Repository}. Will return null if no
	 *         requests left.
	 */
	public Repository getRepository() {
		if (this.repository != null) {
			return this.repository;
		}

		return this.changeRepository();
	}

	/**
	 * Changes {@link #repository} to the repository specified in the
	 * {@link Settings}.
	 *
	 * @return The {@link #repository} as {@link Repository}. Will return null if no
	 *         requests left.
	 */
	public Repository changeRepository() {
		RepositoryService repositoryService;

		if (!this.hasRemainingRequests()) {
			return null;
		}

		repositoryService = new RepositoryService(this.getGitHubClient());

		try {
			this.repository = repositoryService.getRepository(Settings.EPROJECT_OWNER.get(), Settings.EPROJECT_NAME.get());
		} catch (IOException _e) {
			System.err.println("Failed to get repository specified in the config by owner=" + Settings.EPROJECT_OWNER.get()
					+ " and repository name=" + Settings.EPROJECT_NAME.get());
			_e.printStackTrace();
		}

		return this.repository;
	}

	/**
	 * Returns the {@link #gitHubClient} of current {@link GitHubHelper}. If the
	 * {@link #gitHubClient} is null creates an instance of it after getting the
	 * credentials.
	 *
	 * @return The {@link #gitHubClient} as {@link GitHubClient}.
	 */
	public GitHubClient getGitHubClient() {
		CredentialsManager	credentialsManager;
		boolean					firstTry;

		if (this.gitHubClient != null) {
			return this.gitHubClient;
		}

		this.gitHubClient		= new GitHubClient();
		credentialsManager	= new CredentialsManager("GitHub");
		firstTry					= true;

		if (credentialsManager.getFromFile(Settings.GITHUB_CREDENTIALS.get())) {
			this.gitHubClient.setCredentials(credentialsManager.getUsername(), credentialsManager.getPassword());

			if (this.makeTestRequest()) {
				return this.gitHubClient;
			}

			new WarningDialog(
					"The stored credentials for GitHub were wrong! Please try to login with the correct credentials.");
		}

		// Ask user to type in credentials. If user cancels -> return null. Else check
		// the credentials. If wrong -> ask user again
		do {
			if (firstTry) {
				credentialsManager.getFromGUI(Settings.GITHUB_CREDENTIALS.get(), false);
				firstTry = false;
			} else {
				credentialsManager.getFromGUI(Settings.GITHUB_CREDENTIALS.get(), true);
			}

			// In case the user canceled the authentification -> return null and show a
			// warning
			if (credentialsManager.getUsername() == null) {
				new WarningDialog(
						"Without credentials the standard request limit rate of 60 requests will be used. If you want to login and use 5000 requests, please restart the program and login.");
				return this.gitHubClient;
			}

			this.gitHubClient.setCredentials(credentialsManager.getUsername(), credentialsManager.getPassword());
		} while (!this.makeTestRequest());

		return this.gitHubClient;
	}

	/**
	 * Tries to get the number of remaining requests. If -1 is returned, it means
	 * that no requests were made yet. In this case will make a test request. After
	 * that the proper number of requests will be returned.
	 *
	 * @return The number of remaining requests for this {@link #gitHubClient}.
	 */
	public int getRemainingRequests() {
		int remainingRequest;

		remainingRequest = this.getGitHubClient().getRemainingRequests();

		if (remainingRequest == -1) {
			this.makeTestRequest();
		}

		return this.getGitHubClient().getRemainingRequests();
	}

	/**
	 * @return True if the number of remaining requests for this
	 *         {@link #gitHubClient} > 0.
	 */
	public boolean hasRemainingRequests() {
		return this.getRemainingRequests() > 0;
	}

	// FIXME The data counters are using too much data, because they are loading all
	// commits/issues/prs all over again
	// there must be an efficient way for this
	// /**
	// * @return Number of open and closed issues for this {@link #repository}. Will
	// * return -1 if no requests left.
	// */
	// public int getIssuesCount() {
	// IssueService issueService;
	// int result;
	//
	// if (!this.hasRemainingRequests()) {
	// return -1;
	// }
	//
	// result = 0;
	// issueService = new IssueService(this.getGitHubClient());
	//
	// try {
	// result = issueService.getIssues(this.getRepository(),
	// IssueUpdater.ISSUE_FILTER).size();
	// } catch (IOException _e) {
	// System.err.println("Failed counting issues for the repository " +
	// this.getRepository().getName());
	// _e.printStackTrace();
	// }
	//
	// return result;
	// }

	// /**
	// * @return Number of open and closed pull requests for this {@link
	// #repository}.
	// * Will return -1 if no requests left.
	// */
	// public int getPullRequestsCount() {
	// PullRequestService pullRequestService;
	// int result;
	//
	// if (!this.hasRemainingRequests()) {
	// return -1;
	// }
	//
	// result = 0;
	// pullRequestService = new PullRequestService(this.getGitHubClient());
	//
	// try {
	// result = pullRequestService.getPullRequests(this.getRepository(),
	// PullRequestUpdater.PULL_REQUEST_FILTER)
	// .size();
	// } catch (IOException _e) {
	// System.err.println("Failed counting pull requests for the repository " +
	// this.getRepository().getName());
	// _e.printStackTrace();
	// }
	//
	// return result;
	// }

	// /**
	// * @return Number of commits for this {@link #repository}. Will return -1 if
	// no
	// * requests left.
	// */
	// public int getCommitsCount() {
	// CommitService commitService;
	// int result;
	//
	// if (!this.hasRemainingRequests()) {
	// return -1;
	// }
	//
	// result = 0;
	// commitService = new CommitService(this.getGitHubClient());
	//
	// try {
	// result = commitService.getCommits(this.getRepository()).size();
	// } catch (IOException _e) {
	// System.err.println("Failed counting commits for the repository " +
	// this.getRepository().getName());
	// _e.printStackTrace();
	// }
	//
	// return result;
	// }

	/**
	 * @return Number of forks for this {@link #repository}. Will return -1 if no
	 *         requests left.
	 */
	public int getForksCount() {
		if (!this.hasRemainingRequests()) {
			return -1;
		}

		return this.getRepository().getForks();
	}

	/**
	 * Makes a test request.
	 *
	 * @return True if the request returned no error, false otherwise.
	 */
	private boolean makeTestRequest() {
		UserService userService;

		userService = new UserService(this.gitHubClient);

		try {
			userService.getUser();
		} catch (IOException _e) {
			return false;
		}

		return true;
	}

}
