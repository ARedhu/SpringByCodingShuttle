package com.codingShuttle.jpaTutorials.jpaTuts;

import com.codingShuttle.jpaTutorials.jpaTuts.dto.CProductDTOClass;
import com.codingShuttle.jpaTutorials.jpaTuts.dto.IProductView;
import com.codingShuttle.jpaTutorials.jpaTuts.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ProjectionTests {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void interfaceBasedTest(){
        List<IProductView> ls = productRepository.findAllProjectedBy();
        for(IProductView prod : ls){
            System.out.println("Title : " + prod.getTitle());
            System.out.println("Price : " + prod.getPrice());
            System.out.println("------------------");
        }
    }


    @Test
    void classBasedTest(){
        List<CProductDTOClass> ls = productRepository.getProducts();
        for(CProductDTOClass prod : ls){
            System.out.println(prod.getOutcome());
            System.out.println("--------------- ");
        }
    }

    @Test
    void updateQueryTest(){
        int fieldsAffected = productRepository.updateProductNameWithId("Britania Biscuits", 1L);
        System.out.println("Fields Affected are: "+fieldsAffected);
    }


}
