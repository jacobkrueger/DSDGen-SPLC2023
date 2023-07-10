package update;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.egit.github.core.Comment;
import org.eclipse.egit.github.core.PullRequest;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.PullRequestService;

import app.DSDGen;
import app.Settings;
import filter.Filter;
import filter.PullRequestFilter;
import ui.view.WarningDialog;
import utils.JsonManager;
import utils.Progress;

/**
 * Provides functionality for downloading pullRequests from GitHub.
 *
 * @author Alex Mikulinski
 * @since 25.02.2019
 */
public class PullRequestUpdater implements Updater {
	/** Filter for issues to get all (open and closed) pull requests. */
	public static final String	PULL_REQUEST_FILTER	= "all";
	private Repository			repository;
	private GitHubClient			client;

	/**
	 * Creates a new {@link PullRequestUpdater} for a {@link Repository} and a
	 * {@link GitHubClient}.
	 *
	 * @param _repository
	 * @param _client
	 */
	public PullRequestUpdater(Repository _repository, GitHubClient _client) {
		this.repository	= _repository;
		this.client			= _client;
	}

	/**
	 * Downloads the {@link PullRequest} to a JSON file at the passed path using the
	 * {@link Settings#PULL_REQUEST_FILE_NAME}.
	 */
	@Override
	public void update(String _path) throws IOException {
		List<PullRequest> pullRequests;

		if (this.repository == null) {
			new WarningDialog(Updater.noInternetError("pullRequests"));
			return;
		}

		pullRequests = this.getPullRequests();

		if (DSDGen.EVALUATION) {
			pullRequests = Filter.filter(pullRequests, PullRequestFilter.before(DSDGen.DATE_MAX));
		}

		JsonManager.write(pullRequests, _path + Settings.PULL_REQUEST_FILE_NAME.get());
	}

	/**
	 * @return A {@link List} of open and closed {@link PullRequest}s for this
	 *         {@link #repository} with it's {@link Comment}s.
	 * @throws IOException
	 */
	public List<PullRequest> getPullRequests() throws IOException {
		List<org.eclipse.egit.github.core.PullRequest>	gitHubPullRequests;
		PullRequestService										pullRequestService;
		List<PullRequest>											pullRequests;

		pullRequests			= new ArrayList<>();
		gitHubPullRequests	= new ArrayList<>();
		pullRequestService	= new PullRequestService(this.client);

		gitHubPullRequests	= pullRequestService.getPullRequests(this.repository, "all");

		try (Progress progress = new Progress(1, gitHubPullRequests.size(), "Loading pullRequests: ")) {
			for (org.eclipse.egit.github.core.PullRequest gitHubPullRequest : gitHubPullRequests) {
				progress.show().step();
				pullRequests.add(gitHubPullRequest);
			}
		}

		return pullRequests;
	}
}
