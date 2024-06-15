package com.springboot.advice;

import com.springboot.exception.BusinessLogicException;
import com.springboot.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionAdvice {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        final ErrorResponse response = ErrorResponse.of(e.getBindingResult());

        return response;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleConstraintViolationException(
            ConstraintViolationException e) {
        final ErrorResponse response = ErrorResponse.of(e.getConstraintViolations());

        return response;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleBusinessLogicException(BusinessLogicException e) {
        // TODO GlobalExceptionAdvice 기능 추가 1
        //MemberController의 getMember() 핸들러 메서드에 요청 전송 시, 아래와 같은 에러 응답 및 HttpStatus를 볼 수 있어야 합니다.
        // 현재는 요청을 전송해도 콘솔에서 로그만 출력
        return ErrorResponse.of(e);
//        return new ResponseEntity<>(HttpStatus.valueOf(e.getExceptionCode()
//                .getStatus()
//                ));
    }
    // TODO GlobalExceptionAdvice 기능 추가 2
    @ExceptionHandler
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ErrorResponse handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        // TODO GlobalExceptionAdvice 기능 추가 1
        //MemberController의 getMember() 핸들러 메서드에 요청 전송 시, 아래와 같은 에러 응답 및 HttpStatus를 볼 수 있어야 합니다.
        // 현재는 요청을 전송해도 콘솔에서 로그만 출력
        return ErrorResponse.of(e);
//        return new ResponseEntity<>(HttpStatus.valueOf(e.getExceptionCode()
//                .getStatus()
//                ));
    }


    // TODO GlobalExceptionAdvice 기능 추가 3
}
