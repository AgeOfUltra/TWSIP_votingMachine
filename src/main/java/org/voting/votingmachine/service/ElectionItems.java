package org.voting.votingmachine.service;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.voting.votingmachine.controller.BallotController;
import org.voting.votingmachine.controller.VoteController;
import org.voting.votingmachine.dao.BallotUtils;
import org.voting.votingmachine.model.Elections;
import org.voting.votingmachine.model.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ElectionItems extends VBox {
    private BallotUtils utils;

    public ElectionItems(Elections election, Stage owner, User user, BallotController ballotController) {
        utils = new BallotUtils();
        Label name = new Label(election.getName());
        name.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

        Label status = new Label(election.getStatus().toString());
        status.setStyle("-fx-font-size: 14px;");

        ImageView dotImg = new ImageView();
        dotImg.setFitWidth(10);
        dotImg.setFitHeight(10);

        if ("inactive".equalsIgnoreCase(election.getStatus().toString())) {
            dotImg.setImage(new Image(getClass().getResourceAsStream("/images/red.png")));
        } else {
            dotImg.setImage(new Image(getClass().getResourceAsStream("/images/green.png")));
        }

        HBox hbox = new HBox(10);
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.getChildren().addAll(dotImg, status);

        Button button = new Button("Cast Vote");
        Button resultButton = new Button("Result");

        button.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/voting/votingmachine/vote.fxml"));
                Scene scene = new Scene(loader.load());
                VoteController voteController = loader.getController();
                voteController.setElectionData(election, user, ballotController);

                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("Cast Vote");
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(owner);
                owner.hide();
                stage.setOnHiding(e -> {
                    owner.show();
                    try {
                        if (utils.hasUserVoted(user.getId(), election.getEid())) {
                            resultButton.setDisable(false);
                            button.setDisable(true);
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                });
                stage.show();
            } catch (IOException | SQLException e) {
                throw new RuntimeException(e);
            }
        });

        resultButton.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Results");
            alert.setHeaderText(null);

            HashMap<String, Integer> electionResults = null;
            try {
                electionResults = utils.getCountVote(election.getEid());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            GridPane resultPane = getGridPane(election, electionResults);

            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.setContent(resultPane);
            alert.showAndWait();
        });

        HBox lower = new HBox(10);
        lower.setAlignment(Pos.CENTER_LEFT);
        lower.setSpacing(20);

        VBox vBox1 = new VBox(10);
        vBox1.setAlignment(Pos.CENTER_LEFT);
        vBox1.setSpacing(15);
        vBox1.getChildren().addAll(hbox);

        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER_LEFT);
        vBox.getChildren().addAll(button, resultButton);
        vBox.setSpacing(5);


        try {
            if (utils.hasUserVoted(user.getId(), election.getEid())) {
                Label votedLabel = new Label("Casted");
                votedLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
                vBox1.getChildren().add(votedLabel);
                button.setDisable(true);
                resultButton.setDisable(false);
            } else {
                button.setDisable("inactive".equalsIgnoreCase(election.getStatus().toString()));
                resultButton.setDisable(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        setPadding(new Insets(10));
        setStyle(
                "-fx-background-color: #ffffff;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.2), 10, 0, 0, 5);" +
                        "-fx-background-radius: 10;" +
                        "-fx-border-radius: 10;"
        );
        lower.getChildren().addAll(vBox1,vBox);
        getChildren().addAll(name, lower);
    }

    private static GridPane getGridPane(Elections election, HashMap<String, Integer> electionResults) {
        Label label = new Label(election.getName());
        GridPane resultPane = new GridPane();
        resultPane.setPadding(new Insets(20));
        resultPane.setHgap(10);
        resultPane.setVgap(10);
        resultPane.add(label, 0, 0);
        int i=2;
        for(Map.Entry<String,Integer> elements : electionResults.entrySet()) {

            Label partyName = new Label(elements.getKey());
            partyName.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
            Label voteCount = new Label(elements.getValue().toString());
            voteCount.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

            resultPane.add(partyName, 0, i);
            resultPane.add(voteCount, 1, i);
            i++;
        }
        return resultPane;
    }
}
