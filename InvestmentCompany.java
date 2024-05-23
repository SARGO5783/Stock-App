/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datamodels;
import java.util.ArrayList;
import java.util.List;
import exceptionhandlers.InvalidDataException;
import java.io.Serializable;
import java.util.Objects;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
/**
 *
 * @author Racheli
 */
@XmlRootElement(name = "InvestmentCompany")
@XmlAccessorType(XmlAccessType.FIELD)
public class InvestmentCompany implements Serializable {
    
    @XmlElement(name = "companyName")
    private String companyName;
    @XmlElement(name = "listOfBrokers")
    private List<Broker> listOfBrokers;
    
    
    public InvestmentCompany(){
        
    }
    public InvestmentCompany(String companyName, List <Broker>listOfBrokers)
           throws InvalidDataException{
        
        if (companyName.isEmpty()) {
            throw new InvalidDataException(" Setting investment company name failed, company name not specified");
        }
        this.companyName = companyName;
        this.listOfBrokers = listOfBrokers;
        
    }
    public InvestmentCompany(String companyName){
        this.companyName = companyName;
    }
    public String getCompanyName(){
        return this.companyName;
    }
    public void setCompanyName( String companyName)throws InvalidDataException {
        if (companyName.isEmpty()) {
            throw new InvalidDataException(" Setting investment company name failed, company name not specified");
            } 
        else {
            this.companyName = companyName;
        }
    }
    public List<Broker> getListOfBrokers(){
        return this.listOfBrokers;
    }
    public void addBroker (Broker newBroker){
        if (this.listOfBrokers == null) {
             this.listOfBrokers = new ArrayList<Broker>();   
        }
        listOfBrokers.add(newBroker);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.companyName);
        hash = 79 * hash + Objects.hashCode(this.listOfBrokers);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final InvestmentCompany other = (InvestmentCompany) obj;
        if (!Objects.equals(this.companyName, other.companyName)) {
            return false;
        }
        return Objects.equals(this.listOfBrokers, other.listOfBrokers);
    }
    @Override
    public String toString() {
        
        String CompanyInfo = "\n" +"\n" + "InvestmentCompany:"+ this.getCompanyName() + "\n"
                + "List Of Brokers: \n" + "---------- \n";
                for(int n=0;n<this.listOfBrokers.size();n++){
                    CompanyInfo += this.listOfBrokers.get(n).getName() + "\n";
                }
        return CompanyInfo;
    }
}
