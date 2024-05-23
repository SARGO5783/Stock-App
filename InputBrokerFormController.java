/*
 * Listens for events on the input form. 
 * Implements the ActionListener interface which contains a single method, 
 * "actionPerformed" - this method contains all the logic to process the data
 * on the form, as well as several other events
 */
package controllers.inputformcontrollers;

import controllers.Application;
import datacontainers.BrokerDataContainer;
import datacontainers.InvestorDataContainer;
import datamodels.Broker;
import datamodels.Investor;
import exceptionhandlers.ErrorPopup;
import exceptionhandlers.InvalidDataException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.List;
import utilities.date.DateFunctions;
import view.inputforms.BrokerInputForm;

public class InputBrokerFormController implements ActionListener {

    // The data containers are passed in via the constructor
    BrokerDataContainer brokerDataContainer;
    InvestorDataContainer investorDataContainer;

    // The form is declared here
    BrokerInputForm form;

    public InputBrokerFormController(BrokerDataContainer brokerDataContainer,
            InvestorDataContainer investorDataContainer) {

        this.brokerDataContainer = brokerDataContainer;
        this.investorDataContainer = investorDataContainer;
        form = new BrokerInputForm(this);
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
     * Private method to save all the data on the form and create a new Broker
     * object
     */
    private void saveData() {

        // Create a new broker
        Broker newBroker = new Broker();

        // Retrieve data from all form fields and store directly into object
        // Retrieve name and address strings
        try {
            newBroker.setName(form.getNameField().getText());
            newBroker.setAddress(form.getAddressField().getText());

            // Retrieve id string and convert to a long before storing in object
            long idString = Integer.parseInt(form.getIdField().getText());
            newBroker.setId(idString);

            // Retrieve status from drop down list
            String selectedStatus = (String) form.getStatusField().getSelectedItem();
            newBroker.setStatus(selectedStatus);

            // Retrieve salary and convert to a double before storing in object
            String salarystring = form.getSalaryField().getText();
            Double salarydouble = Double.parseDouble(salarystring);
            newBroker.setSalary(salarydouble);

            // Retrieve the dates  and convert to Calendar objects
            String dateString = form.getDateOfBirthFormattedTextField().getText();
            Calendar dateOfBirth = DateFunctions.stringToDate(dateString);
            newBroker.setDateOfBirth(dateOfBirth);

            dateString = form.getDateOfHireFormattedTextField().getText();
            Calendar dateOfHire = DateFunctions.stringToDate(dateString);
            newBroker.setDateOfHire(dateOfHire);

            dateString = form.getDateOfTerminationFormattedTextField().getText();
            Calendar dateOfTermination = DateFunctions.stringToDate(dateString);
            newBroker.setDateOfTermination(dateOfTermination);

            // Retrieve all selected clients and add to broker's client list
            // The list only contains client names and ids.  Need to look them up
            // in the invester data container and add pointers to the objects in
            // the data container
            // Retrieve the selected clients (the returned list is a list of generic objects)
            List clientList = (List) this.form.getDropdownClientList().getSelectedValuesList();

            // Convert the generic objects to Strings and look them up in the investor data container
            for (Object selectedClient : clientList) {
                String selectedClientString = (String) selectedClient;
                // This is where it probably would have been better to store the clients
                // as a map instead of a list but oh well, the damage is done...

                // Split the string into name and id.  We'll use the unique id to find
                // the investor in the investor data container
                String[] name_id = selectedClientString.split(":");

                //Hold on to the id
                long selectedId = Integer.parseInt(name_id[1].strip());

                // Find the investor in the investor data container based on the id
                // Temporay  arraylist of investors from the investor data container
                List<Investor> investorList = investorDataContainer.getInvestorList();
                for (Investor investor : investorList) {
                    if (investor.getId() == selectedId) {
                        newBroker.addClient(investor);
                    }
                }
            }
            this.brokerDataContainer.getBrokerList().add(newBroker);
            // to-do - Add the new broker to the data container
            
        Application.getAPPLICATION_LOGGER().info("Creating Broker with the following values:"
                    + "Name: " + newBroker.getName() 
                    + ",Address: " + newBroker.getAddress()
                   +",Id: " + newBroker.getId()
                   + ", Date of Birth: " + DateFunctions.dateToString(newBroker.getDateOfBirth())
                   + ", Date of Hire: " + DateFunctions.dateToString(newBroker.getDateOfHire())
                   + ", Date of Termination: " + DateFunctions.dateToString(newBroker.getDateOfTermination())
                   + ",Salary:" + newBroker.getSalary()
                   + ",Status:" + newBroker.getStatus()
                   + ",List Of Clients:" + newBroker.getListOfClients());

        } catch (InvalidDataException exp) {
            // Log the error
            Application.getAPPLICATION_LOGGER().severe(exp.getMessage());
            new ErrorPopup(form, exp);
    }
    }

    /**
     * Private method to clear the data
     */
    private void clearForm() {

        // The text fields are set to blank
        this.form.getNameField().setText("");
        this.form.getAddressField().setText("");
        this.form.getSalaryField().setText("");
        this.form.getStatusField().setSelectedIndex(0);
        form.getDateOfBirthFormattedTextField().setText("");
        form.getDateOfHireFormattedTextField().setText("");
        form.getDateOfTerminationFormattedTextField().setText("");

    }

    /**
     * Private method to close the form
     */
    private void closeForm() {
        this.form.dispose();
    }

    public BrokerDataContainer getBrokerDataContainer() {
        return brokerDataContainer;
    }

    public InvestorDataContainer getInvestorDataContainer() {
        return investorDataContainer;
    }

    public BrokerInputForm getForm() {
        return form;
    }

}
