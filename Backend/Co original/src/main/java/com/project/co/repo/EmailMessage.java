package com.project.co.repo;

public class EmailMessage {

    private String from;
    private String name;
    private String message;
    private String phone;

    public EmailMessage(String from, String name, String message, String phone){
        this.from = from;
        this.message = message;
        this.name = name;
        this.phone = phone;

    }
    public String getFrom(){
        return from;
    }

    public void setFrom(String from){
        this.from = from;
    }
    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
    public String getMessage(){
        return message;
    }

    public void setMessage(String message){
        this.message = message;
    }
    public String getPhone(){
        return phone;
    }

    public void setPhone(String phone){
        this.phone = phone;
    }

}

