package parser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.egit.github.core.PullRequest;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;

import app.Settings;
import classContainer.ClassContainer;
import classContainer.ClassInfo;
import docGen.SOResult;
import filter.CommitFilter;
import filter.Filter;
import filter.IssueFilter;
import filter.PullRequestFilter;
import model.Commit;
import model.Issue;

/**
 * Provides basic functionality for parsing java classes. <br>
 * Uses JavaParser.
 *
 * @see <a href="https://github.com/javaparser/javaparser">JavaParser</a>
 * @author Alex Mikulinski
 * @since Jun 16, 2018
 */
public class ClassParser {

	/**
	 * Parses a java class and returns the code as {@link String}.
	 *
	 * @param _path
	 *           The path to the java class.
	 * @return Class code as {@link CompilationUnit}.
	 */
	public CompilationUnit getCompilationUnit(String _path) {
		FileInputStream	fileInputStream;
		CompilationUnit	result;
		String				path;

		path					= _path;
		result				= new CompilationUnit();
		fileInputStream	= null;

		try {
			fileInputStream = new FileInputStream(path);
		} catch (FileNotFoundException e) {
			System.err.println("Could not find the class in " + path);
			e.printStackTrace();
		}

		result = JavaParser.parse(fileInputStream);

		return result;
	}

	/**
	 * Parses all available data for a class and puts them into a
	 * {@link ClassContainer}.
	 *
	 * @param _classInfo
	 *           The {@link ClassInfo} for the chosen class. commits, pullrequests
	 *           and issues.
	 * @return The {@link ClassContainer} with all parsed data.
	 * @throws IOException
	 */
	public ClassContainer getClassContainer(ClassInfo _classInfo) throws IOException {
		// List<List<Inheritance>> allInheritances;
		PullRequestParser	pullRequestParser;
		List<PullRequest>	pullRequests;
		// InheritanceParser inheritanceParser;
		// List<List<Call>> allCalls;
		// CallGraphParser callGraphParser;
		CompilationUnit	compilationUnit;
		ClassContainer		classContainer;
		List<SOResult>		stackOverflowResults;
		// List<String> exampleClasses;
		List<Commit>		commits;
		CommitParser		commitParser;
		IssueParser			issueParser;
		List<Issue>			issues;
		ClassInfo			classInfo;
		SOParser				stackOverflowParser;

		classInfo				= new ClassInfo(_classInfo);

		stackOverflowParser	= new SOParser();
		// inheritanceParser = new InheritanceParser();
		// callGraphParser = new CallGraphParser();
		compilationUnit		= new CompilationUnit();
		classContainer			= new ClassContainer();
		// exampleClasses = new ArrayList<>();
		pullRequests			= new ArrayList<>();
		commitParser			= new CommitParser();
		issueParser				= new IssueParser();
		pullRequestParser		= new PullRequestParser();
		commits					= new ArrayList<>();
		issues					= new ArrayList<>();

		commits					= commitParser.parse(classInfo.getProjectPath() + Settings.COMMIT_FILE_NAME.get(),
				classInfo.getProjectPath() + Settings.REPOSITORY_DIR_NAME.get());
		commits					= Filter.filter(commits, CommitFilter.changedFile(classInfo.getUnixPath()));

		issues					= issueParser.parse(classInfo.getProjectPath() + Settings.ISSUES_FILE_NAME.get());
		issues					= Filter.filter(issues, IssueFilter.contains(classInfo.getName()));

		pullRequests			= pullRequestParser
				.parse(classInfo.getProjectPath() + Settings.PULL_REQUEST_FILE_NAME.get());
		pullRequests			= Filter.filter(pullRequests, PullRequestFilter.contains(classInfo.getName()));

		compilationUnit		= this.getCompilationUnit(classInfo.getProjectPath() + classInfo.getPath());

		// allCalls = callGraphParser.parseCallsForClass(classInfo.getFqn());
		// exampleClasses = callGraphParser.getExampleClasses(allCalls.get(2));
		//
		// allInheritances =
		// inheritanceParser.parseInheritanceForClass(classInfo.getFqn());

		stackOverflowResults	= stackOverflowParser.parseResults(classInfo.getName(), Arrays.asList(" bug ", " example "));

		classContainer.setClassInfo(classInfo);
		classContainer.setIssues(issues);
		classContainer.setCommits(commits);
		classContainer.setCode(compilationUnit);
		// classContainer.setExamples(exampleClasses);
		classContainer.setPullRequests(pullRequests);

		// classContainer.setPrivateCalls(allCalls.get(0));
		// classContainer.setCalees(allCalls.get(1));
		// classContainer.setCallers(allCalls.get(2));
		//
		// classContainer.setParents(allInheritances.get(0));
		// classContainer.setChildren(allInheritances.get(1));

		classContainer.setStackOverflowResults(stackOverflowResults);

		return classContainer;
	}
}
