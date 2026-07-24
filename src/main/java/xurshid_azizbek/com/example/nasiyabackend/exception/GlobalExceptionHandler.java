package xurshid_azizbek.com.example.nasiyabackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import xurshid_azizbek.com.example.nasiyabackend.payload.ApiResponse;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponse> notFound(NotFoundException ex){
        ApiResponse apiResponse= ApiResponse.builder()
                .message(ex.getMessage())
                .success(false)
                .status(HttpStatus.NOT_FOUND)
                .body(null)
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidation(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        ApiResponse response = ApiResponse.builder()
                .message(message)
                .success(false)
                .status(HttpStatus.BAD_REQUEST)
                .body(null)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleOther(Exception ex) {
        ApiResponse response = ApiResponse.builder()
                .message(ex.getMessage())
                .success(false)
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(null)
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
    @ExceptionHandler(AlreadyNameException.class)
    public ResponseEntity<ApiResponse> alreadyName(AlreadyNameException ex){
        ApiResponse apiResponse=ApiResponse.builder()
                .message(ex.getMessage())
                .success(false)
                .status(HttpStatus.CONFLICT)
                .body(null)
                .build();
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse>  badRequest(BadRequestException ex){
        ApiResponse apiResponse=ApiResponse.builder()
                .message(ex.getMessage())
                .success(false)
                .status(HttpStatus.BAD_REQUEST)
                .body(null)
                .build();
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFount(ResourceNotFoundException exception){
        ApiResponse apiResponse=ApiResponse.builder()
                .message(exception.getMessage())
                .success(false)
                .status(HttpStatus.NOT_FOUND)
                .body(null)
                .build();
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }
}
