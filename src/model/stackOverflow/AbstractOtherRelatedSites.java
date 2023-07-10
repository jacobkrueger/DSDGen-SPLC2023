package model.stackOverflow;

import keys.SOKey;

/**
 * Contains objects for other sites and related sites from stack overflow.
 * 
 * @author Alex Mikulinski
 * @since Sep 25, 2018
 */
public abstract class AbstractOtherRelatedSites {
	/** {@link SOKey#API_SITE_PARAMETER} */
	private String	apiSiteParameter;
	/** {@link SOKey#SITE_URL} */
	private String	siteUrl;
	/** {@link SOKey#NAME} */
	private String	name;

	/**
	 * Returns the {@link #apiSiteParameter} of current
	 * {@link AbstractOtherRelatedSites}.
	 *
	 * @return The {@link #apiSiteParameter} as {@link String}.
	 */
	public String getApiSiteParameter() {
		return this.apiSiteParameter;
	}

	/**
	 * Sets the {@link #apiSiteParameter} of current
	 * {@link AbstractOtherRelatedSites}.
	 *
	 * @param apiSiteParameter
	 *           The {@link #apiSiteParameter} to set as {@link String}.
	 */
	public void setApiSiteParameter(String apiSiteParameter) {
		this.apiSiteParameter = apiSiteParameter;
	}

	/**
	 * Returns the {@link #siteUrl} of current {@link AbstractOtherRelatedSites}.
	 *
	 * @return The {@link #siteUrl} as {@link String}.
	 */
	public String getSiteUrl() {
		return this.siteUrl;
	}

	/**
	 * Sets the {@link #siteUrl} of current {@link AbstractOtherRelatedSites}.
	 *
	 * @param siteUrl
	 *           The {@link #siteUrl} to set as {@link String}.
	 */
	public void setSiteUrl(String siteUrl) {
		this.siteUrl = siteUrl;
	}

	/**
	 * Returns the {@link #name} of current {@link AbstractOtherRelatedSites}.
	 *
	 * @return The {@link #name} as {@link String}.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the {@link #name} of current {@link AbstractOtherRelatedSites}.
	 *
	 * @param name
	 *           The {@link #name} to set as {@link String}.
	 */
	public void setName(String name) {
		this.name = name;
	}
}
