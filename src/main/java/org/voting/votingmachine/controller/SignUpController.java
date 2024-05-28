package org.voting.votingmachine.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
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
public class SignUpController {
    public TextField name;
    public TextField phone;
    public BorderPane signup_bdr;
    public Button signBtn;
    public PasswordField rPin;
    public PasswordField cPin;
    public Text pin_sts;
    public Text signUp_sts;
    public TextField uid;

    private Stage stage;

    protected BallotUtils util;

    private User user;
    public void initialize() {
        util = new BallotUtils();

        signBtn.setDisable(true);
        name.textProperty().addListener((_, _, _) -> checkFields());
        phone.textProperty().addListener((_, _, _) -> checkFields());
        uid.textProperty().addListener((_, _, _) -> checkFields());
        cPin.textProperty().addListener((_, _, _) -> checkFields());
        rPin.textProperty().addListener((_, _, _) -> checkFields());
    }
    private void checkFields() {
        pin_sts.setVisible(true);
        pin_sts.setFont(new Font("Arial", 22));
        boolean allFieldsFilled = !name.getText().isEmpty()
                && !phone.getText().isEmpty() && phone.getText().length()==10
                && !uid.getText().isEmpty()
                && !cPin.getText().isEmpty()
                && !rPin.getText().isEmpty();


        if (allFieldsFilled) {
            pin_sts.setStyle("-fx-text-fill: green;");
            pin_sts.setText("Great");
            enableSignUpButton(true);
        } else {
            if(phone.getText().length()!=10) {
                pin_sts.setText("Phone number should be of 10 digit");
            }else{
                pin_sts.setText("Missing fields");
            }
            pin_sts.setStyle("-fx-text-fill: red;");
            pin_sts.setText("Missed Some Fields");

            enableSignUpButton(false);
        }
    }
    private void enableSignUpButton(boolean enable) {
        signBtn.setDisable(!enable);
    }

    @FXML
    public void backToLogin(ActionEvent actionEvent) throws IOException {
        var loader = new FXMLLoader(getClass().getResource("/org/voting/votingmachine/login.fxml"));
        Parent login_pane = loader.load();

        Stage stage = (Stage) signup_bdr.getScene().getWindow();

        Scene scene = new Scene(login_pane);
        stage.setTitle("Login");
        stage.setScene(scene);

        LoginController login = loader.getController();
        login.setStage(stage);

    }

    @FXML
    public void goToBallot(ActionEvent event) throws IOException, SQLException {

        boolean pin = checkPinSame();
        System.out.println(pin);
        if(pin){

            user = new User();
            user.setUid(uid.getText());
            user.setName(name.getText());
            user.setPhone(phone.getText());
            user.setPassword(cPin.getText());

            util.saveUser(user);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/voting/votingmachine/ballot.fxml"));
            Parent homePane;
            homePane = loader.load();
            Stage stage = (Stage) signup_bdr.getScene().getWindow();
            Scene scene = new Scene(homePane);
            stage.setTitle("Home");
            stage.setScene(scene);

            BallotController home = loader.getController();
            home.setStage(stage);
            home.setUserData(user);
        }else{
            System.out.println("Something went wrong");
        }
    }

    public boolean checkPinSame(){
        boolean status = false;
        pin_sts.setVisible(true);
        if(cPin.getText().equals(rPin.getText())){
            pin_sts.setText("Matched Pin");
            pin_sts.setStyle("-fx-text-fill: green;");
            status = true;
        }else {
            pin_sts.setText("Miss Matched Pin");
            pin_sts.setStyle("-fx-text-fill: red;");
        }
        return status;
    }
}
