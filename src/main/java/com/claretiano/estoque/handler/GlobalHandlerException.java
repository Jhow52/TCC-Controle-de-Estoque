package com.claretiano.estoque.handler;

import com.claretiano.estoque.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(UserCreateNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserCreateNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(ProductCreateNotFoundException.class)
    public ResponseEntity<String> handleEmailNotFoundException(ProductCreateNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
    
    @ExceptionHandler(CategoryCreateNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlerCategoryCreateNotFoundException(CategoryCreateNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ErrorResponse.builder()
                        .message((ex.getMessage()))
                        .time(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(CategoryEmUsoException.class)
    public ResponseEntity<String> handlerCategoryEmUsoException(CategoryEmUsoException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> handlerProductNotFoundException(ProductNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(ProductStockMinException.class)
    public ResponseEntity<String> handlerProductStockMinException(ProductStockMinException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handlerMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(ErrorResponse.builder()
                .message(("Error:"  +((BeanPropertyBindingResult) ex.getBindingResult()).getFieldErrors().getFirst().getDefaultMessage()))
                .time(LocalDateTime.now())
                .build());

    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlerCategoryNotFoundException(CategoryNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.builder()
                        .message(("Categoria não encontrada. Insira um id valido"))
                        .time(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(CategoryAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handlerCategoryAlreadyExistsException(CategoryAlreadyExistsException ex){
        return ResponseEntity.status(HttpStatus.IM_USED).body(ErrorResponse.builder()
                .message((ex.getMessage()))
                .time(LocalDateTime.now()).build());
    }

    @ExceptionHandler(InventoryNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlerInventoryNotFoundProduct(InventoryNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.builder()
                .message((ex.getMessage()))
                .time(LocalDateTime.now()).build());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlerUserNotFound(UserNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.builder()
                .message((ex.getMessage()))
                .time(LocalDateTime.now()).build());
    }

    @ExceptionHandler(PasswordIncorrectException.class)
    public ResponseEntity<ErrorResponse> handlerPasswordIncorrectException(PasswordIncorrectException ex){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorResponse.builder()
                .message((ex.getMessage()))
                .time(LocalDateTime.now()).build());
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handlerEmailAlreadyExistsException(EmailAlreadyExistsException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorResponse.builder()
                .message((ex.getMessage()))
                .time(LocalDateTime.now()).build());
    }

    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlerEmailNotFoundException(EmailNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.builder()
                .message((ex.getMessage()))
                .time(LocalDateTime.now()).build());
    }
}
