package apshomebe.caregility.com.exception;

import java.sql.SQLException;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class TokenControllerAdvice extends ResponseEntityExceptionHandler {

//  @ExceptionHandler(value = TokenRefreshException.class)
//  @ResponseStatus(code=HttpStatus.FORBIDDEN)
//  public ErrorMessage handleTokenRefreshException(TokenRefreshException ex, WebRequest request) {
//    return new ErrorMessage(
//        HttpStatus.FORBIDDEN.value(),
//        new Date(),
//        ex.getMessage(),
//        request.getDescription(false));
//  }

  @ExceptionHandler(value = VinNotFoundException.class)
  @ResponseStatus(code=HttpStatus.NOT_FOUND)
  public ErrorMessage handleVinNotFoundException(VinNotFoundException ex, WebRequest request) {
    return new ErrorMessage(
        HttpStatus.NOT_FOUND.value(),
        new Date(),
        ex.getMessage(),
        request.getDescription(false));
  }
  @ExceptionHandler(value = MachineNotFoundException.class)
  @ResponseStatus(code=HttpStatus.NOT_FOUND)
  public ErrorMessage handleMachineNotFoundException(MachineNotFoundException ex, WebRequest request) {
    return new ErrorMessage(
        HttpStatus.NOT_FOUND.value(),
        new Date(),
        ex.getMessage(),
        request.getDescription(false));
  }
  @ExceptionHandler(value = MachineAlredyMappedException.class)
  @ResponseStatus(code=HttpStatus.NOT_ACCEPTABLE)

  public ErrorMessage handleMachineAlredyMappedException(MachineAlredyMappedException ex, WebRequest request) {
    return new ErrorMessage(
        HttpStatus.NOT_ACCEPTABLE.value(),
        new Date(),
        ex.getMessage(),
        request.getDescription(false));
  }

  @ExceptionHandler(value = NoDataFoundException.class)
  public ResponseEntity<ErrorMessage> handleNoDataException(NoDataFoundException ex, WebRequest request) {
    ErrorMessage msg=   new ErrorMessage(
            HttpStatus.FORBIDDEN.value(),
            new Date(),
            ex.getMessage(),
            request.getDescription(false));

    return  new  ResponseEntity<ErrorMessage>(msg, HttpStatus.FORBIDDEN);
  }
  @ExceptionHandler(value = SQLException.class)
  @ResponseStatus(code=HttpStatus.FORBIDDEN)
  public ErrorMessage handleSqlException(NoDataFoundException ex, WebRequest request) {
    return new ErrorMessage(
            HttpStatus.FORBIDDEN.value(),
            new Date(),
            ex.getMessage(),
            request.getDescription(false));
  }

  @ExceptionHandler(value = TokenRefreshException.class)
  @ResponseStatus(code=HttpStatus.FORBIDDEN)
  public ResponseEntity<ErrorMessage> handleTokenRefreshException(TokenRefreshException ex, WebRequest request) {

    ErrorMessage msg=  new ErrorMessage(
            HttpStatus.BAD_REQUEST.value(),
            new Date(),
            ex.getMessage(),
            request.getDescription(false));
    return ResponseEntity.badRequest().body(msg);
  }

}