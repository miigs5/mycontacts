package com.migs.mycontacts.repository;

import java.util.Map;
import java.util.Optional;

public interface Repository<ID, T> {
    T salvar(T obj);
    Optional<T> buscarPorId(ID id);
    Map<ID, T> buscarTodos();
    T editar(ID id, T obj);
    T remover(ID id);
}
