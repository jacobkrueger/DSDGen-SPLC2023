package utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

/**
 * Provides methods for writing and parsing JSON objects/files.
 *
 * @author Alex Mikulinski
 * @since 21.03.2019
 */
public class JsonManager {
	/**
	 * Writes the passed items to the file at passed path.
	 *
	 * @param _items
	 *           A {@link List} of items of chosen type.
	 * @param _target
	 *           The path to the future JSON file e.g. data/test.json
	 * @param _objectMapper
	 *           The {@link ObjectMapper} to use. For example that makes possible to
	 *           add mixins.
	 * @throws IOException
	 */
	public static void write(Object _items, String _target, ObjectMapper _objectMapper) throws IOException {
		_write(_items, _target, _objectMapper != null ? _objectMapper : new ObjectMapper());
	}

	/**
	 * Writes the passed items to the file at passed path.
	 *
	 * @param _items
	 *           A {@link List} of items of chosen type.
	 * @param _target
	 *           The path to the future JSON file e.g. data/test.json
	 * @throws IOException
	 */
	public static void write(Object _items, String _target) throws IOException {
		_write(_items, _target, new ObjectMapper());
	}

	private static void _write(Object _items, String _target, ObjectMapper _objectMapper) throws IOException {
		ObjectMapper	objectMapper;
		File				target;

		target			= new File(_target);
		objectMapper	= _objectMapper;

		objectMapper.writeValue(target, _items);
	}

	/**
	 * Gets a JSON response from the passed {@link URL}. Decompresses the gzip
	 * compressed response and returns a {@link Map} containing the JSON.
	 *
	 * @param _url
	 *           The request {@link URL}.
	 * @return A {@link Map} representation of the JSON.
	 * @throws IOException
	 *            If getting response goes wrong.
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getJSONResponse(URL _url) throws IOException {
		GZIPInputStream	gzipInputStream;
		URLConnection		urlConnection;
		ObjectMapper		objectMapper;

		objectMapper		= new ObjectMapper();

		urlConnection		= _url.openConnection();
		gzipInputStream	= new GZIPInputStream(urlConnection.getInputStream());

		return objectMapper.readValue(gzipInputStream, Map.class);
	}

	/**
	 * Counts the items of a JSON file starting at the root.<br>
	 * Using: https://github.com/json-path/JsonPath
	 *
	 * @param _target
	 *           The path to the JSON file as {@link String}.
	 * @return The number of items in the JSON file or -1 if there are no items to
	 *         count (Empty file or file is missing).
	 */
	public static int getItemCount(String _target) {
		File		jsonFile;
		Integer	result;

		jsonFile	= new File(_target);
		result	= null;

		if (!jsonFile.exists()) {
			return -1;
		}

		try {
			result = JsonPath.parse(jsonFile).read("$.length()");
		} catch (IOException _e) {
			throw new IllegalArgumentException("Failed counting JSON items in " + _target, _e);
		}

		if (result != null) {
			return result;
		} else {
			return -1;
		}
	}
}
