package com.inti.formation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MathematiqueServiceTest {
	
	@Test
	public void additionTest() {
		MathematiqueService mathServ = new MathematiqueService();
		
		assertEquals(11, mathServ.addition(5, 6));
	}

}
