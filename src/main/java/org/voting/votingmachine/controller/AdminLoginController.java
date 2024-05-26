package org.voting.votingmachine.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.Data;

import java.io.IOException;

@Data
public class AdminLoginController {
    public BorderPane login_bdr;
    public TextField userId;
    public TextField passcode;
    public Button loginBtn;
    public Button backToStart;
    public Text login_sts;

    private Stage stage;

    public void goToAdminHome(ActionEvent event) throws IOException {
        login_sts.setVisible(true);
        String username = userId.getText();
        String password = passcode.getText();
        if(username.isEmpty() || password.isEmpty()) {
            login_sts.setText("Please enter username and/or password");
            return;
        }

        if(username.equals("admin") && password.equals("admin@123")) {
            login_sts.setVisible(false);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/voting/votingmachine/adminHome.fxml"));
            Parent homePage = loader.load();
            Stage stage = (Stage) login_bdr.getScene().getWindow();
            Scene scene = new Scene(homePage);
            stage.setScene(scene);
            stage.setTitle("Admin Home");
            stage.show();

            AdminHomeController homeController = loader.getController();
            homeController.setStageData(stage);

        }else{
            login_sts.setText("Invalid Credentials");

        }

    }

    public void backToStart(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/voting/votingmachine/start.fxml"));
        Parent loginPage = loader.load();
        Stage startStage = (Stage) login_bdr.getScene().getWindow();
        Scene scene = new Scene(loginPage);
        startStage.setScene(scene);
        startStage.setTitle("Start");
        startStage.show();

        StartController controller = loader.getController();
        controller.setStage(startStage);
    }
}
