package model;

import keys.InheritanceKey;

/**
 * Provides basic structure to hold one inheritance relationship.
 * 
 * @author Alex Mikulinski
 * @since Jul 5, 2018
 */
public class Inheritance {
	/** {@link InheritanceKey#RECORD_ID} */
	private String	record_id;
	/** {@link InheritanceKey#PARENT_CLASS} */
	private String	parent_class;
	/** {@link InheritanceKey#CHILD_CLASS} */
	private String	child_class;
	/** {@link InheritanceKey#RELATIONSHIP_TYPE} */
	private String	relationship_type;

	/**
	 * Returns the {@link #record_id} of current {@link Inheritance}.
	 *
	 * @return The record_id as {@link String}.
	 */
	public String getRecord_id() {
		return this.record_id;
	}

	/**
	 * @param record_id
	 *           The record_id to set.
	 */
	public void setRecord_id(String record_id) {
		this.record_id = record_id;
	}

	/**
	 * Returns the {@link #parent_class} of current {@link Inheritance}.
	 *
	 * @return The parent_class as {@link String}.
	 */
	public String getParent_class() {
		return this.parent_class;
	}

	/**
	 * @param parent_class
	 *           The parent_class to set.
	 */
	public void setParent_class(String parent_class) {
		this.parent_class = parent_class;
	}

	/**
	 * Returns the {@link #child_class} of current {@link Inheritance}.
	 *
	 * @return The child_class as {@link String}.
	 */
	public String getChild_class() {
		return this.child_class;
	}

	/**
	 * @param child_class
	 *           The child_class to set.
	 */
	public void setChild_class(String child_class) {
		this.child_class = child_class;
	}

	/**
	 * Returns the {@link #relationship_type} of current {@link Inheritance}.
	 *
	 * @return The relationship_type as {@link String}.
	 */
	public String getRelationship_type() {
		return this.relationship_type;
	}

	/**
	 * @param relationship_type
	 *           The relationship_type to set.
	 */
	public void setRelationship_type(String relationship_type) {
		this.relationship_type = relationship_type;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Inheritance [");
		if (this.record_id != null) {
			builder.append("record_id=");
			builder.append(this.record_id);
			builder.append(", ");
		}
		if (this.parent_class != null) {
			builder.append("parent_class=");
			builder.append(this.parent_class);
			builder.append(", ");
		}
		if (this.child_class != null) {
			builder.append("child_class=");
			builder.append(this.child_class);
			builder.append(", ");
		}
		if (this.relationship_type != null) {
			builder.append("relationship_type=");
			builder.append(this.relationship_type);
		}
		builder.append("]\n");
		return builder.toString();
	}

}
