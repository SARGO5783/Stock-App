/*
 * Listens for events on the menu form. 
 * Implements the ActionListener interface which contains a single method, 
 * "actionPerformed"
 */
package controllers;

import controllers.reportformcontrollers.ListAllStockQuotesController;
import controllers.reportformcontrollers.ListAllInvestorsController;
import controllers.reportformcontrollers.ListAllBrokersController;
import controllers.reportformcontrollers.ListAllInvestmentCompaniesController;
import controllers.inputformcontrollers.InputStockQuoteFormController;
import controllers.inputformcontrollers.InputInvestorFormController;
import controllers.inputformcontrollers.InputBrokerFormController;
import controllers.inputformcontrollers.InputInvestmentCompanyFormController;
import java.awt.event.ActionListener;
import datacontainers.StockQuoteDataContainer;
import datacontainers.InvestorDataContainer;
import datacontainers.BrokerDataContainer;
import datacontainers.InvestmentCompanyDataContainer;
import exceptionhandlers.DatabaseException;
import exceptionhandlers.DatabaseErrorPopup;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import utilities.db.DatabaseIO;
import view.MainMenu;

public class MainMenuController implements ActionListener {

    // File location
    private String fileLocation;
    // Log file location;
    private String logfilelocation;
    // Log level
    private String logLevel;

    // The data models are instantiated here and passed to the 
    // constructors for the controllers
    private StockQuoteDataContainer stockQuoteDataContainer = new StockQuoteDataContainer();
    private InvestorDataContainer investorDataContainer = new InvestorDataContainer();
    private BrokerDataContainer brokerDataContainer = new BrokerDataContainer();
    private InvestmentCompanyDataContainer investmentCompanyDataContainer = new InvestmentCompanyDataContainer();


    /**
     * Constructor
     *
     */
    public MainMenuController(String fileLocation, String logfilelocation) {
        this.fileLocation = fileLocation;
        this.logfilelocation = logfilelocation;
        this.logLevel = logLevel;
    }

    // The main menu form gets created here. Notice it takes this controller object
    // as an argument to the constructor
    private MainMenu mainMenu = new MainMenu(this);

    /**
     * The ActionListener interface contains a single method, actionPerformed
     */
    public void actionPerformed(java.awt.event.ActionEvent event) {

        //  Figure out which button was clicked
        String menuItemClicked = event.getActionCommand();

        // create the controller which will open the correct form (refer to the controller constructor
        // methods do determine which data container classes need to be passed to the controller constructors)
        if (menuItemClicked.equals("Add Stock Quote")) {
            InputStockQuoteFormController inputController = new InputStockQuoteFormController(stockQuoteDataContainer);
        } else if (menuItemClicked.equals("List Available Stocks")) {
            ListAllStockQuotesController reportController = new ListAllStockQuotesController(stockQuoteDataContainer);
        } else if (menuItemClicked.equals("Add Investor")) {
            // Create an input form controller object for the investor and pass the correct containers to the constructor
            InputInvestorFormController inputController = new InputInvestorFormController(investorDataContainer, stockQuoteDataContainer);
        } else if (menuItemClicked.equals("List Investors")) {
            // Create a report controller object for the investors and pass the correct containers to the constructor  
            ListAllInvestorsController reportController = new ListAllInvestorsController(investorDataContainer);
        }else if (menuItemClicked.equals("Add Investment Company")) {
            // to-do create an input form controller object for the investment 
            // compnay and pass the correct containers to the constructor. look 
            // at the constructor method to see what you should pass to it as
            // arguments
            InputInvestmentCompanyFormController inputController = new InputInvestmentCompanyFormController(investmentCompanyDataContainer, brokerDataContainer);
        } else if (menuItemClicked.equals("List Investment Companies")) {
            // to-do create a report controller object for the investment company 
            // and pass the correct containers to the constructor.  look 
            // at the constructor method to see what you should pass to it as
            // arguments         
            ListAllInvestmentCompaniesController reportController = new ListAllInvestmentCompaniesController(investmentCompanyDataContainer);
        } else if (menuItemClicked.equals("Add Broker")) {
            // to-do create an input form controller object for the broker and
            // pass the correct containers to the constructor. look 
            // at the constructor method to see what you should pass to it as
            // arguments
            InputBrokerFormController inputController = new InputBrokerFormController(brokerDataContainer, investorDataContainer);

        } else if (menuItemClicked.equals("List Brokers")) {
            // to-do create a report controller object for the broker and pass 
            // the correct containers to the constructor. look 
            // at the constructor method to see what you should pass to it as
            // arguments     
            ListAllBrokersController reportController = new ListAllBrokersController(brokerDataContainer);

        }else if (menuItemClicked.equals("Exit")) {
            System.exit(0);
        } else if (menuItemClicked.equals("Save Data")) {
            try {
                DatabaseIO.storeStockQuotes(stockQuoteDataContainer);
                DatabaseIO.storeInvestors(investorDataContainer);
                DatabaseIO.storeBrokers(brokerDataContainer);
                DatabaseIO.storeInvestmentCompany(investmentCompanyDataContainer);
            } catch (DatabaseException exp) {
                new DatabaseErrorPopup(mainMenu, exp);
            }
        } else if (menuItemClicked.equals("Load Data")) {
            try {
                stockQuoteDataContainer.setStockQuoteList(DatabaseIO.retrieveStockQuotes());
                investorDataContainer.setInvestorList(DatabaseIO.retrieveInvestors());
                brokerDataContainer.setBrokerList(DatabaseIO.retrieveBrokers());
                investmentCompanyDataContainer.setInvestmentCompanyList(DatabaseIO.retrieveInvestmentCompany());
            } catch (DatabaseException exp) {
                new DatabaseErrorPopup(mainMenu, exp);
            } catch (SQLException ex) {
                Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    // Getter used in the Application.java class
    public MainMenu getMainMenu() {
        return mainMenu;
    }
}
