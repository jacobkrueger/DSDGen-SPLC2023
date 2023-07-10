package docGen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import app.Settings;
import classContainer.ClassContainer;
import model.Inheritance;
import utils.JsonManager;

/**
 * Generates a JSON file to use in sigmajs and display inheritances.
 *
 * @author Alex Mikulinski
 * @since Oct 8, 2018
 */
public class InheritanceGraphGenerator {

	/**
	 * Creates a JSON file with a graph with the chosen class in the middle, the
	 * parent classes above and the child classes below. The file is put in
	 * {@link Settings#PAGE_OUTPUT} with {@link Settings#INHERITANCE_JSON_NAME} as
	 * file name.
	 *
	 * @param _classContainer
	 *           The {@link ClassContainer} for chosen class.
	 */
	public void generateInheritanceGraph(ClassContainer _classContainer) {
		List<Inheritance>	children;
		List<Inheritance>	parents;
		ClassContainer		classContainer;
		ObjectMapper		mapper;
		List<Node>			childrenNodeList;
		List<Node>			parentNodeList;
		ObjectNode			rootNode;
		ArrayNode			edges;
		ArrayNode			nodes;
		String				relation;
		Node					node;
		Node					classNode;
		Edge					edge;
		int					counter;

		classContainer		= _classContainer;
		parents				= classContainer.getParents();
		children				= classContainer.getChildren();

		mapper				= new ObjectMapper();
		rootNode				= mapper.createObjectNode();
		nodes					= mapper.createArrayNode();
		edges					= mapper.createArrayNode();

		childrenNodeList	= new ArrayList<>();
		parentNodeList		= new ArrayList<>();

		// Get class
		classNode			= new Node(classContainer.getClassInfo().getFqn(), classContainer.getClassInfo().getName(), 0,
				0.2, 1);
		nodes.add(classNode.toObjectNode());

		// Get parents
		counter = 0;
		for (Inheritance inheritance : parents) {
			relation	= inheritance.getRelationship_type() + " " + inheritance.getParent_class();

			node		= new Node(inheritance.getParent_class(), relation, counter * -0.1, counter * -0.1, 0.4);

			if (!parentNodeList.contains(node)) {
				counter++;
				parentNodeList.add(node);
				nodes.add(node.toObjectNode());
			}
		}

		// Get children
		counter = 0;
		for (Inheritance inheritance : children) {
			relation	= inheritance.getRelationship_type().replaceAll("s$", "ed by ");
			relation	+= inheritance.getChild_class();

			node		= new Node(inheritance.getChild_class(), relation, counter * -0.1, counter * 0.1 + 0.3, 0.4);

			if (!childrenNodeList.contains(node)) {
				counter++;
				childrenNodeList.add(node);
				nodes.add(node.toObjectNode());
			}
		}

		// Create edges
		for (Node nodeTemp : parentNodeList) {
			edge = new Edge(nodeTemp.getId() + "_E", nodeTemp.getId(), classNode.getId());
			edges.add(edge.toObjectNode());
		}
		for (Node nodeTemp : childrenNodeList) {
			edge = new Edge(nodeTemp.getId() + "_E", nodeTemp.getId(), classNode.getId());
			edges.add(edge.toObjectNode());
		}

		rootNode.putPOJO("nodes", nodes);
		rootNode.putPOJO("edges", edges);

		try {
			JsonManager.write(rootNode, Settings.PAGE_OUTPUT.get() + Settings.INHERITANCE_JSON_NAME.get());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
