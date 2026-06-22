package com.codingShuttle.jpaTutorials.jpaTuts.repositories;

import com.codingShuttle.jpaTutorials.jpaTuts.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    List<ProductEntity> findByCreatedAtAfter(LocalDateTime of);

//    @Query("select e from ProductEntity e where e.title=:title and e.price=:price") // this will also work like below one.
    @Query("select e from ProductEntity e where e.title=?1 and e.price=?2")
    Optional<ProductEntity> findByTitleAndPrice(String title, BigDecimal price);
}
