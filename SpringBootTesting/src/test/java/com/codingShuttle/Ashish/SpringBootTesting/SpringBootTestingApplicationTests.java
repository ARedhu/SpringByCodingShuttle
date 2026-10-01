package com.codingShuttle.Ashish.SpringBootTesting;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

// @SpringBootTest
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

        // JUnit based Assertion.
		// Assertions.assertEquals(8, addTwoNumbers(a, b));
		// Problems: This Assertions comes from Junit and not from AssertJ liabrary. In this we have to memorize all the methods which is quite difficult. But when we will import the methods from AssertJ liabrary in that we have to provide result value inside of that which will later provide the respected methods only and we provide expected value there. Also in JUnit we can't do chaining of multiple assertions but in case of AssertJ liabrary we can do so.

		// AssertJ liabrary based assertion.
		int result = addTwoNumbers(a, b);
		Assertions.assertThat(result).isEqualTo(8)
				.isCloseTo(10, Offset.offset(2));


		log.info("Test case passed successfully");

	}

	int addTwoNumbers(int a, int b){
		return a + b;
	}


	@Test
	void testDivideTwoNumbers_WhenDenominatorIsZero_ThenArithematicException(){ // We have to be as much descriptive as possible while naming the test-method name.
		int a = 5, b=0;

		Assertions.assertThatThrownBy(()-> divideTwoNumbers(a, b))
				.isInstanceOf(ArithmeticException.class);

		log.info("testDivideTwoNumbers done...");
	}

	int divideTwoNumbers(int a, int b){
		try{
			int result = a / b;
			return result;
		}catch(ArithmeticException e){
			log.error("Arithematic exception occured: "+e.getLocalizedMessage());
			throw new ArithmeticException(e.getLocalizedMessage());
		}
	}

}
