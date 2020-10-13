package com.pro.demo;

public class Client {
    public static void main(String[] args) {
        Host host=new Host();
        //pih 调用程序处理角色
        ProxyInvocationHandler pih = new ProxyInvocationHandler();
        //通过pih来处理要调用的接口对象
        pih.setTarget(host);
        //生成代理
        Rent proxy=(Rent)pih.getProxy();
        proxy.rent();
    }
}
