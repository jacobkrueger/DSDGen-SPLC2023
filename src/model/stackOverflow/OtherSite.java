package model.stackOverflow;

import java.util.Date;
import java.util.List;

import keys.SOKey;

/**
 * Contains objects for other site from stack overflow.
 * 
 * @author Alex Mikulinski
 * @since Sep 25, 2018
 */
public class OtherSite extends AbstractOtherRelatedSites {
	/** {@link SOKey#HIGH_RESOLUTION_ICON_URL} */
	private String					highResolutionIconUrl;
	/** {@link SOKey#MARKDOWN_EXTENSIONS} */
	private List<String>			markdownExtensions;
	/** {@link SOKey#CLOSED_BETA_DATE} */
	private Date					closedBetaDate;
	/** {@link SOKey#TWITTER_ACCOUNT} */
	private String					twitterAccount;
	/** {@link SOKey#OPEN_BETA_DATE} */
	private Date					openBetaDate;
	/** {@link SOKey#RELATED_SITES} */
	private List<RelatedSite>	relatedSites;
	/** {@link SOKey#LAUNCH_DATE} */
	private Date					launchDate;
	/** {@link SOKey#FAVICON_URL} */
	private String					faviconUrl;
	/** {@link SOKey#SITE_STATE} */
	private String					siteState;
	/** {@link SOKey#SITE_TYPE} */
	private String					siteType;
	/** {@link SOKey#AUDIENCE} */
	private String					audience;
	/** {@link SOKey#ICON_URL} */
	private String					iconUrl;
	/** {@link SOKey#LOGO_URL} */
	private String					logoUrl;
	/** {@link SOKey#ALIASES} */
	private List<String>			aliases;
	/** {@link SOKey#STYLING} */
	private Styling				styling;

	/**
	 * Returns the {@link #highResolutionIconUrl} of current {@link OtherSite}.
	 *
	 * @return The {@link #highResolutionIconUrl} as {@link String}.
	 */
	public String getHighResolutionIconUrl() {
		return this.highResolutionIconUrl;
	}

	/**
	 * Sets the {@link #highResolutionIconUrl} of current {@link OtherSite}.
	 *
	 * @param highResolutionIconUrl
	 *           The {@link #highResolutionIconUrl} to set as {@link String}.
	 */
	public void setHighResolutionIconUrl(String highResolutionIconUrl) {
		this.highResolutionIconUrl = highResolutionIconUrl;
	}

	/**
	 * Returns the {@link #markdownExtensions} of current {@link OtherSite}.
	 *
	 * @return The {@link #markdownExtensions} as {@link List}.
	 */
	public List<String> getMarkdownExtensions() {
		return this.markdownExtensions;
	}

	/**
	 * Sets the {@link #markdownExtensions} of current {@link OtherSite}.
	 *
	 * @param markdownExtensions
	 *           The {@link #markdownExtensions} to set as {@link List}.
	 */
	public void setMarkdownExtensions(List<String> markdownExtensions) {
		this.markdownExtensions = markdownExtensions;
	}

	/**
	 * Returns the {@link #closedBetaDate} of current {@link OtherSite}.
	 *
	 * @return The {@link #closedBetaDate} as {@link Date}.
	 */
	public Date getClosedBetaDate() {
		return this.closedBetaDate;
	}

	/**
	 * Sets the {@link #closedBetaDate} of current {@link OtherSite}.
	 *
	 * @param closedBetaDate
	 *           The {@link #closedBetaDate} to set as {@link Date}.
	 */
	public void setClosedBetaDate(Date closedBetaDate) {
		this.closedBetaDate = closedBetaDate;
	}

	/**
	 * Returns the {@link #twitterAccount} of current {@link OtherSite}.
	 *
	 * @return The {@link #twitterAccount} as {@link String}.
	 */
	public String getTwitterAccount() {
		return this.twitterAccount;
	}

	/**
	 * Sets the {@link #twitterAccount} of current {@link OtherSite}.
	 *
	 * @param twitterAccount
	 *           The {@link #twitterAccount} to set as {@link String}.
	 */
	public void setTwitterAccount(String twitterAccount) {
		this.twitterAccount = twitterAccount;
	}

	/**
	 * Returns the {@link #openBetaDate} of current {@link OtherSite}.
	 *
	 * @return The {@link #openBetaDate} as {@link Date}.
	 */
	public Date getOpenBetaDate() {
		return this.openBetaDate;
	}

	/**
	 * Sets the {@link #openBetaDate} of current {@link OtherSite}.
	 *
	 * @param openBetaDate
	 *           The {@link #openBetaDate} to set as {@link Date}.
	 */
	public void setOpenBetaDate(Date openBetaDate) {
		this.openBetaDate = openBetaDate;
	}

	/**
	 * Returns the {@link #relatedSites} of current {@link OtherSite}.
	 *
	 * @return The {@link #relatedSites} as {@link List}.
	 */
	public List<RelatedSite> getRelatedSites() {
		return this.relatedSites;
	}

	/**
	 * Sets the {@link #relatedSites} of current {@link OtherSite}.
	 *
	 * @param relatedSites
	 *           The {@link #relatedSites} to set as {@link List}.
	 */
	public void setRelatedSites(List<RelatedSite> relatedSites) {
		this.relatedSites = relatedSites;
	}

	/**
	 * Returns the {@link #launchDate} of current {@link OtherSite}.
	 *
	 * @return The {@link #launchDate} as {@link Date}.
	 */
	public Date getLaunchDate() {
		return this.launchDate;
	}

	/**
	 * Sets the {@link #launchDate} of current {@link OtherSite}.
	 *
	 * @param launchDate
	 *           The {@link #launchDate} to set as {@link Date}.
	 */
	public void setLaunchDate(Date launchDate) {
		this.launchDate = launchDate;
	}

	/**
	 * Returns the {@link #faviconUrl} of current {@link OtherSite}.
	 *
	 * @return The {@link #faviconUrl} as {@link String}.
	 */
	public String getFaviconUrl() {
		return this.faviconUrl;
	}

	/**
	 * Sets the {@link #faviconUrl} of current {@link OtherSite}.
	 *
	 * @param faviconUrl
	 *           The {@link #faviconUrl} to set as {@link String}.
	 */
	public void setFaviconUrl(String faviconUrl) {
		this.faviconUrl = faviconUrl;
	}

	/**
	 * Returns the {@link #siteState} of current {@link OtherSite}.
	 *
	 * @return The {@link #siteState} as {@link String}.
	 */
	public String getSiteState() {
		return this.siteState;
	}

	/**
	 * Sets the {@link #siteState} of current {@link OtherSite}.
	 *
	 * @param siteState
	 *           The {@link #siteState} to set as {@link String}.
	 */
	public void setSiteState(String siteState) {
		this.siteState = siteState;
	}

	/**
	 * Returns the {@link #siteType} of current {@link OtherSite}.
	 *
	 * @return The {@link #siteType} as {@link String}.
	 */
	public String getSiteType() {
		return this.siteType;
	}

	/**
	 * Sets the {@link #siteType} of current {@link OtherSite}.
	 *
	 * @param siteType
	 *           The {@link #siteType} to set as {@link String}.
	 */
	public void setSiteType(String siteType) {
		this.siteType = siteType;
	}

	/**
	 * Returns the {@link #audience} of current {@link OtherSite}.
	 *
	 * @return The {@link #audience} as {@link String}.
	 */
	public String getAudience() {
		return this.audience;
	}

	/**
	 * Sets the {@link #audience} of current {@link OtherSite}.
	 *
	 * @param audience
	 *           The {@link #audience} to set as {@link String}.
	 */
	public void setAudience(String audience) {
		this.audience = audience;
	}

	/**
	 * Returns the {@link #iconUrl} of current {@link OtherSite}.
	 *
	 * @return The {@link #iconUrl} as {@link String}.
	 */
	public String getIconUrl() {
		return this.iconUrl;
	}

	/**
	 * Sets the {@link #iconUrl} of current {@link OtherSite}.
	 *
	 * @param iconUrl
	 *           The {@link #iconUrl} to set as {@link String}.
	 */
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	/**
	 * Returns the {@link #logoUrl} of current {@link OtherSite}.
	 *
	 * @return The {@link #logoUrl} as {@link String}.
	 */
	public String getLogoUrl() {
		return this.logoUrl;
	}

	/**
	 * Sets the {@link #logoUrl} of current {@link OtherSite}.
	 *
	 * @param logoUrl
	 *           The {@link #logoUrl} to set as {@link String}.
	 */
	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	/**
	 * Returns the {@link #aliases} of current {@link OtherSite}.
	 *
	 * @return The {@link #aliases} as {@link List}.
	 */
	public List<String> getAliases() {
		return this.aliases;
	}

	/**
	 * Sets the {@link #aliases} of current {@link OtherSite}.
	 *
	 * @param aliases
	 *           The {@link #aliases} to set as {@link List}.
	 */
	public void setAliases(List<String> aliases) {
		this.aliases = aliases;
	}

	/**
	 * Returns the {@link #styling} of current {@link OtherSite}.
	 *
	 * @return The {@link #styling} as {@link Styling}.
	 */
	public Styling getStyling() {
		return this.styling;
	}

	/**
	 * Sets the {@link #styling} of current {@link OtherSite}.
	 *
	 * @param styling
	 *           The {@link #styling} to set as {@link Styling}.
	 */
	public void setStyling(Styling styling) {
		this.styling = styling;
	}
}
