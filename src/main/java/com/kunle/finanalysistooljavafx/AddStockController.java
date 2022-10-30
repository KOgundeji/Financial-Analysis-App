package com.kunle.finanalysistooljavafx;

import com.kunle.finanalysistooljavafx.Applications.MainApplication;
import com.kunle.finanalysistooljavafx.Support.DatabaseConnection;
import com.kunle.finanalysistooljavafx.Support.PricingAPI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;

public class AddStockController {

    @FXML
    public TextField create_ticker, create_name, create_shares, create_cost;

    public void onAddStockClick() {
        PricingAPI pricingAPI = new PricingAPI();
        if (pricingAPI.checkNewStock(create_ticker.getText())) {
            try {
                DatabaseConnection database = new DatabaseConnection();

                String check_ticker = "Select * from stocks where ticker = '"
                        + create_ticker.getText() + "'";
                ResultSet rs = database.statement.executeQuery(check_ticker);

                if (!rs.next()) {
                    String command = "INSERT INTO stocks(ticker,name,shares,total_cost,avg_cost) VALUES('"
                            + create_ticker.getText() + "','"
                            + create_name.getText() + "','"
                            + create_shares.getText() + "','"
                            + create_cost.getText() + "','"
                            + (Double.parseDouble(create_cost.getText()) /
                            Double.parseDouble(create_shares.getText())) + "')";

                    System.out.println(command);
                    database.statement.executeUpdate(command);

                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("Successfully added stock");
                    successAlert.show();
                } else {
                    Alert fail = new Alert(Alert.AlertType.ERROR);
                    fail.setHeaderText(null);
                    fail.setContentText("Error: Stock already exists in portfolio");
                    fail.show();
                     
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        create_ticker.setText("");
        create_name.setText("");
        create_shares.setText("");
        create_cost.setText("");
    }

    public void onGoBackClick(ActionEvent actionEvent) {
        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("viewPort.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), bounds.getWidth() - 100, bounds.getHeight() - 100);
            Stage stage = new Stage();
            stage.setTitle("View Portfolio");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
    }
}
