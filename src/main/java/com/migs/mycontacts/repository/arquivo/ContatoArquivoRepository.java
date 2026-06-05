package com.migs.mycontacts.repository.arquivo;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.migs.mycontacts.mapper.ContatoArquivoMapper;
import com.migs.mycontacts.mapper.MapperPersistence;
import com.migs.mycontacts.model.Contato;
import com.migs.mycontacts.model.Nome;
import com.migs.mycontacts.model.Telefone;
import com.migs.mycontacts.repository.ContatoRepository;

public class ContatoArquivoRepository
    extends ArquivoRepository<UUID, Contato> implements ContatoRepository {

    public ContatoArquivoRepository() {
        super(Path.of("contatos.txt"));
    }

    public UUID gerarId() { return UUID.randomUUID(); }

    public UUID getId(Contato contato) { return contato.getId(); }

    public void setId(Contato contato, UUID id) { contato.setId(id); }

    public MapperPersistence<Contato, String> getMapper() { return ContatoArquivoMapper.INSTANCE; }

    public boolean existePorNomeETelefone(Nome nome, Telefone telefone) {
        for (Contato c : dados.values()) {
            if (c.getNome().equals(nome)) { return true; }
        }

        return false;
    }

    public List<Contato> buscarPorNome(Nome nome) {
        List<Contato> contatos = new ArrayList<>();
        for (Contato c : dados.values()) {
            if (c.getNome().equals(nome)) { contatos.add(c); }
        }

        return contatos;
    }
}
