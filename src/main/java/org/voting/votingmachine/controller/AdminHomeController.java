package org.voting.votingmachine.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.Data;
import org.voting.votingmachine.dao.BallotUtils;
import org.voting.votingmachine.model.Elections;
import org.voting.votingmachine.model.Party;
import org.voting.votingmachine.model.Status;

import java.sql.SQLException;

@Data
public class AdminHomeController {
    public BorderPane home_bdr;
    public TextField ename;
    public TextField estatus;
    public Button saveBtn;
    public Text ele_sts;
    public TextField cname;
    public TextField party_name;
    public Button candidate_save;
    public Text eid;
    public TextField create_eid;
    public Text candidate_sts;
    public TextField delete_eid;
    public Text delete_ele_sts;
    public Button candidate_delete;
    public TextField cid_delete;
    public Text sts_can_delete;
    public Text ele_result;
    public TextField election_name;
    public Button getIdBtn;
    public Tab createEleTab;
    public Tab createCanTab;
    public Tab delEleTab;
    public Tab delCanTab;
    public TextField delete_can_eid;
    public TextField sts_election_id;
    public Text change_sts_sts;
    public TextField new_status_ele;
    public Tab election_sts_tab;

    protected BallotUtils utils;

    private Elections election;

    public void initialize(){
        utils = new BallotUtils();
        initializeTabListeners();
    }

    private Stage stage;
    public void setStageData(Stage stage) {
        this.stage = stage;
    }

    public void saveElection(ActionEvent event) {
        ele_sts.setVisible(true);
        String eName = ename.getText();
        String eStatus = estatus.getText();

        if(eName.isEmpty() || eStatus.isEmpty()){
            ele_sts.setText("Please Enter the Name and /or Status of the election");
            ele_sts.setVisible(false);
        }

        if(!eName.isEmpty() && !eStatus.isEmpty()){
            ele_sts.setText("");
            if(eStatus.equals("active") || eStatus.equals("inactive")){
                election = new Elections();
                election.setName(eName);
                election.setStatus(Status.valueOf(eStatus));
                try {
                    utils.saveElection(election);
                    ele_sts.setText("");
                    ele_result.setText("");
                    try{
                        ele_result.setVisible(true);
                        election.setEid(utils.getElectionId(election.getName()));
                        ele_sts.setVisible(false);
                        ele_result.setText("Election ID : "+election.getEid());
                        ename.clear();
                        estatus.clear();
                    } catch (Exception e) {
                        System.out.println("An error occurred while getting eid");
                        throw new RuntimeException(e);
                    }
                } catch (SQLException e) {
                    System.out.println("error while saving the election ");
                    System.out.println(e.getMessage());
                }


            }else{
                ele_sts.setText("Status can of active or inactive");
            }
        }
    }

    public void candidateBtnDelete(ActionEvent event) throws SQLException {
        sts_can_delete.setVisible(true);
        String eid = delete_can_eid.getText();
        String id = cid_delete.getText();
        if(eid.isEmpty() || id.isEmpty()){

            sts_can_delete.setText("Please provide credential");
            return;
        }
        int electionId ;
        int candidateId;
        try{
            electionId = Integer.parseInt(eid);
            candidateId = Integer.parseInt(id);

        }catch(NumberFormatException e){
            sts_can_delete.setText("Please Enter Number");
            return;
        }

        String res = utils.deleteCandidate(electionId,candidateId);
        if(res.equals("yes")){
            sts_can_delete.setText("Deleted Candidate Successfully");
        }else{
            sts_can_delete.setText("No record found!");
        }
        delete_can_eid.clear();
        cid_delete.clear();


    }

    public void getIdOfElection(ActionEvent event) throws SQLException {
        ele_sts.setVisible(true);
        ele_sts.setText("");
        String eName= election_name.getText();
        if(eName.isEmpty()){
            ele_sts.setText("Enter exact Election Name");
            return;
        }
        int id = utils.getElectionId(eName);
        if(id==-1){
            ele_sts.setText("Election ID not found");
            ele_result.setVisible(false);
        }else{
            ele_result.setVisible(true);
            ele_sts.setVisible(false);
            ele_result.setText("Election ID : "+id);
            election_name.clear();

        }
    }

    public void deleteElection(ActionEvent event) throws SQLException {
        String id = delete_eid.getText();
        delete_ele_sts.setVisible(true);
        delete_ele_sts.setText("");
        int eid;
        try{
            eid = Integer.parseInt(id);
        }catch (NumberFormatException e){
            delete_ele_sts.setText("Enter Number");
            System.out.println(e.getMessage());
            return;
        }

        if(id.isEmpty()){
            delete_ele_sts.setText("Enter the Election ID");
        }else{
            String res = utils.deleteElection(eid);
            if(res.equals("yes")){
                delete_ele_sts.setText("Deleted SuccessFully");
                delete_eid.clear();
            }else{
                delete_ele_sts.setText("No record found!");
                delete_eid.clear();
            }
        }


    }

    private void initializeTabListeners() {
        createEleTab.setOnSelectionChanged(event -> {
            if (createEleTab.isSelected()) {
                clearAndDisableFields();
            }
        });

        createCanTab.setOnSelectionChanged(event -> {
            if (createCanTab.isSelected()) {
                clearAndDisableFields();
            }
        });

        delEleTab.setOnSelectionChanged(event -> {
            if (delEleTab.isSelected()) {
                clearAndDisableFields();
            }
        });

        delCanTab.setOnSelectionChanged(event -> {
            if (delCanTab.isSelected()) {
                clearAndDisableFields();
            }
        });
        election_sts_tab.setOnSelectionChanged(event -> {
            if (election_sts_tab.isSelected()) {
                clearAndDisableFields();
            }
        });
    }

    private void clearAndDisableFields() {
        ele_sts.setVisible(false);
        ele_result.setVisible(false);
        delete_ele_sts.setVisible(false);
        candidate_sts.setVisible(false);
        sts_can_delete.setVisible(false);
        change_sts_sts.setVisible(false);
    }

    public void addCandidate(ActionEvent event) {
        candidate_sts.setVisible(true);
        String eid = create_eid.getText();
        String cName = cname.getText();
        String partyName = party_name.getText();

        if(eid.isEmpty() || cName.isEmpty()|| partyName.isEmpty()){
            candidate_sts.setText("Please fill the fields");
            return;
        }
        int election_id;
        try{
            election_id = Integer.parseInt(eid);
        }catch (NumberFormatException e){
            candidate_sts.setText("Election Id should be number");
            return;
        }

        Elections curr;
        try{
            curr = utils.getElection(election_id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(curr==null){
            candidate_sts.setText("Election ID not found");
            create_eid.clear();
            cname.clear();
            party_name.clear();
            return;
        }
        Party party = new Party();
        party.setPartyName(partyName);
        party.setCandidateName(cName);
        party.setEid(election_id);
        try{
            utils.saveParty(party);
            candidate_sts.setText("Candidate saved successfully") ;
            create_eid.clear();
            cname.clear();
            party_name.clear();

        } catch (SQLException e) {
            candidate_sts.setText("Unable to save the Candidate");
            System.out.println("Unable to save the Candidate");
        }

    }

    public void exit(ActionEvent event) {
        Stage stage1 = (Stage) home_bdr.getScene().getWindow();
        stage1.close();
    }

    public void changeSts(ActionEvent event) throws SQLException {
        String election_id = sts_election_id.getText();
        String new_sts = new_status_ele.getText();

        change_sts_sts.setVisible(true);
        change_sts_sts.setText("");
        if(election_id.isEmpty() || new_sts.isEmpty()){
            change_sts_sts.setText("Please enter valid details ");
            return;
        }
        int eid;
        try{
            eid = Integer.parseInt(election_id);
        }catch (NumberFormatException e){
            change_sts_sts.setText("Enter Valid Election Id");
            sts_election_id.clear();
            return;
        }

        Elections election = null ;
        try {
            election = utils.getElection(eid);
        } catch (SQLException e) {
            sts_election_id.clear();
            System.out.println("Unable to find record");
        }


        if(election==null){
            change_sts_sts.setText("Election record not found");
            sts_election_id.clear();
            new_status_ele.clear();
            return;
        }

//        if all true casess ok
        if(new_sts.equalsIgnoreCase("active") || new_sts.equalsIgnoreCase("inactive")){
            election.setStatus(Status.valueOf(new_sts));
            boolean res = utils.updateElectionSts(election);
            if(res){
                change_sts_sts.setText("Election record updated successfully");
                sts_election_id.clear();
                new_status_ele.clear();
            }
        }else{
            change_sts_sts.setText("status can of active or inactive");
        }


    }
}
