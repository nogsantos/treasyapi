package me.fabricionogueira.treasyapi.repository;

import java.util.Collection;
import me.fabricionogueira.treasyapi.model.Node;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NodeRepository extends JpaRepository<Node, Long> {
	/**
	 * Find by id
	 *
	 * @param Long  node id
	 */
    public Collection<Node> findById(Long id);
}
