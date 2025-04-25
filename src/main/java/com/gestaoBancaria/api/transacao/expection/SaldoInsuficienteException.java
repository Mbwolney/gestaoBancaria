package com.gestaoBancaria.api.transacao.expection;

public class SaldoInsuficienteException extends RuntimeException{
    public SaldoInsuficienteException(String mensagem) {
        super(mensagem);
    }
}
