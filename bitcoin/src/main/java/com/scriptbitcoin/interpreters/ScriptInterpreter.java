package com.scriptbitcoin.interpreters;

import java.util.Deque;
import java.util.List;

/**
 * @author Abigail Escobar
 * Fecha: 12/2/2026 
 * Descripcion: clase encargada de evaluar los scripts de bitcoin
 * Estado: en desarrollo
 */
public class ScriptInterpreter {
    //pila principal para la ejecución del script
    private Deque<String> stack;
    //pila condicional (para manejar IF, ELSE, ENDIF)
    private Deque<String> stackcondicional;
    //verificación del estado de trace
    private boolean trace;

    /**
     * 
     * @param script
     * @return
     */
    public ScriptResult execute(List<String> valores, boolean trace) {
        this.trace = trace;
        //procesa cada operación y manipula las pilas 
        return new ScriptResult(true, "Ejecución exitosa", stack);
    }


}
