package com.project.pixchallenge.core.usecases;

import com.project.pixchallenge.builds.KeyBuilder;
import com.project.pixchallenge.core.domain.Key;
import com.project.pixchallenge.core.exception.KeyValidatorException;
import com.project.pixchallenge.core.ports.FindKeyPort;
import com.project.pixchallenge.core.ports.SaveKeyPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateKeyUseCaseTest {

    @InjectMocks
    private CreateKeyUseCase useCase;

    @Mock
    private FindKeyPort findKeyPort;

    @Mock
    private SaveKeyPort saveKeyPort;

    private Key key;

    @BeforeEach
    void setup() {
        key = KeyBuilder.cpfCreating();
    }

    @Test
    void when_executeWithSuccess_expect_noExceptions() {
        when(findKeyPort.findByValue(anyString())).thenReturn(Optional.empty());
        when(findKeyPort.findByAccount(anyInt(), anyInt())).thenReturn(Collections.emptyList());

        assertDoesNotThrow(() -> useCase.execute(key));

        verify(findKeyPort, times(1)).findByValue(eq(key.getValue()));
        verify(findKeyPort, times(1)).findByAccount(
                eq(key.getAccountNumber()), eq(key.getBranchNumber()));

        ArgumentCaptor<Key> keySaving = ArgumentCaptor.forClass(Key.class);
        verify(saveKeyPort, times(1)).save(keySaving.capture());

        assertTrue(keySaving.getValue().isActive());

    }

    @Test
    void when_executeWithExistingKey_expect_keyValidatorException() {
        when(findKeyPort.findByValue(anyString())).thenReturn(Optional.of(key));

        var exception = assertThrows(KeyValidatorException.class, () -> useCase.execute(key));

        assertEquals(String.format("The %s key already exists in the database", key.getValue()), exception.getMessage());

        verify(findKeyPort, times(1)).findByValue(eq(key.getValue()));
        verify(findKeyPort, times(0)).findByAccount(
                eq(key.getAccountNumber()), eq(key.getBranchNumber()));
        verify(saveKeyPort, times(0)).save(any());

    }

    @Test
    void when_executeWithExceededFiveKeys_expect_keyValidatorException() {
        var mockKeys = new ArrayList<Key>();

        for (int i = 0; i <= 5; i++) {
            mockKeys.add(key);
        }

        when(findKeyPort.findByValue(anyString())).thenReturn(Optional.empty());
        when(findKeyPort.findByAccount(anyInt(), anyInt())).thenReturn(mockKeys);

        var exception = assertThrows(KeyValidatorException.class, () -> useCase.execute(key));

        assertEquals("The number of keys for this account has been exceeded", exception.getMessage());

        verify(findKeyPort, times(1)).findByValue(eq(key.getValue()));
        verify(findKeyPort, times(1)).findByAccount(
                eq(key.getAccountNumber()), eq(key.getBranchNumber()));
        verify(saveKeyPort, times(0)).save(any());
    }

    @Test
    void when_executeWithExceededTwentyKeys_expect_keyValidatorException() {
        key = KeyBuilder.cnpjCreating();

        var mockKeys = new ArrayList<Key>();

        for (int i = 0; i <= 20; i++) {
            mockKeys.add(key);
        }

        when(findKeyPort.findByValue(anyString())).thenReturn(Optional.empty());
        when(findKeyPort.findByAccount(anyInt(), anyInt())).thenReturn(mockKeys);

        var exception = assertThrows(KeyValidatorException.class, () -> useCase.execute(key));

        assertEquals("The number of keys for this account has been exceeded", exception.getMessage());

        verify(findKeyPort, times(1)).findByValue(eq(key.getValue()));
        verify(findKeyPort, times(1)).findByAccount(
                eq(key.getAccountNumber()), eq(key.getBranchNumber()));
        verify(saveKeyPort, times(0)).save(any());
    }
}
