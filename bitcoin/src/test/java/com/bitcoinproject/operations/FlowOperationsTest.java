/**
 * @author Paola Itzep
 * Fecha: 18/03/2026
 */

package com.bitcoinproject.operations;

import java.util.ArrayDeque;
import java.util.Deque;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

import com.scriptbitcoin.interpreters.ExceptionsInterpreter;
import com.scriptbitcoin.operations.FlowOperations;
import com.scriptbitcoin.utils.Utiles;

public class FlowOperationsTest {

    private Deque<byte[]> stack;
    private Deque<Boolean> conditionStack;

    @Before
    public void setup() {
        stack = new ArrayDeque<>();
        conditionStack = new ArrayDeque<>();
    }

    @Test
    public void testOpIf_True() {
        stack.push(Utiles.intToBytes(1));
        FlowOperations.opIf(stack, conditionStack);
        assertTrue(conditionStack.peek());
    }

    @Test
    public void testOpIf_False() {
        stack.push(new byte[0]);
        FlowOperations.opIf(stack, conditionStack);
        assertFalse(conditionStack.peek());
    }

    @Test
    public void testOpIf_PilaVacia() {
        try {
            FlowOperations.opIf(stack, conditionStack);
            fail("Debió lanzar excepción OP_IF");
        } catch (ExceptionsInterpreter e) {
            assertTrue(e.getMessage().contains("OP_IF"));
        }
    }

    @Test
    public void testOpElse_CambiaCondicion() {
        conditionStack.push(true);
        FlowOperations.opElse(conditionStack);
        assertFalse(conditionStack.peek());
    }

    @Test
    public void testOpElse_SinIf() {
        try {
            FlowOperations.opElse(conditionStack);
            fail("Debió lanzar excepción OP_ELSE");
        } catch (ExceptionsInterpreter e) {
            assertTrue(e.getMessage().contains("OP_ELSE"));
        }
    }

    @Test
    public void testOpVerify_True() {
        stack.push(Utiles.intToBytes(1));
        FlowOperations.opVerify(stack);
        assertEquals(0, stack.size());
    }

    @Test
    public void testOpVerify_False() {
        stack.push(new byte[0]);
        try {
            FlowOperations.opVerify(stack);
            fail("Debió lanzar excepción OP_VERIFY");
        } catch (ExceptionsInterpreter e) {
            assertTrue(e.getMessage().contains("OP_VERIFY"));
        }
    }
}
