package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import app.Settings;
import classContainer.ClassInfo;

/**
 * Provides searching for .java source files in the
 * {@link Settings#EPROJECT_REPOSITORY_PATH}.
 *
 * @author Alex Mikulinski
 * @since 14.04.2019
 */
public class ClassSearch {
	/**
	 * Searches for classes in a repository containing the passed keyword.
	 *
	 * @param _keyword
	 *           The keyword to search for. The case is ignored.
	 * @param _projectPath
	 *           The path to a project.
	 * @return A {@link List} of found {@link ClassInfo}s.
	 */
	public List<ClassInfo> search(String _keyword, String _projectPath) {
		List<ClassInfo>	classes;
		Path					dirPath;
		String				repositoryPath;

		repositoryPath = _projectPath + Settings.REPOSITORY_DIR_NAME.get();

		if (!FileManager.exists(repositoryPath)) {
			System.err.println("Unable to search for classes at " + repositoryPath + " because this path does not exist.");
			return null;
		}

		dirPath	= Paths.get(repositoryPath);
		classes	= new ArrayList<>();

		try {
			Files.walk(dirPath).filter(Files::isRegularFile).forEach(path -> {
				if (!path.toString().endsWith(FileExtension.JAVA)) {
					return;
				}

				if (!StringUtils.containsIgnoreCase(path.getFileName().toString(), _keyword)) {
					return;
				}
				classes.add(new ClassInfo(path, _projectPath));
			});
		} catch (IOException e) {
			e.printStackTrace();
		}

		return classes;
	}
}
