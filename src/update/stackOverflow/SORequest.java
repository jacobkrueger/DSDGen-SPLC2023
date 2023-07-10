package update.stackOverflow;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;

/**
 * Provides a structure for a request to the stackexchange.com api.
 *
 * @see <a href="https://api.stackexchange.com/docs">StackExchange</a>
 * @author Alex Mikulinski
 * @since Jan 11, 2019
 */
public class SORequest {
	private String	pagesize;
	private String	mainURL;
	private String	version;
	private String	tagged;
	private String	order;
	private String	type;
	private String	site;
	private String	sort;
	private String	page;
	private String	filter;

	/**
	 * Creates a new instance of {@link SORequest} with
	 * <ul>
	 * <li>{@link #pagesize} set to "100"</li>
	 * <li>{@link #mainURL} set to "http://api.stackexchange.com"</li>
	 * <li>{@link #version} set to "2.2"</li>
	 * <li>{@link #type} set to "questions"</li>
	 * <li>{@link #site} set to "stackoverflow"</li>
	 * <li>{@link #tagged} set to the passed tags</li>
	 * </ul>
	 *
	 * @param _tags
	 *           A list of tags with ";" as delimiter as {@link String}.
	 *           <p>
	 *           Possible tags can be found at
	 *           <a href="stackoverflow.com/tags">Stack Overflow</a>.
	 */
	public SORequest(String _tags) {
		this.setPagesize("100");
		this.setMainURL("http://api.stackexchange.com");
		this.setVersion("2.2");
		this.setType("questions");
		this.setSite("stackoverflow");
		this.setTagged(_tags);
	}

	/**
	 * @return An {@link URL} for this {@link SORequest} out of all parameters.
	 * @throws MalformedURLException
	 *            If the {@link URL} ist malformed. (e.g. wrong arguments were
	 *            passed)
	 */
	public URL getURL() throws MalformedURLException {
		StringBuilder builder;

		builder = new StringBuilder();

		builder.append(this.mainURL);
		builder.append("/");
		builder.append(this.version);
		builder.append("/");
		builder.append(this.type);
		builder.append("?");
		builder.append("site=");
		builder.append(this.site);

		if (StringUtils.isNotBlank(this.pagesize)) {
			builder.append("&");
			builder.append("pagesize=");
			builder.append(this.pagesize);
		}

		if (StringUtils.isNotBlank(this.tagged)) {
			builder.append("&");
			builder.append("tagged=");
			builder.append(this.tagged);
		}

		if (StringUtils.isNotBlank(this.order)) {
			builder.append("&");
			builder.append("order=");
			builder.append(this.order);
		}
		if (StringUtils.isNotBlank(this.sort)) {
			builder.append("&");
			builder.append("sort=");
			builder.append(this.sort);
		}
		if (StringUtils.isNotBlank(this.page)) {
			builder.append("&");
			builder.append("page=");
			builder.append(this.page);
		}

		if (StringUtils.isNotBlank(this.filter)) {
			builder.append("&");
			builder.append("filter=");
			builder.append(this.filter);
		}
		return new URL(builder.toString());
	}

	/**
	 * Returns the {@link #pagesize} of current {@link SORequest}.
	 *
	 * @return The {@link #pagesize} as {@link String}.
	 */
	public String getPagesize() {
		return this.pagesize;
	}

	/**
	 * Sets the {@link #pagesize} of current {@link SORequest}.
	 *
	 * @param pagesize
	 *           The {@link #pagesize} to set as int.
	 */
	public void setPagesize(int pagesize) {
		this.pagesize = Integer.toString(pagesize);
	}

	/**
	 * Sets the {@link #pagesize} of current {@link SORequest}.
	 *
	 * @param pagesize
	 *           The {@link #pagesize} to set as {@link String}.
	 */
	public void setPagesize(String pagesize) {
		this.pagesize = pagesize;
	}

	/**
	 * Returns the {@link #mainURL} of current {@link SORequest}.
	 *
	 * @return The {@link #mainURL} as {@link String}.
	 */
	public String getMainURL() {
		return this.mainURL;
	}

	/**
	 * Sets the {@link #mainURL} of current {@link SORequest}.
	 *
	 * @param mainURL
	 *           The {@link #mainURL} to set as {@link String}.
	 */
	public void setMainURL(String mainURL) {
		if (mainURL.isEmpty()) {
			throw new IllegalArgumentException("The mainURL cannot be \"\"");
		}

		this.mainURL = mainURL;
	}

	/**
	 * Returns the {@link #version} of current {@link SORequest}.
	 *
	 * @return The {@link #version} as {@link String}.
	 */
	public String getVersion() {
		return this.version;
	}

	/**
	 * Sets the {@link #version} of current {@link SORequest}.
	 *
	 * @param version
	 *           The {@link #version} to set as {@link String}.
	 */
	public void setVersion(String version) {
		if (version.isEmpty()) {
			throw new IllegalArgumentException("The version cannot be \"\"");
		}

		this.version = version;
	}

	/**
	 * Returns the {@link #tagged} of current {@link SORequest}.
	 *
	 * @return The {@link #tagged} as {@link String}.
	 */
	public String getTagged() {
		return this.tagged;
	}

	/**
	 * Sets the {@link #tagged} of current {@link SORequest}.
	 *
	 * @param tagged
	 *           The {@link #tagged} to set as {@link String}.
	 */
	public void setTagged(String tagged) {
		this.tagged = tagged;
	}

	/**
	 * Returns the {@link #order} of current {@link SORequest}.
	 *
	 * @return The {@link #order} as {@link String}.
	 */
	public String getOrder() {
		return this.order;
	}

	/**
	 * Sets the {@link #order} of current {@link SORequest}.
	 *
	 * @param order
	 *           The {@link #order} to set as {@link String}.
	 */
	public void setOrder(String order) {
		this.order = order;
	}

	/**
	 * Returns the {@link #type} of current {@link SORequest}.
	 *
	 * @return The {@link #type} as {@link String}.
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * Sets the {@link #type} of current {@link SORequest}.
	 *
	 * @param type
	 *           The {@link #type} to set as {@link String}.
	 */
	public void setType(String type) {
		if (type.isEmpty()) {
			throw new IllegalArgumentException("The type cannot be \"\"");
		}

		this.type = type;
	}

	/**
	 * Returns the {@link #site} of current {@link SORequest}.
	 *
	 * @return The {@link #site} as {@link String}.
	 */
	public String getSite() {
		return this.site;
	}

	/**
	 * Sets the {@link #site} of current {@link SORequest}.
	 *
	 * @param site
	 *           The {@link #site} to set as {@link String}.
	 */
	public void setSite(String site) {
		if (site.isEmpty()) {
			throw new IllegalArgumentException("The site cannot be \"\"");
		}

		this.site = site;
	}

	/**
	 * Returns the {@link #sort} of current {@link SORequest}.
	 *
	 * @return The {@link #sort} as {@link String}.
	 */
	public String getSort() {
		return this.sort;
	}

	/**
	 * Sets the {@link #sort} of current {@link SORequest}.
	 *
	 * @param sort
	 *           The {@link #sort} to set as {@link String}.
	 */
	public void setSort(String sort) {
		this.sort = sort;
	}

	/**
	 * Returns the {@link #page} of current {@link SORequest}.
	 *
	 * @return The {@link #page} as {@link String}.
	 */
	public String getPage() {
		return this.page;
	}

	/**
	 * Sets the {@link #page} of current {@link SORequest}.
	 *
	 * @param page
	 *           The {@link #page} to set as {@link String}.
	 */
	public void setPage(String page) {
		this.page = page;
	}

	/**
	 * Sets the {@link #page} of current {@link SORequest}.
	 *
	 * @param page
	 *           The {@link #page} to set as int.
	 */
	public void setPage(int page) {
		this.page = Integer.toString(page);
	}

	/**
	 * Returns the {@link #filter} of current {@link SORequest}.
	 *
	 * @return The {@link #filter} as {@link String}.
	 */
	public String getFilter() {
		return this.filter;
	}

	/**
	 * Sets the {@link #filter} of current {@link SORequest}.
	 *
	 * @param filter
	 *           The {@link #filter} to set as {@link String}.
	 */
	public void setFilter(String filter) {
		this.filter = filter;
	}

}
