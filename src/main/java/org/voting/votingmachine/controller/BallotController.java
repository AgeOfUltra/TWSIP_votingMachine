package org.voting.votingmachine.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import lombok.Data;
import org.voting.votingmachine.dao.BallotUtils;
import org.voting.votingmachine.model.Elections;
import org.voting.votingmachine.model.User;
import org.voting.votingmachine.service.ElectionItems;

import java.sql.SQLException;
import java.util.Collection;

@Data
public class BallotController {
    public BorderPane home_bdr;
    public Tab profileTab;
    public Tab ballot_elections;
    public TextField phone;
    public TextField uid;
    public TextField name;
    public GridPane electionGrid;
    private Stage stage;
    protected static User user;
    protected BallotUtils utils;

    public void setUserData(User user) throws SQLException {
        BallotController.user =user;
        setProfileTab();
        setBallotElectionsTab();
    }
    public void setStageData(Stage stage) {
        this.stage = stage;
    }

    public void initialize() {
        utils = new BallotUtils();
    }
    protected void setProfileTab(){
        name.setText(user.getName());
        phone.setText(user.getPhone());
        uid.setText(user.getUid());
    }
    protected void setBallotElectionsTab() throws SQLException {
        Collection<Elections> electionsCollection = utils.getAllElections();
        int column = 1;
        int row = 1;
        int maxCol = 5;
        int maxRow = 4;
        for(Elections election : electionsCollection){
            if(row<maxRow && column <maxCol){
                ElectionItems items = new ElectionItems(election,stage,user,this);
                electionGrid.add(items, column, row);
                column++;

                if(column == maxCol){
                    column = 1;
                    row++;
                }
            }
        }
    }
    public void refreshElections() {
        try {
            setBallotElectionsTab();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void exit(ActionEvent event) {
        Stage stage = (Stage) home_bdr.getScene().getWindow();
        stage.close();
    }
}
