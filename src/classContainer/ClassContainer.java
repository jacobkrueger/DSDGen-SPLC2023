package classContainer;

import java.util.List;

import org.eclipse.egit.github.core.PullRequest;

import com.github.javaparser.ast.CompilationUnit;

import docGen.SOResult;
import model.Commit;
import model.Inheritance;
import model.Issue;
import model.callGraph.Call;
import model.stackOverflow.Post;

/**
 * Holds all informations retrieved for a single java class.
 *
 * @author Alex Mikulinski
 * @since Jun 17, 2018
 */
public class ClassContainer {
	private ClassInfo				classInfo;
	private List<Issue>			issues;
	private List<PullRequest>	pullRequests;
	private List<Commit>			commits;
	private CompilationUnit		code;
	/** Calls, where a class calls itself. */
	private List<Call>			privateCalls;
	/** Calls, where classes are called from this class. */
	private List<Call>			calees;
	/** Calls with classes, which called this class. */
	private List<Call>			callers;
	/** <b>F</b>ully <b>Q</b>ualified <b>N</b>ames of classes calling this class. */
	private List<String>			examples;
	/**
	 * Contains all {@link Inheritance}s where this class extends/implements another
	 * class.
	 */
	private List<Inheritance>	parents;
	/**
	 * Contains all {@link Inheritance}s where another class extends/implements this
	 * class.
	 */
	private List<Inheritance>	children;
	/**
	 * Contains all {@link Post}s where this class is mentioned with other chosen
	 * key words.
	 */
	private List<Post>			posts;
	private List<SOResult>		stackOverflowResults;

	/**
	 * Returns the {@link #classInfo} of current {@link ClassContainer}.
	 *
	 * @return The {@link #classInfo} as {@link ClassInfo}.
	 */
	public ClassInfo getClassInfo() {
		return this.classInfo;
	}

	/**
	 * Sets the {@link #classInfo} of current {@link ClassContainer}.
	 *
	 * @param classInfo
	 *           The {@link #classInfo} to set as {@link ClassInfo}.
	 */
	public void setClassInfo(ClassInfo classInfo) {
		this.classInfo = classInfo;
	}

	/**
	 * Returns the {@link #issues} of current {@link ClassContainer}.
	 *
	 * @return The {@link #issues} as {@link List}.
	 */
	public List<Issue> getIssues() {
		return this.issues;
	}

	/**
	 * Sets the {@link #issues} of current {@link ClassContainer}.
	 *
	 * @param issues
	 *           The {@link #issues} to set as {@link List}.
	 */
	public void setIssues(List<Issue> issues) {
		this.issues = issues;
	}

	/**
	 * Returns the {@link #pullRequests} of current {@link ClassContainer}.
	 *
	 * @return The {@link #pullRequests} as {@link List}.
	 */
	public List<PullRequest> getPullRequests() {
		return this.pullRequests;
	}

	/**
	 * Sets the {@link #pullRequests} of current {@link ClassContainer}.
	 *
	 * @param pullRequests
	 *           The {@link #pullRequests} to set as {@link List}.
	 */
	public void setPullRequests(List<PullRequest> pullRequests) {
		this.pullRequests = pullRequests;
	}

	/**
	 * Returns the {@link #commits} of current {@link ClassContainer}.
	 *
	 * @return The {@link #commits} as {@link List}.
	 */
	public List<Commit> getCommits() {
		return this.commits;
	}

	/**
	 * Sets the {@link #commits} of current {@link ClassContainer}.
	 *
	 * @param commits
	 *           The {@link #commits} to set as {@link List}.
	 */
	public void setCommits(List<Commit> commits) {
		this.commits = commits;
	}

	/**
	 * Returns the {@link #code} of current {@link ClassContainer}.
	 *
	 * @return The {@link #code} as {@link CompilationUnit}.
	 */
	public CompilationUnit getCode() {
		return this.code;
	}

	/**
	 * Sets the {@link #code} of current {@link ClassContainer}.
	 *
	 * @param code
	 *           The {@link #code} to set as {@link CompilationUnit}.
	 */
	public void setCode(CompilationUnit code) {
		this.code = code;
	}

	/**
	 * Returns the {@link #privateCalls} of current {@link ClassContainer}.
	 *
	 * @return The {@link #privateCalls} as {@link List}.
	 */
	public List<Call> getPrivateCalls() {
		return this.privateCalls;
	}

	/**
	 * Sets the {@link #privateCalls} of current {@link ClassContainer}.
	 *
	 * @param privateCalls
	 *           The {@link #privateCalls} to set as {@link List}.
	 */
	public void setPrivateCalls(List<Call> privateCalls) {
		this.privateCalls = privateCalls;
	}

	/**
	 * Returns the {@link #calees} of current {@link ClassContainer}.
	 *
	 * @return The {@link #calees} as {@link List}.
	 */
	public List<Call> getCalees() {
		return this.calees;
	}

	/**
	 * Sets the {@link #calees} of current {@link ClassContainer}.
	 *
	 * @param calees
	 *           The {@link #calees} to set as {@link List}.
	 */
	public void setCalees(List<Call> calees) {
		this.calees = calees;
	}

	/**
	 * Returns the {@link #callers} of current {@link ClassContainer}.
	 *
	 * @return The {@link #callers} as {@link List}.
	 */
	public List<Call> getCallers() {
		return this.callers;
	}

	/**
	 * Sets the {@link #callers} of current {@link ClassContainer}.
	 *
	 * @param callers
	 *           The {@link #callers} to set as {@link List}.
	 */
	public void setCallers(List<Call> callers) {
		this.callers = callers;
	}

	/**
	 * Returns the {@link #examples} of current {@link ClassContainer}.
	 *
	 * @return The {@link #examples} as {@link List}.
	 */
	public List<String> getExamples() {
		return this.examples;
	}

	/**
	 * Sets the {@link #examples} of current {@link ClassContainer}.
	 *
	 * @param examples
	 *           The {@link #examples} to set as {@link List}.
	 */
	public void setExamples(List<String> examples) {
		this.examples = examples;
	}

	/**
	 * Returns the {@link #parents} of current {@link ClassContainer}.
	 *
	 * @return The {@link #parents} as {@link List}.
	 */
	public List<Inheritance> getParents() {
		return this.parents;
	}

	/**
	 * Sets the {@link #parents} of current {@link ClassContainer}.
	 *
	 * @param parents
	 *           The {@link #parents} to set as {@link List}.
	 */
	public void setParents(List<Inheritance> parents) {
		this.parents = parents;
	}

	/**
	 * Returns the {@link #children} of current {@link ClassContainer}.
	 *
	 * @return The {@link #children} as {@link List}.
	 */
	public List<Inheritance> getChildren() {
		return this.children;
	}

	/**
	 * Sets the {@link #children} of current {@link ClassContainer}.
	 *
	 * @param children
	 *           The {@link #children} to set as {@link List}.
	 */
	public void setChildren(List<Inheritance> children) {
		this.children = children;
	}

	/**
	 * Returns the {@link #posts} of current {@link ClassContainer}.
	 *
	 * @return The {@link #posts} as {@link List}.
	 */
	public List<Post> getPosts() {
		return this.posts;
	}

	/**
	 * Sets the {@link #posts} of current {@link ClassContainer}.
	 *
	 * @param posts
	 *           The {@link #posts} to set as {@link List}.
	 */
	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	/**
	 * Returns the {@link #stackOverflowResults} of current {@link ClassContainer}.
	 *
	 * @return The {@link #stackOverflowResults} as {@link List}.
	 */
	public List<SOResult> getStackOverflowResults() {
		return this.stackOverflowResults;
	}

	/**
	 * Sets the {@link #stackOverflowResults} of current {@link ClassContainer}.
	 *
	 * @param stackOverflowResults
	 *           The {@link #stackOverflowResults} to set as {@link List}.
	 */
	public void setStackOverflowResults(List<SOResult> stackOverflowResults) {
		this.stackOverflowResults = stackOverflowResults;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ClassContainer [");
		if (this.classInfo != null) {
			builder.append("classInfo=");
			builder.append(this.classInfo);
			builder.append(", ");
		}
		if (this.issues != null) {
			builder.append("issues=");
			builder.append(this.issues);
			builder.append(", ");
		}
		if (this.issues != null) {
			builder.append("PullRequests=");
			builder.append(this.pullRequests);
			builder.append(", ");
		}
		if (this.commits != null) {
			builder.append("commits=");
			builder.append(this.commits);
			builder.append(", ");
		}
		if (this.code != null) {
			builder.append("code=");
			builder.append(this.code);
			builder.append(", ");
		}
		if (this.privateCalls != null) {
			builder.append("privateCalls=");
			builder.append(this.privateCalls);
			builder.append(", ");
		}
		if (this.calees != null) {
			builder.append("calees=");
			builder.append(this.calees);
			builder.append(", ");
		}
		if (this.callers != null) {
			builder.append("callers=");
			builder.append(this.callers);
			builder.append(", ");
		}
		if (this.examples != null) {
			builder.append("examples=");
			builder.append(this.examples);
			builder.append(", ");
		}
		if (this.parents != null) {
			builder.append("parents=");
			builder.append(this.parents);
			builder.append(", ");
		}
		if (this.children != null) {
			builder.append("children=");
			builder.append(this.children);
			builder.append(", ");
		}
		if (this.posts != null) {
			builder.append("posts=");
			builder.append(this.posts);
			builder.append(", ");
		}
		if (this.stackOverflowResults != null) {
			builder.append("stackOverflowResults=");
			builder.append(this.stackOverflowResults);
		}
		builder.append("]");
		return builder.toString();
	}
}
