package com.pro.pojo;

public class Hello {
    private String str;
    public Hello(String str){
        this.str=str;
    }
    @Override
    public String toString() {
        return "Hello{" +
                "str='" + str + '\'' +
                '}';
    }
    public String getStr() {
        return str;
    }
    public void setStr(String str) {
        this.str = str;
    }
}
