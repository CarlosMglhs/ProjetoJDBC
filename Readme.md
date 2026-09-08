# ☕ Java JDBC DAO Pattern Project

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/postgresql-4169e1?style=for-the-badge&logo=postgresql&logoColor=white)

## 📌 Sobre o Projeto
Este projeto é uma aplicação Java backend que demonstra a integração com banco de dados relacional utilizando **JDBC (Java Database Connectivity)**. O grande diferencial deste projeto é a aplicação rigorosa do padrão de projeto **DAO (Data Access Object)**, garantindo um código limpo, modular e de fácil manutenção.

Foi desenvolvido com foco em boas práticas de Programação Orientada a Objetos (POO), encapsulando a lógica de acesso a dados e isolando-a da regra de negócio principal.

## ⚙️ Funcionalidades (CRUD)
O sistema gerencia duas entidades principais: `Seller` (Vendedor) e `Department` (Departamento), permitindo as seguintes operações no banco de dados:

* **Buscar por ID:** Retorna um vendedor ou departamento específico.
* **Buscar por Departamento:** Retorna uma lista de vendedores associados a um determinado departamento.
* **Buscar Todos:** Lista todos os registros.
* **Inserir:** Cadastra novos vendedores ou departamentos no banco.
* **Atualizar:** Modifica os dados de registros existentes.
* **Deletar:** Remove registros com base no ID.

## 🏗️ Arquitetura e Estrutura do Projeto
A arquitetura do projeto foi baseada na imagem da estrutura de diretórios, dividida de forma semântica:

```text
📁 src
 ├── 📁 MainJDBC
 │    └── 📄 Main.java              # Ponto de entrada e testes da aplicação
 ├── 📁 db
 │    ├── 📄 DB.java                # Gerenciamento da conexão com o banco de dados
 │    └── 📄 DbException.java       # Exceções personalizadas para erros de banco
 └── 📁 model
      ├── 📁 entities
      │    ├── 📄 Department.java   # Classe de domínio (Entidade Departamento)
      │    └── 📄 Seller.java       # Classe de domínio (Entidade Vendedor)
      └── 📁 DAO
           ├── 📄 DaoFactory.java   # Factory pattern para instanciar os DAOs
           ├── 📄 DepartmentDAO.java# Interface com as operações de Departamento
           ├── 📄 SellerDAO.java    # Interface com as operações de Vendedor
           └── 📁 Impl
                └── 📄 (Implementações JDBC das interfaces DAO)
```

## 🛠️ Tecnologias e Padrões Utilizados
* **Linguagem:** Java (JDK 8+)
* **Banco de Dados:** PostgreSQL
* **API:** JDBC
* **Design Patterns:**
    * **DAO (Data Access Object):** Isolamento da camada de persistência.
    * **Factory Method:** Criação das instâncias de conexão (`DaoFactory`) sem expor a lógica de instanciação.
* **Tratamento de Exceções:** Criação de exceções personalizadas (`DbException`) para evitar o vazamento de stack traces SQL genéricos para a camada de visualização.

## 🚀 Como Executar o Projeto

1. Clone este repositório.
2. Certifique-se de ter o driver JDBC do seu banco de dados configurado no *build path* do projeto.
3. Configure as credenciais do banco de dados (geralmente em um arquivo `db.properties` na raiz do projeto).
4. Crie as tabelas `department` e `seller` no seu banco de dados relacional.
5. Execute a classe `Main.java` para ver as operações acontecendo no console.

## 👨‍💻 Autor
Carlos Magalhães
