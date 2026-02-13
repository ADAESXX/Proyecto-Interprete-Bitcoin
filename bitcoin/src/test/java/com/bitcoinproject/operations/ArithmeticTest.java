/**
 * @author Paola Merida
 * Fecha: 12/02/2026
 */

package com.bitcoinproject.operations;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.scriptbitcoin.interpreters.ScriptInterpreter;
import com.scriptbitcoin.interpreters.ExceptionsInterpreter;

class ArithmeticTest {

    @Test
    void opAddShouldAddTwoNumbers() {
        ScriptInterpreter interpreter = new ScriptInterpreter();
        interpreter.execute("3 4 OP_ADD");
    }

    @Test
    void opSubShouldSubtractTwoNumbers() {
        ScriptInterpreter interpreter = new ScriptInterpreter();
        interpreter.execute("10 3 OP_SUB");
    }

    @Test
    void opLessThanShouldReturnTrueWhenFirstLess() {
        ScriptInterpreter interpreter = new ScriptInterpreter();
        interpreter.execute("3 5 OP_LESSTHAN");
    }

    @Test
    void opGreaterThanShouldReturnTrueWhenFirstGreater() {
        ScriptInterpreter interpreter = new ScriptInterpreter();
        interpreter.execute("5 3 OP_GREATERTHAN");
    }

    @Test
    void opLessThanOrEqualShouldAcceptEqual() {
        ScriptInterpreter interpreter = new ScriptInterpreter();
        interpreter.execute("5 5 OP_LESSTHANOREQUAL");
    }

    @Test
    void opGreaterThanOrEqualShouldAcceptEqual() {
        ScriptInterpreter interpreter = new ScriptInterpreter();
        interpreter.execute("5 5 OP_GREATERTHANOREQUAL");
    }

    @Test
    void opNumEqualVerifyShouldPassForEqualNumbers() {
        ScriptInterpreter interpreter = new ScriptInterpreter();
        interpreter.execute("5 5 OP_NUMEQUALVERIFY");
    }

    @Test
    void opNumEqualVerifyShouldFailForUnequalNumbers() {
        ScriptInterpreter interpreter = new ScriptInterpreter();
        assertThrows(ExceptionsInterpreter.class, () -> {
            interpreter.execute("5 6 OP_NUMEQUALVERIFY");
        });
    }
}