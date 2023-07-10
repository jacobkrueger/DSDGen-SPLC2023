package model.stackOverflow;

import keys.SOKey;

/**
 * Contains objects for styling from stack overflow.
 * 
 * @author Alex Mikulinski
 * @since Sep 25, 2018
 */
public class Styling {
	/** {@link SOKey#TAG_FOREGROUND_COLOR} */
	private String	tagForegroundColor;
	/** {@link SOKey#TAG_BACKGROUND_COLOR} */
	private String	tagBackgroundColor;
	/** {@link SOKey#LINK_COLOR} */
	private String	linkColor;

	/**
	 * Returns the {@link #tagForegroundColor} of current {@link Styling}.
	 *
	 * @return The {@link #tagForegroundColor} as {@link String}.
	 */
	public String getTagForegroundColor() {
		return this.tagForegroundColor;
	}

	/**
	 * Sets the {@link #tagForegroundColor} of current {@link Styling}.
	 *
	 * @param tagForegroundColor
	 *           The {@link #tagForegroundColor} to set as {@link String}.
	 */
	public void setTagForegroundColor(String tagForegroundColor) {
		this.tagForegroundColor = tagForegroundColor;
	}

	/**
	 * Returns the {@link #tagBackgroundColor} of current {@link Styling}.
	 *
	 * @return The {@link #tagBackgroundColor} as {@link String}.
	 */
	public String getTagBackgroundColor() {
		return this.tagBackgroundColor;
	}

	/**
	 * Sets the {@link #tagBackgroundColor} of current {@link Styling}.
	 *
	 * @param tagBackgroundColor
	 *           The {@link #tagBackgroundColor} to set as {@link String}.
	 */
	public void setTagBackgroundColor(String tagBackgroundColor) {
		this.tagBackgroundColor = tagBackgroundColor;
	}

	/**
	 * Returns the {@link #linkColor} of current {@link Styling}.
	 *
	 * @return The {@link #linkColor} as {@link String}.
	 */
	public String getLinkColor() {
		return this.linkColor;
	}

	/**
	 * Sets the {@link #linkColor} of current {@link Styling}.
	 *
	 * @param linkColor
	 *           The {@link #linkColor} to set as {@link String}.
	 */
	public void setLinkColor(String linkColor) {
		this.linkColor = linkColor;
	}
}
