package me.fabricionogueira.treasyapi.service;

import me.fabricionogueira.treasyapi.model.Node;
import org.springframework.stereotype.Component;

import java.util.Hashtable;

/**
 * Layer para as regras de negócio
 */
@Component
public class NodeService {

	/**
	 * Um pai não pode ser filho de seu filho ou seus decendentes
	 *
	 * @param node   Node
	 * @param target Node
	 * @return boolean
	 */
	public boolean isDescendant(Node node, Node target) {
		return search(node, target);
	}

	/**
	 * Realiza a busca na árvore comparando os valores.
	 *
	 * @param parent     Node
	 * @param parentId Long
	 * @return boolean
	 */
	private boolean search(Node node, Node target) {
		return node.getChildrens().stream().anyMatch(children ->
				children.getId().equals(target.getId()) || (children.getChildrens().size() > 0) && search(children, target)
		);
	}

	/**
	 * Defina a estrutura para a resposta (Valor único)
	 *
	 * @param key  String
	 * @param body <T>
	 * @return Hashtable
	 */
	public <T> Hashtable<String, T> response(String key, T body) {
		Hashtable<String, T> output = new Hashtable<>();
		output.put(key, body);
		return output;
	}
}
