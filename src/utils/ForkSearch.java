package utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.RepositoryService;

import app.DSDGen;
import app.Settings;
import filter.Filter;
import filter.RepositoryFilter;
import parser.ForkParser;
import ui.UIManager;
import ui.view.WarningDialog;

/**
 * Provides searching for forks of repositories hosted on
 * <a href="https://github.com">GitHub</a> via <a href=
 * "https://github.com/eclipse/egit-github/tree/master/org.eclipse.egit.github.core">GitHub
 * Java API</a>.
 *
 * @author Alex Mikulinski
 * @since 14.07.2019
 */
public class ForkSearch {
	private final String			INFO_FORMATTER	= "%-12s %s\n";

	private GitHubClient			client;
	private List<Repository>	results;

	/**
	 * Creates a new {@link ForkSearch}.
	 *
	 * @param _client
	 *           The used {@link GitHubClient}.
	 */
	public ForkSearch(GitHubClient _client) {
		this.client = _client;
	}

	/**
	 * Searches for forks of a chosen repository will return only active forks (see
	 * {@link RepositoryFilter#active()}.
	 *
	 * @param _repository
	 *           The {@link Repository} containing forks.
	 * @param _owner
	 *           The owner to search for. Case is ignored.
	 * @return A List of {@link Repository}. May be empty if the {@link Repository}
	 *         has no forks.
	 * @throws IOException
	 */
	public List<Repository> search(Repository _repository, String _owner) throws IOException {
		RepositoryService	repositoryService;
		ForkParser			forkParser;
		File					forkInfos;

		repositoryService	= new RepositoryService(this.client);
		forkInfos			= new File(Settings.EPROJECT_FORK_INFO.get());
		forkParser			= new ForkParser();

		/*
		 * Check if the JSON is already there. Check if the JSON contains the same
		 * amount of forks as there realy are. If yes, use the JSON, else update the
		 * JSON first and use it then.
		 */
		if (!forkInfos.exists()) {
			UIManager.getInstance().setStatus("Loading " + _repository.getForks() + " fork infos.");

			this.results = this.getForks(_repository, repositoryService);
			JsonManager.write(this.results, Settings.EPROJECT_FORK_INFO.get());
		} else {
			this.results = forkParser.parse(Settings.EPROJECT_FORK_INFO.get());

			if (this.results.size() != _repository.getForks()) {
				if (!new WarningDialog("Fork list is not up to date. Do you want to update it?").isCanceled()) {
					UIManager.getInstance().setStatus("Updating " + _repository.getForks() + " fork infos.");

					this.results = this.getForks(_repository, repositoryService);
					JsonManager.write(this.results, Settings.EPROJECT_FORK_INFO.get());
				}
			}
		}

		UIManager.getInstance().clearStatus();

		if (DSDGen.FIND_ACTIVE_FORKS) {
			this.results = Filter.filter(this.results, RepositoryFilter.owner(_owner), RepositoryFilter.active());
		} else {
			this.results = Filter.filter(this.results, RepositoryFilter.owner(_owner));
		}

		return this.results;
	}

	/**
	 * Will recursively return forks of a repository and their forks.
	 *
	 * @param _parent
	 *           The parent {@link Repository} of forks.
	 * @param _repositoryService
	 * @return A {@link List} of forks.
	 * @throws IOException
	 */
	private List<Repository> getForks(Repository _parent, RepositoryService _repositoryService) throws IOException {
		RepositoryService	repositoryService;
		List<Repository>	resultList;
		List<Repository>	forks;

		resultList			= new ArrayList<>();
		repositoryService	= _repositoryService;

		forks					= repositoryService.getForks(_parent);

		// Set the parent
		forks.forEach(f -> f.setParent(_parent));

		forks.forEach(repo -> {
			resultList.add(repo);
			if (repo.getForks() > 0) {
				try {
					resultList.addAll(this.getForks(repo, repositoryService));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		return resultList;
	}

	/**
	 * Returns the chosen search result from last search or null if nothing was
	 * found.
	 *
	 * @param _number
	 *           The number of the search result. Starts with 0. If the number is
	 *           higher than the number of results, returns the last result.
	 * @return The chosen search result as {@link Repository} or null if not result
	 *         found.
	 */
	public Repository getResult(int _number) {
		int number;

		if (this.results == null || this.results.isEmpty()) {
			return null;
		}

		number = _number;

		if (number >= this.results.size()) {
			number = this.results.size() - 1;
		}

		return this.results.get(number);
	}

	/**
	 * Converts the last results to formatted {@link String}s.
	 *
	 * @return The passed results as formatted {@link String}s or null if no results
	 *         found.
	 */
	public List<String> getResults() {
		if (this.results == null) {
			throw new IllegalArgumentException("There are no search results to print.");
		}

		if (this.results.isEmpty()) {
			return null;
		}

		return this.getResults(this.results);
	}

	/**
	 * Converts the passed results to formatted {@link String}s.
	 *
	 * @param _results
	 *           The {@link Repository} results.
	 * @return The passed results as formatted {@link String}s.
	 */
	public List<String> getResults(List<Repository> _results) {
		List<String>	stringResults;
		Repository		tempResult;
		String			number;
		String			formatter;
		String			description;
		String			parentDescription;
		int				currentNameLength;
		int				maxNameLength;

		stringResults		= new ArrayList<>();
		parentDescription	= null;

		// Determine the max name length
		maxNameLength		= 0;
		for (Repository result : this.results) {
			currentNameLength = result.getOwner().getLogin().length();
			if (currentNameLength > maxNameLength) {
				maxNameLength = currentNameLength;
			}
		}

		// Set the formatter so that the names for repos make a column
		formatter = "%-3s %-" + maxNameLength + "s %s";

		stringResults.add(String.format(formatter, "Nr.", "Owner", "Description"));

		for (int i = 0; i < this.results.size(); i++) {
			tempResult	= this.results.get(i);
			description	= tempResult.getDescription();

			if (tempResult.getParent() != null) {
				parentDescription = tempResult.getParent().getDescription();
			}

			// Do not allow line breaks in description.
			// Description can be null.
			// Don't bother to show a description if it's the same as the parents one
			if (StringUtils.isNotBlank(description)) {
				if (description.equals(parentDescription)) {
					description = "=";
				} else {
					description = description.replaceAll("\n", " ");
				}
			} else {
				description = "none";
			}

			// Check if the fork is already downloaded
			number = String.valueOf(i);
			if (FileManager.exists(Settings.EPROJECT_FORKS.get() + tempResult.getOwner().getLogin())) {
				number = number + "*";
			}

			stringResults.add(String.format(formatter, number, tempResult.getOwner().getLogin(), description));
		}

		return stringResults;

	}

	/**
	 * Returns a more detailed information for the chosen search result from last
	 * search.
	 *
	 * @param _number
	 *           The number of the search result. Starts with 0. If the number is
	 *           higher than the number of results, shows the last result.
	 * @return Info as {@link String} or null if no info found.
	 * @throws IOException
	 */
	public String getInfo(int _number) throws IOException {
		Repository result;

		result = this.getResult(_number);

		if (result == null) {
			return null;
		}

		return this.getInfo(result);
	}

	/**
	 * Returns a more detailed information for a search result.
	 *
	 * @param _result
	 *           The result as {@link Repository}.
	 * @return Info as {@link String}.
	 * @throws IOException
	 */
	public String getInfo(Repository _result) throws IOException {
		StringBuilder	stringBuilder;
		Repository		result;

		result			= _result;
		stringBuilder	= new StringBuilder();

		stringBuilder.append(String.format(this.INFO_FORMATTER, "URL", result.getHtmlUrl()));
		stringBuilder.append(String.format(this.INFO_FORMATTER, "Owner", result.getOwner().getLogin()));
		stringBuilder.append(String.format(this.INFO_FORMATTER, "Forks", result.getForks()));
		stringBuilder.append(String.format(this.INFO_FORMATTER, "Homepage", result.getHomepage()));
		stringBuilder.append(String.format(this.INFO_FORMATTER, "Watchers", result.getWatchers()));
		stringBuilder.append(String.format(this.INFO_FORMATTER, "Open issues", result.getOpenIssues()));
		stringBuilder
				.append(String.format(this.INFO_FORMATTER, "Created at", DateHelper.toString(result.getCreatedAt())));
		stringBuilder.append(String.format(this.INFO_FORMATTER, "Pushed at", DateHelper.toString(result.getPushedAt())));
		stringBuilder
				.append(String.format(this.INFO_FORMATTER, "Updated at", DateHelper.toString(result.getUpdatedAt())));
		stringBuilder.append(String.format(this.INFO_FORMATTER, "Grade", RepositoryHelper.getGrade(result)));
		stringBuilder.append(String.format(this.INFO_FORMATTER, "Ancestors",
				RepositoryHelper.getURLs(RepositoryHelper.getAncestors(result))));

		return stringBuilder.toString();
	}
}
