package com.project.pixchallenge.core.usecases;

import com.project.pixchallenge.core.domain.Key;
import com.project.pixchallenge.core.domain.KeyPage;
import com.project.pixchallenge.core.domain.KeyType;
import com.project.pixchallenge.core.exception.KeyValidatorException;
import com.project.pixchallenge.core.ports.ListKeyPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ListKeyUseCaseTest {

    private static final int PAGE = 0;
    private static final int SIZE = 0;

    @InjectMocks
    private ListKeyUseCase useCase;

    @Mock
    private ListKeyPort listKeyPort;

    private Key key;

    @Test
    void when_executeKeyWithIdOnly_expect_noExceptions() {
        key = Key.builder().id(UUID.randomUUID()).build();

        var keyPage = KeyPage.builder().keys(List.of(key)).build();

        when(listKeyPort.list(any(), anyInt(), anyInt())).thenReturn(keyPage);

        assertDoesNotThrow(() -> useCase.execute(key, PAGE, SIZE));

        verify(listKeyPort, times(1)).list(eq(key), eq(PAGE), eq(SIZE));
    }

    @Test
    void when_executeKeyWithMultipleFields_expect_noExceptions() {
        key = Key.builder()
                .type(KeyType.CPF)
                .accountNumber(12345678)
                .branchNumber(1234)
                .name("Test")
                .createdAt(LocalDateTime.now())
                .build();

        var keyPage = KeyPage.builder().keys(List.of(key)).build();

        when(listKeyPort.list(any(), anyInt(), anyInt())).thenReturn(keyPage);

        assertDoesNotThrow(() -> useCase.execute(key, PAGE, SIZE));

        verify(listKeyPort, times(1)).list(eq(key), eq(PAGE), eq(SIZE));
    }

    @Test
    void when_executeWithNonExistentKey_expect_entityNotFoundException() {
        key = Key.builder().build();

        var keyPage = KeyPage.builder().keys(Collections.emptyList()).build();

        when(listKeyPort.list(any(), anyInt(), anyInt())).thenReturn(keyPage);

        var exception = assertThrows(EntityNotFoundException.class, () ->
                useCase.execute(key, PAGE, SIZE));

        assertEquals("No Key Found", exception.getMessage());

        verify(listKeyPort, times(1)).list(eq(key), eq(PAGE), eq(SIZE));
    }

    @Test
    void when_executeWithIdAndAnotherField_expect_keyValidatorException() {
        key = Key.builder()
                .id(UUID.randomUUID())
                .name("Test")
                .build();

        var exception = assertThrows(KeyValidatorException.class, () ->
                useCase.execute(key, PAGE, SIZE));

        assertEquals("The filter by id must be unique", exception.getMessage());

        verify(listKeyPort, times(0)).list(eq(key), eq(PAGE), eq(SIZE));
    }

    @Test
    void when_executeWithCreatedAtAndInactivationDate_expect_keyValidatorException() {
        key = Key.builder()
                .createdAt(LocalDateTime.now())
                .inactivationDate(LocalDateTime.now())
                .build();

        var exception = assertThrows(KeyValidatorException.class, () ->
                useCase.execute(key, PAGE, SIZE));

        assertEquals("'createdAt' and 'inactivationDate' filters should not be used together",
                exception.getMessage());

        verify(listKeyPort, times(0)).list(eq(key), eq(PAGE), eq(SIZE));
    }


}
