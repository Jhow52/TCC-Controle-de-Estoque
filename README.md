# Sistema de Gestão de Estoque

## 📋 Sobre o Projeto

O Sistema de Gestão de Estoque foi desenvolvido como Trabalho de Conclusão de Curso (TCC) com o objetivo de auxiliar empresas no controle de produtos, categorias, movimentações de estoque e gerenciamento de usuários.

A aplicação permite realizar o cadastro e gerenciamento de produtos, controlar entradas e saídas de estoque, gerenciar categorias e aplicar diferentes níveis de acesso através de autenticação e autorização utilizando JWT.

---

## 🚀 Funcionalidades

### 👤 Usuários

* Cadastro de usuários
* Login com autenticação JWT
* Controle de permissões por perfil
* Promoção de usuários para Administrador
* Remoção de permissões de Administrador
* Proteção contra remoção do próprio perfil ADMIN
* Proteção contra remoção do último ADMIN do sistema

### 📦 Produtos

* Cadastro de produtos
* Consulta de produtos
* Atualização de produtos
* Remoção de produtos
* Controle de estoque mínimo

### 🏷️ Categorias

* Cadastro de categorias
* Consulta de categorias
* Atualização de categorias
* Remoção de categorias
* Validação para impedir exclusão de categorias em uso

### 📊 Estoque

* Controle de entrada de produtos
* Controle de saída de produtos
* Atualização automática das quantidades em estoque
* Histórico de movimentações

### 🔒 Segurança

* Autenticação com JWT
* Autorização baseada em Roles (USER e ADMIN)
* Rotas protegidas
* Swagger integrado com autenticação JWT
* Tratamento global de exceções
* CORS configurado para integração com Angular

---

## 🛠️ Tecnologias Utilizadas

### Backend

* Java 21
* Spring Boot
* Spring Security
* Spring Data JPA
* JWT (JSON Web Token)
* Hibernate
* MySQL
* Flyway
* Swagger / OpenAPI

### Frontend

* Angular
* TypeScript
* HTML
* CSS

### Ferramentas

* Git
* GitHub
* IntelliJ IDEA
* MySQL Workbench
* Postman
* Swagger UI

---

## 🏗️ Arquitetura do Projeto

O projeto segue o padrão de arquitetura em camadas:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
Banco de Dados
```

### Camadas

* Controller: Responsável por receber as requisições HTTP.
* Service: Contém as regras de negócio.
* Repository: Responsável pela comunicação com o banco de dados.
* Entity: Representação das tabelas do banco.
* DTO: Transferência de dados entre cliente e servidor.
* Security: Configuração de autenticação e autorização.

---

## 🔐 Controle de Acesso

### ROLE_USER

Possui acesso a:

* Produtos
* Categorias
* Inventário
* Movimentações de Estoque

### ROLE_ADMIN

Possui acesso a:

* Todas as funcionalidades do sistema
* Gerenciamento de usuários
* Promoção e remoção de administradores

---

## 📚 Documentação da API

Após iniciar a aplicação, a documentação pode ser acessada através do Swagger:

```text
http://localhost:8080/swagger-ui/index.html
```

Utilize o endpoint de login para gerar um token JWT e clique no botão "Authorize" para testar os endpoints protegidos.

---

## ⚙️ Como Executar o Projeto

### Clonar o Repositório

```bash
git clone https://github.com/seu-usuario/seu-repositorio.git
```

### Configurar o Banco de Dados

Crie um banco MySQL e configure as credenciais no arquivo:

```properties
application.properties
```

Exemplo:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/estoque
spring.datasource.username=root
spring.datasource.password=sua_senha
```

### Executar o Backend

```bash
./mvnw spring-boot:run
```

ou

```bash
mvn spring-boot:run
```

### Executar o Frontend

```bash
npm install
ng serve
```

Aplicação disponível em:

```text
http://localhost:4200
```

---

## 📸 Telas do Sistema

### Login

(Adicionar imagem)

### Dashboard

(Adicionar imagem)

### Produtos

(Adicionar imagem)

### Categorias

(Adicionar imagem)

### Administração de Usuários

(Adicionar imagem)

---

## 🎯 Objetivo Acadêmico

Este projeto foi desenvolvido como Trabalho de Conclusão de Curso com o objetivo de aplicar na prática conhecimentos adquiridos durante a graduação, envolvendo:

* Programação Orientada a Objetos
* Desenvolvimento Web
* APIs REST
* Segurança com JWT
* Banco de Dados Relacional
* Arquitetura em Camadas
* Boas Práticas de Desenvolvimento

---

## 👨‍💻 Autor

Jhonata

Desenvolvedor Back-end Java | Spring Boot | APIs REST | Segurança com JWT

GitHub: https://github.com/seu-usuario

LinkedIn: (Adicionar LinkedIn)