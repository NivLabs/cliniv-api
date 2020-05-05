<h1 align="center">
  <a href="https://gestao-prontuario.herokuapp.com">
    <img alt="FTGP Logo" src="./readme/logo.png" width="350px" />
  </a>
</h1>
<h2 align="center">
  FT - Gestão de Prontuário - API
</h2>

O FT - Gestão de Prontuário - API (FTGP-API) é uma API Rest OpenSource criada inicialmente como doação para hospitais que não possuem nenhuma solução de automação no processo de prontuário. O FTGP-API é integrado com um client baseado em Angular, o [FTGP-UI](https://github.com/viniciosarodrigues/gestao-de-prontuario-ui).

<p align="center">
    <a href="https://github.com/viniciosarodrigues/">
        <img alt="Made by Vinícios Rodrigues" src="https://img.shields.io/badge/made%20by-Vin%C3%ADcios%20Rodrigues-brightgreen" />
    </a>
    <img alt="Last Commit" src="https://img.shields.io/github/last-commit/viniciosarodrigues/gestao-de-prontuario" />
    <img alt="Contributors" src="https://img.shields.io/github/contributors/viniciosarodrigues/gestao-de-prontuario" />
    <img alt="License" src="https://img.shields.io/badge/license-MIT-%2304D361" />
</p>

Atualmente encontra-se na versão 1.0.0
[Swagger](https://gestao-prontuario.herokuapp.com/swagger-ui.html)

---

## Índice

<ul>
  <li><a href="#funcionalidades-da-api">Funcionalidades da API</a></li>
  <li><a href="#mínimo-para-rodar">Mínimo para rodar</a></li>
  <li><a href="#projeto-lombok">Projeto Lombok</a></li>
  <li><a href="#mysql-ou-mariadb">Configurando banco de dados</a></li>
  <li><a href="#como-rodar-a-api">Como rodar a API?</a></li>
  <li><a href="#como-contribuir">Como contribuir?</a></li>
  <li><a href="#contato">Contato</a></li>
  <li><a href="#-license">License</a></li>
</ul>

---

## Funcionalidades da API

- [x] Cadastro de Pacientes
- [x] Cadastro de Responsáveis (Enfermeiro, Médico, Técnico, etc)
- [x] Cadastro de Eventos (Consultas, exames, prescriçoes, etc)
- [x] Cadastro de Usuários
- [x] Controle de Acesso
- [ ] Armazenamento de documentos (Resultado de exames, radiografia, tumografia, etc)
- [x] Histórico de visita em forma de timeline
- [ ] Relatórios
- [x] Paciente não identificado
- [ ] Fluxo padrão para atendimento ambulatorial
- [ ] Fluxo padrão para atendimento emergencial
- [ ] Cadastro de medicamentos 

---

## Mínimo para rodar

Para rodar o projeto, existem alguns requisítos mínimos de configurações, são eles:

- Java 11 + **(Obrigatório)**
- MySql Server ou MariaDB Server **(Obrigatório)**
- Lombok 1.16.0 + **(Obrigatório)**
- Eclipse ou STS **(Obrigatório)**
- No caso do Eclipse, usar o plugin do Spring Tools *(Opcional)*

---

## Projeto Lombok

### O que é o Lombok?
O [Lombok](https://projectlombok.org/) é uma biblioteca Java focada em produtividade e redução de código boilerplate que por meio de anotações adicionadas ao nosso código ensinamos o compilador (maven ou gradle) durante o processo de compilação a criar código Java.

### Por qual motivo devo configurar minha IDE para usar o Lombok?
Este projeto foi desenvolvido utilizando o Lombok para criação de POJOs e DTOs, por este motivos, faz-se necessário configurar a sua IDE.

### Como configurar?
Obs: Este processo só é válido para IDEs baseadas em [Eclipse Project](https://www.eclipse.org/) (Eclipse IDE e STS).

##### Passo 1

- Navegar via terminal para o repositório do Lombok
- Windows -> cd `C:\users\{usuario}\.m2\repository\org\projectlombok\lombok\{versao_baixada}`
- Linux e Mac -> cd `~/.m2/repository/org/projectlombok/lombok/{versao_baixada}`

##### Passo 2

- Executar o jar do lombok utilizando o comando `java -jar lombok.{versao}.jar`

##### Passo 3

- Selecionar o Eclipse/STS que irá receber o Lombok

##### Passo 4

- Clicar em Install/Update

##### Passo 5

- Sair do instalador

##### Passo 6

- Reiniciar a IDE

##### Passo 7

- Realizar um Maven Update no projeto

##### Mais informações

Existe um manual mais detalhado de como instalar o plugin no Eclipse: [Criando uma API Rest de cadastro de contatos em 5 minutos com Spring Boot + Lombok](https://medium.com/@viniciosarodrigues/criando-uma-api-rest-de-cadastro-de-contatos-em-5-minutos-com-spring-boot-ce5ba775d2d8)

---

## MySql ou MariaDB

Para que seja possível rodar a aplicação, faz-se necessário cofigurar previamente as informações de conexão no arquivo `application-dev.properties` deste projeto.

Um setup inicial válido para uma base nova pode seguir os padrões abaixo:

- URL de conexão da base `spring.datasource.url=jdbc:mysql://[USUARIO]:[SENHA]@[SERVIDOR]:[PORTA]/[BASE]?createDatabaseIfNotExist=TRUE`, o `createDatabaseIfNotExist=TRUE` faz com que o próprio framework crie a base de dados.

- No meu caso, a primeira linha do meu `application-dev.properties` fica `spring.datasource.url=jdbc:mysql://admin:123456dv@localhost:3306/devdb?createDatabaseIfNotExist=TRUE`

---

## Como rodar a API?

### Passo 1

- 👯 Clone este repositório na sua máquina local usando `https://github.com/viniciosarodrigues/gestao-de-prontuario.git`

### Passo 2

- ✅ Importe o projeto na sua IDE

### Passo 3

- 📝 Altere a primeira linha do `application.properties`para `spring.profiles.active=dev`

### Passo 4

- 🔃 Rode a aplicação com botão direito do mouse no projeto, `run as` > `Spring Boot App`. Também é possível rodar a aplicação à partir da classe `ApplicationMain` dentro do projeto, basca clicar com o botão direito na classe e seguir o mesmo fluxo.

### Dica
Se você estiver usando o Eclipse, pode ser uma boa ideia baixar o plugin disponibilizado pela Pitoval no marketplace, o `Spring Tools 4 (release)`

---

## Como contribuir

### Passo 1

- 🍴 Realize um Fork deste respositório!

### Passo 2

- 👯 Clone este repositório na sua máquina local usando `https://github.com/viniciosarodrigues/gestao-de-prontuario.git`

### Passo 3

- 🎋 Crie sua branch de funcionalidade usando `git checkout -b minha-funcionalidade`

### Passo 4

- ✅ Realize o commit de suas alterações usando `git commit -m 'feat: Minha nova funcionalidade'`;

### Passo 5

- 📌 Realize o push para a branch usando `git push origin minha-funcionalidade`;

### Passo 6

- 🔃 Crie um novo pull request

Depois que seu Pull Request é aceito e o merge é realizado, você pode deletar a sua branch de funcionalidade.

---

## Arquitetura (Em desenvolvimento...)
Esta área especifica definições técnicas da aplicação, suas características e comportamentos.
### Modelagem de dados
<img alt="Diagrama" src="./readme/Diagrama.png" />

---

## Contato

> Você pode me encontrar por aqui...

- Linkedin :: [Vinícios Rodrigues](https://www.linkedin.com/in/viniciosrodrigues/)
- Instagram :: [@viniarodrigues](https://www.instagram.com/viniarodrigues/)
- Hangouts/Gmail :: [viniciosarodrigues@gmail.com](viniciosarodrigues@gmail.com)

---

## 📝 License

<img alt="License" src="https://img.shields.io/badge/license-MIT-%2304D361">

Este projeto é licenciado por MIT License - Veja a licença no arquivo [LICENSE](LICENSE) para mais detalhes.

---

