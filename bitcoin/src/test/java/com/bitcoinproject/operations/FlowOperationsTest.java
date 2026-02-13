/**
 * @author Paola Merida
 * Fecha: 13/02/2026
 */
package com.bitcoinproject.operations;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.scriptbitcoin.interpreters.ScriptInterpreter;
import com.scriptbitcoin.interpreters.ExceptionsInterpreter;

class FlowOperationsTest {

    @Test
    void opIfShouldExecuteTrueBranch() {
        ScriptInterpreter interpreter = new ScriptInterpreter();
        interpreter.execute("1 OP_IF 5 OP_ENDIF");
    }

    @Test
    void opIfShouldSkipFalseBranch() {
        ScriptInterpreter interpreter = new ScriptInterpreter();
        assertThrows(ExceptionsInterpreter.class, () -> {
            interpreter.execute("0 OP_IF 5 OP_ENDIF");
        });
    }

    @Test
    void opElseShouldExecuteOnFalse() {
        ScriptInterpreter interpreter = new ScriptInterpreter();
        interpreter.execute("0 OP_IF 5 OP_ELSE 6 OP_ENDIF");
    }

    @Test
    void opVerifyShouldFailOnFalseValue() {
        ScriptInterpreter interpreter = new ScriptInterpreter();
        assertThrows(ExceptionsInterpreter.class, () -> {
            interpreter.execute("0 OP_VERIFY");
        });
    }

    @Test
    void opVerifyShouldPassOnTrueValue() {
        ScriptInterpreter interpreter = new ScriptInterpreter();
        interpreter.execute("1 OP_VERIFY");
    }

    @Test
    void opNotIfShouldExecuteOnFalse() {
        ScriptInterpreter interpreter = new ScriptInterpreter();
        interpreter.execute("0 OP_NOTIF 5 OP_ENDIF");
    }
}
