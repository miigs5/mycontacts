package com.migs.mycontacts.security;

import com.migs.mycontacts.dto.request.usuario.UsuarioLoginRequestDTO;
import com.migs.mycontacts.dto.response.UsuarioResponseDTO;
import com.migs.mycontacts.entity.UsuarioEntity;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AuthService {

    @NonNull
    private TokenService tokenService;

    @NonNull
    private AuthenticationManager authManager;

    public UsuarioResponseDTO autenticar(UsuarioLoginRequestDTO dto) {
        var authToken = new UsernamePasswordAuthenticationToken(dto.username(), dto.password());
        var auth = authManager.authenticate(authToken);
        var user = (UsuarioEntity) auth.getPrincipal();
        var token = tokenService.gerarToken(user);

        return new UsuarioResponseDTO(token, user);
    }
}
