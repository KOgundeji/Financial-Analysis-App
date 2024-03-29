package FinAnalysisToolJAVAFX.Applications;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class UpdateStockApplication extends Application {
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(AddAccountApplication.class.getResource("updateStock.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 450, 350);
            stage.setTitle("Update Stock");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
