# SpringMVC



---



## 1. 回顾

### 1.1 MVC

​	**MVC**：Model View Controller，是模型(model)－视图(view)－控制器(controller)的缩写，一种软件设计典范，将业务、数据、显示分离实现，降低了视图与业务逻辑之间的双向耦合

​	经典MVC：M（JavaBean）V（JSP）C（Servlet）

​	Model：包含数据与行为

​	View：进行模型展示，用户看到的界面

​	Controller：调度，接受用户请求并传给模型处理，处理完毕后返回给视图进行展示

### 1.2 Servlet

​	创建新maven项目，删除src文件夹

1. 导入依赖

```xml
<dependencies>

    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-webmvc</artifactId>
        <version>5.2.8.RELEASE</version>
    </dependency>
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>4.11</version>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>javax.servlet</groupId>
        <artifactId>javax.servlet-api</artifactId>
        <version>4.0.1</version>
        <scope>provided</scope>
    </dependency>
    <dependency>
        <groupId>javax.servlet.jsp</groupId>
        <artifactId>javax.servlet.jsp-api</artifactId>
        <version>2.3.3</version>
        <scope>provided</scope>
    </dependency>
    <dependency>
        <groupId>javax.servlet</groupId>
        <artifactId>jstl</artifactId>
        <version>1.2</version>
    </dependency>


</dependencies>
```

2. 创建子项目，Add Framework Support，添加Web Application
3. 编写Servlet类，处理用户请求，重写doGet、doPost方法

```java
public class HelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取前端参数
        String method=req.getParameter("method");
        if(method.equals("add")){
            req.getSession().setAttribute("msg","add...");
        }
        if(method.equals("delete")){
            req.getSession().setAttribute("msg","delete...");
        }
        //2.调用业务层
        //3.视图转发或重定向
        req.getRequestDispatcher("/WEB-INF/jsp/test.jsp").forward(req,resp);//转发
        //resp.sendRedirect(); //重定向
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
```

4. 在web.xml中注册该servlet

```xml
<servlet>
    <servlet-name>hello</servlet-name>
    <servlet-class>com.pro.servlet.HelloServlet</servlet-class>
</servlet>
<servlet-mapping>
    <servlet-name>hello</servlet-name>
    <url-pattern>/hello</url-pattern>
</servlet-mapping>
```

5. 配置TOMCAT，创建表单页面进行测试

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Form</title>
</head>
<body>
  	<!--注意调用时通过地址栏“?method=...”进行-->
    <form action="/hello" method="post">
        <input type="text" name="method">
        <input type="submit">
    </form>
</body>
</html>
```

---

## 2. 认识SpringMVC

- SpringMVC的框架以请求为驱动，围绕一个中心DispatcherServlet来分派请求和提供其他功能，它继承自HttpServlet基类

### 2.1 Hello SpringMVC

​	原理实现：

1. 新建项目，添加web支持，导入依赖

 	2. 配置web.xml，注册DispatcherServlet

```xml
<servlet>
    <servlet-name>springmvc</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    <!--关联SpringMVC配置文件-->
    <init-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>classpath:springmvc-servlet.xml</param-value>
    </init-param>
    <load-on-startup>1</load-on-startup>
</servlet>
    <!--/*匹配所有请求，包括（.jsp）-->
    <!--/匹配所有请求，不包括（.jsp）-->
<servlet-mapping>
    <servlet-name>springmvc</servlet-name>
    <url-pattern>/</url-pattern>
</servlet-mapping>
```

3. 实现Controller接口

```java
public class HelloController implements Controller {
    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView();
      	//调用业务层
        //封装模型放入mv
        mv.addObject("msg","HelloSpringMVC");
        //封装要跳转的视图
        mv.setViewName("hello");
        return mv;
    }
}
```

4. springmvc-servlet.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://www.springframework.org/schema/beans
         http://www.springframework.org/schema/beans/spring-beans.xsd">
		<!--处理器映射器、适配器-->
    <bean class="org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping"/>
    <bean class="org.springframework.web.servlet.mvc.SimpleControllerHandlerAdapter"/>
    <!--视图解析器-->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver" name="InternalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/jsp/"/>
        <property name="suffix" value=".jsp"/>
    </bean>
  	<!--BeanNameUrlHandlerMapping的需求-->
    <bean id="/hello" class="com.pro.controller.HelloController"/>
</beans>
```

5. hello.jsp文件测试

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
${msg}
</body>
</html>
```

​	404问题解决：在Artifacts中的具体模块下增加lib目录，并导入包

### 2.2 流程简要分析

- DispatcherServlet表示前置控制器，是SpringMVC的控制中心，它接受并拦截用户的请求
- HandlerMapping 处理器映射，被DispatcherServlet调用，根据url查找Handler
- HandlerExecution为具体的Handler，根据url查找控制器，hello，将解析后的信息返回DS
- HandlerAdapter 处理器适配器，按照特定的规则执行Handler
- Handler交给具体的Controller执行
- Controller调用业务层，封装模型和视图并返回给HandlerAdapter，mv，HandlerAdapter再返回DS
- DS调用视图解析器，获取了mv的数据，解析了mv的试图名字（setViewName（）），拼接试图名字找到对应视图
- DS根据视图解析结果，调用具体视图，视图呈现给用户

## 3. 使用注解开发

### 3.1 流程

1. 配置环境，在Artifacts中导入jar包，web.xml
2. springmvc-servlet.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd
        http://www.springframework.org/schema/mvc
        https://www.springframework.org/schema/mvc/spring-mvc.xsd">
    <!--自动扫描包-->
    <context:annotation-config/>
    <context:component-scan base-package="com.pro.controller"/>

    <!--让SpringMVC不处理静态资源-->
    <mvc:default-servlet-handler/>
    <!--支持注解驱动，并自动完成映射器和适配器的注入-->
    <mvc:annotation-driven/>
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver" name="InternalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/jsp/"/>
        <property name="suffix" value=".jsp"/>
    </bean>

</beans>
```

 	3. 通过注解配置Controller类，完善视图和Controller的对应

```java
@Controller
public class HelloController {
  	//真实访问地址
    @RequestMapping("/hello")
    public String hello(Model model){
        //封装数据
        model.addAttribute("msg","HelloSpringMVC");
        //会被视图解析器处理
        return "hello";
    }
}
```

4. 创建视图层，测试

​	在SpringMVC中，利用注解驱动自动配置映射器和适配器，只需手动配置视图解析器

​	@RequestBody:return时不走视图解析器，返回字符串

​	@RestController：同样作用

### 3.2 相对完善的xml

​	web.xml

```xml
<?xml version="1.0" encoding="GBK"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">
    <servlet>
        <servlet-name>springmvc</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <!--关联SpringMVC配置文件-->
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:springmvc-servlet.xml</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>
    <!--/*匹配所有请求，包括（.jsp）-->
    <!--/匹配所有请求，不包括（.jsp）-->
    <servlet-mapping>
        <servlet-name>springmvc</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>
</web-app>
```

​	springmvc-servlet.xml

```xml
<?xml version="1.0" encoding="GBK"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd
        http://www.springframework.org/schema/mvc
        https://www.springframework.org/schema/mvc/spring-mvc.xsd">
    <!--自动扫描包-->
    <context:annotation-config/>
    <context:component-scan base-package="com.pro.controller"/>

    <!--让SpringMVC不处理静态资源-->
    <mvc:default-servlet-handler/>
    <!--支持注解驱动，并自动完成映射器和适配器的注入-->
    <mvc:annotation-driven/>
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver" name="InternalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/jsp/"/>
        <property name="suffix" value=".jsp"/>
    </bean>

</beans>
```

---

## 4. RESTful风格

### 4.1 什么是RESTful

​	REST：Representational State Transfer（表象层状态转变），特点：

1. 每一个URI代表一种资源

2. 客户端和服务器之间，传递这种资源的某种表现层

3. 客户端通过HTTP动词（get、post、put、delete等），对服务器端资源进行操作，实现”表现层状态转化”
4. 简洁、高效，支持缓存、安全

### 4.2 实践

1. 创建RESTfulController测试类，test1普通方式，test2使用RESTful风格

```Java
@Controller
public class RESTfulController {
    @RequestMapping("/add")
    public String test1(int a, int b, Model model){
        //此时传参：localhost:8080/add?a=1&b=2
        int res=a+b;
        model.addAttribute("msg","结果为"+res);
        return "test";
    }
    @RequestMapping(value="/sub/{a}/{b}",method = RequestMethod.GET)
    public String test2(@PathVariable int a,@PathVariable int b, Model model){
        //此时传参：localhost:8080/add?a=1&b=2
        int res=a-b;
        model.addAttribute("msg","结果为"+res);
        return "test";
    }
}
```

​	通过在RequestMapping中配置value，method等属性，限定执行方式

​	或@GetMapping("/sub/{a}/{b}")的变体代替上述配置

---

## 5. 重定向和转发

### 5.1 视图解析器方式

​	见2.1

### 5.2 Servlet API

​	不需要视图解析器，也可通过之前的servlet方式实现

```java
@Controller
public class TEST {
    @RequestMapping("/sub")
    public void test2(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().println("hello");
    }
}
```

### 5.3 SpringMVC实现

​	在测试前注释掉视图解析器，通过在return参数前添加forward或redirect声明转发或重定向

```Java
@RequestMapping("/sub")
public String  test2(){
    return "forward:/WEB-INF/jsp/hello.jsp";
}
```

​	如果设置了视图解析器，默认没有声明的方式是转发，使用重定向要添加redirect声明

---

## 6. 数据处理

### 6.1 处理提交数据

- 提交的域名称与方法的参数名一致

	提交：http://localhost:8080/hello?name=lam

```Java
@RequestMapping("/hello")
public String  hello(String name){
    System.out.println(name);
    return "hello";
}
```

- 提交的域名称与方法的参数名不一致，使用注解

	提交：http://localhost:8080/hello?username=lam

```Java
@RequestMapping("/hello")
public String  hello(@RequestParam("username") String name){
    System.out.println(name);
    return "hello";
}
```

- 提交的是对象

	提交：封装成实体类后 http://localhost:8080/user?name=lam&id=1 ...

```java
@RequestMapping("/user")
public String  user(User user){
    System.out.println(user);
    return "hello";
}
```

### 6.2 数据回显到前端

1. 通过ModelAndView和Model：见之前
2. 通过ModelMap：继承了LinkedHashMap，Model是其精简版

---

## 7. 乱码问题

### 7.1 SpringMVC的过滤器

​	在web.xml中绑定：

```xml
<filter>
  	<filter-name>encoding</filter-name>
  	<filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
 	 	<init-param>
    <param-name>encoding</param-name>
    <param-value>utf-8</param-value>
  	</init-param>
</filter>
<filter-mapping>
  	<filter-name>encoding</filter-name>
  	<url-pattern>/*</url-pattern>
  	<!--/*包含jsp页面-->
</filter-mapping>
```

​    注意：urlpattern要使用/*过滤

---

## 8. JSON

​	JSON（JavaScript Object Notation，JS对象标记）是一种轻量级的数据交换格式，采用独立于编程语言的文本格式来存储和表示数据，易于机器和人解析，有效提升网络传输效率

### 8.1 语法格式

- 对象表示为键值对，逗号分隔数据，花括号
- 数组用方括号

```jso
{"name"："lam"}
{"gender"："male"}
```

### 8.2 测试

​	JavaScript和JSON的互相转换：

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>JSON</title>
    <script type="text/javascript">
        //创建js对象
        var user = {
            name: "lam";
            age: 3,
            gender: "male"
        };
        // 将js对象转换为json对象
        var json = JSON.stringify(user);
        // 反向转换
        var obj = JSON.parse(json);
    </script>
</head>
<body>
</body>
</html>
```

### 8.3 Jackson

​	1. 导入依赖

```xml
<!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind -->
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.11.2</version>
</dependency>
```

2. 测试类

```java
@Controller
public class UserController {
		//解决乱码
    @RequestMapping(value = "/json",produces = "application/json;charset=utf-8")
    @ResponseBody
    public String jsonTest() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        User user = new User("冉", 7, "female");
        String str = mapper.writeValueAsString(user);
        return user.toString();
    }

}
```

3. 代码优化：在springmvc-servlet.xml中解决乱码问题

```xml
<mvc:annotation-driven>
    <mvc:message-converters register-defaults="true">
        <bean class="org.springframework.http.converter.StringHttpMessageConverter">
            <constructor-arg value="UTF-8"/>
        </bean>
        <bean class="org.springframework.http.converter.json.MappingJackson2HttpMessageConverter">
            <property name="objectMapper">
                <bean class="org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean">
                    <property name="failOnEmptyBeans" value="false"/>
                </bean>
            </property>
        </bean>
    </mvc:message-converters>
</mvc:annotation-driven>
```

4. 解析List测试

```Java
@RequestMapping("/json2")
public String jsonTest2() throws JsonProcessingException {
    User user1 = new User("冉", 7, "female");
    User user2 = new User("lam", 7, "male");
    User user3 = new User("ran", 7, "female");
    User user4 = new User("rem", 8, "female");
    List<User> list =new ArrayList<>();
    list.add(user1);
    list.add(user2);
    list.add(user3);
    list.add(user4);
    return new ObjectMapper().writeValueAsString(list);
}
```

5. 解析时间测试

```Java
@RequestMapping(value = "/json3")
public String jsonTest3() throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    //不使用时间戳
    mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    mapper.setDateFormat(sdf);
    Date date =new Date();
    return mapper.writeValueAsString(date);
}
```

6. 通过工具类实现

```Java
public class JsonUtils {
    public static String formatDate(Object obj){
        return formatDate(obj,"yyyy-MM-dd HH:mm:ss");
    }
    public static String formatDate(Date date,String dateFormat){
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false);
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        mapper.setDateFormat(sdf);
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
```

### 8.4 Fastjson

1. 导入依赖

```xml
<!-- https://mvnrepository.com/artifact/com.alibaba/fastjson -->
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>fastjson</artifactId>
    <version>1.2.73</version>
</dependency>
```

2. 测试类

```java
@RequestMapping("/json4")
public String jsonTest4() throws JsonProcessingException {
    User user1 = new User("冉", 7, "female");
    User user2 = new User("lam", 7, "male");
    List<User> list =new ArrayList<>();
    list.add(user1);
    list.add(user2);
    String s = JSON.toJSONString(list);
    return s;
}
```

3. 问题 java.lang.ClassNotFoundException：在lib中添加新导入的包

---

## 9. 整合SSM示例项目

### 9.1 环境配置

1. 建立新项目SSMBuild，连接数据库，注意在url中设置时区
2. 导入依赖 dependencies
3. 在build标签中处理静态资源导出问题和maven插件问题
4. 建立包的层次结构（dao、service、pojo、controller、utils、filter...）
5. 配置文件（mybatis-config.xml、applicationContext.xml、spring-dao.xml等xml文件、database.properties）

### 9.2 MyBatis层

1. mybatis-config.xml中，首先增加别名

```xml
<?xml version="1.0" encoding="UTF8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <typeAliases>
        <package name="com.pro.pojo"/>
    </typeAliases>
</configuration>
```

2. 创建实体类

```java
public class Books {
    private int id;
    private String bookName;
    private int bookCounts;
    private String detail;
  	//...生成方法
}
```

3. Dao层创建BookMapper接口，提供抽象接口操作

```java
public interface BookMapper {
    int addBook(Books book);
    int deleteBookById(@Param("bookID") int bookID);
    int updateBook(Books book);
    Books queryBookById(@Param("bookID") int bookID);
    List<Books> queryAllBooks();
}
```

4. 编写其对应的BookMapper.xml，在其中实现SQL语句

```xml
<?xml version="1.0" encoding="UTF8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.pro.dao.BookMapper">
    <insert id="addBook" parameterType="Books">
        insert into ssmbuild.books (bookName,bookCounts,detail)
        values (#{bookName},#{bookCounts},#{detail})
    </insert>

    <delete id="deleteBookById" parameterType="int">
        delete from ssmbuild.books where bookID=#{bookID}
    </delete>

    <update id="updateBook" parameterType="books">
        update ssmbuild.books
        set bookName=#{bookName},bookCounts=#{bookCounts},detail=#{detail}
        where bookID=#{bookID}
    </update>

    <select id="queryBookById" resultType="Books">
        select * from ssmbuild.books where bookID=#{bookID}
    </select>

    <select id="queryAllBooks" resultType="Books">
        select * from ssmbuild.books
    </select>

</mapper>
```

5. 把Book Mapper.xml绑定到配置文件中

```xml
<mappers>
    <mapper class="com.pro.dao.BookMapper"/>
</mappers>
```

6. 在业务层创建接口BookService

```java
public interface BookService {
    int addBook(Books book);
    int deleteBookById(int bookID);
    int updateBook(Books book);
    Books queryBookById(int bookID);
    List<Books> queryAllBooks();
}
```

7. 编写BookServiceImpl实现类，业务层调用dao层，提供setter方法供spring注入

```Java
public class BookServiceImpl implements BookService{
    private BookMapper bookMapper;

    public void setBookMapper(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }
    public int addBook(Books book) { return bookMapper.addBook(book); }
    public int deleteBookById(int bookID) { return bookMapper.deleteBookById(bookID);}
    public int updateBook(Books book) { return bookMapper.updateBook(book);}
    public Books queryBookById(int bookID) { return bookMapper.queryBookById(bookID); }
    public List<Books> queryAllBooks() { return bookMapper.queryAllBooks();}
}
```

### 9.3 Spring层

1. 编写spring-dao.xml，关联数据库配置文件，引入连接池，sqlSessionFactory

```xml
<?xml version="1.0" encoding="GBK"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd">
    
    <!--关联数据库配置文件-->
    <context:property-placeholder location="classpath:database.properties"/>
    <!--连接池-->
    <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
        <property name="driverClass" value="${jdbc.driver}"/>
        <property name="jdbcUrl" value="${jdbc.url}"/>
        <property name="user" value="${jdbc.username}"/>
        <property name="password" value="${jdbc.password}"/>
        <property name="maxPoolSize" value="30"/>
        <property name="minPoolSize" value="10"/>
        <property name="autoCommitOnClose" value="false"/>
        <property name="checkoutTimeout" value="10000"/>
        <property name="acquireRetryAttempts" value="2"/>
    </bean>

    <!--sqlSessionFactory-->
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="dataSource" ref="dataSource"/>
        <!--绑定mybatis配置文件-->
        <property name="configLocation" value="classpath:mybatis-config.xml"/>
    </bean>

    <!--配置dao接口扫描包，动态实现接口的注入-->
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory"/>
        <property name="basePackage" value="com.pro.dao"/>
    </bean>
    
</beans>
```
​	2. 编写spring-service.xml，实现分工

```xml
<?xml version="1.0" encoding="GBK"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/aop
        https://www.springframework.org/schema/aop/spring-aop.xsd
        http://www.springframework.org/schema/tx
        https://www.springframework.org/schema/tx/spring-tx.xsd">
  
    <!--扫描service下的包-->
    <context:component-scan base-package="com.pro.service"/>
    <!--通过配置或注解，把业务类注入到spring-->
    <bean id="BookServiceImpl" class="com.pro.service.BookServiceImpl">
        <property name="bookMapper" ref="bookMapper"/>
    </bean>
  	<!--声明式事务-->
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
</beans>
```

### 9.4 SpringMVC层

1. 添加web支持
2. 编写web.xml，配置DispatcherServlet和乱码过滤

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">
    <!--DispatcherServlet-->    
    <servlet>
        <servlet-name>springmvc</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <!--关联SpringMVC配置文件-->
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:spring-mvc.xml</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>
    <!--/*匹配所有请求，包括（.jsp）-->
    <!--/匹配所有请求，不包括（.jsp）-->
    <servlet-mapping>
        <servlet-name>springmvc</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>
    
    <!--乱码过滤-->
    <filter>
        <filter-name>encoding</filter-name>
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
        <init-param>
            <param-name>encoding</param-name>
            <param-value>utf-8</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>encoding</filter-name>
        <url-pattern>/*</url-pattern>
        <!--/*包含jsp页面-->
    </filter-mapping>
    
    <!--session过期时间-->
    <session-config>
        <session-timeout>15</session-timeout>
    </session-config>
    
</web-app>
```

3. 编写spring-mvc.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/mvc
       https://www.springframework.org/schema/mvc/spring-mvc.xsd
       http://www.springframework.org/schema/context
       https://www.springframework.org/schema/context/spring-context.xsd">

    <!--注解驱动-->
    <mvc:annotation-driven/>
    <!--静态资源过滤-->
    <mvc:default-servlet-handler/>
    <!--扫描包-->
    <context:component-scan base-package="com.pro.controller"/>
    <!--视图解析器-->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver" name="InternalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/jsp/"/>
        <property name="suffix" value=".jsp"/>
    </bean>
</beans>
```

4. 在applicationContext.xml中进行整合

```xml
<?xml version="1.0" encoding="GBK"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd">

        <import resource="classpath:spring-dao.xml"/>
        <import resource="classpath:spring-service.xml"/>
        <import resource="classpath:spring-mvc.xml"/>
</beans>
```

5. 在Artifacts中创建lib目录，导入所有jar包

---

## 10. 示例项目

### 10.1 查询所有书籍实现

1. 创建BookController类，调用service层，实现查询所有书籍并返回书籍页面

```Java
@Controller
@RequestMapping("/book")
public class BookController {
    @Autowired
    @Qualifier("bookServiceImpl")
    private BookService bookService;
    @RequestMapping("/allbooks")
    public String list(Model model){
        List<Books> list=bookService.queryAllBooks();
        model.addAttribute("list",list);
        return "allbooks";
    }
}
```

2. 首页设置跳转,导入bootstrap进行美化

```jsp
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All Books</title>
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 flex-column">
            <div class="page-header">
                <h1>
                    <small>全部书籍</small>
                </h1>
            </div>
        </div>
    </div>
    <div class="row clearfix">
        <div class="col-md-12 flex-column">
            <table class="table">
                <thead class="thead-dark">
                <tr>
                    <th scope="col">编号</th>
                    <th scope="col">书名</th>
                    <th scope="col">数量</th>
                    <th scope="col">详情</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="book" items="${list}">
                    <tr>
                        <td>${book.bookID}</td>
                        <td>${book.bookName}</td>
                        <td>${book.bookCounts}</td>
                        <td>${book.detail}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

</body>
</html>

```

### 10.2 添加书籍实现

1. 实现跳转到添加书籍页面

```Java
@RequestMapping("/toAddBook")
public String toAddBook() {
    return "toAddBook";
}
```

2. 创建添加书籍页面

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Book</title>
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<div>

    <div class="col-md-4 flex-column">
        <form action="${pageContext.request.contextPath}/book/addBook" method="post">
            <div class="form-group">
                <label for="bookName">书籍名称</label>
                <input type="text" name="bookName" class="form-control" id="bookName" required>
            </div>
            <div class="form-group">
                <label for="bookCounts">书籍数量</label>
                <input type="text" name="bookCounts" class="form-control" id="bookCounts" required>
            </div>
            <div class="form-group">
                <label for="detail">书籍详情</label>
                <input type="text" name="detail" class="form-control" id="detail" required>
            </div>
            <button type="submit" class="btn btn-primary">提交</button>
        </form>
    </div>

</div>
</body>
</html>
```

3. 在allBooks页面添加跳转按钮

```jsp
<div class="row">
    <div class="col-md-4 flex-column">
        <a  href="${pageContext.request.contextPath}/book/toAddBook">
            <button class="btn btn-primary">添加书籍</button>
        </a>
    </div>
</div>
```

4. 实现添加方法

```Java
@RequestMapping("/addBook")
public String addBook(Books book){
    bookService.addBook(book);
    return "redirect:/book/allBooks";
}
```

### 10.3 修改书籍实现

1. 方法实现

```java
//跳转至修改页面
@RequestMapping("/toUpdateBook")
public String toUpdateBook(int id,Model model) {
    Books book = bookService.queryBookById(id);
    model.addAttribute("QueryBook",book);
    return "toUpdateBook";
}
//重定向
@RequestMapping("/updateBook")
public String updateBook(Books book){
    bookService.updateBook(book);
    return "redirect:/book/allBooks";
}
//删除书籍
@RequestMapping("/deleteBook/{bookID}")
public String deleteBook(@PathVariable("bookID") int id){
    bookService.deleteBookById(id);
    return "redirect:/book/allBooks";
}
```

2. updateBook页面表单的变动:
	- 为bookID设置隐藏域,使其能正确传递
	- 通过后端给Model设置QueryBook属性,来获得每个属性的值

```jsp
<form action="${pageContext.request.contextPath}/book/updateBook" method="post">
    <input type="hidden" name="bookID" value="${QueryBook.bookID}">
    <div class="form-group">
        <label for="bookName">书籍名称</label>
        <input type="text" name="bookName" class="form-control" id="bookName" value="${QueryBook.bookName}" required>
    </div>
    <div class="form-group">
        <label for="bookCounts">书籍数量</label>
        <input type="text" name="bookCounts" class="form-control" id="bookCounts" value="${QueryBook.bookCounts}" required>
    </div>
    <div class="form-group">
        <label for="detail">书籍详情</label>
        <input type="text" name="detail" class="form-control" id="detail" value="${QueryBook.detail}" required>
    </div>
    <button type="submit" class="btn btn-primary">提交</button>
</form>
```

3. allBooks页面中添加删除和修改操作列

```jsp
<a href="${pageContext.request.contextPath}/book/toUpdateBook?id=${book.bookID}">修改</a>
&nbsp;|&nbsp;
<a href="${pageContext.request.contextPath}/book/deleteBook/${book.bookID}">删除</a>
```

### 10.4 搜索书籍实现

1. 新的业务,要在dao层进行添加

```Java
Books queryBookByName(@Param("bookName") String bookName);
```

```xml
<select id="queryBookByName" resultType="Books">
    select * from ssmbuild.books where bookName=#{bookName}
</select>
```

2. 更新service层

```java
Books queryBookByName(String bookName);
```

```java
public Books queryBookByName(String bookName) { return bookMapper.queryBookByName(bookName); }
```

3. Controller中实现

```java
@RequestMapping("/queryBook")
public String queryBook(String bookName,Model model){
    Books book = bookService.queryBookByName(bookName);
    List<Books> list =new ArrayList<>();
    list.add(book);
    model.addAttribute("list",list);
    return "allBooks";
}
```

4. allBooks网页中添加表单

```jsp
<div class="col-md-4 flex-column form-inline">
    <form action="${pageContext.request.contextPath}/book/queryBook" method="post" >
        <input type="text" name="bookName" class="form-control" placeholder="请输入要查询的书籍名称">
        <input type="submit" value="查询" class="btn btn-primary" >
    </form>
</div>
```

### 10.5 遇到的问题

1. 出现Mapped Statements collection already contains value for XXX 错误：Mapper.xml被多次扫描了,绑定配置文件时注意执行了几次
2. 配置数据库连接池时出现Access denied for user ''@'localhost' (using password: YES)错误:username是关键字,建议在database.properties文件中把所有属性用(jdbc.xxx)代替

3. 1字节的UTF-8序列的字节1无效:手动将< ? xml version=”1.0” encoding=”UTF-8”?>中的UTF-8更改成UTF8
4. 2字节的UTF-8序列的字节2无效: 将UTF8更改成GBK
5. NoSuchBeanDefinitionException: No qualifying bean of type 'com.pro.service.BookService' available错误: 配置的是spring-mvc.xml,里面没有service的bean,改成applicaiton.xml

```xml
<init-param>
    <param-name>contextConfigLocation</param-name>
    <param-value>classpath:applicationContext.xml</param-value>
</init-param>
```

6. 在实现修改书籍时,不能成功:默认没有传bookID,前端传递隐藏域
7. WEB-INF目录下的所有资源，只能通过Controller或Servlet访问

---

## 11. AJAX

​	AJAX（Asynchronous JavaScript and XML）

- 无需加载整个网页，同时更新部分网页
- 用于创建更好更强的Web应用程序
- 通过与服务器的少量数据交换，实现异步局部更新

### 11.1 初识AJAX

1. 在页面中导入jquery

```xml
<script src="https://cdn.bootcss.com/jquery/3.4.1/jquery.js"></script>
```

2. 设计一个前端元素，失去焦点时发起请求，携带信息到后台

```jsp
<body>
Username:<input type="text" id="username" onblur="a()">
</body>
```

3. 通过jquery.ajax发起一个请求

```jsp
<script>
    function a(){
        $.post({
            url:"${pageContext.request.contextPath}/a1",
            data:{"name":$("#username").val()},
            success:function (data){
                alert(data);
            },
            error:function (){

            }
        })
    }
</script>
```

4. 后端接受请求，提供字符串

```java
@RequestMapping("/a1")
public void a1(String name, HttpServletResponse response) throws IOException {
    System.out.println(name);
    if("lam".equals(name)){
        response.getWriter().println("true");
    }else {
        response.getWriter().println("false");
    }
}
```

### 11.2 异步加载数据

1. 测试方法

```Java
@RequestMapping("/a2")
public List<User> a2(){
    List<User> userList = new ArrayList<>();
    userList.add(new User("ll",1,"f"));
    userList.add(new User("rr",2,"m"));
    userList.add(new User("xx",3,"f"));
    return userList;
}
```

2. test.jsp，点击按钮加载数据

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="https://cdn.bootcss.com/jquery/3.4.1/jquery.js"></script>
    <script>
        $(function(){
            $("#btn").click(function (){
                $.post("${pageContext.request.contextPath}/a2",function (data){
                    var html="";
                    for (let i=0;i<data.length;i++){
                        html+="<tr>"+
                            "<td>"+data[i].name+"</td>"+
                            "<td>"+data[i].age+"</td>"+
                            "<td>"+data[i].sex+"</td>"+
                            "</tr>"
                    }
                    $("#content").html(html);
                })
            })
        });

    </script>
</head>
<body>
<input type="button" value="loading" id="btn">
<table>
    <tr>
        <td>Name</td>
        <td>Age</td>
        <td>Sex</td>
    </tr>
    <tbody id="content">

    </tbody>
</table>
</body>
</html>
```

### 11.3 登陆验证

1. 判断登录信息

```java
@RequestMapping("/a3")
public String a3(String name,String pwd){
    String msg="";
    if(name!=null){
        if("root".equals(name)){
            msg="ok";
        }else{
            msg="not ok";
        }
    }
    if(pwd!=null){
        if("root".equals(pwd)){
            msg="ok";
        }else{
            msg="not ok";
        }
    }
    return msg;
}
```

2. login.jsp

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="https://cdn.bootcss.com/jquery/3.4.1/jquery.js"></script>
    <script>
        function a1(){
            $.post({
                url:"${pageContext.request.contextPath}/a3",
                data:{"name":$("#name").val()},
                success:function (data){
                    if(data.toString()==='ok'){
                        $("#userInfo").css("color","green");
                    }
                    $("#userInfo").html(data);
                }
            })
        }
        function a2(){
            $.post({
                url:"${pageContext.request.contextPath}/a3",
                data:{"pwd":$("#pwd").val()},
                success:function (data){
                    if(data.toString()==='ok'){
                        $("#pwdInfo").css("color","green");
                    }
                    $("#pwdInfo").html(data);
                }
            })
        }

    </script>
</head>
<body>
  <p>
      Username:<input type="text" id="name" onblur="a1()">
      <span id="userInfo"></span>
  </p>
  <p>
      Password:<input type="text" id="pwd" onblur="a2()">
      <span id="pwdInfo"></span>
  </p>
</body>
</html>
```

---

## 12. 其他内容

### 12.1 拦截器

- 拦截器是AOP思想的应用
- 属于SpringMVC，只会拦截Controller的方法

1. 创建拦截器类，实现HandlerInterceptor接口，重写三个方法

```java
public class MyInterceptor implements HandlerInterceptor {
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //return true 执行下一个拦截器，放行，否则方法不执行
        System.out.println("before...");
        return true;
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("completed");
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("after...");
    }
}
```

2. 在applicationContext中配置拦截器

```xml
<mvc:interceptors>
    <mvc:interceptor>
        <!--**:所有请求-->
        <mvc:mapping path="/**"/>
        <bean class="com.pro.interceptor.MyInterceptor"/>
    </mvc:interceptor>
</mvc:interceptors>
```

	3. 登陆验证实现

```Java
@Controller
@RequestMapping("/user")
public class LoginController {
    @RequestMapping("/main")
    public String main(){
        return "main";
    }
    @RequestMapping("/toLogin")
    public String toLogin(){
        return "login";
    }
    @RequestMapping("/login")
    public String login(HttpSession session,String username, String password ){
        //把用户信息储存在session中
        session.setAttribute("userLogInfo",username);
        return "main";
    }
    //注销，移除节点
    @RequestMapping("/logOut")
    public String loginOut(HttpSession session){
        session.removeAttribute("userLogInfo");
        return "main";
    }
}
```

​	4. 拦截器需要被配置

```java
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        //判断何时放行,有session说明已经登陆，本身在登陆界面也放行
        if(request.getRequestURI().contains("toLogin")){
            return true;
        }
        //第一次传入。也没有session
        if(request.getAttribute("userLoginInfo")!=null){
            return true;
        }
        //
        if(request.getRequestURI().contains("login")){
            return true;
        }
        request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request,response);
        return false;
    }
}
```

### 12.2 文件上传和下载

1. 在applicationContext.xml中添加配置,maven中导入包

```xml
<dependency>
    <groupId>commons-fileupload</groupId>
    <artifactId>commons-fileupload</artifactId>
    <version>1.4</version>
</dependency>
```

```xml
<!--文件上传配置-->
<bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
    <property name="defaultEncoding" value="utf-8"/>
    <property name="maxUploadSize" value="10485760"/>
    <property name="maxInMemorySize" value="40960"/>
</bean>
```

2. 上传方法1 Controller

```java
package com.pro.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;

@RestController
public class FileController {
		//@RequestParam("file")将name=file的控件得到的文件封装成CMF对象，接收文件
    @RequestMapping("/upload")
    public String upload(@RequestParam("file") CommonsMultipartFile file, HttpServletRequest request) throws IOException {
        //获取文件名
        String uploadFilename=file.getOriginalFilename();
        //如果为空返回首页
        if("".equals(uploadFilename)){
            return "redirect:/index.jsp";
        }
        //上传路径设置
        String path=request.getServletContext().getRealPath("/upload");
        //不存在则创建一个
        File realPath=new File(path);
        if(!realPath.exists()){
            realPath.mkdir();
        }
        System.out.println("文件保存地址： "+realPath);
        InputStream is = file.getInputStream();//输入流
        OutputStream os = new FileOutputStream(new File(realPath, uploadFilename));//输出流
        //读取写出
        int len=0;
        byte[] buffer=new byte[1024];
        while ((len=is.read(buffer))!=-1){
            os.write(buffer,0,len);
            os.flush();
        }
        os.close();
        is.close();
        return "redirect:/index.jsp";
    }
}
```

3. 上传方法2 Controller

```java
@RequestMapping("/upload2")
public String upload2(@RequestParam("file") CommonsMultipartFile file, HttpServletRequest request) throws IOException {
    String path=request.getServletContext().getRealPath("/upload");
    //不存在则创建一个
    File realPath=new File(path);
    if(!realPath.exists()){
        realPath.mkdir();
    }
    System.out.println("文件保存地址： "+realPath);
    //通过CommonsMultipartFile的方法直接写文件
    file.transferTo(new File(realPath+"/"+file.getOriginalFilename()));
    return "redirect:/index.jsp";
}
```

4. 测试

```jsp
<form action="${pageContext.request.contextPath}/upload" enctype="multipart/form-data" method="post">
    <input type="file" name="file">
    <input type="submit" value="upload">
</form>
```

5. 下载 Controller

```java
@RequestMapping("/download")
public String download(HttpServletResponse response, HttpServletRequest request) throws IOException {
    //获取要下载的文件名和地址
    String path=request.getServletContext().getRealPath("/upload");
    String filename="Icy_Grass_by_Raymond_Lavoie.jpg";
    //设置response响应头
    response.reset();//清空buffer
    response.setCharacterEncoding("utf-8");
    response.setContentType("multipart/form-data");//二进制传输数据
    //设置响应头
    response.setHeader("Content-Disposition",
            "attachment;filename="+ URLEncoder.encode(filename,"UTF-8"));
    File file=new File(path,filename);
    InputStream is = new FileInputStream(file);//输入流
    OutputStream os = response.getOutputStream();//输出流
    //读取写出
    int index=0;
    byte[] buffer=new byte[1024];
    while ((index=is.read(buffer))!=-1){
        os.write(buffer,0,index);
        os.flush();
    }
    os.close();
    is.close();
    return "ok";
}
```

6. a标签下载静态内容

```jsp
<a href="${pageContext.request.contextPath}/statics/Icy_Grass_by_Raymond_Lavoie.jpg">Download</a>
```