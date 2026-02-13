package com.scriptbitcoin.interpreters;
/**
 * @author Abigail Escobar
 * Fecha: 12/2/2026
 * Descripción: Clase que implementa las excepciones específicas para el intérprete de Bitcoin Script, como:
 * Estado: completa
 */

 //Expecipcion perzonalizada para cuando hay errores en la ejecución, ambos son constructores
public class ExceptionsInterpreter extends RuntimeException {
    /**
     * Descripcion del error
     * @param message
     */
    public ExceptionsInterpreter(String message) {
        super(message);
    }

    /**

     * Descripcion del error
     * @param message
     * Descripcion de la causa del error
     * @param cause
     */
    public ExceptionsInterpreter(String message, Throwable cause) {
        super(message, cause);
    }

    
}
