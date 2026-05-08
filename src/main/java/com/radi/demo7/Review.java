package com.radi.demo7;

public class Review {
    private String content;
    private String user_name;


    public Review(String content, String user_name) {
        this.content = content;
        this.user_name = user_name;
    }

    public String getContent() {
        return content;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setContent(String content) { this.content = content; }
    public void setUser_name(String user_name) { this.user_name = user_name; }
}