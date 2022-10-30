package com.project.pixchallenge.web.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static lombok.AccessLevel.PRIVATE;

@JsonInclude(NON_NULL)
@Getter
@Builder
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(access = PRIVATE)
@ToString
public class ErrorDTO {

    @Schema(description = "Code Status")
    private int code;

    @Schema(description = "Error")
    private String error;

    @Schema(description = "API Error Code")
    private String apiErrorCode;

    @Schema(description = "Message error")
    private String message;

    @Schema(description = "Field Message")
    private List<FieldErrorDTO> fieldErrors;

    @Schema(description = "Datetime event message")
    private LocalDateTime timestamp;

    public static ErrorDTO from(final HttpStatus status, final String message, final List<FieldErrorDTO> fieldErrors, final String apiErrorCode) {
        return ErrorDTO.builder()
                .code(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .fieldErrors(fieldErrors)
                .timestamp(LocalDateTime.now(ZoneId.of("UTC")))
                .apiErrorCode(apiErrorCode)
                .build();
    }

    public static ErrorDTO from(final HttpStatus status, final String message, final List<FieldErrorDTO> fieldErrors) {
        return from(status, message, fieldErrors, null);
    }

    public static ErrorDTO from(final HttpStatus status, final String message, final String apiErrorCode) {
        return from(status, message, null, apiErrorCode);
    }

    public static ErrorDTO from(final HttpStatus status, final String message) {
        return from(status, message, null, null);
    }

}