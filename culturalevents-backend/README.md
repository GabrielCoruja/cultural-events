# Eventos Culturais: API

## Descrição

A API de **Eventos Culturais** é uma aplicação back-end desenvolvida com Java e o framework Spring
Boot. Seu principal objetivo é gerenciar eventos culturais, oferecendo funcionalidades completas
para criação, edição, exclusão e consulta de eventos, além de gerenciar categorias associadas.

## Funcionalidades

### Eventos

- Criar novos eventos culturais.
- Editar eventos existentes.
- Excluir eventos cadastrados.
- Listar todos os eventos.
- Buscar eventos específicos por ID.

### Categorias

- Cadastrar novas categorias de eventos.
- Atualizar categorias existentes.
- Remover categorias cadastradas.
- Listar todas as categorias.
- Buscar uma categoria específica e listar os eventos associados.

## Tecnologias e Ferramentas Utilizadas

### Tecnologias

- [Java Versão 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html):
  Linguagem base do projeto.
- [Spring Boot](https://spring.io/projects/spring-boot): Framework para criação de aplicações
  robustas e escaláveis.
    - [Spring Data JPA](https://spring.io/projects/spring-data-jpa): Integração para persistência de
      dados.
- [Docker](https://docs.docker.com/engine/install/): Contêineres para padronização e portabilidade.
    - [Docker Compose](https://docs.docker.com/compose/): Orquestração de serviços.
- [MySQL](https://www.mysql.com/) (Baixado via Docker): Banco de dados relacional usado em produção.
- [H2 database](https://www.h2database.com/html/main.html): Banco de dados em memória usado em
  ambiente de desenvolvimento e testes.

### Ferramentas

- [IntelliJ IDEA Community Edition](https://www.jetbrains.com/pt-br/idea/): IDE para
  desenvolvimento.
- [Insomnia](https://insomnia.rest/): Cliente para testar requisições HTTP.

### Qualidade de código

O [Checkstyle](https://checkstyle.sourceforge.io/) é uma ferramenta de análise estática de código
que ajuda a garantir que o código-fonte Java siga um conjunto predefinido de regras de codificação.
No projeto, é utilizado o arquivo de
configuração [intellij-java-google-style.xml](https://github.com/google/styleguide/blob/gh-pages/intellij-java-google-style.xml)
do Google, que define um conjunto de diretrizes de codificação baseadas nas práticas recomendadas do
Google para Java.

Para executar a análise, utilize o comando:

```sh
mvn checkstyle:check
```

### Testes

Na aplicação foi utilizado o [JUnit 5](https://junit.org/junit5/) para criar e executar testes de
integração.

- Para executar todos os testes, utilize o comando:

    ```sh
    mvn test
    ```

- Para executar um teste específico, utilize o comando:

   ```sh
   mvn -Dtest=classe#nome_do_metodo test
   ```

  Exemplo:

   ```sh
   mvn -Dtest=EventTest#getAllEventsSuccessTest test
   ```

**Dica:** No IntelliJ IDEA, você pode rodar os testes diretamente clicando no ícone de execução ao
lado do método de teste.

## Execução do `Back-End`

### Pré-requisitos:

- [SDKMAN! (Software Development Kit Manager)](https://sdkman.io/): Gerenciador de versões para
  Java. Versão utilizada: **Java 17 Oracle**.
- [Docker](https://docs.docker.com/engine/install/): Para rodar a aplicação com contêineres.

### Instalação e Execução

1. Clone o repositório
   ```sh
   git@github.com:GabrielCoruja/cultural-events.git
    ```

2. Acesse a pasta do projeto
   ```sh
   cd culturalevents-backend
   ```

3. Start da Aplicação

<details>
  <summary>Com Docker</summary>

- Para subir a aplicação e o banco de dados:

    ```sh
    docker-compose up -d --build
    ```

**Obs**: Os dados serão persistidos no banco MySQL.

</details>

<details>
  <summary>Start da aplicação sem Docker</summary>

- Instalação e compilação dos recursos necessários:
   ```sh
   mvn install -DskipTests
   ```

- Iniciar a aplicação e o banco de dados:
   ```sh
   mvn spring-boot:run
   ```

**Obs**: Os dados serão persistidos no banco H2 em memória.

</details>

4. O endpoint base da aplicação será:

- http://localhost:8080

### Documentação

A documentação da API foi desenvolvida utilizando o Swagger, que oferece uma interface interativa para explorar os endpoints. Execute a aplicação e Acesse em:

- http://localhost:8080/swagger-ui/index.html

Também é possível acessar os endpoints para fazer as requisições diretamente pelo Insomnia ou Postman. Basta importar o arquivo [Insomnia_cultural-events.json](https://github.com/GabrielCoruja/cultural-events/blob/main/culturalevents-backend/Insomnia_cultural-events.json) localizado na raiz do projeto.
