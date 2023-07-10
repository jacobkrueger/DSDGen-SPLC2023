package ui.view;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;

/**
 * Provides a {@link JPanel} for navigation through the UI.
 *
 * @author Alex Mikulinski
 * @since 10.04.2019
 */
public class NavigationPanel extends JPanel {
	private static final long	serialVersionUID	= 3626657563926316749L;

	private JButton				btnRepoSearch;
	private JButton				btnClassSearch;
	private JButton				btnForkSearch;
	private JButton				btnUpdate;

	/**
	 * Create the panel.
	 */
	public NavigationPanel() {
		this.setBorder(null);
		this.setBackground(Color.WHITE);
		this.setLayout(null);

		this.btnRepoSearch = new JButton("Search Repositories");
		this.btnRepoSearch.setFont(new Font("Arial", Font.PLAIN, 11));
		this.btnRepoSearch.setBounds(10, 5, 161, 23);
		this.add(this.btnRepoSearch);

		this.btnUpdate = new JButton("Update");
		this.btnUpdate.setFont(new Font("Arial", Font.PLAIN, 11));
		this.btnUpdate.setBounds(10, 39, 161, 23);
		this.add(this.btnUpdate);

		this.btnClassSearch = new JButton("Class Examination");
		this.btnClassSearch.setFont(new Font("Arial", Font.PLAIN, 11));
		this.btnClassSearch.setBounds(10, 107, 161, 23);
		this.add(this.btnClassSearch);

		this.btnForkSearch = new JButton("Search Forks");
		this.btnForkSearch.setFont(new Font("Arial", Font.PLAIN, 11));
		this.btnForkSearch.setBounds(10, 73, 161, 23);
		this.add(this.btnForkSearch);
	}

	/**
	 * Returns the {@link #btnRepoSearch} of current {@link NavigationPanel}.
	 *
	 * @return The {@link #btnRepoSearch} as {@link JButton}.
	 */
	public JButton getBtnRepoSearch() {
		return this.btnRepoSearch;
	}

	/**
	 * Returns the {@link #btnClassSearch} of current {@link NavigationPanel}.
	 *
	 * @return The {@link #btnClassSearch} as {@link JButton}.
	 */
	public JButton getBtnClassSearch() {
		return this.btnClassSearch;
	}

	/**
	 * Returns the {@link #btnUpdate} of current {@link NavigationPanel}.
	 *
	 * @return The {@link #btnUpdate} as {@link JButton}.
	 */
	public JButton getBtnUpdate() {
		return this.btnUpdate;
	}

	/**
	 * Returns the {@link #btnForkSearch} of current {@link NavigationPanel}.
	 *
	 * @return The {@link #btnForkSearch} as {@link JButton}.
	 */
	public JButton getBtnForkSearch() {
		return this.btnForkSearch;
	}

	/**
	 * Sets the {@link #btnForkSearch} of current {@link NavigationPanel}.
	 *
	 * @param btnForkSearch
	 *           The {@link #btnForkSearch} to set as {@link JButton}.
	 */
	public void setBtnForkSearch(JButton btnForkSearch) {
		this.btnForkSearch = btnForkSearch;
	}
}
