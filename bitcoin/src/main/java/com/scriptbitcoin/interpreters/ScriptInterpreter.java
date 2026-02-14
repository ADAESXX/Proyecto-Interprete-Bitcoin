package com.scriptbitcoin.interpreters;
/**
 * @author Abigail Escobar
 * Fecha: 12/2/2026 - 13/2/2026
 * Descripcion: clase encargada de evaluar los scripts de bitcoin
 */
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import com.scriptbitcoin.model.OpCode;
import com.scriptbitcoin.model.Valor;
import com.scriptbitcoin.model.ValorTipo;
import com.scriptbitcoin.operations.Arithmetic;
import com.scriptbitcoin.operations.Crypto;
import com.scriptbitcoin.operations.Firmas;
import com.scriptbitcoin.operations.Literales;
import com.scriptbitcoin.operations.Logic;
import com.scriptbitcoin.operations.StackOperations;
import com.scriptbitcoin.utils.TraceAplication;
import com.scriptbitcoin.utils.Utiles;




/**
 * @author Abigail Escobar
 *         Fecha: 12/2/2026 - 13/2/2026
 *         Descripcion: clase encargada de evaluar los scripts de bitcoin
 *         Estado: en desarrollo
 */
public class ScriptInterpreter {
    // pila principal para la ejecución del script
    private Deque<byte[]> stack;
    // pila condicional (para manejar IF, ELSE, ENDIF)
    private Deque<Boolean> stackcondicional;
    //Logger para el modo trace
    private TraceAplication traceLogger;
    // verificación del estado de trace
    private boolean trace;

    /**
     * @param valores son los valores del script a ejecutar
     * @param trace true para activar el modo --trace
     */
    public ScriptResult execute(List<Valor> valores, boolean trace) {
        //Inicializar variables
        stack = new ArrayDeque<>();
        stackcondicional = new ArrayDeque<>();
        this.trace = trace;
        // Inicializar el logger de trace si el modo trace está habil
        traceLogger = trace ? new TraceAplication() : null;

        
        try {
            // Ejecutar cada valor del script
            for (int i = 0; i < valores.size(); i++) {
                Valor valor = valores.get(i);
                if (!isExecuting()){
                    handleNoneExecutingBranch(valor);

                    // Loguear el estado actual de la pila antes de ejecutar el valor
                    if (trace) {
                        traceLogger.log(valor, stack);
                    }
                    continue;
                }
    
                // Si el valor es un literal de datos, se empuja directamente a la pila, de lo contrario se despacha el opcode para su ejecución
                if (valor.getType() == ValorTipo.DATA_LITERAL){
                    stack.push(valor.getData());
                }
                else{
                    dispatchOpCode(valor.getOpCode());
                }
                if (trace){
                    traceLogger.log(valor, stack);
                }
            }
            // manejo de errores específicos para control de flujo, como IF sin ENDIF o ELSE sin IF
            if(!stackcondicional.isEmpty()){
                return new ScriptResult(false, "Error: falta un OP_ENDIF para cerrar una rama condicional", stack);
            }
            if (stack.isEmpty()){
                return new ScriptResult(false, "Error: pila vacía", stack);
            }
            //temporal//////////////////////////
            byte[] top = stack.peek();
            System.out.println("DEBUG top int = " + Utiles.bytesToInt(top));

            if (!Utiles.isTruthy(stack.peek())){
                return new ScriptResult(false, "Cima del stack falsa", stack);
            }
            // Si se llega al final del script sin errores, se devuelve un resultado exitoso con el estado final de la pila
            return new ScriptResult(true, "Script ejecutado correctamente", stack);

        }
         catch (ExceptionsInterpreter e) {
            if (trace) {
                System.out.println("Error: " + e.getMessage());

            }
            return new ScriptResult(false, e.getMessage(), stack);
        }
    }

    //metodos utiles para 
    private boolean isExecuting() {
        for (Boolean condition : stackcondicional) {
            if (!condition) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param valor
      * Maneja los valores que se encuentran en ramas condicionales que no se están ejecutando
      * Este método es crucial para asegurar que el script se ejecute correctamente
     */
    private void handleNoneExecutingBranch(Valor valor) {
        //ignorar cualquier valor que no sea un opcode mientras no se esté ejecutando
        if (valor.getType() != ValorTipo.OPCODE) {
            return;
        }
        OpCode opCode = valor.getOpCode();
        //manejar opcodes
        switch (opCode) {

            //casos para control de flujo
            case OP_IF:
                stackcondicional.push(false); // Iniciar una nueva rama condicional
                break;

            case OP_NOTIF:
                stackcondicional.push(false); // Iniciar una nueva rama condicional
                break;

            case OP_ELSE:
                if (stackcondicional.isEmpty()) {
                    // Manejar error: ELSE sin IF correspondiente
                    throw new IllegalStateException("OP_ELSE sin OP_IF correspondiente");
                }
                // Cambiar la condición para la rama ELSE en caso de que todos los padres sean verdaderos
                Boolean top = stackcondicional.pop();
                boolean parentsCondition = true;
                //Verificar si los padres están en una rama ejecutable
                for (Boolean condition : stackcondicional) {
                    if (!condition) {
                        parentsCondition = false;
                        break;
                    }
                }
                //si cumplen las condiciones para ejecutar la rama ELSE, cambiar la condición a true, de lo contrario mantenerla como false
                if (parentsCondition) {
                    stackcondicional.push(!top); // Cambiar la condición para la rama ELSE
                } else {
                    stackcondicional.push(top); // Mantener la condición como false si algún padre es false
                }
                break;

            case OP_ENDIF:
                if (!stackcondicional.isEmpty()) {
                    stackcondicional.pop(); // Finalizar la rama condicional actual
                }
                else {
                    // Manejar error: ENDIF sin IF correspondiente
                    throw new IllegalStateException("OP_ENDIF sin OP_IF correspondiente");
                }
                break;
            default:
                // Ignorar otros opcodes mientras no se esté ejecutando
                break;
        }
    }


    /**
     * @param opCode
     * //si el opCode no soporta la ejecución, se lanza una excepción indicando que el opcode no es soportado
     * @throws EceptionsInterpreter
     * Método encargado de despachar la ejecución de cada opcode a su función correspondiente
     * Este método asegura ue cada opcode se ejecute correctamente según su lógica específica
     * Cada una envía la pila a la función correspondiente para que esta pueda manipularla según sea necesario
     */
    private void dispatchOpCode(OpCode opCode) {
        // Implementar la lógica para ejecutar cada opcode
        switch (opCode) {
            //////////LITERALES///////////
            case OP_0:
            case OP_FALSE:
            case OP_1:
            case OP_2:
            case OP_3:
            case OP_4:
            case OP_5:
            case OP_6:
            case OP_7:
            case OP_8:
            case OP_9:
            case OP_10:
            case OP_11:
            case OP_12:
            case OP_13:
            case OP_14:
            case OP_15:
            case OP_16:
                Literales.executeLiteral(opCode, stack);
                break;


            ///////////OPERACIONES DEL STACK///////////

            case OP_DUP:
                StackOperations.opDup(stack);
                break;
            case OP_DROP:
                StackOperations.opDrop(stack);
                break;
            case OP_SWAP:
                StackOperations.opSwap(stack);
                break;
            case OP_OVER:
                StackOperations.opOver(stack);
                break;


            ////////////OPERACIONES LÓGICAS///////////
            case OP_EQUAL:
                Logic.opEqual(stack);
                break;
            case OP_EQUALVERIFY:
                Logic.opEqualVerify(stack);
                break;
            case OP_NOT:
                Logic.opNot(stack);
                break;
            case OP_BOOLAND:
                Logic.opBoolAnd(stack);
                break;
            case OP_BOOLOR:
                Logic.opBoolOr(stack);
                break;


            //////////OPERACIONES ARITMÉTICAS///////////
            case OP_ADD:
                Arithmetic.opAdd(stack);
                break;
            case OP_SUB:
                //Arithmetic.opSub(stack);
                break;
            case OP_NUMEQUALVERIFY:
                //Arithmetic.opNumEqualVerify(stack);
                break;
            case OP_LESSTHAN:
                //Arithmetic.opLessThan(stack);
                break;
            case OP_GREATERTHAN:
                Arithmetic.opGreaterThan(stack);
                break;
            case OP_LESSTHANOREQUAL:
                //Arithmetic.opLessThanOrEqual(stack);
                break;
            case OP_GREATERTHANOREQUAL:
                //Arithmetic.opGreaterThanOrEqual(stack);
                break;


            /////////CONTROL DE FLUJO///////////
            case OP_IF:
                //FlowOperations.opIf(stack, stackcondicional);
                break;
            case OP_NOTIF:
                //FlowOperations.opNotIf(stack, stackcondicional);
                break;
            case OP_ELSE:
                //FlowOperations.opElse(stackcondicional);
                break;
            case OP_ENDIF:
                //FlowOperations.opEndIf(stackcondicional);
                break;
            case OP_VERIFY:
                //FlowOperations.opVerify(stack);
                break;
            case OP_RETURN:
                //FlowOperations.opReturn();
                break;


            //////////////CRIPTOGRAFIA///////////
            case OP_SHA256:
                Crypto.opSha256(stack);
                break;
            case OP_HASH160:
                Crypto.opHash160(stack);
                break;
            case OP_HASH256:
                Crypto.opHash256(stack);
                break;
            /////////////FIRMAS///////////
            case OP_CHECKSIG:
                Firmas.opCheckSig(stack);
                break;
            case OP_CHECKSIGVERIFY:
                //Firmas.opCheckSigVerify(stack);
                break;
            
            /////////DESAFIO EXTRA: MULTISIG///////////
            case OP_CHECKMULTISIG:
                //Firmas.opCheckMultiSig(stack);
                break;
            case OP_CHECKMULTISIGVERIFY:
                //Firmas.opCheckMultiSigVerify(stack);
                break;
            default:
                throw new UnsupportedOperationException("Opcode no soportado: " + opCode);
        }
    }
    //devuelve el estado actual de la pila, útil para pruebas y para el modo trace
    public Deque<byte[]> getStack() {
        return stack;
    }
}
