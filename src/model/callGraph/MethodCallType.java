package model.callGraph;

/**
 * Contains the types of method calls.
 * <ul>
 * <li>M for invokevirtual calls.</li>
 * <li>I for invokeinterface calls.</li>
 * <li>O for invokespecial calls.</li>
 * <li>S for invokestatic calls.</li>
 * <li>D for invokedynamic calls.</li>
 * <li>EMPTY when only the class itself is called.</li>
 * </ul>
 * 
 * @author Alex Mikulinski
 * @since Jun 28, 2018
 */
public enum MethodCallType {
	/** For invokespecial calls. */
	M,
	/** For invokeinterface calls. */
	I,
	/** For invokespecial calls. */
	O,
	/** For invokestatic calls. */
	S,
	/** For invokedynamic calls. */
	D,
	/** When only the class itself is called. */
	EMPTY
}
