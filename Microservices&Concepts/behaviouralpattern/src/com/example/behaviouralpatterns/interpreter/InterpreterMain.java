package com.example.behaviouralpatterns.interpreter;

public class InterpreterMain {
    public static void main(String[] args) {
        Expression isJava = new TerminalExpression("Java");
        Expression isKotlin = new TerminalExpression("Kotlin");
        Expression languageCheck = new OrExpression(isJava, isKotlin);

        System.out.println(languageCheck.interpret("I love Java"));       // true
        System.out.println(languageCheck.interpret("I love Python"));     // false

        Expression bothCheck = new AndExpression(isJava, new TerminalExpression("is"));
        System.out.println(bothCheck.interpret("Java is awesome"));       // true
    }
}
