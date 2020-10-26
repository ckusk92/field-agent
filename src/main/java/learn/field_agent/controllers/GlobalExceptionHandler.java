package learn.field_agent.controllers;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//@ControllerAdvice
//public class GlobalExceptionHandler {

//    // Should be able to find more specific Exceptions in here
//    @ExceptionHandler(DataAccessException.class)
//    public ResponseEntity<ErrorResponse> handleException(DataAccessException ex) {
//
////        return new ResponseEntity<ErrorResponse>(
////                new ErrorResponse("We can't show you the details, but something went wrong in our database. Sorry :("),
////                HttpStatus.INTERNAL_SERVER_ERROR);
//
//        return new ResponseEntity<>(
//                "We can't show you the details, but something went wrong in our database. Sorry :(",
//                HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//    // IllegalArgumentException is the super class for many Java exceptions
//    // including all formatting (number, date) exceptions.
//    @ExceptionHandler(IllegalArgumentException.class)
//    public ResponseEntity<ErrorResponse> handleException(IllegalArgumentException ex) {
//
//        return new ResponseEntity<ErrorResponse>(
//                new ErrorResponse(ex.getMessage()),
//                HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//    // This is our absolute last resort. Yuck.
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
//        return new ResponseEntity<>(
//                HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//}
