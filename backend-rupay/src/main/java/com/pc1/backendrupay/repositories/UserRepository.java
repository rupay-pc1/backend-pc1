package com.pc1.backendrupay.repositories;

import com.pc1.backendrupay.domain.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID> {
    Optional<UserModel> findByName(String name);
    Optional<UserModel> findByEmail(String email);

}
