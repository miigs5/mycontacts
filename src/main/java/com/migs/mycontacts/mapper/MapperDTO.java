package com.migs.mycontacts.mapper;

public interface MapperDTO<E, D> {
    E paraEntidade(D obj);
    D paraDto(E obj);
}
