package com.migs.mycontacts;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MyContacts extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/migs/mycontacts/controller/Main.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("MyContacts");
        stage.setMinHeight(600);
        stage.setMinWidth(900);
        stage.show();
    }

    public static void main(String[] args) { launch(args); }
}
