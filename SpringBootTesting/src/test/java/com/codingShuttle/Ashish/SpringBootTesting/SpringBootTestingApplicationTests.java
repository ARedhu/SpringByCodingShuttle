package com.codingShuttle.Ashish.SpringBootTesting;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class SpringBootTestingApplicationTests {


	// We should not return anything from the test method.
//	@Test
//	void contextLoads() {
//	}


	@Test
//	@DisplayName("DisplayNameOfTest1")
//	@Disabled // To make the test case disable.
	void testTwoNumbersAddition(){
		log.info("Testing two numbers addition");
		int a = 5, b=3;
		Assertions.assertEquals(8, addTwoNumbers(a, b)); // This Assertions comes from Junit and not from AssertJ liabrary. In this we have to memorize all the methods which is quite difficult. But when we will import the methods from AssertJ liabrary in that we have to provide expected value inside of that which will later provide the respected methods only. Also in JUnit we can't do chaining of multiple assertions but in case of AssertJ liabrary we can do so. 
		log.info("Test case passed successfully");

	}

	int addTwoNumbers(int a, int b){
		return a + b;
	}

}
