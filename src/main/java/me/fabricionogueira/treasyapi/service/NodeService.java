package me.fabricionogueira.treasyapi.service;

import java.util.Collection;

import me.fabricionogueira.treasyapi.model.Node;
import me.fabricionogueira.treasyapi.repository.NodeRepository;

/**
 * Layer para as regras de negócio
 */
public class NodeService {
	/**
	 * Confirma se o pai não está vazio e se o pai informado não é o mesmo id do nó
	 *
	 * @param Node
	 * @return boolean
	 */
	public static boolean isSelfParent(Node node) {
		try {
			return node.getParentId() != null && (node.getParentId() != node.getId());
		} catch (NullPointerException e) {
			return false;
		}
	}

	/**
	 * Um pai não pode ser filho de seu filho
	 *
	 * @todo
	 *
	 * @param Long parent
	 * @param Long id
	 * @return boolean
	 */
	public static boolean incestCheck(Node node, NodeRepository repository) {
		Collection<Node> childrens = repository.findById(node.getId());
		for (Node c : childrens) {
			if (c.getChildrens().size() > 0) {			
				for(Node l : c.getChildrens()){
					if(l.getId() == node.getId()){
						return false;
					}				
				}
			}
		}
		return true;
	}
}
