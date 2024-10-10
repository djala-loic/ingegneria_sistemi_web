package com.esame.kit.model.dao.exception;

public class NonExistObjectException extends  Exception {
    public NonExistObjectException(){}

    public NonExistObjectException(String msg){
        super(msg);
    }

}
