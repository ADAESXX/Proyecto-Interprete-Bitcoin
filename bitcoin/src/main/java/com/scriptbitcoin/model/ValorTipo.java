package com.scriptbitcoin.model;
/**
 * @author Abigail Escobar
 * Fecha: 12/02/2026
 * Descripción: Enum que representa los tipos de valores que pueden existir en el lenguaje de scripting de Bitcoin.
 */

public enum ValorTipo {
    //inicializacion de los tipos de valores que pueden existir en el lenguaje de scripting de Bitcoin, que son básicamente dos: 
    //OPCODE para representar las operaciones y DATA_LITERAL para representar los datos literales que se pueden empujar a la pila durante la ejecución del script.
    OPCODE,
    DATA_LITERAL
}
