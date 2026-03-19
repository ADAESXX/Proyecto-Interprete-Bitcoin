/**
 * @author Raquel Vega
 * Fecha: 18/03/2026
 */
package com.bitcoinproject.interpreters;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.scriptbitcoin.interpreters.ScriptInterpreter;
import com.scriptbitcoin.interpreters.ScriptResult;
import com.scriptbitcoin.model.OpCode;
import com.scriptbitcoin.model.Valor;
import com.scriptbitcoin.operations.Firmas;
import com.scriptbitcoin.utils.Utiles;

import static org.junit.Assert.*;

/**
 * Tests unitarios para la clase ScriptInterpreter (JUnit 4).
 */
public class ScriptInterpreterTest {

    private ScriptInterpreter interpreter;

    @Before
    public void setup() {
        interpreter = new ScriptInterpreter();
        Firmas.clearSignatures();
    }

    @Test
    public void testScriptOP1_Exitoso() {
        List<Valor> script = new ArrayList<>();
        script.add(new Valor(OpCode.OP_1));
        ScriptResult result = interpreter.execute(script, false);
        assertTrue(result.isExito());
    }

    @Test
    public void testScriptOP0_Falla() {
        List<Valor> script = new ArrayList<>();
        script.add(new Valor(OpCode.OP_0));
        ScriptResult result = interpreter.execute(script, false);
        assertFalse(result.isExito());
    }

    @Test
    public void testScriptVacio_Falla() {
        List<Valor> script = new ArrayList<>();
        ScriptResult result = interpreter.execute(script, false);
        assertFalse(result.isExito());
        assertTrue(result.getMensaje().contains("vacía"));
    }

    @Test
    public void testScript_3_4_ADD() {
        List<Valor> script = new ArrayList<>();
        script.add(new Valor(OpCode.OP_3));
        script.add(new Valor(OpCode.OP_4));
        script.add(new Valor(OpCode.OP_ADD));
        ScriptResult result = interpreter.execute(script, false);
        assertTrue(result.isExito());
        assertEquals(7, Utiles.bytesToInt(result.getFinalStack().peek()));
    }

    @Test
    public void testScript_2_3_ADD_5_EQUAL() {
        List<Valor> script = new ArrayList<>();
        script.add(new Valor(OpCode.OP_2));
        script.add(new Valor(OpCode.OP_3));
        script.add(new Valor(OpCode.OP_ADD));
        script.add(new Valor(OpCode.OP_5));
        script.add(new Valor(OpCode.OP_EQUAL));
        ScriptResult result = interpreter.execute(script, false);
        assertTrue(result.isExito());
    }

    @Test
    public void testScript_2_3_ADD_6_EQUAL_Falla() {
        List<Valor> script = new ArrayList<>();
        script.add(new Valor(OpCode.OP_2));
        script.add(new Valor(OpCode.OP_3));
        script.add(new Valor(OpCode.OP_ADD));
        script.add(new Valor(OpCode.OP_6));
        script.add(new Valor(OpCode.OP_EQUAL));
        ScriptResult result = interpreter.execute(script, false);
        assertFalse(result.isExito());
    }

    @Test
    public void testScript_5_3_GREATERTHAN() {
        List<Valor> script = new ArrayList<>();
        script.add(new Valor(OpCode.OP_5));
        script.add(new Valor(OpCode.OP_3));
        script.add(new Valor(OpCode.OP_GREATERTHAN));
        ScriptResult result = interpreter.execute(script, false);
        assertTrue(result.isExito());
    }

    @Test
    public void testScript_DUP_EQUAL() {
        List<Valor> script = new ArrayList<>();
        script.add(new Valor(OpCode.OP_5));
        script.add(new Valor(OpCode.OP_DUP));
        script.add(new Valor(OpCode.OP_EQUAL));
        ScriptResult result = interpreter.execute(script, false);
        assertTrue(result.isExito());
    }

    @Test
    public void testScript_DROP() {
        List<Valor> script = new ArrayList<>();
        script.add(new Valor(OpCode.OP_1));
        script.add(new Valor(OpCode.OP_2));
        script.add(new Valor(OpCode.OP_DROP));
        ScriptResult result = interpreter.execute(script, false);
        assertTrue(result.isExito());
        assertEquals(1, Utiles.bytesToInt(result.getFinalStack().peek()));
    }

    @Test
    public void testScript_SWAP() {
        List<Valor> script = new ArrayList<>();
        script.add(new Valor(OpCode.OP_1));
        script.add(new Valor(OpCode.OP_2));
        script.add(new Valor(OpCode.OP_SWAP));
        ScriptResult result = interpreter.execute(script, false);
        assertTrue(result.isExito());
        assertEquals(1, Utiles.bytesToInt(result.getFinalStack().peek()));
    }

    @Test
    public void testScript_OVER() {
        List<Valor> script = new ArrayList<>();
        script.add(new Valor(OpCode.OP_3));
        script.add(new Valor(OpCode.OP_7));
        script.add(new Valor(OpCode.OP_OVER));
        ScriptResult result = interpreter.execute(script, false);
        assertTrue(result.isExito());
        assertEquals(3, result.getFinalStack().size());
        assertEquals(3, Utiles.bytesToInt(result.getFinalStack().peek()));
    }

    @Test
    public void testScript_BOOLAND_TrueTrue() {
        List<Valor> script = new ArrayList<>();
        script.add(new Valor(OpCode.OP_1));
        script.add(new Valor(OpCode.OP_1));
        script.add(new Valor(OpCode.OP_BOOLAND));
        ScriptResult result = interpreter.execute(script, false);
        assertTrue(result.isExito());
    }

    @Test
    public void testScript_BOOLAND_TrueFalse() {
        List<Valor> script = new ArrayList<>();
        script.add(new Valor(OpCode.OP_1));
        script.add(new Valor(OpCode.OP_0));
        script.add(new Valor(OpCode.OP_BOOLAND));
        ScriptResult result = interpreter.execute(script, false);
        assertFalse(result.isExito());
    }

    @Test
    public void testScript_BOOLOR_FalseTrue() {
        List<Valor> script = new ArrayList<>();
        script.add(new Valor(OpCode.OP_0));
        script.add(new Valor(OpCode.OP_1));
        script.add(new Valor(OpCode.OP_BOOLOR));
        ScriptResult result = interpreter.execute(script, false);
        assertTrue(result.isExito());
    }

    @Test
    public void testScript_NOT_False() {
        List<Valor> script = new ArrayList<>();
        script.add(new Valor(OpCode.OP_0));
        script.add(new Valor(OpCode.OP_NOT));
        ScriptResult result = interpreter.execute(script, false);
        assertTrue(result.isExito());
    }

    @Test
    public void testScript_NOT_True() {
        List<Valor> script = new ArrayList<>();
        script.add(new Valor(OpCode.OP_1));
        script.add(new Valor(OpCode.OP_NOT));
        ScriptResult result = interpreter.execute(script, false);
        assertFalse(result.isExito());
    }

    @Test
    public void testGetStack_AntesDeEjecutar() {
        assertNull(interpreter.getStack());
    }

    @Test
    public void testGetStack_DespuesDeEjecutar() {
        List<Valor> script = new ArrayList<>();
        script.add(new Valor(OpCode.OP_1));
        interpreter.execute(script, false);
        assertNotNull(interpreter.getStack());
        assertFalse(interpreter.getStack().isEmpty());
    }
}