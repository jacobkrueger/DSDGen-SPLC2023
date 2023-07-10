package utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.io.FileUtils;

/**
 * Provides static methods for creating directories for this program and
 * extraction of resources.
 *
 * @author Alex Mikulinski
 * @since 03.02.2019
 */
public class FileManager {

	/**
	 * Creates all directories on the passed path. <br>
	 * See {@link File#mkdirs()}.
	 *
	 * @param _dirPath
	 *           The path to the last directory.
	 * @return Ttrue if and only if the directory was created, along with all
	 *         necessary parent directories; false otherwise.
	 */
	public static boolean createDirs(String _dirPath) {
		return new File(_dirPath).mkdirs();
	}

	/**
	 * Tests whether the file or directory with passed path exists.
	 *
	 * @param _pathToFile
	 *           Path to the file as {@link String}.
	 * @return True if the file or directory exists.
	 */
	public static boolean exists(String _pathToFile) {
		return new File(_pathToFile).exists();
	}

	/**
	 * Copies a directory using {@link FileUtils#copyDirectory(File, File)}.
	 *
	 * @param _source
	 *           The path to the source directory.
	 * @param _destination
	 *           The path to the destination directory.
	 * @throws IOException
	 *            If {@link FileUtils#copyDirectory(File, File)} throws an
	 *            {@link IOException}.
	 */
	public static void copyDirectory(String _source, String _destination) throws IOException {
		File	destination;
		File	source;

		destination	= new File(_destination);
		source		= new File(_source);

		try {
			FileUtils.copyDirectory(source, destination);
		} catch (IOException _e) {
			throw new IOException("Failed copying the directory \"" + _source + "\" to \"" + _destination + "\"", _e);
		}
	}

	/**
	 * Will try to extract a resource from a jar into the given destination. If the
	 * program was not started in a .jar will do nothing. The destination must be a
	 * directory. If the destination directory does not exist it will be created.
	 *
	 * @param _resource
	 *           The path to the resource starting at the .jar root.
	 * @param _destination
	 *           The path to the destination starting in the directory containing
	 *           the .jar.
	 * @throws IOException
	 *            If coyping the resource goes wrong.
	 */
	public static void extractResource(String _resource, String _destination) throws IOException {
		try {
			_extractResource(_resource, _destination);
		} catch (IOException _e) {
			throw new IOException("Failed extracting the resource \"" + _resource + "\" to \"" + _destination + "\"", _e);
		}
	}

	private static void _extractResource(String _resource, String _destination) throws IOException {
		Enumeration<JarEntry>	tempEnum;
		FileOutputStream			fileOutputStream;
		InputStream					inputStream;
		JarEntry						jarEntry;
		JarFile						jarFile;
		boolean						found;
		File							destinationFile;
		File							resourceFile;
		File							tempFile;
		File							source;

		fileOutputStream	= null;
		resourceFile		= new File(_resource);
		destinationFile	= new File(_destination);
		inputStream			= null;
		jarFile				= null;
		source				= null;
		found					= false;

		// If no destination directory found, create it.
		if (!destinationFile.exists()) {
			destinationFile.mkdirs();
		}

		try {
			source = new File(FileManager.class.getProtectionDomain().getCodeSource().getLocation().toURI());
		} catch (URISyntaxException _e) {
			throw new IOException("Failed while getting the path to the .jar.", _e);
		}

		// If we are not in a jar e.g. if the program is executed from IDE then there is
		// no jar to extract from
		if (!source.toString().endsWith(FileExtension.JAR)) {
			return;
		}

		try {
			jarFile	= new JarFile(source);
			tempEnum	= jarFile.entries();

			while (tempEnum.hasMoreElements()) {
				jarEntry = tempEnum.nextElement();

				// Copy only files which starts with the passed resource string.
				if (!jarEntry.getName().startsWith(resourceFile.toString())) {
					continue;
				}

				found		= true;

				tempFile	= new File(destinationFile + File.separator + jarEntry.getName());

				// If the current file is a directory, create it and proceed.
				if (jarEntry.isDirectory()) {
					tempFile.mkdir();
					continue;
				}

				// Copy the file.
				inputStream			= jarFile.getInputStream(jarEntry);
				fileOutputStream	= new FileOutputStream(tempFile);

				while (inputStream.available() > 0) {
					fileOutputStream.write(inputStream.read());
				}
			}
		} catch (IOException _e) {
			throw new IOException("Failed while copying the resource.", _e);
		} finally {
			try {
				if (fileOutputStream != null) {
					fileOutputStream.close();
				}
				if (inputStream != null) {
					inputStream.close();
				}
				if (jarFile != null) {
					jarFile.close();
				}
			} catch (IOException _e) {
				throw new IOException("Failed closing a stream.", _e);
			}
		}

		if (!found) {
			throw new IOException("Failed finding resource \"" + resourceFile + "\"");
		}
	}
}
