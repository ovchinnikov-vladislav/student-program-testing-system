package rsoi.lab2.rservice.exception;

public class HttpNotFoundException extends RuntimeException {

    public HttpNotFoundException(String message) {
        super(message);
    }
}
