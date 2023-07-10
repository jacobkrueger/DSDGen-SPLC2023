package model.stackOverflow;

import keys.SOKey;

/**
 * Contains objects for posts and original questions from stack overflow.
 * 
 * @author Alex Mikulinski
 * @since Jul 8, 2018
 */
public abstract class AbstractPostOriginalQuestion extends AbstractPostAnswerOriginalQuestion {
	/** {@link SOKey#ACCEPTED_ANSWER_ID} */
	private int	acceptedAnswerId;
	/** {@link SOKey#ANSWER_COUNT} */
	private int	answerCount;

	/**
	 * Returns the {@link #acceptedAnswerId} of current {@link OriginalQuestion}.
	 *
	 * @return The acceptedAnswerId as int.
	 */
	public int getAcceptedAnswerId() {
		return this.acceptedAnswerId;
	}

	/**
	 * Sets the {@link #acceptedAnswerId} of current {@link OriginalQuestion}.
	 *
	 * @param acceptedAnswerId
	 *           The acceptedAnswerId to set as int.
	 */
	public void setAcceptedAnswerId(int acceptedAnswerId) {
		this.acceptedAnswerId = acceptedAnswerId;
	}

	/**
	 * Returns the {@link #answerCount} of current
	 * {@link AbstractPostOriginalQuestion}.
	 *
	 * @return The answerCount as int.
	 */
	public int getAnswerCount() {
		return this.answerCount;
	}

	/**
	 * Sets the {@link #answerCount} of current
	 * {@link AbstractPostOriginalQuestion}.
	 *
	 * @param answerCount
	 *           The answerCount to set as int.
	 */
	public void setAnswerCount(int answerCount) {
		this.answerCount = answerCount;
	}

}
