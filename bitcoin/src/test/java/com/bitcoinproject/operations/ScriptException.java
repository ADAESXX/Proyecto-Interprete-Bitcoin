package com.bitcoinproject.operations;

/**
 * @author Abigail Escobar
 * Fecha: 13/02/2026
 * Descripción: Excepción personalizada para errores en la ejecución de scripts de Bitcoin
 */
public class ScriptException extends RuntimeException {
    
    public ScriptException(String message) {
        super(message);
    }
    
    public ScriptException(String message, Throwable cause) {
        super(message, cause);
    }
}
