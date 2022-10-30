package com.kunle.finanalysistooljavafx.Applications;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AddAccountApplication extends Application {

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(AddAccountApplication.class.getResource("addAccount.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 300, 350);
            stage.setTitle("Add Account");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main() {
        launch();
    }
}
