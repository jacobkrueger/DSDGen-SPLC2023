package ui.view;

import javax.swing.JPanel;

import ui.handler.StatusHandler;
import ui.handler.UpdateHandler;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JTextField;

import app.Settings;

/**
 * Provides a {@link JPanel} for updating repository data.
 *
 * @author Alex Mikulinski
 * @since 10.04.2019
 */
public class UpdatePanel extends JPanel {
	private static final long	serialVersionUID	= -421595617411887654L;

	private JButton				btnUpdateStackOverflow;
	private JButton				btnUpdateGitRepository;
	private JButton				btnUpdateCommits;
	private JButton				btnUpdateIssues;
	private JButton				btnUpdateAll;

	private UpdateHandler		updateHandler;
	private JButton				btnUpdatePullRequests;
	private JTextField			txtTags;

	/**
	 * Create the panel.
	 */
	public UpdatePanel() {
		this.setBackground(Color.WHITE);
		this.setLayout(null);

		this._addButtons();
		this._addListeners();
	}

	private void _addButtons() {
		this.btnUpdateAll = new JButton("Update All");
		this.btnUpdateAll.setBounds(10, 11, 177, 23);
		this.add(this.btnUpdateAll);

		this.btnUpdateGitRepository = new JButton("Update Git Repository");
		this.btnUpdateGitRepository.setBounds(10, 79, 177, 23);
		this.add(this.btnUpdateGitRepository);

		this.btnUpdateIssues = new JButton("Update Issues");
		this.btnUpdateIssues.setBounds(10, 113, 177, 23);
		this.add(this.btnUpdateIssues);

		this.btnUpdateCommits = new JButton("Update Commits");
		this.btnUpdateCommits.setBounds(10, 147, 177, 23);
		this.add(this.btnUpdateCommits);

		this.btnUpdateStackOverflow = new JButton("Update Stack Overflow");
		this.btnUpdateStackOverflow.setBounds(10, 45, 177, 23);
		this.add(this.btnUpdateStackOverflow);

		this.btnUpdatePullRequests = new JButton("Update Pull Requests");
		this.btnUpdatePullRequests.setBounds(10, 183, 177, 23);
		this.add(this.btnUpdatePullRequests);

		this.txtTags = new JTextField();
		this.txtTags.setBounds(197, 46, 200, 20);
		this.txtTags.setColumns(10);
		this.txtTags.setText(Settings.SO_TAGS.get());
		this.add(this.txtTags);
	}

	private void _addListeners() {
		this.btnUpdateStackOverflow.addActionListener(a -> {
			this.updateHandler.updateSO();
			StatusHandler.getInstance().updateSOStatus();
			StatusHandler.getInstance().updateSOReq();
		});
		this.btnUpdateGitRepository.addActionListener(a -> {
			this.updateHandler.updateGitRepository();
			StatusHandler.getInstance().updateRepoStatus();
			StatusHandler.getInstance().updateGitHubReq();
		});
		this.btnUpdateCommits.addActionListener(a -> {
			this.updateHandler.updateCommits();
			StatusHandler.getInstance().updateCommitStatus();
			StatusHandler.getInstance().updateGitHubReq();
		});
		this.btnUpdateIssues.addActionListener(a -> {
			this.updateHandler.updateIssues();
			StatusHandler.getInstance().updateIssueStatus();
			StatusHandler.getInstance().updateGitHubReq();
		});
		this.btnUpdatePullRequests.addActionListener(a -> {
			this.updateHandler.updatePullRequests();
			StatusHandler.getInstance().updatePullRequestStatus();
			StatusHandler.getInstance().updateGitHubReq();
		});
		this.btnUpdateAll.addActionListener(a -> {
			this.updateHandler.updateAll();
			StatusHandler.getInstance().updateAll();
		});
	}

	/**
	 * Returns the {@link #txtTags} of current {@link UpdatePanel}.
	 *
	 * @return The {@link #txtTags} as {@link JTextField}.
	 */
	public JTextField getTxtTags() {
		return this.txtTags;
	}

	/**
	 * Sets the {@link #updateHandler} of current {@link UpdatePanel}.
	 *
	 * @param updateHandler
	 *           The {@link #updateHandler} to set as {@link UpdateHandler}.
	 */
	public void setUpdateHandler(UpdateHandler updateHandler) {
		this.updateHandler = updateHandler;
	}
}
