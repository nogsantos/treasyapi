package me.fabricionogueira.treasyapi.controller.integration;

import me.fabricionogueira.treasyapi.model.Node;
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

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NodeControllerTest {

	private MediaType contentType = new MediaType(
		MediaType.APPLICATION_JSON.getType(),
		MediaType.APPLICATION_JSON.getSubtype(),
		Charset.forName("utf8")
	);

	private MockMvc mockMvc;
	private Node node;
	private HttpMessageConverter mappingJackson2HttpMessageConverter;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	void setConverters(HttpMessageConverter<?>[] converters) {
		mappingJackson2HttpMessageConverter = Arrays.stream(converters).filter(
			hmc -> hmc instanceof MappingJackson2HttpMessageConverter
		).findAny().orElse(null);
		assertNotNull("the JSON message converter must not be null", this.mappingJackson2HttpMessageConverter);
	}

	@Before
	public void setup() {
		this.mockMvc = webAppContextSetup(webApplicationContext).build();
		this.node = new Node();
		this.node.setCode("TEST");
		this.node.setDescription("Test description");
		this.node.setDetail("Test detail");
	}

	@Test
	public void itReturnsNoContentMessageWhenNodeNotFound() throws Exception {
		mockMvc.perform(
			get("/node/123456").contentType(contentType)
		).andExpect(
			status().isNoContent()
		);
	}

	@Test
	public void itReturnsBadRequestWhenTheParamValueIsNotAValidNumber() throws Exception {
		mockMvc.perform(
			get("/node/asd").contentType(contentType)
		).andExpect(
			status().isBadRequest()
		);
	}

	@Test
	public void itCreateASingleNodeWhenReceivesValidValues() throws Exception {
		Node node = new Node();
		node.setCode("TEST");
		node.setDescription("Test description");
		node.setDetail("Test detail");
		String nodeJson = json(node);
		this.mockMvc.perform(
			post("/node").contentType(contentType).content(nodeJson)
		).andExpect(status().isCreated());
	}

	@Test
	public void itCreateNodeWithoutRequiredFields() throws Exception {
		Node node = new Node();
		node.setCode("TEST");
		String nodeJson = json(node);
		this.mockMvc.perform(
			post("/node").contentType(contentType).content(nodeJson)
		).andExpect(status().isBadRequest());
	}

	private String json(Object o) throws IOException {
		MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
		this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
		return mockHttpOutputMessage.getBodyAsString();
	}
}
