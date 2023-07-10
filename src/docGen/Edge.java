package docGen;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Provides a structure for an edge in a documentation graph.
 * 
 * @author Alex Mikulinski
 * @since Oct 8, 2018
 */
public class Edge {
	private String	id;
	/** Id of the source {@link Node} for this {@link Edge}. */
	private String	source;
	/** Id of the target {@link Node} for this {@link Edge}. */
	private String	target;

	/**
	 * Creates a new instance of {@link Edge}.
	 * 
	 * @param _id
	 *           The {@link #id} to set.
	 * @param _source
	 *           The {@link #source} to set.
	 * @param _target
	 *           The {@link #source} to set.
	 */
	public Edge(String _id, String _source, String _target) {
		this.id		= _id;
		this.source	= _source;
		this.target	= _target;
	}

	/**
	 * @return This {@link Edge} as {@link ObjectNode}.
	 */
	public ObjectNode toObjectNode() {
		ObjectMapper	mapper;
		ObjectNode		result;

		mapper	= new ObjectMapper();
		result	= mapper.createObjectNode();

		result.put("id", this.id);
		result.put("source", this.source);
		result.put("target", this.target);

		return result;
	}

	/**
	 * Returns the {@link #id} of current {@link Edge}.
	 *
	 * @return The {@link #id} as {@link String}.
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * Sets the {@link #id} of current {@link Edge}.
	 *
	 * @param id
	 *           The {@link #id} to set as {@link String}.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Returns the {@link #source} of current {@link Edge}.
	 *
	 * @return The {@link #source} as {@link String}.
	 */
	public String getSource() {
		return this.source;
	}

	/**
	 * Sets the {@link #source} of current {@link Edge}.
	 *
	 * @param source
	 *           The {@link #source} to set as {@link String}.
	 */
	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * Returns the {@link #target} of current {@link Edge}.
	 *
	 * @return The {@link #target} as {@link String}.
	 */
	public String getTarget() {
		return this.target;
	}

	/**
	 * Sets the {@link #target} of current {@link Edge}.
	 *
	 * @param target
	 *           The {@link #target} to set as {@link String}.
	 */
	public void setTarget(String target) {
		this.target = target;
	}
}
