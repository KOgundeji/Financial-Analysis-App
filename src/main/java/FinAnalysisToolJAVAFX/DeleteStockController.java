package FinAnalysisToolJAVAFX;

import FinAnalysisToolJAVAFX.Applications.ViewPortfolioApplication;
import FinAnalysisToolJAVAFX.Support.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DeleteStockController {

    @FXML
    public ComboBox<String> delete_ticker_dropdown;

    public void initialize() {
        fillDropdown();
    }

    public void onDeleteClick(ActionEvent actionEvent) {
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

        Alert successAlert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete " + delete_ticker_dropdown.getValue()
                        + " from your portfolio?",
                yes,no);
        successAlert.setHeaderText(null);
        successAlert.showAndWait();
        if(successAlert.getResult().getText().equals("Yes")) {
            try {
                DatabaseConnection database = new DatabaseConnection();
                // need to add functionality to prevent dropdown name and info being different
                String update = "DELETE from stocks WHERE ticker='" + delete_ticker_dropdown.getValue() + "';";
                System.out.println(update);
                database.statement.executeUpdate(update);

                Alert deleted = new Alert(Alert.AlertType.INFORMATION);
                deleted.setHeaderText(null);
                deleted.setContentText("Successfully deleted " + delete_ticker_dropdown.getValue() + " from your portfolio");
                deleted.showAndWait();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            new ViewPortfolioApplication().start(new Stage());
            ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
        }
    }

    private void fillDropdown() {
        try {
            DatabaseConnection database = new DatabaseConnection();
            String populate = "SELECT * FROM stocks";
            ResultSet rs = database.statement.executeQuery(populate);

            while (rs.next()) {
                delete_ticker_dropdown.getItems().add(rs.getString("ticker"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
