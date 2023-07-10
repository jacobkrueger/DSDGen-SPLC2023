package model.stackOverflow;

/**
 * Contains objects for related site from stack overflow.
 * 
 * @author Alex Mikulinski
 * @since Sep 25, 2018
 */
public class RelatedSite extends AbstractOtherRelatedSites {
	private String relation;

	/**
	 * Returns the {@link #relation} of current {@link RelatedSite}.
	 *
	 * @return The {@link #relation} as {@link String}.
	 */
	public String getRelation() {
		return this.relation;
	}

	/**
	 * Sets the {@link #relation} of current {@link RelatedSite}.
	 *
	 * @param relation
	 *           The {@link #relation} to set as {@link String}.
	 */
	public void setRelation(String relation) {
		this.relation = relation;
	}
}
