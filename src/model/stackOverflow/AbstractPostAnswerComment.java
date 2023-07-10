package model.stackOverflow;

import java.util.Date;

import keys.SOKey;

/**
 * Contains objects for posts, answers and comments from stack overflow.
 * 
 * @author Alex Mikulinski
 * @since Jul 8, 2018
 */
public abstract class AbstractPostAnswerComment extends AbstractPostAnswerCommentUser {
	/** {@link SOKey#BODY_MARKDOWN} */
	private String	bodyMarkdown;
	/** {@link SOKey#CREATION_DATE} */
	private Date	creationDate;
	/** {@link SOKey#OWNER} */
	private User	owner;
	/** {@link SOKey#SCORE} */
	private int		score;
	/** {@link SOKey#BODY} */
	private String	body;

	/**
	 * Returns the {@link #bodyMarkdown} of current
	 * {@link AbstractPostAnswerComment}.
	 *
	 * @return The bodyMarkdown as {@link String}.
	 */
	public String getBodyMarkdown() {
		return this.bodyMarkdown;
	}

	/**
	 * Sets the {@link #bodyMarkdown} of current {@link AbstractPostAnswerComment}.
	 *
	 * @param bodyMarkdown
	 *           The bodyMarkdown to set as {@link String}.
	 */
	public void setBodyMarkdown(String bodyMarkdown) {
		this.bodyMarkdown = bodyMarkdown;
	}

	/**
	 * Returns the {@link #creationDate} of current
	 * {@link AbstractPostAnswerComment}.
	 *
	 * @return The creationDate as {@link Date}.
	 */
	public Date getCreationDate() {
		return this.creationDate;
	}

	/**
	 * Sets the {@link #creationDate} of current {@link AbstractPostAnswerComment}.
	 *
	 * @param creationDate
	 *           The creationDate to set as {@link Date}.
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * Returns the {@link #owner} of current {@link AbstractPostAnswerComment}.
	 *
	 * @return The owner as {@link User}.
	 */
	public User getOwner() {
		return this.owner;
	}

	/**
	 * Sets the {@link #owner} of current {@link AbstractPostAnswerComment}.
	 *
	 * @param owner
	 *           The owner to set as {@link User}.
	 */
	public void setOwner(User owner) {
		this.owner = owner;
	}

	/**
	 * Returns the {@link #score} of current {@link AbstractPostAnswerComment}.
	 *
	 * @return The score as int.
	 */
	public int getScore() {
		return this.score;
	}

	/**
	 * Sets the {@link #score} of current {@link AbstractPostAnswerComment}.
	 *
	 * @param score
	 *           The score to set as int.
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * Returns the {@link #body} of current {@link AbstractPostAnswerComment}.
	 *
	 * @return The body as {@link String}.
	 */
	public String getBody() {
		return this.body;
	}

	/**
	 * Sets the {@link #body} of current {@link AbstractPostAnswerComment}.
	 *
	 * @param body
	 *           The body to set as {@link String}.
	 */
	public void setBody(String body) {
		this.body = body;
	}

}
