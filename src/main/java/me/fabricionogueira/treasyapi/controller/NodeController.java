package me.fabricionogueira.treasyapi.controller;

import me.fabricionogueira.treasyapi.model.Node;
import me.fabricionogueira.treasyapi.repository.NodeRepository;
import me.fabricionogueira.treasyapi.resource.ApiResponse;
import me.fabricionogueira.treasyapi.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Hashtable;
import java.util.List;

/**
 * Node controller
 */
@RestController
@RequestMapping("/")
public class NodeController {

	private NodeRepository nodeRepository;
	private NodeService service;
	private ApiResponse response;

	@Autowired
	public NodeController(NodeRepository nodeRepository, NodeService service, ApiResponse response) {
		this.nodeRepository = nodeRepository;
		this.service = service;
		this.response = response;
	}

	/**
	 * Get All
	 *
	 * @return A List of Nodes
	 */
	@GetMapping("/")
	public ResponseEntity<String> hello() {
		return response.ok("It's Alive");
	}

	/**
	 * Get All
	 *
	 * @return A List of Nodes
	 */
	@GetMapping("/nodes")
	public ResponseEntity<List<Node>> getAllNodes() {
		return response.list(nodeRepository.findAllByOrderByIdDesc());
	}

	/**
	 * Create a new Note
	 *
	 * @param node node attributes from RequestBody
	 * @return ResponseEntity<Hashtable>
	 */
	@PostMapping("/node")
	public ResponseEntity<Hashtable<String, Long>> createNote(@Valid @RequestBody Node node) {
		if (node.getParent() != null) {
			Node parent = nodeRepository.findOne(node.getParent().getId());
			if (parent != null) {
				node.setParent(parent);
			} else {
				node.setParent(null);
			}
		}
		return response.created(service.response("id", nodeRepository.save(node).getId()));
	}

	/**
	 * Get a Single Note
	 *
	 * @param nodeId The node id
	 * @return Node entity
	 */
	@GetMapping("/node/{id}")
	public ResponseEntity<Node> getNodeById(@PathVariable(value = "id") Long nodeId) {
		Node node = nodeRepository.findOne(nodeId);
		if (node == null) {
			return response.notFound();
		}
		return response.ok(node);
	}

	/**
	 * Update a Note
	 *
	 * @param nodeId      The node id
	 * @param bodyNode Node attributes
	 * @return Node entity
	 */
	@PutMapping("/node/{id}")
	public ResponseEntity<Hashtable<String, Long>> updateNote(@PathVariable(value = "id") Long nodeId,
															  @Valid @RequestBody Node bodyNode) {

		final Node node = nodeRepository.findOne(nodeId);
		if (node == null) {
			return response.notFound();
		}

		node.setCode(bodyNode.getCode());
		node.setDescription(bodyNode.getDescription());
		node.setDetail(bodyNode.getDetail());

		bodyNode.setId(nodeId);

		if (bodyNode.getParent() != null) {
			final Node target = nodeRepository.findOne(node.getId());
			if (!service.isDescendant(bodyNode, target)) {
				final Node parent = nodeRepository.findOne(bodyNode.getParent().getId());
				if (parent != null && (!parent.getId().equals(bodyNode.getId()))) {
					node.setParent(parent);
				} else {
					node.setParent(null);
				}
			}
		}
		return response.ok(service.response("id", nodeRepository.save(node).getId()));
	}

	/**
	 * Delete a Note
	 *
	 * @param nodeId The node id
	 * @return Node entity
	 */
	@DeleteMapping("/node/{id}")
	public ResponseEntity<Hashtable<String, String>> deleteNote(@PathVariable(value = "id") Long nodeId) {
		Node node = nodeRepository.findOne(nodeId);
		if (node == null) {
			return response.notFound();
		}
		nodeRepository.delete(node);
		return response.ok(service.response("Success", "Node deleted successfully"));
	}
}
