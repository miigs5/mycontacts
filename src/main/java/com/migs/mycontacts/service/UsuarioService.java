package com.migs.mycontacts.service;

import com.migs.mycontacts.dto.request.usuario.AtualizarUsuarioRequestDTO;
import com.migs.mycontacts.dto.request.usuario.UsuarioCadastroRequestDTO;
import com.migs.mycontacts.dto.response.UsuarioResponseDTO;
import com.migs.mycontacts.entity.UsuarioEntity;
import com.migs.mycontacts.exception.RegraNegocioException;
import com.migs.mycontacts.mapper.UsuarioMapper;
import com.migs.mycontacts.repository.UsuarioRepository;
import com.migs.mycontacts.security.UsuarioAutenticado;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@AllArgsConstructor
@Transactional(readOnly = true)
@Service
public class UsuarioService {
    @NonNull
    private UsuarioRepository repository;

    @NonNull
    private PasswordEncoder passwordEncoder;

    @NonNull
    private UsuarioMapper mapper;

    @NonNull
    private UsuarioAutenticado usuarioLogado;

    @Transactional
    public UsuarioResponseDTO salvarUsuario(UsuarioCadastroRequestDTO dto) {
        UsuarioEntity entity = mapper.paraEntidade(dto);

        if (repository.existsByUsername(entity.getUsername())) {
            throw new RegraNegocioException("Login existente.");
        }

        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        return mapper.paraDto(repository.save(entity));
    }

    public UsuarioResponseDTO buscarUsuario() {
        UUID id = usuarioLogado.getId();

        return repository.findById(id).map(mapper::paraDto).orElseThrow(
            () -> new EntityNotFoundException("Usuário não encontrado.")
        );
    }

    public UsuarioResponseDTO atualizarUsuario(AtualizarUsuarioRequestDTO dto) {
        if (dto == null) {
            throw new NullPointerException("Usuario não deve ser nulo.");
        }

        if (
            !usuarioLogado.getUsuario().getUsername().equals(dto.username())
            && repository.existsByUsername(dto.username())
        ) {
            throw new RegraNegocioException("Login existente.");
        }

        UsuarioEntity usuario = usuarioLogado.getUsuario();

        if (dto.username() != null) { usuario.setUsername(dto.username()); }
        if (dto.password() != null) { usuario.setPassword(passwordEncoder.encode(dto.password())); }
        if (dto.nome() != null) { usuario.setNome(dto.nome()); }

        return mapper.paraDto(repository.save(usuario));
    }

    public void deletarUsuario() {
        UUID id = usuarioLogado.getId();

        repository.deleteById(id);
    }
}
