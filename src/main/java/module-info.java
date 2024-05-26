module org.voting.votingmachine {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires java.sql;
    requires org.apache.commons.dbcp2;
    requires static lombok;

    opens org.voting.votingmachine to javafx.fxml;
    exports org.voting.votingmachine;
    exports org.voting.votingmachine.controller;
    opens org.voting.votingmachine.controller to javafx.fxml;
}