package com.garrytrue.retrofitlambdaxjava.events;


public class EventComplete {
    public static final int TASK_FORECAST = 1;
    public static final int TASK_CURRENT_WEATHER = 2;

    private final int flag;
    public EventComplete(int flag){
        this.flag = flag;
    }
    public int getFlag() {
        return flag;
    }
}
