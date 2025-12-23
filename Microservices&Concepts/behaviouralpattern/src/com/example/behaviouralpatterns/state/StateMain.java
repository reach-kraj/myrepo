package com.example.behaviouralpatterns.state;

public class StateMain {
    public static void main(String[] args) {
        Context context = new Context();

        StartState start = new StartState();
        start.doAction(context);
        System.out.println(context.getState());

        StopState stop = new StopState();
        stop.doAction(context);
        System.out.println(context.getState());
    }
}
