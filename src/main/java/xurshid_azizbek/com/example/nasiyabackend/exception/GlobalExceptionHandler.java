package xurshid_azizbek.com.example.nasiyabackend.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import xurshid_azizbek.com.example.nasiyabackend.payload.response.ApiResponse;

import java.util.stream.Collectors;
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Barcha handlerlar shu metod orqali javob quradi — takrorlanishni yo'qotish uchun
    private ResponseEntity<ApiResponse> buildResponse(String message, HttpStatus status) {
        ApiResponse apiResponse = ApiResponse.builder()
                .message(message)
                .success(false)
                .status(status)
                .body(null)
                .build();
        return ResponseEntity.status(status).body(apiResponse);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponse> notFound(NotFoundException ex) {
        log.warn("Not Found: {}", ex.getMessage(),ex);
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFound(ResourceNotFoundException ex) {
        log.warn("Resource not found: {}", ex.getMessage(),ex);
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse> userNotFound(UserNotFoundException ex) {
        log.warn("User not found: {}", ex.getMessage(),ex);
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AlreadyNameException.class)
    public ResponseEntity<ApiResponse> alreadyName(AlreadyNameException ex) {
        log.warn("Already Name Exception: {}", ex.getMessage(),ex);
        return buildResponse(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse> badRequest(BadRequestException ex) {
        log.warn("Bad Request: {}",ex.getMessage(),ex);
        return buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidation(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        log.warn("Validation Exception: {}",ex.getMessage(),message);
        return buildResponse(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleOther(Exception ex) {
        log.error("Kutilmagan xatolik: {} ", ex.getMessage(), ex);
        // Ichki xato matnini clientga chiqarmaymiz — xavfsizlik uchun
        return buildResponse("Serverda xatolik yuz berdi", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}