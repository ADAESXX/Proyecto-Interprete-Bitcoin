package com.scriptbitcoin.operations;

import java.util.Deque;

import com.scriptbitcoin.interpreters.ExceptionsInterpreter;
import com.scriptbitcoin.utils.Utiles;

/**
 * @author Paola Merida
 * @author Abigail Escobar
 * Fecha de modificación: 18/03/2026
 * Estado: completa
 * Descripción: implementa las operaciones de control de flujo de Bitcoin Script:
 *  - OP_IF: Ejecuta el bloque de código siguiente si el elemento superior de la pila
 *  - OP_NOTIF: Ejecuta el bloque de código siguiente si el elemento superior de la pila es falso.
 *  - OP_ELSE: Marca el inicio del bloque de código alternativo que se ejecuta si la condición del OP_IF o OP_NOTIF no se cumple.
 *  - OP_ENDIF: Marca el final de un bloque de código iniciado por OP_IF o OP_NOTIF.
 *  - OP_VERIFY: Verifica que el elemento superior de la pila es verdadero, si no lo es, el script falla.
 *  - OP_RETURN: Termina la ejecución del script y marca la transacción como no válida, generalmente se utiliza para almacenar datos en la cadena de bloques sin intención de gastar los bitcoins
 */
public class FlowOperations {
    private FlowOperations() {
        // Clase utilitaria, no instanciable
    }

    /**
     * OP_IF: Evalua el elemento superior de la pila principal.
     * Si es truthy, ejecuta el bloque siguiente. Si es falsy, salta al OP_ELSE o OP_ENDIF.
     *
     * @param mainStack la pila principal (se hace pop del valor de condicion)
     * @param conditionStack la pila de condiciones
     * @throws ExceptionsInterpreter si la pila principal esta vacia
     */
    public static void opIf(Deque<byte[]> mainStack, Deque<Boolean> conditionStack) {
        if (mainStack.isEmpty()) {
            throw new ExceptionsInterpreter("OP_IF: pila vacia");
        }
        byte[] top = mainStack.pop();
        boolean condition = Utiles.isTruthy(top);
        conditionStack.push(condition);
    }

    /**
     * OP_NOTIF: Inverso de OP_IF. Si el elemento superior es falsy, ejecuta el bloque.
     *
     * @param mainStack la pila principal
     * @param conditionStack la pila de condiciones
     * @throws ExceptionsInterpreter si la pila principal esta vacia
     */
    public static void opNotIf(Deque<byte[]> mainStack, Deque<Boolean> conditionStack) {
        if (mainStack.isEmpty()) {
            throw new ExceptionsInterpreter("OP_NOTIF: pila vacia");
        }
        byte[] top = mainStack.pop();
        boolean condition = !Utiles.isTruthy(top);
        conditionStack.push(condition);
    }

    /**
     * OP_ELSE: Invierte la condicion actual del bloque condicional.
     *
     * @param conditionStack la pila de condiciones
     * @throws ExceptionsInterpreter si la pila de condiciones esta vacia
     */
    public static void opElse(Deque<Boolean> conditionStack) {
        if (conditionStack.isEmpty()) {
            throw new ExceptionsInterpreter("OP_ELSE: sin OP_IF correspondiente");
        }
        boolean current = conditionStack.pop();
        conditionStack.push(!current);
    }

    /**
     * OP_ENDIF: Termina el bloque condicional actual.
     *
     * @param conditionStack la pila de condiciones
     * @throws ExceptionsInterpreter si la pila de condiciones esta vacia
     */
    public static void opEndIf(Deque<Boolean> conditionStack) {
        if (conditionStack.isEmpty()) {
            throw new ExceptionsInterpreter("OP_ENDIF: sin OP_IF correspondiente");
        }
        conditionStack.pop();
    }

    /**
     * OP_VERIFY: Verifica que el elemento superior de la pila es truthy.
     * Si es falsy, falla el script inmediatamente.
     * Pila: [..., a] -> [...] (falla si a es falsy)
     *
     * @param mainStack la pila principal
     * @throws ExceptionsInterpreter si el valor es falsy o la pila esta vacia
     */
    public static void opVerify(Deque<byte[]> mainStack) {
        if (mainStack.isEmpty()) {
            throw new ExceptionsInterpreter("OP_VERIFY: pila vacia");
        }
        byte[] top = mainStack.pop();
        if (!Utiles.isTruthy(top)) {
            throw new ExceptionsInterpreter("OP_VERIFY: el valor en la cima es falso");
        }
    }

    /**
     * OP_RETURN: Marca la salida como no gastable. Falla el script inmediatamente.
     *
     * @throws ExceptionsInterpreter siempre (OP_RETURN siempre falla)
     */
    public static void opReturn() {
        throw new ExceptionsInterpreter("OP_RETURN: script marcado como no gastable");
    }
}
