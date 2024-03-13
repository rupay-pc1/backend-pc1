package com.pc1.backendrupay.repositories;

import com.pc1.backendrupay.domain.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This interface represents the repository for user data access.
 */
public interface UserRepository extends JpaRepository<UserModel, Long> {
}
