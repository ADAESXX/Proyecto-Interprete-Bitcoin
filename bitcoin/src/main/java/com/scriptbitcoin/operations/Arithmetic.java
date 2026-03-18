package com.scriptbitcoin.operations;

import java.util.Deque;

import com.scriptbitcoin.interpreters.ExceptionsInterpreter;
import com.scriptbitcoin.utils.Utiles;

/**
 * @author Abigail Escobar
 * Fecha: 13/02/2026
 * Fecha de modificación: 17/03/2026
 * Estado: completa
 * Descripción: Clase que implementa las operaciones aritméticas de Bitcoin Script
 *  - OP_ADD: Suma dos elementos de la pila.
 *  - OP_SUB: Resta el segundo elemento de la pila al primero.
 *  - OP_NUMEQUALVERIFY: Compara dos elementos de la pila y verifica si son iguales, si no lo son, el script falla.
 *  - OP_LESSTHAN: Compara dos elementos de la pila y verifica si el primero es menor que el segundo, si no lo es, el script falla.
 *  - OP_GREATERTHAN: Compara dos elementos de la pila y verifica si
 *  - OP_LESSTHANOREQUAL: Compara dos elementos de la pila y verifica si el primero es menor o igual que el segundo, si no lo es, el script falla.
 *  - OP_GREATERTHANOREQUAL: Compara dos elementos de la pila y verifica si el primero es mayor o igual que el segundo, si no lo es, el script falla.
 */
public class Arithmetic {
    private Arithmetic() {
        // Clase utilitaria, no instanciable
    }

    /**
     * OP_ADD: Suma los dos elementos superiores de la pila.
     * @param stack la pila principal
     * @throws ExceptionsInterpreter si la pila tiene menos de 2 elementos
     */
    public static void opAdd(Deque<byte[]> stack) {
        if (stack.size() < 2) {
            throw new ExceptionsInterpreter("OP_ADD: se necesitan al menos 2 elementos");
        }
        int b = Utiles.bytesToInt(stack.pop());
        int a = Utiles.bytesToInt(stack.pop());
        stack.push(Utiles.intToBytes(a + b));
    }

    /**
     * OP_SUB: Resta el elemento superior del segundo elemento.
     * @param stack la pila principal
     * @throws ExceptionsInterpreter si la pila tiene menos de 2 elementos
     */
    
    public static void opSub(Deque<byte[]> stack){
        if (stack.size() < 2) {
            throw new ExceptionsInterpreter("OP_SUB: se necesitan al menos 2 elementos");
        }
        int b = Utiles.bytesToInt(stack.pop());
        int a = Utiles.bytesToInt(stack.pop());
        stack.push(Utiles.intToBytes(a - b));
    }

    /**
     * OP_NUMEQUALVERIFY: Verifica que los dos elementos superiores son numericamente iguales.
     * Si no son iguales, falla el script
     *
     * @param stack la pila principal
     * @throws ExceptionsInterpreter si no son iguales o pila insuficiente
     */
    public static void opNumEqualVerify(Deque<byte[]> stack) {
        if (stack.size() < 2) {
            throw new ExceptionsInterpreter("OP_NUMEQUALVERIFY: se necesitan al menos 2 elementos");
        }
        int b = Utiles.bytesToInt(stack.pop());
        int a = Utiles.bytesToInt(stack.pop());
        if (a != b) {
            throw new ExceptionsInterpreter("OP_NUMEQUALVERIFY: " + a + " != " + b);
        }
    }

    /**
     * OP_LESSTHAN: Verifica si el segundo elemento es menor que el superior.
     * Pila: [..., a, b] -> [..., (a < b ? 1 : 0)]
     *
     * @param stack la pila principal
     * @throws ExceptionsInterpreter si la pila tiene menos de 2 elementos
     */
    public static void opLessThan(Deque<byte[]> stack) {
        if (stack.size() < 2) {
            throw new ExceptionsInterpreter("OP_LESSTHAN: se necesitan al menos 2 elementos");
        }
        int b = Utiles.bytesToInt(stack.pop());
        int a = Utiles.bytesToInt(stack.pop());
        stack.push(a < b ? Utiles.intToBytes(1) : new byte[0]);
    }


    /**
     * OP_GREATERTHAN: Verifica si el segundo elemento es mayor que el superior.
     * @param stack la pila principal
     * @throws ExceptionsInterpreter si la pila tiene menos de 2 elementos
     */
    public static void opGreaterThan(Deque<byte[]> stack) {
        if (stack.size() < 2) {
            throw new ExceptionsInterpreter("OP_GREATERTHAN: se necesitan al menos 2 elementos");
        }
        int b = Utiles.bytesToInt(stack.pop());
        int a = Utiles.bytesToInt(stack.pop());
        int result = (a > b) ? 1 : 0;
        stack.push(Utiles.intToBytes(result));
    }

    /**
     * OP_LESSTHANOREQUAL: Verifica si el segundo elemento es menor o igual al superior.
     *
     * @param stack la pila principal
     * @throws ExceptionsInterpreter si la pila tiene menos de 2 elementos
     */
    public static void opLessThanOrEqual(Deque<byte[]> stack) {
        if (stack.size() < 2) {
            throw new ExceptionsInterpreter("OP_LESSTHANOREQUAL: se necesitan al menos 2 elementos");
        }
        int b = Utiles.bytesToInt(stack.pop());
        int a = Utiles.bytesToInt(stack.pop());
        stack.push(a <= b ? Utiles.intToBytes(1) : new byte[0]);
    }

    /**
     * OP_GREATERTHANOREQUAL: Verifica si el segundo elemento es mayor o igual al superior.
     *
     * @param stack la pila principal
     * @throws ExceptionsInterpreter si la pila tiene menos de 2 elementos
     */
    public static void opGreaterThanOrEqual(Deque<byte[]> stack) {
        if (stack.size() < 2) {
            throw new ExceptionsInterpreter("OP_GREATERTHANOREQUAL: se necesitan al menos 2 elementos");
        }
        int b = Utiles.bytesToInt(stack.pop());
        int a = Utiles.bytesToInt(stack.pop());
        stack.push(a >= b ? Utiles.intToBytes(1) : new byte[0]);
    }
}

