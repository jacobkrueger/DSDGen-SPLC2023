package classContainer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.github.javaparser.ast.CompilationUnit;

import app.Settings;
import parser.ClassParser;
import utils.FileExtension;

/**
 * This class holds informations for a class e.g. it's name, FQN, path. Also
 * contains methods for building these informations from other.
 *
 * @author Alex Mikulinski
 * @since Oct 7, 2018
 */
public class ClassInfo {
	/** The name of the class. */
	private String	name;
	/** The <b>F</b>ully <b>Q</b>ualified <b>N</b>ame for this class. */
	private String	fqn;
	/**
	 * Absolute system dependent path to a java class starting at
	 * {@link #projectPath}.
	 */
	private String	path;
	/**
	 * Absolute system dependent path to the project holding the java class.
	 */
	private String	projectPath;

	/**
	 * Constructs a new empty {@link ClassInfo}.
	 */
	public ClassInfo() {
	};

	/**
	 * Constructs this {@link ClassInfo} from another {@link ClassInfo}.
	 *
	 * @param _classInfo
	 *           Another {@link ClassInfo}.
	 */
	public ClassInfo(ClassInfo _classInfo) {
		ClassInfo classInfo;

		classInfo = _classInfo;

		this.setFqn(classInfo.getFqn());
		this.setName(classInfo.getName());
		this.setPath(classInfo.getPath());
		this.setProjectPath(classInfo.getProjectPath());
	}

	/**
	 * Constructs a new {@link ClassInfo} from a passed {@link Path}.
	 *
	 * @param _path
	 *           The {@link #path} as {@link Path}.
	 * @param _projectPath
	 *           The {@link #projectPath} as {@link String}.
	 */
	public ClassInfo(Path _path, String _projectPath) {
		this.projectPath	= _projectPath;
		this.path			= _path.toString().replace(this.projectPath, "");
		this.fqn				= getFqn(this.projectPath + this.path);
		this.name			= getName(this.fqn);
	}

	/**
	 * Constructs a new {@link ClassInfo} from a passed FQN.
	 *
	 * @param _fqn
	 *           The {@link #fqn}.
	 * @param _projectPath
	 *           The {@link #projectPath} as {@link String}.
	 */
	public ClassInfo(String _fqn, String _projectPath) {
		this.projectPath	= _projectPath;
		this.fqn				= _fqn;
		this.name			= getName(this.fqn);
		this.path			= this.getPath(this.fqn);
	}

	/**
	 * Finds a certain amount of {@link ClassInfo}s for java classes from this
	 * project. The classes are chosen randomly by a passed seed. Starting directory
	 * is "{@link #projectPath} + {@link Settings#SRC_PATH}". Only files with .java
	 * extension are found.
	 *
	 * @param _seed
	 *           The seed used to randomly chose files.
	 * @param _count
	 *           The amount of files to find.
	 * @return A {@link List} of {@link ClassInfo}s for found java classes.
	 */
	public List<ClassInfo> getRandom(long _seed, int _count) {
		List<ClassInfo>	results;
		Stream<Path>		stream;
		List<Path>			paths;
		ClassInfo			classInfo;
		Random				random;
		Path					startDir;
		int					count;

		count		= _count;
		random	= new Random(_seed);
		stream	= null;
		results	= new ArrayList<>();
		startDir	= Paths.get(this.projectPath + Settings.SRC_PATH.get());

		try {
			stream = Files.find(startDir, 100, (path, attr) -> path.toString().endsWith(FileExtension.JAVA));
		} catch (IOException _e) {
			_e.printStackTrace();
		}

		paths = stream.collect(Collectors.toList());

		for (int i = 0; i < count; i++) {
			classInfo = new ClassInfo(paths.get(random.nextInt(paths.size())), this.projectPath);
			results.add(classInfo);
		}

		return results;
	}

	/**
	 * Retrieves the FQN from passed {@link #path}.
	 *
	 * @param _path
	 *           The {@link #path}.
	 * @return The {@link #fqn}.
	 */
	static public String getFqn(String _path) {
		CompilationUnit	compilationUnit;
		ClassParser			classParser;
		String				packageString;
		String				className;
		String				regex;

		classParser			= new ClassParser();
		compilationUnit	= new CompilationUnit();

		regex					= ".*" + Matcher.quoteReplacement(File.separator) + "(.*)" + FileExtension.JAVA + "$";
		className			= _path.replaceAll(regex, "$1");
		compilationUnit	= classParser.getCompilationUnit(_path);

		if (!compilationUnit.getPackageDeclaration().isEmpty()) {
			packageString = compilationUnit.getPackageDeclaration().get().getNameAsString();
			return packageString + "." + className;
		} else {
			return className;
		}
	}

	/**
	 * Searches for a file path to a java class from a FQN.<br>
	 * Search start directory is {@link #projectPath}.
	 *
	 * @param _fqn
	 *           The {@link #fqn}.
	 * @return The {@link #path}.
	 */
	public String getPath(String _fqn) {
		String	fqnAsPath;
		String	result;
		String	fqn;

		fqn			= _fqn;
		result		= "";

		fqnAsPath	= fqn.replace(".", File.separator) + FileExtension.JAVA;

		try (Stream<Path> stream = Files.find(Paths.get(this.projectPath), 100,
				(path, attr) -> path.toString().contains(fqnAsPath))) {
			result = stream.findFirst().get().toString();
		} catch (IOException _e) {
			_e.printStackTrace();
		}

		result = result.replace(this.projectPath, "");

		return result;
	}

	/**
	 * Retrieves the class name from a passed FQN of this class.
	 *
	 * @param _fqn
	 *           The {@link #fqn}.
	 * @return The {@link #name}.
	 */
	static public String getName(String _fqn) {
		return _fqn.replaceAll(".*\\.(.*)$", "$1");
	}

	/**
	 * Retrieves the package from a passed FQN of this class.
	 *
	 * @param _fqn
	 *           The {@link #fqn}.
	 * @return The FQN without last dot and the word after it.
	 */
	static public String getPackage(String _fqn) {
		return _fqn.replaceAll("(.*)\\..*$", "$1");
	}

	/**
	 * @return {@link #path} converted to a Unix path when working on Windows.
	 *         Therfore replaces all "\" with "/".
	 */
	public String getUnixPath() {
		return this.getPath().replace("\\", "/");
	}

	/**
	 * Returns the {@link #name} of current {@link ClassInfo}.
	 *
	 * @return The {@link #name} as {@link String}.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the {@link #name} of current {@link ClassInfo}.
	 *
	 * @param name
	 *           The {@link #name} to set as {@link String}.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the {@link #fqn} of current {@link ClassInfo}.
	 *
	 * @return The {@link #fqn} as {@link String}.
	 */
	public String getFqn() {
		return this.fqn;
	}

	/**
	 * Sets the {@link #fqn} of current {@link ClassInfo}.
	 *
	 * @param fqn
	 *           The {@link #fqn} to set as {@link String}.
	 */
	public void setFqn(String fqn) {
		this.fqn = fqn;
	}

	/**
	 * Returns the {@link #path} of current {@link ClassInfo}.
	 *
	 * @return The {@link #path} as {@link String}.
	 */
	public String getPath() {
		return this.path;
	}

	/**
	 * Sets the {@link #path} of current {@link ClassInfo}.
	 *
	 * @param path
	 *           The {@link #path} to set as {@link String}.
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * Returns the {@link #projectPath} of current {@link ClassInfo}.
	 *
	 * @return The {@link #projectPath} as {@link String}.
	 */
	public String getProjectPath() {
		return this.projectPath;
	}

	/**
	 * Sets the {@link #projectPath} of current {@link ClassInfo}.
	 *
	 * @param projectPath
	 *           The {@link #projectPath} to set as {@link String}.
	 */
	public void setProjectPath(String projectPath) {
		this.projectPath = projectPath;
	}
}
