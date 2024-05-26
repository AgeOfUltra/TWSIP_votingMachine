package org.voting.votingmachine.model;

import lombok.Data;

@Data
public class User {
    private int id;
    private String name;
    private String phone;
    private String uid;
    private String password;

}
