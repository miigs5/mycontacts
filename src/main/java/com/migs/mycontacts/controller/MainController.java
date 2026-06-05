package com.migs.mycontacts.controller;

import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;

import com.migs.mycontacts.dto.ContatoDTO;
import com.migs.mycontacts.enums.Menu;
import com.migs.mycontacts.exception.ContatoInvalidoException;
import com.migs.mycontacts.service.ContatoService;
import com.migs.mycontacts.view.MainView;

public class MainController {
    private final MainView mainView;
    private final ContatoService contatoService;

    public MainController(MainView mainView, ContatoService contatoService) {
        if (mainView == null || contatoService == null) {
            throw new NullPointerException("[ERRO] MainView ou ContatoService nao pode ser NULL.");
        }

        this.mainView = mainView;
        this.contatoService = contatoService;
    }

    public void init() {
        mainView.init(this);
    }

    public void handleMenu(int opcao) {
        try {
            Menu opcaoMenu = Menu.deInteiro(opcao-1);
            List<ContatoDTO> contatos = this.contatoService.buscarTodosContatos().values().stream().toList();
            switch (opcaoMenu) {
                case ADICIONAR -> {
                    mainView.criarTitulo("ADICIONAR CONTATO");
                    this.adicionarContato(mainView.receberContato());
                }

                case LISTAR -> { this.listarContatos(); }
                
                case PESQUISAR_POR_NOME -> { 
                    if (contatos.isEmpty()) { mainView.mostrarMensagem("Sem contatos registrados!"); }
                    else { this.pesquisarPorNome(mainView.pesquisarPorNome()); }
                }
                
                case EDITAR -> {
                    if (contatos.isEmpty()) { mainView.mostrarMensagem("Sem contatos registrados!"); }
                    else {
                        ContatoDTO dto = null;

                        do { dto = mainView.escolherContato(contatos); }
                        while (dto == null);

                        mainView.criarTitulo("EDITAR CONTATO");
                        ContatoDTO dtoNovo = mainView.receberContato();
                        dtoNovo = new ContatoDTO(dto.id(), dtoNovo.nome(), dtoNovo.telefones(), dtoNovo.emails(), dtoNovo.descricao());

                        this.editarContato(dto.id(), dtoNovo);
                    }
                }
                
                case REMOVER -> {
                    if (contatos.isEmpty()) { mainView.mostrarMensagem("Sem contatos registrados!"); }
                    else {
                        ContatoDTO dto = null;

                        do { dto = mainView.escolherContato(contatos); }
                        while (dto == null);

                        this.removerContato(dto);
                    }
                }

                case SAIR -> { System.exit(0); }
            }
        }

        catch (IllegalArgumentException e) {
            mainView.mostrarMensagem(e.getMessage());
        }
    }

    public void adicionarContato(ContatoDTO dto) {
        if (dto == null) {
            throw new NullPointerException("DTO nao pode ser NULL.");
        }

        try {
            System.out.println(dto.nome());
            contatoService.salvarContato(dto);
            mainView.mostrarMensagem("Contato adicionado!");
        }

        catch (ContatoInvalidoException e) {
            mainView.mostrarMensagem(e.getMessage());
        }
    }

    public void listarContatos() {
        try {
            List<ContatoDTO> contatos = contatoService.buscarTodosContatos().values().stream().toList();

            if (contatos.isEmpty()) { mainView.mostrarMensagem("Sem contatos registrados!");}
            else { mainView.listarContatos(contatos); }
        }

        catch (ContatoInvalidoException e) {
            mainView.mostrarMensagem(e.getMessage());
        }
    }

    public void pesquisarPorNome(ContatoDTO dto) {
        if (dto == null) {
            throw new NullPointerException("Pesquisa invalida.");
        }

        try {
            List<ContatoDTO> contatos = contatoService.buscarContatoPorNome(dto.nome());

            if (contatos.isEmpty()) { mainView.mostrarMensagem("Contato nao encontrado!");}
            else { mainView.listarContatos(contatos); }
        }

        catch (ContatoInvalidoException e) {
            mainView.mostrarMensagem(e.getMessage());
        }
    }

    public void editarContato(String id, ContatoDTO dto) {
        if (dto == null) {
            throw new NullPointerException("[ERRO] DTO nao pode ser NULL.");
        }

        try {
            contatoService.editarContato(id, dto);
            mainView.mostrarMensagem("Contato editado!");
        }

        catch (ContatoInvalidoException e) {
            mainView.mostrarMensagem(e.getMessage());
        }
    }

    public void removerContato(ContatoDTO dto) {
        if (dto == null) {
            throw new NullPointerException("[ERRO] DTO nao pode ser NULL.");
        }

        try {
            contatoService.removerContato(dto.id());
            mainView.mostrarMensagem("Contato removido!");
        }

        catch (ContatoInvalidoException e) {
            mainView.mostrarMensagem(e.getMessage());
        }
    }

    public boolean confirmarOpcao() {
        return mainView.receberString().trim().toLowerCase().equals("s");
    }

    public ContatoDTO escolherContato(List<ContatoDTO> contatos, int opcao) {
        try {
            return contatos.get(opcao-1);
        }

        catch (IndexOutOfBoundsException e) {
            mainView.mostrarMensagem("[ERRO] Opcao invalida.");
        }

        catch (ContatoInvalidoException e) {
            mainView.mostrarMensagem(e.getMessage());
        }

        return null;
    }

    public int receberInt() {
        try {
            return mainView.receberInt();
        }

        catch (InputMismatchException e) {
            mainView.mostrarMensagem("[ERRO] Valor invalido.");
        }
        
        catch (NoSuchElementException e) {
            mainView.mostrarMensagem("[ERRO] Valor invalido.");
        }

        return -1;
    }

    public String receberString() {
        try {
            return mainView.receberString();
        }

        catch (InputMismatchException e) {
            mainView.mostrarMensagem("[ERRO] Valor invalido.");
        }
        
        catch (NoSuchElementException e) {
            mainView.mostrarMensagem("[ERRO] Valor invalido.");
        }

        return null;
    }
}
