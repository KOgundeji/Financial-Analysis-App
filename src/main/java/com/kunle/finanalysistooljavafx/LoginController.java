package com.kunle.finanalysistooljavafx;

import com.kunle.finanalysistooljavafx.Applications.AddAccountApplication;
import com.kunle.finanalysistooljavafx.Applications.MainApplication;
import com.kunle.finanalysistooljavafx.Support.DatabaseConnection;
import com.kunle.finanalysistooljavafx.Support.EncryptPassword;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.prefs.Preferences;

public class LoginController {

    @FXML
    public PasswordField password;
    @FXML
    public TextField username;
    @FXML
    public Button submitButton, createAccountButton;
    @FXML
    public CheckBox rememberMeCheckBox;
    public String savedUserName;

    //springframeword.security.crypto is a good library apparently

    public void initialize() {
        savedUserName = Preferences.userRoot().get("username", "");
        if (!savedUserName.isEmpty()) {
            rememberMeCheckBox.setSelected(true);
            username.setText(savedUserName);
        }
    }

    @FXML
    public void LoginClick(javafx.event.ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(submitButton)) {
            try {
                DatabaseConnection database = new DatabaseConnection();
                String command = "Select * from login where username = '"
                        + username.getText() + "' and password = '"
                        + EncryptPassword.encrypt(password.getText(), username.getText()) + "'";
                ResultSet rs = database.statement.executeQuery(command);
                if (rs.next()) {
                    if (rememberMeCheckBox.isSelected()) {
                        Preferences pref = Preferences.userRoot();
                        pref.put("username", username.getText());
                    }

                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("Login successful");
                    successAlert.showAndWait();
                    //showAndWait waits until the user clicks a button
                    //before executing the rest of the code. Just show() doesn't wait and immediately
                    //hides the window
                    try {
                        System.out.println("Login successful");
                        new MainApplication().start(new Stage());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ((Node) (actionEvent.getSource())).getScene().getWindow().hide();

                } else {
                    Alert failAlert = new Alert(Alert.AlertType.ERROR);
                    failAlert.setContentText("Invalid login");
                    failAlert.setHeaderText(null);
                    failAlert.showAndWait();
                    password.setText("");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void createAccountClick(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(createAccountButton)) {
            new AddAccountApplication().start(new Stage());
            ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
        }
    }

    public void onCheckBoxClick() {
        if (!rememberMeCheckBox.isSelected()) {
            Preferences pref = Preferences.userRoot();
            pref.put("username", "");
        }
    }


}
