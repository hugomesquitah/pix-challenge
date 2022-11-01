package com.project.pixchallenge.web;

import com.project.pixchallenge.builds.KeyBuilder;
import com.project.pixchallenge.builds.KeyWebDTOBuilder;
import com.project.pixchallenge.infra.entities.KeyEntity;
import com.project.pixchallenge.infra.repositories.KeyRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.project.pixchallenge.helper.ObjectMapperHelper.OBJECT_MAPPER;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class KeyControllerIntegrationTest {

    private static final String BASE_URL = "/keys";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private KeyRepository keyRepository;

    @AfterEach
    void clean() {
        keyRepository.deleteAll();
    }

    @Test
    void when_createKey_expect_statusOk() throws Exception {
        var createKeyRequestWebDTO = KeyWebDTOBuilder.cpfCreate();

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.asJsonString(createKeyRequestWebDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.keyType", equalTo(createKeyRequestWebDTO.getKeyType().name())))
                .andExpect(jsonPath("$.keyValue", equalTo(createKeyRequestWebDTO.getKeyValue())))
                .andExpect(jsonPath("$.accountNumber", equalTo(createKeyRequestWebDTO.getAccountNumber())))
                .andExpect(jsonPath("$.accountType", equalTo(createKeyRequestWebDTO.getAccountType().name())))
                .andExpect(jsonPath("$.branchNumber", equalTo(createKeyRequestWebDTO.getBranchNumber())))
                .andExpect(jsonPath("$.name", equalTo(createKeyRequestWebDTO.getName())))
                .andExpect(jsonPath("$.lastName", equalTo(createKeyRequestWebDTO.getLastName())))
                .andExpect(jsonPath("$.createdAt", notNullValue()));
    }

    @Test
    void when_updateKey_expect_statusOk() throws Exception {
        var savedKey = keyRepository.save(KeyEntity.from(KeyBuilder.cpfCreated()));

        var updateKeyRequestWebDTO = KeyWebDTOBuilder.checkingUpdate();

        mockMvc.perform(patch(BASE_URL + "/{id}", savedKey.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.asJsonString(updateKeyRequestWebDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(savedKey.getId().toString())))
                .andExpect(jsonPath("$.keyType", equalTo(savedKey.getType().name())))
                .andExpect(jsonPath("$.keyValue", equalTo(savedKey.getValue())))
                .andExpect(jsonPath("$.accountNumber", equalTo(updateKeyRequestWebDTO.getAccountNumber())))
                .andExpect(jsonPath("$.accountType", equalTo(updateKeyRequestWebDTO.getAccountType().name())))
                .andExpect(jsonPath("$.branchNumber", equalTo(updateKeyRequestWebDTO.getBranchNumber())))
                .andExpect(jsonPath("$.name", equalTo(updateKeyRequestWebDTO.getName())))
                .andExpect(jsonPath("$.lastName", equalTo(updateKeyRequestWebDTO.getLastName())))
                .andExpect(jsonPath("$.createdAt", notNullValue()));
    }

    @Test
    void when_deleteKey_expect_statusOk() throws Exception {
        var savedKey = keyRepository.save(KeyEntity.from(KeyBuilder.cpfCreated()));

        mockMvc.perform(delete(BASE_URL + "/{id}", savedKey.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(savedKey.getId().toString())))
                .andExpect(jsonPath("$.keyType", equalTo(savedKey.getType().name())))
                .andExpect(jsonPath("$.keyValue", equalTo(savedKey.getValue())))
                .andExpect(jsonPath("$.accountNumber", equalTo(savedKey.getAccountNumber())))
                .andExpect(jsonPath("$.accountType", equalTo(savedKey.getAccountType().name())))
                .andExpect(jsonPath("$.branchNumber", equalTo(savedKey.getBranchNumber())))
                .andExpect(jsonPath("$.name", equalTo(savedKey.getName())))
                .andExpect(jsonPath("$.lastName", equalTo(savedKey.getLastName())))
                .andExpect(jsonPath("$.createdAt", notNullValue()))
                .andExpect(jsonPath("$.inactivationDate", notNullValue()));
    }

    @Test
    void when_tryDeleteNonExistentKey_expect_statusUnprocessableEntity() throws Exception {
        var savedKey = keyRepository.save(KeyEntity.from(KeyBuilder.cpfCreating()));

        mockMvc.perform(delete(BASE_URL + "/{id}", savedKey.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.code", equalTo(422)))
                .andExpect(jsonPath("$.error", equalTo("Unprocessable Entity")))
                .andExpect(jsonPath("$.message", equalTo(String
                        .format("The key with id %s is already inactive", savedKey.getId()))));
    }

    @Test
    void when_listKeyWithIdFilter_expect_statusOk() throws Exception {
        var savedKey = keyRepository.save(KeyEntity.from(KeyBuilder.cpfCreated()));

        mockMvc.perform(get(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id", savedKey.getId().toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.hasNext", equalTo(false)))
                .andExpect(jsonPath("$.totalCount", equalTo(1)))
                .andExpect(jsonPath("$.offset", equalTo(0)))
                .andExpect(jsonPath("$.pageSize", equalTo(10)))
                .andExpect(jsonPath("$.keys.[0].id", equalTo(savedKey.getId().toString())))
                .andExpect(jsonPath("$.keys.[0].keyType", equalTo(savedKey.getType().name())))
                .andExpect(jsonPath("$.keys.[0].keyValue", equalTo(savedKey.getValue())))
                .andExpect(jsonPath("$.keys.[0].accountNumber", equalTo(savedKey.getAccountNumber())))
                .andExpect(jsonPath("$.keys.[0].accountType", equalTo(savedKey.getAccountType().name())))
                .andExpect(jsonPath("$.keys.[0].branchNumber", equalTo(savedKey.getBranchNumber())))
                .andExpect(jsonPath("$.keys.[0].name", equalTo(savedKey.getName())))
                .andExpect(jsonPath("$.keys.[0].lastName", equalTo(savedKey.getLastName())))
                .andExpect(jsonPath("$.keys.[0].createdAt", notNullValue()));
    }

    @Test
    void when_listKeyWithMultipleFilters_expect_statusOk() throws Exception {
        var savedKey = keyRepository.save(KeyEntity.from(KeyBuilder.cpfCreated()));

        mockMvc.perform(get(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("keyType", savedKey.getType().name())
                        .param("accountNumber", savedKey.getAccountNumber().toString())
                        .param("branchNumber", savedKey.getBranchNumber().toString())
                        .param("name", savedKey.getName()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.hasNext", equalTo(false)))
                .andExpect(jsonPath("$.totalCount", equalTo(1)))
                .andExpect(jsonPath("$.offset", equalTo(0)))
                .andExpect(jsonPath("$.pageSize", equalTo(10)))
                .andExpect(jsonPath("$.keys.[0].id", equalTo(savedKey.getId().toString())))
                .andExpect(jsonPath("$.keys.[0].keyType", equalTo(savedKey.getType().name())))
                .andExpect(jsonPath("$.keys.[0].keyValue", equalTo(savedKey.getValue())))
                .andExpect(jsonPath("$.keys.[0].accountNumber", equalTo(savedKey.getAccountNumber())))
                .andExpect(jsonPath("$.keys.[0].accountType", equalTo(savedKey.getAccountType().name())))
                .andExpect(jsonPath("$.keys.[0].branchNumber", equalTo(savedKey.getBranchNumber())))
                .andExpect(jsonPath("$.keys.[0].name", equalTo(savedKey.getName())))
                .andExpect(jsonPath("$.keys.[0].lastName", equalTo(savedKey.getLastName())))
                .andExpect(jsonPath("$.keys.[0].createdAt", notNullValue()));
    }
}
