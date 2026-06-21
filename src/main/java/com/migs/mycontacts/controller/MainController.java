package com.migs.mycontacts.controller;

import com.migs.mycontacts.dto.ContatoDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class MainController {
    @FXML
    private ChoiceBox<String> filtroBuscar;

    @FXML
    private TextField textoBuscar;

    @FXML
    private Button botaoBuscar;

    @FXML
    private Button botaoAdicionar;

    @FXML
    private Button botaoEditar;

    @FXML
    private Button botaoExcluir;

    @FXML
    private TableView<ContatoDTO> tableView;

    @FXML
    private void initialize() {
        filtroBuscar.getItems().addAll("Nome", "Telefone", "E-mail");
    }

    @FXML
    private void botaoBuscar() {

    }
}
