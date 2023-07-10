package model.stackOverflow;

import java.util.List;

import keys.SOKey;

/**
 * Contains objects for answers from stack overflow.
 * 
 * @author Alex Mikulinski
 * @since Jul 9, 2018
 */
public class Answer extends AbstractPostAnswer {
	/**
	 * Using some methods from original question because they are also used in
	 * answers.
	 */
	private OriginalQuestion	originalQuestion;

	/** {@link SOKey#AWARDED_BOUNTY_AMOUNT} */
	private int						awardedBountyAmount;
	/** {@link SOKey#AWARDED_BOUNTY_USERS} */
	private List<User>			awardedBountyUsers;
	/** {@link SOKey#IS_ACCEPTED} */
	private boolean				isAccepted;
	/** {@link SOKey#ANSWER_ID} */
	private int						answerId;

	/**
	 * Creates a new instance of {@link Answer}.
	 */
	public Answer() {
		this.originalQuestion = new OriginalQuestion();

	}

	/** {@link OriginalQuestion#getQuestionId()} */
	@SuppressWarnings("javadoc")
	public int getQuestionId() {
		return this.originalQuestion.getQuestionId();
	}

	/** {@link OriginalQuestion#setQuestionId(int)} */
	@SuppressWarnings("javadoc")
	public void setQuestionId(int questionId) {
		this.originalQuestion.setQuestionId(questionId);
	}

	/** {@link OriginalQuestion#getTitle()} */
	@SuppressWarnings("javadoc")
	public String getTitle() {
		return this.originalQuestion.getTitle();
	}

	/** {@link OriginalQuestion#setTitle(String)} */
	@SuppressWarnings("javadoc")
	public void setTitle(String title) {
		this.originalQuestion.setTitle(title);
	}

	/**
	 * Returns the {@link #awardedBountyAmount} of current {@link Answer}.
	 *
	 * @return The awardedBountyAmount as int.
	 */
	public int getAwardedBountyAmount() {
		return this.awardedBountyAmount;
	}

	/**
	 * Sets the {@link #awardedBountyAmount} of current {@link Answer}.
	 *
	 * @param awardedBountyAmount
	 *           The awardedBountyAmount to set as int.
	 */
	public void setAwardedBountyAmount(int awardedBountyAmount) {
		this.awardedBountyAmount = awardedBountyAmount;
	}

	/**
	 * Returns the {@link #awardedBountyUsers} of current {@link Answer}.
	 *
	 * @return The awardedBountyUsers as {@link List}.
	 */
	public List<User> getAwardedBountyUsers() {
		return this.awardedBountyUsers;
	}

	/**
	 * Sets the {@link #awardedBountyUsers} of current {@link Answer}.
	 *
	 * @param awardedBountyUsers
	 *           The awardedBountyUsers to set as {@link List}.
	 */
	public void setAwardedBountyUsers(List<User> awardedBountyUsers) {
		this.awardedBountyUsers = awardedBountyUsers;
	}

	/**
	 * Returns the {@link #isAccepted} of current {@link Answer}.
	 *
	 * @return The isAccepted as boolean.
	 */
	public boolean isAccepted() {
		return this.isAccepted;
	}

	/**
	 * Sets the {@link #isAccepted} of current {@link Answer}.
	 *
	 * @param isAccepted
	 *           The isAccepted to set as boolean.
	 */
	public void setAccepted(boolean isAccepted) {
		this.isAccepted = isAccepted;
	}

	/**
	 * Returns the {@link #answerId} of current {@link Answer}.
	 *
	 * @return The answerId as int.
	 */
	public int getAnswerId() {
		return this.answerId;
	}

	/**
	 * Sets the {@link #answerId} of current {@link Answer}.
	 *
	 * @param answerId
	 *           The answerId to set as int.
	 */
	public void setAnswerId(int answerId) {
		this.answerId = answerId;
	}
}
