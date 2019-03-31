package com.irvin.markov.entity;

import java.io.Serializable;

public class Message implements Serializable {

    private static final long serialVersionUID = 4826977893362301175L;
    private Boolean success;
    private String message;

    public Message() {}

    public Message(Boolean success, String message) {
        super();
        this.success = success;
        this.message = message;
    }

    public Boolean isSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
