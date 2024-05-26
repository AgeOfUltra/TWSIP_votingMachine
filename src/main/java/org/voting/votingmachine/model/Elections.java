package org.voting.votingmachine.model;

import lombok.Data;

@Data
public class Elections {
    private int eid;
    private String name;
    private Status status;
}
