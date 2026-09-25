package com.migs.mycontacts.repository;

import com.migs.mycontacts.entity.ContatoEntity;
import com.migs.mycontacts.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ContatoRepository extends JpaRepository<ContatoEntity, UUID> {
    @Query("""
        SELECT COUNT(c) > 0
        FROM ContatoEntity c
        WHERE c.usuario = :usuario
            AND (c.id <> :id OR :id IS NULL)
            AND c.nome = :nome
            AND c.telefone = :telefone
    """)
    boolean existeContatoDuplicado(String nome, String telefone, UsuarioEntity usuario, UUID id);

    Optional<ContatoEntity> findByIdAndUsuario(UUID id, UsuarioEntity usuario);
    List<ContatoEntity> findByNomeAndUsuario(String nome, UsuarioEntity usuario);
    List<ContatoEntity> findByTelefoneAndUsuario(String telefone, UsuarioEntity usuario);
    List<ContatoEntity> findByUsuario(UsuarioEntity usuario);
    void deleteByIdAndUsuario(UUID id, UsuarioEntity usuario);
}
