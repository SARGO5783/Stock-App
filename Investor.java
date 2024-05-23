package datamodels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import utilities.date.DateFunctions;

/**
 Investor extends the Person class and inherits all of the public methods
 from the Person class and all of it's private member variables
 */
public class Investor extends Person implements Serializable {  

    private Calendar memberSince;
    private List<InvestorStockQuote> listOfStocks = new ArrayList<>();

    /**
     * no-arg constructor
     */
    public Investor() {}

    /**
     * overloaded constructor
     */
    public Investor(String name, String address, Calendar dateOfBirth, 
            long id, Calendar memberSince) {
        super(name, address, dateOfBirth, id);
        this.memberSince = memberSince;
    }
    
    public Calendar getMemberSince() {
        return this.memberSince;
    }

    public void setMemberSince(Calendar memberSince) {
       this.memberSince = memberSince;
    }

    public double getAccountValue() {
        double accountValue = 0;
        for (InvestorStockQuote investorStock : listOfStocks) {
            double stockValue = investorStock.getStock().getValue();
            double investorShareValue = stockValue * investorStock.getShares();
            accountValue += investorShareValue;
        }
        return accountValue;
    }

    public List<InvestorStockQuote> getListOfStocks() {
        return this.listOfStocks;
    }

    public void addStock(InvestorStockQuote value) {
        this.listOfStocks.add(value);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + Objects.hashCode(this.getId());
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
        final Investor other = (Investor) obj;
        if (this.getId() == other.getId()) {
            return true;
        }
        return true;
    }

    @Override
    public String toString() {
        return   "Invester:\n" 
                +"---------\n"
                +this.getName() + "\n"
                +this.getAddress() + "\n"
                +"Date of Birth : " + DateFunctions.dateToString(this.getDateOfBirth()) + "\n"
                +"Id : " + this.getId() + "\n"
                +"Portfolio Value : " + this.getAccountValue() + "\n";
    }

    
   
    
}
