package exceptionhandlers;

public class FileException extends Exception {

    public FileException() {
        super();
    }
    
    public FileException(String errorMessage) {
        super(errorMessage);
    }
}
