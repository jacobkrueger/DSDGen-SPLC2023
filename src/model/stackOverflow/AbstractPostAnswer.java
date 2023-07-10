package model.stackOverflow;

import java.util.Date;
import java.util.List;

import keys.SOKey;

/**
 * Contains objects for posts and answers from stack overflow.
 * 
 * @author Alex Mikulinski
 * @since Jul 8, 2018
 */
public abstract class AbstractPostAnswer extends AbstractPostAnswerComment {
	/** {@link SOKey#COMMUNITY_OWNED_DATE} */
	private Date				communityOwnedDate;
	/** {@link SOKey#LAST_ACTIVITY_DATE} */
	private Date				lastActivityDate;
	/** {@link SOKey#DOWN_VOTE_COUNT} */
	private int					downVoteCount;
	/** {@link SOKey#UP_VOTE_COUNT} */
	private int					upVoteCount;
	/** {@link SOKey#COMMENT_COUNT} */
	private int					commentCount;
	/** {@link SOKey#LAST_EDIT_DATE} */
	private Date				lastEditDate;
	/** {@link SOKey#LAST_EDITOR} */
	private User				lastEditor;
	/** {@link SOKey#SHARE_LINK} */
	private String				shareLink;
	private List<Comment>	comments;
	/** {@link SOKey#TAGS} */
	private List<String>		tags;

	/**
	 * Returns the {@link #communityOwnedDate} of current
	 * {@link AbstractPostAnswer}.
	 *
	 * @return The {@link #communityOwnedDate} as {@link Date}.
	 */
	public Date getCommunityOwnedDate() {
		return this.communityOwnedDate;
	}

	/**
	 * Sets the {@link #communityOwnedDate} of current {@link AbstractPostAnswer}.
	 *
	 * @param communityOwnedDate
	 *           The {@link #communityOwnedDate} to set as {@link Date}.
	 */
	public void setCommunityOwnedDate(Date communityOwnedDate) {
		this.communityOwnedDate = communityOwnedDate;
	}

	/**
	 * Returns the {@link #lastActivityDate} of current {@link AbstractPostAnswer}.
	 *
	 * @return The lastActivityDate as {@link Date}.
	 */
	public Date getLastActivityDate() {
		return this.lastActivityDate;
	}

	/**
	 * Sets the {@link #lastActivityDate} of current {@link AbstractPostAnswer}.
	 *
	 * @param lastActivityDate
	 *           The lastActivityDate to set as {@link Date}.
	 */
	public void setLastActivityDate(Date lastActivityDate) {
		this.lastActivityDate = lastActivityDate;
	}

	/**
	 * Returns the {@link #downVoteCount} of current {@link AbstractPostAnswer}.
	 *
	 * @return The downVoteCount as int.
	 */
	public int getDownVoteCount() {
		return this.downVoteCount;
	}

	/**
	 * Sets the {@link #downVoteCount} of current {@link AbstractPostAnswer}.
	 *
	 * @param downVoteCount
	 *           The downVoteCount to set as int.
	 */
	public void setDownVoteCount(int downVoteCount) {
		this.downVoteCount = downVoteCount;
	}

	/**
	 * Returns the {@link #upVoteCount} of current {@link AbstractPostAnswer}.
	 *
	 * @return The upVoteCount as int.
	 */
	public int getUpVoteCount() {
		return this.upVoteCount;
	}

	/**
	 * Sets the {@link #upVoteCount} of current {@link AbstractPostAnswer}.
	 *
	 * @param upVoteCount
	 *           The upVoteCount to set as int.
	 */
	public void setUpVoteCount(int upVoteCount) {
		this.upVoteCount = upVoteCount;
	}

	/**
	 * Returns the {@link #commentCount} of current {@link AbstractPostAnswer}.
	 *
	 * @return The commentCount as int.
	 */
	public int getCommentCount() {
		return this.commentCount;
	}

	/**
	 * Sets the {@link #commentCount} of current {@link AbstractPostAnswer}.
	 *
	 * @param commentCount
	 *           The commentCount to set as int.
	 */
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}

	/**
	 * Returns the {@link #lastEditDate} of current {@link AbstractPostAnswer}.
	 *
	 * @return The lastEditDate as {@link Date}.
	 */
	public Date getLastEditDate() {
		return this.lastEditDate;
	}

	/**
	 * Sets the {@link #lastEditDate} of current {@link AbstractPostAnswer}.
	 *
	 * @param lastEditDate
	 *           The lastEditDate to set as {@link Date}.
	 */
	public void setLastEditDate(Date lastEditDate) {
		this.lastEditDate = lastEditDate;
	}

	/**
	 * Returns the {@link #lastEditor} of current {@link AbstractPostAnswer}.
	 *
	 * @return The lastEditor as {@link User}.
	 */
	public User getLastEditor() {
		return this.lastEditor;
	}

	/**
	 * Sets the {@link #lastEditor} of current {@link AbstractPostAnswer}.
	 *
	 * @param lastEditor
	 *           The lastEditor to set as {@link User}.
	 */
	public void setLastEditor(User lastEditor) {
		this.lastEditor = lastEditor;
	}

	/**
	 * Returns the {@link #shareLink} of current {@link AbstractPostAnswer}.
	 *
	 * @return The shareLink as {@link String}.
	 */
	public String getShareLink() {
		return this.shareLink;
	}

	/**
	 * Sets the {@link #shareLink} of current {@link AbstractPostAnswer}.
	 *
	 * @param shareLink
	 *           The shareLink to set as {@link String}.
	 */
	public void setShareLink(String shareLink) {
		this.shareLink = shareLink;
	}

	/**
	 * Returns the {@link #comments} of current {@link AbstractPostAnswer}.
	 *
	 * @return The comments as {@link List}.
	 */
	public List<Comment> getComments() {
		return this.comments;
	}

	/**
	 * Sets the {@link #comments} of current {@link AbstractPostAnswer}.
	 *
	 * @param comments
	 *           The comments to set as {@link List}.
	 */
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	/**
	 * Returns the {@link #tags} of current {@link AbstractPostAnswer}.
	 *
	 * @return The tags as {@link List}.
	 */
	public List<String> getTags() {
		return this.tags;
	}

	/**
	 * Sets the {@link #tags} of current {@link AbstractPostAnswer}.
	 *
	 * @param tags
	 *           The tags to set as {@link List}.
	 */
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
}
