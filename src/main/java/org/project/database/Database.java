package org.project.database;

// file reader
import java.sql.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Database {
    private static Connection dbInstance = null;

    public Database() {
        connect();
        System.out.println("Initiated database instance");
    }

    public static void connect() {
        FileInputStream in = null;

        try {
            Class.forName("org.sqlite.JDBC");

            System.out.println("got here");
            Properties props = new Properties();

            in = new FileInputStream("./db.props");
            props.load(in);
            in.close();

            // specify the data source, username and password
            String url = props.getProperty("jdbc.url");
            String username = props.getProperty("jdbc.username");
            String password = props.getProperty("jdbc.password");
            String schema = props.getProperty("jdbc.schema");

            // get a connection
            dbInstance = DriverManager.getConnection(url + "/" + schema, username, password);
        } catch (SQLException sqle) {
            System.err.println(sqle);
        } catch (FileNotFoundException fnfe) {
            System.err.println(fnfe);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static Connection getInstance() {
        if (dbInstance == null) new Database();

        return dbInstance;
    }
}
