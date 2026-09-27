# API MyContacts
![Static Badge](https://img.shields.io/badge/java-21-orange?style=for-the-badge) ![Static Badge](https://img.shields.io/badge/SpringBoot-3-green?style=for-the-badge) ![Static Badge](https://img.shields.io/badge/postgresql-blue?style=for-the-badge) ![Static Badge](https://img.shields.io/badge/swaggerui-green?style=for-the-badge) ![Static Badge](https://img.shields.io/badge/maven-brown?style=for-the-badge)

Uma API para usuários criarem uma conta própria e gerenciar seus contatos telefônicos feito em Java com SpringBoot.

## Para rodar com Docker:
1. Instale o [Docker](https://www.docker.com/products/docker-desktop/)
2. Abra o terminal no diretório
3. Execute o seguinte comando: `docker compose up`

## Para rodar em uma IDE:
1. Instale e use Maven na sua IDE
2. Configure um banco de dados local em PostgreSQL
3. Configure as variáveis da aplicação em `/src/main/resources/application.properties`:
4. Defina `spring.datasource.url = jdbc:postgresql://<nome_do_host>:<porta>/<nome_do_banco>`
5. Defina `spring.datasource.username = <nome_do_usuário_do_banco>`
6. Defina `spring.datasource.password = <senha_do_usuário_do_banco>`
7. Execute o arquivo `/src/main/java/com/migs/mycontacts/MyContacts.java`
