/*
/*
 *  This Class contains methods to write out the stockquote objects in several different formats
 *  and read in the data in the same formats.
 */
package utilities.io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import datacontainers.InvestmentCompanyDataContainer;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import datamodels.InvestmentCompany;
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
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import utilities.date.DateFunctions;

public class InvestmentCompanyIO implements Serializable {

    /**
     * Constructor is declared private because the IO classes are utilities
     * which contain static methods and should never be instantiated
     */
    private InvestmentCompanyIO() {
    }

    /**
     * Writes out a text file containing all stock quotes in the stock quote
     * data container
     *
     * The format of the text file is:
     *
     * Example: FA301,STOCKQUOTE
     */
    public static void writeTextFile(String fileLocation, InvestmentCompanyDataContainer datacontainer) throws FileException {

        PrintWriter textFile = null;

        try {
            // Create output file
            // We are putting it in a location specified when the program is run
            // This is done via a command line argument
            textFile = new PrintWriter(fileLocation + "/investmentcompany.txt");

            // Loop through the array list of stockquotes and print delimited text to a file
            for (InvestmentCompany company : datacontainer.getInvestmentCompanyList()) {
                textFile.println(company.getCompanyName() + "," + company.getListOfBrokers());
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
    public static void writeSerializedFile(String fileLocation, InvestmentCompanyDataContainer datacontainer) throws FileException {
        try {
            // Create output file
            ObjectOutputStream serializedFile = new ObjectOutputStream(
                    new FileOutputStream(fileLocation + "/investmentcompany.ser"));
            // Write out the data
            serializedFile.writeObject(datacontainer.getInvestmentCompanyList());
        } catch (IOException exp) {
            throw new FileException("Can't serialize file");
        }
    }

    /**
     * Creates an xml output file containing all StockQuotes in the StockQuote
     * data container using the JAXB libraries
     */
    public static void writeXMLFile(String fileLocation, InvestmentCompanyDataContainer investmentcompanyDataContainer) throws FileException {
        try {
            // Create the format of the xml
            JAXBContext jaxbContext = JAXBContext.newInstance(InvestmentCompanyDataContainer.class);
            // Create the marshaller
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            // Create nicely formatted xml
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            //Marshal the stockquotes list into an xml file
            jaxbMarshaller.marshal(investmentcompanyDataContainer, new File(fileLocation + "/investmentcompany.xml"));
        } catch (JAXBException exp) {
            throw new FileException(exp.getMessage());
        }
    }

    /**
     * Writes out the StockQuote data in JSON format containing all StockQuotes
     * in the InvestmentCompany data container
     *
     */
    public static void writeJSONFile(String fileLocation, InvestmentCompanyDataContainer datacontainer) throws FileException {

        PrintWriter jsonFile = null;

        try {
            // Create output file
            jsonFile = new PrintWriter(fileLocation + "/investmentcompany.json");

            // Create JSON object
            Gson gson = new GsonBuilder().create();

            // Convert stockquote list to JSON format
            gson.toJson(datacontainer.getInvestmentCompanyList(), jsonFile);

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
     * Reads a set of InvestmentCompany objects from a serialized file and returns an
     * array list of InvestmentCompany
     */
    public static ArrayList<InvestmentCompany> readSerializedFile(String fileLocation) throws FileException {

        ArrayList<InvestmentCompany> listOfInvestmentCompany = new ArrayList<>();

        try {
            ObjectInputStream serializedFile = new ObjectInputStream(
                    new FileInputStream(fileLocation + "/investmentcompany.ser"));
            // Read the serialized object and cast to its original type
            listOfInvestmentCompany = (ArrayList<InvestmentCompany>) serializedFile.readObject();
            return listOfInvestmentCompany;
        } catch (IOException | ClassNotFoundException exp) {
            throw new FileException("Can't deserialize file");
        }
    }

    /**
     * Reads a delimited text file of InvestmentCompany and returns an array list of
     * InvestmentCompany.
     *
     * An end of file flag is used to keep track of whether we hit the end of
     * the file, It starts out false and if we hit the end of file (null input),
     * it changes to true and execution stops.
     *
     * The format of the text file is:
     *
     * Example: FA301,StockQuote
     */
    public static ArrayList<InvestmentCompany> readTextFile(String fileLocation) throws FileException {

        ArrayList<InvestmentCompany> listOfInvestmentCompany = new ArrayList<>();

        try {
            boolean eof = false;
            BufferedReader textFile = new BufferedReader(new FileReader(fileLocation + "/investmentcompany.txt"));
            while (!eof) {
                String lineFromFile = textFile.readLine();
                if (lineFromFile == null) {
                    eof = true;
                } else {
                    // Create a stockquote
                    InvestmentCompany company = new InvestmentCompany();

                    // Split the input line into stockquote elements using the delimiter
                    String[] lineElements = lineFromFile.split(",");

                    // The first element is the ticker symbol
                    company.setCompanyName(lineElements[0]);

                    // add the stockquote to the arraylist
                        listOfInvestmentCompany.add(company);
                }
            }
            return listOfInvestmentCompany;
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
    public static InvestmentCompanyDataContainer readXMLFile(String fileLocation) throws FileException {

        try {
            // Create the format of the xml
            JAXBContext jaxbContext = JAXBContext.newInstance(InvestmentCompanyDataContainer.class);
            // Create the unmarshaller
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            //Unmarshal the file
            return (InvestmentCompanyDataContainer) jaxbUnmarshaller.unmarshal(new File(fileLocation + "/investmentcompany.xml"));
        } catch (JAXBException exp) {
            throw new FileException(exp.getMessage());
        }

    }

    /**
     * Reads a JSON formatted file of stock quotes and returns an array list of
     * StockQuotes.
     *
     */
    public static ArrayList<InvestmentCompany> readJSONFile(String fileLocation) throws FileException {

        ArrayList<InvestmentCompany> listOfInvestmentCompany = new ArrayList<>();

        try {
            // Create input file
            BufferedReader jsonFile = new BufferedReader(new FileReader(fileLocation + "/investmentcompany.json"));

            // Create JSON object
            Gson gson = new GsonBuilder().create();

            // fromJson returns an array
            InvestmentCompany[] investmentcompanyArray = gson.fromJson(jsonFile, InvestmentCompany[].class);

            // Convert to arraylist for the data model
            listOfInvestmentCompany.addAll(Arrays.asList(investmentcompanyArray));
            return listOfInvestmentCompany;
        } catch (JsonIOException | JsonSyntaxException | FileNotFoundException exp) {
            throw new FileException(exp.getMessage());
        }
    }
}
