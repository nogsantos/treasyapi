package me.fabricionogueira.treasyapi.repository;

import me.fabricionogueira.treasyapi.model.Node;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NodeRepository extends JpaRepository<Node, Long> {
}