#!/bin/sh
export DB_VERSION=10.10

echo "Baixando a versão ${DB_VERSION} do Maria DB..."
docker pull mariadb:${DB_VERSION}

echo "Matando processos existentes com o nome de MariaDB..."
docker ps -q -f name=MariaDB | xargs -r docker kill

echo "Removendo container antigo do MariaDB ..."
docker rm MariaDB

echo "Rodando MariaDB atualizado com volume persistente..."
docker run -p 3306:3306/tcp --name MariaDB -e MYSQL_USER=admin -e MYSQL_PASSWORD=admin -e MYSQL_DATABASE=cliniv -e MYSQL_ROOT_PASSWORD=root -v $HOME/mariadb-data:/var/lib/mysql -d mariadb:${DB_VERSION}