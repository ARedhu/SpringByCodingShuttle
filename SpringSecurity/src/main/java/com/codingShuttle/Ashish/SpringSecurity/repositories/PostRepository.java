package com.codingShuttle.Ashish.SpringSecurity.repositories;

import com.codingShuttle.Ashish.SpringSecurity.entities.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
}
