package FinAnalysisToolJAVAFX;

import FinAnalysisToolJAVAFX.Applications.ViewPortfolioApplication;
import FinAnalysisToolJAVAFX.Support.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class UpdateStockController {

    @FXML
    public ComboBox<String> ticker_dropdown;
    @FXML
    public TextField update_name, update_shares, update_cost;
    private final HashMap<String, ArrayList<String>> stock_map = new HashMap<>();
    private ArrayList<String> stock_info_list = new ArrayList<>();

    public void initialize() {
        fillDropdown();

    }

    public void onSubmitClick(ActionEvent actionEvent) {
        try {
            DatabaseConnection database = new DatabaseConnection();
            // need to add functionality to prevent dropdown name and info being different
            String update = "UPDATE stocks SET name = '" + update_name.getText() + "', " +
                    "shares = '" + update_shares.getText() + "', " +
                    "total_cost = '" + update_cost.getText() + "' WHERE (ticker='" +
                    ticker_dropdown.getValue() + "');";
            System.out.println(update);
            database.statement.executeUpdate(update);

            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setHeaderText(null);
            successAlert.setContentText("Successfully updated " + ticker_dropdown.getValue());
            successAlert.show();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void onCancelClick(ActionEvent actionEvent) {
        new ViewPortfolioApplication().start(new Stage());
        ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
    }

    public void onUpdateClick(ActionEvent actionEvent) {
        ArrayList<String> temp_list = stock_map.get(ticker_dropdown.getValue());
        update_name.setText(temp_list.get(0));
        update_shares.setText(temp_list.get(1));
        update_cost.setText(temp_list.get(2));
    }

    private void fillDropdown() {
        try {
            DatabaseConnection database = new DatabaseConnection();
            String populate = "SELECT * FROM stocks";
            ResultSet rs = database.statement.executeQuery(populate);

            while (rs.next()) {
                ticker_dropdown.getItems().add(rs.getString("ticker"));

                stock_info_list.add(String.valueOf(rs.getString("name")));
                stock_info_list.add(String.valueOf(rs.getString("shares")));
                stock_info_list.add(String.valueOf(rs.getString("total_cost")));

                stock_map.put(rs.getString("ticker"), stock_info_list);
                stock_info_list = new ArrayList<>();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
