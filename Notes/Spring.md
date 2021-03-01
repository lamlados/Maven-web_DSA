# Spring



---

笔记基于狂神说Java的b站Spring学习视频，根据自己的理解随之记录。

<https://www.bilibili.com/video/BV1WE411d7Dv>

官方网站：https://spring.io/projects/spring-framework#learn

优点：轻量、非入侵、控制反转、面向切面

Maven导入：

```xml
<!-- https://mvnrepository.com/artifact/org.springframework/spring-webmvc -->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-webmvc</artifactId>
    <version>5.2.8.RELEASE</version>
</dependency>
```

---

## 1. HelloSpring

### 1.1 第一个Spring程序

1. 创建实体类

```java
public class Hello {
    private String str;
    @Override
    public String toString() {
        return "Hello{" +
                "str='" + str + '\'' +
                '}';
    }
    public String getStr() {
        return str;
    }
  	//利用set方法进行依赖注入
    public void setStr(String str) {
        this.str = str;
    }
}
```

2. 编写配置文件applicationContext.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd">
    <bean id="hello" class="com.pro.pojo.Hello">
        <property name="str" value="Spring"/>
    </bean>
</beans>
```

3. 测试

```java
@Test
public void test(){
  	//获取Spring的上下文对象
    ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
  	//对象现在由Spring管理，要使用从中取出
  	Hello hello = (Hello) context.getBean("hello");
    System.out.println(hello);
}
```

---

## 2. IOC创建对象的方式

​	在配置文件加载时，容器里的对象已经都被初始化了

### 2.1 IOC

​	**控制反转（Inversion of Control）**是一种设计思想，**依赖注入（Dependency Injection）**是其一种实现方法。在没有IOC的程序中，使用面向对象编程，对象的创建与依赖关系被编码在程序中，而控制反转后将对象的创建交给第三方。获得依赖的方式反转了

​	IOC通过注解或xml来从第三方进行生产或获取特定对象，在Spring中通过IoC容器，DI的方式实现

### 2.2 默认方式

​	使用无参构造

### 2.3 使用有参构造

​	方法一：通过下标赋值，index为有参构造里的参数下标

```xml
<bean id="hello" class="com.pro.pojo.Hello">
    <constructor-arg index="0" value="Spring"/>
</bean>
```

​	方法二：通过参数类型赋值,不建议使用

```xml
<bean id="hello" class="com.pro.pojo.Hello">
    <constructor-arg type="java.lang.String" value="Spring"/>
</bean>
```

​	方法三：直接通过参数名

```xml
<bean id="hello" class="com.pro.pojo.Hello">
    <constructor-arg name="str" value="Spring"/>
</bean>
```

---

## 3. Spring配置

### 3.1 别名alias

```xml
<alias name="hello" alias="hello2"/>
```

### 3.2 bean的配置

- id：bean的唯一标识符，相当于对象名
- class：对象所对应的全限定名，相当于包名+类型
- name：也是别名，可以取多个
- property：对象的属性
- value：基本类型的值
- ref：引用容器中的其他对象

```xml
<bean id="hello" class="com.pro.pojo.Hello" name="u,u1,u2">
    <constructor-arg index="0" value="Spring"/>
</bean>
```

### 3.3 import

​	一般用于团队开发，可以将多个配置文件导入合并为一个，如导入到统一的applicationContext.xml中

```xml
<import resource="bean1.xml"/>
<import resource="bean2.xml"/>
<import resource="bean3.xml"/>
```

---

## 4. 依赖注入

- 依赖：bean对象的创建依赖于容器
- 注入：bean对象的属性由容器来注入

### 4.1 构造器注入

​	见2.1

### 4.2 Set注入

​	复杂环境：

```java
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
```

```java
public class Student {
    private String name;
    private Address addr;
    private String[] books;
    private List<String> hobbies;
    private Map<String ,String> cards;
    private Set<String> games;
    private String values;
    private Properties info;

    public Student() {
    }
    //...getter&setter、toString
}
```

​	各种注入：

```xml
<bean id="address" class="com.pro.pojo.Address">
    <property name="addr" value="London"/>
</bean>
<bean id="student" class="com.pro.pojo.Student" >
    <!--普通值注入，value-->
    <property name="name" value="lam"/>
    <!--Bean注入，ref-->
    <property name="addr" ref="address"/>
    <!--数组注入，array-->
    <property name="books">
        <array>
            <value>1984</value>
            <value>百年孤独</value>
            <value>Data Structures</value>
        </array>
    </property>
    <!--List注入-->
    <property name="hobbies">
        <list>
            <value>music</value>
            <value>coding</value>
        </list>
    </property>
    <!--Map注入-->
    <property name="cards">
        <map>
            <entry key="IP" value="1997"/>
            <entry key="IC" value="1007"/>
        </map>
    </property>
    <!--set注入-->
    <property name="games">
        <set>
            <value>DOTA2</value>
            <value>CS:GO</value>
        </set>
    </property>
    <!--null注入-->
    <property name="values">
        <null/>
    </property>
    <!--properties注入-->
    <property name="info">
        <props>
            <prop key="学号">2016302442</prop>
            <prop key="身高">185cm</prop>
        </props>
    </property>
</bean>
```

### 4.3 拓展方式注入

​	p-namespace注入（set方式）：

```xml
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:p="http://www.springframework.org/schema/p" 
       xsi:schemaLocation="http://www.springframework.org/schema/beans
    https://www.springframework.org/schema/beans/spring-beans.xsd">
		<!--上方第三行为添加的约束-->

    <bean id="user" class="com.pro.pojo.User" p:name="lam" p:id="2016"/>

</beans>
```

​	c-namespace注入（有参构造）：

```xml
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:c="http://www.springframework.org/schema/c"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
    https://www.springframework.org/schema/beans/spring-beans.xsd">
		<!--上方第三行为添加的约束-->
    <bean id="user2" class="com.pro.pojo.User" c:name="ran" c:id="2018"/>

</beans>
```

### 4.4 bean的作用域

1. 单例模式 singleton（默认）：创建时共享一个对象

```xml
<bean id="user" class="com.pro.pojo.User" p:name="lam" p:id="2016" scope="singleton"/>
```

2. 原型模式 prototype：每次get都会产生新对象

```xml
<bean id="user" class="com.pro.pojo.User" p:name="lam" p:id="2016" scope="prototype"/>
```

3. request、session、applicaiton等：web开发中使用

---

## 5. bean的自动装配

​	Spring在上下文中查找，自动给bean装配属性

- 三种装配方式
	- xml中显式配置
	- Java中显式配置
	- 隐式自动装配

### 5.1 环境搭建

```xml
<bean id="cat" class="com.pro.pojo.Cat"/>
<bean id="dog" class="com.pro.pojo.Dog"/>
<bean id="human" class="com.pro.pojo.Human">
    <property name="name" value="ran"/>
    <property name="cat" ref="cat"/>
    <property name="dog" ref="dog"/>
</bean>
```

### 5.2 bean标签的autowire

​	byName：在容器上下文中查找与对象中set名称相同的bean id，需保证id唯一且一致

```xml
<bean id="cat" class="com.pro.pojo.Cat"/>
<bean id="dog" class="com.pro.pojo.Dog"/>
<bean id="human" class="com.pro.pojo.Human" autowire="byName">
    <property name="name" value="ran"/>
</bean>
```

​	byType：在容器上下文中查找与对象中属性类型相同的bean，需保证class唯一

```xml
<bean class="com.pro.pojo.Cat"/>
<bean class="com.pro.pojo.Dog"/>
<bean id="human" class="com.pro.pojo.Human" autowire="byType">
    <property name="name" value="ran"/>
</bean>
```

### 5.3 注解自动装配

​	使用注解：导入context约束，配置注解支持

​	annotation-config:支持注解

​	component-scan:扫描指定的包,注解生效

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd">
    <context:annotation-config/>
    <context:component-scan base-package="com.pro.pojo"/>
    <bean id="cat" class="com.pro.pojo.Cat"/>
    <bean id="dog" class="com.pro.pojo.Dog"/>
    <bean id="human" class="com.pro.pojo.Human"/>

</beans>
```

​	@Autowired：添加在成员或set方法前，通过byType实现,required属性为false时，说明对象可为null

​	如果自动装配环境比较复杂，可以使用@Qualifier(value="...")来指定唯一的bean对象注入

```java
public class Human {
    @Autowired
    private Cat cat;
    private String name;
    private Dog dog;
    @Autowired
    public void setDog(Dog dog) {
        this.dog = dog;
    }
    //...
}
    
```

​	@Resource(name="...") : Java自带注解,通过byName实现,找不到名字则转为byType

​	@Nullable: 标记一个字段,指明其可以取null

---

## 6. 使用注解开发

​	最佳实践:用xml管理bean,用注解配置其属性

### 6.1 注解

 - @Component: 在实体类上添加,相当于将某个类注册到Spring中,创建了一个bean,名字默认为类名小写,有衍生注解
		- dao层: @Repository
		- service层: @Service
		- controller层: @Controller
 - @Value: 在成员上添加,相当于设置该属性
 - @Scope: 同之前的scope属性

---

## 7. 使用Java配置Spring

###  7.1 @Configuration

- @Configuration是一个Component,也会被注册到容器中,它表示该类是一个配置类,相当于bean.xml
- @Bean相当于注册一个bean,方法名相当于id,返回值相当于class属性
- @ComponentScan相当于之前的扫描
- @Import:把另外的类引入,合并

​	实体类:

```java
public class User {
    public String name;

    public String getName() {
        return name;
    }
    @Value("lam")
    public void setName(String name) {
        this.name = name;
    }
}
```

​	编写MyConfig类:

```Java
@Configuration
public class MyConfig {

    @Bean
    public User getUser(){
        return new User();
    }
}
```

​	测试:

```java
@Test
public void test(){
    ApplicationContext context = new AnnotationConfigApplicationContext(MyConfig.class);
    User user = (User) context.getBean("getUser");
    System.out.println(user.name);
}
```

---

## 8. 代理模式

​	好处：

- 真实角色无需关注公共业务，操作更加纯粹
- 公共业务交给代理，实现分工
- 公共业务发生扩展，便于集中管理

### 8.1 静态代理

​	角色分析：

- 抽象角色 --- 接口或抽象类

```java
public interface Rent {
    void rent();
}
```

- 真实角色 --- 被代理的

```Java
public class Host implements Rent{
  	//只想单纯完成的事
    public void rent(){
        System.out.println("renting...");
    }
}
```

- 代理角色 --- 进行代理的

```java
public class Proxy implements Rent{
    private Host host;
    public Proxy(){}
    public Proxy(Host host) {
        this.host = host;
    }
  	//代理的方法
    public void rent(){
        this.host.rent();
    }
  	//自身额外的方法
    public void extraAction(){
        System.out.println("do sth else...");
    }
}
```

- 客户 --- 访问代理的

```java
public class Client {
    public static void main(String[] args) {
      	//有一个host
        Host host=new Host();
      	//这个host的代理
        Proxy proxy =new Proxy(host);
      	//帮host做事
        proxy.rent();
        proxy.extraAction();
    }
}
```

​	缺点：一个真实角色就需要一个代理，开发效率变低

### 8.2 动态代理

​	动态生成代理

- 基于接口：JDK的原生动态代理
- 基于类：cglib
- 基于Java字节码：Javasist

​	流程：

​	用ProxyInvocationHandler类，自动生成代理类（万能）：

```java
public class ProxyInvocationHandler implements InvocationHandler {
    //被代理的接口
    private Object target;

    public void setTarget(Object target) {
        this.target = target;
    }
    //生成得到代理类
    public Object getProxy(){
        return Proxy.newProxyInstance(this.getClass().getClassLoader(), 
                                      target.getClass().getInterfaces(), this);
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
```

​	Client类：

```java
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
```

​	好处：

- 一个动态代理代理的是一个接口，对应一类业务
- 只要实现了同一个接口，可以代理多个类

---

## 9. AOP

### 9.1 AOP in Spring

- 面向切面编程AOP（Aspect Oriented Programming），在程序开发中主要用来解决一些系统层面上的问题，比如日志，事务，权限等待，在不改变原有的逻辑的基础上，增加一些额外的功能。代理也是这个功能，读写分离也能用aop来做。AOP可以说是面向对象编程OOP（Object Oriented Programming）的补充和完善
- 它利用一种称为“横切”的技术，剖解开封装的对象内部，并将那些影响了多个类的公共行为封装到一个可重用模块，并将其命名为“Aspect”，即切面

​	相关概念：

1. 横切关注点：对哪些方法进行拦截，拦截后怎么处理，这些关注点称之为横切关注点

 	2. Aspect(切面):通常是一个类，里面可以定义切入点和通知
 	3. JointPoint(连接点):程序执行过程中明确的点，一般是方法的调用。被拦截到的点，因为Spring只支持方法类型的连接点，所以在Spring中连接点指的就是被拦截到的方法，实际上连接点还可以是字段或者构造器
 	4. Advice(通知):AOP在特定的切入点上执行的增强处理，有before(前置),after(后置),afterReturning(最终),afterThrowing(异常),around(环绕)
 	5. Pointcut(切入点):就是带有通知的连接点，在程序中主要体现为书写切入点表达式
 	6. Weave(织入)：将切面应用到目标对象并导致代理对象创建的过程
 	7. Introduction(引入)：在不修改代码的前提下，引入可以在**运行期**为类动态地添加一些方法或字段
 	8. AOP代理(AOP Proxy)：AOP框架创建的对象，代理就是目标对象的加强。Spring中的AOP代理可以使JDK动态代理，也可以是CGLIB代理，前者基于接口，后者基于子类
 	9.  目标对象（Target Object）: 包含连接点的对象。也被称作被通知或被代理对象

### 9.2 Spring实现AOP

​	导入依赖：

```xml
<!-- https://mvnrepository.com/artifact/org.aspectj/aspectjweaver -->
<dependency>
    <groupId>org.aspectj</groupId>
    <artifactId>aspectjweaver</artifactId>
    <version>1.9.6</version>
</dependency>
```

​	方法一：使用Spring的API接口

```xml
<bean id="userService" class="com.pro.service.UserServiceImpl"/>
<bean id="beforeLog" class="com.pro.log.BeforeLog"/>
<bean id="afterLog" class="com.pro.log.AfterLog"/>
<!--配置aop-->
<aop:config>
    <!--切入点,execution(要执行的位置)-->
    <aop:pointcut expression="execution(* com.pro.service.UserServiceImpl.*(..))" id="pointcut"/>
    <!--执行环绕增加-->
    <aop:advisor advice-ref="beforeLog" pointcut-ref="pointcut"/>
    <aop:advisor advice-ref="afterLog" pointcut-ref="pointcut"/>
</aop:config>
```

```Java
public class BeforeLog implements MethodBeforeAdvice {
    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println(target.getClass().getName()+"的"+method.getName()+"方法被执行了");
    }
}
```

```Java
public class AfterLog implements AfterReturningAdvice {
    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        System.out.println("执行了"+method.getName()+"方法，返回结果为"+returnValue);
    }
}
```

方法二：使用自定义类

```java
public class Own {
    public void before() {
        System.out.println("before...");
    }

    public void after() {
        System.out.println("after...");
    }
}
```

```xml
<bean id="own" class="com.pro.own.Own"/>
<aop:config>
  	<!--自定义切面-->
    <aop:aspect ref="own">
      	<!--切入点-->
        <aop:pointcut id="pointcut" expression="execution(* com.pro.service.UserServiceImpl.*(..))"/>
     		<!--通知-->
        <aop:before method="before" pointcut-ref="pointcut"/>
        <aop:after method="after" pointcut-ref="pointcut"/>
    </aop:aspect>
</aop:config>
```

​	方法三：使用注解

```java
@Aspect
public class AnnoPointcut {
    @Before("execution(* com.pro.service.UserServiceImpl.*(..))")
    public void before() {
        System.out.println("before...");
    }
    @After("execution(* com.pro.service.UserServiceImpl.*(..))")
    public void after() {
        System.out.println("after...");
    }
    @Around("execution(* com.pro.service.UserServiceImpl.*(..))")
    public void around(ProceedingJoinPoint joinPoint) throws Throwable{
        System.out.println("around before...");
        Object proceed = joinPoint.proceed();
        System.out.println("around after...");
    }
}
```

```xml
    <bean id="annoPointcut" class="com.pro.own.AnnoPointcut"/>
		<!--启用注解支持，JDK（默认），cglib（proxy-target-class属性设置为true）-->
    <aop:aspectj-autoproxy/>
```

---

## 10. 整合MyBatis

### 10.1 方式一

1. 数据源、sqlSessionFactory、sqlSessionTemplate配置，建议固定在一个spring-dao.xml中
	- sqlSessionTemplate的名字写为sqlSession

```xml
<?xml version="1.0" encoding="GBK"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/aop
        https://www.springframework.org/schema/aop/spring-aop.xsd">

    <!--使用spring的数据源替换mybatis的配置,使用spring提供的jdbc-->
    <bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
        <property name="driverClassName" value="com.mysql.cj.jdbc.Driver"/>
        <property name="url" value="jdbc:mysql://localhost:3306/mybatis?useSSL=true&amp;useUnicode=true&amp;characterEncoding=UTF-8&amp;serverTimezone = GMT"/>
        <property name="username" value="root"/>
        <property name="password" value="root"/>
    </bean>
    
    <!--sqlSessionFactory-->
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
    <property name="dataSource" ref="dataSource"/>
    <!--绑定mybatis配置文件-->
    <property name="configLocation" value="classpath:mybatis-config.xml"/>
    <property name="mapperLocations" value="classpath:com/pro/dao/*.xml"/>
    </bean>

    <bean id="sqlSession" class="org.mybatis.spring.SqlSessionTemplate">
    <!--只能用构造器注入，因为它没有set方法-->
    <constructor-arg index="0" ref="sqlSessionFactory"/>
    </bean>
</beans>
```

2. UserMapper.xml及其实现类，把sqlSession注入

```xml
<?xml version="1.0" encoding="UTF8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.pro.dao.UserMapper">
    <select id="selectUser" resultType="user">
        select * from mybatis.user
    </select>
</mapper>
```

```Java
public class UserMapperImpl implements UserMapper{
    private SqlSessionTemplate sqlSession;

    public void setSqlSession(SqlSessionTemplate sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public List<User> selectUser() {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        return mapper.selectUser();
    }
}
```

3. 测试

```java
public class MyTest {
    @Test
    public void test(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserMapper userMapper = context.getBean("userMapper",UserMapper.class);
        for (User user : userMapper.selectUser()) {
            System.out.println(user);
        }
    }
}
```

### 10.2 方式二

1. 精简的实现类，继承父类SqlSessionDaoSupport，因此不用在spring-dao.xml中配置sqlSession

```Java
public class UserMapperImpl2 extends SqlSessionDaoSupport implements UserMapper {
    @Override
    public List<User> selectUser() {
        return getSqlSession().getMapper(UserMapper.class).selectUser();
    }
}
```

2. applicationContext.xml

```xml
<bean id="userMapper2" class="com.pro.dao.UserMapperImpl2">
    <property name="sqlSessionFactory" ref="sqlSessionFactory"/>
</bean>
```

---

## 11. 声明式事务

​	与其不同的是编程式事务，在代码中进行管理

### 11.1 事务的ACID原则

- **原子性 Atomicity**：要么全部成功，要么全部失败
- **一致性Consistency**：事务的执行不能破坏数据库的完整性和一致性
- **隔离性Isolation**：并发的事务是互相隔离的，不能互相干扰
- **持久性Durability**：事务一旦提交，对数据的改变是持久化的保存的

### 11.2 声明式事务的配置

​	在spring-dao.xml中进行配置：

```xml
<!--配置声明式事务-->
<bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
    <constructor-arg ref="dataSource"/>
</bean>
<!--结合AOP-->
<!--配置事务的类,导入tx约束-->
<tx:advice id="txAdvice" transaction-manager="transactionManager">
    <!--给哪些方法配置事务,以及传播特性-->
    <tx:attributes>
        <tx:method name="*" propagation="REQUIRED"/>
    </tx:attributes>
</tx:advice>
<!--配置事务切入-->
<aop:config>
    <aop:pointcut id="pointcut" expression="execution(* com.pro.dao.*.*(..))"/>
    <aop:advisor advice-ref="txAdvice" pointcut-ref="pointcut"/>
</aop:config>
```