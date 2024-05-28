package org.voting.votingmachine.dao;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DataBaseUtil {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/ovs";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "123456789";//enter your password
    static DataSource dSource;

    static {
        dSource = createDataSource();
    }

    private static DataSource createDataSource() {
        BasicDataSource ds = new BasicDataSource();
        ds.setUrl(JDBC_URL);
        ds.setUsername(USERNAME);
        ds.setPassword(PASSWORD);
        ds.setDriverClassName("com.mysql.jdbc.Driver");

        return ds;
    }

    public static Connection getConnection() throws SQLException {
        return dSource.getConnection();
    }
}
