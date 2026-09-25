package com.migs.mycontacts.repository;

import com.migs.mycontacts.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, UUID> {
    boolean existsByUsername(String username);
    Optional<UserDetails> findByUsername(String username);
}
