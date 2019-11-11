package rsoi.lab2.teservice.controller;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import rsoi.lab2.teservice.exception.HttpNotFoundException;
import rsoi.lab2.teservice.model.ErrorResponse;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionController {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ErrorResponse requestHttpMessageNotReadableException(MethodArgumentNotValidException exc) {
        List<FieldError> errors = exc.getBindingResult().getFieldErrors();
        String result = errors.stream().map(err -> String.format("Field %s has wrong value: [%s]", err.getField(), err.getDefaultMessage()))
                .collect(Collectors.joining("; "));
        logger.error("Bad Request: {}", result);
        return new ErrorResponse(HttpStatus.BAD_REQUEST.toString(), "Bad Request: " + result, new Date());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidFormatException.class)
    @ResponseBody
    public ErrorResponse requestJsonParseException(InvalidFormatException exc) {
        logger.error("Bad Request: {}", exc.getMessage());
        return new ErrorResponse(HttpStatus.BAD_REQUEST.toString(), "Bad Request: " + exc.getMessage(), new Date());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(HttpNotFoundException.class)
    @ResponseBody
    public ErrorResponse requestHandlingNoHandlerFound(HttpNotFoundException exc) {
        logger.error("Not Found: {}", exc.getMessage());
        return new ErrorResponse(HttpStatus.NOT_FOUND.toString(), exc.getMessage(), new Date());
    }
}
