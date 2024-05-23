package datamodels;

import java.io.Serializable;
import java.util.Objects;

/**
 * This class creates an InvestorStockQuote object
 */
public class InvestorStockQuote implements Serializable {

    private StockQuote stock;
    private int shares=10;

    /**
     * no-arg constructor
     */
    public InvestorStockQuote() {
    }

    /**
     * Overloaded constructor
     *
     * @param int Number of shares
     * @param stock The StockQuote object
     */
    public InvestorStockQuote(StockQuote stock, int shares) {
        this.stock = stock;
        this.shares = shares;
    }

    public int getShares() {
        return this.shares;
    }

    public void setShares(int shares) {
        this.shares = shares;
    }

    public StockQuote getStock() {
        return this.stock;
    }

    public void setStock(StockQuote stock) {
        this.stock = stock;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.stock);
        hash = 41 * hash + this.shares;
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
        final InvestorStockQuote other = (InvestorStockQuote) obj;
        if (this.shares != other.shares) {
            return false;
        }
        if (!Objects.equals(this.stock, other.stock)) {
            return false;
        }
        return true;
    }
    
    

    @Override
    public String toString() {
        return "InvestorStockQuote{" + "stock=" + stock + ", shares=" + shares + '}';
    }
    
    

}
