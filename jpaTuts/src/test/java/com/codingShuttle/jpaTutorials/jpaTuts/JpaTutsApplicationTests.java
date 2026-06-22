package com.codingShuttle.jpaTutorials.jpaTuts;

import com.codingShuttle.jpaTutorials.jpaTuts.entities.ProductEntity;
import com.codingShuttle.jpaTutorials.jpaTuts.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.swing.text.html.Option;
import javax.swing.text.html.parser.Entity;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class JpaTutsApplicationTests {

	@Autowired
	private ProductRepository productRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void testProductRepository(){
		ProductEntity productEntity = ProductEntity.builder()
				.sku("nestle234")
				.title("Nestle Chocolate")
				.price(BigDecimal.valueOf(233.15))
				.quantity(12)
				.build();

		ProductEntity savedEntity = productRepository.save(productEntity);
		System.out.println("Saved entity: "+savedEntity);
	}

	@Test
	void getRepository(){
		// Spring Data JPA automatically generates the implementation of this method at runtime.
		// The property name (createdAt) must match a field in the ProductEntity, and the naming should be in camelCase.
		// and keywords like After, Before, Between, etc. are used to build the query.
		List<ProductEntity> entities = productRepository.findByCreatedAtAfter(
				LocalDateTime.of(2026, 1, 1, 0, 0, 0));

		System.out.println("Entities are: ");
		for(ProductEntity entity : entities){
			System.out.println("Entity: "+ entity);
			System.out.println(" ------------------ ");
		}
	}

	@Test
	void getByTitleAndPrice(){
		Optional<ProductEntity> productEntityOptional = productRepository.findByTitleAndPrice("Pepsi", BigDecimal.valueOf(14.4));
		productEntityOptional.ifPresent(System.out::println);
	}
}
