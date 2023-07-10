package ui.view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import ui.handler.search.SearchHandler;

/**
 * Provides a {@link JPanel} for searching for repositories or classes.
 *
 * @author Alex Mikulinski
 * @since 03.04.2019
 */
public class SearchPanel extends JPanel {
	private static final long	serialVersionUID	= 4365691215059636456L;

	private JScrollPane			scrollPaneInfo;
	private JScrollPane			scrollPaneList;
	private JTextField			txtSearchBar;
	private JTextArea				txtrInfo;
	private JButton				btnSelection;
	private JButton				btnSearch;
	private JLabel					lblListHeader;
	private JLabel					lblWelcome;
	private JList<String>		resultList;

	private SearchHandler		searchHandler;

	/**
	 * Create the panel.
	 */
	public SearchPanel() {
		this.setBackground(Color.WHITE);
		this.setBorder(null);
		this.setLayout(null);

		this.lblWelcome = new JLabel();
		this.lblWelcome.setFont(new Font("Arial", Font.BOLD, 13));
		this.lblWelcome.setBounds(15, 5, 579, 20);
		this.add(this.lblWelcome);

		this.txtSearchBar = new JTextField();
		this.txtSearchBar.setFont(new Font("Arial", Font.PLAIN, 12));
		this.txtSearchBar.setBounds(15, 55, 640, 20);
		this.txtSearchBar.setText("Type in search terms.");
		this.txtSearchBar.setColumns(10);
		this.add(this.txtSearchBar);

		this.resultList = new JList<>();
		this.resultList.setFont(new Font("Monospaced", Font.PLAIN, 11));
		this.resultList.setVisibleRowCount(20);
		this.resultList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.resultList.setBorder(null);

		this.scrollPaneList = new JScrollPane();
		this.scrollPaneList.setBounds(15, 105, 800, 234);
		this.add(this.scrollPaneList);
		this.scrollPaneList.setViewportView(this.resultList);

		this.lblListHeader = new JLabel("");
		this.lblListHeader.setFont(new Font("Monospaced", Font.PLAIN, 11));
		this.lblListHeader.setBounds(15, 86, 590, 20);
		this.add(this.lblListHeader);

		this.txtrInfo = new JTextArea();
		this.txtrInfo.setFont(new Font("Monospaced", Font.PLAIN, 11));

		this.scrollPaneInfo = new JScrollPane();
		this.scrollPaneInfo.setBounds(15, 384, 800, 161);
		this.add(this.scrollPaneInfo);
		this.scrollPaneInfo.setViewportView(this.txtrInfo);

		this.btnSelection = new JButton("Choose selected");
		this.btnSelection.setFont(new Font("Arial", Font.PLAIN, 12));
		this.btnSelection.setBounds(15, 350, 150, 23);
		this.add(this.btnSelection);

		this.btnSearch = new JButton("Search");
		this.btnSearch.setFont(new Font("Arial", Font.PLAIN, 12));
		this.btnSearch.setBounds(665, 55, 150, 20);
		this.add(this.btnSearch);

		this._addListeners();
	}

	private void _addListeners() {
		this.txtSearchBar.addActionListener(a -> this.searchHandler.handleSearch());
		this.btnSearch.addActionListener(a -> this.searchHandler.handleSearch());

		this.resultList.addListSelectionListener(e -> {
			// Prevents this listener from fireing multiple times
			if (e.getValueIsAdjusting()) {
				return;
			}

			@SuppressWarnings("unchecked")
			JList<String> jList = (JList<String>) e.getSource();
			this.searchHandler.setSelectedListItem(jList.getSelectedIndex());

			this.searchHandler.showInfo();
		});

		this.btnSelection.addActionListener(a -> this.searchHandler.choose());
	}

	/**
	 * Returns the {@link #txtSearchBar} of current {@link SearchPanel}.
	 *
	 * @return The {@link #txtSearchBar} as {@link JTextField}.
	 */
	public JTextField getTxtSearchBar() {
		return this.txtSearchBar;
	}

	/**
	 * Returns the {@link #txtrInfo} of current {@link SearchPanel}.
	 *
	 * @return The {@link #txtrInfo} as {@link JTextArea}.
	 */
	public JTextArea getTxtrInfo() {
		return this.txtrInfo;
	}

	/**
	 * Returns the {@link #lblListHeader} of current {@link SearchPanel}.
	 *
	 * @return The {@link #lblListHeader} as {@link JLabel}.
	 */
	public JLabel getLblListHeader() {
		return this.lblListHeader;
	}

	/**
	 * Returns the {@link #resultList} of current {@link SearchPanel}.
	 *
	 * @return The {@link #resultList} as {@link JList}.
	 */
	public JList<String> getResultList() {
		return this.resultList;
	}

	/**
	 * Sets the text for the {@link #lblWelcome} of current {@link SearchPanel}.
	 *
	 * @param _text
	 *           The text as {@link String}.
	 */
	public void setWelcomeText(String _text) {
		this.lblWelcome.setText(_text);
	}

	/**
	 * Sets the {@link #searchHandler} of current {@link SearchPanel}.
	 *
	 * @param searchHandler
	 *           The {@link #searchHandler} to set as {@link SearchHandler}.
	 */
	public void setSearchHandler(SearchHandler searchHandler) {
		this.searchHandler = searchHandler;
	}

	/**
	 * Returns the {@link #btnSelection} of current {@link SearchPanel}.
	 *
	 * @return The {@link #btnSelection} as {@link JButton}.
	 */
	public JButton getBtnSelection() {
		return this.btnSelection;
	}

}
