package com.project.pixchallenge.web.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.validation.FieldError;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FieldErrorDTO {

    @Schema(description = "Field")
    private String field;

    @Schema(description = "Message")
    private String message;

    public static FieldErrorDTO from(FieldError fieldError) {
        return new FieldErrorDTO(fieldError.getField(), fieldError.getDefaultMessage());
    }

}
