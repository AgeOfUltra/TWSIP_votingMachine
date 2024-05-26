package org.voting.votingmachine.model;

import lombok.Data;

@Data
public class Party {
    private int candidate_id;
    private String candidateName;
    private String partyName;
    private int eid;

}
