package com.migs.mycontacts;

import java.util.Scanner;

import com.migs.mycontacts.controller.MainController;
import com.migs.mycontacts.mapper.ContatoDTOMapper;
import com.migs.mycontacts.repository.ContatoRepository;
import com.migs.mycontacts.repository.arquivo.ContatoArquivoRepository;
import com.migs.mycontacts.service.ContatoService;
import com.migs.mycontacts.view.MainView;

public class MyContacts {
    public static void main(String[] args) {
        ContatoRepository repository = new ContatoArquivoRepository();
        ContatoService service = new ContatoService(repository, ContatoDTOMapper.INSTANCE);
        MainView mainView = new MainView(new Scanner(System.in));
        MainController mainController = new MainController(mainView, service);

        mainController.init();
    }
}
