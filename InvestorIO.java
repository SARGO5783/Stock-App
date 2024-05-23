package utilities.io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import datacontainers.InvestorDataContainer;
import datamodels.Investor;
import exceptionhandlers.FileException;


public class InvestorIO  {

	   /**
	    * Constructor is declared private because the IO classes are utilities which
	    * contain static methods and should never be instantiated
	    */
	   private InvestorIO() {
	   }

	   
	   /**
	    * Writes out the Investor data in JSON format containing all Investor objects
	    * in the Investor data container
	    *
	    */
	   public static void writeJSONFile(String fileLocation, InvestorDataContainer datacontainer) throws FileException {

	      PrintWriter jsonFile = null;

	      try {
	         // Create output file
	         jsonFile = new PrintWriter(fileLocation + "/investors.json");

	         // Create JSON object
	         Gson gson = new GsonBuilder().create();

	         // Convert investor list to JSON format
	         gson.toJson(datacontainer.getInvestorList(), jsonFile);

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
	    * Reads a JSON formatted file of investors and returns an array list of
	    * Investor objects.
	    *
	    */
	   public static ArrayList<Investor> readJSONFile(String fileLocation) throws FileException {

	      ArrayList<Investor> listOfInvestors = new ArrayList<>();

	      try {
	         // Create input file
	         BufferedReader jsonFile = new BufferedReader(new FileReader(fileLocation + "/investors.json"));

	         // Create JSON object
	         Gson gson = new GsonBuilder().create();

	         // fromJson returns an array
	         Investor[] investorArray = gson.fromJson(jsonFile, Investor[].class);

	         // Convert to arraylist for the data model
	         listOfInvestors.addAll(Arrays.asList(investorArray));
	         return listOfInvestors;
	      } catch (JsonIOException | JsonSyntaxException | FileNotFoundException exp) {
	         throw new FileException(exp.getMessage());
	      }
	   }
	   
	   	   
}	   