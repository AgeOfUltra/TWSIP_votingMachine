package org.voting.votingmachine.model;

import lombok.Data;

@Data
public class VoteCount {
    private int countId;
    private int eid;
    private int cid;
    private  int count;
}
