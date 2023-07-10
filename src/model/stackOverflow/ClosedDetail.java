package model.stackOverflow;

import java.util.List;

import keys.SOKey;

/**
 * Contains objects for closed details from stack overflow.
 * 
 * @author Alex Mikulinski
 * @since Jul 9, 2018
 */
public class ClosedDetail {
	private List<OriginalQuestion>	originalQuestions;
	/** {@link SOKey#DESCRIPTION} */
	private String							description;
	/** {@link SOKey#BY_USERS} */
	private List<User>					byUsers;
	/** {@link SOKey#ON_HOLD} */
	private boolean						onHold;
	/** {@link SOKey#REASON} */
	private String							reason;

	/**
	 * Returns the {@link #originalQuestions} of current {@link ClosedDetail}.
	 *
	 * @return The originalQuestions as {@link List}.
	 */
	public List<OriginalQuestion> getOriginalQuestions() {
		return this.originalQuestions;
	}

	/**
	 * Sets the {@link #originalQuestions} of current {@link ClosedDetail}.
	 *
	 * @param originalQuestions
	 *           The originalQuestions to set as {@link List}.
	 */
	public void setOriginalQuestions(List<OriginalQuestion> originalQuestions) {
		this.originalQuestions = originalQuestions;
	}

	/**
	 * Returns the {@link #description} of current {@link ClosedDetail}.
	 *
	 * @return The description as {@link String}.
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * Sets the {@link #description} of current {@link ClosedDetail}.
	 *
	 * @param description
	 *           The description to set as {@link String}.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Returns the {@link #byUsers} of current {@link ClosedDetail}.
	 *
	 * @return The byUsers as {@link List}.
	 */
	public List<User> getByUsers() {
		return this.byUsers;
	}

	/**
	 * Sets the {@link #byUsers} of current {@link ClosedDetail}.
	 *
	 * @param byUsers
	 *           The byUsers to set as {@link List}.
	 */
	public void setByUsers(List<User> byUsers) {
		this.byUsers = byUsers;
	}

	/**
	 * Returns the {@link #onHold} of current {@link ClosedDetail}.
	 *
	 * @return The onHold as boolean.
	 */
	public boolean isOnHold() {
		return this.onHold;
	}

	/**
	 * Sets the {@link #onHold} of current {@link ClosedDetail}.
	 *
	 * @param onHold
	 *           The onHold to set as boolean.
	 */
	public void setOnHold(boolean onHold) {
		this.onHold = onHold;
	}

	/**
	 * Returns the {@link #reason} of current {@link ClosedDetail}.
	 *
	 * @return The reason as {@link String}.
	 */
	public String getReason() {
		return this.reason;
	}

	/**
	 * Sets the {@link #reason} of current {@link ClosedDetail}.
	 *
	 * @param reason
	 *           The reason to set as {@link String}.
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}
}
