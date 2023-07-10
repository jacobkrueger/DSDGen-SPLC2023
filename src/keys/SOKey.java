package keys;

/**
 * Contains JSON keys from a JSON file for a Stack Overflow thread.
 * 
 * @author Alex Mikulinski
 * @since Jul 7, 2018
 */
@SuppressWarnings("javadoc")
public interface SOKey {

	// Tags for posts, answers, comments and users
	String	LINK								= "link";
	// Tags for posts, answers and comments
	String	BODY_MARKDOWN					= "body_markdown";
	String	CREATION_DATE					= "creation_date";
	String	OWNER								= "owner";
	String	SCORE								= "score";
	String	BODY								= "body";
	// Tags for posts and answers
	String	COMMUNITY_OWNED_DATE			= "community_owned_date";
	String	LAST_ACTIVITY_DATE			= "last_activity_date";
	String	DOWN_VOTE_COUNT				= "down_vote_count";
	String	LAST_EDIT_DATE					= "last_edit_date";
	String	UP_VOTE_COUNT					= "up_vote_count";
	String	COMMENT_COUNT					= "comment_count";
	String	LAST_EDITOR						= "last_editor";
	String	SHARE_LINK						= "share_link";
	String	COMMENTS							= "comments";
	String	TAGS								= "tags";

	// Tags for posts, answers and original questions
	String	QUESTION_ID						= "question_id";
	String	TITLE								= "title";
	// Tags for posts and original questions
	String	ACCEPTED_ANSWER_ID			= "accepted_answer_id";
	String	ANSWER_COUNT					= "answer_count";

	// Tags for posts
	String	DELETE_VOTE_COUNT				= "delete_vote_count";
	String	REOPEN_VOTE_COUNT				= "reopen_vote_count";
	String	CLOSE_VOTE_COUNT				= "close_vote_count";
	String	FAVORITE_COUNT					= "favorite_count";
	String	PROTECTED_DATE					= "protected_date";
	String	CLOSED_DETAILS					= "closed_details";
	String	MIGRATED_FROM					= "migrated_from";
	String	CLOSED_REASON					= "closed_reason";
	String	CLOSED_DATE						= "closed_date";
	String	IS_ANSWERED						= "is_answered";
	String	VIEW_COUNT						= "view_count";
	String	ANSWERS							= "answers";

	// Tags for answers
	String	AWARDED_BOUNTY_AMOUNT		= "awarded_bounty_amount";
	String	AWARDED_BOUNTY_USERS			= "awarded_bounty_users";
	String	IS_ACCEPTED						= "is_accepted";
	String	ANSWER_ID						= "answer_id";

	// Tags for comments
	String	REPLY_TO_USER					= "reply_to_user";
	String	COMMENT_ID						= "comment_id";
	String	POST_TYPE						= "post_type";
	String	POST_ID							= "post_id";
	String	EDITED							= "edited";

	// Tags for users
	String	PROFILE_IMAGE					= "profile_image";
	String	BADGE_COUNTS					= "badge_counts";
	String	DISPLAY_NAME					= "display_name";
	String	ACCEPT_RATE						= "accept_rate";
	String	REPUTATION						= "reputation";
	String	USER_TYPE						= "user_type";
	String	USER_ID							= "user_id";

	// Tags for closed details
	String	ORIGINAL_QUESTIONS			= "original_questions";
	String	DESCRIPTION						= "description";
	String	BY_USERS							= "by_users";
	String	ON_HOLD							= "on_hold";
	String	REASON							= "reason";

	// Tags for badge counts
	String	BRONZE							= "bronze";
	String	SILVER							= "silver";
	String	GOLD								= "gold";

	// Tags for migrated from
	String	OTHER_SITE						= "other_site";
	String	ON_DATE							= "on_date";

	// Tags for other sites and related sites
	String	API_SITE_PARAMETER			= "api_site_parameter";
	String	SITE_URL							= "site_url";
	String	NAME								= "name";

	// Tags for other site
	String	HIGH_RESOLUTION_ICON_URL	= "high_resolution_icon_url";
	String	MARKDOWN_EXTENSIONS			= "markdown_extensions";
	String	CLOSED_BETA_DATE				= "closed_beta_date";
	String	TWITTER_ACCOUNT				= "twitter_account";
	String	OPEN_BETA_DATE					= "open_beta_date";
	String	RELATED_SITES					= "related_sites";
	String	LAUNCH_DATE						= "launch_date";
	String	FAVICON_URL						= "favicon_url";
	String	SITE_STATE						= "site_state";
	String	SITE_TYPE						= "site_type";
	String	AUDIENCE							= "audience";
	String	ICON_URL							= "icon_url";
	String	LOGO_URL							= "logo_url";
	String	ALIASES							= "aliases";
	String	STYLING							= "styling";

	// Tags for related sites
	String	RELATION							= "relation";

	// Tags for styling
	String	TAG_FOREGROUND_COLOR			= "tag_foreground_color";
	String	TAG_BACKGROUND_COLOR			= "tag_background_color";
	String	LINK_COLOR						= "link_color";
}
