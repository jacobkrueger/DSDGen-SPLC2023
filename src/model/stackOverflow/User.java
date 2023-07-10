package model.stackOverflow;

import keys.SOKey;

/**
 * Contains objects for users from stack overflow.
 * 
 * @author Alex Mikulinski
 * @since Jul 9, 2018
 */
public class User extends AbstractPostAnswerCommentUser {
	/** {@link SOKey#PROFILE_IMAGE} */
	private String			profileImage;
	private BadgeCounts	badgeCounts;
	/** {@link SOKey#DISPLAY_NAME} */
	private String			displayName;
	/** {@link SOKey#ACCEPT_RATE} */
	private int				acceptRate;
	/** {@link SOKey#REPUTATION} */
	private int				reputation;
	/** {@link SOKey#USER_TYPE} */
	private String			userType;
	/** {@link SOKey#USER_ID} */
	private int				userId;

	/**
	 * Returns the {@link #profileImage} of current {@link User}.
	 *
	 * @return The profileImage as {@link String}.
	 */
	public String getProfileImage() {
		return this.profileImage;
	}

	/**
	 * Sets the {@link #profileImage} of current {@link User}.
	 *
	 * @param profileImage
	 *           The profileImage to set as {@link String}.
	 */
	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	/**
	 * Returns the {@link #badgeCounts} of current {@link User}.
	 *
	 * @return The badgeCounts as {@link BadgeCounts}.
	 */
	public BadgeCounts getBadgeCounts() {
		return this.badgeCounts;
	}

	/**
	 * Sets the {@link #badgeCounts} of current {@link User}.
	 *
	 * @param badgeCounts
	 *           The badgeCounts to set as {@link BadgeCounts}.
	 */
	public void setBadgeCounts(BadgeCounts badgeCounts) {
		this.badgeCounts = badgeCounts;
	}

	/**
	 * Returns the {@link #displayName} of current {@link User}.
	 *
	 * @return The displayName as {@link String}.
	 */
	public String getDisplayName() {
		return this.displayName;
	}

	/**
	 * Sets the {@link #displayName} of current {@link User}.
	 *
	 * @param displayName
	 *           The displayName to set as {@link String}.
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * Returns the {@link #acceptRate} of current {@link User}.
	 *
	 * @return The acceptRate as int.
	 */
	public int getAcceptRate() {
		return this.acceptRate;
	}

	/**
	 * Sets the {@link #acceptRate} of current {@link User}.
	 *
	 * @param acceptRate
	 *           The acceptRate to set as int.
	 */
	public void setAcceptRate(int acceptRate) {
		this.acceptRate = acceptRate;
	}

	/**
	 * Returns the {@link #reputation} of current {@link User}.
	 *
	 * @return The reputation as int.
	 */
	public int getReputation() {
		return this.reputation;
	}

	/**
	 * Sets the {@link #reputation} of current {@link User}.
	 *
	 * @param reputation
	 *           The reputation to set as int.
	 */
	public void setReputation(int reputation) {
		this.reputation = reputation;
	}

	/**
	 * Returns the {@link #userType} of current {@link User}.
	 *
	 * @return The userType as {@link String}.
	 */
	public String getUserType() {
		return this.userType;
	}

	/**
	 * Sets the {@link #userType} of current {@link User}.
	 *
	 * @param userType
	 *           The userType to set as {@link String}.
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}

	/**
	 * Returns the {@link #userId} of current {@link User}.
	 *
	 * @return The userId as int.
	 */
	public int getUserId() {
		return this.userId;
	}

	/**
	 * Sets the {@link #userId} of current {@link User}.
	 *
	 * @param userId
	 *           The userId to set as int.
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}
}
