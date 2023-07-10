package parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import app.Settings;
import keys.CallKey;
import model.callGraph.Call;
import model.callGraph.MethodCallType;

/**
 * Parses a call graph from a .csv file with the call graph between methods and
 * between classes. Class A calls class B if there exists a call between amethod
 * of class A and a method of class B. The call graph needs to be produced by
 * the tool
 * 
 * @author Alex Mikulinski
 * @since Jun 28, 2018
 */
public class CallGraphParser {
	/**
	 * Contains 3 Types of {@link List}s of {@link Call}s:
	 * <ol>
	 * <li>Private Calls: Calls, where a class calls itself.</li>
	 * <li>Callees: Calls, where classes are called from this class.</li>
	 * <li>Callers: Calls with classes, which called this class.</li>
	 * </ol>
	 */
	private List<List<Call>> allCalls;

	/**
	 * Creates a new instance if {@link CallGraphParser}.
	 */
	public CallGraphParser() {
		List<Call>	privateCalls;
		List<Call>	callees;
		List<Call>	callers;

		privateCalls	= new ArrayList<>();
		callees			= new ArrayList<>();
		callers			= new ArrayList<>();
		this.allCalls	= new ArrayList<>(3);

		this.allCalls.add(privateCalls);
		this.allCalls.add(callees);
		this.allCalls.add(callers);
	}

	/**
	 * Parses all {@link Call}s from a .csv file in {@link Settings#CALL_GRAPH_PATH}
	 * and return them.
	 * 
	 * @param _fqn
	 *           The <b>F</b>ully <b>Q</b>ualified <b>N</b>ame for needed class.
	 * @return {@link #allCalls} which can contain two empty {@link List}s of
	 *         {@link Call}s if no {@link Call}s were found.
	 * @see <a href=
	 *      "https://github.com/FasterXML/jackson-dataformats-text/tree/master/csv">Jackson
	 *      CSV</a>
	 * @throws IOException
	 *            If parsing goes wrong.
	 */
	public List<List<Call>> parseCallsForClass(String _fqn) throws IOException {
		MappingIterator<Map<String, String>>	mappingIterator;
		Map<String, String>							rowAsMap;
		CsvSchema										schema;
		CsvMapper										mapper;
		String											fqn;
		File												csvFile;
		Call												call;
		int												suitableListNr;

		fqn					= _fqn;
		csvFile				= new File(Settings.CALL_GRAPH_PATH.get());
		mapper				= new CsvMapper();

		// Use first row as header.
		schema				= CsvSchema.emptySchema().withHeader();

		mappingIterator	= mapper.readerFor(Map.class).with(schema).readValues(csvFile);

		while (mappingIterator.hasNext()) {
			suitableListNr	= -1;
			rowAsMap			= mappingIterator.next();

			// Access by column name, as defined in the header row.
			// Check which list from allCalls needs to hold the call
			if (rowAsMap.get(CallKey.CALLER).contains(fqn) && rowAsMap.get(CallKey.CALLEE).contains(fqn)) {
				suitableListNr = 0;
			} else if (rowAsMap.get(CallKey.CALLER).contains(fqn)) {
				suitableListNr = 1;
			} else if (rowAsMap.get(CallKey.CALLEE).contains(fqn)) {
				suitableListNr = 2;
			}

			// If a call for this fqn was found, add it to the suitable list.
			if (suitableListNr != -1) {
				call	= new Call();
				call	= this._createCall(rowAsMap);
				if (!this.allCalls.get(suitableListNr).contains(call)) {
					this.allCalls.get(suitableListNr).add(call);
				}
			}
		}

		return this.allCalls;
	}

	/**
	 * Creates a {@link List} of <b>F</b>ully <b>Q</b>ualified <b>N</b>ames (FQNs)
	 * of example classes, which call the chosen class.
	 * 
	 * @param _callers
	 *           A {@link List} of callers.
	 * @return A {@link List} of FQNs.
	 */
	public List<String> getExampleClasses(List<Call> _callers) {
		List<String>	exampleClasses;
		List<Call>		callers;
		String			fqn;

		callers			= _callers;
		exampleClasses	= new ArrayList<>();
		for (Call call : callers) {
			if (call.getCaller().contains("examples")) {
				fqn = this._getClassFQN(call.getCaller());
				if (!exampleClasses.contains(fqn)) {
					exampleClasses.add(fqn);
				}
			}
		}

		return exampleClasses;
	}

	/**
	 * Extract the class <b>F</b>ully <b>Q</b>ualified <b>N</b>ame of a class from a
	 * call.
	 * 
	 * @param _caller
	 *           The caller.
	 * @return <b>F</b>ully <b>Q</b>ualified <b>N</b>ame for a class.
	 */
	private String _getClassFQN(String _caller) {
		String	caller;
		String	fqn;
		String	regex;

		caller = _caller;
		if (caller.contains("(")) {
			// Get fqn for class without method
			regex	= "(?i)(.*)\\.(.*?)(\\().*";
			fqn	= caller.replaceAll(regex, "$1");
		} else {
			fqn = caller;
		}

		return fqn;
	}

	/**
	 * Creates a new {@link Call} from a row of tags as a {@link Map}.
	 * 
	 * @param _rowAsMap
	 *           A row of {@link MethodCallType}s.
	 * @return A new {@link Call}.
	 */
	private Call _createCall(Map<String, String> _rowAsMap) {
		Map<String, String>	rowAsMap;
		Call						call;
		String					methodCallType;

		rowAsMap	= _rowAsMap;
		call		= new Call();

		call.setCall_type(rowAsMap.get(CallKey.CALL_TYPE));
		call.setCaller(rowAsMap.get(CallKey.CALLER));

		// Check whether only the class itself is called.
		methodCallType = rowAsMap.get(CallKey.METHOD_CALL_TYPE);
		if (methodCallType != "") {
			call.setMethod_call_type(MethodCallType.valueOf(methodCallType));
		} else {
			call.setMethod_call_type(MethodCallType.EMPTY);
		}

		call.setCallee(rowAsMap.get(CallKey.CALLEE));

		return call;
	}
}
