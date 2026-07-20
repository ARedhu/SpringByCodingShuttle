package com.codingShuttle.Ashish.prod_ready_features.notes;

public class Notes {
}

/*

We learnt about Spring Data Jpa Auditing.
Like at any point with the help of Auditing we can tell that now the latest one the entity looks like this, and it was updated by this persona at that time. But what if we want to know how that entity was looking like in previous version or perviously 4th version. Then we can get this only from Hibernate envers. As it stores each version  of change, even the delete function.

- Spring Data JPA Auditing fills metadata like createdAt, updatedAt, createdBy, and updatedBy.
- Hibernate Envers maintains a complete history of every insert, update, and delete in separate audit tables.


Implementation:
// Step-1: Just include this dependency inside of dependencies.
<dependency>
    <groupId>org.hibernate</groupId>
    <artifactId>hibernate-envers</artifactId>
    <version>7.4.3.Final</version>
    <type>pom</type>
    <scope>compile</scope>
</dependency>

// Step-2: Use @Audited on the top of the entity which you want to be tracked by Hibernate envers.

@NotAudited: If you want some fields to be not auditied simply use it. Envers will not track it.

Note: We can use both JPA auditing and envers together.

Question: What will happen insideo of DB?
Ans: It will create one extra table for all i.e. revinfo. And, it will create one more table for each @Audited table by enver and that is like: post_AUD. But remember the database will fill very fast.
 */
