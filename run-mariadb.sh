#!/bin/sh

echo "Baixando a última versão do Maria DB..."
docker pull mariadb:latest

echo "Matando processos existentes com o nome de MariaDB..."
docker kill MariaDB

echo "Removendo imagem do MariaDB ..."
docker rm MariaDB

echo "Rodando MariaDB Atualizado..."
docker run  -p 3306:3306/tcp --name MariaDB -e MYSQL_USER=admin -e MYSQL_PASSWORD=admin -e MYSQL_DATABASE=cliniv -e MYSQL_ROOT_PASSWORD=root -d mariadb:latest