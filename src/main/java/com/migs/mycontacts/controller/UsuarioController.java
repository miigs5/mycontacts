package com.migs.mycontacts.controller;

import com.migs.mycontacts.controller.interfaces.UsuarioControllerInterface;
import com.migs.mycontacts.dto.request.usuario.AtualizarUsuarioRequestDTO;
import com.migs.mycontacts.dto.request.usuario.UsuarioCadastroRequestDTO;
import com.migs.mycontacts.dto.response.UsuarioResponseDTO;
import com.migs.mycontacts.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/usuarios")
@PreAuthorize("hasRole('USER')")
public class UsuarioController implements UsuarioControllerInterface {

    @NonNull
    private UsuarioService usuarioService;

    @GetMapping("/me")
    @Override
    public ResponseEntity<UsuarioResponseDTO> buscarUsuario() {
        return ResponseEntity.ok(usuarioService.buscarUsuario());
    }

    @PatchMapping("/me")
    @Override
    public ResponseEntity<UsuarioResponseDTO> atualizarUsuario(@Valid @RequestBody AtualizarUsuarioRequestDTO dto) {
        return ResponseEntity.ok(usuarioService.atualizarUsuario(dto));
    }

    @DeleteMapping("/me")
    @Override
    public ResponseEntity<Void> deletarUsuario() {
        usuarioService.deletarUsuario();

        return ResponseEntity.noContent().build();
    }
}
