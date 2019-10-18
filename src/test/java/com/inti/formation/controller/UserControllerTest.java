package com.inti.formation.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inti.formation.TestUnitaireApplication;
import com.inti.formation.dao.UserDaoTest;
import com.inti.formation.entity.User;
import com.inti.formation.service.UserService;

@WebAppConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestUnitaireApplication.class)
public class UserControllerTest {

	@Autowired
	WebApplicationContext webApplicationContext;

	/**
	 * Used to mock the Web Context
	 */
	protected MockMvc mvc;
	
	@InjectMocks
	private UserController userControllerToMock;
	
	@Mock
	private UserService userService;

	/**
	 * Used for the web service adressing. You need to initiate it in the subclasses
	 * constructors.
	 */
	protected String uri;

	@Before
	public void setUp() {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		MockitoAnnotations.initMocks(this);
	}

	private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoTest.class);

	public UserControllerTest() {
		super();
		this.uri = "/user";
	}

//	@Test
//	public void getAllEntityList() {
//		MvcResult mvcResult;
//		try {
//			LOGGER.info("--------- Testing getAllEntityList Method -------");
//			LOGGER.info("--------- Constructing User -------");
//			LOGGER.info("--------- Saving User -------");
//			userService.addUser(new User(10, "Zidane"));
//			LOGGER.info("--------- Mocking Context Webservice -------");
//			mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri + "/all").accept(MediaType.APPLICATION_JSON_VALUE))
//					.andReturn();
//			LOGGER.info("--------- Getting Http Status -------");
//			int status = mvcResult.getResponse().getStatus();
//			LOGGER.info("--------- Verifying Http Status -------");
//			assertEquals(200, status);
//			LOGGER.info("--------- Getting Http Response -------");
//			String content = mvcResult.getResponse().getContentAsString();
//			LOGGER.info("--------- Deserializing JSON Response -------");
//			User[] userList = this.mapFromJson(content, User[].class);
//			assertTrue(userList.length > 0);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	@Test
	public void test_Http_getAllEntityList() {
		try {
			LOGGER.info("--------- Mocking Context Webservice -------");
			MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri + "/all").accept(MediaType.APPLICATION_JSON_VALUE))
					.andReturn();
			LOGGER.info("--------- Getting Http Status -------");
			int status = mvcResult.getResponse().getStatus();
			LOGGER.info("--------- Verifying Http Status -------");
			assertEquals(200, status);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test_appel_userService_getAllEntityList() {
		try {
			userControllerToMock.getAllUsers();
			LOGGER.info("--------- Searching for User ----------");
			Mockito.verify(userService).getAllUsers();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test_Http_createEntity() {
		try  {
			LOGGER.info("--------- Serializing User Object ----------");
			String inputJson = this.mapToJson(new User());
			LOGGER.info("--------- Mocking Context WebService and invoking the WebService ----------");
			MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri + "/adduser")
					.contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
			LOGGER.info("--------- Getting HTTP Status ----------");
			int status = mvcResult.getResponse().getStatus();
			LOGGER.info("--------- Verifying HTTP Status ----------");
			assertEquals(200, status);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void createEntity() {
		
		LOGGER.info("--------- Testing createEntity Method ----------");
		User user = new User();
		try {
			userControllerToMock.addNewUser(user);
			LOGGER.info("--------- Searching for User ----------");
			Mockito.verify(userService).addUser(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void updateEntity() {
		try {
			LOGGER.info("--------- Testing updateEntity Method ----------");
			LOGGER.info("--------- Constructing User ----------");
			User oldUser = new User(5, "Jordan");
			LOGGER.info("--------- Saving user ----------");
			userService.addUser(oldUser);
			LOGGER.info("--------- Modifying User ----------");
			User newUser = new User(5, "Mbape");
			LOGGER.info("--------- Serializing User Object ----------");
			String inputJson = this.mapToJson(newUser);
			LOGGER.info("--------- Mocking Context WebService and invoking the WebService ----------");
			MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri + "/5")
					.contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
			LOGGER.info("--------- Getting HTTP Status ----------");
			int status = mvcResult.getResponse().getStatus();
			LOGGER.info("--------- Verifying HTTP Status ----------");
			assertEquals(200, status);
			LOGGER.info("--------- Searching for User ----------");
			User userFound = userService.getUserById((long) 5);
			LOGGER.info("--------- Verifying User ----------");
			assertEquals(userFound.getUserName(), newUser.getUserName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void deleteEntity() {
		LOGGER.info("--------- Testing deleteEntity Method ----------");
		try {
			LOGGER.info("--------- Constructing User ----------");
			User user = new User(4, "Dartagnan");
			LOGGER.info("--------- Saving user ----------");
			userService.addUser(user);
			LOGGER.info("--------- Mocking Context WebService and invoking the WebService ----------");
			MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri + "/4")).andReturn();
			LOGGER.info("--------- Getting HTTP Status ----------");
			int status = mvcResult.getResponse().getStatus();
			LOGGER.info("--------- Verifying HTTP Status ----------");
			assertEquals(200, status);
			LOGGER.info("--------- Searching for User ----------");
			User userFound = userService.getUserById((long) 4);
			LOGGER.info("--------- Verifying User ----------");
			assertEquals(userFound, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Serialize the given object into Json
	 * 
	 * @param obj
	 * @return String
	 * @throws JsonProcessingException
	 */
	protected final String mapToJson(Object obj) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(obj);
	}

	/**
	 * Deserialize a given Json string into an objec
	 * 
	 * @param json
	 * @param clazz
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	protected final <T> T mapFromJson(String json, Class<T> clazz)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, clazz);
	}

}
