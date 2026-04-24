# 💰 Sistema Folha de Pagamento - Serratec, POO
### Projeto realizado para o trabalho final da disciplina "Programação orientada a Objetos" da residencia do Serratec

Aplicação desenvolvida em **Java** para automatizar o cálculo da folha de pagamento de funcionários, com suporte a persistência em banco de dados **PostgreSQL**. O sistema calcula automaticamente descontos de INSS e IRRF (considerando deduções por dependentes) seguindo as tabelas e alíquotas vigentes.

## 👥 Membros do grupo e Responsabilidades

* **Guilherme**
    * *Responsabilidades:* Montagem da arquitetura, refatoração e revisão do codigo completo, testagem manual do codigo, implementação dos fluxos 3 a 5 no menu.
* **Patrick**
    * *Responsabilidades:* Arquitetura do projeto, revisão, lógica do fluxo 1 e 2, criação de views para as folhas e services.
* **Jose Ricardo**
    * *Responsabilidades:* Estrutura do package DAO e criação da base de dados.
* **Liliane**
    * *Responsabilidades:* Criação do menu.
* **Nicolas**
    * *Responsabilidades:* Tratamento de Erros & Exceções, Criação dos pacotes: "Utils", "Exception" e das classes de validação, além da implementação das mesmas nas classes criadas anteriormente.

## 🚀 Funcionalidades

* **Cálculo em Lote (CSV):** Leitura de arquivo TXT/CSV para processamento massivo de funcionários e dependentes, gerando um arquivo de saída com os resultados.
* **Cadastro Avulso (Manual):** Entrada de dados via console com validações rigorosas (CPF, Datas, Regras de Salário Mínimo).
* **Persistência de Dados:** Salvamento automático de departamentos, funcionários, dependentes e histórico de folhas calculadas no banco de dados.

## 🏗️ Estrutura do Projeto (Pacotes)
Para garantir a organização e a escalabilidade do sistema, dividimos a aplicação nos seguintes pacotes dentro de br.com.folhapag:

* **br.com.folhapag:** Contém a classe Main.java, o ponto de entrada da aplicação que orquestra o início do sistema.

* **br.com.folhapag.config:** Contém a classe Conexao.java, responsável pela configuração do Driver JDBC e por estabelecer a conexão com o banco de dados PostgreSQL. 

* **br.com.folhapag.contexts:** Contém a FolhaContexto.java, que gerencia o estado global e o fluxo de inicialização da aplicação, garantindo que o banco seja populado corretamente ao iniciar.
  
* **br.com.folhapag.dao:** Contém as classes responsáveis pela persistência: DepartamentoDao, DependenteDao, FolhaPagamentoDao e FuncionarioDao. Elas isolam os comandos SQL do restante da lógica de negócio.

* **br.com.folhapag.enums:** Contém o enumerador Parentesco.java, que restringe os tipos de dependentes permitidos, evitando erros de digitação e inconsistências no banco.

* **br.com.folhapag.exception:** Contém as exceções personalizadas do projeto. Elas permitem que o sistema lance erros específicos (ex: CPF inválido) em vez de erros genéricos do Java, melhorando o debug e a experiência do usuário.

* **br.com.folhapag.interfaces:** Contém a interface CalcularImposto.java, estabelecendo um contrato para os cálculos do sistema, o que facilita a manutenção e a aplicação de padrões.

* **br.com.folhapag.model:** Representa os objetos de dados do sistema. Inclui a classe Pessoa.java , demonstrando o uso de Herança para as classes Funcionario e Dependente, além de Departamento e FolhaPagamento.
  
* **br.com.folhapag.service:** Contém a lógica de processamento massivo (FolhaLoteService). Classes de cálculo específico: CalcularINSS e CalcularIR, que aplicam as alíquotas oficiais.

* **br.com.folhapag.utils:** Classes utilitárias focadas em garantir a integridade dos dados antes de chegarem ao banco. Centraliza as regras de validação para evitar repetição de código.

* **br.com.folhapag.views:** Gerencia a interação via console.

## 📊 Diagrama UML

Para facilitar a compreensão da arquitetura e das relações entre as classes do sistema, disponibilizamos os diagramas UML na pasta `diagramVersions/` na raiz do repositório.

Pensando em diferentes níveis de aprofundamento, os diagramas foram divididos em três versões, todas disponíveis nos formatos de imagem (`.png`, `.svg`) e em código-fonte aberto (`.plantuml`):

* **simple:** Diagrama completo porem apenas com o nome de cada classe, pensado nas conexões e na visualização.
* **medium:** Diagrama focado nas classes principais, colocando-as com todos os campos e metodos e deixando as classes complementares dentro dos seus packages, para melhor visualização .
* **complete:** Diagrama arquitetural completo, contendo todos os pacotes, exceções, utilitários e métodos do sistema e com todos os campos de cada classe.

## 🛠️ Tecnologias Utilizadas

* **Linguagem:** Java
* **Banco de Dados:** PostgreSQL
* **JDBC:** Driver de conexão `org.postgresql`
* **Arquitetura:** MVC Adaptado para consoles (Model, View, DAO, Service, Utils, Exceptions)

## ⚙️ Como configurar e rodar o projeto
1. Preparando o Banco de Dados
   * Crie um banco de dados chamado **banco_folha**.

   * Para que o sistema conecte ao seu banco local, é necessário ajustar as credenciais no código de conexão:

   * Arquivo: Localize a classe **Conexao.java** no pacote **br.com.folhapag.config**.

   * Ação: Altere a variável de senha para a senha do seu PostgreSQL local.

2. Execução
   * Execute a classe **Main.java** localizada no pacote raiz br.com.folhapag.

   * Dados Prontos: O sistema já iniciará com alguns dados de exemplo carregados no banco.

   * Teste de Arquivo: Para o cálculo em lote, utilize o arquivo dados.csv que se encontra na raiz da pasta do projeto.
