<h1 align="center">
  <a href="https://cliniv-api.herokuapp.com">
    <img alt="NivLabs Logo" src="./readme/logo.png" width="350px" />
  </a>
</h1>
<h2 align="center">
  CliNiv - API
</h2>

CliNiv-API é uma API Rest criada inicialmente como doação para hospitais que não possuem nenhuma solução de automação no processo de prontuário. O CliNiv-API é integrado com um client baseado em Angular, o [CliNiv-UI](https://github.com/niv-labs/cliniv-ui).

<p align="center">
    <a href="https://github.com/niv-labs/">
        <img alt="Made by NiV Labs" src="https://img.shields.io/badge/made%20by-NiV%20Labs-brightgreen" />
    </a>
    <img alt="License" src="https://img.shields.io/badge/license-MIT-%2304D361" />
</p>

Atualmente encontra-se na versão 1.1.0
[Swagger](https://cliniv-api.herokuapp.com/swagger-ui.html)

---

## Índice

<ul>
  <li><a href="#funcionalidades-da-api">Funcionalidades da API</a></li>
  <li><a href="#mínimo-para-rodar">Mínimo para rodar</a></li>
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
- [x] Armazenamento de documentos (Resultado de exames, radiografia, tumografia, etc)
- [x] Histórico de visita em forma de timeline
- [x] Relatórios
- [x] Paciente não identificado
- [x] Fluxo padrão para atendimento ambulatorial
- [x] Fluxo padrão para atendimento emergencial
- [x] Cadastro de medicamentos e materiais
- [x] Prescrição
- [x] Anamnese
- [x] Customização de relatórios
- [x] Cadastro de operadoras de saúde
- [x] Cadastro de procedimentos
- [x] Cadastro de setores

---

## Mínimo para rodar

Para rodar o projeto, existem alguns requisítos mínimos de configurações, são eles:

- Java 17 **(Obrigatório)**
- MariaDB Server 10.5+ **(Obrigatório se não for rodar via docker)**
- Eclipse ou STS **(Obrigatório)**
- Docker **(Obrigatório se for rodar via script)

---

## MariaDB
### Sem Docker

Para que seja possível rodar a aplicação, faz-se necessário configurar previamente as informações de conexão no arquivo `application-prod.properties` deste projeto.

Um setup inicial válido para uma base nova pode seguir os padrões abaixo:

- URL de conexão da base `spring.datasource.url=jdbc:mariadb://[SERVIDOR]:[PORTA]`.

- No meu caso, a primeira linha do meu `application-prod.properties` fica `spring.datasource.url=jdbc:mariadb://localhost:3306`

- As duas linhas seguintes servem para você informar o usuário e a senha de conexão.

- Não informamos o nome da base neste momento porque a aplicação possui um sistema de múltiplas bases. Isto será explicado mais abaixo.

### Com Docker
O projeto possui um build pronto do MariaDB em Docker, neste caso não precisamos alterar nada, basta executar o shellscript `./run-mariadb.sh` e seguir para o próximo passo.

OBS: Obrigatório ter o Docker instalado.

- URL de conexão da base `spring.datasource.url=jdbc:mariadb://[SERVIDOR]:[PORTA]`.

- No meu caso, a primeira linha do meu `application-prod.properties` fica `spring.datasource.url=jdbc:mariadb://localhost:3306`

- As duas linhas seguintes servem para você informar o usuário e a senha de conexão.

- Não informamos o nome da base neste momento porque a aplicação possui um sistema de múltiplas bases. Isto será explicado mais abaixo.

### Observações
A base de dados deve ser criada manualmente no MariaDB em ambos os casos. A diferença entre elas é que com o Docker não há a necessidade de instalação do banco, facilitando o desenvolvimento.

---

## Como rodar a API?

### Usando Docker
#### Passo 1
- 👯 Clone este repositório na sua máquina local usando `https://github.com/niv-labs/cliniv-api.git`

#### Passo 1
- 🔃 Rode o script `./startWithDocker.sh`

- OBS: Obrigatório ter o Docker instalado.

### Usando o Eclipse ou STS
#### Passo 1

- 👯 Clone este repositório na sua máquina local usando `https://github.com/niv-labs/cliniv-api.git`

#### Passo 2

- ✅ Importe o projeto na sua IDE

#### Passo 3

- 🔃 Rode a aplicação com botão direito do mouse no projeto, `run as` > `Spring Boot App`. Também é possível rodar a aplicação à partir da classe `ApplicationMain` dentro do projeto, basca clicar com o botão direito na classe e seguir o mesmo fluxo.

#### Dica
- Se você estiver usando o Eclipse, pode ser uma boa ideia baixar o plugin disponibilizado pela Pitoval no marketplace, o `Spring Tools 4 (release)`.

#### Dica 2
- A aplicação utiliza um sistema de múltiplas conexões, para que a API saiba qual base se conectar, deve-se informar o nome da mesma via cabeçalho com a chave `CUSTOMER_ID`.

---

## Como contribuir

### Passo 1

- 🍴 Realize um Fork deste respositório!

### Passo 2

- 👯 Clone este repositório na sua máquina local usando `https://github.com/niv-labs/cliniv-api.git`

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

- NiV Labs :: [NiV Labs](http://www.nivlabs.com.br)
- Atendimento :: [atendimento@nivlabs.com.br](atendimento@nivlabs.com.br)
- Vinícios (eu) :: [viniciosrodrigues@nivlabs.com.br](viniciosrodrigues@nivlabs.com.br)

> Você pode baixar os manuais aqui...

- Manual do Usuário Final :: [Download](./readme/CliNiv - Manual do usuário final - v1.pdf)

---

## 📝 License

<img alt="License" src="https://img.shields.io/badge/license-MIT-%2304D361">

Este projeto é licenciado por MIT License - Veja a licença no arquivo [LICENSE](LICENSE) para mais detalhes.

---

