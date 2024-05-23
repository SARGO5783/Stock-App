/*
 *  This Class contains several containers which can hold Investor objects
 *  created in the UI
 */
package datacontainers;

import datamodels.Investor;
import java.util.ArrayList;
import java.util.List;

public class InvestorDataContainer {

    /**
     * Simple container that stores elements as a list, can contain duplicates
     * Stores in FIFO order
     */
    private List<Investor> investorList = new ArrayList<>();    // list of Investor objects


    /**
     * Gets the list of Investor objects
     *
     * @return InvestorList   the list of Investor objects
     */
    public List<Investor> getInvestorList() { return investorList; }

    /**
     * Sets the list of Investor objects
     *
     * @param InvestorList the list of Investor objects
     */
    public void setInvestorList(List<Investor> investorList) { this.investorList = investorList; }

}