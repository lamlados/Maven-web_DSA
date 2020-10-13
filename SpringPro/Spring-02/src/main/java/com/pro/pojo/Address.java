package com.pro.pojo;

public class Address {
    private String addr;
    public String getAddr() {
        return addr;
    }

    @Override
    public String toString() {
        return "Address{" +
                "addr='" + addr + '\'' +
                '}';
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }
}
