[![CircleCI](https://circleci.com/gh/viniciosarodrigues/gestao-de-prontuario/tree/master.svg?style=shield)](https://circleci.com/gh/viniciosarodrigues/gestao-de-prontuario/tree/master)

# FT - Gestão de Prontuário - API
O FT - Gestão de Prontuário - API (FTGP-API) é uma API Rest OpenSource criada inicialmente como doação para hospitais que não possuem nenhuma solução de automação no processo de prontuário. O FTGP-API é integrado com um client baseado em Angular, o [FTGP-UI](https://github.com/viniciosarodrigues/gestao-de-prontuario-ui).


Atualmente encontra-se na versão 1.0.0

Funcionalidades da API
- Cadastro de Pacientes
- Cadastro de Responsáveis (Enfermeiro, Médico, Técnico, etc)
- Cadastro de Eventos (Consultas, exames, prescriçoes, etc)
- Armazenamento de documentos (Resultado de exames, radiografia, tumografia, etc)
- Histórico de visita em forma de timeline
- Anamnese

# Como densenvolver?
Para contribuir com o projeto, existem alguns requisítos mínimos de configurações, são eles?
- Java 11 + **(Obrigatório)**
- MySql Server (Community) **(Obrigatório)**
- Lombok 1.16.0 + **(Obrigatório)**
- Eclipse ou STS **(Obrigatório)**
- No caso do Eclipse, usar o plugin do Spring Tools *(Opcional)*

## Configurando o Lombok
Caso não tenha o Lombok, já existe uma dependência do mesmo no projeto, basta importar o projeto no **Eclipse/STS** e aguardar que a dependẽncia seja baixada. Com o Jar do Lombok baixado no repositório local do Maven, siga o passo-a-passo logo abaixo:

- Navegar até o repositório do Lombok
-`C:\users\{usuario}\.m2\repository\org\projectlombok\lombok\{versao_baixada}`
- Executar o jar do lombok
-`java -jar lombok.{versao}.jar`
- Selecionar o Eclipse/STS que irá receber o Lombok
- Clicar em Install/Update
- Sair do instalador
- Abrir a IDE novamente
- Rebuild no projeto

## Como rodar a API?
Por ser um projeto SpringBoot, não se faz necessário o uso de nenhum servidor externo, pois o framework já disponibiliza um Tomcat embarcado, desta forma basta clicar com o botão direito na raiz do projeto, **Run As** -> **SpringBoot App**.
Também pode rodar apartir do **ApplicationMain** encontrado no pacote base da aplicação.

## Como contribuir
Caso queira contribuir, basta realizar um fork do repositório, fazer a implementação desejada, **criar uma issue de push** e realizar pull request para a **master**.

## Contato
Qualquer dúvida ou sugestão, favor enviar para o e-mai *viniciosarodrigues@gmail.com* ou enviar mensagem privada pelo próprio GitGub.
