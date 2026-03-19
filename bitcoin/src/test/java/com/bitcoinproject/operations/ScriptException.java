/**
 * @author Paola Itzep 
 * Fecha: 18/03/2026
 */

package com.bitcoinproject.operations;

public class ScriptException extends RuntimeException {

    public ScriptException(String message) {
        super(message);
    }

    public ScriptException(String message, Throwable cause) {
        super(message, cause); // 
    }
}
