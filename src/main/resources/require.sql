CREATE DATABASE ovs;
USE ovs;


CREATE TABLE users (
                       user_id INT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       phone VARCHAR(255) NOT NULL UNIQUE,
                       uid VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL
);


CREATE TABLE elections (
                           election_id INT AUTO_INCREMENT PRIMARY KEY,
                           name VARCHAR(255) NOT NULL UNIQUE,
                           status ENUM('active', 'inactive') NOT NULL
);


CREATE TABLE candidates (
                            candidate_id INT AUTO_INCREMENT PRIMARY KEY,
                            name VARCHAR(255) NOT NULL,
                            party VARCHAR(255) NOT NULL,
                            election_id INT,
                            FOREIGN KEY (election_id) REFERENCES elections(election_id) ON DELETE CASCADE
);


CREATE TABLE votes (
                       vote_id INT AUTO_INCREMENT PRIMARY KEY,
                       user_id INT,
                       election_id INT,
                       candidate_id INT,
                       FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
                       FOREIGN KEY (election_id) REFERENCES elections(election_id) ON DELETE CASCADE,
                       FOREIGN KEY (candidate_id) REFERENCES candidates(candidate_id) ON DELETE CASCADE,
                       UNIQUE (user_id, election_id)
);


CREATE TABLE vote_count (
                            count_id INT AUTO_INCREMENT PRIMARY KEY,
                            election_id INT,
                            candidate_id INT,
                            vote_count INT DEFAULT 0,
                            FOREIGN KEY (election_id) REFERENCES elections(election_id) ON DELETE CASCADE,
                            FOREIGN KEY (candidate_id) REFERENCES candidates(candidate_id) ON DELETE CASCADE
);


DELIMITER //

CREATE TRIGGER after_vote_insert
    AFTER INSERT ON votes
    FOR EACH ROW
BEGIN
    IF EXISTS (SELECT * FROM vote_count WHERE election_id = NEW.election_id AND candidate_id = NEW.candidate_id) THEN
        UPDATE vote_count SET vote_count = vote_count + 1 WHERE election_id = NEW.election_id AND candidate_id = NEW.candidate_id;
    ELSE
        INSERT INTO vote_count (election_id, candidate_id, vote_count) VALUES (NEW.election_id, NEW.candidate_id, 1);
    END IF;
END;
//

CREATE TRIGGER after_vote_delete
    AFTER DELETE ON votes
    FOR EACH ROW
BEGIN
    UPDATE vote_count SET vote_count = vote_count - 1 WHERE election_id = OLD.election_id AND candidate_id = OLD.candidate_id;
    DELETE FROM vote_count WHERE vote_count = 0;
END;
//

DELIMITER ;





INSERT INTO users (name, phone, uid, password) VALUES
                                                   ('User1', '1234567890', 'UID1', 'password1'),
                                                   ('User2', '1234567891', 'UID2', 'password2'),
                                                   ('User3', '1234567892', 'UID3', 'password3'),
                                                   ('User4', '1234567893', 'UID4', 'password4'),
                                                   ('User5', '1234567894', 'UID5', 'password5'),
                                                   ('User6', '1234567895', 'UID6', 'password6'),
                                                   ('User7', '1234567896', 'UID7', 'password7'),
                                                   ('User8', '1234567897', 'UID8', 'password8'),
                                                   ('User9', '1234567898', 'UID9', 'password9'),
                                                   ('User10', '1234567899', 'UID10', 'password10'),
                                                   ('User11', '1234567880', 'UID11', 'password11'),
                                                   ('User12', '1234567881', 'UID12', 'password12'),
                                                   ('User13', '1234567882', 'UID13', 'password13'),
                                                   ('User14', '1234567883', 'UID14', 'password14'),
                                                   ('User15', '1234567884', 'UID15', 'password15'),
                                                   ('User16', '1234567885', 'UID16', 'password16'),
                                                   ('User17', '1234567886', 'UID17', 'password17'),
                                                   ('User18', '1234567887', 'UID18', 'password18'),
                                                   ('User19', '1234567888', 'UID19', 'password19'),
                                                   ('User20', '1234567889', 'UID20', 'password20');


INSERT INTO elections (name, status) VALUES
                                         ('MLA Elections', 'active'),
                                         ('MP Elections', 'active');


INSERT INTO candidates (name, party, election_id) VALUES
                                                      ('Candidate1_MLA', 'Party A', 1),
                                                      ('Candidate2_MLA', 'Party B', 1),
                                                      ('Candidate3_MLA', 'Party C', 1);


INSERT INTO candidates (name, party, election_id) VALUES
                                                      ('Candidate1_MP', 'Party A', 2),
                                                      ('Candidate2_MP', 'Party B', 2),
                                                      ('Candidate3_MP', 'Party C', 2);



INSERT INTO votes (user_id, election_id, candidate_id) VALUES
                                                           (1, 1, 1),
                                                           (2, 1, 2),
                                                           (3, 1, 3),
                                                           (4, 1, 1),
                                                           (5, 1, 2),
                                                           (6, 1, 3),
                                                           (7, 1, 1),
                                                           (8, 1, 2),
                                                           (9, 1, 3),
                                                           (10, 1, 1);


INSERT INTO votes (user_id, election_id, candidate_id) VALUES
                                                           (11, 2, 4),
                                                           (12, 2, 5),
                                                           (13, 2, 6),
                                                           (14, 2, 4),
                                                           (15, 2, 5),
                                                           (16, 2, 6),
                                                           (17, 2, 4),
                                                           (18, 2, 5),
                                                           (19, 2, 6),
                                                           (20, 2, 4);

