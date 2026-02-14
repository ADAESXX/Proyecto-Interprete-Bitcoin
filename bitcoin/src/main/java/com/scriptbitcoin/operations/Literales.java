package com.scriptbitcoin.operations;

import java.util.Deque;

import com.scriptbitcoin.model.OpCode;
import com.scriptbitcoin.utils.Utiles;

/**
 * @author Abigail Escobar
 * Fecha: 13/02/2026
 * Descripción: implementa las operaciones de literales de Bitcoin Script y el empuje de esos datos:
 *  - OP_0/OP_FALSE: Empuja un valor vacío a la pila.
 *  - OP_1: Empuja el valor 1 a la pila.
 *  - OP_2 a OP_16: Empuja el valor numérico correspondiente (2 a 16) a la pila.
 *  - No implementa OP_PUSHDATA1, OP_PUSHDATA2, que permiten empujar datos arbitrarios a la pila, porque requieren manejo de índices y lectura de datos del script, lo cual se maneja directamente en el ScriptInterpreter.
 */
public class Literales {
    public Literales() {
        // Clase utilitaria, no instanciable
    }

    public static void executeLiteral(OpCode opCode, Deque<byte[]> stack) {

        if (opCode == OpCode.OP_0 || opCode == OpCode.OP_FALSE) {
            stack.push(new byte[0]);
            return;
        }

        if (opCode.ordinal() >= OpCode.OP_1.ordinal() &&
            opCode.ordinal() <= OpCode.OP_16.ordinal()) {

            int value = opCode.ordinal() - OpCode.OP_1.ordinal() + 1;
            stack.push(Utiles.intToBytes(value));
            return;
        }

        throw new IllegalArgumentException("Opcode literal inválido: " + opCode);
    }
    
}
