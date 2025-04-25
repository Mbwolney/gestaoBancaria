package com.gestaoBancaria.api.conta.expection;

public class ContaNaoEncontradaException extends RuntimeException{
    public ContaNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}
