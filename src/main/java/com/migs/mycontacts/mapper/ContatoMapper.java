package com.migs.mycontacts.mapper;

import com.migs.mycontacts.dto.request.contato.AtualizarContatoRequestDTO;
import com.migs.mycontacts.dto.request.contato.CriarContatoRequestDTO;
import com.migs.mycontacts.dto.response.ContatoResponseDTO;
import com.migs.mycontacts.entity.ContatoEntity;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContatoMapper {
    ContatoEntity paraEntidade(CriarContatoRequestDTO entity);
    ContatoResponseDTO paraDto(ContatoEntity dto);
}
