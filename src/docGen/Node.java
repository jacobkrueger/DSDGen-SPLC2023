package docGen;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Provides a structure for a node in a documentation graph.
 * 
 * @author Alex Mikulinski
 * @since Oct 8, 2018
 */
public class Node {
	private String	id;
	private String	label;
	private double	x;
	private double	y;
	private double	size;

	/**
	 * Creates a new instance of {@link Node}.
	 * 
	 * @param _id
	 *           The {@link #id} to set.
	 * @param _label
	 *           The {@link #label} to set.
	 * @param _x
	 *           The {@link #x} position to set.
	 * @param _y
	 *           The {@link #y} position to set.
	 * @param _size
	 *           The {@link #size} to set.
	 */
	public Node(String _id, String _label, double _x, double _y, double _size) {
		this.id		= _id;
		this.label	= _label;
		this.x		= _x;
		this.y		= _y;
		this.size	= _size;
	}

	/**
	 * @return This {@link Node} as {@link ObjectNode}.
	 */
	public ObjectNode toObjectNode() {
		ObjectMapper	mapper;
		ObjectNode		result;

		mapper	= new ObjectMapper();
		result	= mapper.createObjectNode();

		result.put("id", this.id);
		result.put("label", this.label);
		result.put("x", this.x);
		result.put("y", this.y);
		result.put("size", this.size);

		return result;
	}

	/**
	 * Returns the {@link #id} of current {@link Node}.
	 *
	 * @return The {@link #id} as {@link String}.
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * Sets the {@link #id} of current {@link Node}.
	 *
	 * @param id
	 *           The {@link #id} to set as {@link String}.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Returns the {@link #label} of current {@link Node}.
	 *
	 * @return The {@link #label} as {@link String}.
	 */
	public String getLabel() {
		return this.label;
	}

	/**
	 * Sets the {@link #label} of current {@link Node}.
	 *
	 * @param label
	 *           The {@link #label} to set as {@link String}.
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * Returns the {@link #x} of current {@link Node}.
	 *
	 * @return The {@link #x} as double.
	 */
	public double getX() {
		return this.x;
	}

	/**
	 * Sets the {@link #x} of current {@link Node}.
	 *
	 * @param x
	 *           The {@link #x} to set as double.
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * Returns the {@link #y} of current {@link Node}.
	 *
	 * @return The {@link #y} as double.
	 */
	public double getY() {
		return this.y;
	}

	/**
	 * Sets the {@link #y} of current {@link Node}.
	 *
	 * @param y
	 *           The {@link #y} to set as double.
	 */
	public void setY(double y) {
		this.y = y;
	}

	/**
	 * Returns the {@link #size} of current {@link Node}.
	 *
	 * @return The {@link #size} as double.
	 */
	public double getSize() {
		return this.size;
	}

	/**
	 * Sets the {@link #size} of current {@link Node}.
	 *
	 * @param size
	 *           The {@link #size} to set as double.
	 */
	public void setSize(double size) {
		this.size = size;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int	prime		= 31;
		int			result	= 1;
		result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Node)) {
			return false;
		}
		Node other = (Node) obj;
		if (this.id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!this.id.equals(other.id)) {
			return false;
		}
		return true;
	}
}
