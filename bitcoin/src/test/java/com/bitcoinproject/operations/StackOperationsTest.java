/**
 * @author Paola Itzep 
 * Fecha: 18/03/2026
 */
package com.bitcoinproject.operations;

import java.util.ArrayDeque;
import java.util.Deque;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

import com.scriptbitcoin.interpreters.ExceptionsInterpreter;
import com.scriptbitcoin.operations.StackOperations;
import com.scriptbitcoin.utils.Utiles;

public class StackOperationsTest {

    private Deque<byte[]> stack;

    @Before
    public void setup() {
        stack = new ArrayDeque<>();
    }

    @Test
    public void testOpDup() {
        stack.push(Utiles.intToBytes(5));
        StackOperations.opDup(stack);
        assertEquals(2, stack.size());
    }

    @Test
    public void testOpDup_PilaVacia() {
        try {
            StackOperations.opDup(stack);
            fail("Debió lanzar excepción OP_DUP");
        } catch (ExceptionsInterpreter e) {
            assertTrue(e.getMessage().contains("OP_DUP"));
        }
    }

    @Test
    public void testOpDrop() {
        stack.push(Utiles.intToBytes(5));
        StackOperations.opDrop(stack);
        assertEquals(0, stack.size());
    }

    @Test
    public void testOpSwap() {
        stack.push(Utiles.intToBytes(3));
        stack.push(Utiles.intToBytes(5));
        StackOperations.opSwap(stack);
        assertEquals(3, Utiles.bytesToInt(stack.pop()));
    }

    @Test
    public void testOpOver() {
        stack.push(Utiles.intToBytes(1));
        stack.push(Utiles.intToBytes(2));
        StackOperations.opOver(stack);
        assertEquals(3, stack.size());
    }
}
