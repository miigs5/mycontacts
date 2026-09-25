package com.migs.mycontacts.security;

import com.migs.mycontacts.entity.UsuarioEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.UUID;

@Component
@RequestScope
public class UsuarioAutenticado {
    public UsuarioEntity getUsuario() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof UsuarioEntity)) {
            throw new AccessDeniedException("Usuario nao logado.");
        }
        return (UsuarioEntity) auth.getPrincipal();
    }

    public UUID getId() {
        return getUsuario().getId();
    }
}
