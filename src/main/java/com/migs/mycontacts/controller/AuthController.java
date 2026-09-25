package com.migs.mycontacts.controller;

import com.migs.mycontacts.controller.interfaces.AuthControllerInterface;
import com.migs.mycontacts.dto.request.usuario.UsuarioCadastroRequestDTO;
import com.migs.mycontacts.dto.request.usuario.UsuarioLoginRequestDTO;
import com.migs.mycontacts.dto.response.UsuarioResponseDTO;
import com.migs.mycontacts.security.AuthService;
import com.migs.mycontacts.service.UsuarioService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController implements AuthControllerInterface {

    @NonNull
    private AuthService authService;

    @NonNull
    private UsuarioService usuarioService;

    @PostMapping("/user")
    @Override
    public ResponseEntity<UsuarioResponseDTO> cadastrar(
        UsuarioCadastroRequestDTO dto,
        UriComponentsBuilder uriBuilder
    ) {
        UsuarioResponseDTO usuario = usuarioService.salvarUsuario(dto);
        URI uri = uriBuilder.path("/{id}").buildAndExpand(usuario.id()).toUri();

        return ResponseEntity.created(uri).body(usuario);
    }

    @PostMapping("/login")
    @Override
    public ResponseEntity<UsuarioResponseDTO> login(UsuarioLoginRequestDTO dto) {
        return ResponseEntity.ok(authService.autenticar(dto));
    }
}
