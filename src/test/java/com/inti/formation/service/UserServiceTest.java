package com.inti.formation.service;

import static org.junit.Assert.assertEquals;

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
import org.springframework.test.context.junit4.SpringRunner;

import com.inti.formation.TestUnitaireApplication;
import com.inti.formation.dao.UserDao;
import com.inti.formation.dao.UserDaoTest;
import com.inti.formation.entity.User;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestUnitaireApplication.class)
public class UserServiceTest {

	@InjectMocks
	private UserService userServiceToMock;
	@Autowired
	private UserService userService;
	@Mock
	private UserDao userDao;
	private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoTest.class);

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void givenUsers_getHalfOfNumber() {
		LOGGER.info("--------------------- Testing givenUsers_getHalfOfNumber Method ---------------------");
		LOGGER.info("--------------------- Constructing users ---------------------");
		LOGGER.info("--------------------- Mocking getAll() method of IUserService ---------------------");
		Mockito.when(userServiceToMock.getAllUsers()).thenReturn(Arrays.asList(new User(10, "Zidan"),
				new User(1, "Mbape"), new User(2, "Messi"), new User(7, "Ronaldo")));
		LOGGER.info("--------------------- Method Mocked ---------------------");
		LOGGER.info("--------------------- Verifying Results ---------------------");
		assertEquals(2, userService.getUserNbrHalf(userServiceToMock.getAllUsers()));
	}

	@Test
	public void should_store_when_save_is_called() {
		LOGGER.info("------------- Executing should_store_when_save_is_called test of UserServiceTest ---------------");
		User myUser = new User();
		userServiceToMock.addUser(myUser);
		Mockito.verify(userDao).save(myUser);
	}

}
