package com.migs.mycontacts.controller;

import com.migs.mycontacts.dto.ContatoDTO;
import com.migs.mycontacts.exception.ContatoInvalidoException;
import com.migs.mycontacts.service.ContatoService;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;

public class FormContatoController {
    @FXML
    private TextField textId;

    @FXML
    private TextField textNome;

    @FXML
    private TextField textTelefone;

    @FXML
    private TextField textEmail;

    @FXML
    private TextArea textDescricao;

    private boolean ehAdicionar = true;
    private Stage stage;
    private ContatoService contatoService;
    private MainController mainController;

    public void adicionarContato() {
        ehAdicionar = true;
        stage.setTitle("Adicionar Contato");
        stage.show();
    }

    public void editarContato(ContatoDTO contatoDto) {
        ehAdicionar = false;

        textId.setText(contatoDto.id());
        textNome.setText(contatoDto.nome());
        textTelefone.setText(contatoDto.telefone());
        textEmail.setText(contatoDto.email() == null ? "" : contatoDto.email());
        textDescricao.setText(contatoDto.descricao() == null ? "" : contatoDto.descricao());

        stage.setTitle("Editar Contato");
        stage.show();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setContatoService(ContatoService contatoService) {
        this.contatoService = contatoService;
    }

    @FXML
    private void sairTela() {
        int opcao = JOptionPane.showConfirmDialog(null, "Cancelar?", "Cancelar", JOptionPane.YES_NO_OPTION);

        if (opcao == JOptionPane.YES_OPTION) { stage.close(); }
    }

    @FXML
    private void enviarContato() {
        ContatoDTO contatoDto = new ContatoDTO(
            textId.getText().isBlank() ? null : textId.getText(),
            textNome.getText(),
            textTelefone.getText(),
            textEmail.getText().isBlank() ? null : textEmail.getText(),
            textDescricao.getText().isBlank() ? null : textDescricao.getText()
        );

        try {
            if (ehAdicionar) {
                ContatoDTO novoContatoDto = contatoService.salvarContato(contatoDto);
                JOptionPane.showMessageDialog(null, "Contato adicionado com sucesso!");
                mainController.adicionarItem(novoContatoDto);
            }

            else {
                contatoService.editarContato(contatoDto.id(), contatoDto);
                JOptionPane.showMessageDialog(null, "Contato alterado com sucesso!");
                mainController.resetarTabela();
            }
        }

        catch (ContatoInvalidoException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
