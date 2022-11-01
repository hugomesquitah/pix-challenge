package com.project.pixchallenge.web;

import com.project.pixchallenge.core.usecases.CreateKeyUseCase;
import com.project.pixchallenge.core.usecases.DeleteKeyUseCase;
import com.project.pixchallenge.core.usecases.ListKeyUseCase;
import com.project.pixchallenge.core.usecases.UpdateKeyUseCase;
import com.project.pixchallenge.web.dto.request.CreateKeyRequestWebDTO;
import com.project.pixchallenge.web.dto.request.ListKeysRequestWebDTO;
import com.project.pixchallenge.web.dto.request.UpdateKeyRequestWebDTO;
import com.project.pixchallenge.web.dto.response.KeyResponseWebDTO;
import com.project.pixchallenge.web.dto.response.ListKeysResponseWebDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Tag(name = "KeysController", description = "Gerenciamento de Chaves PIX")
@RequestMapping(value = "/keys", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@Slf4j
@RequiredArgsConstructor
public class KeyController {

    private final CreateKeyUseCase createKeyUseCase;
    private final UpdateKeyUseCase updateKeyUseCase;
    private final DeleteKeyUseCase deleteKeyUseCase;
    private final ListKeyUseCase listKeyUseCase;

    @Operation(description = "Inclusão de Chaves PIX")
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public KeyResponseWebDTO create(@RequestBody @Valid @NotNull final CreateKeyRequestWebDTO requestWebDTO) {

        log.info("Key_creating {}", requestWebDTO.getKeyValue());

        var keyValidator = requestWebDTO.getKeyType().getValidator();
        keyValidator.validate(requestWebDTO.getKeyValue());

        var savedKey = createKeyUseCase.execute(requestWebDTO.toDomain());

        log.info("Key_saved", savedKey);

        return KeyResponseWebDTO.from(savedKey);
    }

    @Operation(description = "Alteração de Chaves PIX")
    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public KeyResponseWebDTO update(@RequestBody @Valid @NotNull final UpdateKeyRequestWebDTO requestWebDTO,
                                    @PathVariable UUID id) {

        log.info("Key_updating - Id: {}", id, requestWebDTO);

        var updatedKey = updateKeyUseCase.execute(requestWebDTO.toDomain(id));

        log.info("Key_updated", updatedKey);

        return KeyResponseWebDTO.from(updatedKey);
    }

    @Operation(description = "Deleção de Chaves PIX")
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public KeyResponseWebDTO delete(@PathVariable UUID id) {

        log.info("Key_deleting - Id: {}", id);

        var deletedKey = deleteKeyUseCase.execute(id);

        log.info("Key_deleted", deletedKey);

        return KeyResponseWebDTO.from(deletedKey);
    }

    @Operation(description = "Consulta de Chaves PIX")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ListKeysResponseWebDTO list(@Valid ListKeysRequestWebDTO requestWebDTO,
                                       @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                       @RequestParam(value = "size", required = false, defaultValue = "10") int size) {

        log.info("Key_listing");

        var keyPage = listKeyUseCase.execute(requestWebDTO.toDomain(), page, size);

        log.info("Key_listed", keyPage);

        return ListKeysResponseWebDTO.from(keyPage);
    }
}
