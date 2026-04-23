# 💰 Sistema de Cálculo - Folha de Pagamento
### Projeto realizado para o trabalho final da disciplina "Programação orientada a Objetos" da residencia do Serratec

Aplicação desenvolvida em **Java** para automatizar o cálculo da folha de pagamento de funcionários, com suporte a persistência em banco de dados **PostgreSQL**. O sistema calcula automaticamente descontos de INSS e IRRF (considerando deduções por dependentes) seguindo as tabelas e alíquotas vigentes.

## 👥 O Esquadrão (Equipe Desenvolvedora) e Responsabilidades

* **Guilherme**
    * *Responsabilidades:* [Insira aqui as tarefas, classes ou fluxos desenvolvidos]
* **Patrick**
    * *Responsabilidades:* [Insira aqui as tarefas, classes ou fluxos desenvolvidos]
* **Jose Ricardo**
    * *Responsabilidades:* [Insira aqui as tarefas, classes ou fluxos desenvolvidos]
* **Liliane**
    * *Responsabilidades:* [Insira aqui as tarefas, classes ou fluxos desenvolvidos]
* **Nicolas**
    * *Responsabilidades:* [Insira aqui as tarefas, classes ou fluxos desenvolvidos]

## 🚀 Funcionalidades

* **Cálculo em Lote (CSV):** Leitura de arquivo TXT/CSV para processamento massivo de funcionários e dependentes, gerando um arquivo de saída com os resultados.
* **Cadastro Avulso (Manual):** Entrada de dados via console com validações rigorosas (CPF, Datas, Regras de Salário Mínimo).
* **Persistência de Dados:** Salvamento automático de departamentos, funcionários, dependentes e histórico de folhas calculadas no banco de dados.

## 🛠️ Tecnologias Utilizadas

* **Linguagem:** Java (Orientação a Objetos)
* **Banco de Dados:** PostgreSQL
* **JDBC:** Driver de conexão `org.postgresql`
* **Arquitetura:** MVC Adaptado (Model, View, DAO, Service, Utils, Exceptions)
* **Padrões de Projeto:** Strategy (para cálculo de impostos)

## ⚙️ Como configurar e rodar o projeto

### 1. Preparando o Banco de Dados
Certifique-se de ter o PostgreSQL instalado na sua máquina.
1. Abra o `pgAdmin` ou o seu terminal SQL.
2. Crie um novo banco de dados chamado `banco_folha`:
   ```sql
   CREATE DATABASE banco_folha;
3. Execute os scripts DDL disponíveis no projeto para criar as tabelas necessárias.
