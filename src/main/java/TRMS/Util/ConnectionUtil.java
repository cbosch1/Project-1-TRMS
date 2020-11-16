package TRMS.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class ConnectionUtil {

    private Connection conn;

    private static Logger Log = LogManager.getLogger("Util");

	public Connection createConnection() {

        try {
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres?currentSchema=TRMS", "postgres", "password");
            Log.info("Successfully retrieved connection to database.");
            return conn;

        } catch (SQLException e) {
            Log.warn("Threw SQL Exception while attempting to get connection: " + e);
            return null;
        }
	}
    
}