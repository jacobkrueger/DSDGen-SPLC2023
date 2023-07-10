package keys;

import model.callGraph.MethodCallType;

/**
 * Contains column names for a .csv file holding all calls.
 * 
 * @author Alex Mikulinski
 * @since Jun 17, 2018
 */
public interface CallKey {

	/** Call between (C)lasses or (M)ethods. */
	String	CALL_TYPE			= "call_type";

	/** The <b>F</b>ully <b>Q</b>ualified <b>N</b>ame (FQN) of the caller. */
	String	CALLER				= "caller";

	/** Contains the types of method calls. See: {@link MethodCallType}. */
	String	METHOD_CALL_TYPE	= "method_call_type";

	/** The <b>F</b>ully <b>Q</b>ualified <b>N</b>ame (FQN) of the callee. */
	String	CALLEE				= "callee";
}
