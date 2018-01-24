package me.fabricionogueira.treasyapi.controller;

import java.util.Hashtable;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.fabricionogueira.treasyapi.model.Node;
import me.fabricionogueira.treasyapi.repository.NodeRepository;
import me.fabricionogueira.treasyapi.resource.ApiResponse;
import me.fabricionogueira.treasyapi.service.NodeService;

/**
 * Node controller
 *
 */
@RestController
@RequestMapping("/")
public class NodeController {

	@Autowired
	NodeRepository nodeRepository;

	/**
	 * Get All
	 *
	 * @return A List of Nodes
	 */
	@GetMapping("/nodes")
	public ResponseEntity<List<Node>> getAllNodes() {
		try {
			return ApiResponse.getInstance().list(nodeRepository.findAllByOrderByIdDesc());
		} catch (StackOverflowError e) {
			System.out.println(e.getClass().getCanonicalName());
		}
		return ApiResponse.getInstance().requestFail();
	}

	/**
	 * Create a new Note
	 *
	 * @todo Corrigir o retorno
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
		return ApiResponse.getInstance().ok(NodeService.response("id", nodeRepository.save(node).getId()));
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
			return ApiResponse.getInstance().notFound();
		}
		return ApiResponse.getInstance().ok(node);
	}

	/**
	 * Update a Note
	 *
	 * @param nodeId The node id
	 * @param nodeDetails Node attributes
	 * @return Node entity
	 */
	@PutMapping("/node/{id}")
	public ResponseEntity<Hashtable<String, Long>> updateNote(@PathVariable(value = "id") Long nodeId,
			@Valid @RequestBody Node nodeDetails) {
		Node node = this.nodeRepository.findOne(nodeId);
		if (node == null) {
			return ApiResponse.getInstance().notFound();
		}
		node.setCode(nodeDetails.getCode());
		node.setDescription(nodeDetails.getDescription());
		node.setDetail(nodeDetails.getDetail());

		nodeDetails.setId(nodeId);

		if (nodeDetails.getParent() != null) {
			if (!NodeService.isDescendant(nodeDetails, this.nodeRepository)) {
				Node parent = this.nodeRepository.findOne(nodeDetails.getParent().getId());
				if (parent != null && (parent.getId() != nodeDetails.getId())) {
					node.setParent(parent);
				} else {
					node.setParent(null);
				}
			}
		}

		return ApiResponse.getInstance().ok(NodeService.response("id", this.nodeRepository.save(node).getId()));
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
			return ApiResponse.getInstance().notFound();
		}
		nodeRepository.delete(node);
		return ApiResponse.getInstance().ok(NodeService.response("Success", "Node deleted successfully"));
	}

}
