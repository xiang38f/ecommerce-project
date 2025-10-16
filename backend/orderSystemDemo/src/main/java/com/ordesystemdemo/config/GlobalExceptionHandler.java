package com.ordesystemdemo.config;

import com.ordesystemdemo.excption.OrderValidationException;
import com.ordesystemdemo.excption.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice // 宣告這是一個全域的例外處理組件
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private static final String ERROR = "error";
    private static final String MESSAGE = "message";

    @ResponseStatus(HttpStatus.BAD_REQUEST) // 設定回應的 HTTP 狀態碼為 400
    @ExceptionHandler(MethodArgumentNotValidException.class) // 指定處理哪種類型的例外
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        // 從例外物件中獲取所有欄位的驗證錯誤
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField(); // 獲取錯誤的欄位名稱
            String errorMessage = error.getDefaultMessage(); // 獲取我們在DTO中定義的錯誤訊息
            errors.put(fieldName, errorMessage);
        });

        return errors;
    }

    @ExceptionHandler(OrderValidationException.class) // 3. 確保這裡攔截的是 OrderValidationException.class
    public ResponseEntity<Map<String, String>> handleOrderValidationException(OrderValidationException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put(ERROR, "Order Validation Failed");
        errorResponse.put(MESSAGE, ex.getMessage()); // 取得我們在 Service 中拋出的具體錯誤訊息
        log.warn("Order validation failed: {}", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put(ERROR, "Invalid JSON Request");
        errorResponse.put(MESSAGE, "請求的 JSON 格式或資料類型有誤，請檢查您傳送的資料。");

        // 印出詳細的錯誤日誌，方便後端除錯
        // ex.getCause() 通常會包含更具體的錯誤原因
        log.error("JSON parse error: {}", ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put(ERROR, "Resource Not Found");
        errorResponse.put(MESSAGE, ex.getMessage());
        log.warn("Resource not found: {}", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}