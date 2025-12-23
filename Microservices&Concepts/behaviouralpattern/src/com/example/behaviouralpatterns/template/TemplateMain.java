package com.example.behaviouralpatterns.template;

public class TemplateMain {
    public static void main(String[] args) {
        Game cricket = new Cricket();
        cricket.play();

        Game football = new Football();
        football.play();
    }
}