package com.codingShuttle.Ashish.prod_ready_features;

import com.codingShuttle.Ashish.prod_ready_features.clients.impl.EmployeeClientImpl;
import com.codingShuttle.Ashish.prod_ready_features.dto.EmployeeDTO;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProdReadyFeaturesApplicationTests {

	@Autowired
	public EmployeeClientImpl employeeClient;

//	@Test
//	void contextLoads() {
//	}


	@Test
	@Order(3)
	void getAllEmployeesTest(){
		List<EmployeeDTO> employeeDTOList = employeeClient.getAllEmployees();
		System.out.println("---------- All Employees are: --------------");
		System.out.println(employeeDTOList);
	}

	@Test
	@Order(2)
	void getEmployeeByIdTest(){
		Long id = 1L;
		EmployeeDTO employeeDTO = employeeClient.getEmployeeById(id);
		System.out.println("--------------- Employee with id="+id+" ---------------");
		System.out.println(employeeDTO);
	}

	@Test
	@Order(1)
	void createNewEmployeeTest(){
		EmployeeDTO employeeDTO = new EmployeeDTO(null, "EmpName-Golu", "Golu@gmail.com", 36);
		EmployeeDTO savedEmployeeDTO = employeeClient.createNewEmployee(employeeDTO);
		System.out.println("-------------- Saved employee is: --------------");
		System.out.println(savedEmployeeDTO);
	}

}
