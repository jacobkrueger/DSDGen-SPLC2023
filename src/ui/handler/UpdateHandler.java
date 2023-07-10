package ui.handler;

import java.io.IOException;

import javax.swing.JTextField;

import org.apache.commons.lang3.StringUtils;

import app.Settings;
import ui.UIManager;
import ui.view.UpdatePanel;
import ui.view.WarningDialog;
import update.CommitUpdater;
import update.GitUpdater;
import update.IssueUpdater;
import update.PullRequestUpdater;
import update.Updater;
import update.stackOverflow.SOUpdater;
import utils.FileManager;
import utils.GitHubHelper;

/**
 * Provides handling for updating all used databases.
 *
 * @author Alex Mikulinski
 * @since Jan 21, 2019
 */
public class UpdateHandler {
	private UpdatePanel	updatePanel;
	private Updater		sOUpdater;
	private Updater		gitUpdater;
	private Updater		issueUpdater;
	private Updater		commitUpdater;
	private Updater		pullRequestUpdater;

	private final String	SO_TAGS_TEXT	= "Please chose tags separated by ';'";

	/**
	 * Creates a new {@link UpdateHandler} for the passed {@link UpdatePanel}.
	 *
	 * @param _updatePanel
	 *           A {@link UpdatePanel}.
	 */
	public UpdateHandler(UpdatePanel _updatePanel) {
		this.updatePanel = _updatePanel;
	}

	/**
	 * Updates the whole database.
	 */
	public void updateAll() {
		this.updateSO();
		this.updateGitRepository();
		this.updateIssues();
		this.updatePullRequests();
		this.updateCommits();
	}

	/**
	 * Gets the Stack Overflow tags from {@link UpdatePanel}, checks them and stores
	 * to config.
	 */
	private boolean SOTagsOk() {
		JTextField	tagsField;
		String		text;

		tagsField	= this.updatePanel.getTxtTags();
		text			= tagsField.getText();

		if (text.equals(this.SO_TAGS_TEXT)) {
			return false;
		} else if (StringUtils.isBlank(text)) {
			tagsField.setText(this.SO_TAGS_TEXT);
			return false;
		}

		try {
			Settings.SO_TAGS.save(text);
			Settings.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return true;
	}

	/**
	 * Updates the database for StackOverflow to {@link Settings#SO_PATH}.
	 */
	public void updateSO() {
		if (!this.SOTagsOk()) {
			new WarningDialog("Please set the tags!");
			return;
		}

		FileManager.createDirs(Settings.SO_PATH.get());

		try {
			this.getSOUpdater().update(Settings.SO_PATH.get());
		} catch (IOException _e) {
			System.err.println("Failed to update StackOverflow data.");
			_e.printStackTrace();
		}
	}

	/**
	 * Updates the original repository to {@link Settings#EPROJECT_REPOSITORY_PATH}.
	 */
	public void updateGitRepository() {
		try {
			UIManager.getInstance().setCursorWait();
			this.getGitUpdater().update(Settings.EPROJECT_REPOSITORY_PATH.get());
			UIManager.getInstance().setCursorNormal();
		} catch (IOException _e) {
			System.err.println("Failed to update the original repository.");
			_e.printStackTrace();
		}
	}

	/**
	 * Updates the issues for the original repository to
	 * {@link Settings#EPROJECT_PATH}.
	 */
	public void updateIssues() {
		try {
			this.getIssueUpdater().update(Settings.EPROJECT_PATH.get());
		} catch (IOException _e) {
			System.err.println("Failed to update the issues.");
			_e.printStackTrace();
		}
	}

	/**
	 * Updates the pull requests for the original repository to
	 * {@link Settings#EPROJECT_PATH}.
	 */
	public void updatePullRequests() {
		try {
			this.getPullRequestUpdater().update(Settings.EPROJECT_PATH.get());
		} catch (IOException _e) {
			System.err.println("Failed to update the pull requests.");
			_e.printStackTrace();
		}
	}

	/**
	 * Updates the commits for the original repository to
	 * {@link Settings#EPROJECT_PATH}.
	 */
	public void updateCommits() {
		try {
			UIManager.getInstance().setCursorWait();
			this.getCommitUpdater().update(Settings.EPROJECT_PATH.get());
			UIManager.getInstance().setCursorNormal();
		} catch (IOException _e) {
			System.err.println("Failed to update the commits.");
			_e.printStackTrace();
		}
	}

	/**
	 * Returns the {@link #sOUpdater} of current {@link UpdateHandler}. If the
	 * {@link #sOUpdater} is null creates an instance of it.
	 *
	 * @return The {@link #sOUpdater} as {@link SOUpdater}.
	 */
	public Updater getSOUpdater() {
		if (this.sOUpdater == null) {
			this.sOUpdater = new SOUpdater();
		}
		return this.sOUpdater;
	}

	/**
	 * Returns the {@link #gitUpdater} of current {@link UpdateHandler}. If the
	 * {@link #gitUpdater} is null creates an instance of it.
	 *
	 * @return The {@link #gitUpdater} as {@link Updater}.
	 * @throws IOException
	 */
	public Updater getGitUpdater() throws IOException {
		if (this.gitUpdater == null) {
			this.gitUpdater = new GitUpdater(GitHubHelper.getInstance().getRepository());
		}
		return this.gitUpdater;
	}

	/**
	 * Returns the {@link #issueUpdater} of current {@link UpdateHandler}. If the
	 * {@link #issueUpdater} is null creates an instance of it.
	 *
	 * @return The {@link #issueUpdater} as {@link Updater}.
	 * @throws IOException
	 */
	public Updater getIssueUpdater() throws IOException {
		if (this.issueUpdater == null) {
			this.issueUpdater = new IssueUpdater(GitHubHelper.getInstance().getRepository(),
					GitHubHelper.getInstance().getGitHubClient());
		}
		return this.issueUpdater;
	}

	/**
	 * Returns the {@link #pullRequestUpdater} of current {@link UpdateHandler}. If
	 * the {@link #pullRequestUpdater} is null creates an instance of it.
	 *
	 * @return The {@link #pullRequestUpdater} as {@link Updater}.
	 * @throws IOException
	 */
	public Updater getPullRequestUpdater() throws IOException {
		if (this.pullRequestUpdater == null) {
			this.pullRequestUpdater = new PullRequestUpdater(GitHubHelper.getInstance().getRepository(),
					GitHubHelper.getInstance().getGitHubClient());
		}
		return this.pullRequestUpdater;
	}

	/**
	 * Returns the {@link #commitUpdater} of current {@link UpdateHandler}. If the
	 * {@link #commitUpdater} is null creates an instance of it.
	 *
	 * @return The {@link #commitUpdater} as {@link Updater}.
	 * @throws IOException
	 */
	public Updater getCommitUpdater() throws IOException {
		if (this.commitUpdater == null) {
			this.commitUpdater = new CommitUpdater(Settings.EPROJECT_REPOSITORY_PATH.get(),
					GitHubHelper.getInstance().getRepository().getOwner().getLogin(),
					GitHubHelper.getInstance().getRepository().getName());
		}
		return this.commitUpdater;
	}

}
