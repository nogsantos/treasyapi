package me.fabricionogueira.treasyapi.controller;

import org.junit.Before;
import org.junit.runner.RunWith;
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

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import org.junit.Test;

/**
 * @author Fabricio Nogueira
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class NodeControllerTest {

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private MockMvc mockMvc;
	private Node node;
	private HttpMessageConverter mappingJackson2HttpMessageConverter;
	private List<Node> nodeList = new ArrayList<>();

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
		this.node.setCode("TEST-1");
		this.node.setDescription("Test description");
		this.node.setDetail("Test detail");
		this.node = this.nodeRepository.save(this.node);
	}

	@Test
	public void nodeNotFound() throws Exception {
		mockMvc.perform(get("/node/123456").contentType(contentType)).andExpect(status().isNotFound());
	}

	@Test
	public void readSingleNode() throws Exception {
	  mockMvc.perform(get("/node/" + this.node.getId())).andExpect(status().isOk())
	      .andExpect(content().contentType(contentType))
	      .andExpect(jsonPath("$.id", is(this.node.getId().intValue())))
	      .andExpect(jsonPath("$.code", is("TEST-1")))
	      .andExpect(jsonPath("$.description", is("Test description")))
	      .andExpect(jsonPath("$.detail", is("Test detail")));
	}

	@Test
	public void createNode() throws Exception {
		Node node = new Node();
		node.setCode("TESTE-3");
		node.setDescription("Teste description");
		node.setDetail("Test details");
		String nodeJson = json(node);
		this.mockMvc.perform(post("/node").contentType(contentType).content(nodeJson)).andExpect(status().isOk());
	}

	protected String json(Object o) throws IOException {
		MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
		this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
		return mockHttpOutputMessage.getBodyAsString();
	}
}
