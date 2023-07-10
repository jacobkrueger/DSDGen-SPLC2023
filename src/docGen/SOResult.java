package docGen;

import java.util.Date;

import keys.SOKey;

/**
 * Contains the results for a search over stack overflow posts.
 * 
 * @author Alex Mikulinski
 * @since Oct 3, 2018
 */
public class SOResult {
	/** {@link SOKey#LINK} */
	private String	link;
	/** Contains keywords found for this {@link SOResult} */
	private String	content;
	/** Post {@link SOKey#TITLE}. */
	private String	title;
	private Date	date;
	/** {@link SOKey#SCORE} */
	private int		score;

	/**
	 * Creates a new instance of {@link SOResult}.
	 * 
	 * @param _date
	 *           The {@link #date} to set;
	 * @param _link
	 *           The {@link #link} to set;
	 * @param _content
	 *           The {@link #content} to set;
	 * @param _upVoteCount
	 *           The {@link #score} to set;
	 * @param _title
	 *           The {@link #title} to set;
	 */
	public SOResult(Date _date, String _link, String _content, int _upVoteCount, String _title) {
		;
		this.link		= _link;
		this.content	= _content;
		this.score		= _upVoteCount;
		this.title		= _title;
		this.date		= _date;
	}

	/**
	 * Adds the passed {@link String} to {@link #content} separated by ", ".
	 * 
	 * @param _content
	 *           A new keyword to add to {@link #content}.
	 */
	public void addContent(String _content) {
		this.content += ", " + _content;
	}

	/**
	 * Returns the {@link #link} of current {@link SOResult}.
	 *
	 * @return The {@link #link} as {@link String}.
	 */
	public String getLink() {
		return this.link;
	}

	/**
	 * Sets the {@link #link} of current {@link SOResult}.
	 *
	 * @param link
	 *           The {@link #link} to set as {@link String}.
	 */
	public void setLink(String link) {
		this.link = link;
	}

	/**
	 * Returns the {@link #content} of current {@link SOResult}.
	 *
	 * @return The {@link #content} as {@link String}.
	 */
	public String getContent() {
		return this.content;
	}

	/**
	 * Sets the {@link #content} of current {@link SOResult}.
	 *
	 * @param content
	 *           The {@link #content} to set as {@link String}.
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * Returns the {@link #score} of current {@link SOResult}.
	 *
	 * @return The {@link #score} as int.
	 */
	public int getScore() {
		return this.score;
	}

	/**
	 * Sets the {@link #score} of current {@link SOResult}.
	 *
	 * @param score
	 *           The {@link #score} to set as int.
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * Returns the {@link #title} of current {@link SOResult}.
	 *
	 * @return The {@link #title} as {@link String}.
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * Sets the {@link #title} of current {@link SOResult}.
	 *
	 * @param title
	 *           The {@link #title} to set as {@link String}.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Returns the {@link #date} of current {@link SOResult}.
	 *
	 * @return The {@link #date} as {@link Date}.
	 */
	public Date getDate() {
		return this.date;
	}

	/**
	 * Sets the {@link #date} of current {@link SOResult}.
	 *
	 * @param date
	 *           The {@link #date} to set as {@link Date}.
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("StackOverflowResult [");
		if (this.link != null) {
			builder.append("link=");
			builder.append(this.link);
			builder.append(", ");
		}
		if (this.content != null) {
			builder.append("content=");
			builder.append(this.content);
			builder.append(", ");
		}
		if (this.title != null) {
			builder.append("title=");
			builder.append(this.title);
			builder.append(", ");
		}
		builder.append("score=");
		builder.append(this.score);
		builder.append(", ");
		if (this.date != null) {
			builder.append("date=");
			builder.append(this.date);
		}
		builder.append("]");
		return builder.toString();
	}

}
