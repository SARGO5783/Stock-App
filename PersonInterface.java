package interfaces;

import exceptionhandlers.InvalidDataException;
import java.util.Calendar;

/**
 * This interface lists all of the methods which need to be implemented in the
 * Person class
 */
public interface PersonInterface {

    public String getName();

    public void setName(String value) throws InvalidDataException;

    public String getAddress();

    public void setAddress(String value) throws InvalidDataException;

    public Calendar getDateOfBirth();

    public void setDateOfBirth(Calendar value);

    public long getId();

    public void setId(long value) throws InvalidDataException;

    @Override
    boolean equals(Object obj);

    @Override
    int hashCode();

    @Override
    String toString();

}
