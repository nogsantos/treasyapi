package me.fabricionogueira.treasyapi.service;

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
			return node.getParent() != null && (node.getParent().getId() != node.getId());
		} catch (NullPointerException e) {
			return false;
		}
	}

	/**
	 * Um pai não pode ser filho de seu filho ou seus decendentes
	 *
	 *
	 * @param Node node
	 * @param NodeRepository repository
	 * @return boolean
	 */
	public static boolean isDescendant(Node node, NodeRepository repository) {
		return search(repository.findOne(node.getId()), node.getParent().getId());
	}

	/**
	 * Realiza a busca na árvore comparando os valores.
	 *
	 *
	 * @param Node node
	 * @param Long parentId
	 * @return boolean
	 */
	private static boolean search(Node node, Long parentId) {
		return node.getChildrens().stream().anyMatch(chidren -> {
			if (chidren.getId() == parentId) {
				return true;
			} else {
				return (chidren.getChildrens().size() > 0) ? search(chidren, parentId) : false;
				// if (chidren.getChildrens().size() > 0) {
				// 	return search(chidren, parentId);
				// } else {
				// 	return false;
				// }
			}
		});
	}
}
