# Proyecto-Interprete-Bitcoin
Proyecto de Algoritmos y Estructuras de Datos que permitirá diseñar e implementar un intérprete de un subconjunto de Bitcoin Script

Para correrlo en la terminal escribir:
mvn compile
mvn exec:java "-Dexec.mainClass=com.scriptbitcoin.Main" "-Dexec.args=3 4 OP_ADD 7 OP_EQUAL --trace"
(3 4 OP_ADD 7 OP_EQUAL solo es un ejemplo)
