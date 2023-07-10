package ui.handler.search;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.DefaultListModel;

import app.Settings;
import classContainer.ClassInfo;
import docGen.DocumentationGenerator;
import ui.view.SearchPanel;
import ui.view.WarningDialog;
import utils.ClassSearch;
import utils.FileManager;

/**
 * Contains the logic behind the {@link SearchPanel} for classes.
 *
 * @author Alex Mikulinski
 * @since 02.06.2019
 */
public class ClassSearchHandler extends SearchHandler {
	private final String		INFO_FORMATTER	= "%-5s %s\n";
	private List<ClassInfo>	classes;

	/**
	 * Creates a new {@link SearchHandler} for the passed {@link SearchPanel}.
	 *
	 * @param _searchPanel
	 *           A {@link SearchPanel}.
	 */
	public ClassSearchHandler(SearchPanel _searchPanel) {
		super(_searchPanel);
	}

	@Override
	public void handleSearch() {
		DefaultListModel<String>	listModel;
		ClassSearch						classSearch;

		classSearch	= new ClassSearch();
		listModel	= new DefaultListModel<>();

		if (!FileManager.exists(Settings.EPROJECT_REPOSITORY_PATH.get())) {
			listModel.addElement("No repository found. Please choose and update the repository first!");
			this.getSearchPanel().getResultList().setModel(listModel);
			return;
		}

		this.classes = classSearch.search(this.getSearchPanel().getTxtSearchBar().getText(),
				Settings.EPROJECT_PATH.get());
		this.classes.forEach(c -> listModel.addElement(c.getFqn()));

		this.getSearchPanel().getResultList().setModel(listModel);
	}

	@Override
	public void choose() {
		if (this.classes == null || this.classes.isEmpty()) {
			new WarningDialog("Documentation can not be generated because no class selected.");
			return;
		}

		try {
			DocumentationGenerator.getInstance().generateDoc(this.classes.get(this.getSelectedListItem()), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void showInfo() {
		StringBuilder	stringBuilder;
		ClassInfo		classInfo;

		if (this.classes == null || this.classes.isEmpty()) {
			this.getSearchPanel().getTxtrInfo().setText("No classes found.");
			return;
		}

		if (this.getSelectedListItem() < 0) {
			this.getSearchPanel().getTxtrInfo().setText("No class selected.");
			return;
		}

		classInfo		= this.classes.get(this.getSelectedListItem());
		stringBuilder	= new StringBuilder();

		stringBuilder.append(String.format(this.INFO_FORMATTER, "Name", classInfo.getName()));
		stringBuilder.append(String.format(this.INFO_FORMATTER, "FQN", classInfo.getFqn()));
		stringBuilder
				.append(String.format(this.INFO_FORMATTER, "Path", Settings.EPROJECT_PATH.get() + classInfo.getPath()));

		this.getSearchPanel().getTxtrInfo().setText(stringBuilder.toString());
	}

	/**
	 * Opens the selected java class in default editor.
	 */
	public void open() {
		ClassInfo classInfo;
		if (this.classes == null || this.classes.isEmpty() || this.getSelectedListItem() < 0) {
			new WarningDialog("No class selected.");
			return;
		}

		classInfo = this.classes.get(this.getSelectedListItem());

		try {
			Desktop.getDesktop().edit(new File(Settings.EPROJECT_PATH.get() + classInfo.getPath()));
		} catch (IOException e) {
			System.err.println("Failed opening class file.");
			e.printStackTrace();
		}
	}
}
