#!/bin/sh

./run-mariadb.sh

./apache-maven-3.6.3/bin/mvn clean install

## Configura a porta que o servidor irá rodar
export SERVER_PORT=8080
## Configura chave secreta para geração de Token
export JWT_SECRET=#SECRET!@#$132
## Configura tempo que o token deve expirar ex: 86400000 -> 1 dia
export JWT_EXPIRE_MS=86400000

## Configura a String de conexão com o banco
## ex: export URL_DB=jdbc:mysql://[USUARIO]:[SENHA]@[SERVIDOR]:[PORTA]/[BASE]?createDatabaseIfNotExist=TRUE
export URL_DB=jdbc:mariadb://localhost:3306/devdb?user=admin&password=admin

echo "Aguardando start do MariaDB..."
sleep 30s
java -jar target/gestao-de-prontuario.jar
