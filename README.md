# Sistema de Gestão Bancária

[![Build Status](https://img.shields.io/badge/Build-Success-green)](https://travis-ci.com)

## Escopo, Pré-requisitos e Objetivo

**Projeto de Sistema de Gestão Bancária**: 
O objetivo deste projeto é ilustrar uma aplicação de gestão bancária desenvolvida em Java, utilizando **Spring Boot 3**, **JDK 17** e **Maven 3.8.6**. A aplicação simula o funcionamento de contas bancárias, onde é possível realizar transações (Pix, Débito e Crédito) com validações de saldo.

**Objetivo**: Prover uma referência para a organização de código e implementar boas práticas de desenvolvimento em projetos reais com Spring Boot e Java. 

Este projeto também serve como base para integrações de DevOps, como integração contínua, análise estática e análise de segurança.

---

## Iniciando

Para rodar o projeto localmente, siga os passos abaixo.

### Clonando o Repositório

```bash
git clone https://github.com/Mbwolney/gestaoBancaria.git
```

## Pré-requisitos

- `mvn --version`<br>
  Você deverá ver a indicação da versão do Maven instalada e
  a versão do JDK, dentre outras. Observe que o JDK é obrigatório, assim como
  a definição das variáveis de ambiente **JAVA_HOME** e **M2_HOME**.

- `mvn compile`<br>
  Compila o projeto e coloca os resultados no diretório _target_.

- `mvn test`<br>
  Executa todos os testes do projeto.

- `mvn verify -P cobertura`<br>
  Executa testes de unidade e produz relatório de
  cobertura em _target/site/jacoco/index.html_.
    
## Gerar Código Executável (JAR)

- `mvn package`<br>
  Gera arquivo _gestao-bancaria.jar_ no diretório _target_.

- `mvn package -P executavel-dir`<br>
  Gera um JAR executável único com todas as dependências incluídas.

## Executando a aplicação e a RESTFul API

- `mvn spring-boot:run`<br>
  Isso vai iniciar a API na porta padrão 8080.

## Via JAR

- `java -jar target/gestao-bancaria.jar`<br>

## Endpoints da API

### 1. Criar Conta

**Método**: `POST`

**URL**: `/conta`

**Body** (JSON):

```json
{
  "numero_conta": 1234,
  "saldo": 1000.00
}
```

### 2. Realizar Transação

**Método**: `POST`

**URL**: `/transacao`

**Body** (JSON):

```json
{
  "forma_pagamento": "P",  // P: Pix, D: Débito, C: Crédito
  "numero_conta": 1234,
  "valor": 100.00
}
```

### 3. Consultar Conta

**Método**: `GET`

**URL**: `/conta?numeroConta=1234`

**Body** (JSON):

```json
{
  "numero_conta": 1234,
  "saldo": 900.00
}
```
