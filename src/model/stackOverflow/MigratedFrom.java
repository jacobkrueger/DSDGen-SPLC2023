package model.stackOverflow;

import java.util.Date;

/**
 * Contains objects for "migrated from" from stack overflow.
 * 
 * @author Alex Mikulinski
 * @since Sep 25, 2018
 */
public class MigratedFrom {
	/**
	 * Using some methods from original question because they are also used in
	 * answers.
	 */
	private OriginalQuestion	originalQuestion;

	private OtherSite				otherSite;
	private Date					onDate;

	/**
	 * Creates a new instance of {@link MigratedFrom}.
	 */
	public MigratedFrom() {
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

	/**
	 * Returns the {@link #otherSite} of current {@link MigratedFrom}.
	 *
	 * @return The {@link #otherSite} as {@link OtherSite}.
	 */
	public OtherSite getOtherSite() {
		return this.otherSite;
	}

	/**
	 * Sets the {@link #otherSite} of current {@link MigratedFrom}.
	 *
	 * @param otherSite
	 *           The {@link #otherSite} to set as {@link OtherSite}.
	 */
	public void setOtherSite(OtherSite otherSite) {
		this.otherSite = otherSite;
	}

	/**
	 * Returns the {@link #onDate} of current {@link MigratedFrom}.
	 *
	 * @return The {@link #onDate} as {@link Date}.
	 */
	public Date getOnDate() {
		return this.onDate;
	}

	/**
	 * Sets the {@link #onDate} of current {@link MigratedFrom}.
	 *
	 * @param onDate
	 *           The {@link #onDate} to set as {@link Date}.
	 */
	public void setOnDate(Date onDate) {
		this.onDate = onDate;
	}
}
