package ui.handler;

import java.awt.Color;
import java.io.File;
import java.io.IOException;

import javax.swing.JLabel;

import app.Settings;
import ui.view.StatusPanel;
import update.GitUpdater;
import utils.GitHubHelper;
import utils.JsonManager;

/**
 * Handles the status view for the data of this program displayed by
 * {@link StatusPanel}.
 *
 * @author Alex Mikulinski
 * @since 05.04.2019
 */
public class StatusHandler {
	private StatusPanel				statusPanel;
	/** Whether the commits are up to date. See {@link #updateCommitStatus}. */
	private Status						commitsStatus;

	private static StatusHandler	instance;

	/** The status of each data type */
	private enum Status {
		INCOMPLETE, READY, OUTDATED
	};

	private StatusHandler() {
		this.commitsStatus = Status.INCOMPLETE;
	}

	/**
	 * @return The {@link StatusHandler} instance for this program.
	 */
	public static StatusHandler getInstance() {
		if (instance == null) {
			instance = new StatusHandler();
		}
		return instance;
	}

	/**
	 * Updates all status views displayed by {@link StatusPanel}.
	 */
	public void updateAll() {
		if (this.statusPanel == null) {
			throw new IllegalStateException("You need to set the status panel first!");
		}

		this.updateOwnerName();

		this.updateSOStatus();
		this.updateForkStatus();
		this.updateIssueStatus();
		this.updateCommitStatus();
		this.updatePullRequestStatus();

		this.updateRepoStatus();
		this.updateGitHubReq();
		this.updateSOReq();
	}

	/**
	 * Updates the {@link Settings#EPROJECT_OWNER} and
	 * {@link Settings#EPROJECT_NAME}.
	 */
	public void updateOwnerName() {
		String	owner;
		String	name;

		owner	= Settings.EPROJECT_OWNER.get();
		name	= Settings.EPROJECT_NAME.get();

		this._updateLabel(this.statusPanel.getLblCurOwnerName(), owner + " / " + name, Status.READY);
	}

	/**
	 * Updates the {@link StatusPanel#lblGitHubReq} with current remaining request
	 * count to the GitHub API. <br>
	 * <ul>
	 * <li>if -1 -> "-" (If no requests were made)</li>
	 * <li>if 0 -> "No"</li>
	 * <li>else "Yes - count"</li>
	 * </ul>
	 */
	public void updateGitHubReq() {
		String	result;
		Status	status;
		int		remainingRequests;

		remainingRequests = GitHubHelper.getInstance().getRemainingRequests();

		if (remainingRequests == -1) {
			result	= "Unknown";
			status	= Status.INCOMPLETE;
		} else if (remainingRequests == 0) {
			result	= "No";
			status	= Status.OUTDATED;
		} else {
			result	= "Yes - " + String.valueOf(remainingRequests);
			status	= Status.READY;
		}

		this._updateLabel(this.statusPanel.getLblGitHubReq(), result, status);
	}

	public void updateSOReq() {
		// TODO implement
		this.statusPanel.getLblSOAPIReq().setText("-");
	}

	public void updateSOStatus() {
		// TODO implement
		this.statusPanel.getLblSOLoaded().setText("-");
	}

	/**
	 * Updates repository status. If there are is no repository dir in
	 * {@link Settings#EPROJECT_REPOSITORY_PATH} then "No" will be displayed. Else
	 * if the {@link #commitsStatus is not ready} "Outdated" (in bloody red).
	 * Otherwise simply "Yes". TODO DOES NOT WORK!
	 */
	public void updateRepoStatus() {
		String	result;
		Status	status;
		boolean	cleanStatus;

		cleanStatus = false;

		if (!new File(Settings.EPROJECT_REPOSITORY_PATH.get()).exists()) {
			result	= "No";
			status	= Status.INCOMPLETE;
		} else {
			// Fetch and see if status is clear to know whether the repo is up to date
			try {
				GitUpdater.fetch(GitHubHelper.getInstance().getRepository().getCloneUrl(),
						Settings.EPROJECT_REPOSITORY_PATH.get());
				// TODO somehow git status needs to be used to see whether the repo is up to
				// date. Does not work with jgit Status. Does not work with jgit fetch dry-run
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (cleanStatus) {
				result	= "Yes";
				status	= Status.READY;
			} else {
				// result = "Outdated";
				result	= "Not working";
				status	= Status.OUTDATED;
			}
		}

		this._updateLabel(this.statusPanel.getLblRepoLoaded(), result, status);
	}

	/**
	 * Updates forks status. If there are no forks in
	 * {@link Settings#EPROJECT_FORKS} then "No" will be displayed. Otherwise "local
	 * forks" / "remote forks". If those numbers are not equal, the text will turn
	 * bloody red.
	 */
	public void updateForkStatus() {
		String	result;
		Status	status;
		File		forkDir;
		int		forkDirCount;
		int		forkCount;

		forkDir			= new File(Settings.EPROJECT_FORKS.get());
		forkCount		= 0;
		forkDirCount	= 0;

		if (!forkDir.exists()) {
			result	= "No";
			status	= Status.INCOMPLETE;
		} else {
			forkDirCount	= forkDir.list().length;
			forkCount		= GitHubHelper.getInstance().getForksCount();
			result			= "Yes - " + forkDirCount + "/" + (forkCount == -1 ? "?" : forkCount);

			// Highlight the font if not all/to many forks are loaded
			if (forkDirCount != forkCount) {
				status = Status.OUTDATED;
			} else {
				status = Status.READY;
			}
		}

		this._updateLabel(this.statusPanel.getLblForksLoaded(), result, status);
	}

	/**
	 * Updates issues status. If there are no issues in
	 * {@link Settings#ISSUES_FILE_NAME} then "No" will be displayed. Otherwise
	 * "local issues" / "remote issues". If those numbers are not equal, the text
	 * will turn bloody red.
	 */
	public void updateIssueStatus() {
		String	result;
		Status	status;
		int		localIssuesCount;
		int		remoteIssuesCount;

		remoteIssuesCount	= -1;
		localIssuesCount	= JsonManager.getItemCount(Settings.EPROJECT_PATH.get() + Settings.ISSUES_FILE_NAME.get());

		if (localIssuesCount == -1) {
			// No local issues? No reason to check how many there are on the server.
			result	= "No";
			status	= Status.INCOMPLETE;
		} else {
			// remoteIssuesCount = GitHubHelper.getInstance().getIssuesCount();

			result = "Yes - " + localIssuesCount + "/" + (remoteIssuesCount == -1 ? "?" : remoteIssuesCount);

			// Highlight the font if not all/to many issues are loaded
			if (localIssuesCount != remoteIssuesCount) {
				status = Status.OUTDATED;
			} else {
				status = Status.READY;
			}
		}

		this._updateLabel(this.statusPanel.getLblIssuesLoaded(), result, status);
	}

	/**
	 * Updates pull requests status. If there are no pull requests in
	 * {@link Settings#PULL_REQUEST_FILE_NAME} then "No" will be displayed.
	 * Otherwise "local pull requests" / "remote pull requests". If those numbers
	 * are not equal, the text will turn bloody red.
	 */
	public void updatePullRequestStatus() {
		String	result;
		Status	status;
		int		localPullRequestsCount;
		int		remotePullRequestsCount;

		remotePullRequestsCount	= -1;
		localPullRequestsCount	= JsonManager
				.getItemCount(Settings.EPROJECT_PATH.get() + Settings.PULL_REQUEST_FILE_NAME.get());

		if (localPullRequestsCount == -1) {
			// No local pull requests? No reason to check how many there are on the server.
			result	= "No";
			status	= Status.INCOMPLETE;
		} else {
			// remotePullRequestsCount = GitHubHelper.getInstance().getPullRequestsCount();
			result = "Yes - " + localPullRequestsCount + "/"
					+ (remotePullRequestsCount == -1 ? "?" : remotePullRequestsCount);

			// Highlight the font if not all/to many pull requests are loaded
			if (localPullRequestsCount != remotePullRequestsCount) {
				status = Status.OUTDATED;
			} else {
				status = Status.READY;
			}
		}

		this._updateLabel(this.statusPanel.getLblPullRequestsLoaded(), result, status);
	}

	/**
	 * Updates commits status. If there are no commits in
	 * {@link Settings#COMMIT_FILE_NAME} then "No" will be displayed. Otherwise
	 * "local commits" / "remote commits". If those numbers are not equal, the text
	 * will turn bloody red.<br>
	 */
	public void updateCommitStatus() {
		String	result;
		Status	status;
		int		localCommitsCount;
		int		remoteCommitsCount;

		remoteCommitsCount	= -1;
		localCommitsCount		= JsonManager.getItemCount(Settings.EPROJECT_PATH.get() + Settings.COMMIT_FILE_NAME.get());

		if (localCommitsCount == -1) {
			// No local commits? No reason to check how many there are on the server.
			result					= "No";
			status					= Status.INCOMPLETE;

			this.commitsStatus	= Status.INCOMPLETE;
		} else {
			// remoteCommitsCount = GitHubHelper.getInstance().getCommitsCount();
			result = "Yes - " + localCommitsCount + "/" + (remoteCommitsCount == -1 ? "?" : remoteCommitsCount);

			// Highlight the font if not all/to many issues are loaded
			if (localCommitsCount != remoteCommitsCount) {
				status					= Status.OUTDATED;
				this.commitsStatus	= Status.OUTDATED;
			} else {
				status					= Status.READY;
				this.commitsStatus	= Status.READY;
			}

		}

		this._updateLabel(this.statusPanel.getLblCommitsLoaded(), result, status);
	}

	/**
	 * Sets the {@link #statusPanel} of current {@link StatusHandler} if it's not
	 * null. In this way the {@link StatusPanel} can only be assigned once.
	 *
	 * @param statusPanel
	 *           The {@link #statusPanel} to set as {@link StatusPanel}.
	 */
	public void setStatusPanel(StatusPanel statusPanel) {
		if (this.statusPanel == null) {
			this.statusPanel = statusPanel;
		}
	}

	/**
	 * Sets the foreground color for the passed label according to the status.
	 *
	 * @param _label
	 *           A {@link JLabel}.
	 * @param _status
	 *           The {@link Status}.
	 */
	private void _setColor(JLabel _label, Status _status) {
		Color newColor;

		switch (_status) {
		case INCOMPLETE:
			newColor = Color.BLUE;
			break;
		case READY:
			newColor = Color.GREEN;
			break;
		default:
			newColor = Color.RED;
			break;
		}

		_label.setForeground(newColor);
	}

	/**
	 * Sets the text and color of a lable.
	 *
	 * @param _label
	 *           The {@link JLabel} to set up.
	 * @param _text
	 *           The new text for the label.
	 * @param _status
	 *           The {@link Status} of the label.
	 */
	private void _updateLabel(JLabel _label, String _text, Status _status) {
		this._setColor(_label, _status);
		_label.setText(_text);
	}

}
