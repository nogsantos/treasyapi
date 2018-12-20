package me.fabricionogueira.treasyapi.repository;

import me.fabricionogueira.treasyapi.model.Node;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NodeRepository extends JpaRepository<Node, Long> {
	List<Node> findAllByOrderByIdDesc();
}
