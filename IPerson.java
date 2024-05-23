/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;
import exceptionhandlers.InvalidDataException;
import java.util.Calendar;


 public interface IPerson {

   /**
     * Returns the name of the Person
     */
   public String getName();
    /**
     * Returns the address of the Person
     */
   public String getAddress();
     /**
     * Returns the socialSecurityNumber of the Person
     */
   //public String getSocialSecurityNumber();
      /**
     * Returns the dateOfBirth of the Person
     */
   public Calendar getDateOfBirth();
      /**
     * Returns the current GPA of the Person
     */
   //public float getCurrentGPA();
   /**
     * Sets the name of the StockQuote object
     */
   public void setName(String name) throws InvalidDataException;
      /**
     * Sets the address of the StockQuote object
     */
   public void setAddress(String address) throws InvalidDataException;
      /**
     * Sets the socialSecurity Number of the StockQuote object
     */
   //public void setSocialSecurityNumber (String ssn);
      /**
     * Sets the date of Birth of the StockQuote object
     */
   public void setDateOfBirth(Calendar dateOfBirth); 
    /**
     * Returns a String representation of the StockQuote object in JSON format
     */
   public String toString();
}
