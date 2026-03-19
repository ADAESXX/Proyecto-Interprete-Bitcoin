/**
 * @author Raquel Vega
 * Fecha: 18/03/2026
 */
package com.bitcoinproject.operations;

import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.Deque;

import org.junit.Before;
import org.junit.Test;

import com.scriptbitcoin.interpreters.ExceptionsInterpreter;
import com.scriptbitcoin.operations.Crypto;

import static org.junit.Assert.*;

/**
 * Tests unitarios para la clase Crypto (JUnit 4).
 */
public class CryptoTest {

    private Deque<byte[]> stack;

    @Before
    public void setup() {
        stack = new ArrayDeque<>();
    }

    // ===================== OP_SHA256 =====================

    @Test
    public void testOpSha256_PrefijoAplicado() {
        byte[] data = "hello".getBytes(StandardCharsets.UTF_8);
        stack.push(data);
        Crypto.opSha256(stack);

        assertEquals(1, stack.size());
        String resultado = new String(stack.peek(), StandardCharsets.UTF_8);
        assertTrue(resultado.startsWith("SHA256:"));
        assertEquals("SHA256:hello", resultado);
    }

    @Test
    public void testOpSha256_DatosVacios() {
        stack.push(new byte[0]);
        Crypto.opSha256(stack);

        assertEquals(1, stack.size());
        String resultado = new String(stack.peek(), StandardCharsets.UTF_8);
        assertEquals("SHA256:", resultado);
    }

    @Test
    public void testOpSha256_PilaVacia() {
        try {
            Crypto.opSha256(stack);
            fail("Se esperaba excepción");
        } catch (ExceptionsInterpreter ex) {
            assertTrue(ex.getMessage().contains("OP_SHA256"));
        }
    }

    @Test
    public void testOpSha256_NoAfectaElementosAnteriores() {
        byte[] base = "base".getBytes(StandardCharsets.UTF_8);
        byte[] data = "test".getBytes(StandardCharsets.UTF_8);

        stack.push(base);
        stack.push(data);

        Crypto.opSha256(stack);

        assertEquals(2, stack.size());
        stack.pop();
        assertArrayEquals(base, stack.peek());
    }

    // ===================== OP_HASH160 =====================

    @Test
    public void testOpHash160_PrefijoAplicado() {
        byte[] data = "pubkey".getBytes(StandardCharsets.UTF_8);
        stack.push(data);

        Crypto.opHash160(stack);

        assertEquals(1, stack.size());
        String resultado = new String(stack.peek(), StandardCharsets.UTF_8);
        assertEquals("HASH160:pubkey", resultado);
    }

    @Test
    public void testOpHash160_DatosVacios() {
        stack.push(new byte[0]);

        Crypto.opHash160(stack);

        String resultado = new String(stack.peek(), StandardCharsets.UTF_8);
        assertEquals("HASH160:", resultado);
    }

    @Test
    public void testOpHash160_PilaVacia() {
        try {
            Crypto.opHash160(stack);
            fail("Se esperaba excepción");
        } catch (ExceptionsInterpreter ex) {
            assertTrue(ex.getMessage().contains("OP_HASH160"));
        }
    }

    @Test
    public void testOpHash160_ConsumeYEmpuja() {
        stack.push("data".getBytes(StandardCharsets.UTF_8));

        assertEquals(1, stack.size());
        Crypto.opHash160(stack);
        assertEquals(1, stack.size());
    }

    // ===================== OP_HASH256 =====================

    @Test
    public void testOpHash256_PrefijoAplicado() {
        byte[] data = "transaction".getBytes(StandardCharsets.UTF_8);
        stack.push(data);

        Crypto.opHash256(stack);

        assertEquals(1, stack.size());
        String resultado = new String(stack.peek(), StandardCharsets.UTF_8);
        assertEquals("HASH256:transaction", resultado);
    }

    @Test
    public void testOpHash256_DatosVacios() {
        stack.push(new byte[0]);

        Crypto.opHash256(stack);

        String resultado = new String(stack.peek(), StandardCharsets.UTF_8);
        assertEquals("HASH256:", resultado);
    }

    @Test
    public void testOpHash256_PilaVacia() {
        try {
            Crypto.opHash256(stack);
            fail("Se esperaba excepción");
        } catch (ExceptionsInterpreter ex) {
            assertTrue(ex.getMessage().contains("OP_HASH256"));
        }
    }

    // ===================== MOCK DIRECTOS =====================

    @Test
    public void testMockSha256_Directo() {
        byte[] data = {0x01, 0x02, 0x03};
        byte[] result = Crypto.mockSha256(data);

        byte[] prefix = "SHA256:".getBytes(StandardCharsets.UTF_8);

        assertEquals(prefix.length + data.length, result.length);

        for (int i = 0; i < prefix.length; i++) {
            assertEquals(prefix[i], result[i]);
        }

        for (int i = 0; i < data.length; i++) {
            assertEquals(data[i], result[prefix.length + i]);
        }
    }

    @Test
    public void testMockHash160_Directo() {
        byte[] data = "test".getBytes(StandardCharsets.UTF_8);
        byte[] result = Crypto.mockHash160(data);

        String resultStr = new String(result, StandardCharsets.UTF_8);
        assertEquals("HASH160:test", resultStr);
    }

    @Test
    public void testMockHash256_Directo() {
        byte[] data = "test".getBytes(StandardCharsets.UTF_8);
        byte[] result = Crypto.mockHash256(data);

        String resultStr = new String(result, StandardCharsets.UTF_8);
        assertEquals("HASH256:test", resultStr);
    }

    @Test
    public void testHashesDiferentes() {
        byte[] data = "same_data".getBytes(StandardCharsets.UTF_8);

        byte[] sha256 = Crypto.mockSha256(data);
        byte[] hash160 = Crypto.mockHash160(data);
        byte[] hash256 = Crypto.mockHash256(data);

        assertFalse(java.util.Arrays.equals(sha256, hash160));
        assertFalse(java.util.Arrays.equals(sha256, hash256));
        assertFalse(java.util.Arrays.equals(hash160, hash256));
    }
}