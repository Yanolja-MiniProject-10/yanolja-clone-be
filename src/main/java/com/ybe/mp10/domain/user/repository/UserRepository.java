package com.ybe.mp10.domain.user.repository;

import com.ybe.mp10.domain.user.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndIsDeleted(String email, Boolean isDeleted);

    boolean existsByEmail(String email);
}
