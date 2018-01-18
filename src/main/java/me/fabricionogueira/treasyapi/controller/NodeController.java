package me.fabricionogueira.treasyapi.controller;

import me.fabricionogueira.treasyapi.model.Node;
import me.fabricionogueira.treasyapi.repository.NodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
/**
 *
 */
@RestController
@RequestMapping("/")
public class NodeController {

    @Autowired
    NodeRepository nodeRepository;
    /**
     * Get All Nodes
     */
    @GetMapping("/nodes")
    public List<Node> getAllNodes() {
        return nodeRepository.findAll();
    }
    /**
     * Create a new Note
     */
    @PostMapping("/node")
    public Long createNote(@Valid @RequestBody Node node) {
        if(node.getParent_id() != null){
            Node parent = nodeRepository.findOne(node.getParent_id());
            node.setParent(parent);
        }
        Node new_node = nodeRepository.save(node);
        return new_node.getId();
    }
    /**
     * Get a Single Note
     */
    @GetMapping("/node/{id}")
    public ResponseEntity<Node> getNodeById(@PathVariable(value = "id") Long nodeId) {
        Node node = nodeRepository.findOne(nodeId);
        if (node == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(node);
    }
    /**
     * Update a Note
     */
    @PutMapping("/node/{id}")
    public ResponseEntity<Node> updateNote(@PathVariable(value = "id") Long nodeId, @Valid @RequestBody Node nodeDetails) {
        Node node = nodeRepository.findOne(nodeId);
        if (node == null) {
            return ResponseEntity.notFound().build();
        }
        node.setCode(nodeDetails.getCode());
        node.setDescription(nodeDetails.getDescription());
        node.setDetail(nodeDetails.getDetail());
        node.setParent(nodeDetails.getParent());

        Node updatedNote = nodeRepository.save(node);
        return ResponseEntity.ok(updatedNote);
    }
    /**
     * Delete a Note
     */
    @DeleteMapping("/node/{id}")
    public ResponseEntity<Node> deleteNote(@PathVariable(value = "id") Long nodeId) {
        Node node = nodeRepository.findOne(nodeId);
        if (node == null) {
            return ResponseEntity.notFound().build();
        }
        nodeRepository.delete(node);
        return ResponseEntity.ok().build();
    }
}