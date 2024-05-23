package utilities.db;

import datacontainers.InvestorDataContainer;
import datacontainers.StockQuoteDataContainer;
import datacontainers.BrokerDataContainer;
import datacontainers.InvestmentCompanyDataContainer;
import datamodels.Investor;
import datamodels.InvestorStockQuote;
import datamodels.StockQuote;
import datamodels.Broker;
import datamodels.InvestmentCompany;
import exceptionhandlers.DatabaseException;
import exceptionhandlers.InvalidDataException;
import exceptionhandlers.FileException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * This class contains methods to write to and read from, a Mysql database I
 * suppose I could have put them in the I/O class but I decided to leave all the
 * database code together.
 */
public class DatabaseIO {

    // Used in all methods, declared once
    static Statement insertStatement;
    static Statement queryStatement;
    static Connection connection;
    static String commandString;

    /**
     * Store all stockquotes to database
     */
    public static void storeStockQuotes(StockQuoteDataContainer stockquoteDataContainer) throws DatabaseException {

        // Retrieve the database connection and create the statement object
        try {
            connection = DatabaseUtilities.openDatabaseConnection();
            insertStatement = connection.createStatement();

        } catch (SQLException error) {
            throw new DatabaseException("A database error occured, could not connect to database: " + error.getMessage());
        }

        //Loo pthrough the list of stock quotes. 
        for (StockQuote quote : stockquoteDataContainer.getStockQuoteList()) {
            try {

                // Create the string for the sql statement
                String command = "INSERT INTO stockquotes (tickersymbol, value, date)"
                        + "VALUES ('"
                        + quote.getTickerSymbol() + "','"
                        + quote.getValue() + "','"
                        + DatabaseDateUtilities.getSqlFormattedDate(quote.getQuoteDate()) + "')";

                // Execute the statement
                insertStatement.executeUpdate(command);

            } catch (SQLException error) {
                // eat the error
            }
        }
    }

    public static void storeInvestors(InvestorDataContainer investorDataContainer) throws DatabaseException {

        // Retrieve the database connection and create the statement object
        try {
            connection = DatabaseUtilities.openDatabaseConnection();
            insertStatement = connection.createStatement();

        } catch (SQLException error) {
            throw new DatabaseException("A database error occured, could not connect to database: " + error.getMessage());
        }

        // Loop through the list of investors and store each one in the investors table
        for (Investor investor : investorDataContainer.getInvestorList()) {
            try {

                // Create the string for the sql statement
                String command1 = "INSERT INTO investors (id, name, address, dateOfBirth, memberSince)"
                        + "VALUES ('"
                        + investor.getId() + "','"
                        + investor.getName() + "','"
                        + investor.getAddress() + "','"
                        + DatabaseDateUtilities.getSqlFormattedDate(investor.getDateOfBirth()) + "','"
                        + DatabaseDateUtilities.getSqlFormattedDate(investor.getMemberSince()) + "')";

                // Execute the statement
                insertStatement.executeUpdate(command1);

            } catch (SQLException error) {
              // eat the error
            }

            try {

                //Loop through list of stocks for this investor
                for (InvestorStockQuote quote : investor.getListOfStocks()) {
                    //For each stock create an investorquote entry
                    String command2 = "INSERT INTO investorstocks (id, tickersymbol, shares)"
                            + "VALUES ('"
                            + investor.getId() + "','"
                            + quote.getStock().getTickerSymbol() + "','"
                            + quote.getShares() + "')";

                    // Execute the statement
                    insertStatement.executeUpdate(command2);
                }

            } catch (SQLException error) {
               // eat the error
            }
        }
    }
    public static void storeBrokers(BrokerDataContainer brokerDataContainer) throws DatabaseException {

        // Retrieve the database connection and create the statement object
        try {
            connection = DatabaseUtilities.openDatabaseConnection();
            insertStatement = connection.createStatement();

        } catch (SQLException error) {
            throw new DatabaseException("A database error occured, could not connect to database: " + error.getMessage());
        }

        // Loop through the list of investors and store each one in the investors table
        for (Broker broker : brokerDataContainer.getBrokerList()) {
            try {

                // Create the string for the sql statement
                String command1 = "INSERT INTO brokers (name, address, dateOfBirth, dateOfHire, dateOfTermination, salary, status, id, listOfClients)"
                        + "VALUES ('"
                        + broker.getName() + "','"
                        + broker.getAddress()+ "','"
                        + DatabaseDateUtilities.getSqlFormattedDate(broker.getDateOfBirth()) + "','"
                        + DatabaseDateUtilities.getSqlFormattedDate(broker.getDateOfHire()) + "','"
                        + DatabaseDateUtilities.getSqlFormattedDate(broker.getDateOfTermination()) + "','"
                        + broker.getSalary() + "','"
                        + broker.getStatus() + "','"
                        + broker.getId(); 

                // Execute the statement
                insertStatement.executeUpdate(command1);

            } catch (SQLException error) {
              // eat the error
            }

            try {

                //Loop through list of investors for this broker
                for (Investor investor : broker.getListOfClients()) {
                    //For each investor create an investorquote entry
                    String command2 = "INSERT INTO investors (id, name, address, dateOfBirth, memberSince)"
                            + "VALUES ('"
                            + broker.getId() + "','"
                            + investor.getName()+ "','"
                            + investor.getId() + "')";
                    // Execute the statement
                    insertStatement.executeUpdate(command2);
                }

            } catch (SQLException error) {
               // eat the error
            }
        }
    }
        public static void storeInvestmentCompany (InvestmentCompanyDataContainer investmentCompanyDataContainer) throws DatabaseException {

        // Retrieve the database connection and create the statement object
        try {
            connection = DatabaseUtilities.openDatabaseConnection();
            insertStatement = connection.createStatement();

        } catch (SQLException error) {
            throw new DatabaseException("A database error occured, could not connect to database: " + error.getMessage());
        }

        // Loop through the list of investors and store each one in the investors table
        for (InvestmentCompany company : investmentCompanyDataContainer.getInvestmentCompanyList()) {
            try {

                // Create the string for the sql statement
                String command1 = "INSERT INTO investmentCompany (companyName)"
                        + "VALUES ('"
                        + company.getCompanyName(); 

                // Execute the statement
                insertStatement.executeUpdate(command1);

            } catch (SQLException error) {
              // eat the error
            }

            try {

                //Loop through list of brokers for this company
                for (Broker broker : company.getListOfBrokers()) {
                    //For each stock create an investorquote entry
                    String command2 = "INSERT INTO brokers (name, address, dateOfBirth, dateOfHire, dateOfTermination, salary, status, id, listOfClients)"
                            + "VALUES ('"
                            + company.getCompanyName() + "','"
                            + broker.getName()+ "','"
                            + broker.getId() + "')";
                    // Execute the statement
                    insertStatement.executeUpdate(command2);
                }

            } catch (SQLException error) {
               // eat the error
            }
        }
    }
    public static List<StockQuote> retrieveStockQuotes() throws DatabaseException {

        ArrayList<StockQuote> listOfStockQuotes = new ArrayList<>();

        // Retrieve the database connection and create the statement object
        try {
            connection = DatabaseUtilities.openDatabaseConnection();
            queryStatement = connection.createStatement();
        } catch (SQLException error) {
            throw new DatabaseException("A database error occured, could not connect to database: " + error.getMessage());
        }

        try {

            // Create the string for the statement object
            String command = "SELECT tickersymbol, value, date FROM stockquotes ORDER BY tickersymbol";

            // Execute the statement object 
            ResultSet results = queryStatement.executeQuery(command);

            // Call private helper method to parse the result set into the array list
            listOfStockQuotes = parseStockQuote(results);

        } catch (SQLException error) {
            throw new DatabaseException("A database error occured retrieving data from the stock quote table " + error.getMessage());
        }

        return listOfStockQuotes;
    }

    /**
     * Populate the array list with data from the database
     */
    private static ArrayList<StockQuote> parseStockQuote(ResultSet results) throws DatabaseException {

        ArrayList<StockQuote> listOfStockquotes = new ArrayList<>();

        try {
            while (results.next()) {
                StockQuote quote = new StockQuote();
                quote.setTickerSymbol(results.getString(1));
                quote.setValue(Double.parseDouble(results.getString(2)));
                quote.setQuoteDate(DatabaseDateUtilities.getJavaFormattedDate(results.getDate("date")));
                listOfStockquotes.add(quote);
            }
        } catch (InvalidDataException | NumberFormatException | SQLException e) {
            throw new DatabaseException("Error parsing database results"
                    + " stockquotes table " + e.getMessage());
        }

        return listOfStockquotes;
    }

    public static List<Investor> retrieveInvestors() throws DatabaseException {

        ArrayList<Investor> listOfInvestors = new ArrayList<>();

        // Retrieve the database connection and create the statement object
        try {
            connection = DatabaseUtilities.openDatabaseConnection();
            queryStatement = connection.createStatement();

        } catch (SQLException error) {
            throw new DatabaseException("A database error occured, could not connect to database: " + error.getMessage());
        }

        try {

            //Get list of investors from the db.  
            commandString = "SELECT id, name, address, dateOfBirth, memberSince FROM investors";
            ResultSet investors = queryStatement.executeQuery(commandString);
            listOfInvestors = parseInvestorListResults(investors);

        } catch (SQLException error) {
            throw new DatabaseException("A database error occured retrieving data from the investor table " + error.getMessage());
        }

        try {

            for (Investor investor : listOfInvestors) {
                //   Create a sql statement to retrieve all the stocks associated with the investor ID
                commandString = "SELECT tickersymbol, shares FROM investorstocks WHERE id = ?";
                PreparedStatement pstmt = connection.prepareStatement(commandString);
                pstmt.setLong(1, investor.getId());
                ResultSet unparsedInvestorStockQuotes = pstmt.executeQuery();
                ArrayList<InvestorStockQuote> listOfParsedStockQuotes = parseInvestorStockQuotes(unparsedInvestorStockQuotes);
                for (InvestorStockQuote parsedInvestorStockQuote : listOfParsedStockQuotes) {
                    investor.addStock(parsedInvestorStockQuote);
                }
            }
            
        } catch (SQLException | FileException | InvalidDataException error) {
            throw new DatabaseException("A database error occured retrieving data from the investor stocks table " + error.getMessage());
        }

        return listOfInvestors;
    }
    public static List<Broker> retrieveBrokers() throws DatabaseException, SQLException {

        ArrayList<Broker> listOfBrokers = new ArrayList<>();

        // Retrieve the database connection and create the statement object
        try {
            connection = DatabaseUtilities.openDatabaseConnection();
            queryStatement = connection.createStatement();

        } catch (SQLException error) {
            throw new DatabaseException("A database error occured, could not connect to database: " + error.getMessage());
        }

        try {
            //Get list of brokers from the db.  
           
            commandString = "SELECT name, address, dateOfBirth, dateOfHire, dateOfTermination, salary, status, id, listOfClients FROM brokers";
            ResultSet brokers = queryStatement.executeQuery(commandString);
            listOfBrokers = parseBrokerListResults(brokers);

        } catch (SQLException error) {
            throw new DatabaseException("A database error occured retrieving data from the broker table " + error.getMessage());
        }

        try {

            for (Broker broker : listOfBrokers) {
                //   Create a sql statement to retrieve all the investors associated with the broker ID
                commandString = "SELECT name, id FROM investors WHERE id = ?";
                PreparedStatement pstmt = connection.prepareStatement(commandString);
                pstmt.setLong(1, broker.getId());
                ResultSet unparsedInvestors = pstmt.executeQuery();
                ArrayList<Investor> listOfParsedInvestors = parseInvestorListResults(unparsedInvestors);
                for (Investor parsedInvestor : listOfParsedInvestors) {
                    broker.addClient(parsedInvestor);
                }
            }
            
        } catch (DatabaseException | SQLException error) {
            throw new DatabaseException("A database error occured retrieving data from the investor stocks table " + error.getMessage());
        }

        return listOfBrokers;
    }
    public static List<InvestmentCompany> retrieveInvestmentCompany() throws DatabaseException, SQLException {

        ArrayList<InvestmentCompany> listOfInvestmentCompanies = new ArrayList<>();

        // Retrieve the database connection and create the statement object
        try {
            connection = DatabaseUtilities.openDatabaseConnection();
            queryStatement = connection.createStatement();

        } catch (SQLException error) {
            throw new DatabaseException("A database error occured, could not connect to database: " + error.getMessage());
        }

        try {

            //Get list of companies from the db.  
           
            commandString = "SELECT companyName FROM investmentCompany";
            ResultSet investmentCompanies = queryStatement.executeQuery(commandString);
            listOfInvestmentCompanies = parseInvestmentCompanyListResults(investmentCompanies);

        } catch (SQLException error) {
            throw new DatabaseException("A database error occured retrieving data from the broker table " + error.getMessage());
        }

        try {

            for (InvestmentCompany company : listOfInvestmentCompanies) {
                //   Create a sql statement to retrieve all the brokers associated with the company name
                commandString = "SELECT name, id FROM brokers WHERE companyName = ?";
                PreparedStatement pstmt = connection.prepareStatement(commandString);
                pstmt.setString(1, company.getCompanyName());
                ResultSet unparsedBrokers = pstmt.executeQuery();
                ArrayList<Broker> listOfParsedBrokers = parseBrokerListResults(unparsedBrokers);
                for (Broker parsedBroker : listOfParsedBrokers) {
                    company.addBroker(parsedBroker);
                }
            }
            
        } catch (DatabaseException | SQLException error) {
            throw new DatabaseException("A database error occured retrieving data from the investor stocks table " + error.getMessage());
        }

        return listOfInvestmentCompanies;
    }
    private static ArrayList<InvestorStockQuote> parseInvestorStockQuotes(ResultSet investorQuotes) throws SQLException, FileException, InvalidDataException {

        ArrayList<InvestorStockQuote> investorStockQuotes = new ArrayList<>();

        connection = DatabaseUtilities.openDatabaseConnection();
        
        while (investorQuotes.next()) {
            // Retrieving the stock quotes using this list 
            commandString = "SELECT tickersymbol, value, date FROM stockquotes WHERE tickersymbol IN (?) ";
            PreparedStatement pstmt = connection.prepareStatement(commandString);
            pstmt = connection.prepareStatement(commandString);
            pstmt.setString(1, investorQuotes.getString(1));
            ResultSet stockQuotes = pstmt.executeQuery();

            stockQuotes.next();

            StockQuote stockQuote = new StockQuote();
            stockQuote.setTickerSymbol(stockQuotes.getString(1));
            stockQuote.setValue(stockQuotes.getFloat(2));
            stockQuote.setQuoteDate(DatabaseDateUtilities.getJavaFormattedDate(stockQuotes.getDate("date")));

            InvestorStockQuote investorStockQuote = new InvestorStockQuote();
            investorStockQuote.setStock(stockQuote);
            investorStockQuote.setShares(investorQuotes.getInt(2));

            investorStockQuotes.add(investorStockQuote);
        }

        return investorStockQuotes;
    }

    private static ArrayList<Investor> parseInvestorListResults(ResultSet results) throws DatabaseException {

        ArrayList<Investor> listOfInvestors = new ArrayList<>();

        try {
            while (results.next()) {
                Investor investor = new Investor();
                investor.setId(results.getLong(1));
                investor.setName(results.getString(2));
                investor.setAddress(results.getString(3));
                investor.setDateOfBirth(DatabaseDateUtilities.getJavaFormattedDate(results.getDate("dateOfBirth")));
                investor.setMemberSince(DatabaseDateUtilities.getJavaFormattedDate(results.getDate("memberSince")));

                listOfInvestors.add(investor);
            }
        } catch (InvalidDataException | NumberFormatException | SQLException e) {
            throw new DatabaseException("Error parsing database results"
                    + " investor table " + e.getMessage());
        }

        return listOfInvestors;
    }
    private static ArrayList<Broker> parseBrokerListResults(ResultSet results) throws DatabaseException {

        ArrayList<Broker> listOfBrokers = new ArrayList<>();

        try {
            while (results.next()) {
                Broker broker = new Broker();
                broker.setName(results.getString(1));
                broker.setAddress(results.getString(2));
                broker.setDateOfBirth(DatabaseDateUtilities.getJavaFormattedDate(results.getDate("dateOfBirth")));
                broker.setDateOfHire(DatabaseDateUtilities.getJavaFormattedDate(results.getDate("dateOfHire")));
                broker.setDateOfTermination(DatabaseDateUtilities.getJavaFormattedDate(results.getDate("dateOfTermination")));
                broker.setSalary(results.getLong(6));
                broker.setStatus(results.getString(7));
                broker.setId(results.getLong(8));

                listOfBrokers.add(broker);
            }
        } catch (InvalidDataException | NumberFormatException | SQLException e) {
            throw new DatabaseException("Error parsing database results"
                    + " broker table " + e.getMessage());
        }

        return listOfBrokers;
    }
    private static ArrayList<InvestmentCompany> parseInvestmentCompanyListResults(ResultSet results) throws DatabaseException {

        ArrayList<InvestmentCompany> listOfInvestmentCompanies = new ArrayList<>();

        try {
            while (results.next()) {
                InvestmentCompany investmentCompany = new InvestmentCompany();
                investmentCompany.setCompanyName(results.getString(1));

                listOfInvestmentCompanies.add(investmentCompany);
            }
        } catch (InvalidDataException | NumberFormatException | SQLException e) {
            throw new DatabaseException("Error parsing database results"
                    + " investmentCompany table " + e.getMessage());
        }

        return listOfInvestmentCompanies;
    }
}
