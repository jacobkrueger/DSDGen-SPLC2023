package model.stackOverflow;

import keys.SOKey;

/**
 * Contains objects for posts, answers, comments and users from stack overflow.
 * 
 * @author Alex Mikulinski
 * @since Jul 8, 2018
 */
public abstract class AbstractPostAnswerCommentUser {
	/** {@link SOKey#LINK} */
	private String link;

	/**
	 * Returns the {@link #link} of current {@link AbstractPostAnswerCommentUser}.
	 *
	 * @return The link as {@link String}.
	 */
	public String getLink() {
		return this.link;
	}

	/**
	 * Sets the {@link #link} of current {@link AbstractPostAnswerCommentUser}.
	 *
	 * @param link
	 *           The link to set as {@link String}.
	 */
	public void setLink(String link) {
		this.link = link;
	}

}
