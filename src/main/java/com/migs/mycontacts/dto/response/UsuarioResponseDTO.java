package com.migs.mycontacts.dto.response;

import com.migs.mycontacts.entity.UsuarioEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.UUID;

public record UsuarioResponseDTO(

    @Schema(description = "Token de autenticação.")
    String token,

    @Schema(description = "Tipo do token.", example = "Bearer")
    String type,

    @Schema(description = "Lista de nível de autorização do usuário.", example = "['ROLE_USER']")
    List<String> roles,

    UUID id,

    @Schema(description = "Nome do login do usuário.", example = "usuario123")
    String username,

    @Schema(description = "Nome do usuário.", example = "João Fulano")
    String nome,

    @Schema(description = "E-mail do usuário.", example = "joaofulano@email.com")
    String email
) {
    public UsuarioResponseDTO(String token, UsuarioEntity usuario) {
        this(
            token,
            "Bearer",
            usuario.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList(),
            usuario.getId(),
            usuario.getUsername(),
            usuario.getNome(),
            usuario.getEmail()
        );
    }
}
