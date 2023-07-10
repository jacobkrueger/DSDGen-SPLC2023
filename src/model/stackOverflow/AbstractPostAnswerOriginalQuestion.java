package model.stackOverflow;

import keys.SOKey;

/**
 * Contains objects for posts, answers and original questions from stack
 * overflow.
 * 
 * @author Alex Mikulinski
 * @since Jul 8, 2018
 */
public abstract class AbstractPostAnswerOriginalQuestion {
	/** {@link SOKey#QUESTION_ID} */
	private int		questionId;
	/** {@link SOKey#TITLE} */
	private String	title;

	/**
	 * Returns the {@link #questionId} of current
	 * {@link AbstractPostAnswerOriginalQuestion}.
	 *
	 * @return The questionId as int.
	 */
	public int getQuestionId() {
		return this.questionId;
	}

	/**
	 * Sets the {@link #questionId} of current
	 * {@link AbstractPostAnswerOriginalQuestion}.
	 *
	 * @param questionId
	 *           The questionId to set as int.
	 */
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	/**
	 * Returns the {@link #title} of current
	 * {@link AbstractPostAnswerOriginalQuestion}.
	 *
	 * @return The title as {@link String}.
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * Sets the {@link #title} of current
	 * {@link AbstractPostAnswerOriginalQuestion}.
	 *
	 * @param title
	 *           The title to set as {@link String}.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

}
