package keys;

/**
 * Contains column names for a .csv file with the inheritance hierarchy of the
 * examined project.
 * 
 * @author Alex Mikulinski
 * @since Jul 5, 2018
 */
public interface InheritanceKey {
	/**
	 * Sequential number.
	 */
	String	RECORD_ID			= "record_id";

	/**
	 * The parent class.
	 */
	String	PARENT_CLASS		= "parent_class";

	/**
	 * The child class.
	 */
	String	CHILD_CLASS			= "child_class";

	/**
	 * The type of relationship between classes, i.e., the child class 'extends' or
	 * 'implements' the parent class.
	 */
	String	RELATIONSHIP_TYPE	= "relationship_type";
}
