package utils.commit;

import org.eclipse.jgit.diff.DiffEntry;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Mixin for {@link DiffEntry}.
 * 
 * @author Alex Mikulinski
 * @since 25.03.2019
 * @see <a href=
 *      "https://github.com/FasterXML/jackson-docs/wiki/JacksonMixInAnnotations">JacksonMixInAnnotations</a>
 */
@JsonIgnoreProperties({ "oldMode", "newMode", "oldId", "newId" })
public abstract class DiffEntryMixin {
	// nothing
}
