package com.kunle.finanalysistooljavafx.Applications;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AddAccountApplication.class.getResource("main.fxml"));
        setUserAgentStylesheet(STYLESHEET_CASPIAN);
        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        Scene scene = new Scene(fxmlLoader.load(), bounds.getWidth()-50, bounds.getHeight()-50);
        stage.setTitle("Financial Home Page");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}
