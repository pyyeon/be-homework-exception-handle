package com.springboot.response;

import com.springboot.exception.BusinessLogicException;
import com.springboot.exception.ExceptionCode;
import lombok.Getter;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class ErrorResponse {
    private static Integer status;
    private static String message;
    private List<FieldError> fieldErrors;
    private List<ConstraintViolationError> violationErrors;
    //내가 하고 싶은건 에러응답으로 HttpStatus어쩌구를 날리는 것. return http어쩌구 멤버가 없다.
    //에러 응답은 어디있느냐 > exceptioncode에!
    //에러나는 경로 : 컨트롤러에서 findmember > 멤버서비스에서  익셉션 코드 > 익셉션코드에서 출력
    //비지니스로직 클래스의 익셉션 써야함


    private ErrorResponse(Integer status, String message, final List<FieldError> fieldErrors,
                          final List<ConstraintViolationError> violationErrors) {
        this.status = status;
        this.message = message;
        this.fieldErrors = fieldErrors;
        this.violationErrors = violationErrors;

    }

    public static ErrorResponse of(BindingResult bindingResult) {
        return new ErrorResponse(null, null, FieldError.of(bindingResult), null);
    }

    public static ErrorResponse of(Set<ConstraintViolation<?>> violations) {
        return new ErrorResponse(null,null ,
                null, ConstraintViolationError.of(violations));
    }

    public static ErrorResponse of(BusinessLogicException e) {
        return new  ErrorResponse(e.getExceptionCode().getStatus(), e.getExceptionCode().getMessage(), null, null);
    }

    public static ErrorResponse of(HttpRequestMethodNotSupportedException e) {
        return new  ErrorResponse( null, null);
    }
    @Getter
    public static class FieldError {
        private String field;
        private Object rejectedValue;
        private String reason;

        private FieldError(String field, Object rejectedValue, String reason) {
            this.field = field;
            this.rejectedValue = rejectedValue;
            this.reason = reason;
        }

        public static List<FieldError> of(BindingResult bindingResult) {
            final List<org.springframework.validation.FieldError> fieldErrors =
                                                        bindingResult.getFieldErrors();
            return fieldErrors.stream()
                    .map(error -> new FieldError(
                            error.getField(),
                            error.getRejectedValue() == null ?
                                            "" : error.getRejectedValue().toString(),
                            error.getDefaultMessage()))
                    .collect(Collectors.toList());
        }
    }

    @Getter
    public static class ConstraintViolationError {
        private String propertyPath;
        private Object rejectedValue;
        private String reason;

        private ConstraintViolationError(String propertyPath, Object rejectedValue,
                                   String reason) {
            this.propertyPath = propertyPath;
            this.rejectedValue = rejectedValue;
            this.reason = reason;
        }

        public static List<ConstraintViolationError> of(
                Set<ConstraintViolation<?>> constraintViolations) {
            return constraintViolations.stream()
                    .map(constraintViolation -> new ConstraintViolationError(
                            constraintViolation.getPropertyPath().toString(),
                            constraintViolation.getInvalidValue().toString(),
                            constraintViolation.getMessage()
                    )).collect(Collectors.toList());
        }
    }
}