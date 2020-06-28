#!/bin/sh

invalid_arguments_error() {
	echo "";
	echo "";
	echo "## Para iniciar a aplicação em produção, deves informar todos os argumentos necessários..."
	echo "## ./start.sh [HOST] [BASE] [USUARIO] [SENHA]"
	echo "## ex: ./start.sh localhost devdb admin 123456"
	echo "";
	echo "";
	exit 1
}

if [ -z "$1" ]; then
        echo "Host da base nao informado!"
		invalid_arguments_error
fi

if [ -z "$2" ]; then
        echo "Nome da base nao informada!"
		invalidArgumentsError
fi

if [ -z "$3" ]; then
        echo "Usuário da base nao informado!"
		invalid_arguments_error
fi

if [ -z "$4" ]; then
        echo "Senha da base nao informada!"
		invalid_arguments_error
fi


HOST=$1
BASE=$2
USER=$3
PASS=$4

## Configura a porta que o servidor irá rodar
export SERVER_PORT=8080
## Configura chave secreta para geração de Token
export JWT_SECRET=#SECRET!@#$132
## Configura tempo que o token deve expirar ex: 86400000 -> 1 dia
export JWT_EXPIRE_MS=86400000

## Configura a String de conexão com o banco
## ex: export URL_DB=jdbc:mysql://[USUARIO]:[SENHA]@[SERVIDOR]:[PORTA]/[BASE]?createDatabaseIfNotExist=TRUE
export URL_DB=jdbc:mariadb://${HOST}:3306/${BASE}?user=${USER}&password=${PASS}

java -jar target/gestao-de-prontuario.jar
