package rsoi.lab2.gservice.model;

import java.util.Date;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ErrorResponse that = (ErrorResponse) o;
        return Objects.equals(error, that.error) &&
                Objects.equals(message, that.message) &&
                Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(error, message, time);
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "error='" + error + '\'' +
                ", message='" + message + '\'' +
                ", time=" + time +
                '}';
    }
}
