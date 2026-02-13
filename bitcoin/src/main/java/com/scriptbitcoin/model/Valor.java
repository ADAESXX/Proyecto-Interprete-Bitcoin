package com.scriptbitcoin.model;
import java.util.Arrays;
/**
 * @author Abigail Escobar
 * Fecha: 12/02/2026
 * Descripción: Clase que representa un valor en el lenguaje de scripting de Bitcoin.
 */
public class Valor {
    //atributo
    private final ValorTipo type;
    private final OpCode opCode;
    private final byte[] data;

    //constructores
    public Valor(OpCode opCode) {
        //en este constructor el tipo de los valores es OPCODE, por lo que se asigna el opcode correspondiente y se deja el campo de datos como null
        this.type = ValorTipo.OPCODE;
        this.opCode = opCode;
        this.data = null;
    }
    public Valor(byte[] data) {
        //en este constructor el tipo de los valores es DATA_LITERAL, por lo que se asigna el tipo correspondiente y se deja el campo de opcode como null
        this.type = ValorTipo.DATA_LITERAL;
        this.opCode = null;
        this.data = data != null ? Arrays.copyOf(data, data.length) : null; // Copia defensiva para evitar mutabilidad (esto no afecta los datos originales pasados al constructor)
    }
    //metodos de acceso

    public ValorTipo getType() {
        return type;
    }
    public OpCode getOpCode() {
        return opCode;
    }
    public byte[] getData() {
        //basicamente regresa una copia del array si no es null, para evitar que el array original pueda ser modificado desde fuera de la clase
        return data != null ? Arrays.copyOf(data, data.length) : null; // Copia defensiva para evitar mutabilidad (esto no afecta los datos originales almacenados en el objeto)
    }

    @Override
    public String toString() {
        if (type == ValorTipo.OPCODE) {
            return opCode.name();
        } else {
            return "DATA[" + bytesToHex(data) + "]";
        }
    }
    private String bytesToHex(byte[] bytes) {
        //basicamente si el array es null o esta vacio no regresa nada
        //pero si tiene datos, recorre cada byte y lo convierte a su representación decimal
        //esto lo hace paa poder mostrar los datos de manera "legibloe" en el toString, ya que los datos son bytes y no se pueden mostrar directamente como texto
        if(bytes == null || bytes.length == 0) return "";
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            //el format %02x convierte el byte a su representación hexadecimal de dos dígitos, y el & 0xFF asegura que se trate como un valor sin signo (esto es importante para evitar problemas con bytes negativos en Java)
            sb.append(String.format("%02x", b & 0xFF));
        }
        return sb.toString();
    }


}
