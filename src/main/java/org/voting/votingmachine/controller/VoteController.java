package org.voting.votingmachine.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.Data;
import org.voting.votingmachine.dao.BallotUtils;
import org.voting.votingmachine.model.Elections;
import org.voting.votingmachine.model.Party;
import org.voting.votingmachine.model.User;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

@Data
public class VoteController {

    public Button voteBtn;
    public Text can_sts;
    @FXML
    private BorderPane ballot_vote;

    @FXML
    private GridPane ballot_vote_grid;

    private static Elections elections;

    private static User user;

    protected BallotUtils utils;
    private ToggleGroup toggleGroup;
    private BallotController ballotController;

    private Collection<Party> candidates = new ArrayList<>();
    public void setElectionData(Elections elections,User user,BallotController ballotController) throws SQLException {
        VoteController.elections = elections;
        VoteController.user = user;
        this.ballotController = ballotController;
        setCandidates(elections.getEid());
        initializeCandidates();
    }
    public void initialize() throws SQLException {
        utils = new BallotUtils();
    }
    public void setCandidates(int eid) throws SQLException {
        candidates=utils.getCandidatesOfElection(eid);
    }
    private void initializeCandidates() {
        toggleGroup = new ToggleGroup();
        if(candidates.isEmpty()){
            voteBtn.setDisable(true);
            can_sts.setVisible(true);
            can_sts.setText("No Candidates Yet!");
            return;
        }

        int row = 2;
        for (Party candidate : candidates) {
            RadioButton radioButton = new RadioButton(candidate.getPartyName());
            radioButton.setToggleGroup(toggleGroup);
            ballot_vote_grid.add(radioButton, 1, row);
            row++;
        }

        voteBtn.setOnAction(event -> {
            RadioButton selectedRadioButton = (RadioButton) toggleGroup.getSelectedToggle();
            if (selectedRadioButton != null) {
                String selectedCandidateName = selectedRadioButton.getText();
                candidates.stream()
                        .filter(candidate -> candidate.getPartyName().equals(selectedCandidateName))
                        .findFirst().ifPresent(this::castVote);

            }
        });
    }

    private void castVote(Party selectedParty) {
        try {
            utils.castVote(user.getId(), elections.getEid(), selectedParty.getCandidate_id());
            ballotController.refreshElections();
            Stage stage = (Stage) ballot_vote.getScene().getWindow();
            stage.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public void backToBallot(ActionEvent event) {
        Stage stage = (Stage) ballot_vote.getScene().getWindow();
        stage.close();
    }
}
