package com.br.sgme.exceptions;

public class LoginInvalidoException extends RuntimeException{
    public LoginInvalidoException(String message){
        super(message);
    }
}
