package com.scriptbitcoin.operations;
/**
 * @author Paola Merida
 * Fecha:
 * Descripción: implementa las operaciones de control de flujo de Bitcoin Script:
 *  - OP_IF: Ejecuta el bloque de código siguiente si el elemento superior de la pila
 *  - OP_NOTIF: Ejecuta el bloque de código siguiente si el elemento superior de la pila es falso.
 *  - OP_ELSE: Marca el inicio del bloque de código alternativo que se ejecuta si la condición del OP_IF o OP_NOTIF no se cumple.
 *  - OP_ENDIF: Marca el final de un bloque de código iniciado por OP_IF o OP_NOTIF.
 *  - OP_VERIFY: Verifica que el elemento superior de la pila es verdadero, si no lo es, el script falla.
 *  - OP_RETURN: Termina la ejecución del script y marca la transacción como no válida, generalmente se utiliza para almacenar datos en la cadena de bloques sin intención de gastar los bitcoins
 */
public class FlowOperations {
    
}
