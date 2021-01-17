#!/bin/sh
## Configura a porta que o servidor irá rodar
export SERVER_PORT=8080
## Configura chave secreta para geração de Token
export JWT_SECRET=#CHAVE_SECRETA!@#$132
## Configura tempo que o token deve expirar ex: 86400000 -> 1 dia
export JWT_EXPIRE_MS=86400000



## ======== Configurações do banco ======== ##
## Host em que o seu banco está rodando
export HOST=localhost
## Porta em que o seu banco está rodando
export PORT=3306
## Nome do banco de dados da aplicação
export BASE=devdb
## Usuário com privilégios máximos do seu banco (Tem que ter privilégio total para que a aplicação realize a criação da base)
export USER=admin
## Senha de acesso do banco de dados
export PASS=admin
## ======== Configurações do banco ======== ##

echo 'Configurações de conexão com o banco de dados...'
echo 'HOST: ' ${HOST}
echo 'PORTA: ' ${PORT}
echo 'BANCO: ' ${BASE}
echo 'USUARIO: ' ${USER}

## Configura a String de conexão com o banco
## ex: export DB_CONFIG=jdbc:mysql://[USUARIO]:[SENHA]@[SERVIDOR]:[PORTA]/[BASE]?createDatabaseIfNotExist=TRUE
export DB_CONFIG='jdbc:mariadb://'${HOST}':'${PORT}'/'${BASE}'?user='${USER}'&password='${PASS}

jdk-15/bin/java -jar api/gestao-de-prontuario.jar
