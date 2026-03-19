# Proyecto-Interprete-Bitcoin
Proyecto de Algoritmos y Estructuras de Datos que permitirá diseñar e implementar un intérprete de un subconjunto de Bitcoin Script.

# Uso de --trace
--trace se usa en este proyecto para el intérprete imprime cada paso
  - Token actual
  - Estado de la pila después de cada operación
  - Flujo de ejecución
Esto es útil para ver si algo falla y ver exactamente cuál es ese error, también nos permite verificar que si se funciones en el orden correcto (LIFO). Para resumir es como una cajita transparente que te permite ver que es lo que está pasando dentro.

# Compilar/ejecutar
Para correrlo en la terminal escribir:
mvn compile

mvn exec:java "-Dexec.mainClass=com.scriptbitcoin.Main" "-Dexec.args=3 4 OP_ADD 7 OP_EQUAL --trace"
(3 4 OP_ADD 7 OP_EQUAL solo es un ejemplo)

Para compilar archivos test

 mvn clean compile:
 
 mvn test
