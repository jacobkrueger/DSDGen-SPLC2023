package utils.commit;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;

import model.Commit;

/**
 * Provides helper methods for dealing with git repositories while
 * parsing/updating {@link Commit}s.
 *
 * @author Alex Mikulinski
 * @since 25.03.2019
 */
public class CommitHelper {
	/**
	 * Returns a {@link Repository} at passed path.
	 *
	 * @param _repositoryPath
	 *           The path to the directory of the repository to get commits from.
	 *           The directory must contain the .git directory.
	 * @return The {@link Repository} at passed path.
	 * @throws IOException
	 *            If no repository found.
	 */
	public static Repository getRepository(String _repositoryPath) throws IOException {
		File repositoryFile;

		repositoryFile = new File(_repositoryPath + "/.git");

		if (!repositoryFile.exists()) {
			System.err.println("Tried to get a repository from the path " + _repositoryPath
					+ " which doesn't exist or doesn't lead to a repository dir.");
			return null;
		}

		return new FileRepository(repositoryFile);
	}

	/**
	 * Tries to resolve a {@link RevCommit} from it's git object references
	 * expression.
	 *
	 * @param _objectId
	 *           The git object references expression as {@link String} for the
	 *           chosen {@link RevCommit}.
	 * @param _repository
	 *           The coresponding {@link Repository}.
	 * @return The coresponding {@link RevCommit}.
	 * @throws IOException
	 */
	public static RevCommit resolveRevCommit(String _objectId, Repository _repository) throws IOException {
		Repository	repository;
		RevCommit	commit;

		repository = _repository;

		try (RevWalk walk = new RevWalk(repository)) {
			commit = walk.parseCommit(repository.resolve(_objectId));
			walk.dispose();
		}

		return commit;
	}
}
