package com.scriptbitcoin.operations;
/**
 * @author Raquel Tejada
 * Fecha:
 * Descripción: implementa las operaciones de literales de Bitcoin Script y el empuje de esos datos:
 *  - OP_0/OP_FALSE: Empuja un valor vacío a la pila.
 *  - OP_1: Empuja el valor 1 a la pila.
 *  - OP_2 a OP_16: Empuja el valor numérico correspondiente (2 a 16) a la pila.
 *  - OP_PUSHDATA1: Indica que el siguiente byte contiene el número de bytes a empujar a la pila.
 *  - OP_PUSHDATA2: Indica que los siguientes dos bytes contienen el número de bytes a empujar a la pila.
 */
public class Literales {
    
}
