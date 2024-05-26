package org.voting.votingmachine.dao;

import org.voting.votingmachine.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import static org.voting.votingmachine.dao.DataBaseUtil.dSource;

public class BallotUtils {
    public void saveUser(User user) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        try{
            con = dSource.getConnection();
            String query  = "insert into users(name, phone, uid, password) values(?,?,?,?)";
            ps = con.prepareStatement(query);
            ps.setString(1, user.getName());
            ps.setString(2, user.getPhone());
            ps.setString(3, user.getUid());
            ps.setString(4, user.getPassword());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Failed to save user \n "+e.getMessage());
        }
        finally {
            if(ps != null){
                ps.close();
            }
            if(con != null){
                con.close();
            }
        }
    }

    public void saveElection(Elections elections) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        try{
            con = dSource.getConnection();
            String query  = "insert into elections(name, status) values(?,?)";
            ps = con.prepareStatement(query);
            ps.setString(1,elections.getName());
            ps.setString(2,elections.getStatus().toString());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Failed to save election \n "+e.getMessage());

        }
        finally {
            if(ps != null){
                ps.close();
            }
            if(con != null){
                con.close();
            }
        }
    }
    public void saveParty(Party party) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        try{
            con = dSource.getConnection();
            String query  = "insert into candidates (name, party, election_id) values(?,?,?)";
            ps = con.prepareStatement(query);
            ps.setString(1,party.getCandidateName());
            ps.setString(2,party.getPartyName());
            ps.setInt(3,party.getEid());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Failed to save user \n "+e.getMessage());

        }
        finally {
            if(ps != null){
                ps.close();
            }
            if(con != null){
                con.close();
            }
        }
    }
//    public void assignPartyToElection(int eid,int cid) throws SQLException {
//        Connection con = null;
//        PreparedStatement ps = null;
//        try{
//            con = dSource.getConnection();
//            String query  = "insert into ballots (election_id, candidate_id) values(?,?)";
//            ps = con.prepareStatement(query);
//            ps.setInt(1,eid);
//            ps.setInt(2,cid);
//            int res = ps.executeUpdate();
//            if(res > 0){
//                System.out.println("Assigned  Party to the election successfully");
//            }
//        } catch (SQLException e) {
//            System.out.println("Failed to assign election \n "+e.getMessage());
//
//        }
//        finally {
//            if(ps != null){
//                ps.close();
//            }
//            if(con != null){
//                con.close();
//            }
//        }
//    }
    public void castVote(int id,int election_id,int candidate_id) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        try{
            con = dSource.getConnection();
            String query  = "insert into votes(user_id, election_id, candidate_id) values(?,?,?)";
            ps = con.prepareStatement(query);
            ps.setInt(1,id);
            ps.setInt(2,election_id);
            ps.setInt(3,candidate_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Failed to cast  your vote\n "+e.getMessage());
        }
        finally {
            if(ps != null){
                ps.close();
            }
            if(con != null){
                con.close();
            }
        }
    }
    public User getUserByUid(String uid) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user = null;
        try{
            con = dSource.getConnection();
            String query  = "select  * from  users where uid = ?";
            ps = con.prepareStatement(query);
            ps.setString(1,uid);
            rs = ps.executeQuery();
            if(rs.next()){
                user = new User();
                user.setId(rs.getInt("user_id"));
                user.setUid(rs.getString("uid"));
                user.setName(rs.getString("name"));
                user.setPhone(rs.getString("phone"));
                user.setPassword(rs.getString("password"));
            }
            return user;
        } catch (SQLException e) {
            System.out.println("Failed to get User Details \n");
            throw new RuntimeException(e);
        }
        finally {
            if(ps != null){
                ps.close();
            }
            if(con != null){
                con.close();
            }
            if(rs!= null){
                rs.close();
            }
        }
    }
    public Collection<Elections> getAllElections() throws SQLException {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        Collection<Elections> electionsCollection = new ArrayList<>();
        try{
            con = dSource.getConnection();
            String query  = "select  * from  elections";
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            while(rs.next()){
                Elections elections = new Elections();
                elections.setEid(rs.getInt("election_id"));
                elections.setName(rs.getString("name"));
                elections.setStatus(Status.valueOf(rs.getString("status")));
                electionsCollection.add(elections);
            }
            return electionsCollection;
        } catch (SQLException e) {
            System.out.println("Failed to get all the elections list. \n");
            throw new RuntimeException(e);
        }
        finally {
            if(stmt != null){
                stmt.close();
            }
            if(con != null){
                con.close();
            }
            if(rs!= null){
                rs.close();
            }
        }
    }
    public Collection<Party> getCandidatesOfElection(int eid) throws SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Collection<Party> electionCandidates = new ArrayList<>();
        try{
            con = dSource.getConnection();
            String query  = "select * from candidates where election_id=?";
            stmt = con.prepareStatement(query);
            stmt.setInt(1,eid);
            rs = stmt.executeQuery();
            while(rs.next()){
                Party party = new Party();
                party.setCandidate_id(rs.getInt("candidate_id"));
                party.setEid(rs.getInt("election_id"));
                party.setPartyName(rs.getString("party"));
                party.setCandidateName(rs.getString("name"));
                electionCandidates.add(party);
            }
            return electionCandidates;
        } catch (SQLException e) {
            System.out.println("Failed to get all the candidates list. \n");
            throw new RuntimeException(e);
        }
        finally {
            if(stmt != null){
                stmt.close();
            }
            if(con != null){
                con.close();
            }
            if(rs!= null){
                rs.close();
            }
        }
    }
    public boolean hasUserVoted(int userId, int electionId) throws SQLException {
        String query = "SELECT COUNT(*) FROM votes WHERE user_id = ? AND election_id = ?";
        try (Connection connection = dSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            statement.setInt(2, electionId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        }
        return false;
    }
    public int getElectionId(String name) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int electionId = -1;
        try{
            conn = dSource.getConnection();
            String query = "select election_id from elections where name = ?";
            ps= conn.prepareStatement(query);
            ps.setString(1,name);
            rs = ps.executeQuery();
            if(rs.next()){
                electionId = rs.getInt("election_id");
            }

            return electionId;
        } catch (SQLException e) {
            System.out.println("Failed to get election id from elections. \n");
            throw new RuntimeException(e);
        }
        finally {
            if(ps != null){
                ps.close();
            }
            if(conn != null){
                conn.close();
            }
            if(rs != null){
                rs.close();
            }
        }
    }
    public String deleteElection(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        String res1= "no";
        try{
            conn = dSource.getConnection();
            String query  = "delete from elections where election_id = ?";
            ps= conn.prepareStatement(query);
            ps.setInt(1,id);
            int res = ps.executeUpdate();
            if(res > 0){
                System.out.println("Deleted election id "+id);
                res1="yes";
            }
            return res1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            if(ps != null){
                ps.close();
            }
            if(conn != null){
                conn.close();
            }
        }

    }
    public String deleteCandidate(int eid, int id) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        String res1= "no";
        try{
            conn = dSource.getConnection();
            String query  = "delete from candidates where election_id = ? and candidate_id = ?";
            ps= conn.prepareStatement(query);
            ps.setInt(1,eid);
            ps.setInt(2,id);
            int res = ps.executeUpdate();
            if(res > 0){
                System.out.println("Deleted candidate id "+id);
                res1= "yes";
            }
            return res1;
        }catch (SQLException e){
            System.out.println("Failed to delete candidate");
            throw new RuntimeException(e);
        }
        finally {

            if(ps != null){
                ps.close();
            }
            if(conn != null){
                conn.close();
            }
        }
    }
    public Collection<VoteCount> getVotes(int eid) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Collection<VoteCount> voteCounts = new ArrayList<>();
        try{
            conn = dSource.getConnection();
            String query ="select * from vote_count where election_id = ?";
            ps = conn.prepareStatement(query);
            ps.setInt(1,eid);
            rs = ps.executeQuery();
            while (rs.next()){
                VoteCount voteCount = new VoteCount();
                voteCount.setEid(rs.getInt("election_id"));
                voteCount.setCid(rs.getInt("candidate_id"));
                voteCount.setCount(rs.getInt("count"));
                voteCount.setCountId(rs.getInt("count_id"));
                voteCounts.add(voteCount);
            }
            return voteCounts;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            if(ps != null){
                ps.close();
            }
            if(conn != null){
                conn.close();
            }
            if(rs != null){
                rs.close();
            }
        }
    }
    public HashMap<String , Integer> getCountVote(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        HashMap<String , Integer> voteCounts = new HashMap<>();
        try{
            conn = dSource.getConnection();
            String query = "SELECT c.party, COUNT(v.vote_id) AS vote_count FROM votes v " +
                    "JOIN " +
                    "candidates c ON v.candidate_id = c.candidate_id WHERE v.election_id = ? GROUP BY c.party";
            ps = conn.prepareStatement(query);
            ps.setInt(1,id);
            rs = ps.executeQuery();
            while (rs.next()){
                String party = rs.getString("party");
                int voteCount = rs.getInt("vote_count");
                voteCounts.put(party, voteCount);
            }
            return voteCounts;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            if(ps != null){
                ps.close();
            }
            if(conn != null){
                conn.close();
            }
            if(rs != null){
                rs.close();
            }
        }
    }
    public Elections getElection(int eid) throws SQLException{
        Connection con = null;
        PreparedStatement ps = null;
        Elections election = null;

        con = dSource.getConnection();
        String query = "select * from elections where election_id = ?";
        ps = con.prepareStatement(query);
        ps.setInt(1,eid);
        ResultSet rs = ps.executeQuery();
        if (rs.next()){
            election = new Elections();
            election.setEid(rs.getInt("election_id"));
            election.setName(rs.getString("name"));
            election.setStatus(Status.valueOf(rs.getString("status")));
        }
        return election;

    }
    public boolean updateElectionSts(Elections election) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        try{
            con = dSource.getConnection();
            String query = "update elections set status = ? where election_id = ?";
            ps = con.prepareStatement(query);
            ps.setString(1,election.getStatus().toString());
            ps.setInt(2,election.getEid());
            int res = ps.executeUpdate();
            return res > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            if(ps != null){
                ps.close();
            }
            if(con != null){
                con.close();
            }
        }
    }
}
