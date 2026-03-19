/**
 * @author Raquel Vega
 * Fecha: 18/03/2026
 */
package com.bitcoinproject.operations;

import java.util.ArrayDeque;
import java.util.Deque;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.scriptbitcoin.interpreters.ExceptionsInterpreter;
import com.scriptbitcoin.operations.Firmas;
import com.scriptbitcoin.utils.Utiles;

import static org.junit.Assert.*;

/**
 * Tests unitarios para la clase Firmas (JUnit 4).
 */
public class FirmasTest {

    private Deque<byte[]> stack;

    @Before
    public void setup() {
        stack = new ArrayDeque<>();
        Firmas.clearSignatures();
    }

    @After
    public void tearDown() {
        Firmas.clearSignatures();
    }

    // ===================== OP_CHECKSIG =====================

    @Test
    public void testOpCheckSig_FirmaValida() {
        byte[] sig = "mi_firma".getBytes();
        byte[] pubKey = "mi_clave_publica".getBytes();
        Firmas.registerValidSignature(sig, pubKey);

        stack.push(sig);
        stack.push(pubKey);
        Firmas.opCheckSig(stack);

        assertEquals(1, stack.size());
        assertEquals(1, Utiles.bytesToInt(stack.peek()));
    }

    @Test
    public void testOpCheckSig_FirmaInvalida() {
        byte[] sig = "firma_falsa".getBytes();
        byte[] pubKey = "clave_falsa".getBytes();

        stack.push(sig);
        stack.push(pubKey);
        Firmas.opCheckSig(stack);

        assertEquals(1, stack.size());
        assertFalse(Utiles.isTruthy(stack.peek()));
    }

    @Test
    public void testOpCheckSig_FirmaCorrectaClaveIncorrecta() {
        byte[] sig = "firma_real".getBytes();
        byte[] pubKeyCorrecta = "clave_correcta".getBytes();
        byte[] pubKeyIncorrecta = "clave_incorrecta".getBytes();
        Firmas.registerValidSignature(sig, pubKeyCorrecta);

        stack.push(sig);
        stack.push(pubKeyIncorrecta);
        Firmas.opCheckSig(stack);

        assertFalse(Utiles.isTruthy(stack.peek()));
    }

    @Test
    public void testOpCheckSig_ConsumeYEmpuja() {
        byte[] sig = "s".getBytes();
        byte[] pubKey = "p".getBytes();

        stack.push(Utiles.intToBytes(99));
        stack.push(sig);
        stack.push(pubKey);

        assertEquals(3, stack.size());

        Firmas.opCheckSig(stack);

        assertEquals(2, stack.size());
    }

    @Test
    public void testOpCheckSig_PilaVacia() {
        try {
            Firmas.opCheckSig(stack);
            fail("Se esperaba excepción");
        } catch (ExceptionsInterpreter e) {
            // OK
        }
    }

    @Test
    public void testOpCheckSig_UnElemento() {
        stack.push("solo_uno".getBytes());

        try {
            Firmas.opCheckSig(stack);
            fail("Se esperaba excepción");
        } catch (ExceptionsInterpreter e) {
            // OK
        }
    }

    // ===================== REGISTRO Y LIMPIEZA =====================

    @Test
    public void testRegistroMultiplesFirmas() {
        byte[] sig1 = "firma1".getBytes();
        byte[] pub1 = "pub1".getBytes();
        byte[] sig2 = "firma2".getBytes();
        byte[] pub2 = "pub2".getBytes();

        Firmas.registerValidSignature(sig1, pub1);
        Firmas.registerValidSignature(sig2, pub2);

        stack.push(sig1);
        stack.push(pub1);
        Firmas.opCheckSig(stack);
        assertTrue(Utiles.isTruthy(stack.pop()));

        stack.push(sig2);
        stack.push(pub2);
        Firmas.opCheckSig(stack);
        assertTrue(Utiles.isTruthy(stack.pop()));
    }

    @Test
    public void testClearSignatures() {
        byte[] sig = "firma".getBytes();
        byte[] pubKey = "clave".getBytes();
        Firmas.registerValidSignature(sig, pubKey);

        stack.push(sig);
        stack.push(pubKey);
        Firmas.opCheckSig(stack);
        assertTrue(Utiles.isTruthy(stack.pop()));

        Firmas.clearSignatures();

        stack.push(sig);
        stack.push(pubKey);
        Firmas.opCheckSig(stack);
        assertFalse(Utiles.isTruthy(stack.pop()));
    }

    @Test
    public void testOpCheckSig_BytesArbitrarios() {
        byte[] sig = {0x00, (byte) 0xFF, 0x7F, (byte) 0x80};
        byte[] pubKey = {(byte) 0xAA, (byte) 0xBB, (byte) 0xCC};
        Firmas.registerValidSignature(sig, pubKey);

        stack.push(sig);
        stack.push(pubKey);
        Firmas.opCheckSig(stack);

        assertTrue(Utiles.isTruthy(stack.peek()));
    }
}