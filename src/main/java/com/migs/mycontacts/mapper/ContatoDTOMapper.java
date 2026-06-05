package com.migs.mycontacts.mapper;

import com.migs.mycontacts.model.Contato;
import com.migs.mycontacts.model.Email;
import com.migs.mycontacts.model.Nome;
import com.migs.mycontacts.model.Telefone;

import java.util.Optional;
import java.util.UUID;

import com.migs.mycontacts.dto.ContatoDTO;

public enum ContatoDTOMapper implements MapperDTO<Contato, ContatoDTO> {
    INSTANCE;
    
    @Override
    public Contato paraEntidade(ContatoDTO dto) {
        return new Contato(
            Optional.ofNullable(dto.id()).map(UUID::fromString).orElse(null),
            new Nome(dto.nome()),
            dto.telefones().stream().map(Telefone::new).toList(),
            dto.emails().stream().map(Email::new).toList(),
            dto.descricao()
        );
    }

    @Override
    public ContatoDTO paraDto(Contato contato) {
        return new ContatoDTO(
            contato.getId().toString(),
            contato.getNome().nome(),
            contato.getTelefones().stream().map(Telefone::telefone).toList(),
            contato.getEmails().stream().map(Email::email).toList(),
            contato.getDescricao()
        );
    }
}
