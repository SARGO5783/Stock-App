package utilities.db;

/**
 * This class is used to initialize the database. It has methods to drop all the
 * tables and create all the tables. NOTE: the database must already exist in
 * order to create and drop tables
 */
//import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;
import controllers.Application;

import java.sql.*;
import java.sql.Connection;
import java.sql.Statement;

public final class InitializeDatabase {

    /**
     * Table creation Strings Used to initialize the database tables
     */
    private static final String createTables[] = {
        "CREATE TABLE `stockquotes` (`tickersymbol` varchar(10) NOT NULL, `value` float DEFAULT NULL,`date` datetime DEFAULT NULL, PRIMARY KEY (`tickersymbol`))",
        "CREATE TABLE `investors` (`id` int NOT NULL, `name` varchar(150) NOT NULL, `address` varchar(100) NOT NULL, `dateOfBirth` datetime DEFAULT NULL, `memberSince` datetime DEFAULT NULL, PRIMARY KEY (`id`))",
        "CREATE TABLE `investorstocks` (`id` int NOT NULL, `tickersymbol` varchar(5) NOT NULL, `shares` int DEFAULT NULL, PRIMARY KEY (`id`,`tickersymbol`))",
        "CREATE TABLE `brokers` (`id` int NOT NULL,`name` varchar(150) NOT NULL,`address` varchar(100) NOT NULL,`dateOfBirth` datetime DEFAULT NULL,`dateOfHire` datetime DEFAULT NULL,`dateOfTermination` datetime DEFAULT NULL, `salary` double NOT NULL,`status` varchar(10) DEFAULT NULL, PRIMARY KEY (`id`))",
        "CREATE TABLE `brokerclients` (`brokerid` int NOT NULL, `investorid` int NOT NULL, PRIMARY KEY (`brokerid`,`investorid`))",
        "CREATE TABLE `companies` (`companyname` varchar(250) NOT NULL, PRIMARY KEY (`companyname`))",
        "CREATE TABLE `companybrokers` (`companyname` varchar(250) NOT NULL, `brokerid` int NOT NULL, PRIMARY KEY (`companyname`,`brokerid`))"
    };

    /**
     * Table deletion strings Used to delete the tables from the database.
     * Dropping tables will also remove all the data!
     */
    private static final String dropTables[] = {
        "DROP TABLE stockquotes",
        "DROP TABLE investors",
        "DROP TABLE investorstocks",
        "DROP TABLE brokers",
        "DROP TABLE brokerclients",
        "DROP TABLE companies",
        "DROP TABLE companybrokers"
    };

    /**
     * Drops the tables listed in the dropTables array and removes all the data
     *
     * @throws SQLException
     */
    public static void dropTables() throws SQLException {
        Connection connection = DatabaseUtilities.openDatabaseConnection();
        try ( Statement statement = connection.createStatement()) {
            for (int i = 0; i < createTables.length; i++) {
                try {
                    statement.execute(dropTables[i]);
                    Application.getAPPLICATION_LOGGER().info("Database tables dropped: " + " uml");
                } catch (SQLException sqlException) {
                    Application.getAPPLICATION_LOGGER().info("Problem dropping database tables for uml: " + sqlException.getMessage());
                }
            }
        }
    }

    /**
     * Create the tables listed in the createTables array
     */
    public static void createTables() throws SQLException {
        Connection connection = DatabaseUtilities.openDatabaseConnection();
        try ( Statement statement = connection.createStatement()) {
            for (String createTable : createTables) {
                try {
                    statement.execute(createTable);
                    Application.getAPPLICATION_LOGGER().info("Database tables created: " + "uml");
                } catch (SQLException sqlException) {
                    Application.getAPPLICATION_LOGGER().info("Problem creating database tables for uml: " + sqlException.getMessage());
                }
            }
        }
    }

    /**
     * Restore database to initial settings
     */
    public static void resetDatabase() throws SQLException {
        Application.getAPPLICATION_LOGGER().info("Dropping and creating database tables for: " + " uml");
        dropTables();
        createTables();
    }

    /**
     * This method is available if you want to initialize the database external
     * to the application.
     *
     * @param args
     */
    public static void main(String args[]) {
        try {
            resetDatabase();
            Application.getAPPLICATION_LOGGER().info("Resetting database uml:" + "");
        } catch (SQLException e) {
            Application.getAPPLICATION_LOGGER().severe("Problem resetting database uml: " + e.getMessage());
        }
    }

}
