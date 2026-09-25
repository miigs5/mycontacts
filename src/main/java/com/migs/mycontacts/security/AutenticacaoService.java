package com.migs.mycontacts.security;

import com.migs.mycontacts.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AutenticacaoService implements UserDetailsService {

    @NonNull
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return usuarioRepository.findByUsername(username).orElseThrow(
            () -> new UsernameNotFoundException("Username nao encontrado: " + username)
        );
    }
}
