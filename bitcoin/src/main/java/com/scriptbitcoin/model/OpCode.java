package com.scriptbitcoin.model;
/**
 * @author Abigail Escobar
 * Fecha: 12/02/2026
 * Descripción: Enum que representa los diferentes opcodes disponibles en el lenguaje de scripting de Bitcoin. 
 * Cada opcode corresponde a una operación específica que puede ser ejecutada durante la interpretación del script. 
 * Este enum es fundamental para la implementación del intérprete de scripts, ya que permite identificar y ejecutar las operaciones correspondientes a cada opcode durante la evaluación del script.
 * 
 * Dato: Esto no es una clase, sino que un enum, lo que significa que cada valor es una "instancia" única de OpCode.
 * Referencia: https://www.w3schools.com/java/java_enums.asp
 */

public enum OpCode {
    // Literales y empuje de datos
    OP_0, OP_FALSE,
    OP_1, OP_2, OP_3, OP_4, OP_5, OP_6, OP_7, OP_8,
    OP_9, OP_10, OP_11, OP_12, OP_13, OP_14, OP_15, OP_16, OP_PUSHDATA1, OP_PUSHDATA2,

    // Operaciones de pila
    OP_DUP, OP_DROP, OP_SWAP, OP_OVER,

    // Logica y comparacion
    OP_EQUAL, OP_EQUALVERIFY, OP_NOT, OP_BOOLAND, OP_BOOLOR,

    // Aritmetica basica
    OP_ADD, OP_SUB, OP_NUMEQUALVERIFY,
    OP_LESSTHAN, OP_GREATERTHAN, OP_LESSTHANOREQUAL, OP_GREATERTHANOREQUAL,

    // Control de flujo
    OP_IF, OP_NOTIF, OP_ELSE, OP_ENDIF, OP_VERIFY, OP_RETURN,

    // Criptograficas (simuladas)
    OP_SHA256, OP_HASH160, OP_HASH256,

    // Firmas (simuladas)
    OP_CHECKSIG, OP_CHECKSIGVERIFY,

    // Opcionales (avanzado)// Desafio
    OP_CHECKMULTISIG, OP_CHECKMULTISIGVERIFY
}
