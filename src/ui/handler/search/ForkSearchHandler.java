package ui.handler.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import org.eclipse.egit.github.core.Repository;

import app.Settings;
import docGen.DocumentationGenerator;
import ui.UIManager;
import ui.handler.StatusHandler;
import ui.view.SearchPanel;
import ui.view.WarningDialog;
import update.ForkUpdater;
import utils.FileManager;
import utils.ForkSearch;
import utils.GitHubHelper;

/**
 * Contains the logic behind {@link SearchPanel}.
 *
 * @author Alex Mikulinski
 * @since 03.04.2019
 */
public class ForkSearchHandler extends SearchHandler {
	private final String		WARNING__WRONG_SELECTION	= "Please select AND download 1 or 2 forks!";
	private ForkSearch		forkSearch;
	private List<Integer>	selectedListItems;

	/**
	 * Creates a new {@link SearchHandler} for the passed {@link SearchPanel}.
	 *
	 * @param _searchPanel
	 *           A {@link SearchPanel}.
	 */
	public ForkSearchHandler(SearchPanel _searchPanel) {
		super(_searchPanel);
		this.forkSearch			= new ForkSearch(GitHubHelper.getInstance().getGitHubClient());
		this.selectedListItems	= new ArrayList<>();
	}

	/**
	 * Searches for the selected repository via {@link ForkSearch}.
	 */
	@Override
	public void handleSearch() {
		DefaultListModel<String>	listModel;
		List<String>					results;

		results		= new ArrayList<>();
		listModel	= new DefaultListModel<>();

		if (!GitHubHelper.getInstance().hasRemainingRequests()) {
			listModel.addElement("No remaining requests!");
			this.getSearchPanel().getResultList().setModel(listModel);
			return;
		}

		UIManager.getInstance().setCursorWait();

		try {
			this.forkSearch.search(GitHubHelper.getInstance().getRepository(),
					this.getSearchPanel().getTxtSearchBar().getText());
		} catch (IOException e) {
			e.printStackTrace();
		}

		results = this.forkSearch.getResults();

		if (results == null || results.isEmpty()) {
			listModel.addElement("No results found!");
		} else {

			// Set the header (first item of list) and then remove it
			this.getSearchPanel().getLblListHeader().setText(results.get(0));
			results.remove(0);

			listModel.addAll(results);
		}

		this.getSearchPanel().getResultList().setModel(listModel);

		StatusHandler.getInstance().updateGitHubReq();

		UIManager.getInstance().setCursorNormal();
	}

	/**
	 * Will check if at least one fork was slected. Then checks whether the selected
	 * forks were also downloaded. If yes sets their owners to
	 * {@link DocumentationGenerator}. Else displays warnings.
	 */
	@Override
	public void choose() {
		Repository tempRepository;

		if (this.selectedListItems.isEmpty() || this.forkSearch.getResults() == null
				|| this.selectedListItems.size() > 2) {
			new WarningDialog(this.WARNING__WRONG_SELECTION);
			return;
		}

		tempRepository = this.forkSearch.getResult(this.selectedListItems.get(0));

		// Check if at least the first fork was already downloaded
		if (!FileManager.exists(Settings.EPROJECT_FORKS.get() + tempRepository.getOwner().getLogin())) {
			new WarningDialog(this.WARNING__WRONG_SELECTION);
			return;
		}

		DocumentationGenerator.getInstance().setRepository1(tempRepository);

		// If only one fork was selected, there's nothing more to do
		if (this.selectedListItems.size() < 2) {
			new WarningDialog("You've selected the fork from " + tempRepository.getOwner().getLogin());
			return;
		}

		tempRepository = this.forkSearch.getResult(this.selectedListItems.get(1));

		// Check if the second fork was already downloaded
		if (!FileManager.exists(Settings.EPROJECT_FORKS.get() + tempRepository.getOwner().getLogin())) {
			new WarningDialog(
					"Will not use the second fork because it was not downloaded. If you want to use it, please download it and select 'choose' again.");
			return;
		}

		DocumentationGenerator.getInstance().setRepository2(tempRepository);

		new WarningDialog("You've selected the forks from "
				+ DocumentationGenerator.getInstance().getRepository1().getOwner().getLogin() + " and "
				+ DocumentationGenerator.getInstance().getRepository2().getOwner().getLogin());
	}

	/**
	 * Shows additional informations for selected repository.
	 */
	@Override
	public void showInfo() {
		String info;

		info = null;

		if (this.getSelectedListItem() < 0) {
			this.getSearchPanel().getTxtrInfo().setText("No repository selected.");
			return;
		}

		try {
			info = this.forkSearch.getInfo(this.getSelectedListItem());
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (info != null) {
			this.getSearchPanel().getTxtrInfo().setText(info);
		} else {
			this.getSearchPanel().getTxtrInfo().setText("No Info found!");
		}

		StatusHandler.getInstance().updateGitHubReq();
	}

	/**
	 * Sets a {@link List} of selected indices.
	 *
	 * @param _selectedIndices
	 */
	public void setSelectedListItems(List<Integer> _selectedIndices) {
		this.selectedListItems = _selectedIndices;
	}

	/**
	 * Downloads selected forks.
	 */
	public void download() {
		List<Repository>	repositories;
		ForkUpdater			forkUpdater;

		repositories	= new ArrayList<>();
		forkUpdater		= new ForkUpdater(GitHubHelper.getInstance().getRepository(),
				GitHubHelper.getInstance().getGitHubClient());

		if (this.selectedListItems.isEmpty() || this.forkSearch.getResults() == null) {
			new WarningDialog("No fork downloaded because no fork selected!");
			return;
		}

		this.selectedListItems.forEach(i -> {
			repositories.add(this.forkSearch.getResult(i));
		});

		try {
			forkUpdater.cloneForks(repositories, Settings.EPROJECT_FORKS.get());
		} catch (IOException e) {
			System.err.println("Failed downloading forks.");
			e.printStackTrace();
		}

		StatusHandler.getInstance().updateForkStatus();
		StatusHandler.getInstance().updateGitHubReq();

	}
}
