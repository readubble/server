package com.capstone.server.Repository;

import com.capstone.server.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findById(String id);
    User save(User user);
    boolean existsByUserNm(String nickname);
    boolean existsById(String id);
}
