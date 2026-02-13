package com.scriptbitcoin.utils;
import java.util.*;

import com.scriptbitcoin.model.OpCode;
import com.scriptbitcoin.model.Valor;
/**
 * @author Abigail Escobar
 * Fecha: 12/02/2026
 * Basicamente convierte una cadena de texto con un script de Bitcoin en una lista de valores
 */

public class ScriptValores {
    //mapa de "nombre" de cada opcode 
    private final Map<String, OpCode> opcodeMap;
    //constructor
    public ScriptValores() {
        //inicializa el mapa de opcodes, recorriendo cada valor del enum OpCode y almacenándolo en el mapa con su nombre como clave
        opcodeMap = new HashMap<>();
        for (OpCode op : OpCode.values()) {
            opcodeMap.put(op.name(), op);
        }
    }
    
    /**
     * 
     * @param script
     * @return
     * @throws IllegalArgumentException si el script contiene un token no reconocido
     */
    //este método se encarga de convertir una cadena de texto con un script de Bitcoin en una lista de valores, donde cada valor puede ser un opcode o un literal de datos.
    //un opcode es una operación que se ejecuta durante la interpretación del script, mientras que un literal de datos es un valor que se empuja a la pila para ser utilizado por los opcodes.
    public List<Valor> tokenize(String script){
        List<Valor> tokens = new ArrayList<>();
        //trim se usa para eliminar espacios en blanco al inicio y al final de la cadena, y split("\\s+") se usa para dividir la cadena en partes utilizando uno o más espacios como delimitador
        if (script == null || script.trim().isEmpty()) {
            return tokens;
        }

        String[] parts = script.trim().split("\\s+");

        for (String part : parts) {
            Valor token = parseToken(part);
            tokens.add(token);
        }

        return tokens;
    }
    /**
     * este método se encarga de analizar cada parte del script para determinar si es un opcode conocido, un literal de datos entre angulares, un número entero o un dato hexadecimal.
     * @param part
     * @return
     */
    private Valor parseToken(String part) {
        // Verificar si es un opcode conocido
        if (opcodeMap.containsKey(part)) {
            return new Valor(opcodeMap.get(part));
        }

        // Verificar si es un dato entre angulares como <sig>, <pubKey>, <pubKeyHash>
        if (part.startsWith("<") && part.endsWith(">")) {
            String placeholder = part.substring(1, part.length() - 1);
            return new Valor(placeholder.getBytes()); // Convertir el placeholder a bytes para simular un literal de datos
        }

        // Verificar si es un literal numerico entero
        try {
            int value = Integer.parseInt(part);
            return new Valor(intToBytes(value));
        } catch (NumberFormatException e) {
            // No es un entero
        }

        // Verificar si es un dato hexadecimal (prefijo 0x)
        if (part.startsWith("0x") || part.startsWith("0X")) {
            try {
                byte[] hexData = hexToBytes(part.substring(2));
                return new Valor(hexData);
            } catch (IllegalArgumentException e) {
                // No es hex valido
            }
        }

        throw new IllegalArgumentException("Token no reconocido: " + part);
    }

    /**
     * Convierte un entero a su representación en bytes, siguiendo la convención de Bitcoin Script para números enteros, donde el bit más significativo del último byte se utiliza para indicar el signo del número (positivo o negativo).
     * @param value
     * @return
     * 
     * Use chatGPT para esta función, ya que es un poco más compleja y requiere manejar el formato específico de los enteros en Bitcoin Script, incluyendo la representación de números negativos y la necesidad de agregar un byte adicional si el bit más significativo del último byte está encendido.
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
        // agregar un byte extra para el signo
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
    //Convierte una cadena de hexadecimal a un array de bytes, lo que es útil para interpretar datos literales en formato hexadecimal dentro de los scripts de Bitcoin.
    private byte[] hexToBytes(String hex){
        if (hex.length() % 2 != 0) {
            hex = "0" + hex;
        }
        byte[] result = new byte[hex.length() / 2];
        for (int i = 0; i < result.length; i++) {
            int high = Character.digit(hex.charAt(i * 2), 16);
            int low = Character.digit(hex.charAt(i * 2 + 1), 16);
            if (high == -1 || low == -1) {
                throw new IllegalArgumentException("Caracter hex invalido");
            }
            result[i] = (byte) ((high << 4) | low);
        }
        return result;

    }
}
