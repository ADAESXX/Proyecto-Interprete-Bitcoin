package com.scriptbitcoin.utils;
/**
 * @author Abigail Escobar
 * Fecha: 12/02/2026
 * Descripción: Clase utilitaria que proporciona métodos para convertir entre tipos de datos utilizados en Bitcoin Script, como la conversión entre byte arrays e enteros, y la evaluación de valores "truthy" o "falsy" según las reglas de Bitcoin Script.
 */

import java.util.Arrays;

public class Utiles {
    private Utiles() {
        // Clase utilitaria, no instanciable
    }

    /**
     * Determina si un valor de la pila es "verdadero" (truthy).
     * Un valor es falsy si es un arreglo vacio o si representa cero numerico.
     *
     * @param value el valor a evaluar
     * @return true si el valor es truthy
     */
    public static boolean isTruthy(byte[] value) {
        if (value == null || value.length == 0) {
            return false;
        }
        for (int i = 0; i < value.length; i++) {
            if (i == value.length - 1) {
                // El ultimo byte puede ser 0x80 (cero negativo)
                if ((value[i] & 0xFF) != 0x00 && (value[i] & 0xFF) != 0x80) {
                    return true;
                }
            } else {
                if (value[i] != 0x00) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Convierte un arreglo de bytes (codificacion little-endian con signo) a un entero.
     * Usa la codificacion de enteros de Bitcoin Script: little-endian con
     * el bit mas significativo del ultimo byte como bit de signo.
     *
     * @param bytes el arreglo de bytes a convertir
     * @return el valor entero representado
     */
    public static int bytesToInt(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return 0;
        }

        // Determinar si es negativo (bit 7 del ultimo byte)
        boolean negative = (bytes[bytes.length - 1] & 0x80) != 0;

        int result = 0;
        for (int i = bytes.length - 1; i >= 0; i--) {
            int b = bytes[i] & 0xFF;
            if (i == bytes.length - 1) {
                b &= 0x7F; // Remover bit de signo del ultimo byte
            }
            result = (result << 8) | b;
        }

        // Reordenar de big-endian del loop a little-endian correcto
        // En realidad el loop ya lee correctamente:
        // byte[0] es el menos significativo en little-endian
        result = 0;
        for (int i = 0; i < bytes.length; i++) {
            int b = bytes[i] & 0xFF;
            if (i == bytes.length - 1) {
                b &= 0x7F;
            }
            result |= (b << (8 * i));
        }

        return negative ? -result : result;
    }

    /**
     * Convierte un entero a su representacion en byte array.
     * Usa codificacion little-endian con bit de signo.
     * El valor 0 se representa como un arreglo vacio.
     *
     * @param value el valor entero a convertir
     * @return arreglo de bytes representando el valor
     */
    public static byte[] intToBytes(int value) {
        if (value == 0) {
            return new byte[0];
        }

        boolean negative = value < 0;
        int absValue = Math.abs(value);

        // Determinar cuantos bytes se necesitan
        int numBytes = 0;
        int temp = absValue;
        while (temp > 0) {
            numBytes++;
            temp >>= 8;
        }

        byte[] result = new byte[numBytes];
        temp = absValue;
        for (int i = 0; i < numBytes; i++) {
            result[i] = (byte) (temp & 0xFF);
            temp >>= 8;
        }

        // Si el bit mas significativo del ultimo byte esta encendido,
        // necesitamos un byte extra para el signo
        if ((result[numBytes - 1] & 0x80) != 0) {
            byte[] extended = new byte[numBytes + 1];
            System.arraycopy(result, 0, extended, 0, numBytes);
            extended[numBytes] = negative ? (byte) 0x80 : 0x00;
            return extended;
        }

        if (negative) {
            result[numBytes - 1] |= 0x80;
        }

        return result;
    }

    /**
     * Convierte un arreglo de bytes a su representacion hexadecimal.
     *
     * @param bytes el arreglo de bytes
     * @return cadena hexadecimal
     */
    public static String bytesToHex(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b & 0xFF));
        }
        return sb.toString();
    }

    /**
     * Compara dos arreglos de bytes por igualdad.
     *
     * @param a primer arreglo
     * @param b segundo arreglo
     * @return true si son iguales
     */
    public static boolean bytesEqual(byte[] a, byte[] b) {
        return Arrays.equals(a, b);
    }
}
