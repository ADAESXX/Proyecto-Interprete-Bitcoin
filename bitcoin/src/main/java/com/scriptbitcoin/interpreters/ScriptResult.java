package com.scriptbitcoin.interpreters;
import java.util.Deque;
/**
 * @author Abigail Escobar
 * Fecha: 12/2/2026
 * Descripción: Clase que representa el resultado de la ejecución de un script, indicando si la validación fue exitosa o no y el estado de la pila.
 * Estado: completa
 */
public class ScriptResult {
    //variable para indicar si la ejecucion fue exitosa
    private final boolean exito;
    //mensaje que describe el resultado de la ejecución
    private final String mensaje;
    //pila final después de la ejecución del script
    private final Deque<String> finalStack;

    //Constructor
    public ScriptResult(boolean exito, String mensaje, Deque<String> finalStack) {
        this.exito = exito;
        this.mensaje = mensaje;
        this.finalStack = finalStack;
    }

    //metodos

    public boolean isExito() {
        return exito;
    }

    public String getMensaje() {
        return mensaje;
    }

    public Deque<String> getFinalStack() {
        return finalStack;
    }
}
