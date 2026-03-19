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
import com.scriptbitcoin.operations.Logic;
import com.scriptbitcoin.utils.Utiles;

public class LogicTest {

    private Deque<byte[]> stack;

    @Before
    public void setup() {
        stack = new ArrayDeque<>();
    }

    @Test
    public void testOpEqual_True() {
        stack.push(Utiles.intToBytes(5));
        stack.push(Utiles.intToBytes(5));
        Logic.opEqual(stack);
        assertEquals(1, Utiles.bytesToInt(stack.peek()));
    }

    @Test
    public void testOpEqual_False() {
        stack.push(Utiles.intToBytes(5));
        stack.push(Utiles.intToBytes(6));
        Logic.opEqual(stack);
        assertFalse(Utiles.isTruthy(stack.peek()));
    }

    @Test
    public void testOpEqualVerify_Falla() {
        stack.push(Utiles.intToBytes(5));
        stack.push(Utiles.intToBytes(6));
        try {
            Logic.opEqualVerify(stack);
            fail("Debió lanzar excepción");
        } catch (ExceptionsInterpreter e) {
            assertTrue(e.getMessage().contains("OP_EQUALVERIFY"));
        }
    }

    @Test
    public void testOpNot() {
        stack.push(new byte[0]);
        Logic.opNot(stack);
        assertEquals(1, Utiles.bytesToInt(stack.peek()));
    }

    @Test
    public void testOpBoolAnd() {
        stack.push(Utiles.intToBytes(1));
        stack.push(Utiles.intToBytes(1));
        Logic.opBoolAnd(stack);
        assertEquals(1, Utiles.bytesToInt(stack.peek()));
    }

    @Test
    public void testOpBoolOr() {
        stack.push(new byte[0]);
        stack.push(Utiles.intToBytes(1));
        Logic.opBoolOr(stack);
        assertEquals(1, Utiles.bytesToInt(stack.peek()));
    }
}
