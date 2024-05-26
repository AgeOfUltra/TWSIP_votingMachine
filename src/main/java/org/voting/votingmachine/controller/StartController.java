package org.voting.votingmachine.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lombok.Data;

import java.io.IOException;

@Data
public class StartController {

    public BorderPane start_bdr;

    private Stage stage;

    public void loginPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/voting/votingmachine/login.fxml"));
        Parent login=loader.load();
        Stage stage =(Stage)start_bdr.getScene().getWindow();
        Scene scene = new Scene(login);
        stage.setTitle("User Login");
        stage.setScene(scene);

        LoginController loginController = loader.getController();
        loginController.setStage(stage);
    }

    public void singUpPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/voting/votingmachine/signup.fxml"));
        Parent signup =loader.load();
        Stage stage =(Stage)start_bdr.getScene().getWindow();
        Scene scene = new Scene(signup);
        stage.setTitle("Register New User");
        stage.setScene(scene);

        SignUpController signUpController = loader.getController();
        signUpController.setStage(stage);

    }

    public void loginAdmin(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/voting/votingmachine/adminLogin.fxml"));
        Parent adminLogin = loader.load();
        Stage stage =(Stage)start_bdr.getScene().getWindow();
        Scene scene = new Scene(adminLogin);
        stage.setTitle("Admin Login");
        stage.setScene(scene);
        AdminLoginController adminLoginController = loader.getController();
        adminLoginController.setStage(stage);
    }

}
