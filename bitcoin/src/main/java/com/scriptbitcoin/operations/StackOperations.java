package com.scriptbitcoin.operations;

import java.util.Arrays;
import java.util.Deque;

import com.scriptbitcoin.interpreters.ExceptionsInterpreter;

/**
 * @author Abigail Escobar
 * Fecha: 13/02/2026
 * Descripción: Implementa las operaciones de manipulación de la pila de Bitcoin Script:
 *  - OP_DUP: Duplica el elemento superior de la pila.
 *  - OP_DROP: Elimina el elemento superior de la pila. 
 *  - OP_SWAP: Intercambia los dos elementos superiores de la pila.
 *  - OP_OVER: Duplica el segundo elemento desde la parte superior de la pila y lo coloca en la parte superior. 
 * 
 * Estado: completa
 */
public class StackOperations {
    private StackOperations() {
        // Clase utilitaria, no instanciable
    }

    /**
     * OP_DUP: Duplica el elemento en la cima de la pila.
     * Pila antes: [..., a] -> despues: [..., a, a]
     *
     * @param stack la pila principal
     * @throws InterpreterException si la pila esta vacia
     */
    public static void opDup(Deque<byte[]> stack) {
        if (stack.isEmpty()) {
            throw new ExceptionsInterpreter("OP_DUP: pila vacia");
        }
        byte[] top = stack.peek();
        stack.push(Arrays.copyOf(top, top.length));
    }

    /**
     * OP_DROP: Remueve el elemento de la cima de la pila.
     * Pila antes: [..., a] -> despues: [...]
     *
     * @param stack la pila principal
     * @throws ExceptionsInterpreter si la pila esta vacia
     */
    public static void opDrop(Deque<byte[]> stack) {
        if (stack.isEmpty()) {
            throw new ExceptionsInterpreter("OP_DROP: pila vacia");
        }
        stack.pop();
    }

    /**
     * OP_SWAP: Intercambia los dos elementos superiores de la pila.
     * Pila antes: [..., a, b] -> despues: [..., b, a]
     *
     * @param stack la pila principal
     * @throws InterpreterException si la pila tiene menos de 2 elementos
     */
    public static void opSwap(Deque<byte[]> stack) {
        if (stack.size() < 2) {
            throw new ExceptionsInterpreter("OP_SWAP: se necesitan al menos 2 elementos");
        }
        byte[] top = stack.pop();
        byte[] second = stack.pop();
        stack.push(top);
        stack.push(second);
    }

    /**
     * OP_OVER: Copia el segundo elemento desde la cima y lo coloca en la cima.
     * Pila antes: [..., a, b] -> despues: [..., a, b, a]
     *
     * @param stack la pila principal
     * @throws InterpreterException si la pila tiene menos de 2 elementos
     */
    public static void opOver(Deque<byte[]> stack) {
        if (stack.size() < 2) {
            throw new ExceptionsInterpreter("OP_OVER: se necesitan al menos 2 elementos");
        }
        byte[] top = stack.pop();
        byte[] second = stack.peek();
        stack.push(top);
        stack.push(Arrays.copyOf(second, second.length));
    }
}
