package com.migs.mycontacts.mapper;

public interface MapperPersistence<T1, T2> {
    T2 serializar(T1 obj);
    T1 desserializar(T2 obj);
}
