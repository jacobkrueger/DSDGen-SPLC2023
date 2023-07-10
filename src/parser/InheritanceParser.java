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
import keys.InheritanceKey;
import model.Inheritance;

/**
 * Parses a .csv file with the inheritance hierarchy of the examined software
 * project.
 * 
 * @author Alex Mikulinski
 * @since Jul 5, 2018
 */
public class InheritanceParser {
	/**
	 * Contains 2 Types of {@link List}s of {@link Inheritance}s:
	 * <ol>
	 * <li>Parents: Inheritance relationships, where chosen class implements/extends
	 * another class.</li>
	 * <li>Children: Inheritance relationships, where a class implements/extends the
	 * chosen class.</li>
	 * </ol>
	 */
	private List<List<Inheritance>> allInheritances;

	/**
	 * Creates a new instance of {@link InheritanceParser}.
	 */
	public InheritanceParser() {
		List<Inheritance>	parents;
		List<Inheritance>	children;

		this.allInheritances	= new ArrayList<>(2);
		parents					= new ArrayList<>();
		children					= new ArrayList<>();

		this.allInheritances.add(parents);
		this.allInheritances.add(children);
	}

	/**
	 * Parses all {@link Inheritance}s from a .csv file in
	 * {@link Settings#INHERITANCE_PATH} and return them.
	 * 
	 * @param _fqn
	 *           The <b>F</b>ully <b>Q</b>ualified <b>N</b>ame for needed class.
	 * @return {@link #allInheritances} which can contain two empty {@link List}s of
	 *         {@link Inheritance}s if no {@link Inheritance}s were found.
	 * @see <a href=
	 *      "https://github.com/FasterXML/jackson-dataformats-text/tree/master/csv">Jackson
	 *      CSV</a>
	 * @throws IOException
	 *            If parsing goes wrong.
	 */
	public List<List<Inheritance>> parseInheritanceForClass(String _fqn) throws IOException {
		MappingIterator<Map<String, String>>	mappingIterator;
		Map<String, String>							rowAsMap;
		CsvSchema										schema;
		CsvMapper										mapper;
		Inheritance										inheritance;
		String											fqn;
		File												csvFile;
		int												suitableListNr;

		fqn					= _fqn;
		csvFile				= new File(Settings.INHERITANCE_PATH.get());
		mapper				= new CsvMapper();

		// Use first row as header.
		schema				= CsvSchema.emptySchema().withHeader();

		mappingIterator	= mapper.readerFor(Map.class).with(schema).readValues(csvFile);

		while (mappingIterator.hasNext()) {
			suitableListNr	= -1;
			rowAsMap			= mappingIterator.next();

			// Access by column name, as defined in the header row.
			// Check which list from allInheritances needs to hold the inheritance
			if (rowAsMap.get(InheritanceKey.CHILD_CLASS).contains(fqn)) {
				suitableListNr = 0;
			} else if (rowAsMap.get(InheritanceKey.PARENT_CLASS).contains(fqn)) {
				suitableListNr = 1;
			}

			// If a call for this fqn was found, add it to the suitable list.
			if (suitableListNr != -1) {
				inheritance	= new Inheritance();
				inheritance	= this._createInheritance(rowAsMap);
				this.allInheritances.get(suitableListNr).add(inheritance);
			}
		}

		return this.allInheritances;
	}

	private Inheritance _createInheritance(Map<String, String> _rowAsMap) {
		Map<String, String>	rowAsMap;
		Inheritance				inheritance;

		rowAsMap		= _rowAsMap;
		inheritance	= new Inheritance();

		inheritance.setRecord_id(rowAsMap.get(InheritanceKey.RECORD_ID));
		inheritance.setParent_class(rowAsMap.get(InheritanceKey.PARENT_CLASS));
		inheritance.setChild_class(rowAsMap.get(InheritanceKey.CHILD_CLASS));
		inheritance.setRelationship_type(rowAsMap.get(InheritanceKey.RELATIONSHIP_TYPE));

		return inheritance;
	}
}
