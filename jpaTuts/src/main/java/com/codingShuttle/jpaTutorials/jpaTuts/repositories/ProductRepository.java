package com.codingShuttle.jpaTutorials.jpaTuts.repositories;

import com.codingShuttle.jpaTutorials.jpaTuts.dto.CProductDTOClass;
import com.codingShuttle.jpaTutorials.jpaTuts.dto.IProductView;
import com.codingShuttle.jpaTutorials.jpaTuts.entities.ProductEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    List<ProductEntity> findAllByOrderByPriceDesc();

    // Pagination:
    List<ProductEntity> findByTitleContaining(String title, Pageable pageable);


    // Projection: means fetching only the required fields from the database instead of the entire entity.
    // way-1 (using interface)
    List<IProductView> findAllProjectedBy();

    // way-2 (using DTO class, more preferred)
    @Query("""
        Select new com.codingShuttle.jpaTutorials.jpaTuts.dto.CProductDTOClass(p.title, p.price)
        From ProductEntity p
""")
    List<CProductDTOClass> getProducts();


    // Update query
    @Transactional
    @Modifying
    @Query("Update ProductEntity p set p.title = :title where p.id = :id")
    int updateProductNameWithId(@Param("title") String title, @Param("id") Long id);
    // We can use any variable name at the place of title and id. But remember the value/name/variableName inside of @Param and :title should be same.
}

