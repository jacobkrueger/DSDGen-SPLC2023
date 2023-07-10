package utils.commit;

import org.eclipse.jgit.lib.PersonIdent;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Mixin for {@link PersonIdent}.
 *
 * @author Alex Mikulinski
 * @since 25.03.2019
 * @see <a href=
 *      "https://github.com/FasterXML/jackson-docs/wiki/JacksonMixInAnnotations">JacksonMixInAnnotations</a>
 */
@JsonIgnoreProperties("timeZone")
public abstract class PersonIdentMixin {
	/**
	 * @param name
	 * @param emailAddress
	 * @param when
	 * @param tzOffset
	 */
	@JsonCreator
	public PersonIdentMixin(@JsonProperty("name") String name, @JsonProperty("emailAddress") String emailAddress,
			@JsonProperty("when") long when, @JsonProperty("timeZoneOffset") int tzOffset) {
		// nothing
	}
}
