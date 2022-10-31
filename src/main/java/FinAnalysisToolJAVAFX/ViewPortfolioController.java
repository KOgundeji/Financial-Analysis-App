package FinAnalysisToolJAVAFX;

import FinAnalysisToolJAVAFX.Applications.AddStockApplication;
import FinAnalysisToolJAVAFX.Applications.DeleteStockApplication;
import FinAnalysisToolJAVAFX.Applications.UpdateStockApplication;
import FinAnalysisToolJAVAFX.Support.DatabaseConnection;
import FinAnalysisToolJAVAFX.Support.PricingAPI;
import FinAnalysisToolJAVAFX.Support.Stock;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class ViewPortfolioController {

    @FXML
    public TableView<Stock> stockTable = new TableView<>();
    @FXML
    public TableColumn<Stock, String> ticker, companyName, shares, price, change_per, change_abs, avg_cost, tot_cost, market_value, gain, days_gain, return_per;
    @FXML
    public Button addButton, updateButton, deleteButton, refreshButton;
    public ObservableList<Stock> data = FXCollections.observableArrayList();
    public ArrayList<String> stock_name_list = new ArrayList<>();
    public HashMap<String, ArrayList<String>> stock_map;
    public String query = "";


    public void initialize() {
        createColumns();
        stockTable.setItems(populateStockTable());
    }

    public ObservableList<Stock> populateStockTable() {
        DecimalFormat currency_cents = new DecimalFormat("$#,##0.00");
        DecimalFormat currency = new DecimalFormat("$#,##0");
        DecimalFormat percentage_decimal = new DecimalFormat("#,##0.00%");
        DecimalFormat percentage = new DecimalFormat("#,##0%");
        DecimalFormat share_format = new DecimalFormat("#,##0");

        try {
            DatabaseConnection database = new DatabaseConnection();
            String populate = "SELECT * FROM stocks";
            ResultSet rs = database.statement.executeQuery(populate);

            while (rs.next()) {
                query += rs.getString("ticker") + ",";
                stock_name_list.add(rs.getString("ticker"));
            }

            database = new DatabaseConnection();
            rs = database.statement.executeQuery(populate);

            PricingAPI pricingAPI = new PricingAPI();
            stock_map = pricingAPI.connectToStockAPI(query, stock_name_list);

            while (rs.next()) {
                ArrayList<String> stock_name_list = stock_map.get(rs.getString("ticker"));
                String ticker = rs.getString("ticker");
                String name = rs.getString("name");
                double price = Double.parseDouble(stock_name_list.get(2));
                double change_per = Double.parseDouble(stock_name_list.get(3)) / 100;
                double change_abs = Double.parseDouble(stock_name_list.get(4));
                double shares = rs.getDouble("shares");
                double total_cost = rs.getDouble("total_cost");
                double avg_cost = total_cost / shares;
                double market_value = shares * price;
                double gain = (market_value - total_cost);
                double days_gain = change_abs * shares;
                double return_per = gain / total_cost;

                data.add(new Stock(ticker, name,
                        currency_cents.format(price),
                        percentage_decimal.format(change_per),
                        currency_cents.format(change_abs),
                        share_format.format(shares),
                        currency_cents.format(avg_cost),
                        currency.format(total_cost),
                        currency.format(market_value),
                        currency.format(gain),
                        currency.format(days_gain),
                        percentage.format(return_per)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public void createColumns() {
        ticker.setCellValueFactory(new PropertyValueFactory<>("ticker"));
        companyName.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        shares.setCellValueFactory(new PropertyValueFactory<>("shares"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        change_per.setCellValueFactory(new PropertyValueFactory<>("change_per"));
        change_abs.setCellValueFactory(new PropertyValueFactory<>("change_abs"));
        avg_cost.setCellValueFactory(new PropertyValueFactory<>("avg_cost"));
        tot_cost.setCellValueFactory(new PropertyValueFactory<>("total_cost"));
        market_value.setCellValueFactory(new PropertyValueFactory<>("market_value"));
        gain.setCellValueFactory(new PropertyValueFactory<>("gain"));
        days_gain.setCellValueFactory(new PropertyValueFactory<>("days_gain"));
        return_per.setCellValueFactory(new PropertyValueFactory<>("return_per"));

        ticker.prefWidthProperty().bind(stockTable.widthProperty().multiply(.05));
        companyName.prefWidthProperty().bind(stockTable.widthProperty().multiply(.15));
        shares.prefWidthProperty().bind(stockTable.widthProperty().multiply(.0775));
        price.prefWidthProperty().bind(stockTable.widthProperty().multiply(.08));
        change_per.prefWidthProperty().bind(stockTable.widthProperty().multiply(.08));
        change_abs.prefWidthProperty().bind(stockTable.widthProperty().multiply(.08));
        avg_cost.prefWidthProperty().bind(stockTable.widthProperty().multiply(.08));
        tot_cost.prefWidthProperty().bind(stockTable.widthProperty().multiply(.08));
        market_value.prefWidthProperty().bind(stockTable.widthProperty().multiply(.08));
        gain.prefWidthProperty().bind(stockTable.widthProperty().multiply(.08));
        days_gain.prefWidthProperty().bind(stockTable.widthProperty().multiply(.08));
        return_per.prefWidthProperty().bind(stockTable.widthProperty().multiply(.08));
    }
    @FXML
    public void addStock(ActionEvent actionEvent) {
        try {
            new AddStockApplication().start(new Stage());
            ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void updateStock(ActionEvent actionEvent) {
        try {
            new UpdateStockApplication().start(new Stage());
            ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void deleteStock(ActionEvent actionEvent) {
        new DeleteStockApplication().start(new Stage());
        ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
    }

    @FXML
    public void refreshPortfolio() {
        stockTable.getItems().clear();
        stockTable.setItems(populateStockTable());
    }

}
