package com.bitcoinproject.operations;

import java.util.ArrayDeque;
import java.util.Deque;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

import com.scriptbitcoin.model.OpCode;
import com.scriptbitcoin.operations.Literales;
import com.scriptbitcoin.utils.Utiles;

public class LiteralesTest {

    private Deque<byte[]> stack;

    @Before
    public void setup() {
        stack = new ArrayDeque<>();
    }

    @Test
    public void testOp0() {
        Literales.executeLiteral(OpCode.OP_0, stack);
        assertFalse(Utiles.isTruthy(stack.peek()));
    }

    @Test
    public void testOp1() {
        Literales.executeLiteral(OpCode.OP_1, stack);
        assertEquals(1, Utiles.bytesToInt(stack.peek()));
    }

    @Test
    public void testOp16() {
        Literales.executeLiteral(OpCode.OP_16, stack);
        assertEquals(16, Utiles.bytesToInt(stack.peek()));
    }

    @Test
    public void testLiteralInvalido() {
        try {
            Literales.executeLiteral(null, stack);
            fail("Debió lanzar excepción");
        } catch (Exception e) {
            assertTrue(true);
        }
    }
}