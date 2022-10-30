package com.kunle.finanalysistooljavafx;

import com.kunle.finanalysistooljavafx.Applications.OptionsCalcApplication;
import com.kunle.finanalysistooljavafx.Applications.ViewPortfolioApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class MainController {

    @FXML
    public MenuItem uploadData, viewData, viewPortfolio, pricePerformance, betaAnalysis,
            optionPricing, strategies, cryptoPrices, exit;
    @FXML
    public MenuBar homeMenuItems;

    public void homeSelection(ActionEvent actionEvent) {
        Object menuItemClick = actionEvent.getSource();
        if (menuItemClick == viewPortfolio) {
            new ViewPortfolioApplication().start(new Stage());
        } else if (menuItemClick == optionPricing) {
            new OptionsCalcApplication().start(new Stage());
        } else if (menuItemClick == exit) {
            homeMenuItems.getScene().getWindow().hide();
        }

    }

}
