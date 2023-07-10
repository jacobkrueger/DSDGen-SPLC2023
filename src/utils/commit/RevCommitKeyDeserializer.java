package utils.commit;

import java.io.IOException;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;

/**
 * Provides deserializing of {@link RevCommit}s as keys.
 * 
 * @author Alex Mikulinski
 * @since 25.03.2019
 */
public class RevCommitKeyDeserializer extends KeyDeserializer {
	Repository repository;

	/**
	 * Creates a new {@link RevCommitKeyDeserializer}.
	 * 
	 * @param _repository
	 *           The {@link Repository} used to deserialize a {@link RevCommit}.
	 * @throws IOException
	 */
	public RevCommitKeyDeserializer(Repository _repository) throws IOException {
		this.repository = _repository;
	}

	/**
	 * Splits the key at " " and uses the second part (the commit id) for
	 * {@link CommitHelper#resolveRevCommit(String, Repository)}.
	 */
	@Override
	public RevCommit deserializeKey(final String key, final DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		String[] parts = key.split(" ");

		return CommitHelper.resolveRevCommit(parts[1], this.repository);
	}
}