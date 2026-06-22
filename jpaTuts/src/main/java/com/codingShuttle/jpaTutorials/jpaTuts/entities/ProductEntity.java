package com.codingShuttle.jpaTutorials.jpaTuts.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table( // This annotation tells hibernate how our Java entity has to be mapped to a database table.
        name = "productTable",
       // catalog = "products_catalog",  // We can group multiple tables in a single catalog.
       uniqueConstraints = {
                // Bydeafult hibernate creates indexes for unique constraints as well.
            //    @UniqueConstraint(name = "sku_unique", columnNames = {"sku"}), // So, we can remove this one now to remove redundancy. As we created an index for this below.
                @UniqueConstraint(name = "title_price_unique", columnNames = {"title_x", "price"})
       },
       indexes = {
               @Index(name = "sku_index", columnList = "sku") // Indexes are created to speed up searching. Database uses the index (like a book's index) and jumps directly to the matching row.
        }
)
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String sku;

    @Column(name = "title_x")
    private String title;

    private BigDecimal price;

    private Integer quantity;

    @CreationTimestamp // Using this we don't need to assign value in it by ourself.
    private LocalDateTime createdAt;
    // Now the naming convension is camelCase but later it would be converted to kawab_Case by the DB. created_at.

    @UpdateTimestamp // Now we don't have to update and the field by ourself.
    private LocalDateTime updatedAt;

}



//Database Server
//│
//├── products_catalog
//│      ├── productTable
//│      ├── categoryTable
//│
//├── employee_catalog
//│      ├── employee
//│      ├── department




