package ua.com.sinenko.things.common.exception;


import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(AppExceptionHandler.class);

    public AppExceptionHandler() {
        super();
    }

    @ExceptionHandler({ResourceNotFoundException.class, EntityNotFoundException.class, UsernameNotFoundException.class})
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException, HttpServletRequest request) {
        ErrorDescription errorDescription = new ErrorDescription();
        errorDescription.setTimestamp(String.valueOf(new Date()));
        errorDescription.setStatus(HttpStatus.NOT_FOUND.value());
        errorDescription.setTitle("Resource Not Found");
        errorDescription.setDetail(resourceNotFoundException.getMessage());

        return new ResponseEntity<>(errorDescription, null, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserExistsException.class)
    public ResponseEntity<?> userExistsException(UserExistsException userExistsException, HttpServletRequest request) {
        ErrorDescription errorDescription = new ErrorDescription();
        errorDescription.setTimestamp(String.valueOf(new Date()));
        errorDescription.setStatus(HttpStatus.BAD_REQUEST.value());
        errorDescription.setTitle(userExistsException.getMessage());
        errorDescription.setDetail(userExistsException.getMessage());

        return new ResponseEntity<>(errorDescription, null, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<?> handleSQLIntegrityConstraintViolationException(BadCredentialsException badCredentialsException, HttpServletRequest request) {
        ErrorDescription errorDescription = new ErrorDescription();
        errorDescription.setTimestamp(String.valueOf(new Date()));
        errorDescription.setStatus(HttpStatus.UNAUTHORIZED.value());
        errorDescription.setTitle("Username or password is incorrect");
        errorDescription.setDetail(badCredentialsException.getMessage());

        return new ResponseEntity<>(errorDescription, null, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({SQLIntegrityConstraintViolationException.class})
    public ResponseEntity<?> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException sqlIntegrityConstraintViolationException, HttpServletRequest request) {
        ErrorDescription errorDescription = new ErrorDescription();
        errorDescription.setTimestamp(String.valueOf(new Date()));
        errorDescription.setStatus(HttpStatus.NOT_FOUND.value());
        errorDescription.setTitle("Resource Not Found");
        errorDescription.setDetail(sqlIntegrityConstraintViolationException.getMessage());

        return new ResponseEntity<>(errorDescription, null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status) {
        ErrorDescription errorDescription = new ErrorDescription();
        errorDescription.setTimestamp(String.valueOf(new Date().getTime()));
        errorDescription.setStatus(HttpStatus.BAD_REQUEST.value());
        errorDescription.setTitle("Validation Errors");
        errorDescription.setDetail("Input validation failed");

        return new ResponseEntity<>(errorDescription, headers, HttpStatus.BAD_REQUEST);
    }

    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status) {
        ErrorDescription errorDescription = new ErrorDescription();
        errorDescription.setTimestamp(String.valueOf(new Date().getTime()));
        errorDescription.setStatus(status.value());
        errorDescription.setTitle("Message Not Readable");
        errorDescription.setDetail(ex.getMessage());

        return new ResponseEntity<>(errorDescription, headers, status);
    }


    protected ResponseEntity<Object>
    handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
                                    HttpHeaders headers, HttpStatus status) {
        ErrorDescription errorDescription = new ErrorDescription();
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(" "));

        errorDescription.setTimestamp(String.valueOf(new Date().getTime()));
        errorDescription.setStatus(status.value());
        errorDescription.setTitle("Method not supported");
        errorDescription.setDetail(builder.toString());

        return new ResponseEntity<>(errorDescription, null, HttpStatus.BAD_REQUEST);
    }


    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers, HttpStatus status) {
        ErrorDescription errorDescription = new ErrorDescription();
        errorDescription.setTimestamp(String.valueOf(new Date().getTime()));
        errorDescription.setStatus(HttpStatus.BAD_REQUEST.value());
        errorDescription.setTitle(ex.getVariableName() + " Missing Path Variable");
        errorDescription.setDetail(ex.getMessage());

        return new ResponseEntity<>(errorDescription, null, HttpStatus.BAD_REQUEST);
    }


    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status) {
        ErrorDescription errorDescription = new ErrorDescription();
        errorDescription.setTimestamp(String.valueOf(new Date().getTime()));
        errorDescription.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorDescription.setTitle("Internal Server Error");
        errorDescription.setDetail(ex.getMessage());

        return new ResponseEntity<>(errorDescription, null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({BaseException.class})
    protected ResponseEntity<?> handleBaseException(BaseException ex, HttpServletRequest request) {
        ErrorDescription errorDescription = new ErrorDescription();
        errorDescription.setTimestamp(String.valueOf(new Date().getTime()));
        errorDescription.setStatus(HttpStatus.BAD_REQUEST.value());
        errorDescription.setTitle("Exception");
        errorDescription.setDetail(ex.getMessage());

        logger.error("Unexpected error occurred.", ex);

        return new ResponseEntity<>(errorDescription, null, HttpStatus.BAD_REQUEST);
    }
}
