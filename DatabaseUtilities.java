package utilities.db;

/**
 * This class is used to store commonly used database functions such as 
 * connecting to the database and opening the database
 */
import controllers.Application;
import java.sql.*;
import java.sql.Connection;

public final class DatabaseUtilities {
   
    /**
     * Registers the driver code for the database.The driver communicates
     * between the Java JDBC and the database. Creates the database connection
     * object using the url string. This string contains the IP address of the
     * database and also the database name. In this case, the database name is
     * "uml".
     *
     * @return connection object
     */
    public static Connection openDatabaseConnection() {

        // Register the MySQL JDBC Driver
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            Application.getAPPLICATION_LOGGER().info("Database driver loaded: " + "uml");

            // This URL specifies we are connecting to a local database named uml.
            String url = "jdbc:mariadb://localhost:3306/uml";

            // Make a connection with the database. User is root, no password
            Connection connection = DriverManager.getConnection(url, "root", "user");
            Application.getAPPLICATION_LOGGER().info("Getting database connection: " + "uml");
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            Application.getAPPLICATION_LOGGER().info("Problem getting database connection to um2l: " + e.getMessage());
            return null;
        }
    }   
    
     public static Connection openDatabaseConnectionStudent() {

        // Register the MySQL JDBC Driver
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            Application.getAPPLICATION_LOGGER().info("Database driver loaded: " + "umlstudent");

            // This URL specifies we are connecting to a local database named umlstudent.
            String url = "jdbc:mariadb://localhost:3306/umlstudent";

            // Make a connection with the database. User is root, no password
            Connection connection = DriverManager.getConnection(url, "root", "user");
            Application.getAPPLICATION_LOGGER().info("Getting database connection: " + "umlstudent");
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            Application.getAPPLICATION_LOGGER().info("Problem getting database connection to umlstudent: " + e.getMessage());
            return null;
        }
    }   
  
}
