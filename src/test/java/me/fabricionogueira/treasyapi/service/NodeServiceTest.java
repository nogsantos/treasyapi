package me.fabricionogueira.treasyapi.service;

import me.fabricionogueira.treasyapi.model.Node;
import me.fabricionogueira.treasyapi.repository.NodeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static java.lang.Long.MAX_VALUE;
import static java.lang.Long.MIN_VALUE;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class NodeServiceTest {
	@Mock
	private NodeRepository nodeRepository;

	@Mock
	private NodeService service;

	@Test
	public void itVerifyIfItIsChildrenDescendantOfANode() {
		Node parent = new Node();
		parent.setId(MIN_VALUE);

		Node children = new Node();
		children.setId(MAX_VALUE);
		children.setParent(parent);

		final boolean descendant = service.isDescendant(children, parent);

		assertThat(descendant).isTrue();
	}

	@Test
	public void itVerifyIfItIsGrandsonDescendantOfANode() {
		Node parent = new Node();
		parent.setId(MIN_VALUE);

		Node children = new Node();
		children.setId(MIN_VALUE + 1);

		Node grandson = new Node();
		grandson.setId(MIN_VALUE + 2);
		grandson.setParent(children);

		assertThat(service.isDescendant(children, parent)).isTrue();
	}

	@Test
	public void itVerifyIfItIsDescendantAny() {
		Node any = new Node();
		any.setId(MAX_VALUE);

		Node parent = new Node();
		parent.setId(MIN_VALUE);
		parent.setParent(any);

		assertThat(service.isDescendant(parent, any)).isFalse();
	}
}
