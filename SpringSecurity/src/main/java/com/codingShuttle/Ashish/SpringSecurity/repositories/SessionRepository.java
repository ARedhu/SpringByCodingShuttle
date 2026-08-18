package com.codingShuttle.Ashish.SpringSecurity.repositories;

import com.codingShuttle.Ashish.SpringSecurity.entities.Session;
import com.codingShuttle.Ashish.SpringSecurity.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    List<Session> findByUser(User user);

    Optional<Session> findByRefreshToken(String refreshToken);
}
