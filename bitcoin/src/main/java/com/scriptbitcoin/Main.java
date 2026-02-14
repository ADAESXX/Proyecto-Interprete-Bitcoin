package com.scriptbitcoin;

import java.util.List;

import com.scriptbitcoin.interpreters.ScriptInterpreter;
import com.scriptbitcoin.interpreters.ScriptResult;
import com.scriptbitcoin.model.Valor;
import com.scriptbitcoin.utils.ScriptValores;

/**
 * @author Abigail Escobar
 * Fecha: 13/02/2026
 * Descripción: Clase principal del proyecto, que sirve como punto de entrada para la ejecución del interprete de Bitcoin Script. 
 */

public class Main {
    public static void main(String[] args) {
        System.out.println("¡Bienvenido al intérprete de Bitcoin Script :) !");

        String scriptString;
        boolean traceEnabled = false;

        
        ///////////// Ejemplo de uso especificado en la guía
        if (args.length < 1) {
            System.out.println("Uso de --trace para registrar la ejecución paso a paso.");
            System.out.println("Ejemplo:  \"1 2 OP_ADD 5 OP_GREATERTHAN\"");
            System.exit(1);
            return;
        }
        //Ejemplo funcional creado con chat :)
        /* if (args.length < 1) {
            scriptString = "3 4 OP_ADD 7 OP_EQUAL";
            System.out.println("Ejecutando ejemplo por defecto: " + scriptString);
        } 
        else {
            scriptString = args[0];
        } */
       scriptString = args[0];

        for (int i = 1; i < args.length; i++) {
            if ("--trace".equals(args[i])) {
                traceEnabled = true;
            }
        }

        try {
            // Tokenizar el script
            ScriptValores tokenizer = new ScriptValores();
            List<Valor> tokens = tokenizer.tokenize(scriptString);

            // Ejecutar el script
            ScriptInterpreter interpreter = new ScriptInterpreter();
            ScriptResult result = interpreter.execute(tokens, traceEnabled);

            // Imprimir resultado
            if (result.isExito()) {
                System.out.println("Result: VALID");
            } else {
                System.out.println("Result: INVALID (" + result.getMensaje()+ ")");
            }

            System.exit(result.isExito() ? 0 : 1);

        } catch (IllegalArgumentException e) {
            System.out.println("Error de tokenizacion: " + e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
            System.exit(1);
        }
    }
}
