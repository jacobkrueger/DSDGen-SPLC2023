package parser;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import model.Commit;
import utils.commit.CommitHelper;
import utils.commit.DiffEntryMixin;
import utils.commit.PersonIdentMixin;
import utils.commit.RevCommitKeyDeserializer;

/**
 * Provides methods for parsing {@link Commit}s retrieved from
 * <a href="https://github.com">GitHub</a> and saved in a JSON file to the model
 * described in {@link Commit}.
 *
 * @author Alex Mikulinski
 * @since 25.03.2019
 */
public class CommitParser {
	/**
	 * Parses a JSON file containing the {@link Commit} for the examined software
	 * project.
	 *
	 * @param _filePath
	 *           The path to the JSON file as {@link String}.
	 * @param _repositoryPath
	 *           The path to the{@link Repository} to get the {@link Commit}s from.
	 *           <br>
	 *           See {@link CommitHelper#getRepository(String)}
	 * @return A {@link List} of {@link Commit}.
	 * @throws IOException
	 */
	public List<Commit> parse(String _filePath, String _repositoryPath) throws IOException {
		ObjectMapper	objectMapper;
		SimpleModule	module;
		File				file;
		Repository		repository;

		file				= new File(_filePath);
		module			= new SimpleModule();
		repository		= CommitHelper.getRepository(_repositoryPath);
		objectMapper	= new ObjectMapper();

		module.addKeyDeserializer(RevCommit.class, new RevCommitKeyDeserializer(repository));
		objectMapper.registerModule(module);

		objectMapper.addMixIn(PersonIdent.class, PersonIdentMixin.class);
		objectMapper.addMixIn(DiffEntry.class, DiffEntryMixin.class);

		return Arrays.asList(objectMapper.readValue(file, Commit[].class));
	}
}
