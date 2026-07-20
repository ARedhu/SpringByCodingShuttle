package com.codingShuttle.Ashish.prod_ready_features.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;


@Entity
@Table(name = "posts")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter // If we don't use getter/setter then we will not be able to convert DTO <-> Entity.
@Audited
public class PostEntity extends AuditableEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @NotAudited      // This will not be tracked by hibernate envers.
    private String description;

    // Internal working or auditing:
    // Spring Data JPA is the one who does this auditing of us, neither service nor hibernate.
    // The AuditingEntityListener class will call the two methods before save();
    // 1.
    // @PrePersist // annotation
    // touchForCreate() // method
    // 2.
    // @PreUpdate // annotation
    // touchForUpdate() // method.

    // We can create our own methods also using these annotations and there are few other annotations as well like @PreRemove. We can change the method name.

    // Annotation is very important in springboot.
}
