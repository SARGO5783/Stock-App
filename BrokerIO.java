/*
 *  This Class contains methods to write out the stockquote objects in several different formats
 *  and read in the data in the same formats.
 */
package utilities.io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import datacontainers.BrokerDataContainer;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import datamodels.Broker;
import exceptionhandlers.InvalidDataException;
import exceptionhandlers.FileException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import utilities.date.DateFunctions;

public class BrokerIO implements Serializable {

    /**
     * Constructor is declared private because the IO classes are utilities
     * which contain static methods and should never be instantiated
     */
    private BrokerIO() {
    }

    /**
     * Writes out a text file containing all stock quotes in the stock quote
     * data container
     *
     * The format of the text file is:
     *
     * Example: FA301,STOCKQUOTE
     */
    public static void writeTextFile(String fileLocation, BrokerDataContainer datacontainer) throws FileException {

        PrintWriter textFile = null;

        try {
            // Create output file
            // We are putting it in a location specified when the program is run
            // This is done via a command line argument
            textFile = new PrintWriter(fileLocation + "/brokers.txt");

            // Loop through the array list of stockquotes and print delimited text to a file
            for (Broker broker : datacontainer.getBrokerList()) {
                textFile.println(broker.getName() + "," 
                        + broker.getAddress() + "," 
                        + broker.getId()
                        + "," + broker.getStatus()
                        + "," + "\n" + broker.getListOfClients()
                        + "," + "\n" + DateFunctions.dateToString(broker.getDateOfHire()) 
                        + "," + DateFunctions.dateToString(broker.getDateOfTermination())+ ","
                        + utilities.formatters.CurrencyFormatter.formatCurrencyNoDollarSign(broker.getSalary()) +"\n");
                        //+ (broker.getListOfClients()));
                
                //for(int i =0; i< broker.getListOfClients().size();i++) {
                  //  textFile.println(broker.getListOfClients().get(i).getId());
                //}
            }
        } catch (FileNotFoundException exp) {
            throw new FileException(exp.getMessage());
        } finally {
            // Flush the output stream and close the file
            if (textFile != null) {
                textFile.flush();
                textFile.close();
            }
        }
    }

    /**
     * Creates a serialized object output file containing all StockQuotes in the
     * StockQuote data container
     */
    public static void writeSerializedFile(String fileLocation, BrokerDataContainer datacontainer) throws FileException {
        try {
            // Create output file
            ObjectOutputStream serializedFile = new ObjectOutputStream(
                    new FileOutputStream(fileLocation + "/brokers.ser"));
            // Write out the data
            serializedFile.writeObject(datacontainer.getBrokerList());
        } catch (IOException exp) {
            throw new FileException("Can't serialize file");
        }
    }

    /**
     * Creates an XML output file containing all StockQuotes in the StockQuote
     * data container using the JAXB libraries
     */
    public static void writeXMLFile(String fileLocation, BrokerDataContainer brokerDataContainer) throws FileException {
        try {
            // Create the format of the xml
            JAXBContext jaxbContext = JAXBContext.newInstance(BrokerDataContainer.class);
            // Create the marshaller
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            // Create nicely formatted xml
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            //Marshal the stockquotes list into an xml file
            jaxbMarshaller.marshal(brokerDataContainer, new File(fileLocation + "/brokers.xml"));
        } catch (JAXBException exp) {
            throw new FileException(exp.getMessage());
        }
    }

    /**
     * Writes out the StockQuote data in JSON format containing all StockQuotes
     * in the broker data container
     *
     */
    public static void writeJSONFile(String fileLocation, BrokerDataContainer datacontainer) throws FileException {

        PrintWriter jsonFile = null;

        try {
            // Create output file
            jsonFile = new PrintWriter(fileLocation + "/brokers.json");

            // Create JSON object
            Gson gson = new GsonBuilder().create();

            // Convert stockquote list to JSON format
            gson.toJson(datacontainer.getBrokerList(), jsonFile);

        } catch (JsonIOException | FileNotFoundException exp) {
            throw new FileException(exp.getMessage());
        } finally {
            // Flush the output stream and close the file
            if (jsonFile != null) {
                jsonFile.flush();
                jsonFile.close();
            }
        }
    }

    /**
     * Reads a set of broker objects from a serialized file and returns an
     * array list of broker
     */
    public static ArrayList<Broker> readSerializedFile(String fileLocation) throws FileException {

        ArrayList<Broker> listOfBrokers = new ArrayList<>();

        try {
            ObjectInputStream serializedFile = new ObjectInputStream(
                    new FileInputStream(fileLocation + "/brokers.ser"));
            // Read the serialized object and cast to its original type
            listOfBrokers = (ArrayList<Broker>) serializedFile.readObject();
            return listOfBrokers;
        } catch (IOException | ClassNotFoundException exp) {
            throw new FileException("Can't deserialize file");
        }
    }

    /**
     * Reads a delimited text file of brokers and returns an array list of
     * brokers.
     *
     * An end of file flag is used to keep track of whether we hit the end of
     * the file, It starts out false and if we hit the end of file (null input),
     * it changes to true and execution stops.
     *
     * The format of the text file is:
     *
     * Example: FA301,StockQuote
     */
    public static ArrayList<Broker> readTextFile(String fileLocation) throws FileException {

        ArrayList<Broker> listOfBrokers = new ArrayList<>();

        try {
            boolean eof = false;
            BufferedReader textFile = new BufferedReader(new FileReader(fileLocation + "/brokers.txt"));
            while (!eof) {
                String lineFromFile = textFile.readLine();
                if (lineFromFile == null) {
                    eof = true;
                } else {
                    // Create a stockquote
                    Broker broker = new Broker();

                    // Split the input line into stockquote elements using the delimiter
                    String[] lineElements = lineFromFile.split(",");
                    broker.setName(lineElements[0]);
                    broker.setAddress(lineElements[1]);
                    broker.setId(Long.parseLong(lineElements[2]));
                    broker.setSalary(Double.parseDouble(lineElements[3]));
                    broker.setStatus(lineElements[4]);   
                    Calendar birthDate = DateFunctions.stringToDate(lineElements[5]);
                    broker.setDateOfBirth(birthDate);
                    Calendar hireDate = DateFunctions.stringToDate(lineElements[6]);
                    broker.setDateOfBirth(hireDate);
                    Calendar terminationDate = DateFunctions.stringToDate(lineElements[7]);
                    broker.setDateOfBirth(terminationDate);
                    // add the broker to the arraylist
                    listOfBrokers.add(broker);
                }
            }
            return listOfBrokers;
        } catch (InvalidDataException | IOException exp) {
            throw new FileException(exp.getMessage());
        }
    }

    /**
     * Read in an XML file of StockQuote objects
     *
     * @param fileLocation
     * @return
     */
    public static BrokerDataContainer readXMLFile(String fileLocation) throws FileException {

        try {
            // Create the format of the xml
            JAXBContext jaxbContext = JAXBContext.newInstance(BrokerDataContainer.class);
            // Create the unmarshaller
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            //Unmarshal the file
            return (BrokerDataContainer) jaxbUnmarshaller.unmarshal(new File(fileLocation + "/brokers.xml"));
        } catch (JAXBException exp) {
            throw new FileException(exp.getMessage());
        }

    }

    /**
     * Reads a JSON formatted file of stock quotes and returns an array list of
     * StockQuotes.
     *
     */
    public static ArrayList<Broker> readJSONFile(String fileLocation) throws FileException {

        ArrayList<Broker> listOfBrokers = new ArrayList<>();

        try {
            // Create input file
            BufferedReader jsonFile = new BufferedReader(new FileReader(fileLocation + "/brokers.json"));

            // Create JSON object
            Gson gson = new GsonBuilder().create();

            // fromJson returns an array
            Broker[] brokerArray = gson.fromJson(jsonFile, Broker[].class);

            // Convert to arraylist for the data model
            listOfBrokers.addAll(Arrays.asList(brokerArray));
            return listOfBrokers;
        } catch (JsonIOException | JsonSyntaxException | FileNotFoundException exp) {
            throw new FileException(exp.getMessage());
        }
    }
}
