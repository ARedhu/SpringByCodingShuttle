package com.codingShuttle.jpaTutorials.jpaTuts.contollers;

import com.codingShuttle.jpaTutorials.jpaTuts.entities.ProductEntity;
import com.codingShuttle.jpaTutorials.jpaTuts.repositories.ProductRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {

    private final int PAGE_SIZE = 5;

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Way-1: Using inbuilt sorting query.
//    @GetMapping("/")
//    public List<ProductEntity> getProducts(){
//        return productRepository.findAllByOrderByPriceDesc();
//    }


    // Way-2: Using Sort class
//    @GetMapping("/")
//    public List<ProductEntity> getProducts(){
//        return productRepository.findAll(
//                // i.
//              //   Sort.by(Sort.Direction.DESC, "price", "quantity") // Here if price is same then the entries will be sorted on the basis of quantity. And, remember in descending order for both.
//
//                // ii. More preferred than i.
//              Sort.by(
//                      Sort.Order.asc("price"),  // if price is same then sort on the basis of quantity.
//                      Sort.Order.desc("quantity")
//              ));
//    }

    // Pagination and filtering and sorting in a single program
    @GetMapping("/")
    public List<ProductEntity> getProducts(@RequestParam(defaultValue =
    "id") String title, @RequestParam(defaultValue = "0") int pageNumber){

        Sort mixedSort = Sort.by(
                Sort.Order.asc("price"),
                Sort.Order.desc("quantity")
        );

        Pageable pageable = PageRequest.of(pageNumber, PAGE_SIZE, mixedSort);

        return productRepository.findByTitleContaining(title, pageable);
    }

    //Generated SQL
    /*
    SELECT * FROM product_table
    WHERE title_x LIKE '%your_title%'
    ORDER BY price ASC, quantity DESC
    LIMIT 5 OFFSET (pageNumber * 5);
    */
}
