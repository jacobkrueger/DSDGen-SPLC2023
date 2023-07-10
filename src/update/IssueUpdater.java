package update;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.egit.github.core.Comment;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.IssueService;

import app.DSDGen;
import app.Settings;
import filter.Filter;
import filter.IssueFilter;
import model.Issue;
import ui.view.WarningDialog;
import utils.JsonManager;
import utils.Progress;

/**
 * Provides functionality for downloading issues from GitHub.
 *
 * @author Alex Mikulinski
 * @since 25.02.2019
 */
public class IssueUpdater implements Updater {
	/** Filter for issues to get all (open and closed) issues. */
	public static final Map<String, String>	ISSUE_FILTER	= Map.of(IssueService.FILTER_STATE, "all");
	private Repository								repository;
	private GitHubClient								client;

	/**
	 * Creates a new {@link IssueUpdater} for a {@link Repository} and a
	 * {@link GitHubClient}.
	 *
	 * @param _repository
	 * @param _client
	 */
	public IssueUpdater(Repository _repository, GitHubClient _client) {
		this.repository	= _repository;
		this.client			= _client;
	}

	/**
	 * Downloads the {@link Issue} to a JSON file at the passed path using the
	 * {@link Settings#ISSUES_FILE_NAME}.
	 */
	@Override
	public void update(String _path) throws IOException {
		List<Issue> issues;

		if (this.repository == null) {
			new WarningDialog(Updater.noInternetError("issues"));
			return;
		}

		issues = this.getIssues();

		if (DSDGen.EVALUATION) {
			issues = Filter.filter(issues, IssueFilter.before(DSDGen.DATE_MAX));
		}

		JsonManager.write(issues, _path + Settings.ISSUES_FILE_NAME.get());
	}

	/**
	 * @return A {@link List} of open and closed {@link Issue}s for this
	 *         {@link #repository} with it's {@link Comment}s.
	 * @throws IOException
	 */
	public List<Issue> getIssues() throws IOException {
		List<org.eclipse.egit.github.core.Issue>	gitHubIssues;
		IssueService										issueService;
		List<Issue>											issues;
		Issue													tempIssue;

		issues			= new ArrayList<>();
		gitHubIssues	= new ArrayList<>();
		issueService	= new IssueService(this.client);

		gitHubIssues	= issueService.getIssues(this.repository, ISSUE_FILTER);

		try (Progress progress = new Progress(1, gitHubIssues.size(), "Loading issues: ")) {
			for (org.eclipse.egit.github.core.Issue gitHubIssue : gitHubIssues) {
				progress.show().step();

				if (gitHubIssue.getPullRequest().getHtmlUrl() != null) {
					continue;
				}

				// tempIssue = new Issue(gitHubIssue,
				// this.getComments(gitHubIssue.getNumber()));
				tempIssue = new Issue(gitHubIssue, new ArrayList<Comment>());
				issues.add(tempIssue);
			}
		}

		return issues;
	}

	/**
	 * Retrieves all {@link Comment}s for an {@link Issue}.
	 *
	 * @param _number
	 *           The chosen {@link Issue} number.
	 * @return A {@link List} of {@link Comment}s.
	 * @throws IOException
	 */
	public List<Comment> getComments(int _number) throws IOException {
		return new IssueService(this.client).getComments(this.repository, _number);
	}
}
