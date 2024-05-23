/*
 * Listens for events on the investor input form. 
 * Implements the ActionListener interface which contains a single method, 
 * "actionPerformed" - this method contains all the logic to process the data
 * on the form, as well as several other events
 */
package controllers.inputformcontrollers;

import controllers.Application;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import datacontainers.*;
import datamodels.Investor;
import datamodels.InvestorStockQuote;
import datamodels.StockQuote;
import exceptionhandlers.ErrorPopup;
import exceptionhandlers.InvalidDataException;
import java.util.Calendar;
import java.util.List;
import utilities.date.DateFunctions;
import view.inputforms.InvestorInputForm;

public class InputInvestorFormController implements ActionListener {

   // The data datacontainers are passed in
   InvestorDataContainer investorDataContainer;
   StockQuoteDataContainer stockquoteDataContainer;

   // Input form is created here
   InvestorInputForm form;

   // Create a new Investor object used in the event methods
   Investor newInvestor;

   public InputInvestorFormController(InvestorDataContainer investorDataContainer,
           StockQuoteDataContainer stockquoteDataContainer) {

      // store local pointers to the data datacontainers passed in
      this.investorDataContainer = investorDataContainer;
      this.stockquoteDataContainer = stockquoteDataContainer;

      // Instantiate the forms and make it visible
      form = new InvestorInputForm(this);
      form.setVisible(true);

   }

   /**
    * actionPerformed method implemented from the ActionListener interface
    *
    * This method figures out which button was clicked based on the text of the
    * button. The button click event is passed in via the ActionEvent object.
    *
    * @param event
    */
   public void actionPerformed(ActionEvent event) {
      if (event.getActionCommand().equals("Save")) {
         this.saveData();
      } else if (event.getActionCommand().equals("Clear")) {
         this.clearForm();
      } else if (event.getActionCommand().equals("Close")) {
         this.closeForm();
      }
   }

   /**
    * Private method to save all the data on the form and create a new investor
    * object
    */
   public void saveData() {

      // Create a new Investor object used in the event methods
      newInvestor = new Investor();

      try {

         // Retrieve data from all text fields and store directly into object
         newInvestor.setName(form.getNameField().getText());
         newInvestor.setAddress(form.getAddressField().getText());

         // Retrieve id string and convert to a long before storing in object
         // Note that converting a string to a long may through a built in
         // Java NumberFormatException.  In our case, the form makes sure
         // that we only enter the numbers 0-9 but if this controller were to
         // be used by another form that doesn't have that form checking, we might
         // throw the parser exception. So let's catch the built
         // in exception and then throw our own, more user friendly exception!
         try {
            long idString = Integer.parseInt(form.getIdField().getText());
            newInvestor.setId(idString);
         } catch (NumberFormatException exp) {
            throw new InvalidDataException("Invalid ID");
         }

         long investorIdString = Long.parseLong(form.getIdField().getText());
         newInvestor.setId(investorIdString);

         // Retrieve the dates from the form and convert to Calendar objects
         String dateString = form.getDateOfBirthFormattedTextField().getText();
         Calendar dateOfBirth = DateFunctions.stringToDate(dateString);
         newInvestor.setDateOfBirth(dateOfBirth);

         dateString = form.getMemberSinceFormattedTextField().getText();
         Calendar memberSince = DateFunctions.stringToDate(dateString);
         newInvestor.setMemberSince(memberSince);

         // Retrieve all selected stocks and add to investor's stock list
         // The list only contains stock names.  Need to look them up
         // in the stock quote data container and add pointers to the objects in
         // the data container
         // Retrieve the selected stocks (the returned list is a list of generic objects)
         List stockList = (List) this.form.getDropdownStockList().getSelectedValuesList();

         // Convert the generic objects to Strings before looking up the ticker symbol
         for (Object selectedStock : stockList) {
            String selectedStockString = (String) selectedStock;

            // Split the string into name and id.  We'll use the unique id to find
            // the investor in the investor data container
            String[] stockInfo = selectedStockString.split(":");

            //Hold on to the ticker symbol
            String ticker = stockInfo[0].trim();

            // Find the stock quote in the stock data container based on the ticker symbol
            // Temporay  arraylist of stock quotes from the stock quote data container
            List<StockQuote> stockquoteList = stockquoteDataContainer.getStockQuoteList();

            for (StockQuote stock : stockquoteList) {
               if (stock.getTickerSymbol().equals(ticker)) {
                  // Store an investor stock quote 
                  InvestorStockQuote investorStock = new InvestorStockQuote(stock, 100);
                  newInvestor.addStock(investorStock);
               }
            }
         }

         // Everything good, save the investor
         this.investorDataContainer.getInvestorList().add(newInvestor);

         // Don't log it until it actually happens!
         Application.getAPPLICATION_LOGGER().info("Creating investor with the following values: Name:"
                 + newInvestor.getName() + ", ID:" + newInvestor.getId());

      } catch (InvalidDataException exp) {
         new ErrorPopup(form, exp);
      }

   }

   /**
    * Private method to clear the data
    */
   private void clearForm() {
      // The text fields are set to blank
      form.getNameField().setText("");
      form.getIdField().setText("");
      form.getAddressField().setText("");
      form.getDateOfBirthFormattedTextField().setText("");
      form.getMemberSinceFormattedTextField().setText("");
      form.getDropdownStockList().clearSelection();
   }

   /**
    * Private method to close the form
    */
   private void closeForm() {
      form.dispose();
   }

   public InvestorDataContainer getInvestorDataContainer() {
      return investorDataContainer;
   }

   public StockQuoteDataContainer getStockquoteDataContainer() {
      return stockquoteDataContainer;
   }

}
