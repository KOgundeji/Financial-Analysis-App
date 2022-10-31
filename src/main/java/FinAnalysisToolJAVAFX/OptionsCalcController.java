package FinAnalysisToolJAVAFX;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.apache.commons.math3.distribution.NormalDistribution;

public class OptionsCalcController {

    @FXML
    public TextField spot, strike, vol, rf, time;
    @FXML
    public Label callPrice, putPrice;
    @FXML
    public Button calculateButton, exitButton;

    @FXML
    public void calculate(ActionEvent action) {
        if (action.getSource() == calculateButton) {
            callPrice.setText(calc_call());
            putPrice.setText(calc_put());
        } else if (action.getSource() == exitButton) {
            exitButton.getScene().getWindow().hide();
        }
}

    private String calc_call() {
        //use black-scholes model to calculate call option price
        //the delta_first_part part of the equation (e^-qt) is irrelevant because we assume dividends = 0. Equation always 1
        Double spotD = Double.parseDouble(spot.getText());
        Double strikeD = Double.parseDouble(strike.getText());
        Double rfD = Double.parseDouble(rf.getText())/100;
        Double volD = Double.parseDouble(vol.getText())/100;
        Double timeD = Double.parseDouble(time.getText())/365;
        //perfect place for unit testing

        Double delta_first_part = Math.log(spotD / strikeD);
        Double delta_second_part = timeD * (rfD + Math.pow(volD, 2) / 2);
        Double delta_third_part = volD * Math.pow(timeD, .5);


        Double d1 = (delta_first_part + delta_second_part) / delta_third_part;
        Double d2 = d1 - volD * Math.pow(timeD,.5);

        NormalDistribution norm_dist_d1 = new NormalDistribution();
        NormalDistribution norm_dist_d2 = new NormalDistribution();
        Double Nd1 = norm_dist_d1.cumulativeProbability(d1);
        Double Nd2 = norm_dist_d2.cumulativeProbability(d2);

        Double theAnswer = (Nd1 * spotD) - (Nd2 * strikeD * 1/Math.exp(rfD*timeD));

        return String.format("$%.2f", theAnswer);

    }
    private String calc_put() {
        //use black-scholes model to calculate call option price
        //the delta_first_part part of the equation (e^-qt) is irrelevant because we assume dividends = 0. Equation always 1
        Double spotD = Double.parseDouble(spot.getText());
        Double strikeD = Double.parseDouble(strike.getText());
        Double rfD = Double.parseDouble(rf.getText()) / 100;
        Double volD = Double.parseDouble(vol.getText()) / 100;
        Double timeD = Double.parseDouble(time.getText()) / 365;
        //perfect place for unit testing

        Double delta_first_part = Math.log(spotD / strikeD);
        Double delta_second_part = timeD * (rfD + Math.pow(volD, 2) / 2);
        Double delta_third_part = volD * Math.pow(timeD, .5);


        Double d1 = (delta_first_part + delta_second_part) / delta_third_part;
        Double d2 = d1 - volD * Math.pow(timeD, .5);

        NormalDistribution norm_dist_d1_put = new NormalDistribution();
        NormalDistribution norm_dist_d2_put = new NormalDistribution();
        Double N_neg_d1 = norm_dist_d1_put.cumulativeProbability(-d1);
        Double N_neg_d2 = norm_dist_d2_put.cumulativeProbability(-d2);

        Double theAnswer = (N_neg_d2 * strikeD * 1 / Math.exp(rfD * timeD)) - (N_neg_d1 * spotD);

        return String.format("$%.2f", theAnswer);
    }
}
