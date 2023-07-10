package model.stackOverflow;

import keys.SOKey;

/**
 * Contains objects for comments from stack overflow.
 * 
 * @author Alex Mikulinski
 * @since Jul 9, 2018
 */
public class Comment extends AbstractPostAnswerComment {

	private User		reply_to_user;
	/** {@link SOKey#COMMENT_ID} */
	private int			comment_id;
	/** {@link SOKey#POST_TYPE} */
	private String		post_type;
	/** {@link SOKey#POST_ID} */
	private int			post_id;
	/** {@link SOKey#EDITED} */
	private boolean	edited;

	/**
	 * Returns the {@link #reply_to_user} of current {@link Comment}.
	 *
	 * @return The reply_to_user as {@link User}.
	 */
	public User getReply_to_user() {
		return this.reply_to_user;
	}

	/**
	 * Sets the {@link #reply_to_user} of current {@link Comment}.
	 *
	 * @param reply_to_user
	 *           The reply_to_user to set as {@link User}.
	 */
	public void setReply_to_user(User reply_to_user) {
		this.reply_to_user = reply_to_user;
	}

	/**
	 * Returns the {@link #comment_id} of current {@link Comment}.
	 *
	 * @return The comment_id as int.
	 */
	public int getComment_id() {
		return this.comment_id;
	}

	/**
	 * Sets the {@link #comment_id} of current {@link Comment}.
	 *
	 * @param comment_id
	 *           The comment_id to set as int.
	 */
	public void setComment_id(int comment_id) {
		this.comment_id = comment_id;
	}

	/**
	 * Returns the {@link #post_type} of current {@link Comment}.
	 *
	 * @return The post_type as {@link String}.
	 */
	public String getPost_type() {
		return this.post_type;
	}

	/**
	 * Sets the {@link #post_type} of current {@link Comment}.
	 *
	 * @param post_type
	 *           The post_type to set as {@link String}.
	 */
	public void setPost_type(String post_type) {
		this.post_type = post_type;
	}

	/**
	 * Returns the {@link #post_id} of current {@link Comment}.
	 *
	 * @return The post_id as int.
	 */
	public int getPost_id() {
		return this.post_id;
	}

	/**
	 * Sets the {@link #post_id} of current {@link Comment}.
	 *
	 * @param post_id
	 *           The post_id to set as int.
	 */
	public void setPost_id(int post_id) {
		this.post_id = post_id;
	}

	/**
	 * Returns the {@link #edited} of current {@link Comment}.
	 *
	 * @return The edited as boolean.
	 */
	public boolean isEdited() {
		return this.edited;
	}

	/**
	 * Sets the {@link #edited} of current {@link Comment}.
	 *
	 * @param edited
	 *           The edited to set as boolean.
	 */
	public void setEdited(boolean edited) {
		this.edited = edited;
	}

}
