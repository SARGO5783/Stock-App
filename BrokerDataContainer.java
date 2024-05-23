/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datacontainers;
import datamodels.Broker;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

// Required to use JAXB XML library
@XmlRootElement(name = "BrokerDataContainer")
@XmlAccessorType(XmlAccessType.FIELD)

/**
 *
 * @author Racheli
 */
public class BrokerDataContainer {
    /*
 *  This Class contains several containers which can hold classroom objects 
 *  created in the UI
 */
    
    /** Simple container that stores elements as a list, can contain duplicates 
     *  Stores in FIFO order
     */
       // Required to use JAXB XML library
   @XmlElement(name = "brokerData")   
    private List<Broker> brokerList = new ArrayList<>();
    
    
    
    public List<Broker> getBrokerList() {
        return brokerList;
    }

    public void setBrokerList(List<Broker> brokerList) {
        this.brokerList = brokerList;
    }
}