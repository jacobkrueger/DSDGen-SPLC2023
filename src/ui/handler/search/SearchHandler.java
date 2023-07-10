package ui.handler.search;

import ui.view.SearchPanel;

/**
 * Provides an abstract logic handler for a {@link SearchPanel}.
 *
 * @author Alex Mikulinski
 * @since 13.04.2019
 */
public abstract class SearchHandler {
	/** The item number selected after search. */
	private int				selectedListItem;
	private SearchPanel	searchPanel;

	/**
	 * Creates a new {@link SearchHandler} for the passed {@link SearchPanel}.
	 *
	 * @param _searchPanel
	 *           A {@link SearchPanel}.
	 */
	public SearchHandler(SearchPanel _searchPanel) {
		this.searchPanel			= _searchPanel;
		this.selectedListItem	= -1;
	}

	/** Searches for the selected term. */
	public abstract void handleSearch();

	/** Stores the selected item. */
	public abstract void choose();

	/** Shows additional informations for selected item. */
	public abstract void showInfo();

	/**
	 * Returns the {@link #selectedListItem} of current {@link SearchHandler}.
	 *
	 * @return The {@link #selectedListItem} as int.
	 */
	public int getSelectedListItem() {
		return this.selectedListItem;
	}

	/**
	 * Sets the {@link #selectedListItem} of current {@link SearchHandler}.
	 *
	 * @param selectedListItem
	 *           The {@link #selectedListItem} to set as int.
	 */
	public void setSelectedListItem(int selectedListItem) {
		this.selectedListItem = selectedListItem;
	}

	/**
	 * Returns the {@link #searchPanel} of current {@link SearchHandler}.
	 *
	 * @return The {@link #searchPanel} as {@link SearchPanel}.
	 */
	public SearchPanel getSearchPanel() {
		return this.searchPanel;
	}

	/**
	 * Sets the {@link #searchPanel} of current {@link SearchHandler}.
	 *
	 * @param searchPanel
	 *           The {@link #searchPanel} to set as {@link SearchPanel}.
	 */
	public void setSearchPanel(SearchPanel searchPanel) {
		this.searchPanel = searchPanel;
	}

}
