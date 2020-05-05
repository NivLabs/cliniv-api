#!/bin/sh

if [ -z "$1" ]; then
        echo "Usuário da base nao informado!"
        exit 1
fi

if [ -z "$2" ]; then
        echo "Senha da base noo informada!"
        exit 1
fi

USER=$1
PASS=$2

## Configura a porta d que o servidor irá rodar
export SERVER_PORT=8080
## Configura chave secreta para geração de Token
export JWT_SECRET=#SECRET!@#$132
## Configura tempo que o token deve expirar ex: 86400000 -> 1 dia
export JWT_EXPIRE_MS=86400000

## Configura a String de conexão com o banco
## ex: export URL_DB=jdbc:mysql://[USUARIO]:[SENHA]@[SERVIDOR]:[PORTA]/[BASE]?createDatabaseIfNotExist=TRUE
export URL_DB=jdbc:mysql://${USER}:${PASS}@localhost:3306/devdb?createDatabaseIfNotExist=TRUE

java -jar gestao-de-prontuario-1.0.0.jar -Dreactor.netty.http.server.accessLogEnabled=true
