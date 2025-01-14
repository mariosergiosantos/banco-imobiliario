# **Banco Imobiliário - API Backend**

Este projeto é uma API backend para gerenciar partidas do jogo **Banco Imobiliário**, substituindo o uso de dinheiro físico e permitindo controle automatizado de transações, propriedades, jogadores e negociações.

---

## **Funcionalidades**

- **Gerenciamento de Salas**:
    - Criação de salas com código alfanumérico único.
    - Adição e remoção de jogadores.
    - Controle de status da sala (aberta, em andamento, encerrada).

- **Controle de Jogadores**:
    - Adição de jogadores a uma sala.
    - Atualização de saldo de jogadores.
    - Histórico de transações acessível para todos os jogadores.

- **Propriedades**:
    - Registro automático das propriedades ao iniciar a sala.
    - Controle de compra, venda e hipoteca de propriedades.
    - Construção de casas e hotéis com base nas regras do jogo.

- **Transações**:
    - Compra de propriedades do banco ou de outros jogadores.
    - Registro de pagamento de aluguel e salário.
    - Histórico detalhado de todas as transações.

---

## **Tecnologias Utilizadas**

- **Backend**:
    - Java 21
    - Spring Boot (Data JPA, Web, Validation)
    - FlywayDB para controle de migração do banco de dados.

- **Banco de Dados**:
    - MySQL (modelo relacional)
    - Suporte a H2 em ambiente de desenvolvimento.

- **Docker**:
    - Containerização do backend.
    - Integração com LocalStack para simulação de serviços AWS.

- **Outros**:
    - MapStruct para mapeamento de objetos.
    - JUnit 5 e Mockito para testes unitários.
    - Spring Cache para otimização de consultas.

---

## **Estrutura do Projeto**

```
src/main/java/com/nossogame/bancoimobiliario
├── controller         # Controladores REST
├── dto                # Data Transfer Objects
├── exception          # Classes de exceção personalizadas
├── mapper             # Mapeamento de entidades para DTOs
├── model              # Modelos JPA (entidades do banco)
├── repository         # Repositórios Spring Data JPA
├── service            # Lógica de negócios
├── validation         # Validações de regras de negócios
```

---

## **Como Executar**

### **1. Pré-requisitos**
- Docker e Docker Compose instalados.
- MySQL configurado (ou use o contêiner do Docker).

### **2. Construir e Executar o Contêiner**
```bash
docker build -t banco-imobiliario-api .
docker run -p 8080:8080 banco-imobiliario-api
```

### **3. Variáveis de Ambiente**
As seguintes variáveis de ambiente são configuradas no contêiner:

- **AWS_ACCESS_KEY_ID**: `test`
- **AWS_SECRET_ACCESS_KEY**: `test`
- **AWS_REGION**: `us-east-1`
- **AWS_ENDPOINT_URL**: `http://localstack:4566`

### **4. Executar Localmente**
Se desejar executar localmente:
```bash
./mvnw spring-boot:run
```

---

## **Endpoints Principais**

### **Salas**
| Método | Endpoint                | Descrição                                   |
|--------|-------------------------|--------------------------------------------|
| POST   | `/api/salas`            | Cria uma nova sala.                        |
| GET    | `/api/salas/{id}`       | Obtém detalhes de uma sala específica.     |
| PATCH  | `/api/salas/{id}/status`| Atualiza o status de uma sala.             |
| GET    | `/api/salas`            | Lista todas as salas disponíveis.          |

### **Jogadores**
| Método | Endpoint                       | Descrição                                 |
|--------|--------------------------------|------------------------------------------|
| POST   | `/api/salas/{id}/jogadores`    | Adiciona um novo jogador a uma sala.     |
| GET    | `/api/salas/{id}/jogadores`    | Lista todos os jogadores de uma sala.    |
| PATCH  | `/api/salas/{id}/jogadores/{id}/saldo` | Atualiza o saldo de um jogador.          |

### **Propriedades**
| Método | Endpoint                                   | Descrição                                   |
|--------|-------------------------------------------|--------------------------------------------|
| GET    | `/api/salas/{id}/propriedades`            | Lista todas as propriedades de uma sala.   |
| PATCH  | `/api/salas/{id}/propriedades/{id}/transferir` | Transfere a propriedade para outro jogador. |
| PATCH  | `/api/salas/{id}/propriedades/{id}/hipoteca`  | Atualiza o status de hipoteca de uma propriedade. |
| PATCH  | `/api/salas/{id}/propriedades/{id}/construir` | Adiciona casas ou hotel a uma propriedade.  |

---

## **Testes**

### **Testes Unitários**
- Frameworks: **JUnit 5** e **Mockito**.
- Para executar:
```bash
mvn test
```

### **Cobertura de Testes**
- Validações de transações.
- Métodos de serviço.
- Regras de negócio.

---

## **Contribuição**

1. Faça um fork do repositório.
2. Crie um branch para suas alterações:
   ```bash
   git checkout -b minha-feature
   ```
3. Faça commit das alterações:
   ```bash
   git commit -m "Minha nova feature"
   ```
4. Envie para o branch principal:
   ```bash
   git push origin minha-feature
   ```
5. Abra um pull request.

---

## **Licença**
Este projeto é licenciado sob a **MIT License**.