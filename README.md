# Eventos Culturais

O projeto **Eventos Culturais** é uma aplicação full-stack composta por um back-end em Java com Spring Boot e um front-end desenvolvido em Angular. Ele oferece uma solução completa para gerenciar eventos culturais, com funcionalidades robustas de criação, edição, exclusão e consulta de eventos, além de gerenciamento de categorias associadas.

## Estrutura do Projeto

O repositório está organizado nas seguintes pastas principais:

- `culturalevents-backend/`: Contém o código do back-end, incluindo a lógica de negócio, persistência e API RESTful.
- `culturalevents-frontend/`: Contém o código do front-end, responsável por fornecer uma interface amigável e interativa para os usuários.

Cada pasta possui seu próprio README com instruções detalhadas para instalação, configuração e execução.

## Execução Completa com Docker Compose

A raiz do projeto inclui um arquivo `docker-compose.yml` que simplifica a inicialização de toda a aplicação (front-end + back-end) em contêineres Docker.

### Pré-requisitos

- [Docker](https://docs.docker.com/engine/install/)
- [Docker Compose](https://docs.docker.com/compose/)

### Instalação e Execução

1. Clone o repositório
   ```sh
   git@github.com:GabrielCoruja/cultural-events.git
    ```

2. Acesse a pasta do projeto
   ```sh
   cd cultural-events
   ```

3. Execute o comando para construir e iniciar os serviços:
   ```sh
   docker-compose up -d --build
   ```

4. Após a execução, os serviços estarão disponíveis nos seguintes endpoints:

- **Front-End**: http://localhost:4200
- **Back-End**: http://localhost:8080

### Estrutura do Docker Compose

O `docker-compose.yml` utiliza os `Dockerfile` localizados nas pastas `culturalevents-backend/` e `culturalevents-frontend/` para criar os contêineres:

- `frontend`:
  - Compila e serve o front-end Angular.
  - Exposto na porta 4200.

- `backend`:
  - Inicia o servidor Spring Boot.
  - Exposto na porta 8080.

- `db`:
  - Instância do MySQL utilizada pelo back-end.
  - Exposta na porta 3306.

## Informações Adicionais

Para detalhes mais específicos sobre cada parte do projeto, consulte os READMEs dedicados:

- [README do Back-End](https://github.com/GabrielCoruja/cultural-events/blob/main/culturalevents-backend/README.md)
- [README do Front-End](https://github.com/GabrielCoruja/cultural-events/blob/main/culturalevents-frontend/README.md)
