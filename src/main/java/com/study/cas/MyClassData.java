package com.study.cas;

class MyClassData {

    private volatile int number = 0;

    public synchronized void addPushPush() {
        this.number++;
    }
}