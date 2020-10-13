package com.pro.demo;

public class Proxy implements Rent{
    private Host host;
    public Proxy(){}
    public Proxy(Host host) {
        this.host = host;
    }
    public void rent(){
        this.host.rent();
    }
    public void extraAction(){
        System.out.println("do sth else...");
    }
}
