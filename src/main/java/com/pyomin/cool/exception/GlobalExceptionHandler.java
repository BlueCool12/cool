package com.pyomin.cool.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusiness(BusinessException ex,
            HttpServletRequest request) {
        ErrorCode errorCode = ex.getErrorCode();
        ErrorResponse body = ErrorResponse.of(errorCode.name(), ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(errorCode.getStatus()).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleOthers(Exception ex, HttpServletRequest request) {
        ErrorResponse body = ErrorResponse.of("INTERNAL_SERVER_ERROR",
                "예상치 못한 오류가 발생했습니다.", request.getRequestURI());
        return ResponseEntity.internalServerError().body(body);
    }
}
