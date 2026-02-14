package com.bitcoinproject.operations;


import com.scriptbitcoin.interpreters.ScriptInterpreter;
import com.scriptbitcoin.interpreters.ExceptionsInterpreter;

class StackOperationsTest {

   /*  @Test
    void opDupShouldDuplicateTopElement() {
        ScriptInterpreter interpreter = new ScriptInterpreter();
        interpreter.execute("5 OP_DUP");
        assertEquals("5", interpreter.pop());
        assertEquals("5", interpreter.pop());
    }

    @Test
    void opDropShouldRemoveTopElement() {
        ScriptInterpreter interpreter = new ScriptInterpreter();
        interpreter.execute("5 6 OP_DROP");
        assertEquals("5", interpreter.pop());
    }

    @Test
    void opDupShouldFailOnEmptyStack() {
        ScriptInterpreter interpreter = new ScriptInterpreter();
        assertThrows(ExceptionsInterpreter.class, () -> {
            interpreter.execute("OP_DUP");
        });
    }

    @Test
    void opSwapShouldExchangeTopElements() {
        ScriptInterpreter interpreter = new ScriptInterpreter();
        interpreter.execute("3 5 OP_SWAP");
        assertEquals("3", interpreter.pop());
        assertEquals("5", interpreter.pop());
    }

    @Test
    void opOverShouldDuplicateSecondElement() {
        ScriptInterpreter interpreter = new ScriptInterpreter();
        interpreter.execute("1 2 OP_OVER");
        assertEquals("1", interpreter.pop());
        assertEquals("2", interpreter.pop());
        assertEquals("1", interpreter.pop());
    } */
}