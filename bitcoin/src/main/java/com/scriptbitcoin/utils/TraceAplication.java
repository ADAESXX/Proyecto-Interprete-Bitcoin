package com.scriptbitcoin.utils;

import java.util.ArrayList;
import java.util.Deque;
import java.util.*;

import com.scriptbitcoin.model.Valor;

/**
 * @author Abigail Escobar
 * Fecha: 12/02/2026    
 * Basicamente registra y muestra el estao de la pila despues de cada instruccion, o sea, "traza" literalmente el funcionamineto
 */
public class TraceAplication {
    /** Lista de entradas de traza acumuladas */
    private final List<String> traceLog;
    private int stepCounter;

    /**
     * Empieza a trazar un nuevo 
     */
    public TraceAplication() {
        this.traceLog = new ArrayList<>();
        this.stepCounter = 0;
    }

    /**
     * Registra el estado de la pila despues de ejecutar un token.
     *
     * @param token el token que se acaba de ejecutar
     * @param stack el estado actual de la pila
     */
    public void log(Valor valor, Deque<byte[]> stack) {
        stepCounter++;
        String stackStr = formatStack(stack);
        String entry = String.format("Step %2d: %-20s | Stack: %s",
                stepCounter, valor.toString(), stackStr);
        traceLog.add(entry);
        System.out.println(entry);
    }

    /**
     * Formatea el contenido de la pila como una cadena legible.
     * Muestra los elementos de la cima a la base en formato hexadecimal.
     *
     * @param stack la pila a formatear
     * @return representacion en cadena de la pila
     */
    private String formatStack(Deque<byte[]> stack) {
        if (stack.isEmpty()) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder("[");
        Iterator<byte[]> it = stack.iterator();
        boolean first = true;
        while (it.hasNext()) {
            if (!first) {
                sb.append(", ");
            }
            sb.append(Utiles.bytesToHex(it.next()));
            first = false;
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * @return la lista completa de entradas de traza
     */
    public List<String> getTraceLog() {
        return new ArrayList<>(traceLog);
    }

    /**
     * @return el numero de pasos registrados
     */
    public int getStepCount() {
        return stepCounter;
    }
    
}