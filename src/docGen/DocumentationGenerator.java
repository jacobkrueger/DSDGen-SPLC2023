package docGen;

import java.io.File;
import java.io.IOException;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.egit.github.core.Repository;

import app.Settings;
import classContainer.ClassContainer;
import classContainer.ClassInfo;
import parser.ClassParser;
import ui.view.WarningDialog;
import utils.FileManager;
import utils.GitHubHelper;

/**
 * This class handles the generation of documentation for this project.
 *
 * @author Alex Mikulinski
 * @since Oct 3, 2018
 */
public class DocumentationGenerator {
	/**
	 * Owner of the first repository to examine. If it's a fork, the owner cannot be
	 * the same as the owner of the project.
	 */
	private Repository							repository1;
	/**
	 * Owner of the second repository to examine. If it's a fork, the owner cannot
	 * be the same as the owner of the project.
	 */
	private Repository							repository2;

	private static DocumentationGenerator	instance;

	/**
	 * @return The instance of this {@link GitHubHelper}.
	 */
	public static DocumentationGenerator getInstance() {
		if (DocumentationGenerator.instance == null) {
			DocumentationGenerator.instance = new DocumentationGenerator();
		}
		return DocumentationGenerator.instance;
	}

	private DocumentationGenerator() {
	}

	/**
	 * Generates the documentation and puts it into {@link Settings#PAGE_OUTPUT}.
	 *
	 * @param _classInfo
	 *           The {@link ClassInfo} used.
	 * @param _openPage
	 *           Choose true if the html page should open after generation.
	 * @throws IOException
	 */
	public void generateDoc(ClassInfo _classInfo, boolean _openPage) throws IOException {
		ClassContainer	classContainer1;
		ClassContainer	classContainer2;
		ClassParser		classParser;
		HTMLWriter		htmlWriter;
		ClassInfo		classInfo;

		if (!this.prepareDocGeneration()) {
			return;
		}

		classInfo	= _classInfo;
		classParser	= new ClassParser();

		// Use base + fork1 or fork1 + fork2
		if (this.repository2 == null) {
			classContainer1 = classParser.getClassContainer(classInfo);

			classInfo
					.setProjectPath(Settings.EPROJECT_FORKS.get() + this.repository1.getOwner().getLogin() + File.separator);
			classContainer2	= classParser.getClassContainer(classInfo);

			htmlWriter			= new HTMLWriter(GitHubHelper.getInstance().getRepository(), this.repository1,
					classContainer1, classContainer2);
		} else {
			classInfo
					.setProjectPath(Settings.EPROJECT_FORKS.get() + this.repository1.getOwner().getLogin() + File.separator);
			classContainer1 = classParser.getClassContainer(classInfo);

			classInfo
					.setProjectPath(Settings.EPROJECT_FORKS.get() + this.repository2.getOwner().getLogin() + File.separator);
			classContainer2	= classParser.getClassContainer(classInfo);

			htmlWriter			= new HTMLWriter(this.repository1, this.repository2, classContainer1, classContainer2);
		}

		htmlWriter.setUp();

		htmlWriter.writeHtml();

		if (_openPage) {
			htmlWriter.openPage();
		}
	}

	/**
	 * This method checks whether all data was downloaded and everything was
	 * selected correctly and is prepared for documentation generation.
	 *
	 * @return True if everything was correct. False otherwise.
	 */
	private boolean prepareDocGeneration() {
		String warning;

		warning = null;

		if (this.repository1 == null && this.repository2 == null) {
			warning = "Please select and download one or two forks!";
		} else if (this.repository2 == null) {
			// If no repository2 selected, need to check the base repository
			if (!FileManager.exists(Settings.EPROJECT_REPOSITORY_PATH.get())) {
				warning = "Please download the base repository!";
			}
			if (!FileManager.exists(Settings.EPROJECT_PATH.get() + Settings.COMMIT_FILE_NAME.get())) {
				warning = warning + "Please download the base commits!";
			}
			if (!FileManager.exists(Settings.EPROJECT_PATH.get() + Settings.ISSUES_FILE_NAME.get())) {
				warning = warning + "Please download the base issues!";
			}
			if (!FileManager.exists(Settings.EPROJECT_PATH.get() + Settings.PULL_REQUEST_FILE_NAME.get())) {
				warning = warning + "Please download the base pull requests!";
			}
		}

		// Check whether stack overflow posts were downloaded
		if (!FileManager.exists(Settings.SO_PATH.get())) {
			new WarningDialog("No Stack Overflow data will be used, because not downloaded.");
		}

		if (StringUtils.isNotBlank(warning)) {
			new WarningDialog(warning);
			return false;
		}

		return true;
	}

	/**
	 * Returns the {@link #repository1} of current {@link DocumentationGenerator}.
	 *
	 * @return The {@link #repository1} as {@link Repository}.
	 */
	public Repository getRepository1() {
		return this.repository1;
	}

	/**
	 * Sets the {@link #repository1} of current {@link DocumentationGenerator}.
	 *
	 * @param repository1
	 *           The {@link #repository1} to set as {@link Repository}.
	 */
	public void setRepository1(Repository repository1) {
		this.repository1 = repository1;
	}

	/**
	 * Returns the {@link #repository2} of current {@link DocumentationGenerator}.
	 *
	 * @return The {@link #repository2} as {@link Repository}.
	 */
	public Repository getRepository2() {
		return this.repository2;
	}

	/**
	 * Sets the {@link #repository2} of current {@link DocumentationGenerator}.
	 *
	 * @param repository2
	 *           The {@link #repository2} to set as {@link Repository}.
	 */
	public void setRepository2(Repository repository2) {
		this.repository2 = repository2;
	}
}
