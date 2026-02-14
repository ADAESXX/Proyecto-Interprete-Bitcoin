package com.scriptbitcoin.operations;

import java.util.Arrays;
import java.util.Deque;

import com.scriptbitcoin.interpreters.ExceptionsInterpreter;
import com.scriptbitcoin.utils.Utiles;

/**
 * @author Abigail Escobar
 * Fecha:
 * Descripción: implementa las operaciones lógicas de Bitcoin Script y comparacación de datos:
 *  -OP_EQUAL: Compara dos elementos de la pila y verifica si son iguales, si no lo son, el script falla.
 *  -OP_EQUALVERIFY: Compara dos elementos de la pila y verifica si son iguales, si no lo son, el script falla.
 *  -OP_BOOLAND: Realiza una operación AND lógica entre dos elementos de la pila y empuja el resultado a la pila.
 *  -OP_BOOLOR: Realiza una operación OR lógica entre dos elementos de la pila  y empuja el resultado a la pila.
 *  -OP_NOT: Realiza una operación NOT lógica en el elemento superior de la pila y empuja el resultado a la pila.
 * 
 * Estado: completa
 */
public class Logic {
    private Logic() {
        // Clase utilitaria, no instanciable
    }

    /**
     * OP_EQUAL: Compara los dos elementos superiores de la pila.
     * Empuja 1 si son iguales, 0 si no.
     * Pila: [..., a, b] -> [..., (a == b ? 1 : 0)]
     *
     * @param stack la pila principal
     * @throws InterpreterException si la pila tiene menos de 2 elementos
     */
    public static void opEqual(Deque<byte[]> stack) {
        if (stack.size() < 2) {
            throw new ExceptionsInterpreter("OP_EQUAL: se necesitan al menos 2 elementos");
        }
        byte[] b = stack.pop();
        byte[] a = stack.pop();
        boolean equal = Arrays.equals(a, b);
        stack.push(equal ? Utiles.intToBytes(1) : new byte[0]);
    }

    /**
     * OP_EQUALVERIFY: Igual que OP_EQUAL seguido de OP_VERIFY.
     * Si los valores no son iguales, falla el script inmediatamente.
     * Pila: [..., a, b] -> [...] (falla si a != b)
     *
     * @param stack la pila principal
     * @throws ExceptionsInterpreter si no son iguales o pila insuficiente
     */
    public static void opEqualVerify(Deque<byte[]> stack) {
        if (stack.size() < 2) {
            throw new ExceptionsInterpreter("OP_EQUALVERIFY: se necesitan al menos 2 elementos");
        }
        byte[] b = stack.pop();
        byte[] a = stack.pop();
        if (!Arrays.equals(a, b)) {
            throw new ExceptionsInterpreter("OP_EQUALVERIFY: los valores no son iguales");
        }
    }

    /**
     * OP_NOT: Invierte el valor logico del elemento superior.
     * Si es 0, empuja 1. Si es distinto de 0, empuja 0.
     * Pila: [..., a] -> [..., !a]
     *
     * @param stack la pila principal
     * @throws ExceptionsInterpreter si la pila esta vacia
     */
    public static void opNot(Deque<byte[]> stack) {
        if (stack.isEmpty()) {
            throw new ExceptionsInterpreter("OP_NOT: pila vacia");
        }
        byte[] top = stack.pop();
        boolean truthy = Utiles.isTruthy(top);
        stack.push(truthy ? new byte[0] : Utiles.intToBytes(1));
    }

    /**
     * OP_BOOLAND: AND logico de los dos elementos superiores.
     * Empuja 1 si ambos son distintos de cero, 0 si alguno es cero.
     * Pila: [..., a, b] -> [..., (a && b ? 1 : 0)]
     *
     * @param stack la pila principal
     * @throws ExceptionsInterpreter si la pila tiene menos de 2 elementos
     */
    public static void opBoolAnd(Deque<byte[]> stack) {
        if (stack.size() < 2) {
            throw new ExceptionsInterpreter("OP_BOOLAND: se necesitan al menos 2 elementos");
        }
        boolean b = Utiles.isTruthy(stack.pop());
        boolean a = Utiles.isTruthy(stack.pop());
        stack.push((a && b) ? Utiles.intToBytes(1) : new byte[0]);
    }

    /**
     * OP_BOOLOR: OR logico de los dos elementos superiores.
     * Empuja 1 si alguno es distinto de cero, 0 si ambos son cero.
     * Pila: [..., a, b] -> [..., (a || b ? 1 : 0)]
     *
     * @param stack la pila principal
     * @throws ExceptionsInterpreter si la pila tiene menos de 2 elementos
     */
    public static void opBoolOr(Deque<byte[]> stack) {
        if (stack.size() < 2) {
            throw new ExceptionsInterpreter("OP_BOOLOR: se necesitan al menos 2 elementos");
        }
        boolean b = Utiles.isTruthy(stack.pop());
        boolean a = Utiles.isTruthy(stack.pop());
        stack.push((a || b) ? Utiles.intToBytes(1) : new byte[0]);
    }
}
