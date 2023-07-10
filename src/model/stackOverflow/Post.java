package model.stackOverflow;

import java.util.Date;
import java.util.List;

import keys.SOKey;

/**
 * Contains objects for posts from stack overflow.
 * 
 * @author Alex Mikulinski
 * @since Jul 9, 2018
 */
public class Post extends AbstractPostAnswer {
	/**
	 * Using some methods from original question because they are used in posts
	 * also.
	 */
	private OriginalQuestion	originalQuestion;

	/** {@link SOKey#DELETE_VOTE_COUNT} */
	private int						deleteVoteCount;
	/** {@link SOKey#REOPEN_VOTE_COUNT} */
	private int						reopenVoteCount;
	/** {@link SOKey#CLOSE_VOTE_COUNT} */
	private int						closeVoteCount;
	/** {@link SOKey#FAVORITE_COUNT} */
	private int						favoriteCount;
	/** {@link SOKey#PROTECTED_DATE} */
	private Date					protectedDate;
	/** {@link SOKey#CLOSED_DETAILS} */
	private ClosedDetail			closedDetail;
	/** {@link SOKey#MIGRATED_FROM} */
	private MigratedFrom			migratedFrom;
	/** {@link SOKey#CLOSED_REASON} */
	private String					closedReason;
	/** {@link SOKey#CLOSED_DATE} */
	private Date					closedDate;
	/** {@link SOKey#IS_ANSWERED} */
	private boolean				isAnswered;
	/** {@link SOKey#VIEW_COUNT} */
	private int						viewCount;
	private List<Answer>			answers;

	/**
	 * Creates a new instance of {@link Post}.
	 */
	public Post() {
		this.originalQuestion = new OriginalQuestion();

	}

	/** {@link OriginalQuestion#getAcceptedAnswerId()} */
	@SuppressWarnings("javadoc")
	public int getAcceptedAnswerId() {
		return this.originalQuestion.getAcceptedAnswerId();
	}

	/** {@link OriginalQuestion#setAcceptedAnswerId(int)} */
	@SuppressWarnings("javadoc")
	public void setAcceptedAnswerId(int acceptedAnswerId) {
		this.originalQuestion.setAcceptedAnswerId(acceptedAnswerId);
	}

	/** {@link OriginalQuestion#getAnswerCount()} */
	@SuppressWarnings("javadoc")
	public int getAnswerCount() {
		return this.originalQuestion.getAnswerCount();
	}

	/** {@link OriginalQuestion#setAnswerCount(int)} */
	@SuppressWarnings("javadoc")
	public void setAnswerCount(int answerCount) {
		this.originalQuestion.setAnswerCount(answerCount);
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
	 * Returns the {@link #deleteVoteCount} of current {@link Post}.
	 *
	 * @return The deleteVoteCount as int.
	 */
	public int getDeleteVoteCount() {
		return this.deleteVoteCount;
	}

	/**
	 * Sets the {@link #deleteVoteCount} of current {@link Post}.
	 *
	 * @param deleteVoteCount
	 *           The deleteVoteCount to set as int.
	 */
	public void setDeleteVoteCount(int deleteVoteCount) {
		this.deleteVoteCount = deleteVoteCount;
	}

	/**
	 * Returns the {@link #reopenVoteCount} of current {@link Post}.
	 *
	 * @return The reopenVoteCount as int.
	 */
	public int getReopenVoteCount() {
		return this.reopenVoteCount;
	}

	/**
	 * Sets the {@link #reopenVoteCount} of current {@link Post}.
	 *
	 * @param reopenVoteCount
	 *           The reopenVoteCount to set as int.
	 */
	public void setReopenVoteCount(int reopenVoteCount) {
		this.reopenVoteCount = reopenVoteCount;
	}

	/**
	 * Returns the {@link #closeVoteCount} of current {@link Post}.
	 *
	 * @return The closeVoteCount as int.
	 */
	public int getCloseVoteCount() {
		return this.closeVoteCount;
	}

	/**
	 * Sets the {@link #closeVoteCount} of current {@link Post}.
	 *
	 * @param closeVoteCount
	 *           The closeVoteCount to set as int.
	 */
	public void setCloseVoteCount(int closeVoteCount) {
		this.closeVoteCount = closeVoteCount;
	}

	/**
	 * Returns the {@link #favoriteCount} of current {@link Post}.
	 *
	 * @return The favoriteCount as int.
	 */
	public int getFavoriteCount() {
		return this.favoriteCount;
	}

	/**
	 * Sets the {@link #favoriteCount} of current {@link Post}.
	 *
	 * @param favoriteCount
	 *           The favoriteCount to set as int.
	 */
	public void setFavoriteCount(int favoriteCount) {
		this.favoriteCount = favoriteCount;
	}

	/**
	 * Returns the {@link #protectedDate} of current {@link Post}.
	 *
	 * @return The {@link #protectedDate} as {@link Date}.
	 */
	public Date getProtectedDate() {
		return this.protectedDate;
	}

	/**
	 * Sets the {@link #protectedDate} of current {@link Post}.
	 *
	 * @param protectedDate
	 *           The {@link #protectedDate} to set as {@link Date}.
	 */
	public void setProtectedDate(Date protectedDate) {
		this.protectedDate = protectedDate;
	}

	/**
	 * Returns the {@link #closedDetail} of current {@link Post}.
	 *
	 * @return The closedDetail as {@link ClosedDetail}.
	 */
	public ClosedDetail getClosedDetail() {
		return this.closedDetail;
	}

	/**
	 * Sets the {@link #closedDetail} of current {@link Post}.
	 *
	 * @param closedDetail
	 *           The closedDetail to set as {@link ClosedDetail}.
	 */
	public void setClosedDetail(ClosedDetail closedDetail) {
		this.closedDetail = closedDetail;
	}

	/**
	 * Returns the {@link #migratedFrom} of current {@link Post}.
	 *
	 * @return The {@link #migratedFrom} as {@link MigratedFrom}.
	 */
	public MigratedFrom getMigratedFrom() {
		return this.migratedFrom;
	}

	/**
	 * Sets the {@link #migratedFrom} of current {@link Post}.
	 *
	 * @param migratedFrom
	 *           The {@link #migratedFrom} to set as {@link MigratedFrom}.
	 */
	public void setMigratedFrom(MigratedFrom migratedFrom) {
		this.migratedFrom = migratedFrom;
	}

	/**
	 * Returns the {@link #closedReason} of current {@link Post}.
	 *
	 * @return The closedReason as {@link String}.
	 */
	public String getClosedReason() {
		return this.closedReason;
	}

	/**
	 * Sets the {@link #closedReason} of current {@link Post}.
	 *
	 * @param closedReason
	 *           The closedReason to set as {@link String}.
	 */
	public void setClosedReason(String closedReason) {
		this.closedReason = closedReason;
	}

	/**
	 * Returns the {@link #closedDate} of current {@link Post}.
	 *
	 * @return The closedDate as {@link Date}.
	 */
	public Date getClosedDate() {
		return this.closedDate;
	}

	/**
	 * Sets the {@link #closedDate} of current {@link Post}.
	 *
	 * @param closedDate
	 *           The closedDate to set as {@link Date}.
	 */
	public void setClosedDate(Date closedDate) {
		this.closedDate = closedDate;
	}

	/**
	 * Returns the {@link #isAnswered} of current {@link Post}.
	 *
	 * @return The isAnswered as boolean.
	 */
	public boolean isAnswered() {
		return this.isAnswered;
	}

	/**
	 * Sets the {@link #isAnswered} of current {@link Post}.
	 *
	 * @param isAnswered
	 *           The isAnswered to set as boolean.
	 */
	public void setAnswered(boolean isAnswered) {
		this.isAnswered = isAnswered;
	}

	/**
	 * Returns the {@link #viewCount} of current {@link Post}.
	 *
	 * @return The viewCount as int.
	 */
	public int getViewCount() {
		return this.viewCount;
	}

	/**
	 * Sets the {@link #viewCount} of current {@link Post}.
	 *
	 * @param viewCount
	 *           The viewCount to set as int.
	 */
	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}

	/**
	 * Returns the {@link #answers} of current {@link Post}.
	 *
	 * @return The answers as {@link List}.
	 */
	public List<Answer> getAnswers() {
		return this.answers;
	}

	/**
	 * Sets the {@link #answers} of current {@link Post}.
	 *
	 * @param answers
	 *           The answers to set as {@link List}.
	 */
	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Post [deleteVoteCount=");
		builder.append(this.deleteVoteCount);
		builder.append(", reopenVoteCount=");
		builder.append(this.reopenVoteCount);
		builder.append(", closeVoteCount=");
		builder.append(this.closeVoteCount);
		builder.append(", favoriteCount=");
		builder.append(this.favoriteCount);
		builder.append(", ");
		if (this.protectedDate != null) {
			builder.append("protectedDate=");
			builder.append(this.protectedDate);
			builder.append(", ");
		}
		if (this.closedDetail != null) {
			builder.append("closedDetail=");
			builder.append(this.closedDetail);
			builder.append(", ");
		}
		if (this.migratedFrom != null) {
			builder.append("migratedFrom=");
			builder.append(this.migratedFrom);
			builder.append(", ");
		}
		if (this.closedReason != null) {
			builder.append("closedReason=");
			builder.append(this.closedReason);
			builder.append(", ");
		}
		if (this.closedDate != null) {
			builder.append("closedDate=");
			builder.append(this.closedDate);
			builder.append(", ");
		}
		builder.append("isAnswered=");
		builder.append(this.isAnswered);
		builder.append(", viewCount=");
		builder.append(this.viewCount);
		builder.append(", ");
		if (this.answers != null) {
			builder.append("answers=");
			builder.append(this.answers);
			builder.append(", ");
		}
		builder.append("AcceptedAnswerId=");
		builder.append(this.getAcceptedAnswerId());
		builder.append(", AnswerCount=");
		builder.append(this.getAnswerCount());
		builder.append(", QuestionId=");
		builder.append(this.getQuestionId());
		builder.append(", ");
		if (this.getTitle() != null) {
			builder.append("Title=");
			builder.append(this.getTitle());
			builder.append(", ");
		}
		if (this.getLastActivityDate() != null) {
			builder.append("LastActivityDate=");
			builder.append(this.getLastActivityDate());
			builder.append(", ");
		}
		builder.append("DownVoteCount=");
		builder.append(this.getDownVoteCount());
		builder.append(", UpVoteCount=");
		builder.append(this.getUpVoteCount());
		builder.append(", CommentCount=");
		builder.append(this.getCommentCount());
		builder.append(", ");
		if (this.getLastEditDate() != null) {
			builder.append("LastEditDate=");
			builder.append(this.getLastEditDate());
			builder.append(", ");
		}
		if (this.getLastEditor() != null) {
			builder.append("LastEditor=");
			builder.append(this.getLastEditor());
			builder.append(", ");
		}
		if (this.getShareLink() != null) {
			builder.append("ShareLink=");
			builder.append(this.getShareLink());
			builder.append(", ");
		}
		if (this.getComments() != null) {
			builder.append("Comments=");
			builder.append(this.getComments());
			builder.append(", ");
		}
		if (this.getTags() != null) {
			builder.append("Tags=");
			builder.append(this.getTags());
			builder.append(", ");
		}
		if (this.getBodyMarkdown() != null) {
			builder.append("BodyMarkdown=");
			builder.append(this.getBodyMarkdown());
			builder.append(", ");
		}
		if (this.getCreationDate() != null) {
			builder.append("CreationDate=");
			builder.append(this.getCreationDate());
			builder.append(", ");
		}
		if (this.getOwner() != null) {
			builder.append("Owner=");
			builder.append(this.getOwner());
			builder.append(", ");
		}
		builder.append("Score=");
		builder.append(this.getScore());
		builder.append(", ");
		if (this.getBody() != null) {
			builder.append("Body=");
			builder.append(this.getBody());
			builder.append(", ");
		}
		if (this.getLink() != null) {
			builder.append("Link=");
			builder.append(this.getLink());
		}
		builder.append("]");
		return builder.toString();
	}

}
