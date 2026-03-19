/**
 * @author Raquel Vega
 * Fecha: 18/03/2026
 */
package com.bitcoinproject.operations;

import java.util.ArrayDeque;
import java.util.Deque;

import org.junit.Before;
import org.junit.Test;

import com.scriptbitcoin.interpreters.ExceptionsInterpreter;
import com.scriptbitcoin.operations.Arithmetic;
import com.scriptbitcoin.utils.Utiles;

import static org.junit.Assert.*;

/**
 * Tests unitarios para la clase Arithmetic.
 * Verifica las operaciones aritméticas OP_ADD y OP_GREATERTHAN.
 */
public class ArithmeticTest {

    private Deque<byte[]> stack;

    @Before
    public void setup() {
        stack = new ArrayDeque<>();
    }

    // ===================== OP_ADD =====================

    @Test
    public void testOpAdd_SumaBasica() {
        stack.push(Utiles.intToBytes(3));
        stack.push(Utiles.intToBytes(4));
        Arithmetic.opAdd(stack);
        assertEquals(1, stack.size());
        assertEquals(7, Utiles.bytesToInt(stack.peek()));
    }

    @Test
    public void testOpAdd_SumaCeros() {
        stack.push(Utiles.intToBytes(0));
        stack.push(Utiles.intToBytes(0));
        Arithmetic.opAdd(stack);
        assertEquals(1, stack.size());
        assertEquals(0, Utiles.bytesToInt(stack.peek()));
    }

    @Test
    public void testOpAdd_SumaGrande() {
        stack.push(Utiles.intToBytes(100));
        stack.push(Utiles.intToBytes(200));
        Arithmetic.opAdd(stack);
        assertEquals(1, stack.size());
        assertEquals(300, Utiles.bytesToInt(stack.peek()));
    }

    @Test
    public void testOpAdd_SumaConCero() {
        stack.push(Utiles.intToBytes(1));
        stack.push(Utiles.intToBytes(0));
        Arithmetic.opAdd(stack);
        assertEquals(1, stack.size());
        assertEquals(1, Utiles.bytesToInt(stack.peek()));
    }

    @Test
    public void testOpAdd_NoAfectaElementosAnteriores() {
        stack.push(Utiles.intToBytes(99));
        stack.push(Utiles.intToBytes(5));
        stack.push(Utiles.intToBytes(10));
        Arithmetic.opAdd(stack);
        assertEquals(2, stack.size());
        assertEquals(15, Utiles.bytesToInt(stack.peek()));
        stack.pop();
        assertEquals(99, Utiles.bytesToInt(stack.peek()));
    }

    @Test
    public void testOpAdd_PilaVacia() {
        try {
            Arithmetic.opAdd(stack);
            fail("Debió lanzar excepción OP_ADD");
        } catch (ExceptionsInterpreter ex) {
            assertTrue(ex.getMessage().contains("OP_ADD"));
        }
    }

    @Test
    public void testOpAdd_UnSoloElemento() {
        stack.push(Utiles.intToBytes(5));
        try {
            Arithmetic.opAdd(stack);
            fail("Debió lanzar excepción OP_ADD");
        } catch (ExceptionsInterpreter ex) {
            assertTrue(ex.getMessage().contains("OP_ADD"));
        }
    }

    // ===================== OP_GREATERTHAN =====================

    @Test
    public void testOpGreaterThan_Verdadero() {
        stack.push(Utiles.intToBytes(5));
        stack.push(Utiles.intToBytes(3));
        Arithmetic.opGreaterThan(stack);
        assertEquals(1, stack.size());
        assertEquals(1, Utiles.bytesToInt(stack.peek()));
    }

    @Test
    public void testOpGreaterThan_Falso() {
        stack.push(Utiles.intToBytes(3));
        stack.push(Utiles.intToBytes(5));
        Arithmetic.opGreaterThan(stack);
        assertEquals(1, stack.size());
        assertEquals(0, Utiles.bytesToInt(stack.peek()));
    }

    @Test
    public void testOpGreaterThan_Iguales() {
        stack.push(Utiles.intToBytes(5));
        stack.push(Utiles.intToBytes(5));
        Arithmetic.opGreaterThan(stack);
        assertEquals(1, stack.size());
        assertEquals(0, Utiles.bytesToInt(stack.peek()));
    }

    @Test
    public void testOpGreaterThan_ContraCero() {
        stack.push(Utiles.intToBytes(10));
        stack.push(Utiles.intToBytes(0));
        Arithmetic.opGreaterThan(stack);
        assertEquals(1, stack.size());
        assertEquals(1, Utiles.bytesToInt(stack.peek()));
    }

    @Test
    public void testOpGreaterThan_CeroContraCero() {
        stack.push(Utiles.intToBytes(0));
        stack.push(Utiles.intToBytes(0));
        Arithmetic.opGreaterThan(stack);
        assertEquals(1, stack.size());
        assertEquals(0, Utiles.bytesToInt(stack.peek()));
    }

    @Test
    public void testOpGreaterThan_PilaVacia() {
        try {
            Arithmetic.opGreaterThan(stack);
            fail("Debió lanzar excepción OP_GREATERTHAN");
        } catch (ExceptionsInterpreter ex) {
            assertTrue(ex.getMessage().contains("OP_GREATERTHAN"));
        }
    }

    @Test
    public void testOpGreaterThan_UnSoloElemento() {
        stack.push(Utiles.intToBytes(5));
        try {
            Arithmetic.opGreaterThan(stack);
            fail("Debió lanzar excepción OP_GREATERTHAN");
        } catch (ExceptionsInterpreter ex) {
            assertTrue(ex.getMessage().contains("OP_GREATERTHAN"));
        }
    }
}