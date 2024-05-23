/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datamodels;
import java.util.ArrayList;
import java.util.List;
import utilities.date.DateFunctions;
import exceptionhandlers.InvalidDataException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Objects;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
/**
 *
 * @author Racheli
 */
/** XML element tags are Used to read/write XML  */
@XmlRootElement(name = "Broker")
@XmlAccessorType(XmlAccessType.FIELD)
public class Broker extends Person implements Serializable {

    @XmlElement(name = "dateOfHire")
    private Calendar dateOfHire;
    @XmlElement(name = "dateOfTermination")
    private Calendar dateOfTermination;
    @XmlElement(name = "salary")    
    private double salary;
    @XmlElement(name = "status")
    private String status;
    @XmlElement(name = "listOfClients")
    private List<Investor> listOfClients; 
    
    
    public Broker(){
        
    }
    public Broker (String name, String address, Calendar dateOfBirth, long id, 
                   Calendar dateOfHire, Calendar dateOfTermination, double salary, String status)
        throws InvalidDataException{
        super(name, address, dateOfBirth, id);
        if (name.isEmpty()) {
            throw new InvalidDataException("Setting Person failed, no name specified");
            }
        if (address.isEmpty()) {
            throw new InvalidDataException("Setting Person failed, no address specified");
            }
        
        if (id <=0) {
            throw new InvalidDataException("Setting Id failed, invalid id:" + super.getId() );
            }

        if (salary <=0) {
            throw new InvalidDataException("Setting broker salary failed, broker salary is invalid" );
            }   
        if ((!status.equals("Full Time")) && (!status.equals("Part Time"))) {
         throw new InvalidDataException("Creating broker failed, broker status is invalid");
            }
        
        
        this.dateOfHire = dateOfHire;
        this.dateOfTermination = dateOfTermination;
        this.salary = salary;
        this.status = status;
        
    }
    
    public Calendar getDateOfHire(){
        return this.dateOfHire;
    }
    
    public void setDateOfHire(Calendar dateOfHire){
        this.dateOfHire = dateOfHire;
    }
    
     public Calendar getDateOfTermination(){
        return this.dateOfTermination;
    }
    
    public void setDateOfTermination(Calendar dateOfTermination){
        this.dateOfTermination = dateOfTermination;
    }
    
    public double getSalary(){
        return this.salary;
    }
    
    public void setSalary(double salary) throws InvalidDataException {
        if (salary<=0) {
            throw new InvalidDataException("Setting broker salary failed, broker salary is invalid" );
            } 
        else {
            this.salary = salary;
        }
    }
    
    public String getStatus(){
        return this.status;
    }
    
    public void setStatus(String status)throws InvalidDataException {
        if ((!status.equals("Full Time") && (!status.equals("Part Time")))) {
            throw new InvalidDataException("Setting broker status failed, broker status is invalid ");
            } 
        else {
            this.status = status;
        }
    }
    public List<Investor> getListOfClients(){
        return this.listOfClients;
    }

    public void addClient( Investor newClient){
        if (this.listOfClients == null) {
             this.listOfClients = new ArrayList<Investor>();   
        }
          this.listOfClients.add(newClient);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.dateOfHire);
        hash = 17 * hash + Objects.hashCode(this.dateOfTermination);
        hash = 17 * hash + (int) (Double.doubleToLongBits(this.salary) ^ (Double.doubleToLongBits(this.salary) >>> 32));
        hash = 17 * hash + Objects.hashCode(this.status);
        hash = 17 * hash + Objects.hashCode(this.listOfClients);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Broker other = (Broker) obj;
        if (Double.doubleToLongBits(this.salary) != Double.doubleToLongBits(other.salary)) {
            return false;
        }
        if (!Objects.equals(this.status, other.status)) {
            return false;
        }
        if (!Objects.equals(this.dateOfHire, other.dateOfHire)) {
            return false;
        }
        if (!Objects.equals(this.dateOfTermination, other.dateOfTermination)) {
            return false;
        }
        return Objects.equals(this.listOfClients, other.listOfClients);
    }
    /**
     * Returns a String representation of the StockQuote object in JSON format
     */
    @Override
    public String toString() {
        
        String BrokerInfo =  "Broker:" + "\n"
                 + "---------- \n"
                + super.getName() + " \n"
                + super.getAddress() + " \n"
                + "Date Of Birth:"  + DateFunctions.dateToString(super.getDateOfBirth()) + "\n"
                + "Date Of Hire:"  + DateFunctions.dateToString(this.getDateOfHire()) + "\n"
                + "Date Of Termination:"  + DateFunctions.dateToString(this.getDateOfTermination()) + "\n"
                + "Salary:" + this.getSalary() + "\n"
                + "Status:" + this.getStatus() + "\n"
                + "Id:" + super.getId() + "\n" +"\n"
                + "List Of Clients:" + "\n"
                + "---------- \n";
                for(int n=0;n<this.listOfClients.size();n++){
                    BrokerInfo += this.listOfClients.get(n).getName() + "\n";
                }
        return BrokerInfo;
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

