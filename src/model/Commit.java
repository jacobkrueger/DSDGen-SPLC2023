package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.revwalk.RevCommit;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Provides a model for a git commit wrapping {@link RevCommit}.
 *
 * @author Alex Mikulinski
 * @since 03.03.2019
 */
public class Commit {
	private String										name;
	private String										shortMessage;
	private String										fullMessage;
	private int											commitTime;
	private PersonIdent								authorIdent;
	private PersonIdent								committerIdent;
	private String										link;

	/**
	 * A Map of parent {@link RevCommit RevCommits} and their {@link DiffEntry
	 * DiffEntries} to current commit.
	 */
	private Map<RevCommit, List<DiffEntry>>	parentDiffs;

	/**
	 * <b>Only for JSON parser</b>
	 */
	@JsonCreator
	private Commit() {
	}

	/**
	 * Creates a new {@link Commit}.
	 *
	 * @param _revCommit
	 *           The coresponding {@link RevCommit}.
	 */
	public Commit(RevCommit _revCommit) {
		this._initialize(_revCommit);
	}

	/**
	 * Creates a new {@link Commit}.
	 *
	 * @param _revCommit
	 *           The coresponding {@link RevCommit}.
	 * @param _parentDiffs
	 *           The {@link #parentDiffs} for this {@link Commit}.
	 */
	public Commit(RevCommit _revCommit, Map<RevCommit, List<DiffEntry>> _parentDiffs) {
		this.parentDiffs = _parentDiffs;
		this._initialize(_revCommit);
	}

	/**
	 * Adds a parent {@link RevCommit} and it's {@link DiffEntry DiffEntries} to
	 * current commit to {@link #parentDiffs}.
	 *
	 * @param _parentCommit
	 *           The parent {@link RevCommit}.
	 * @param _diffs
	 *           A {@link List} of {@link DiffEntry DiffEntries}.
	 */
	public void addParentDiff(RevCommit _parentCommit, List<DiffEntry> _diffs) {
		if (this.parentDiffs == null) {
			this.parentDiffs = new HashMap<>();
		}

		this.parentDiffs.put(_parentCommit, _diffs);
	}

	/**
	 * @return A {@link List} of paths to files which where changed in this commit.
	 */
	@JsonIgnore
	public List<String> getChangedFiles() {
		List<String>	filePaths;
		String			tempPath;

		filePaths = new ArrayList<>();

		if (this.parentDiffs == null) {
			return filePaths;
		}

		for (RevCommit parent : this.parentDiffs.keySet()) {
			for (DiffEntry diff : this.parentDiffs.get(parent)) {
				if (diff.getOldPath().equals(diff.getNewPath())) {
					tempPath = diff.getNewPath();
				} else {
					// It's always better to return the new path since
					if (!diff.getNewPath().equals(DiffEntry.DEV_NULL)) {
						tempPath = diff.getNewPath();
					} else {
						tempPath = diff.getOldPath();
					}
				}

				filePaths.add(tempPath);
			}
		}

		return filePaths;
	}

	private void _initialize(RevCommit _revCommit) {
		this.name				= _revCommit.getName();
		this.commitTime		= _revCommit.getCommitTime();
		this.authorIdent		= _revCommit.getAuthorIdent();
		this.fullMessage		= _revCommit.getFullMessage();
		this.shortMessage		= _revCommit.getShortMessage();
		this.committerIdent	= _revCommit.getCommitterIdent();
	}

	/**
	 * Returns the {@link #parentDiffs} of current {@link Commit}.
	 *
	 * @return The {@link #parentDiffs} as {@link Map}.
	 */
	public Map<RevCommit, List<DiffEntry>> getParentDiffs() {
		return this.parentDiffs;
	}

	/**
	 * Sets the {@link #parentDiffs} of current {@link Commit}.
	 *
	 * @param parentDiffs
	 *           The {@link #parentDiffs} to set as {@link Map}.
	 */
	public void setParentDiffs(Map<RevCommit, List<DiffEntry>> parentDiffs) {
		this.parentDiffs = parentDiffs;
	}

	/**
	 * Returns the {@link #commitTime} of current {@link Commit}.
	 *
	 * @return The {@link #commitTime} as int.
	 */
	public int getCommitTime() {
		return this.commitTime;
	}

	/**
	 * Sets the {@link #commitTime} of current {@link Commit}.
	 *
	 * @param commitTime
	 *           The {@link #commitTime} to set as int.
	 */
	public void setCommitTime(int commitTime) {
		this.commitTime = commitTime;
	}

	/**
	 * Returns the {@link #authorIdent} of current {@link Commit}.
	 *
	 * @return The {@link #authorIdent} as {@link PersonIdent}.
	 */
	public PersonIdent getAuthorIdent() {
		return this.authorIdent;
	}

	/**
	 * Sets the {@link #authorIdent} of current {@link Commit}.
	 *
	 * @param authorIdent
	 *           The {@link #authorIdent} to set as {@link PersonIdent}.
	 */
	public void setAuthorIdent(PersonIdent authorIdent) {
		this.authorIdent = authorIdent;
	}

	/**
	 * Returns the {@link #shortMessage} of current {@link Commit}.
	 *
	 * @return The {@link #shortMessage} as {@link String}.
	 */
	public String getShortMessage() {
		return this.shortMessage;
	}

	/**
	 * Sets the {@link #shortMessage} of current {@link Commit}.
	 *
	 * @param shortMessage
	 *           The {@link #shortMessage} to set as {@link String}.
	 */
	public void setShortMessage(String shortMessage) {
		this.shortMessage = shortMessage;
	}

	/**
	 * Returns the {@link #name} of current {@link Commit}.
	 *
	 * @return The {@link #name} as {@link String}.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the {@link #name} of current {@link Commit}.
	 *
	 * @param name
	 *           The {@link #name} to set as {@link String}.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the {@link #fullMessage} of current {@link Commit}.
	 *
	 * @return The {@link #fullMessage} as {@link String}.
	 */
	public String getFullMessage() {
		return this.fullMessage;
	}

	/**
	 * Sets the {@link #fullMessage} of current {@link Commit}.
	 *
	 * @param fullMessage
	 *           The {@link #fullMessage} to set as {@link String}.
	 */
	public void setFullMessage(String fullMessage) {
		this.fullMessage = fullMessage;
	}

	/**
	 * Returns the {@link #committerIdent} of current {@link Commit}.
	 *
	 * @return The {@link #committerIdent} as {@link PersonIdent}.
	 */
	public PersonIdent getCommitterIdent() {
		return this.committerIdent;
	}

	/**
	 * Sets the {@link #committerIdent} of current {@link Commit}.
	 *
	 * @param committerIdent
	 *           The {@link #committerIdent} to set as {@link PersonIdent}.
	 */
	public void setCommitterIdent(PersonIdent committerIdent) {
		this.committerIdent = committerIdent;
	}

	/**
	 * Returns the {@link #link} of current {@link Commit}.
	 *
	 * @return The {@link #link} as {@link String}.
	 */
	public String getLink() {
		return this.link;
	}

	/**
	 * Sets the {@link #link} of current {@link Commit}.
	 *
	 * @param link
	 *           The {@link #link} to set as {@link String}.
	 */
	public void setLink(String link) {
		this.link = link;
	}

}
