package me.fabricionogueira.treasyapi.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import me.fabricionogueira.treasyapi.model.Node;
import me.fabricionogueira.treasyapi.repository.NodeRepository;
import me.fabricionogueira.treasyapi.service.NodeService;

/**
 * @author Fabricio Nogueira
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NodeControllerTest {

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private MockMvc mockMvc;
	private Node node;
	private HttpMessageConverter mappingJackson2HttpMessageConverter;

	@Autowired
	private NodeRepository nodeRepository;;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	void setConverters(HttpMessageConverter<?>[] converters) {
		this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
				.filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().orElse(null);
		assertNotNull("the JSON message converter must not be null", this.mappingJackson2HttpMessageConverter);
	}

	@Before
	public void setup() throws Exception {
		this.mockMvc = webAppContextSetup(webApplicationContext).build();
		this.node = new Node();
		this.node.setCode("TEST");
		this.node.setDescription("Test description");
		this.node.setDetail("Test detail");
		this.node = this.nodeRepository.save(this.node);
	}

	@Test
	public void _1_nodeNotFound() throws Exception {
		mockMvc.perform(get("/node/123456").contentType(contentType)).andExpect(status().isNoContent());
	}

	@Test
	public void _1_1_badRequest() throws Exception {
		mockMvc.perform(get("/node/asd").contentType(contentType)).andExpect(status().isBadRequest());
	}

	@Test
	public void _2_readSingleNode() throws Exception {
		mockMvc.perform(get("/node/" + this.node.getId())).andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$.id", is(this.node.getId().intValue()))).andExpect(jsonPath("$.code", is("TEST")))
				.andExpect(jsonPath("$.description", is("Test description")))
				.andExpect(jsonPath("$.detail", is("Test detail"))).andExpect(status().isCreated());
	}

	@Test
	public void _3_createNode() throws Exception {
		Node node = new Node();
		node.setCode("TEST");
		node.setDescription("Test description");
		node.setDetail("Test detail");
		String nodeJson = json(node);
		this.mockMvc.perform(post("/node").contentType(contentType).content(nodeJson)).andExpect(status().isCreated());
	}

	@Test
	public void _3_1_createNodeWithoutRequiredFields() throws Exception {
		Node node = new Node();
		node.setCode("TEST");
		String nodeJson = json(node);
		this.mockMvc.perform(post("/node").contentType(contentType).content(nodeJson)).andExpect(status().isBadRequest());
	}

	@Test
	public void _4_isDescendantChildren() throws Exception {
		Node parent = this.nodeRepository.findOne(1l);
		Node children = this.nodeRepository.findOne(2l);

		System.out.println(parent);
		children.setParent(parent);

		Node save_children = this.nodeRepository.save(children);

		parent.setParent(save_children);
		boolean test_children = NodeService.isDescendant(parent, this.nodeRepository);

		assertThat(test_children).isTrue();

	}

	@Test
	public void _5_isDescendantGrandson() throws Exception {
		Node parent = this.nodeRepository.findOne(1l);
		Node children = this.nodeRepository.findOne(2l);
		Node grandson = this.nodeRepository.findOne(3l);

		grandson.setParent(children);
		Node save_grandson = this.nodeRepository.save(grandson);

		parent.setParent(save_grandson);
		boolean test_grandson = NodeService.isDescendant(parent, this.nodeRepository);

		assertThat(test_grandson).isTrue();
	}

	@Test
	public void _6_isDescendantAny() throws Exception {
		Node parent = this.nodeRepository.findOne(1l);
		Node any = this.nodeRepository.findOne(4l);

		parent.setParent(any);
		boolean test_any = NodeService.isDescendant(parent, this.nodeRepository);

		assertThat(test_any).isFalse();

	}

	/**
	 *
	 */
	protected String json(Object o) throws IOException {
		MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
		this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
		return mockHttpOutputMessage.getBodyAsString();
	}
}
