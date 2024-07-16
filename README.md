# API_RET_Forum_Hub
##Descrição
O projeto é uma API REST para gerenciamento de um fórum de discussões, construída com JAVA e Spring Boot.
Ela permite que os usuários façam autenticação, criem cursos, postem tópicos respostas referentes a cada curso e visualizem cusos, tópicos e respostas. A aplicação usa Spring Security para autenticação e autorização, Hibernate para mapeamento objeto-relacional, Flyway para migrações de banco de dados e Springdoc OpenAPI para documentação da API.

## Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.3.1**
    - **spring-boot-starter-data-jpa**
    - **spring-boot-starter-security**
    - **spring-boot-starter-validation**
    - **spring-boot-starter-web**
- **Hibernate**
- **Flyway**
- **Springdoc OpenAPI**
- **MySQL**
- **Lombok**

## Configuração do Ambiente

### Pré-requisitos

- Java 17
- SpringBoot 3.3.1
- MySQL
- Maven

## Configuração da Aplicação
No arquivo src/main/resources/application.properties, configure as propriedades do banco de dados e outras configurações:

- spring.application.name=Forum_Hub
- spring.datasource.url=jdbc:mysql://localhost:3306/forum_hub_api
- spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
- spring.datasource.username= [seu nome de usuario]
- spring.datasource.password= [senha do banco de dados]
- spring.jpa.show-sql=true
- spring.jpa.properties.hibernate.format_sql=true
- api.security.token.secret=${JWT_SECRET:12345678}
- spring.flyway.repair-on-migrate=true
- spring.flyway.validateMigrationNaming=true
- spring.flyway.out-of-order=true
- logging.level.org.flywaydb=DEBUG
- logging.level.org.hibernate.SQL=DEBUG
- logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE

## Estrutura do Projeto

src/
└── main/
├── java/
│   └── br/
│       └── com/
│           └── alura/
│               └── forum/
│                   ├── controller/
│                   │   ├── CursoController.java
│                   │   ├── RespostaController.java
│                   │   └── TopicoController.java
│                   ├── domain/
│                   │   ├── curso/
│                   │   │   ├── Curso.java
│                   │   │   ├── CursoRepository.java
│                   │   │   ├── DadosAtualizacaoCurso.java
│                   │   │   ├── DadosCadastroCurso.java
│                   │   │   ├── DadosDetalhamentoCurso.java
│                   │   │   └── DadosListagemCurso.java
│                   │   ├── resposta/
│                   │   │   ├── DadosAtualizacaoResposta.java
│                   │   │   ├── DadosCadastroResposta.java
│                   │   │   ├── DadosDetalhamentoResposta.java
│                   │   │   ├── DadosListagemResposta.java
│                   │   │   ├── Resposta.java
│                   │   │   └── RespostaRepository.java
│                   │   ├── topico/
│                   │   │   ├── DadosAtualizacaoTopico.java
│                   │   │   ├── DadosCadastroTopico.java
│                   │   │   ├── DadosDetalhamentoTopico.java
│                   │   │   ├── DadosListagemTopico.java
│                   │   │   ├── StatusTopico.java
│                   │   │   ├── Topico.java
│                   │   │   └── TopicoRepository.java
│                   │   └── usuario/
│                   │       ├── DadosAutenticacao.java
│                   │       ├── Usuario.java
│                   │       ├── UsuarioRepository.java
│                   │       └── UsuarioService.java
│                   └── security/
│                       ├── SecurityConfiguration.java
│                       ├── TokenService.java
│                       └── UsuarioDetailsServiceImpl.java
└── resources/
├── application.properties
└── db/
└── migration/
└── V1__create-table-usuarios.sql
    V2__adicionar_coluna_permissao.sql
    V3__create_table_cursos.sql
    V4__create_table_respostas.sql
    V5__create-table-topicos.sql
    V6__remove_column_permissao.sql
    V7__create-table-role.sql


### Configuração do Banco de Dados

Certifique-se de que o MySQL esteja instalado e rodando. Crie um banco de dados chamado `forum_hub_api`.

### sql
CREATE DATABASE forum_hub_api;

## Endpoints da API
### CursoController
- POST /cursos: Cadastrar um novo curso.
- GET /cursos: Listar todos os cursos.
- GET /cursos/{idCurso}: Detalhar um curso específico.
- PUT /cursos/{idCurso}: Atualizar um curso específico.
- DELETE /cursos/{idCurso}: Excluir um curso específico.
- 
### TopicoController
-POST /cursos/{idCurso}/topicos: Cadastrar um novo tópico em um curso.
-GET /cursos/{idCurso}/topicos: Listar todos os tópicos de um curso.
-GET /cursos/{idCurso}/topicos/{idTopico}: Detalhar um tópico específico.
-PUT /cursos/{idCurso}/topicos/{idTopico}: Atualizar um tópico específico.
-DELETE /cursos/{idCurso}/topicos/{idTopico}: Excluir um tópico específico.

### RespostaController
-POST /cursos/{idCurso}/topicos/{idTopico}/respostas: Cadastrar uma nova resposta em um tópico.
-GET /cursos/{idCurso}/topicos/{idTopico}/respostas: Listar todas as respostas de um tópico.
-GET /cursos/{idCurso}/topicos/{idTopico}/respostas/{idResposta}: Detalhar uma resposta específica.
-PUT /cursos/{idCurso}/topicos/{idTopico}/respostas/{idResposta}: Atualizar uma resposta específica.
-DELETE /cursos/{idCurso}/topicos/{idTopico}/respostas/{idResposta}: Excluir uma resposta específica.

## Autenticação e Autorização
A aplicação utiliza JWT (JSON Web Token) para autenticação e autorização. O token é gerado durante o login e deve ser enviado em todas as requisições subsequentes no cabeçalho Authorization com o prefixo Bearer.

## Documentação da API
A documentação da API é gerada automaticamente pelo Springdoc OpenAPI e pode ser acessada em:
#### http://localhost:8080/swagger-ui.html

## Como Executar
 Clone o repositório:

- git clone https://github.com/RodneiDias/API_REST_FORUM_HUB.git
- cd forum-api
- Configure o banco de dados no arquivo application.properties.

## Contribuições
Contribuições são bem-vindas! Sinta-se à vontade para abrir issues e pull requests.

## Licença
Este projeto está licenciado sob a Licença MIT. Veja o arquivo LICENSE para mais detalhes.


