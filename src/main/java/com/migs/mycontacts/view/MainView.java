package com.migs.mycontacts.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.migs.mycontacts.controller.MainController;
import com.migs.mycontacts.dto.ContatoDTO;

public class MainView {
    private MainController mainController;
    private final Scanner scanner;
    private final int LIMITE_LINHA = 30;
    private final String SIMBOLO_DIVISOR = "=";

    public MainView(Scanner scanner) {
        if (scanner == null) {
            throw new NullPointerException("Scanner nao deve ser NULL.");
        }

        this.scanner = scanner;
    }

    public void init(MainController mainController) {
        if (mainController == null) {
            throw new NullPointerException("Controller nao deve ser NULL.");
        }

        this.mainController = mainController;

        while (true) {
            System.out.println("""
            \n%s
            1 - ADICIONAR CONTATO
            2 - LISTAR CONTATOS
            3 - PESQUISAR POR NOME
            4 - EDITAR CONTATO
            5 - REMOVER CONTATO
            6 - SAIR
            """.formatted(criarTitulo("MYCONTACTS")));
            
            System.out.print("OPCAO: ");
            int opcao = mainController.receberInt();
            mainController.handleMenu(opcao);
        }
    }

    public void listarContatos(List<ContatoDTO> contatos) {
        int i = 1;
        
        System.out.println("\n" + criarTitulo("CONTATOS"));
        for (ContatoDTO contato : contatos) {
            System.out.println("\n%d. %s".formatted(i, contato.nome()));
            
            int j = 1;
            for (String telefone : contato.telefones()) {
                System.out.println("Telefone #%d: %s".formatted(j, telefone));
                j++;
            }

            j = 1;
            for (String email : contato.emails()) {
                System.out.println("E-mail #%d: %s".formatted(j, email));
                j++;
            }

            if (contato.descricao() != null) { System.out.println("Descricao: " + contato.descricao()); }
            
            i++;
        }
    }

    public ContatoDTO pesquisarPorNome() {
        System.out.println("\n" + criarTitulo("PESQUISA POR NOME"));

        System.out.print("Digite um nome: ");
        String nome = mainController.receberString();

        return new ContatoDTO(null, nome, null, null, null);
    }

    public ContatoDTO receberContato() {
        System.out.print("Nome: ");
        String nome = mainController.receberString();

        List<String> telefones = new ArrayList<>();
        int i = 1;
        do {
            System.out.print("Telefone #%d: ".formatted(i));
            String telefone = mainController.receberString();
            telefones.add(telefone);

            System.out.print("Deseja adicionar um telefone (S/N)? ");
            boolean confirmado = mainController.confirmarOpcao();

            if (!confirmado) { break; }
            i += 1;
        } while (true);

        List<String> emails = new ArrayList<>();
        i = 1;
        do {
            System.out.print("Deseja adicionar um email (S/N)? ");
            boolean confirmado = mainController.confirmarOpcao();
            
            if (!confirmado) { break; }
            
            System.out.print("E-mail #%d: ".formatted(i));
            String email = mainController.receberString();
            emails.add(email);
            i += 1;
        } while (true);

        System.out.print("Deseja adicionar uma descricao (S/N)? ");
        boolean confirmado = mainController.confirmarOpcao();

        String descricao = null;
        if (confirmado) { descricao = scanner.nextLine(); }

        return new ContatoDTO(null, nome, telefones, emails, descricao);
    }

    public ContatoDTO escolherContato(List<ContatoDTO> contatos) {
        listarContatos(contatos);
        System.out.println("\n" + criarDivisor());

        System.out.print("Escolha um contato: ");
        int opcao = mainController.receberInt();

        return mainController.escolherContato(contatos, opcao);
    }

    public void mostrarMensagem(String msg) {
        System.out.println("""
        \n%s
        %s
        %s
        """.formatted(criarDivisor(), msg, criarDivisor()));
    }

    public String criarTitulo(String str) {
        StringBuilder stringBuilder = new StringBuilder(" " + str.trim().toUpperCase() + " ");
        
        while (stringBuilder.length() + 2 <= LIMITE_LINHA) {
            stringBuilder.insert(0, SIMBOLO_DIVISOR);
            stringBuilder.append(SIMBOLO_DIVISOR);
        }

        return stringBuilder.toString();
    }

    public String criarDivisor() {
        return SIMBOLO_DIVISOR.repeat(LIMITE_LINHA);
    }

    public int receberInt() {
        int retornar = scanner.nextInt();
        scanner.nextLine();
        return retornar;
    }
    
    public String receberString() {
        return scanner.nextLine();
    }
}
