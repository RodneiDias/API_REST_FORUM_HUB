# API_RET_Forum_Hub
## Descrição

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

## Funcionalidades
### Autenticação e Autorização
A aplicação utiliza JWT (JSON Web Token) para autenticação e autorização. O token é gerado durante o login e deve ser enviado em todas as requisições subsequentes no cabeçalho Authorization com o prefixo Bearer.

## Roles
ADMIN: Tem permissão para criar, atualizar e excluir cursos, tópicos e respostas.
USER: Tem permissão para criar e visualizar tópicos e respostas.

## Endpoints da API
### UsuarioController
- POST /usuarios: Cadastrar um novo curso.
- GET /usuarios: Listar todos os cursos.
- GET /usuarios/{idUsuario}: Detalhar um usuario específico.
- PUT /usuarios/{idUsuario}: Atualizar um usuario específico.
- DELETE /usuario/{idUsuario}: Excluir um usuario específico.

### CursoController
- POST /cursos: Cadastrar um novo curso.
- GET /cursos: Listar todos os cursos.
- GET /cursos/{idCurso}: Detalhar um curso específico.
- PUT /cursos/{idCurso}: Atualizar um curso específico.
- DELETE /cursos/{idCurso}: Excluir um curso específico.
 
### TopicoController
- POST /cursos/{idCurso}/topicos: Cadastrar um novo tópico em um curso.
- GET /cursos/{idCurso}/topicos: Listar todos os tópicos de um curso.
- GET /cursos/{idCurso}/topicos/{idTopico}: Detalhar um tópico específico.
- PUT /cursos/{idCurso}/topicos/{idTopico}: Atualizar um tópico específico.
- DELETE /cursos/{idCurso}/topicos/{idTopico}: Excluir um tópico específico.

### RespostaController
- POST /cursos/{idCurso}/topicos/{idTopico}/respostas: Cadastrar uma nova resposta em um tópico.
- GET /cursos/{idCurso}/topicos/{idTopico}/respostas: Listar todas as respostas de um tópico.
- GET /cursos/{idCurso}/topicos/{idTopico}/respostas/{idResposta}: Detalhar uma resposta específica.
- PUT /cursos/{idCurso}/topicos/{idTopico}/respostas/{idResposta}: Atualizar uma resposta específica.
- DELETE /cursos/{idCurso}/topicos/{idTopico}/respostas/{idResposta}: Excluir uma resposta específica.
  
## Instruções de Configuração

1. Clone o repositório para o seu ambiente local.
   - git clone https://github.com/RodneiDias/API_REST_FORUM_HUB.git
2. Crie um banco de dados MySQL.   
3. Configure o arquivo `application.properties` com as informações do seu banco de dados.
    - spring.datasource.url=jdbc:mysql://localhost:3306/forum_hub_api
    - spring.datasource.username= [seu nome de usuario]
    - spring.datasource.password= [senha do banco de dados]
4. Crie o banco de dados MySql
    - CREATE DATABASE forum_hub_api;
5. Após a configuração, você pode executar a aplicação executando a classe :
   - ForumApplication.java.      
6. Insira um usuario direto no banco
   - INSERT INTO usuarios (nome, email, senha) VALUES ('USER_NAME', 'exemplo@exemplo.exemplo', '$2a$10$Y50UaMFOxteibQEYLrwuHeehHYfcoafCopUazP12.rqB41bsolF5.');
7. Insira o tipo de acesso especificando a role (ROLE_ADMIN ou ROLE_USER)
   - INSERT INTO user_roles (user_id, role) VALUES (1, 'ROLE_USER'); 
 
## Estrutura do Projeto
```
src/
└── main/
├── java/
│   └── br/
│       └── com/
│           └── alura/
│               └── forum/
│               |   ├── controller/
|               |   |   ├── AutenticacaoController
│               |   │   ├── CursoController.java
│               |   │   ├── RespostaController.java
│               |   │   └── TopicoController.java
│               |   ├── domain/
│               |   │   ├── curso/
│               |   │   │   ├── Curso.java
│               |   │   │   ├── CursoRepository.java
│               |   │   │   ├── DadosAtualizacaoCurso.java
│               |   │   │   ├── DadosCadastroCurso.java
│               |   │   │   ├── DadosDetalhamentoCurso.java
│               |   │   │   └── DadosListagemCurso.java
│               |   │   ├── resposta/
│               |   │   │   ├── DadosAtualizacaoResposta.java
│               |   │   │   ├── DadosCadastroResposta.java
│               |   │   │   ├── DadosDetalhamentoResposta.java
│               |   │   │   ├── DadosListagemResposta.java
│               |   │   │   ├── Resposta.java
│               |   │   │   └── RespostaRepository.java
│               |   │   ├── topico/
│               |   │   │   ├── DadosAtualizacaoTopico.java
│               |   │   │   ├── DadosCadastroTopico.java
│               |   │   │   ├── DadosDetalhamentoTopico.java
│               |   │   │   ├── DadosListagemTopico.java
│               |   │   │   ├── StatusTopico.java
│               |   │   │   ├── Topico.java
│               |   │   │   └── TopicoRepository.java
│               |   │   └── usuario/
│               |   │       ├── DadosAutenticacao.java
│               |   │       ├── Usuario.java
│               |   │       ├── UsuarioRepository.java
│               |   │       └── AltenticacaoService.java
│               |   └── infra
|               |          ├── security/
│               |          |   ├── SecurityFilter
|               |          |   ├── SecurityConfiguration.java
│               |          |   ├── TokenService.java
│               |          |   └── DadosTokenJWT
|               |          └── springdoc
|               |              └── SpingDocConfigurations   
|               └──ForumApplication
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

```

## Documentação da API
A documentação da API é gerada automaticamente pelo Springdoc OpenAPI e pode ser acessada em:
#### http://localhost:8080/swagger-ui.html

## Contribuições
Contribuições são bem-vindas! Sinta-se à vontade para abrir issues e pull requests.

## Licença
Este projeto está licenciado sob a Licença MIT. Veja o arquivo [Licença MIT](LICENSE) para mais detalhes .


