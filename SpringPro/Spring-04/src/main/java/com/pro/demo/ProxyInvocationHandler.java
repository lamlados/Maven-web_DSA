package com.pro.demo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyInvocationHandler implements InvocationHandler {
    //被代理的接口
    private Object target;

    public void setTarget(Object target) {
        this.target = target;
    }
    //生成得到代理类
    public Object getProxy(){
        return Proxy.newProxyInstance(this.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }
    //处理代理实例并返回结果
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //本质依靠反射机制
        //额外的方法在invoke之前执行，利用反射获得方法名
        log(method.getName());
        Object result=method.invoke(target,args);
        return result;
    }
    public void log(String msg){
        System.out.println(msg+" is executed");
    }
}
