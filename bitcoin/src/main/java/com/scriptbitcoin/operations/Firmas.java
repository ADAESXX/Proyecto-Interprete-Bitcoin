package com.scriptbitcoin.operations;

import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

import com.scriptbitcoin.interpreters.ExceptionsInterpreter;
import com.scriptbitcoin.utils.Utiles;

/**
 * @author Abigail Escobar
 * Fecha de modificacion: 17/03/2026
 * Estado: completa
 * Descripción: Implementa las operaciones de firma de Bitcoin Script:
 *  - OP_CHECKSIG: Verifica que la firma proporcionada corresponde a la clave pública y al mensaje.
 *  - OP_CHECKSIGVERIFY: Verifica que la firma proporcionada corresponde a la clave pública
 */
public class Firmas {
    /**
     * Almacen mock de firmas validas.
     * Clave: representacion de la firma, Valor: clave publica asociada.
     * Permite verificacion O(1) promedio.
     */
    private static final Map<String, String> mockValidSignatures = new HashMap<>();

    private Firmas() {
        // Clase utilitaria, no instanciable
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
     * OP_CHECKSIG: Verifica una firma contra una clave publica.
     * Toma la clave publica (cima) y la firma (segundo) de la pila.
     * Empuja 1 si la firma es valida, 0 si no.
     *
     * @param stack la pila principal
     * @throws ExceptionsInterpreter si la pila tiene menos de 2 elementos
     */
    public static void opCheckSig(Deque<byte[]> stack) {
        if (stack.size() < 2) {
            throw new ExceptionsInterpreter("OP_CHECKSIG: se necesitan al menos 2 elementos");
        }
        byte[] pubKey = stack.pop();
        byte[] sig = stack.pop();
        boolean valid = mockVerify(sig, pubKey);
        stack.push(valid ? Utiles.intToBytes(1) : new byte[0]);
    }


    /**
     * OP_CHECKSIGVERIFY: Igual que OP_CHECKSIG seguido de OP_VERIFY.
     * Si la firma no es valida, falla el script inmediatamente.
     *
     * @param stack la pila principal
     * @throws ExceptionsInterpreter si firma invalida o pila insuficiente
     */
    public static void opCheckSigVerify(Deque<byte[]> stack) {
        if (stack.size() < 2) {
            throw new ExceptionsInterpreter("OP_CHECKSIGVERIFY: se necesitan al menos 2 elementos");
        }
        byte[] pubKey = stack.pop();
        byte[] sig = stack.pop();
        if (!mockVerify(sig, pubKey)) {
            throw new ExceptionsInterpreter("OP_CHECKSIGVERIFY: firma invalida");
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

}
