package ui.view;

import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;

import ui.handler.StatusHandler;

/**
 * Provides a {@link JPanel} for displaying the data status for a repository.
 *
 * @author Alex Mikulinski
 * @since 10.04.2019
 */
public class StatusPanel extends JPanel {
	private static final long	serialVersionUID	= -6040853259892748178L;

	private JLabel					lblCurOwnerName;
	private JLabel					lblGitHubReq;
	private JLabel					lblSOLoaded;
	private JLabel					lblSOAPIReq;
	private JLabel					lblCommitsLoaded;
	private JLabel					lblIssuesLoaded;
	private JLabel					lblForksLoaded;
	private JLabel					lblRepoLoaded;
	private JLabel					lblPullRequestsLoaded;

	/**
	 * Create the panel.
	 */
	public StatusPanel() {
		this.setBackground(Color.WHITE);
		this.setLayout(null);

		this._addTextLabels();
		this._addOutputLabels();

		StatusHandler.getInstance().setStatusPanel(this);

		StatusHandler.getInstance().updateAll();
	}

	private void _addTextLabels() {
		JLabel lblStatus = new JLabel("Status:");
		lblStatus.setFont(new Font("Arial", Font.BOLD, 14));
		lblStatus.setBounds(10, 11, 78, 14);
		this.add(lblStatus);

		JLabel lblCurrentRepositoryName = new JLabel("Current repository owner / name:");
		lblCurrentRepositoryName.setFont(new Font("Arial", Font.BOLD, 11));
		lblCurrentRepositoryName.setBounds(10, 42, 197, 14);
		this.add(lblCurrentRepositoryName);

		JLabel lblGithubApiRemaining = new JLabel("GitHub API remaining requests:");
		lblGithubApiRemaining.setFont(new Font("Arial", Font.BOLD, 11));
		lblGithubApiRemaining.setBounds(10, 83, 197, 14);
		this.add(lblGithubApiRemaining);

		JLabel lblSoApiRemaining = new JLabel("SO API remaining requests:");
		lblSoApiRemaining.setFont(new Font("Arial", Font.BOLD, 11));
		lblSoApiRemaining.setBounds(10, 124, 197, 14);
		this.add(lblSoApiRemaining);

		JLabel lblRepositoryLoaded = new JLabel("Repository loaded:");
		lblRepositoryLoaded.setFont(new Font("Arial", Font.BOLD, 11));
		lblRepositoryLoaded.setBounds(10, 165, 113, 14);
		this.add(lblRepositoryLoaded);

		JLabel lblForksLoadedTxt = new JLabel("Forks loaded:");
		lblForksLoadedTxt.setFont(new Font("Arial", Font.BOLD, 11));
		lblForksLoadedTxt.setBounds(10, 190, 113, 14);
		this.add(lblForksLoadedTxt);

		JLabel lblIssuesLoadedTxt = new JLabel("Issues loaded:");
		lblIssuesLoadedTxt.setFont(new Font("Arial", Font.BOLD, 11));
		lblIssuesLoadedTxt.setBounds(10, 215, 113, 14);
		this.add(lblIssuesLoadedTxt);

		JLabel lblCommitsLoadedTxt = new JLabel("Commits loaded:");
		lblCommitsLoadedTxt.setFont(new Font("Arial", Font.BOLD, 11));
		lblCommitsLoadedTxt.setBounds(10, 240, 113, 14);
		this.add(lblCommitsLoadedTxt);

		JLabel lblStackoverflowLoaded = new JLabel("StackOverflow loaded:");
		lblStackoverflowLoaded.setFont(new Font("Arial", Font.BOLD, 11));
		lblStackoverflowLoaded.setBounds(10, 265, 133, 14);
		this.add(lblStackoverflowLoaded);

		JLabel lblPullRequestsLoadedTxt = new JLabel("Pull Requests loaded:");
		lblPullRequestsLoadedTxt.setFont(new Font("Arial", Font.BOLD, 11));
		lblPullRequestsLoadedTxt.setBounds(10, 286, 133, 14);
		this.add(lblPullRequestsLoadedTxt);
	}

	private void _addOutputLabels() {
		this.lblCurOwnerName = new JLabel();
		this.lblCurOwnerName.setFont(new Font("Arial", Font.PLAIN, 11));
		this.lblCurOwnerName.setBounds(10, 58, 197, 14);
		this.add(this.lblCurOwnerName);

		this.lblGitHubReq = new JLabel();
		this.lblGitHubReq.setFont(new Font("Arial", Font.PLAIN, 11));
		this.lblGitHubReq.setBounds(10, 99, 197, 14);
		this.add(this.lblGitHubReq);

		this.lblSOAPIReq = new JLabel();
		this.lblSOAPIReq.setFont(new Font("Arial", Font.PLAIN, 11));
		this.lblSOAPIReq.setBounds(10, 140, 197, 14);
		this.add(this.lblSOAPIReq);

		this.lblRepoLoaded = new JLabel();
		this.lblRepoLoaded.setHorizontalAlignment(SwingConstants.CENTER);
		this.lblRepoLoaded.setFont(new Font("Arial", Font.PLAIN, 11));
		this.lblRepoLoaded.setBounds(133, 165, 74, 14);
		this.add(this.lblRepoLoaded);

		this.lblForksLoaded = new JLabel();
		this.lblForksLoaded.setHorizontalAlignment(SwingConstants.CENTER);
		this.lblForksLoaded.setFont(new Font("Arial", Font.PLAIN, 11));
		this.lblForksLoaded.setBounds(133, 190, 74, 14);
		this.add(this.lblForksLoaded);

		this.lblIssuesLoaded = new JLabel();
		this.lblIssuesLoaded.setHorizontalAlignment(SwingConstants.CENTER);
		this.lblIssuesLoaded.setFont(new Font("Arial", Font.PLAIN, 11));
		this.lblIssuesLoaded.setBounds(133, 215, 74, 14);
		this.add(this.lblIssuesLoaded);

		this.lblCommitsLoaded = new JLabel();
		this.lblCommitsLoaded.setHorizontalAlignment(SwingConstants.CENTER);
		this.lblCommitsLoaded.setFont(new Font("Arial", Font.PLAIN, 11));
		this.lblCommitsLoaded.setBounds(133, 240, 74, 14);
		this.add(this.lblCommitsLoaded);

		this.lblSOLoaded = new JLabel();
		this.lblSOLoaded.setHorizontalAlignment(SwingConstants.CENTER);
		this.lblSOLoaded.setFont(new Font("Arial", Font.PLAIN, 11));
		this.lblSOLoaded.setBounds(133, 265, 74, 14);
		this.add(this.lblSOLoaded);

		this.lblPullRequestsLoaded = new JLabel();
		this.lblPullRequestsLoaded.setHorizontalAlignment(SwingConstants.CENTER);
		this.lblPullRequestsLoaded.setFont(new Font("Arial", Font.PLAIN, 11));
		this.lblPullRequestsLoaded.setBounds(133, 286, 74, 14);
		this.add(this.lblPullRequestsLoaded);
	}

	/**
	 * Returns the {@link #lblCurOwnerName} of current {@link StatusPanel}.
	 *
	 * @return The {@link #lblCurOwnerName} as {@link JLabel}.
	 */
	public JLabel getLblCurOwnerName() {
		return this.lblCurOwnerName;
	}

	/**
	 * Returns the {@link #lblGitHubReq} of current {@link StatusPanel}.
	 *
	 * @return The {@link #lblGitHubReq} as {@link JLabel}.
	 */
	public JLabel getLblGitHubReq() {
		return this.lblGitHubReq;
	}

	/**
	 * Returns the {@link #lblSOLoaded} of current {@link StatusPanel}.
	 *
	 * @return The {@link #lblSOLoaded} as {@link JLabel}.
	 */
	public JLabel getLblSOLoaded() {
		return this.lblSOLoaded;
	}

	/**
	 * Returns the {@link #lblSOAPIReq} of current {@link StatusPanel}.
	 *
	 * @return The {@link #lblSOAPIReq} as {@link JLabel}.
	 */
	public JLabel getLblSOAPIReq() {
		return this.lblSOAPIReq;
	}

	/**
	 * Returns the {@link #lblCommitsLoaded} of current {@link StatusPanel}.
	 *
	 * @return The {@link #lblCommitsLoaded} as {@link JLabel}.
	 */
	public JLabel getLblCommitsLoaded() {
		return this.lblCommitsLoaded;
	}

	/**
	 * Returns the {@link #lblIssuesLoaded} of current {@link StatusPanel}.
	 *
	 * @return The {@link #lblIssuesLoaded} as {@link JLabel}.
	 */
	public JLabel getLblIssuesLoaded() {
		return this.lblIssuesLoaded;
	}

	/**
	 * Returns the {@link #lblForksLoaded} of current {@link StatusPanel}.
	 *
	 * @return The {@link #lblForksLoaded} as {@link JLabel}.
	 */
	public JLabel getLblForksLoaded() {
		return this.lblForksLoaded;
	}

	/**
	 * Returns the {@link #lblRepoLoaded} of current {@link StatusPanel}.
	 *
	 * @return The {@link #lblRepoLoaded} as {@link JLabel}.
	 */
	public JLabel getLblRepoLoaded() {
		return this.lblRepoLoaded;
	}

	/**
	 * Returns the {@link #lblPullRequestsLoaded} of current {@link StatusPanel}.
	 *
	 * @return The {@link #lblPullRequestsLoaded} as {@link JLabel}.
	 */
	public JLabel getLblPullRequestsLoaded() {
		return this.lblPullRequestsLoaded;
	}

	/**
	 * Sets the {@link #lblPullRequestsLoaded} of current {@link StatusPanel}.
	 *
	 * @param lblPullRequestsLoaded
	 *           The {@link #lblPullRequestsLoaded} to set as {@link JLabel}.
	 */
	public void setLblPullRequestsLoaded(JLabel lblPullRequestsLoaded) {
		this.lblPullRequestsLoaded = lblPullRequestsLoaded;
	}
}
