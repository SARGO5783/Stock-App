package datamodels;

import exceptionhandlers.InvalidDataException;
import java.util.Calendar;
import java.util.Objects;
import interfaces.PersonInterface;
import java.io.Serializable;
import utilities.date.DateFunctions;

/**
 * Class which defines a generic Person. This class is extended by other classes
 * that will create specific types of Person (ie. Broker, Investor)
 */
public abstract class Person implements PersonInterface, Serializable {

    private String name;
    private String address;
    private Calendar dateOfBirth;
    private long id;

    /**
     * no-arg constructor
     */
    public Person() {
    }

    /**
     * Overloaded constructor
     */
    public Person(String name, String address, Calendar dateOfBirth, long id) {
        this.name = name;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.id = id;
    }

    @Override
    public long getId() {
        return this.id;
    }

    @Override
    public void setId(long id) throws InvalidDataException {

        // Ensure the id is a valid positive integer
        if (id < 0) {
            throw new InvalidDataException("Setting id failed, invalid id: " + id);
        } else {
            this.id = id;
        }
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) throws InvalidDataException {
        if (name.isEmpty()) {
            throw new InvalidDataException("Setting name failed, no name specified");
        } else {
            this.name = name;
        }
    }

    @Override
    public String getAddress() {
        return this.address;
    }

    @Override
    public void setAddress(String address) throws InvalidDataException {
        if (address.isEmpty()) {
            throw new InvalidDataException("Setting address failed, no address specified");
        } else {
            this.address = address;
        }
    }

    @Override
    public Calendar getDateOfBirth() {
        return this.dateOfBirth;
    }

    @Override
    public void setDateOfBirth(Calendar dateOfBirth) {
       this.dateOfBirth = dateOfBirth;
    }

    /**
     * These methods compares two Person objects to determine equality.
     *
     * @param obj the object being compared
     * @return true if members are equal
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 73 * hash + Objects.hashCode(this.name);
        hash = 73 * hash + Objects.hashCode(this.address);
        hash = 73 * hash + Objects.hashCode(this.dateOfBirth);
        hash = 73 * hash + (int) (this.id ^ (this.id >>> 32));
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
        final Person other = (Person) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Person{" + "name=" + name + ", address=" + address
                + ", dateOfBirth=" + DateFunctions.dateToString(dateOfBirth) + ", id=" + id + '}';
    }

}
