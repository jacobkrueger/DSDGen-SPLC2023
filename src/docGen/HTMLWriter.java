package docGen;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.egit.github.core.PullRequest;
import org.eclipse.egit.github.core.Repository;
import app.Settings;
import classContainer.ClassContainer;
import model.Commit;
import model.Issue;
import utils.DateHelper;
import utils.DiffCreator;
import utils.FileExtension;
import utils.MapHelper;
import utils.RepositoryHelper;

/**
 * Provides methods for displaying the data sets in html format. Uses a template
 * to write the representation to a HTML file.
 *
 * @author Alex Mikulinski
 * @since 18.07.2019
 */
public class HTMLWriter {
	private HashMap<String, String>	tags;
	private ClassContainer				classContainer1;
	private ClassContainer				classContainer2;
	private Repository					repository1;
	private Repository					repository2;
	private Path							templatePath;
	private Path							outputPath;

	/**
	 * Constructs a new {@link HTMLWriter} from two {@link ClassContainer}s and
	 * their {@link Repository Repositories}.
	 *
	 * @param _repository1
	 * @param _repository2
	 * @param _classContainer1
	 * @param _classContainer2
	 */
	public HTMLWriter(Repository _repository1, Repository _repository2, ClassContainer _classContainer1,
			ClassContainer _classContainer2) {
		this.classContainer1	= _classContainer1;
		this.classContainer2	= _classContainer2;
		this.repository1		= _repository1;
		this.repository2		= _repository2;
		this.templatePath		= Paths.get(Settings.RESOURCES_PATH + Settings.PAGE_TEMPLATE.get());
		this.outputPath		= Paths
				.get(Settings.PAGE_OUTPUT.get() + this.classContainer1.getClassInfo().getName() + FileExtension.HTML);
		this.tags				= new HashMap<>();
	}

	/**
	 * Will create HTML representations for both {@link ClassContainer}s in this
	 * {@link HTMLWriter}.
	 */
	public void setUp() {
		File diffFile;

		this._setUp(this.repository1, this.classContainer1, true);
		this._setUp(this.repository2, this.classContainer2, false);

		diffFile = new File(
				Settings.PAGE_OUTPUT.get() + this.classContainer1.getClassInfo().getName() + "_Diff" + FileExtension.HTML);

		DiffCreator.createDiff(
				this.classContainer1.getClassInfo().getProjectPath() + this.classContainer1.getClassInfo().getPath(),
				this.classContainer2.getClassInfo().getProjectPath() + this.classContainer2.getClassInfo().getPath(),
				diffFile.getAbsolutePath());

		this.tags.put("diff", this._getDiffTable(diffFile));
		this.tags.put("tabTitle", this.classContainer1.getClassInfo().getName() + "-Documentation");
		this.tags.put("stackOverflow", this._generateStackOverflow(this.classContainer1.getStackOverflowResults()));
		this.tags.put("className", this.classContainer1.getClassInfo().getName());
	}

	private void _setUp(Repository _repository, ClassContainer _classContainer, boolean _left) {
		ClassContainer	classContainer;
		Repository		repository;
		String			side;

		classContainer	= _classContainer;
		repository		= _repository;

		if (_left) {
			side = "left_";
		} else {
			side = "right_";
		}

		// Project Information
		this.tags.put(side + "projectType", this._getProjectType(repository));
		this.tags.put(side + "user_project",
				"<a href=\"https://github.com/" + repository.getOwner().getLogin() + "/" + repository.getName()
						+ "\"target=\"_blank\">" + repository.getOwner().getLogin() + "/" + repository.getName() + "</a>");

		this.tags.put(side + "description", String.valueOf(repository.getDescription()));
		this.tags.put(side + "watchers", String.valueOf(repository.getWatchers()));
		this.tags.put(side + "createdAt", DateHelper.toString(repository.getCreatedAt()));
		this.tags.put(side + "pushedAt", DateHelper.toString(repository.getPushedAt()));
		this.tags.put(side + "updatedAt", DateHelper.toString(repository.getUpdatedAt()));

		// Class Information
		this.tags.put(side + "fqn", classContainer.getClassInfo().getFqn());
		this.tags.put(side + "path", classContainer.getClassInfo().getUnixPath());
		this.tags.put(side + "classDescription", this._generateDescription(classContainer));
		this.tags.put(side + "developers", this._generateMVPs(classContainer.getCommits()));
		this.tags.put(side + "code", classContainer.getCode().toString());

		this.tags.put(side + "commits", this._generateCommits(classContainer.getCommits()));
		this.tags.put(side + "issues", this._generateIssues(classContainer.getIssues()));
		this.tags.put(side + "pullRequests", this._generatePullRequests(classContainer.getPullRequests()));
	}

	/**
	 * Removes everything out of the diff file to return only the table.
	 *
	 * @param _diffFile
	 * @return The table containing the diff.
	 */
	private String _getDiffTable(File _diffFile) {
		StringBuilder	stringBuilder;
		String			fileString;
		String			tableStart;
		String			identical;
		String			htmlStart;
		String			tableEnd;
		String			htmlEnd;

		fileString	= "";
		tableStart	= "<tr height=\"16\">";
		htmlStart	= "<html>";
		tableEnd		= "</table>";
		htmlEnd		= "</html>";
		identical	= "are identical</pre></td>";

		try {
			fileString = Files.readString(_diffFile.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (fileString.contains(identical)) {
			return "No difference between files.";
		}

		stringBuilder = new StringBuilder(fileString);

		// Replacement should always start at the end of the file to match string length
		// changes
		stringBuilder.replace(fileString.indexOf(tableEnd), fileString.indexOf(htmlEnd) + htmlEnd.length(), "");
		stringBuilder.replace(fileString.indexOf(htmlStart), fileString.indexOf(tableStart), "");

		return stringBuilder.toString();
	}

	private String _getProjectType(Repository _repository) {
		int grade;

		grade = RepositoryHelper.getGrade(_repository);

		if (grade == 0) {
			return "Base";
		}

		return "Fork Grade " + grade;
	}

	private String _generateMVPs(List<Commit> _commits) {
		Map<String, Integer>	results;
		String					result;
		String					name;

		result	= "";
		results	= new HashMap<>();

		for (Commit commit : _commits) {
			name = "<a href=\"mailto:" + commit.getCommitterIdent().getEmailAddress() + "\">@"
					+ commit.getCommitterIdent().getName() + "</a>";

			if (!results.containsKey(name)) {
				results.put(name, 1);
			} else {
				results.put(name, results.get(name).intValue() + 1);
			}
		}

		results = MapHelper.sortByValue(results);

		for (String key : results.keySet()) {
			result += key + " - " + results.get(key) + " Commits, ";
		}

		return result.replaceAll(", $", ".");
	}

	private String _generateCommits(List<Commit> _commits) {
		StringBuilder	stringBuilder;
		List<Commit>	commits;
		String			message;

		commits			= _commits;
		stringBuilder	= new StringBuilder();

		commits.sort((a, b) -> Integer.compare(b.getCommitTime(), a.getCommitTime()));

		for (Commit commit : commits) {
			message	= commit.getFullMessage();
			// https://stackoverflow.com/questions/11974251/java-string-insert-a-dash-after-every-8-characters-starting-from-the-right
			// If the message is to long line breaks are inserted to keep the window width
			message	= message.replaceAll("(.{75})(?!$)", "$1<br/>");

			stringBuilder.append("<tr>\n");
			stringBuilder.append("<td>" + DateHelper.toString(commit.getCommitTime()) + "</td>\n");
			stringBuilder.append("<td><a href=\"" + commit.getLink() + "\"target=\"_blank\">" + message + "</a></td>\n");
			stringBuilder.append("<td>" + "<a href=\"mailto:" + commit.getCommitterIdent().getEmailAddress() + "\">@"
					+ commit.getCommitterIdent().getName() + "</a>" + "</td>\n");
			stringBuilder.append("</tr>\n");
		}

		return stringBuilder.toString();
	}

	private String _generateDescription(ClassContainer _classContainer) {
		ClassContainer	classContainer;
		String			result;

		classContainer = _classContainer;

		if (classContainer.getCode().getComments().isEmpty()) {
			return "";
		}

		result	= classContainer.getCode().getComments().get(0).getContent();
		result	= result.replaceAll("\\*", "");
		result	= result.replaceAll("  ", "");
		result	= result.replaceAll("@", "<br/>@");

		return result;
	}

	private String _generateIssues(List<Issue> _issues) {
		StringBuilder	stringBuilder;
		List<Issue>		issues;

		issues			= _issues;
		stringBuilder	= new StringBuilder();

		issues.sort((b, a) -> a.getState().compareTo(b.getState()));

		for (Issue issue : issues) {
			stringBuilder.append("<tr>\n");
			stringBuilder.append("<td>" + issue.getState() + "</td>\n");
			stringBuilder.append("<td>");

			issue.getLabels().forEach(l -> {
				stringBuilder.append("<mark style=\"color: black; background-color: #");
				stringBuilder.append(l.getColor());
				stringBuilder.append(";\">");
				stringBuilder.append(l.getName());
				stringBuilder.append("</mark> ");
			});

			stringBuilder.append("</td>\n");
			stringBuilder.append(
					"<td><a href=\"" + issue.getHtmlUrl() + "\"target=\"_blank\">" + issue.getTitle() + "</a></td>\n");
			stringBuilder.append("</tr>\n");
		}

		return stringBuilder.toString();
	}

	private String _generatePullRequests(List<PullRequest> _pullRequests) {
		StringBuilder		stringBuilder;
		List<PullRequest>	pullRequests;

		pullRequests	= _pullRequests;
		stringBuilder	= new StringBuilder();

		pullRequests.sort((b, a) -> a.getState().compareTo(b.getState()));

		for (PullRequest pullRequest : pullRequests) {
			stringBuilder.append("<tr>\n");
			stringBuilder.append("<td>" + pullRequest.getState() + "</td>\n");
			stringBuilder.append("<td><a href=\"" + pullRequest.getHtmlUrl() + "\"target=\"_blank\">"
					+ pullRequest.getTitle() + "</a></td>\n");
			stringBuilder.append("</tr>\n");
		}

		return stringBuilder.toString();
	}

	private String _generateStackOverflow(List<SOResult> _results) {
		List<SOResult>	results;
		StringBuilder	stringBuilder;
		DateFormat		dateFormat;

		results			= _results;
		stringBuilder	= new StringBuilder();
		dateFormat		= DateFormat.getDateInstance(DateFormat.SHORT, Locale.GERMANY);

		results.sort((a, b) -> Integer.compare(b.getScore(), a.getScore()));

		for (SOResult result : results) {
			stringBuilder.append("<tr>\n");
			stringBuilder.append("<td>" + result.getContent() + "</td>\n");
			stringBuilder.append("<td>" + dateFormat.format(result.getDate()) + "</td>\n");
			stringBuilder.append(
					"<td><a href=\"" + result.getLink() + "\"target=\"_blank\">" + result.getTitle() + "</a></td>\n");
			stringBuilder.append("<td>" + result.getScore() + "</td>\n");
			stringBuilder.append("</tr>\n");
		}

		return stringBuilder.toString();
	}

	/**
	 * Writes the created HTML to {@link #templatePath}.
	 */
	public void writeHtml() {
		Stream<String> linesStream;

		linesStream = null;

		try {
			linesStream = Files.lines(this.templatePath);

			Files.write(this.outputPath, this._replaceKeys(linesStream));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			linesStream.close();
		}
	}

	private List<String> _replaceKeys(Stream<String> _linesStream) {
		List<String> linesList;

		linesList = _linesStream.collect(Collectors.toList());

		for (String key : this.tags.keySet()) {
			linesList
					.replaceAll(line -> line.replaceAll("\\$" + key + "\\$", Matcher.quoteReplacement(this.tags.get(key))));
		}

		return linesList;
	}

	/**
	 * Will open the created HTML file using default program.
	 */
	public void openPage() {
		try {
			Desktop.getDesktop().browse(this.outputPath.toUri());
		} catch (IOException _e) {
			_e.printStackTrace();
		}
	}
}
