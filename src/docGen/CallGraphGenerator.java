package docGen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import app.Settings;
import classContainer.ClassContainer;
import classContainer.ClassInfo;
import model.callGraph.Call;
import utils.JsonManager;

/**
 * Generates a JSON file to use in sigmajs and display the calls.
 *
 * @author Alex Mikulinski
 * @since Oct 8, 2018
 */
public class CallGraphGenerator {
	/**
	 * Creates a JSON file with a graph with the chosen class in the middle, the
	 * class-packages calling this class above and the class-packages, which are
	 * called from this class below. The file is put in {@link Settings#PAGE_OUTPUT}
	 * with {@link Settings#CALLS_JSON_NAME} as file name.
	 *
	 * @param _classContainer
	 *           The {@link ClassContainer} for chosen class.
	 */
	public void generateInheritanceGraph(ClassContainer _classContainer) {
		ClassContainer	classContainer;
		ObjectMapper	mapper;
		List<Node>		callerNodeList;
		List<Node>		caleeNodeList;
		List<Call>		callers;
		List<Call>		calees;
		ObjectNode		rootNode;
		ArrayNode		edges;
		ArrayNode		nodes;
		String			packageFQN;
		String			relation;
		Node				classNode;
		Node				node;
		Edge				edge;
		int				counter;

		classContainer	= _classContainer;
		calees			= classContainer.getCalees();
		callers			= classContainer.getCallers();

		mapper			= new ObjectMapper();
		rootNode			= mapper.createObjectNode();
		nodes				= mapper.createArrayNode();
		edges				= mapper.createArrayNode();

		callerNodeList	= new ArrayList<>();
		caleeNodeList	= new ArrayList<>();

		// Get class
		classNode		= new Node(classContainer.getClassInfo().getFqn(), classContainer.getClassInfo().getName(), 0, 0.2,
				1);
		nodes.add(classNode.toObjectNode());

		// Get callers
		counter = 0;
		for (Call caller : callers) {
			if (!caller.getCall_type().equals("C")) {
				continue;
			}

			packageFQN	= ClassInfo.getPackage(caller.getCaller());
			relation		= "Called by " + packageFQN;

			node			= new Node(packageFQN, relation, counter * -0.2, counter * -0.1, 0.4);

			if (!callerNodeList.contains(node)) {
				counter++;
				callerNodeList.add(node);
				nodes.add(node.toObjectNode());
			}
		}

		// Get calees
		counter = 0;
		for (Call calee : calees) {
			if (!calee.getCall_type().equals("C")) {
				continue;
			}
			packageFQN	= ClassInfo.getPackage(calee.getCallee());
			relation		= "Calls " + packageFQN;

			node			= new Node(packageFQN, relation, counter * -0.2, counter * 0.1 + 0.3, 0.4);

			// If callers already contain this id, change it to avoid duplicates!
			if (callerNodeList.contains(node)) {
				node.setId(node.getId() + "1");
			}

			if (!caleeNodeList.contains(node)) {
				counter++;
				caleeNodeList.add(node);
				nodes.add(node.toObjectNode());
			}
		}

		// Create edges
		for (Node nodeTemp : caleeNodeList) {
			edge = new Edge(nodeTemp.getId() + "_E", nodeTemp.getId(), classNode.getId());
			edges.add(edge.toObjectNode());
		}
		for (Node nodeTemp : callerNodeList) {
			edge = new Edge(nodeTemp.getId() + "_E", nodeTemp.getId(), classNode.getId());
			edges.add(edge.toObjectNode());
		}

		rootNode.putPOJO("nodes", nodes);
		rootNode.putPOJO("edges", edges);

		try {
			JsonManager.write(rootNode, Settings.PAGE_OUTPUT.get() + Settings.CALLS_JSON_NAME.get());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
