package rsoi.lab2.taskservice.exception;

public class HttpNotFoundException extends RuntimeException {

    public HttpNotFoundException(String message) {
        super(message);
    }
}
