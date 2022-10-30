package com.kunle.finanalysistooljavafx;

import com.kunle.finanalysistooljavafx.Applications.LoginApplication;
import com.kunle.finanalysistooljavafx.Support.DatabaseConnection;
import com.kunle.finanalysistooljavafx.Support.EncryptPassword;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AddAccountController {

    @FXML
    public PasswordField HiddenPassword;
    @FXML
    public TextField VisiblePassword, create_name, create_email, create_username;
    @FXML
    public CheckBox passwordCheckBox;
    private String typedPassword, finalPassword;
    private Alert successAlert;

    @FXML
    public void onCreateAccountClick(ActionEvent actionEvent) {
        if (passwordCheckBox.isSelected()) {
            finalPassword = VisiblePassword.getText();
        } else {
            finalPassword = HiddenPassword.getText();
        }

        try {
            DatabaseConnection database = new DatabaseConnection();
            String command = "INSERT INTO login VALUES('"
                    + create_name.getText() + "','"
                    + create_username.getText() + "','"
                    + EncryptPassword.encrypt(finalPassword, create_username.getText()) + "','"
                    + create_email.getText() + "')";
            System.out.println(command);
            database.statement.executeUpdate(command);
            successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setHeaderText(null);
            successAlert.setContentText("Successfully created account");
            successAlert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource("login.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 250, 300);
            Stage stage = new Stage();
            stage.setTitle("Login");
            stage.setScene(scene);
            stage.show();
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void checkPassword() {
        if (passwordCheckBox.isSelected()) {
            typedPassword = HiddenPassword.getText();
            VisiblePassword.setVisible(true);
            VisiblePassword.setText(typedPassword);
            HiddenPassword.setVisible(false);
        } else {
            typedPassword = VisiblePassword.getText();
            VisiblePassword.setVisible(false);
            HiddenPassword.setText(typedPassword);
            HiddenPassword.setVisible(true);
        }
    }
}
