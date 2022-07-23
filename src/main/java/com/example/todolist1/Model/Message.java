package com.example.todolist1.Model;

public class Message {
    private final String status;
    private final String message;
    private final Object data;

    public Message(String status, String message, Object data) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public Object getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}
