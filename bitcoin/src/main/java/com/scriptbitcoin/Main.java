package com.scriptbitcoin;

import java.util.List;

import com.scriptbitcoin.interpreters.ScriptInterpreter;
import com.scriptbitcoin.interpreters.ScriptResult;
import com.scriptbitcoin.model.Valor;
import com.scriptbitcoin.operations.Desafio;
import com.scriptbitcoin.operations.Firmas;
import com.scriptbitcoin.utils.ScriptValores;
import com.scriptbitcoin.utils.Utiles;

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
        System.out.println("=== ARGS RECEIVED ===");
        System.out.println("Total args: " + args.length);
        for (int i = 0; i < args.length; i++) {
            System.out.println("  args[" + i + "] = '" + args[i] + "'");
        }
        
        if (args.length < 1) {
            System.out.println("No arguments provided!");
            return;
        }
    
        StringBuilder scriptBuilder = new StringBuilder();

        for (String arg : args) {
            if (!arg.equals("--trace")) {
                scriptBuilder.append(arg).append(" ");
            }
        }

        scriptString = scriptBuilder.toString().trim();

        System.out.println("Script string: '" + scriptString + "'");
        System.out.println("Script length: " + scriptString.length());
        System.out.println("====================");
    
        
        ///////////// Ejemplo de uso especificado en la guía
        if (args.length < 1) {
            System.out.println("Uso de --trace para registrar la ejecución paso a paso.");
            System.exit(1);
            return;
        }
        

        for (int i = 1; i < args.length; i++) {
            if ("--trace".equals(args[i])) {
                traceEnabled = true;
            }
        }

        try {
            // Tokenizar el script
            ScriptValores tokenizer = new ScriptValores();
            List<Valor> tokens = tokenizer.tokenize(scriptString);

            Firmas.clearSignatures();
            Firmas.registerValidSignature(
                Utiles.intToBytes(7),
                Utiles.intToBytes(7)
            );

            Desafio.clearSignatures();

            Desafio.registerValidSignature(
                Utiles.intToBytes(1),
                Utiles.intToBytes(1)
            );

            Desafio.registerValidSignature(
                Utiles.intToBytes(2),
                Utiles.intToBytes(2)
            );
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
