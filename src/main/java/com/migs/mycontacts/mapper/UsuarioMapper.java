package com.migs.mycontacts.mapper;

import com.migs.mycontacts.dto.request.usuario.UsuarioCadastroRequestDTO;
import com.migs.mycontacts.dto.response.UsuarioResponseDTO;
import com.migs.mycontacts.entity.UsuarioEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    UsuarioEntity paraEntidade(UsuarioCadastroRequestDTO dto);
    UsuarioResponseDTO paraDto(UsuarioEntity entity);
}
