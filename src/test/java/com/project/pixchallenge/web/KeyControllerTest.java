package com.project.pixchallenge.web;

import com.project.pixchallenge.builds.KeyBuilder;
import com.project.pixchallenge.builds.KeyWebDTOBuilder;
import com.project.pixchallenge.core.domain.KeyPage;
import com.project.pixchallenge.core.domain.KeyType;
import com.project.pixchallenge.core.usecases.CreateKeyUseCase;
import com.project.pixchallenge.core.usecases.ListKeyUseCase;
import com.project.pixchallenge.core.usecases.UpdateKeyUseCase;
import com.project.pixchallenge.web.dto.request.CreateKeyRequestWebDTO;
import com.project.pixchallenge.web.dto.request.ListKeysRequestWebDTO;
import com.project.pixchallenge.web.dto.request.UpdateKeyRequestWebDTO;
import com.project.pixchallenge.web.exception.CustomExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.UUID;

import static com.project.pixchallenge.helper.ObjectMapperHelper.OBJECT_MAPPER;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class KeyControllerTest {

    private static final String BASE_URL = "/keys";

    private MockMvc mockMvc;

    @InjectMocks
    private KeyController keyController;

    @Mock
    private CreateKeyUseCase createKeyUseCase;

    @Mock
    private UpdateKeyUseCase updateKeyUseCase;

    @Mock
    private ListKeyUseCase listKeyUseCase;

    private CreateKeyRequestWebDTO createKeyRequestWebDTO;

    private ListKeysRequestWebDTO listKeysRequestWebDTO;

    private UpdateKeyRequestWebDTO updateKeyRequestWebDTO;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(keyController)
                .setControllerAdvice(new CustomExceptionHandler())
                .build();

        createKeyRequestWebDTO = KeyWebDTOBuilder.cpfCreate();

        updateKeyRequestWebDTO = KeyWebDTOBuilder.checkingUpdate();
    }

    @Test
    void when_createKey_expect_statusOk() throws Exception {
        var createdKey = KeyBuilder.cpfCreated();

        when(createKeyUseCase.execute(any())).thenReturn(createdKey);

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.asJsonString(createKeyRequestWebDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(createdKey.getId().toString())))
                .andExpect(jsonPath("$.keyType", equalTo(createdKey.getType().name())))
                .andExpect(jsonPath("$.keyValue", equalTo(createdKey.getValue())))
                .andExpect(jsonPath("$.accountNumber", equalTo(createdKey.getAccountNumber())))
                .andExpect(jsonPath("$.accountType", equalTo(createdKey.getAccountType().name())))
                .andExpect(jsonPath("$.branchNumber", equalTo(createdKey.getBranchNumber())))
                .andExpect(jsonPath("$.name", equalTo(createdKey.getName())))
                .andExpect(jsonPath("$.lastName", equalTo(createdKey.getLastName())))
                .andExpect(jsonPath("$.createdAt", equalTo(ISO_LOCAL_DATE_TIME.format(createdKey.getCreatedAt()))));
    }

    @Test
    void when_createKeyWithInvalidCpf_expect_statusUnprocessableEntity() throws Exception {
        createKeyRequestWebDTO.setKeyValue("132");

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.asJsonString(createKeyRequestWebDTO)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.code", equalTo(422)))
                .andExpect(jsonPath("$.error", equalTo("Unprocessable Entity")))
                .andExpect(jsonPath("$.message", equalTo("Invalid CPF: 132")));
    }

    @Test
    void when_createKeyWithInvalidPhone_expect_statusUnprocessableEntity() throws Exception {
        createKeyRequestWebDTO.setKeyType(KeyType.PHONE);
        createKeyRequestWebDTO.setKeyValue("132");

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.asJsonString(createKeyRequestWebDTO)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.code", equalTo(422)))
                .andExpect(jsonPath("$.error", equalTo("Unprocessable Entity")))
                .andExpect(jsonPath("$.message", equalTo("Invalid Phone: 132")));
    }

    @Test
    void when_createKeyWithInvalidEmail_expect_statusUnprocessableEntity() throws Exception {
        createKeyRequestWebDTO.setKeyType(KeyType.EMAIL);
        createKeyRequestWebDTO.setKeyValue("132");

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.asJsonString(createKeyRequestWebDTO)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.code", equalTo(422)))
                .andExpect(jsonPath("$.error", equalTo("Unprocessable Entity")))
                .andExpect(jsonPath("$.message", equalTo("Invalid E-mail: 132")));
    }

    @Test
    void when_createKeyWithInvalidCnpj_expect_statusUnprocessableEntity() throws Exception {
        createKeyRequestWebDTO.setKeyType(KeyType.CNPJ);
        createKeyRequestWebDTO.setKeyValue("132");

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.asJsonString(createKeyRequestWebDTO)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.code", equalTo(422)))
                .andExpect(jsonPath("$.error", equalTo("Unprocessable Entity")))
                .andExpect(jsonPath("$.message", equalTo("Invalid CNPJ: 132")));
    }

    @Test
    void when_createKeyWithInvalidRandomKey_expect_statusUnprocessableEntity() throws Exception {
        createKeyRequestWebDTO.setKeyType(KeyType.RANDOM);
        createKeyRequestWebDTO.setKeyValue("132");

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.asJsonString(createKeyRequestWebDTO)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.code", equalTo(422)))
                .andExpect(jsonPath("$.error", equalTo("Unprocessable Entity")))
                .andExpect(jsonPath("$.message", equalTo("Invalid Random Key: 132")));
    }

    @Test
    void when_createKeyWithInvalidAccountNumber_expect_statusUnprocessableEntity() throws Exception {
        createKeyRequestWebDTO.setAccountNumber(123456789);

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.asJsonString(createKeyRequestWebDTO)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.code", equalTo(422)))
                .andExpect(jsonPath("$.error", equalTo("Unprocessable Entity")))
                .andExpect(jsonPath("$.message", equalTo("Invalid Arguments")))
                .andExpect(jsonPath("$.fieldErrors[0].field", equalTo("accountNumber")))
                .andExpect(jsonPath("$.fieldErrors[0].message", equalTo("numeric value out of bounds (<8 digits>.<0 digits> expected)")));
    }

    @Test
    void when_createKeyWithInvalidBranchNumber_expect_statusUnprocessableEntity() throws Exception {
        createKeyRequestWebDTO.setBranchNumber(12345);

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.asJsonString(createKeyRequestWebDTO)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.code", equalTo(422)))
                .andExpect(jsonPath("$.error", equalTo("Unprocessable Entity")))
                .andExpect(jsonPath("$.message", equalTo("Invalid Arguments")))
                .andExpect(jsonPath("$.fieldErrors[0].field", equalTo("branchNumber")))
                .andExpect(jsonPath("$.fieldErrors[0].message", equalTo("numeric value out of bounds (<4 digits>.<0 digits> expected)")));
    }

    @Test
    void when_createKeyWithNullAccountType_expect_statusUnprocessableEntity() throws Exception {
        createKeyRequestWebDTO.setAccountType(null);

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.asJsonString(createKeyRequestWebDTO)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.code", equalTo(422)))
                .andExpect(jsonPath("$.error", equalTo("Unprocessable Entity")))
                .andExpect(jsonPath("$.message", equalTo("Invalid Arguments")))
                .andExpect(jsonPath("$.fieldErrors[0].field", equalTo("accountType")))
                .andExpect(jsonPath("$.fieldErrors[0].message", equalTo("must not be null")));
    }

    @Test
    void when_createKeyWithInvalidName_expect_statusUnprocessableEntity() throws Exception {
        createKeyRequestWebDTO.setName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.asJsonString(createKeyRequestWebDTO)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.code", equalTo(422)))
                .andExpect(jsonPath("$.error", equalTo("Unprocessable Entity")))
                .andExpect(jsonPath("$.message", equalTo("Invalid Arguments")))
                .andExpect(jsonPath("$.fieldErrors[0].field", equalTo("name")))
                .andExpect(jsonPath("$.fieldErrors[0].message", equalTo("size must be between 1 and 30")));
    }

    @Test
    void when_createKeyWithInvalidLastName_expect_statusUnprocessableEntity() throws Exception {
        createKeyRequestWebDTO.setLastName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.asJsonString(createKeyRequestWebDTO)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.code", equalTo(422)))
                .andExpect(jsonPath("$.error", equalTo("Unprocessable Entity")))
                .andExpect(jsonPath("$.message", equalTo("Invalid Arguments")))
                .andExpect(jsonPath("$.fieldErrors[0].field", equalTo("lastName")))
                .andExpect(jsonPath("$.fieldErrors[0].message", equalTo("size must be between 1 and 45")));
    }

    @Test
    void when_updateKey_expect_statusOk() throws Exception {
        var updatedKey = KeyBuilder.cpfCreated();

        when(updateKeyUseCase.execute(any())).thenReturn(updatedKey);

        mockMvc.perform(patch(BASE_URL + "/{id}", updatedKey.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.asJsonString(updateKeyRequestWebDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(updatedKey.getId().toString())))
                .andExpect(jsonPath("$.keyType", equalTo(updatedKey.getType().name())))
                .andExpect(jsonPath("$.keyValue", equalTo(updatedKey.getValue())))
                .andExpect(jsonPath("$.accountNumber", equalTo(updatedKey.getAccountNumber())))
                .andExpect(jsonPath("$.accountType", equalTo(updatedKey.getAccountType().name())))
                .andExpect(jsonPath("$.branchNumber", equalTo(updatedKey.getBranchNumber())))
                .andExpect(jsonPath("$.name", equalTo(updatedKey.getName())))
                .andExpect(jsonPath("$.lastName", equalTo(updatedKey.getLastName())))
                .andExpect(jsonPath("$.createdAt", equalTo(ISO_LOCAL_DATE_TIME.format(updatedKey.getCreatedAt()))));
    }

    @Test
    void when_updateKeyWithInvalidAccountNumber_expect_statusUnprocessableEntity() throws Exception {
        updateKeyRequestWebDTO.setAccountNumber(123456789);

        mockMvc.perform(patch(BASE_URL + "/{id}", UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.asJsonString(updateKeyRequestWebDTO)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.code", equalTo(422)))
                .andExpect(jsonPath("$.error", equalTo("Unprocessable Entity")))
                .andExpect(jsonPath("$.message", equalTo("Invalid Arguments")))
                .andExpect(jsonPath("$.fieldErrors[0].field", equalTo("accountNumber")))
                .andExpect(jsonPath("$.fieldErrors[0].message", equalTo("numeric value out of bounds (<8 digits>.<0 digits> expected)")));
    }

    @Test
    void when_updateKeyWithNullAccountType_expect_statusUnprocessableEntity() throws Exception {
        updateKeyRequestWebDTO.setAccountType(null);

        mockMvc.perform(patch(BASE_URL + "/{id}", UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.asJsonString(updateKeyRequestWebDTO)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.code", equalTo(422)))
                .andExpect(jsonPath("$.error", equalTo("Unprocessable Entity")))
                .andExpect(jsonPath("$.message", equalTo("Invalid Arguments")))
                .andExpect(jsonPath("$.fieldErrors[0].field", equalTo("accountType")))
                .andExpect(jsonPath("$.fieldErrors[0].message", equalTo("must not be null")));
    }

    @Test
    void when_updateKeyWithInvalidBranchNumber_expect_statusUnprocessableEntity() throws Exception {
        updateKeyRequestWebDTO.setBranchNumber(12345);

        mockMvc.perform(patch(BASE_URL + "/{id}", UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.asJsonString(updateKeyRequestWebDTO)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.code", equalTo(422)))
                .andExpect(jsonPath("$.error", equalTo("Unprocessable Entity")))
                .andExpect(jsonPath("$.message", equalTo("Invalid Arguments")))
                .andExpect(jsonPath("$.fieldErrors[0].field", equalTo("branchNumber")))
                .andExpect(jsonPath("$.fieldErrors[0].message", equalTo("numeric value out of bounds (<4 digits>.<0 digits> expected)")));
    }

    @Test
    void when_updateKeyWithInvalidName_expect_statusUnprocessableEntity() throws Exception {
        updateKeyRequestWebDTO.setName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

        mockMvc.perform(patch(BASE_URL + "/{id}", UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.asJsonString(updateKeyRequestWebDTO)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.code", equalTo(422)))
                .andExpect(jsonPath("$.error", equalTo("Unprocessable Entity")))
                .andExpect(jsonPath("$.message", equalTo("Invalid Arguments")))
                .andExpect(jsonPath("$.fieldErrors[0].field", equalTo("name")))
                .andExpect(jsonPath("$.fieldErrors[0].message", equalTo("size must be between 1 and 30")));
    }

    @Test
    void when_updateKeyWithInvalidLastName_expect_statusUnprocessableEntity() throws Exception {
        updateKeyRequestWebDTO.setLastName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

        mockMvc.perform(patch(BASE_URL + "/{id}", UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.asJsonString(updateKeyRequestWebDTO)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.code", equalTo(422)))
                .andExpect(jsonPath("$.error", equalTo("Unprocessable Entity")))
                .andExpect(jsonPath("$.message", equalTo("Invalid Arguments")))
                .andExpect(jsonPath("$.fieldErrors[0].field", equalTo("lastName")))
                .andExpect(jsonPath("$.fieldErrors[0].message", equalTo("size must be between 1 and 45")));
    }

    @Test
    void when_listKey_expect_statusOk() throws Exception {
        var keyFound = KeyBuilder.cpfCreated();

        var keyPage = KeyPage.builder()
                .hasNext(false)
                .totalCount(1)
                .offset(0)
                .pageSize(10)
                .keys(Collections.singletonList(keyFound))
                .build();

        when(listKeyUseCase.execute(any(), anyInt(), anyInt())).thenReturn(keyPage);

        mockMvc.perform(get(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id", keyFound.getId().toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.hasNext", equalTo(false)))
                .andExpect(jsonPath("$.totalCount", equalTo(1)))
                .andExpect(jsonPath("$.offset", equalTo(0)))
                .andExpect(jsonPath("$.pageSize", equalTo(10)))
                .andExpect(jsonPath("$.keys.[0].id", equalTo(keyFound.getId().toString())))
                .andExpect(jsonPath("$.keys.[0].keyType", equalTo(keyFound.getType().name())))
                .andExpect(jsonPath("$.keys.[0].keyValue", equalTo(keyFound.getValue())))
                .andExpect(jsonPath("$.keys.[0].accountNumber", equalTo(keyFound.getAccountNumber())))
                .andExpect(jsonPath("$.keys.[0].accountType", equalTo(keyFound.getAccountType().name())))
                .andExpect(jsonPath("$.keys.[0].branchNumber", equalTo(keyFound.getBranchNumber())))
                .andExpect(jsonPath("$.keys.[0].name", equalTo(keyFound.getName())))
                .andExpect(jsonPath("$.keys.[0].lastName", equalTo(keyFound.getLastName())))
                .andExpect(jsonPath("$.keys.[0].createdAt", equalTo(ISO_LOCAL_DATE_TIME.format(keyFound.getCreatedAt()))));
    }

    @Test
    void when_listKeyWithInvalidAccountNumber_expect_statusUnprocessableEntity() throws Exception {
        mockMvc.perform(get(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("accountNumber", "123456789"))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.code", equalTo(422)))
                .andExpect(jsonPath("$.error", equalTo("Unprocessable Entity")))
                .andExpect(jsonPath("$.message", equalTo("Invalid Arguments")))
                .andExpect(jsonPath("$.fieldErrors[0].field", equalTo("accountNumber")))
                .andExpect(jsonPath("$.fieldErrors[0].message", equalTo("numeric value out of bounds (<8 digits>.<0 digits> expected)")));
    }

    @Test
    void when_listKeyWithInvalidBranchNumber_expect_statusUnprocessableEntity() throws Exception {
        mockMvc.perform(get(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("branchNumber", "12345"))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.code", equalTo(422)))
                .andExpect(jsonPath("$.error", equalTo("Unprocessable Entity")))
                .andExpect(jsonPath("$.message", equalTo("Invalid Arguments")))
                .andExpect(jsonPath("$.fieldErrors[0].field", equalTo("branchNumber")))
                .andExpect(jsonPath("$.fieldErrors[0].message", equalTo("numeric value out of bounds (<4 digits>.<0 digits> expected)")));
    }

    @Test
    void when_listKeyWithInvalidName_expect_statusUnprocessableEntity() throws Exception {
        mockMvc.perform(get(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("name", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.code", equalTo(422)))
                .andExpect(jsonPath("$.error", equalTo("Unprocessable Entity")))
                .andExpect(jsonPath("$.message", equalTo("Invalid Arguments")))
                .andExpect(jsonPath("$.fieldErrors[0].field", equalTo("name")))
                .andExpect(jsonPath("$.fieldErrors[0].message", equalTo("size must be between 1 and 30")));
    }

    @Test
    void when_listKeyWithInvalidCreatedDate_expect_statusUnprocessableEntity() throws Exception {
        mockMvc.perform(get(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("createdAt", "123"))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.code", equalTo(422)))
                .andExpect(jsonPath("$.error", equalTo("Unprocessable Entity")))
                .andExpect(jsonPath("$.message", equalTo("Invalid Arguments")))
                .andExpect(jsonPath("$.fieldErrors[0].field", equalTo("createdAt")))
                .andExpect(jsonPath("$.fieldErrors[0].message", containsString("IllegalArgumentException")));
    }

    @Test
    void when_listKeyWithInvalidInactivationDate_expect_statusUnprocessableEntity() throws Exception {
        mockMvc.perform(get(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("inactivationDate", "123"))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.code", equalTo(422)))
                .andExpect(jsonPath("$.error", equalTo("Unprocessable Entity")))
                .andExpect(jsonPath("$.message", equalTo("Invalid Arguments")))
                .andExpect(jsonPath("$.fieldErrors[0].field", equalTo("inactivationDate")))
                .andExpect(jsonPath("$.fieldErrors[0].message", containsString("IllegalArgumentException")));
    }
}
