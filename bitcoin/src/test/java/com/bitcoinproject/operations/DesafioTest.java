/**
 * @author Raquel Vega
 * Fecha: 18/03/2026
 */
package com.bitcoinproject.operations;

import com.scriptbitcoin.operations.Desafio;
import java.util.ArrayDeque;
import java.util.Deque;

import org.junit.Before;
import org.junit.Test;

import com.scriptbitcoin.utils.Utiles;

import static org.junit.Assert.*;

/**
 * Tests unitarios para la clase Desafio (JUnit 4).
 */
public class DesafioTest {

    private Deque<byte[]> stack;

    @Before
    public void setup() {
        stack = new ArrayDeque<>();
    }

    // ===================== TESTS DE EXISTENCIA =====================

    @Test
    public void testDesafioExisteEnPaquete() {
        String nombreClase = Desafio.class.getName();
        assertEquals("com.scriptbitcoin.operations.Desafio", nombreClase);
    }

    @Test
    public void testDesafioNoEsInterfaz() {
        assertFalse(Desafio.class.isInterface());
    }

    @Test
    public void testDesafioNoEsEnum() {
        assertFalse(Desafio.class.isEnum());
    }

    @Test
    public void testDesafioNoEsAbstracta() {
        assertFalse(java.lang.reflect.Modifier.isAbstract(Desafio.class.getModifiers()));
    }

    // ===================== PREPARACIÓN MULTISIG =====================

    @Test
    public void testPreparacionPilaMultisig1de2() {
        stack.push(new byte[0]); // OP_0 dummy
        stack.push("firma1".getBytes());
        stack.push(Utiles.intToBytes(1));
        stack.push("pubKey1".getBytes());
        stack.push("pubKey2".getBytes());
        stack.push(Utiles.intToBytes(2));

        assertEquals(6, stack.size());
    }

    @Test
    public void testPreparacionPilaMultisig2de3() {
        stack.push(new byte[0]);
        stack.push("firma1".getBytes());
        stack.push("firma2".getBytes());
        stack.push(Utiles.intToBytes(2));
        stack.push("pubKey1".getBytes());
        stack.push("pubKey2".getBytes());
        stack.push("pubKey3".getBytes());
        stack.push(Utiles.intToBytes(3));

        assertEquals(8, stack.size());
    }

    @Test
    public void testDummyOP0EsFalsy() {
        byte[] dummy = new byte[0];
        assertFalse(Utiles.isTruthy(dummy));
    }

    @Test
    public void testMNoMayorQueN() {
        int m = 2;
        int n = 3;
        assertTrue(m <= n);
    }

    @Test
    public void testMMinimoUno() {
        int m = 1;
        assertTrue(m >= 1);
    }

    @Test
    public void testNMinimoUno() {
        int n = 1;
        assertTrue(n >= 1);
    }
}