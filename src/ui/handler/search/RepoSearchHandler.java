package ui.handler.search;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import org.eclipse.egit.github.core.Repository;
import app.Settings;
import ui.handler.StatusHandler;
import ui.view.SearchPanel;
import ui.view.WarningDialog;
import utils.FileManager;
import utils.GitHubHelper;
import utils.RepoSearch;

/**
 * Contains the logic behind {@link SearchPanel}.
 *
 * @author Alex Mikulinski
 * @since 03.04.2019
 */
public class RepoSearchHandler extends SearchHandler {
	private RepoSearch repoSearch;

	/**
	 * Creates a new {@link SearchHandler} for the passed {@link SearchPanel}.
	 *
	 * @param _searchPanel
	 *           A {@link SearchPanel}.
	 */
	public RepoSearchHandler(SearchPanel _searchPanel) {
		super(_searchPanel);
		this.repoSearch = new RepoSearch(GitHubHelper.getInstance().getGitHubClient());
	}

	/**
	 * Searches for the selected repository via {@link RepoSearch}.
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

		try {
			this.repoSearch.search(this.getSearchPanel().getTxtSearchBar().getText());
		} catch (IOException e) {
			e.printStackTrace();
		}

		results = this.repoSearch.getResults();

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
	}

	/**
	 * Saves the repository name and its owner to {@link Settings}.
	 */
	@Override
	public void choose() {
		Repository	repository;
		File			projectDir;

		repository = null;

		if (this.getSelectedListItem() == -1 || this.repoSearch.getResults() == null) {
			new WarningDialog("No repository chosen because no repository selected!");
			return;
		}

		try {
			repository = this.repoSearch.getRepo(this.getSelectedListItem());
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (repository == null) {
			new WarningDialog("No repository chosen because unable to get the chosen repository!");
			return;
		}

		// Create a directory for the project
		projectDir = new File(Settings.EPROJECT_PATH.get());
		if (!projectDir.exists()) {
			FileManager.createDirs(projectDir.getPath());
		}

		try {
			Settings.EPROJECT_OWNER.save(repository.getOwner().getLogin());
			Settings.EPROJECT_NAME.save(repository.getName());
			Settings.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		GitHubHelper.getInstance().changeRepository();
		StatusHandler.getInstance().updateAll();
	}

	/**
	 * Shows additional informations for selected repository.
	 */
	@Override
	public void showInfo() {
		String info;

		info = null;

		if (!GitHubHelper.getInstance().hasRemainingRequests()) {
			this.getSearchPanel().getTxtrInfo().setText("No remaining requests!");
			return;
		}

		if (this.getSelectedListItem() < 0) {
			this.getSearchPanel().getTxtrInfo().setText("No repository selected.");
			return;
		}

		try {
			info = this.repoSearch.getInfo(this.getSelectedListItem());
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
}
