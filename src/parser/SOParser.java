package parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import app.Settings;
import docGen.SOResult;
import keys.SOKey;
import model.stackOverflow.Answer;
import model.stackOverflow.BadgeCounts;
import model.stackOverflow.ClosedDetail;
import model.stackOverflow.Comment;
import model.stackOverflow.MigratedFrom;
import model.stackOverflow.OriginalQuestion;
import model.stackOverflow.OtherSite;
import model.stackOverflow.Post;
import model.stackOverflow.RelatedSite;
import model.stackOverflow.Styling;
import model.stackOverflow.User;
import utils.DateHelper;

/**
 * Contains methods for parsing a JSON file with Stack Overflow threads. The
 * file should be created with the StackExchange API.
 *
 * @author Alex Mikulinski
 * @since Jul 10, 2018
 * @see <a href="https://api.stackexchange.com/docs">StackExchange</a>
 */
public class SOParser {

	private JsonParser parser;

	/**
	 * Creates a new instance of {@link SOParser}.
	 */
	public SOParser() {
		JsonFactory factory;
		factory = new JsonFactory();

		try {
			this.parser = factory.createParser(new File(Settings.SO_PATH.get() + Settings.SO_FILE_NAME.get()));
		} catch (IOException _e) {
			_e.printStackTrace();
		}
	}

	/**
	 * Parses all stack overflow posts and returns only those whose {@link String}
	 * representation matches the passed pattern.
	 *
	 * @param _pattern
	 *           The regex pattern to check a post.
	 * @return A {@link List} of posts which satisfies the passed pattern.
	 * @throws IOException
	 *            If something goes wrong while parsing.
	 */
	public List<Post> parse(String _pattern) throws IOException {
		List<Post>	posts;
		JsonToken	token;
		String		postString;
		String		pattern;
		Post			post;

		pattern	= _pattern;
		posts		= new ArrayList<>();
		token		= this.parser.nextToken();

		if (!JsonToken.START_ARRAY.equals(token)) {
			throw new IOException("Error while parsing stackoverflow: First token is not [.");
		}

		token = this.parser.nextToken();

		while (!token.equals(JsonToken.END_ARRAY)) {
			post			= this._parsePost();
			postString	= post.toString();

			if (!postString.matches(pattern)) {
				token = this.parser.nextToken();
				continue;
			}

			posts.add(post);

			token = this.parser.nextToken();
		}

		this.parser.close();

		return posts;
	}

	/**
	 * Parses all stack overflow posts and returns only those whose post or answer
	 * body contains the passed class name <b>and</b> any of passed key words.
	 *
	 * @param _className
	 *           The class name to search for.
	 * @param _keyWords
	 *           The key words to search for.
	 * @return List of {@link SOResult}s holding needed informations for posts or
	 *         answers.
	 * @throws IOException
	 *            If something goes wrong while parsing.
	 */
	public List<SOResult> parseResults(String _className, List<String> _keyWords) throws IOException {
		List<SOResult>	stackOverflowResults;
		JsonToken		token;
		String			content;
		String			className;
		Post				post;

		className				= _className;
		stackOverflowResults	= new ArrayList<>();
		token						= this.parser.nextToken();

		if (!JsonToken.START_ARRAY.equals(token)) {
			throw new IOException("Error while parsing stackoverflow: First token ist not [.");
		}

		token = this.parser.nextToken();

		while (!token.equals(JsonToken.END_ARRAY)) {
			post		= this._parsePost();

			// Posts
			content	= this._getMatches(className, post.getBody(), _keyWords);
			if (content != "" && post.getScore() >= 0) {
				stackOverflowResults.add(
						new SOResult(post.getLastActivityDate(), post.getLink(), content, post.getScore(), post.getTitle()));
			} else if (post.getAnswers() != null) {
				// Answers
				for (Answer answer : post.getAnswers()) {
					content = this._getMatches(className, answer.getBody(), _keyWords);
					if (content != "" && answer.getScore() >= 0) {
						stackOverflowResults.add(new SOResult(post.getLastActivityDate(), answer.getLink(), content,
								answer.getScore(), post.getTitle()));
					}
				}
			}
			token = this.parser.nextToken();
		}

		this.parser.close();
		return stackOverflowResults;
	}

	/**
	 * Checks the passed string for containing the class name and any of passed key
	 * words.
	 *
	 * @param _className
	 *           The class name to search for.
	 * @param _body
	 *           The {@link String} to search in.
	 * @param _keyWords
	 *           The key words to search for.
	 * @return A {@link String} containing all keys found in the body separated by
	 *         ";".
	 */
	private String _getMatches(String _className, String _body, List<String> _keyWords) {
		HashMap<String, Integer>	results;
		List<String>					keyWords;
		String							body;
		String							result;

		body		= _body;
		result	= "";
		results	= new HashMap<>();
		keyWords	= _keyWords;

		if (!body.contains(_className)) {
			return result;
		}

		for (String keyWord : keyWords) {
			if (!body.contains(keyWord)) {
				continue;
			}

			if (!results.containsKey(keyWord)) {
				results.put(keyWord, 1);
			} else {
				results.put(keyWord, results.get(keyWord).intValue() + 1);
			}
		}

		for (String key : results.keySet()) {
			result += key + " " + results.get(key) + "x; ";
		}

		return result.toString().replaceAll("; $", "");
	}

	/**
	 * Parses and prints out a chosen number of stack overflow posts.
	 *
	 * @param _number
	 *           Number of {@link Post}s to parse.
	 * @throws IOException
	 *            If something goes wrong while parsing.
	 */
	public void parse(int _number) throws IOException {
		JsonToken	token;
		int			number;

		number	= _number;

		token		= this.parser.nextToken();

		if (!JsonToken.START_ARRAY.equals(token)) {
			throw new IOException("Error while parsing stackoverflow: First token ist not [.");
		}

		token = this.parser.nextToken();

		while (number > 0 && !token.equals(JsonToken.END_ARRAY)) {

			System.out.println(this._parsePost());

			number--;
			token = this.parser.nextToken();
		}

		this.parser.close();
	}

	private Post _parsePost() throws IOException {
		JsonToken	token;
		Post			post;

		post	= new Post();

		// It has to be current token here because parsing starts with parsing posts
		token	= this.parser.currentToken();

		if (!JsonToken.START_OBJECT.equals(token)) {
			throw new IOException("Error while parsing stackoverflow: Post does not start with {");
		}

		token = this.parser.nextToken();

		// Start parsing content
		while (!token.equals(JsonToken.END_OBJECT)) {
			switch (this.parser.getText()) {

			case SOKey.LINK:
				post.setLink(this._parseString());
				break;

			case SOKey.BODY_MARKDOWN:
				post.setBodyMarkdown(this._parseString());
				break;
			case SOKey.CREATION_DATE:
				post.setCreationDate(this._parseDate());
				break;
			case SOKey.OWNER:
				post.setOwner(this._parseUser(false));
				break;
			case SOKey.SCORE:
				post.setScore(this._parseInt());
				break;
			case SOKey.BODY:
				post.setBody(this._parseString());
				break;

			case SOKey.COMMUNITY_OWNED_DATE:
				post.setCommunityOwnedDate(this._parseDate());
				break;
			case SOKey.LAST_ACTIVITY_DATE:
				post.setLastActivityDate(this._parseDate());
				break;
			case SOKey.DOWN_VOTE_COUNT:
				post.setDownVoteCount(this._parseInt());
				break;
			case SOKey.UP_VOTE_COUNT:
				post.setUpVoteCount(this._parseInt());
				break;
			case SOKey.COMMENT_COUNT:
				post.setCommentCount(this._parseInt());
				break;
			case SOKey.SHARE_LINK:
				post.setShareLink(this._parseString());
				break;
			case SOKey.TAGS:
				post.setTags(this._parseStringArray());
				break;

			case SOKey.QUESTION_ID:
				post.setQuestionId(this._parseInt());
				break;
			case SOKey.TITLE:
				post.setTitle(this._parseString());
				break;

			case SOKey.ACCEPTED_ANSWER_ID:
				post.setAcceptedAnswerId(this._parseInt());
				break;
			case SOKey.ANSWER_COUNT:
				post.setAnswerCount(this._parseInt());
				break;

			case SOKey.DELETE_VOTE_COUNT:
				post.setDeleteVoteCount(this._parseInt());
				break;
			case SOKey.REOPEN_VOTE_COUNT:
				post.setReopenVoteCount(this._parseInt());
				break;
			case SOKey.CLOSE_VOTE_COUNT:
				post.setCloseVoteCount(this._parseInt());
				break;
			case SOKey.LAST_EDIT_DATE:
				post.setLastEditDate(this._parseDate());
				break;
			case SOKey.FAVORITE_COUNT:
				post.setFavoriteCount(this._parseInt());
				break;
			case SOKey.PROTECTED_DATE:
				post.setProtectedDate(this._parseDate());
				break;
			case SOKey.CLOSED_DETAILS:
				post.setClosedDetail(this._parseClosedDetail());
				break;
			case SOKey.MIGRATED_FROM:
				post.setMigratedFrom(this._parseMigratedFrom());
				break;
			case SOKey.CLOSED_REASON:
				post.setClosedReason(this._parseString());
				break;
			case SOKey.CLOSED_DATE:
				post.setClosedDate(this._parseDate());
				break;
			case SOKey.LAST_EDITOR:
				post.setLastEditor(this._parseUser(false));
				break;
			case SOKey.IS_ANSWERED:
				post.setAnswered(this._parseBool());
				break;
			case SOKey.VIEW_COUNT:
				post.setViewCount(this._parseInt());
				break;
			case SOKey.COMMENTS:
				post.setComments(this._parseComments());
				break;
			case SOKey.ANSWERS:
				post.setAnswers(this._parseAnswers());
				break;

			default:
				throw new IOException(
						"Error while parsing stackoverflow post: Detected unknown tag: " + this.parser.getText());
			}

			// In this way the loop always starts with a tag name
			token = this.parser.nextToken();
		}

		return post;
	}

	private List<Answer> _parseAnswers() throws IOException {
		List<Answer>	answers;
		JsonToken		token;
		Answer			answer;

		answers	= new ArrayList<>();

		token		= this.parser.nextToken();

		if (!JsonToken.START_ARRAY.equals(token)) {
			throw new IOException("Error while parsing stackoverflow: Answer List does not start with [");
		}

		token = this.parser.nextToken();

		// Start parsing content
		while (!token.equals(JsonToken.END_ARRAY)) {
			answer = new Answer();

			if (!JsonToken.START_OBJECT.equals(token)) {
				throw new IOException("Error while parsing stackoverflow: Answer does not start with {");
			}

			token = this.parser.nextToken();

			while (!token.equals(JsonToken.END_OBJECT)) {

				switch (this.parser.getText()) {

				case SOKey.LINK:
					answer.setLink(this._parseString());
					break;

				case SOKey.BODY_MARKDOWN:
					answer.setBodyMarkdown(this._parseString());
					break;
				case SOKey.CREATION_DATE:
					answer.setCreationDate(this._parseDate());
					break;
				case SOKey.OWNER:
					answer.setOwner(this._parseUser(false));
					break;
				case SOKey.SCORE:
					answer.setScore(this._parseInt());
					break;
				case SOKey.BODY:
					answer.setBody(this._parseString());
					break;

				case SOKey.COMMUNITY_OWNED_DATE:
					answer.setCommunityOwnedDate(this._parseDate());
					break;
				case SOKey.LAST_ACTIVITY_DATE:
					answer.setLastActivityDate(this._parseDate());
					break;
				case SOKey.DOWN_VOTE_COUNT:
					answer.setDownVoteCount(this._parseInt());
					break;
				case SOKey.LAST_EDIT_DATE:
					answer.setLastEditDate(this._parseDate());
					break;
				case SOKey.UP_VOTE_COUNT:
					answer.setUpVoteCount(this._parseInt());
					break;
				case SOKey.COMMENT_COUNT:
					answer.setCommentCount(this._parseInt());
					break;
				case SOKey.LAST_EDITOR:
					answer.setLastEditor(this._parseUser(false));
					break;
				case SOKey.SHARE_LINK:
					answer.setShareLink(this._parseString());
					break;
				case SOKey.COMMENTS:
					answer.setComments(this._parseComments());
					break;
				case SOKey.TAGS:
					answer.setTags(this._parseStringArray());
					break;

				case SOKey.QUESTION_ID:
					answer.setQuestionId(this._parseInt());
					break;
				case SOKey.TITLE:
					answer.setTitle(this._parseString());
					break;

				case SOKey.AWARDED_BOUNTY_AMOUNT:
					answer.setAwardedBountyAmount(this._parseInt());
					break;
				case SOKey.AWARDED_BOUNTY_USERS:
					answer.setAwardedBountyUsers(this._parseUserArray());
					break;
				case SOKey.IS_ACCEPTED:
					answer.setAccepted(this._parseBool());
					break;
				case SOKey.ANSWER_ID:
					answer.setAnswerId(this._parseInt());
					break;

				default:
					throw new IOException(
							"Error while parsing stackoverflow answers: Detected unknown tag: " + this.parser.getText());
				}

				// In this way the loop always starts with a tag name
				token = this.parser.nextToken();
			}

			answers.add(answer);

			token = this.parser.nextToken();
		}

		return answers;
	}

	private List<Comment> _parseComments() throws IOException {
		List<Comment>	comments;
		JsonToken		token;
		Comment			comment;

		comments	= new ArrayList<>();

		token		= this.parser.nextToken();

		if (!JsonToken.START_ARRAY.equals(token)) {
			throw new IOException("Error while parsing stackoverflow: Comment List does not start with [");
		}

		token = this.parser.nextToken();

		// Start parsing content
		while (!token.equals(JsonToken.END_ARRAY)) {
			comment = new Comment();

			if (!JsonToken.START_OBJECT.equals(token)) {
				throw new IOException("Error while parsing stackoverflow: Comment does not start with {");
			}

			token = this.parser.nextToken();

			while (!token.equals(JsonToken.END_OBJECT)) {

				switch (this.parser.getText()) {

				case SOKey.LINK:
					comment.setLink(this._parseString());
					break;

				case SOKey.BODY_MARKDOWN:
					comment.setBodyMarkdown(this._parseString());
					break;
				case SOKey.CREATION_DATE:
					comment.setCreationDate(this._parseDate());
					break;
				case SOKey.OWNER:
					comment.setOwner(this._parseUser(false));
					break;
				case SOKey.SCORE:
					comment.setScore(this._parseInt());
					break;
				case SOKey.BODY:
					comment.setBody(this._parseString());
					break;

				case SOKey.REPLY_TO_USER:
					comment.setReply_to_user(this._parseUser(false));
					break;
				case SOKey.COMMENT_ID:
					comment.setComment_id(this._parseInt());
					break;
				case SOKey.POST_TYPE:
					comment.setPost_type(this._parseString());
					break;
				case SOKey.POST_ID:
					comment.setPost_id(this._parseInt());
					break;
				case SOKey.EDITED:
					comment.setEdited(this._parseBool());
					break;

				default:
					throw new IOException(
							"Error while parsing stackoverflow comments: Detected unknown tag: " + this.parser.getText());
				}

				// In this way the loop always starts with a tag name
				token = this.parser.nextToken();
			}

			comments.add(comment);

			token = this.parser.nextToken();
		}

		return comments;
	}

	/**
	 * Parses a {@link User}.
	 *
	 * @param _parsingUserArray
	 *           Is used to determine whether an array of {@link User}s is being
	 *           parsed. That's why parsing of these {@link User} is started by
	 *           looking at the current token instead of the next one to determine
	 *           whether we reached the end of the array. Looks like [{}, {}]
	 *           instead of a normal {}.
	 * @return A new {@link User}.
	 * @throws IOException
	 *            If something goes wrong while parsing.
	 */
	private User _parseUser(boolean _parsingUserArray) throws IOException {
		JsonToken	token;
		User			user;
		boolean		parsingUserArray;

		user					= new User();
		parsingUserArray	= _parsingUserArray;

		if (parsingUserArray) {
			token = this.parser.currentToken();
		} else {
			token = this.parser.nextToken();
		}

		if (!JsonToken.START_OBJECT.equals(token)) {
			throw new IOException("Error while parsing stackoverflow: User does not start with {");
		}

		token = this.parser.nextToken();

		// Start parsing content
		while (!token.equals(JsonToken.END_OBJECT)) {
			switch (this.parser.getText()) {

			case SOKey.LINK:
				user.setLink(this._parseString());
				break;

			case SOKey.PROFILE_IMAGE:
				user.setProfileImage(this._parseString());
				break;
			case SOKey.BADGE_COUNTS:
				user.setBadgeCounts(this._parseBadgeCounts());
				break;
			case SOKey.DISPLAY_NAME:
				user.setDisplayName(this._parseString());
				break;
			case SOKey.ACCEPT_RATE:
				user.setAcceptRate(this._parseInt());
				break;
			case SOKey.REPUTATION:
				user.setReputation(this._parseInt());
				break;
			case SOKey.USER_TYPE:
				user.setUserType(this._parseString());
				break;
			case SOKey.USER_ID:
				user.setUserId(this._parseInt());
				break;

			default:
				throw new IOException(
						"Error while parsing stackoverflow user: Detected unknown tag: " + this.parser.getText());
			}

			// In this way the loop always starts with a tag name
			token = this.parser.nextToken();
		}

		return user;
	}

	private BadgeCounts _parseBadgeCounts() throws IOException {
		JsonToken	token;
		BadgeCounts	badgeCounts;

		badgeCounts	= new BadgeCounts();

		token			= this.parser.nextToken();

		if (!JsonToken.START_OBJECT.equals(token)) {
			throw new IOException("Error while parsing stackoverflow: BadgeCounts does not start with {");
		}

		token = this.parser.nextToken();

		// Start parsing content
		while (!token.equals(JsonToken.END_OBJECT)) {
			switch (this.parser.getText()) {

			case SOKey.BRONZE:
				badgeCounts.setBronze(this._parseInt());
				break;
			case SOKey.SILVER:
				badgeCounts.setSilver(this._parseInt());
				break;
			case SOKey.GOLD:
				badgeCounts.setGold(this._parseInt());
				break;
			default:
				throw new IOException(
						"Error while parsing stackoverflow BadgeCounts: Detected unknown tag: " + this.parser.getText());
			}

			// In this way the loop always starts with a tag name
			token = this.parser.nextToken();
		}

		return badgeCounts;
	}

	private ClosedDetail _parseClosedDetail() throws IOException {
		JsonToken		token;
		ClosedDetail	closedDetail;

		closedDetail	= new ClosedDetail();

		token				= this.parser.nextToken();

		if (!JsonToken.START_OBJECT.equals(token)) {
			throw new IOException("Error while parsing stackoverflow: ClosedDetail does not start with {");
		}

		token = this.parser.nextToken();

		// Start parsing content
		while (!token.equals(JsonToken.END_OBJECT)) {
			switch (this.parser.getText()) {

			case SOKey.ORIGINAL_QUESTIONS:
				closedDetail.setOriginalQuestions(this._parseOriginalQuestions());
				break;
			case SOKey.DESCRIPTION:
				closedDetail.setDescription(this._parseString());
				break;
			case SOKey.BY_USERS:
				closedDetail.setByUsers(this._parseUserArray());
				break;
			case SOKey.ON_HOLD:
				closedDetail.setOnHold(this._parseBool());
				break;
			case SOKey.REASON:
				closedDetail.setReason(this._parseString());
				break;

			default:
				throw new IOException(
						"Error while parsing stackoverflow ClosedDetail: Detected unknown tag: " + this.parser.getText());
			}

			// In this way the loop always starts with a tag name
			token = this.parser.nextToken();
		}

		return closedDetail;
	}

	private List<User> _parseUserArray() throws IOException {
		List<User>	users;
		JsonToken	token;

		users	= new ArrayList<>();

		token	= this.parser.nextToken();

		if (!JsonToken.START_ARRAY.equals(token)) {
			throw new IOException("Error while parsing stackoverflow: ByUsers List does not start with [");
		}

		token = this.parser.nextToken();

		// Start parsing content
		while (!token.equals(JsonToken.END_ARRAY)) {
			users.add(this._parseUser(true));

			token = this.parser.nextToken();
		}

		return users;
	}

	private List<OriginalQuestion> _parseOriginalQuestions() throws IOException {
		List<OriginalQuestion>	originalQuestions;
		OriginalQuestion			originalQuestion;
		JsonToken					token;

		originalQuestions	= new ArrayList<>();

		token					= this.parser.nextToken();

		if (!JsonToken.START_ARRAY.equals(token)) {
			throw new IOException("Error while parsing stackoverflow: OriginalQuestion List does not start with [");
		}

		token = this.parser.nextToken();

		// Start parsing content
		while (!token.equals(JsonToken.END_ARRAY)) {
			originalQuestion = new OriginalQuestion();

			if (!JsonToken.START_OBJECT.equals(token)) {
				throw new IOException("Error while parsing stackoverflow: OriginalQuestion does not start with {");
			}

			token = this.parser.nextToken();

			while (!token.equals(JsonToken.END_OBJECT)) {

				switch (this.parser.getText()) {

				case SOKey.QUESTION_ID:
					originalQuestion.setQuestionId(this._parseInt());
					break;
				case SOKey.TITLE:
					originalQuestion.setTitle(this._parseString());
					break;

				case SOKey.ANSWER_COUNT:
					originalQuestion.setAnswerCount(this._parseInt());
					break;

				case SOKey.ACCEPTED_ANSWER_ID:
					originalQuestion.setAcceptedAnswerId(this._parseInt());
					break;

				default:
					throw new IOException("Error while parsing stackoverflow original questions: Detected unknown tag: "
							+ this.parser.getText());
				}

				// In this way the loop always starts with a tag name
				token = this.parser.nextToken();
			}

			originalQuestions.add(originalQuestion);

			token = this.parser.nextToken();
		}

		return originalQuestions;
	}

	private MigratedFrom _parseMigratedFrom() throws IOException {
		MigratedFrom	migratedFrom;
		JsonToken		token;

		migratedFrom	= new MigratedFrom();

		token				= this.parser.nextToken();

		if (!JsonToken.START_OBJECT.equals(token)) {
			throw new IOException("Error while parsing stackoverflow: MigratedFrom does not start with {");
		}

		token = this.parser.nextToken();

		// Start parsing content
		while (!token.equals(JsonToken.END_OBJECT)) {
			switch (this.parser.getText()) {

			case SOKey.QUESTION_ID:
				migratedFrom.setQuestionId(this._parseInt());
				break;

			case SOKey.OTHER_SITE:
				migratedFrom.setOtherSite(this._parseOtherSite());
				break;
			case SOKey.ON_DATE:
				migratedFrom.setOnDate(this._parseDate());
				break;

			default:
				throw new IOException(
						"Error while parsing stackoverflow MigratedFrom: Detected unknown tag: " + this.parser.getText());
			}

			// In this way the loop always starts with a tag name
			token = this.parser.nextToken();
		}

		return migratedFrom;
	}

	private OtherSite _parseOtherSite() throws IOException {
		OtherSite	otherSite;
		JsonToken	token;

		otherSite	= new OtherSite();

		token			= this.parser.nextToken();

		if (!JsonToken.START_OBJECT.equals(token)) {
			throw new IOException("Error while parsing stackoverflow: OtherSite does not start with {");
		}

		token = this.parser.nextToken();

		// Start parsing content
		while (!token.equals(JsonToken.END_OBJECT)) {
			switch (this.parser.getText()) {

			case SOKey.API_SITE_PARAMETER:
				otherSite.setApiSiteParameter(this._parseString());
				break;
			case SOKey.SITE_URL:
				otherSite.setSiteUrl(this._parseString());
				break;
			case SOKey.NAME:
				otherSite.setName(this._parseString());
				break;

			case SOKey.HIGH_RESOLUTION_ICON_URL:
				otherSite.setHighResolutionIconUrl(this._parseString());
				break;
			case SOKey.MARKDOWN_EXTENSIONS:
				otherSite.setMarkdownExtensions(this._parseStringArray());
				break;
			case SOKey.CLOSED_BETA_DATE:
				otherSite.setClosedBetaDate(this._parseDate());
				break;
			case SOKey.TWITTER_ACCOUNT:
				otherSite.setTwitterAccount(this._parseString());
				break;
			case SOKey.OPEN_BETA_DATE:
				otherSite.setOpenBetaDate(this._parseDate());
				break;
			case SOKey.RELATED_SITES:
				otherSite.setRelatedSites(this._parseRelatedSites());
				break;
			case SOKey.LAUNCH_DATE:
				otherSite.setLaunchDate(this._parseDate());
				break;
			case SOKey.FAVICON_URL:
				otherSite.setFaviconUrl(this._parseString());
				break;
			case SOKey.SITE_STATE:
				otherSite.setSiteState(this._parseString());
				break;
			case SOKey.SITE_TYPE:
				otherSite.setSiteType(this._parseString());
				break;
			case SOKey.AUDIENCE:
				otherSite.setAudience(this._parseString());
				break;
			case SOKey.ICON_URL:
				otherSite.setIconUrl(this._parseString());
				break;
			case SOKey.LOGO_URL:
				otherSite.setLogoUrl(this._parseString());
				break;
			case SOKey.ALIASES:
				otherSite.setAliases(this._parseStringArray());
				break;
			case SOKey.STYLING:
				otherSite.setStyling(this._parseStyling());
				break;
			default:
				throw new IOException(
						"Error while parsing stackoverflow OtherSite: Detected unknown tag: " + this.parser.getText());
			}

			// In this way the loop always starts with a tag name
			token = this.parser.nextToken();
		}

		return otherSite;
	}

	private Styling _parseStyling() throws IOException {
		Styling		styling;
		JsonToken	token;

		styling	= new Styling();

		token		= this.parser.nextToken();

		if (!JsonToken.START_OBJECT.equals(token)) {
			throw new IOException("Error while parsing stackoverflow: Styling does not start with {");
		}

		token = this.parser.nextToken();

		// Start parsing content
		while (!token.equals(JsonToken.END_OBJECT)) {
			switch (this.parser.getText()) {

			case SOKey.TAG_FOREGROUND_COLOR:
				styling.setTagForegroundColor(this._parseString());
				break;
			case SOKey.TAG_BACKGROUND_COLOR:
				styling.setTagBackgroundColor(this._parseString());
				break;
			case SOKey.LINK_COLOR:
				styling.setLinkColor(this._parseString());
				break;
			default:
				throw new IOException(
						"Error while parsing stackoverflow Styling: Detected unknown tag: " + this.parser.getText());
			}

			// In this way the loop always starts with a tag name
			token = this.parser.nextToken();
		}

		return styling;
	}

	private List<RelatedSite> _parseRelatedSites() throws IOException {
		List<RelatedSite>	relatedSites;
		RelatedSite			relatedSite;
		JsonToken			token;

		relatedSites	= new ArrayList<>();

		token				= this.parser.nextToken();

		if (!JsonToken.START_ARRAY.equals(token)) {
			throw new IOException("Error while parsing stackoverflow: RelatedSite List does not start with [");
		}

		token = this.parser.nextToken();

		// Start parsing content
		while (!token.equals(JsonToken.END_ARRAY)) {
			relatedSite = new RelatedSite();

			if (!JsonToken.START_OBJECT.equals(token)) {
				throw new IOException("Error while parsing stackoverflow: RelatedSite does not start with {");
			}

			token = this.parser.nextToken();

			while (!token.equals(JsonToken.END_OBJECT)) {

				switch (this.parser.getText()) {

				case SOKey.API_SITE_PARAMETER:
					relatedSite.setApiSiteParameter(this._parseString());
					break;
				case SOKey.SITE_URL:
					relatedSite.setSiteUrl(this._parseString());
					break;
				case SOKey.NAME:
					relatedSite.setName(this._parseString());
					break;

				case SOKey.RELATION:
					relatedSite.setRelation(this._parseString());
					break;

				default:
					throw new IOException(
							"Error while parsing stackoverflow RelatedSites: Detected unknown tag: " + this.parser.getText());
				}

				// In this way the loop always starts with a tag name
				token = this.parser.nextToken();
			}

			relatedSites.add(relatedSite);

			token = this.parser.nextToken();
		}

		return relatedSites;
	}

	private List<String> _parseStringArray() throws IOException {
		List<String>	strings;
		JsonToken		token;

		strings	= new ArrayList<>();

		token		= this.parser.nextToken();

		if (!JsonToken.START_ARRAY.equals(token)) {
			throw new IOException("Error while parsing stackoverflow: String Array does not start with [");
		}

		token = this.parser.nextToken();

		// Start parsing content
		while (!token.equals(JsonToken.END_ARRAY)) {
			// Here it's not the general string parsing method because its an array of
			// strings
			strings.add(this.parser.getValueAsString());

			token = this.parser.nextToken();
		}

		return strings;
	}

	private int _parseInt() throws IOException {
		int result;

		this.parser.nextToken();

		result = this.parser.getValueAsInt(Integer.MIN_VALUE);

		if (result == Integer.MIN_VALUE) {
			throw new IOException(
					"Error while parsing stackoverflow: Tried parsing " + this.parser.getText() + " as an int!");
		}

		return result;
	}

	private String _parseString() throws IOException {
		String result;

		this.parser.nextToken();

		result = this.parser.getValueAsString();

		if (result == null) {
			throw new IOException(
					"Error while parsing stackoverflow: Tried parsing " + this.parser.getText() + " as a String!");
		}

		return result;
	}

	private boolean _parseBool() throws IOException {
		JsonToken	token;
		boolean		result;

		token = this.parser.nextToken();

		if (!token.isBoolean()) {
			throw new IOException(
					"Error while parsing stackoverflow: Tried parsing " + this.parser.getText() + " as a bool!");
		}

		result = this.parser.getValueAsBoolean();

		return result;
	}

	private Date _parseDate() throws IOException {
		Date result;

		this.parser.nextToken();

		result = DateHelper.toDate(this.parser.getValueAsInt(Integer.MIN_VALUE));

		return result;
	}
}
