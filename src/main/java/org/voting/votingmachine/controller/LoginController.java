package org.voting.votingmachine.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.Data;
import org.voting.votingmachine.dao.BallotUtils;
import org.voting.votingmachine.model.User;

import java.io.IOException;
import java.sql.SQLException;

@Data
public class LoginController {
    public BorderPane login_bdr;
    public TextField uid;
    public TextField passcode;
    public Text login_sts;
    private Stage stage;

    private User user;
    protected BallotUtils utils;
    public void initialize(){
        utils = new BallotUtils();
    }
    @FXML
    private void goToBallot(ActionEvent actionEvent) throws IOException {

        login_sts.setVisible(true);
        login_sts.setFont(new Font("Arial", 15));

        String uId = uid.getText();
        String password = passcode.getText();

        if (uId.isEmpty() || password.isEmpty()) {
            login_sts.setStyle("-fx-text-fill: red;");
            login_sts.setText("Please enter account number and PIN");
            return;
        }

        try {
            user = utils.getUserByUid(uId);
            if (user != null) {
                String storedPin = user.getPassword();
                if (storedPin != null && storedPin.equals(password)) {
                    login_sts.setText("Account Found! Logging in...");
                    login_sts.setStyle("-fx-text-fill: green;");

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/voting/votingmachine/ballot.fxml"));
                    Parent ballotPane = loader.load();
                    Stage stage = (Stage) login_bdr.getScene().getWindow();
                    Scene scene = new Scene(ballotPane);
                    stage.setTitle("Home");
                    stage.setScene(scene);

                    BallotController home = loader.getController();
                    home.setStageData(stage);
                    home.setUserData(user);
                } else {
                    login_sts.setText("Wrong PIN! Please try again.");
                    login_sts.setStyle("-fx-text-fill: red;");
                }
            } else {
                login_sts.setText("Account Not Found!");
                login_sts.setStyle("-fx-text-fill: red;");
            }
        } catch (SQLException e) {
            login_sts.setText("Error accessing database.");
            login_sts.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    public void backToSignUp(ActionEvent actionEvent) throws IOException {
        var loader = new FXMLLoader(getClass().getResource("/org/voting/votingmachine/signUp.fxml"));
        Parent signup = loader.load();

        Stage stage = (Stage) login_bdr.getScene().getWindow();

        Scene scene = new Scene(signup);
        stage.setTitle("Signup");
        stage.setScene(scene);

        SignUpController signUpController = loader.getController();
        signUpController.setStage(stage);

    }

}
