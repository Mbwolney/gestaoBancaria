package com.gestaoBancaria.api.exception;

public class SaldoInsuficienteException extends RuntimeException{
    public SaldoInsuficienteException(String mensagem) {
        super(mensagem);
    }
}
