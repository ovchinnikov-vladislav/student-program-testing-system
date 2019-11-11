package rsoi.lab2.uservice.model;

import java.util.Date;

public class ErrorResponse {
    private String error;
    private String message;
    private Date time;

    public ErrorResponse() {
        this.time = new Date();
    }

    public ErrorResponse(String error, String message) {
        this.error = error;
        this.message = message;
        this.time = new Date();
    }

    public ErrorResponse(String error, String message, Date time) {
        this.error = error;
        this.message = message;
        this.time = time;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
