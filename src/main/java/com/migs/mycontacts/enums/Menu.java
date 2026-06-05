package com.migs.mycontacts.enums;

public enum Menu {
    ADICIONAR,
    LISTAR,
    PESQUISAR_POR_NOME,
    EDITAR,
    REMOVER,
    SAIR;

    public static Menu deInteiro(int num) {
        switch (num) {
            case 0 -> { return ADICIONAR; }
            case 1 -> { return LISTAR; }
            case 2 -> { return PESQUISAR_POR_NOME; }
            case 3 -> { return EDITAR; }
            case 4 -> { return REMOVER; }
            case 5 -> { return SAIR; }
            default -> {
                throw new IllegalArgumentException("[ERRO] Opcao invalida.");
            }
        }
    }
}
