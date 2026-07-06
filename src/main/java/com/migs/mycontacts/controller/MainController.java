package com.migs.mycontacts.controller;

import com.migs.mycontacts.dto.ContatoDTO;
import com.migs.mycontacts.exception.ContatoInvalidoException;
import com.migs.mycontacts.mapper.ContatoDTOMapper;
import com.migs.mycontacts.repository.sqlite.ContatoDAO;
import com.migs.mycontacts.service.ContatoService;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.util.List;

public class MainController {
    @FXML
    private TextField textoBuscar;

    @FXML
    private TableView<ContatoDTO> tabelaContatos;

    @FXML
    private TableColumn<ContatoDTO, String> colunaId;

    @FXML
    private TableColumn<ContatoDTO, String> colunaNome;

    @FXML
    private TableColumn<ContatoDTO, String> colunaTelefone;

    @FXML
    private TableColumn<ContatoDTO, String> colunaEmail;

    @FXML
    private TableColumn<ContatoDTO, String> colunaDescricao;

    private ObservableList<ContatoDTO> contatosList;
    private ContatoService contatoService;
    private FormContatoController formContatoController;

    public void adicionarItem(ContatoDTO contatoDto) {
        contatosList.add(contatoDto);
    }

    @FXML
    private void initialize() throws IOException {
        contatoService = new ContatoService(new ContatoDAO(), ContatoDTOMapper.INSTANCE);

        colunaId.setCellValueFactory(coluna -> new SimpleObjectProperty<>(coluna.getValue().id()));
        colunaNome.setCellValueFactory(coluna -> new SimpleObjectProperty<>(coluna.getValue().nome()));
        colunaTelefone.setCellValueFactory(coluna -> new SimpleObjectProperty<>(coluna.getValue().telefone()));
        colunaEmail.setCellValueFactory(coluna -> new SimpleObjectProperty<>(coluna.getValue().email()));
        colunaDescricao.setCellValueFactory(coluna -> new SimpleObjectProperty<>(coluna.getValue().descricao()));

        contatosList = FXCollections.observableArrayList();
        contatosList.addAll(contatoService.buscarTodosContatos().values());

        tabelaContatos.setItems(contatosList);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/migs/mycontacts/controller/FormContato.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Adicionar Contato");
        stage.setMinWidth(400);
        stage.setMinHeight(500);

        formContatoController = loader.getController();
        formContatoController.setStage(stage);
        formContatoController.setMainController(this);
        formContatoController.setContatoService(this.contatoService);
    }

    @FXML
    private void buscarContato() {
        String texto = textoBuscar.getText();
        if (texto.isBlank()) {
            JOptionPane.showMessageDialog(null, "Insira um nome.");
            return;
        }

        try {
            List<ContatoDTO> lista = contatoService.buscarContatoPorNome(texto);

            if (lista.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nenhum contato encontrado.");
                this.resetarTabela();
            }

            else { contatosList.setAll(lista); }
        }

        catch (ContatoInvalidoException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    @FXML
    private void adicionarContato() {
        formContatoController.adicionarContato();
    }

    @FXML
    private void editarContato() {
        ContatoDTO contato = tabelaContatos.getSelectionModel().getSelectedItem();
        if (contato == null) {
            JOptionPane.showMessageDialog(null, "Selecione um contato.");
            return;
        }

        formContatoController.editarContato(contato);
    }

    @FXML
    private void excluirContato() {
        ContatoDTO contato = tabelaContatos.getSelectionModel().getSelectedItem();
        if (contato == null) {
            JOptionPane.showMessageDialog(null, "Selecione um contato.");
            return;
        }

        String mensagem = """
        Deseja apagar o contato?
        
        Nome: %s
        Telefone: %s
        E-mail: %s
        """.formatted(contato.nome(), contato.telefone(), contato.email());

        int opcao = JOptionPane.showConfirmDialog(null, mensagem, "Excluir o contato?", JOptionPane.YES_NO_OPTION);

        if (opcao == JOptionPane.NO_OPTION) { return; }

        try {
            contatoService.removerContato(contato.id());
            contatosList.remove(contato);
        }

        catch (ContatoInvalidoException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    @FXML
    public void resetarTabela() {
        contatosList.setAll(contatoService.buscarTodosContatos().values());
        tabelaContatos.setItems(contatosList);
    }
}
