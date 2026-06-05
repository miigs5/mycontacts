package com.migs.mycontacts.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import com.migs.mycontacts.dto.ContatoDTO;
import com.migs.mycontacts.exception.ContatoInvalidoException;
import com.migs.mycontacts.mapper.MapperDTO;
import com.migs.mycontacts.model.Contato;
import com.migs.mycontacts.model.Nome;
import com.migs.mycontacts.repository.ContatoRepository;

public class ContatoService {
    private final ContatoRepository repo;
    private final MapperDTO<Contato, ContatoDTO> mapper;

    public ContatoService(ContatoRepository repo, MapperDTO<Contato, ContatoDTO> mapper) {
        if (repo == null || mapper == null) {
            throw new NullPointerException("[ERRO] Repository ou Mapper nao pode ser NULL.");
        }

        this.repo = repo;
        this.mapper = mapper;
    }

    public ContatoDTO salvarContato(ContatoDTO dto) {
        if (dto == null) {
            throw new NullPointerException("Contato nao pode ser NULL.");
        }

        Contato contato = mapper.paraEntidade(dto);

        if (contato.getTelefones().stream().anyMatch(
                telefone -> repo.existePorNomeETelefone(contato.getNome(), telefone)
        )) {
            throw new ContatoInvalidoException("Nome e telefone duplicado.");
        }

        contato.setId(UUID.randomUUID());

        return mapper.paraDto(repo.salvar(contato));
    }

    public Map<String, ContatoDTO> buscarTodosContatos() {
        return repo.buscarTodos().entrySet().stream()
            .collect(Collectors.toUnmodifiableMap(
                entry -> entry.getKey().toString(),
                entry -> mapper.paraDto(entry.getValue())
            ));
    }

    public Optional<ContatoDTO> buscarContatoPorId(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("ID invalido.");
        }

        Optional<Contato> contato = repo.buscarPorId(UUID.fromString(id));
        return contato.map(mapper::paraDto);
    }

    public List<ContatoDTO> buscarContatoPorNome(String nome) {
        List<Contato> contatos = repo.buscarPorNome(new Nome(nome));
        return contatos.stream().map(mapper::paraDto).toList();
    }

    public ContatoDTO editarContato(String id, ContatoDTO dto) {
        if (id == null || dto == null) {
            throw new NullPointerException("ID ou Contato nao deve ser NULL.");
        }

        Contato contato = mapper.paraEntidade(dto);
        if (contato.getTelefones().stream().anyMatch(
            telefone -> repo.existePorNomeETelefone(contato.getNome(), telefone)
        )) {
            throw new ContatoInvalidoException("Dados duplicados.");
        }

        Contato contatoRetorno = repo.editar(UUID.fromString(id), contato);
        return mapper.paraDto(contatoRetorno);
    }

    public ContatoDTO removerContato(String id) {
        if (id == null) {
            throw new NullPointerException("ID nao pode ser NULL.");
        }

        return mapper.paraDto(repo.remover(UUID.fromString(id)));
    }
}
