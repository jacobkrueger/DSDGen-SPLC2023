package update.stackOverflow;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import app.Settings;
import ui.view.WarningDialog;
import update.Updater;
import utils.FileExtension;
import utils.JsonManager;
import utils.Progress;

/**
 * Provides the functionality for updating the StackOverflow JSON via the
 * api.stackexchange.com. The API allows up to 300 requests with 100 items each
 * per IP per day. To use more requests the registration on the API site is
 * needed to get a key for 10000 requests per day.
 *
 * @author Alex Mikulinski
 * @since Jan 10, 2019
 */
public class SOUpdater implements Updater {
	/**
	 * Main filter for updating StackOverflow database. Gets the following fields:
	 * <ul>
	 * <li>post</li>
	 * <ul>
	 * <li>score</li>
	 * <li>body</li>
	 * <li>lastActivityDate</li>
	 * <li>link</li>
	 * <li>title</li>
	 * </ul>
	 * <li>answer</li>
	 * <ul>
	 * <li>score</li>
	 * <li>body</li>
	 * <li>link</li>
	 * </ul>
	 * <li>wrapper</li>
	 * <ul>
	 * <li>quota_max</li>
	 * <li>quota_remaining</li>
	 * <li>page</li>
	 * </ul>
	 * </ul>
	 */
	private final String	sOMainFilter		= "!)EhwLl5mQ7U05LekN(xWS5WCVXVcQXoEtET4.)cfvJ1cXiRG*";

	/**
	 * Filter for getting the total possible response item count. Contains only the
	 * field "total".
	 */
	private final String	sOItemCountFilter	= "!)5IW-5QudQH7_nJ7d.is4eksn6j0";

	/*
	 * TODO handle exceptions,handle quota. handle errors? create progress bar.
	 * write documentation on how to create a filter.
	 */

	/**
	 * Updates the StackOverflow database. First gets the total number of possible
	 * response items. Then starts requests with responses each containing 100
	 * items. For each a new JSON file is created and saved. After getting all items
	 * the files are merged to one JSON file.
	 */
	@Override
	public void update(String _path) throws IOException {
		WarningDialog	warning;
		String			filePartPrefix;
		int				itemCount;

		if (StringUtils.isBlank(Settings.SO_TAGS.get())) {
			new WarningDialog("No Stack Overflow tags were chosen. Request was not executed!");
			return;
		}

		filePartPrefix	= _path + "part_";

		// Item count for standard request
		itemCount		= this.getTotalItemCount(new SORequest(Settings.SO_TAGS.get()));

		if (itemCount > 0) {
			warning = new WarningDialog("You are trying to download " + itemCount
					+ " Stack Overflow questions. The maximum number of questions which can be downloaded is 30,000 per IP/day. Proceed?");
			if (warning.isCanceled()) {
				return;
			}
		} else {
			warning = new WarningDialog("Now Stack Overflow questions found matching chosen filter.");
			return;
		}

		this.getJSONParts(itemCount, filePartPrefix);

		this.concatArray(filePartPrefix, (int) Math.ceil(itemCount / 100.0), _path);
	}

	private void getJSONParts(int _count, String _filePartPrefix) throws IOException {
		Map<String, Object>	map;
		SORequest				soRequest;
		int						count;

		soRequest	= new SORequest(Settings.SO_TAGS.get());
		count			= (int) Math.ceil(_count / 100.0);

		soRequest.setFilter(this.sOMainFilter);

		try (Progress progress = new Progress(1, count, "Loading SO pages: ")) {
			for (int i = 1; i < count + 1; i++) {
				progress.show().step();

				soRequest.setPage(i);

				map = JsonManager.getJSONResponse(soRequest.getURL());

				JsonManager.write(map, _filePartPrefix + i + FileExtension.JSON);
			}
		}
	}

	/**
	 * Concatenates JSON arrays from all chosen files containing StackOverflow posts
	 * to one file with one large array.
	 *
	 * @param _filePrefix
	 *           Path to the JSON file followed by the prefix of the file e.g.
	 *           data/file_i for data/file_i.json with i of 1...10.
	 * @param _count
	 *           The max index of the file e.g. 10 for 10 files 1...10.
	 * @param _path
	 *           The target path.
	 * @throws IOException
	 *            If reading or writing goes wrong.
	 */
	private void concatArray(String _filePrefix, int _count, String _path) throws IOException {
		ObjectMapper	objectMapper;
		ArrayNode		arrayNode;
		JsonNode			tempNode;

		objectMapper	= new ObjectMapper();

		arrayNode		= objectMapper.createArrayNode();

		for (int i = 1; i < _count + 1; i++) {
			tempNode = objectMapper.readTree(new File(_filePrefix + i + FileExtension.JSON)).get("items");
			for (JsonNode node : tempNode) {
				arrayNode.add(node);
			}
		}
		JsonManager.write(arrayNode, _path + Settings.SO_FILE_NAME.get());
	}

	/**
	 * Returns the total count of items which can be fetched with the passed
	 * {@link SORequest}. Therefore a filter is used which returns only the "total"
	 * field.
	 *
	 * @param _soRequest
	 *           The request to StackOverflow to count possible items for.
	 * @return Number of possible response items.
	 * @throws IOException
	 *            If getting response goes wrong.
	 */
	private int getTotalItemCount(SORequest _soRequest) throws IOException {
		Map<String, Object>	map;
		SORequest				soRequest;

		soRequest = _soRequest;

		soRequest.setFilter(this.sOItemCountFilter);

		map = JsonManager.getJSONResponse(soRequest.getURL());

		return (int) map.get("total");
	}
}
