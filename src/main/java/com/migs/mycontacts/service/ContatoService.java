package com.migs.mycontacts.service;

import java.util.List;
import java.util.UUID;

import com.migs.mycontacts.dto.request.contato.AtualizarContatoRequestDTO;
import com.migs.mycontacts.dto.request.contato.CriarContatoRequestDTO;
import com.migs.mycontacts.dto.response.ContatoResponseDTO;
import com.migs.mycontacts.exception.RegraNegocioException;
import com.migs.mycontacts.mapper.ContatoMapper;
import com.migs.mycontacts.entity.ContatoEntity;
import com.migs.mycontacts.repository.ContatoRepository;
import com.migs.mycontacts.security.UsuarioAutenticado;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Transactional(readOnly = true)
@Service
public class ContatoService {
    @NonNull
    private ContatoRepository repo;

    @NonNull
    private ContatoMapper mapper;

    @NonNull
    private UsuarioAutenticado usuarioLogado;

    @Transactional
    public ContatoResponseDTO salvarContato(CriarContatoRequestDTO dto) {
        if (dto == null) {
            throw new NullPointerException("Contato não pode ser vazio.");
        }

        ContatoEntity contato = mapper.paraEntidade(dto);

        if (repo.existeContatoDuplicado(contato.getNome(), contato.getTelefone(), usuarioLogado.getUsuario(), null)) {
            throw new RegraNegocioException("Nome e telefone duplicado.");
        }

        contato.setUsuario(usuarioLogado.getUsuario());
        return mapper.paraDto(repo.save(contato));
    }

    public List<ContatoResponseDTO> listarContatos() {
        return repo.findByUsuario(usuarioLogado.getUsuario()).stream()
            .map(mapper::paraDto).toList();
    }

    public ContatoResponseDTO buscarContatoPorId(UUID id) {
        if (id == null) {
            throw new NullPointerException("ID não deve ser vazio.");
        }

        return repo.findByIdAndUsuario(id, usuarioLogado.getUsuario()).map(mapper::paraDto)
            .orElseThrow(() -> new RegraNegocioException("Contato inexistente."));
    }

    public List<ContatoResponseDTO> buscarContatoPorNome(String nome) {
        if (nome == null) {
            throw new NullPointerException("Nome não deve ser vazio.");
        }

        List<ContatoEntity> contatos = repo.findByNomeAndUsuario(nome, usuarioLogado.getUsuario());
        return contatos.stream().map(mapper::paraDto).toList();
    }

    public List<ContatoResponseDTO> buscarContatoPorTelefone(String telefone) {
        List<ContatoEntity> contatos = repo.findByTelefoneAndUsuario(telefone, usuarioLogado.getUsuario());

        return contatos.stream().map(mapper::paraDto).toList();
    }

    @Transactional
    public ContatoResponseDTO editarContato(UUID id, AtualizarContatoRequestDTO dto) {
        if (id == null || dto == null) {
            throw new NullPointerException("ID ou Contato não deve ser vazio.");
        }

        ContatoEntity contato = repo.findByIdAndUsuario(id, usuarioLogado.getUsuario()).orElseThrow(
            () -> new EntityNotFoundException("Contato inexistente.")
        );

        String nome = (dto.nome() == null || dto.nome().isBlank()) ? contato.getNome() : dto.nome();
        String telefone = (dto.telefone() == null || dto.telefone().isBlank()) ? contato.getTelefone() : dto.telefone();
        if (repo.existeContatoDuplicado(nome, telefone, usuarioLogado.getUsuario(), id)) {
            throw new RegraNegocioException("Dados duplicados.");
        }

        contato.setNome(nome);
        contato.setTelefone(telefone);

        if (dto.email() != null) { contato.setEmail(dto.email()); }
        if (dto.descricao() != null) { contato.setDescricao(dto.descricao()); }

        return mapper.paraDto(repo.save(contato));
    }

    @Transactional
    public void removerContato(UUID id) {
        if (id == null) {
            throw new NullPointerException("ID não pode ser vazio.");
        }

        repo.deleteByIdAndUsuario(id, usuarioLogado.getUsuario());
    }
}
