package service;

public class ServiceException extends Exception {
    
    public ServiceException() {
    }
    
    public ServiceException(String message) {
        super(message);
    }
    
    public ServiceException(Exception cause) {
        super(cause.getMessage(), cause);
    }
}
