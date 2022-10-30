package com.project.pixchallenge.web;

import com.project.pixchallenge.core.usecases.CreateKeyUseCase;
import com.project.pixchallenge.web.dto.request.CreateKeyRequestWebDTO;
import com.project.pixchallenge.web.dto.response.KeyResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Tag(name = "KeysController", description = "Gerenciamento de Chaves PIX")
@RequestMapping(value = "/keys", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@Slf4j
@RequiredArgsConstructor
public class KeyController {

    private final CreateKeyUseCase createKeyUseCase;

    @Operation(description = "Inclusão de Chaves PIX")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public KeyResponseDTO create(@RequestBody @Valid @NotNull final CreateKeyRequestWebDTO requestWebDTO) {

        log.info("Key_creating {}", requestWebDTO.getKeyValue());

        var keyValidator = requestWebDTO.getKeyType().getValidator();
        keyValidator.validate(requestWebDTO.getKeyValue());

        var savedKey = createKeyUseCase.execute(requestWebDTO.toDomain());

        log.info("Key_saved", savedKey);

        return KeyResponseDTO.from(savedKey);
    }

}
