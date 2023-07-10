package model.stackOverflow;

import keys.SOKey;

/**
 * Contains objects for badge counts from stack overflow.
 * 
 * @author Alex Mikulinski
 * @since Jul 9, 2018
 */
public class BadgeCounts {
	/** {@link SOKey#BRONZE} */
	private int	bronze;
	/** {@link SOKey#SILVER} */
	private int	silver;
	/** {@link SOKey#GOLD} */
	private int	gold;

	/**
	 * Returns the {@link #bronze} of current {@link BadgeCounts}.
	 *
	 * @return The bronze as int.
	 */
	public int getBronze() {
		return this.bronze;
	}

	/**
	 * Sets the {@link #bronze} of current {@link BadgeCounts}.
	 *
	 * @param bronze
	 *           The bronze to set as int.
	 */
	public void setBronze(int bronze) {
		this.bronze = bronze;
	}

	/**
	 * Returns the {@link #silver} of current {@link BadgeCounts}.
	 *
	 * @return The silver as int.
	 */
	public int getSilver() {
		return this.silver;
	}

	/**
	 * Sets the {@link #silver} of current {@link BadgeCounts}.
	 *
	 * @param silver
	 *           The silver to set as int.
	 */
	public void setSilver(int silver) {
		this.silver = silver;
	}

	/**
	 * Returns the {@link #gold} of current {@link BadgeCounts}.
	 *
	 * @return The gold as int.
	 */
	public int getGold() {
		return this.gold;
	}

	/**
	 * Sets the {@link #gold} of current {@link BadgeCounts}.
	 *
	 * @param gold
	 *           The gold to set as int.
	 */
	public void setGold(int gold) {
		this.gold = gold;
	}
}
