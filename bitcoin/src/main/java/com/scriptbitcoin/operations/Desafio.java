package com.scriptbitcoin.operations;

import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

import com.scriptbitcoin.interpreters.ExceptionsInterpreter;
import com.scriptbitcoin.utils.Utiles;

/**
 * @author Abigail Escobar
 * Fecha: 18/03/2026
 * Estado:completo
 * Descripción: OP_CHECKMULTISIG, OP_CHECKMULTISIGVERIFY (requiere el elemento OP_0 de relleno por compatibilidad histórica)
 */
public class Desafio {
    private static final Map<String, String> mockValidSignatures = new HashMap<>();

    private Desafio() {
        // constructor que no es insatanciable :)
    }


    /**
     * Registra un par (firma, clave publica) como valido en el almacen mock.
     *
     * @param signature la firma (como byte array)
     * @param pubKey la clave publica (como byte array)
     */
    public static void registerValidSignature(byte[] signature, byte[] pubKey) {
        String key = Utiles.bytesToHex(signature) + ":" + Utiles.bytesToHex(pubKey);
        mockValidSignatures.put(key, "valid");
    }

    /**
     * Limpia todas las firmas registradas en el almacen mock.
     */
    public static void clearSignatures() {
        mockValidSignatures.clear();
    }


    /**
     * OP_CHECKMULTISIG: Verificacion M-de-N multifirma.
     * Formato de la pila: [dummy, sig1, ..., sigM, M, pubKey1, ..., pubKeyN, N]
     * (N esta en la cima, luego las N claves publicas, luego M, luego las M firmas,
     * luego el dummy element OP_0 por compatibilidad historica).
     *
     * @param stack la pila principal
     * @throws ExceptionsInterpreter si el formato es incorrecto o la verificacion falla
     */
    public static void opCheckMultiSig(Deque<byte[]> stack) {
        if (stack.isEmpty()) {
            throw new ExceptionsInterpreter("OP_CHECKMULTISIG: pila vacia");
        }

        // Leer N (numero de claves publicas)
        int n = Utiles.bytesToInt(stack.pop());
        if (n < 0 || stack.size() < n) {
            throw new ExceptionsInterpreter("OP_CHECKMULTISIG: N invalido o pila insuficiente");
        }

        // Leer N claves publicas
        byte[][] pubKeys = new byte[n][];
        for (int i = 0; i < n; i++) {
            pubKeys[i] = stack.pop();
        }

        // Leer M (numero de firmas requeridas)
        if (stack.isEmpty()) {
            throw new ExceptionsInterpreter("OP_CHECKMULTISIG: falta M");
        }
        int m = Utiles.bytesToInt(stack.pop());
        if (m < 0 || m > n || stack.size() < m) {
            throw new ExceptionsInterpreter("OP_CHECKMULTISIG: M invalido o pila insuficiente");
        }

        // Leer M firmas
        byte[][] sigs = new byte[m][];
        for (int i = 0; i < m; i++) {
            sigs[i] = stack.pop();
        }

        // Consumir el dummy element (bug historico de Bitcoin)
        if (stack.isEmpty()) {
            throw new ExceptionsInterpreter("OP_CHECKMULTISIG: falta elemento dummy (OP_0)");
        }
        stack.pop();

        // Verificar firmas: cada firma debe coincidir con una clave publica
        // Las firmas deben estar en el mismo orden que las claves publicas
        boolean valid = verifyMultiSig(sigs, pubKeys, m);
        stack.push(valid ? Utiles.intToBytes(1) : new byte[0]);
    }

    /**
     * OP_CHECKMULTISIGVERIFY: Igual que OP_CHECKMULTISIG seguido de OP_VERIFY.
     *
     * @param stack la pila principal
     * @throws ExceptionsInterpreter si la verificacion falla
     */
    public static void opCheckMultiSigVerify(Deque<byte[]> stack) {
        opCheckMultiSig(stack);
        byte[] result = stack.pop();
        if (!Utiles.isTruthy(result)) {
            throw new ExceptionsInterpreter("OP_CHECKMULTISIGVERIFY: verificacion multifirma fallida");
        }
    }

    /**
     * Verifica una firma contra una clave publica usando el almacen mock.
     *
     * @param sig la firma
     * @param pubKey la clave publica
     * @return true si el par (firma, clave) esta registrado como valido
     */
    private static boolean mockVerify(byte[] sig, byte[] pubKey) {
        String key = Utiles.bytesToHex(sig) + ":" + Utiles.bytesToHex(pubKey);
        return mockValidSignatures.containsKey(key);
    }

    /**
     * Verifica M firmas contra N claves publicas en orden.
     * Las firmas se verifican secuencialmente contra las claves publicas restantes.
     */
    private static boolean verifyMultiSig(byte[][] sigs, byte[][] pubKeys, int m) {
        int sigIndex = 0;
        int keyIndex = 0;

        while (sigIndex < m && keyIndex < pubKeys.length) {
            if (mockVerify(sigs[sigIndex], pubKeys[keyIndex])) {
                sigIndex++;
            }
            keyIndex++;
            // Si quedan mas firmas que claves, falla
            if (m - sigIndex > pubKeys.length - keyIndex) {
                return false;
            }
        }

        return sigIndex == m;
    }
}
