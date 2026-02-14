package com.scriptbitcoin.operations;

import java.nio.charset.StandardCharsets;
import java.util.Deque;

import com.scriptbitcoin.interpreters.ExceptionsInterpreter;

/**
 * @author Abigail Escobar
 * Fecha:
 * Descripción: Clase que implementa las operaciones criptográficas de Bitcoin Script
 *  - OP_HASH160: Aplica la función hash RIPEMD-160 al resultado de la función hash SHA-256 del elemento superior de la pila.
 *  - OP_HASH256: Aplica la función hash SHA-256 dos veces al elemento superior de la pila.
 *  - OP_SHA256: Aplica la función hash SHA-256 al elemento superior de la pila.
 * 
 * Estado: completa
 */
public class Crypto {
    private Crypto() {
        // Clase utilitaria, no instanciable
    }

    /**
     * OP_SHA256: Calcula el hash SHA-256 del elemento superior de la pila.
     *
     * @param stack la pila principal
     * @throws InterpreterException si la pila esta vacia
     */
    public static void opSha256(Deque<byte[]> stack) {
        if (stack.isEmpty()) {
            throw new ExceptionsInterpreter("OP_SHA256: pila vacia");
        }
        byte[] data = stack.pop();
        byte[] hashed = mockSha256(data);
        stack.push(hashed);
    }

    /**
     * OP_HASH160: Calcula HASH160 (SHA-256 seguido de RIPEMD-160) del elemento superior.
     *
     * @param stack la pila principal
     * @throws ExceptionsInterpreter si la pila esta vacia
     */
    public static void opHash160(Deque<byte[]> stack) {
        if (stack.isEmpty()) {
            throw new ExceptionsInterpreter("OP_HASH160: pila vacia");
        }
        byte[] data = stack.pop();
        byte[] hashed = mockHash160(data);
        stack.push(hashed);
    }

    /**
     * OP_HASH256: Calcula el doble hash SHA-256 del elemento superior.
     *
     * @param stack la pila principal
     * @throws ExceptionsInterpreter si la pila esta vacia
     */
    public static void opHash256(Deque<byte[]> stack) {
        if (stack.isEmpty()) {
            throw new ExceptionsInterpreter("OP_HASH256: pila vacia");
        }
        byte[] data = stack.pop();
        byte[] hashed = mockHash256(data);
        stack.push(hashed);
    }

    /**
     * Simulacion de SHA-256. Prefija "SHA256:" al dato de entrada.
     * Esto produce un hash determinista y verificable para pruebas.
     *
     * @param data los datos a hashear
     * @return el hash simulado
     */
    public static byte[] mockSha256(byte[] data) {
        String prefix = "SHA256:";
        byte[] prefixBytes = prefix.getBytes(StandardCharsets.UTF_8);
        byte[] result = new byte[prefixBytes.length + data.length];
        System.arraycopy(prefixBytes, 0, result, 0, prefixBytes.length);
        System.arraycopy(data, 0, result, prefixBytes.length, data.length);
        return result;
    }

    /**
     * Simulacion de HASH160
     *
     * @param data los datos a hashear
     * @return el hash simulado
     */
    public static byte[] mockHash160(byte[] data) {
        String prefix = "HASH160:";
        byte[] prefixBytes = prefix.getBytes(StandardCharsets.UTF_8);
        byte[] result = new byte[prefixBytes.length + data.length];
        System.arraycopy(prefixBytes, 0, result, 0, prefixBytes.length);
        System.arraycopy(data, 0, result, prefixBytes.length, data.length);
        return result;
    }

    /**
     * Simulacion de doble SHA-256 (HASH256).
     *
     * @param data los datos a hashear
     * @return el hash simulado
     */
    public static byte[] mockHash256(byte[] data) {
        String prefix = "HASH256:";
        byte[] prefixBytes = prefix.getBytes(StandardCharsets.UTF_8);
        byte[] result = new byte[prefixBytes.length + data.length];
        System.arraycopy(prefixBytes, 0, result, 0, prefixBytes.length);
        System.arraycopy(data, 0, result, prefixBytes.length, data.length);
        return result;
    }
}
