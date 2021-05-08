### Spring 5 框架

#### Spring的概念

**1.Spring框架概述**

Spring是轻量级的开源的JavaEE框架

Spring可以解决企业应用开发的复杂性

Spring有两个核心部分：IOC 和 AOP

（1）IOC：控制反转，把创建对象过程交给Spring进行管理

（2）AOP：面向切面，不修改源代码进行功能增强

Spring特点

（1）方便解耦，简化开发

（2）AOP编程支持

（3）方便程序测试

（4）方便和其他框架整合

（5）方便进行事务操作

（6）降低API开发难度

**2.入门案例**

<img src="C:\Users\Sammi\Desktop\面试\Java学习\Spring\Spring5架构图.png" alt="Spring5" style="zoom:50%;" />

1.新建Maven工程，导入IOC基本包

<img src="C:\Users\Sammi\Desktop\面试\Java学习\Spring\IOC基本核心包.png" alt="IOC基本核心包" style="zoom:50%;" />

```xml
 <dependencies>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-beans</artifactId>
            <version>5.2.2.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-core</artifactId>
            <version>5.2.2.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>5.2.2.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-expression</artifactId>
            <version>5.2.2.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>commons-logging</groupId>
            <artifactId>commons-logging</artifactId>
            <version>1.2</version>
        </dependency>
     <!--junit 测试用到的依赖-->
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
            <scope>test</scope>
        </dependency>
    </dependencies>
```



2.创建bean类

```java
package org.example.spring.entity;

public class User {
    public void add(){
        System.out.println(111);
    }
}
```

3.编写bean.xml，实现Spring容器托管

```java
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="
   http://www.springframework.org/schema/beans
   http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
   http://www.springframework.org/schema/context
   http://www.springframework.org/schema/context/spring-context-3.0.xsd">
<!--配置User对象创建-->
<bean id="user" class="org.example.spring.entity.User"></bean>


</beans>
```

4.编写测试类，验证容器自动创建bean对象

```java
package org.example.spring;

import org.example.spring.entity.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class test {
    @Test  //这个注解用到Junit依赖包
    public void testAdd(){
        //1.加载配置文件
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:bean.xml");
        //2.获取配置创建的对象
        User user = context.getBean("user", User.class);
        System.out.println(user);
        user.add();

    }
}
```





#### IOC容器

**IOC底层原理**

**1.什么是IOC？**

（1）控制反转，把对象创建和对象之间的调用过程，交给Spring管理

（2）使用IOC目的：为了降低耦合度

2.底层原理

xml解析、工厂模式、反射

**2.IOC接口（BeanFactory）**

IOC思想是基于IOC容器完成，IOC容器底层就是对象工厂

Spring提供IOC容器的两种方式：（两个接口）

（1）BeanFactory：IOC容器基本实现，是Spring内部的私用接口，不提供开发人员使用

*加载配置文件时候不会创建对象，在获取对象（使用）才去创建对象*

（2）ApplicationContext：BeanFactory接口的子接口，提供更多更强大的功能，一般由开发人员进行使用

*加载配置文件时会把在配置文件的对象进行创建*

​		<img src="C:\Users\Sammi\Desktop\面试\Java学习\Spring\ApplicationContext两个实现类.png" alt="ApplicationContext两个实现类" style="zoom:67%;" />

FileSystemXmlApplicationContext：（磁盘）文件全路径

ClassPathXmlApplicationContext：类路径



**Bean管理**

Bean管理指的是两个操作：

- Spring创建对象
- Spring注入属性

Bean管理操作有两种方式

- 基于xml
- 基于注解

**IOC操作Bean管理（基于xml）**

1.基于xml创建对象

```xml
<!--配置User对象创建-->
<bean id="user" class="org.example.spring.entity.User"></bean>
```

- 在Spring配置文件中，使用bean标签，标签里面添加对应属性，就可以实现对象创建。
- 在bean标签有很多属性，常见属性
  - **id属性：唯一标识**
  - **class属性：类全路径（包类路径）**
  - name属性：与id相比，可以加”/“，struts用的比较多
- 创建对象时，默认执行无参构造方法

2.基于xml注入属性

（1）DI：依赖注入，就是注入属性

注入方式：

- 使用set方式注入

  ①创建类、定义属性，及对应的set方法

  ```java
  package org.example.spring.entity;
  public class Book {
      private String bookname;
  
      //set注入
      public void setBookname(String bookname) {
          this.bookname = bookname;
      }
      public String getBookname() {
          return bookname;
      }
  
  }
  ```

  ②Spring配置文件配置对象

  ```xml
     <!--set方式注入属性-->
      <bean id="book" class="org.example.spring.entity.Book">
          <!--
          name : 类中的属性名
          value :属性注入的值
          -->
          <property name="bookname" value="Sping入门到精通"></property>
      </bean>
  ```

  

- 有参构造注入

  ①创建类、定义属性，及有参构造

  ```java
  package org.example.spring.entity;
  
  public class Book {
      private String bookname;
  
      //有参构造注入=======================
      public Book(String bookname) {
          this.bookname = bookname;
      }
      //有参构造注入=======================
  }
  ```

  ②Spring配置文件配置对象

  ```java
  <!--有参构造-->
  <bean id="book2" class="org.example.spring.entity.Book">    //注意这里是book2
      <constructor-arg name="bookname" value="Java入门到精通"></constructor-arg>
  </bean>
  ```

**注意：**如果使用了set注入对象book（bean 的id属性），又用有参构造注入book，会报错:

```shell
org.springframework.beans.factory.parsing.BeanDefinitionParsingException: Configuration problem: Bean name 'book' is already used in this <beans> element
Offending resource: class path resource [bean.xml]
```

P名称空间注入（了解）：简化基于xml的注入

①bean.xml增加p名称空间

```xml
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:p="http://www.springframework.org/schema/p"  //增加P名称空间
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="
	http://www.springframework.org/schema/beans
	http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
	http://www.springframework.org/schema/context
	http://www.springframework.org/schema/context/spring-context-3.0.xsd">
```

②p名称空间注入

```java
<!--p空间注入-->
<bean id="book3" class="org.example.spring.entity.Book" p:bookname="Spring核心">
</bean>
```

(2)xml注入其他类型属性

1. 字面值

   - null值

   ```xml
    <property name="bookname" ><null/></property>
   ```

   - 属性值包含特殊符号

   ```xml
   <!--属性值包含特殊符号
           1.<>进行转义 &lt;&gt;
           2.把带特殊符号内容写道CDATA
      -->
   <property name="bookname" >
       <value><![CDATA[<<Java核心卷1>>]]></value>
   </property>
   ```

2. 注入属性-外部bean

   （1）创建两个类service类和dao类

   （2）在service调用dao里面的方法

   ```xml
   bean.xml
   <!--service 和 dao 对象 创建-->
       <bean id="userService" class="org.example.spring.service.UserService">
           <!--注入bean对象-->
           <property name="userDao" ref="userDao"></property>
       </bean>
   
       <bean id="userDao" class="org.example.spring.dao.impl.UserDao"></bean>
   ```

   ```java
   UserService.java
   package org.example.spring.service;
   
   import org.example.spring.dao.IUserDao;
   
   public class UserService {
       private IUserDao userDao;
       public void add(){
           userDao.add();
           System.out.println("service add ....");
       }
   
       public void setUserDao(IUserDao userDao) {
           this.userDao = userDao;
       }
   
   
   }
   
   
   IUserDao.java
   package org.example.spring.dao;
   
   public interface IUserDao {
       public void add();
   }
   
   UserDao.java
   package org.example.spring.dao.impl;
   
   import org.example.spring.dao.IUserDao;
   
   public class UserDao implements IUserDao {
   
       @Override
       public void add() {
           System.out.println("UserDao add 666");
       }
   }
       
   ```

   ```java
   @Test
   public void testSD(){
       //1.加载配置文件
       ApplicationContext context = new ClassPathXmlApplicationContext("bean2.xml");
       //2.获取配置创建的对象
       UserService userService = context.getBean("userService", UserService.class);
       userService.add();
   }
   ```

3. 注入属性-内部bean和级联赋值

   (1)一对多关系：部门和员工

   ```xml
     <!--内部bean创建-->
       <bean id="emp" class="org.example.spring.entity.Employee">
           <property name="name" value="Lucy"></property>
           <property name="gender" value="female"></property>
           <!--注入bean对象-->
           <property name="department">
               <bean name="department" class="org.example.spring.entity.Department">
                   <property name="deptName" value="IT" ></property>
               </bean>
           </property>
       </bean>
   
   ```

   ```java
   Employee.java
   package org.example.spring.entity;
   
   public class Employee {
       private String name;
       private String gender;
       private Department department ;
   
       public void setName(String name) {
           this.name = name;
       }
   
       public void setGender(String gender) {
           this.gender = gender;
       }
   
       public void setDepartment(Department department) {
           this.department = department;
       }
   
       @Override
       public String toString() {
           return "Employee{" +
                   "name='" + name + '\'' +
                   ", gender='" + gender + '\'' +
                   ", department=" + department.toString() +
                   '}';
       }
   }
   
   Department.java
   package org.example.spring.entity;
   public class Department {
       private String deptName;
   
       public void setDeptName(String deptName) {
           this.deptName = deptName;
       }
   
       @Override
       public String toString() {
           return "Department{" +
                   "deptName='" + deptName + '\'' +
                   '}';
       }
   }
   
   ```

   ```java
     @Test
   public void testInner(){
       //1.加载配置文件
       ApplicationContext context = new ClassPathXmlApplicationContext("bean3.xml");
       //2.获取配置创建的对象
       Employee emp = context.getBean("emp", Employee.class);
       System.out.println(emp.toString());
   }
   ```

   (2)级联赋值

   方法1：

   ```xml
   bean4.xml
     <!--级联赋值-->
       <bean id="emp" class="org.example.spring.entity.Employee">
           <property name="name" value="Lucy"></property>
           <property name="gender" value="female"></property>
           <property name="department" ref="department" ></property>
       </bean>
       <bean name="department" class="org.example.spring.entity.Department">
           <property name="deptName" value="QA"></property>
       </bean>
   ```

   方法2：

   ```xml
     <!--级联赋值-->
       <bean id="emp" class="org.example.spring.entity.Employee">
           <property name="name" value="Lucy"></property>
           <property name="gender" value="female"></property>
           <property name="department" ref="department" ></property>
           <property name="department.deptName" value="Manager"></property>
       </bean>
       <bean name="department" class="org.example.spring.entity.Department">
       </bean>
   ```

   ```java
   public Department getDepartment() {
       return department;
   }
   ```

4. 注入属性-集合属性

   （1）创建类、定义数组、list、map、set，生成set方法

   ```java
   public class Student {
       private String[] courses;
       private List<String> list ;
       private Map<String,String> maps;
       private Set<String> sets;
       
       public void setCourses(String[] courses) {
           this.courses = courses;
       }
   
       public void setList(List<String> list) {
           this.list = list;
       }
   
       public void setMaps(Map<String, String> maps) {
           this.maps = maps;
       }
   
       public void setSets(Set<String> sets) {
           this.sets = sets;
       }
        @Override
       public String toString() {
           return "Employee{" +
                   "name='" + name + '\'' +
                   ", gender='" + gender + '\'' +
                   ", department=" + department.toString() +
                   '}';
       }
   }
   
   ```

   （2）配置文件配置

   ```xml
    <!--集合类型属性注入-->
       <bean name="student" class="org.example.spring.entity.Student">
           <property name="courses">
               <array>
                   <value>java</value>
                   <value>mysql</value>
               </array>
           </property>
           <property name="list">
               <list>
                   <value>张山</value>
                   <value>李四</value>
               </list>
           </property>
           <property name="maps">
               <map>
                   <entry key="Java" value="java"></entry>
                   <entry key="MySQL" value="mysql"></entry>
               </map>
           </property>
           <property name="sets">
               <set>
                   <value>oracle</value>
                   <value>redis</value>
               </set>
           </property>
       </bean>
   ```

   5.在集合里面设置对象类型值

   ```xml
   <bean id="course1" class="org.example.spring.entity.Course">
       <property name="cname" value="测试从入门到精通"></property>
   </bean>
   <bean id="course2" class="org.example.spring.entity.Course">
       <property name="cname" value="计算机网络"></property>
   </bean>
   <bean id="student" class="org.example.spring.entity.Student">
       <property name="courseList">
          <list>
              <ref bean="course1"></ref>
              <ref bean="course2"></ref>
          </list>
       </property>
   </bean>
   ```

   ```java
    @Test
       public void testCoursesList(){
           //1.加载配置文件
           ApplicationContext context = new ClassPathXmlApplicationContext("bean6.xml");
           //2.获取配置创建的对象
           Student stu = context.getBean("student", Student.class);
           System.out.println(stu.toString());
       }
   ```

   

   ```java
   ==Student.java
   package org.example.spring.entity;
   
   import java.util.Arrays;
   import java.util.List;
   import java.util.Map;
   import java.util.Set;
   
   public class Student {
       private String[] courses;
       private List<String> list ;
       private Map<String,String> maps;
       private Set<String> sets;
       private List<Course> courseList;
       public void setCourses(String[] courses) {
           this.courses = courses;
       }
   
       public void setList(List<String> list) {
           this.list = list;
       }
   
       public void setMaps(Map<String, String> maps) {
           this.maps = maps;
       }
   
       public void setSets(Set<String> sets) {
           this.sets = sets;
       }
   
       public void setCourseList(List<Course> courseList) {
           this.courseList = courseList;
       }
   
       @Override
       public String toString() {
           return "Student{" +
                   "courses=" + Arrays.toString(courses) +
                   ", list=" + list +
                   ", maps=" + maps +
                   ", sets=" + sets +
                   ", courseList=" + courseList.toString()+
                   '}';
       }
   }
   
   ```

   6.把集合注入部分提取出来

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <beans xmlns="http://www.springframework.org/schema/beans"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xmlns:p="http://www.springframework.org/schema/p"
          xmlns:util="http://www.springframework.org/schema/util"
          xsi:schemaLocation="
   	http://www.springframework.org/schema/beans
   	http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
   	http://www.springframework.org/schema/util
   	http://www.springframework.org/schema/util/spring-util-3.0.xsd">
       
      <!--提取list集合类型属性注入-->
       <util:list id="bookList">
           <value>Java并发编程的艺术</value>
           <value>oracle使用手册</value>
           <value>图解算法</value>
       </util:list>
       <!--2 提取list集合类型属性注入使用-->
       <bean id="book" class="org.example.spring.entity.Book">
           <property name="list" ref="bookList"></property>
       </bean>
   
   </beans>
   ```

   ```java
    @Test
       public void testUtilList(){
           //1.加载配置文件
           ApplicationContext context = new ClassPathXmlApplicationContext("bean7.xml");
           //2.获取配置创建的对象
           Book book = context.getBean("book", Book.class);
           System.out.println(book.toString());
       }
   ```

3.IOC操作Bean管理（FactoryBean）

1. Spring有两种类型bean，一种普通bean，另外一种工厂bean（FactoryBean）

2. 普通bean：在配置文件中定义bean类型就是返回类型

3. 工厂bean：在配置文件中定义bean类型可以和返回类型不一样

   ①创建类，让这个类作为工厂bean，实现接口FactoryBean

   ②实现接口里面的方法，在实现的方法中定义返回的bean类型

   ```java
   package org.example.spring.factorybean;
   import org.example.spring.entity.Course;
   import org.springframework.beans.factory.FactoryBean;
   
   public class MyBean implements FactoryBean<Course> {
   
       //定义返回bean
       @Override
       public Course getObject() throws Exception {
           Course course = new Course();
           course.setCname("算法基础");
           return course;
       }
       //
       @Override
       public Class<?> getObjectType() {
           return null;
       }
   
       @Override
       public boolean isSingleton() {
           return FactoryBean.super.isSingleton();
       }
   }
   
   ```

   ```xml
      <bean id="myBean" class="org.example.spring.factorybean.MyBean"></bean>
   ```

   ```java
   @Test
   public void testFactoryBean(){
       //1.加载配置文件
       ApplicationContext context = new ClassPathXmlApplicationContext("bean8.xml");
       //2.获取配置创建的对象
       Course course = context.getBean("myBean", Course.class);
       System.out.println(course.toString());
   }
   ```

4.IOC操作Bean管理（Bean的作用域）

①在Spring里面，设置创建bean实例是单实例还是多实例

②在Spring里面，默认情况下，bean是单实例对象

```java
  @Test
    public void testBook1(){
        //1.加载配置文件
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:bean.xml");
        //2.获取配置创建的对象
        Book book = context.getBean("book", Book.class);
        Book book2 = context.getBean("book", Book.class);
        //PS:toString()方法去掉
        System.out.println(book); //org.example.spring.entity.Book@10dba097
        System.out.println(book2); //org.example.spring.entity.Book@10dba097
    }
```

③如何设置单实例还是多实例

（1）在spring配置文件bean标签里面有属性（scope）用于设置单实例还是多实例

（2）scope属性值

第一个值：默认值，singleton，表示是单实例对象

第二个值：prototype

```xml
   <bean id="book" class="org.example.spring.entity.Book" scope="prototype">
       <property name="bookname" >
           <value><![CDATA[<<Java核心卷1>>]]></value>
       </property>
    </bean>
```

```java
  @Test
    public void testBook1(){
        //1.加载配置文件
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:bean.xml");
        //2.获取配置创建的对象
        Book book = context.getBean("book", Book.class);
        Book book2 = context.getBean("book", Book.class);
        //PS:toString()方法去掉
        System.out.println(book); //org.example.spring.entity.Book@10dba097
        System.out.println(book2); //org.example.spring.entity.Book@1786f9d5
    }
```

第三个值：request，一次请求

第四个值：session，一次会话

(3)singleton和prototype区别

第一：singleton单实例，prototype多实例

第二：设置scope值是singleton时候，加载spring配置文件时候都会创建单实例对象

​			设置scope值是prototype时候，不是在加载spring配置文件时候创建对象，在调用getBean方法时候创建多实例对象



5.IOC操作Bean管理（Bean生命周期）

1. 生命周期：从对象创建到对象销毁的过程

2. **bean的生命周期（重点面试题！见4完整的生命周期）**

   ① 通过构造器创建bean实例（无参构造）

   ②为bean的属性设置值和对其他bean引用（调用set方法）

   ③调用bean的初始化的方法（需要进行配置）

   ④bean可以使用了（对象获取到了）

   ⑤当容器关闭时候，调用bean的销毁的方法（需要进行配置销毁的方法）

3. 演示bean生命周期

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <beans xmlns="http://www.springframework.org/schema/beans"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="
   	http://www.springframework.org/schema/beans
   	http://www.springframework.org/schema/beans/spring-beans-3.0.xsd">
   
       <bean id="orders" class="org.example.spring.bean.Orders" init-method="initMethod" destroy-method="destoryMethod">
           <property name="oname" value="手机"></property>
       </bean>
   </beans>
   ```

   ```java
   package org.example.spring.bean;
   
   public class Orders {
       //无参构造
       public Orders() {
           System.out.println(
                   "第一步：执行无参构造创建bean实例"
           );
       }
       private String oname;
       public void setOname(String oname) {
           this.oname = oname;
           System.out.println("第二步：调用set方法设置属性值");
       }
       //创建执行的初始化的方法
       public void initMethod(){
           System.out.println("第三步：执行初始化的方法");
       }
   
       //创建销毁的方法
       public void destoryMethod(){
           System.out.println("第五步：执行销毁的方法");
       }
   
   }
   
   ```

   ```java
   @Test
       public void testBean(){
           //1.加载配置文件
           ApplicationContext context = new ClassPathXmlApplicationContext("bean8.xml");
           //2.获取配置创建的对象
           Orders orders = context.getBean("orders", Orders.class);
           System.out.println("第四步：获取创建bean实例对象");
           System.out.println(orders);
           //手动让bean实例销毁,close()方法是applicationContext实现类ClassPathXmlApplicationContext的方法。
           ((ClassPathXmlApplicationContext)context).close();
       }
   ```

   

<img src="C:\Users\Sammi\Desktop\面试\Java学习\Spring\bean的生命周期.png" alt="bean生命周期" style="zoom:50%;" />



 4.  bean的后置处理器，bean生命周期有七步

     ① 通过构造器创建bean实例（无参构造）

     ②为bean的属性设置值和对其他bean引用（调用set方法）

     **③把bean实例传递bean后置处理器的方法postProcessBeforeInitialization**

     ④调用bean的初始化的方法（需要进行配置）

     **⑤把bean实例传递bean后置处理器的方法postProcessAfterInitialization**

     ⑥bean可以使用了（对象获取到了）

     ⑦当容器关闭时候，调用bean的销毁的方法（需要进行配置销毁的方法）

5. 演示添加后置处理器效果

   （1）创建类，实现接口BeanPostProcessor，创建后置处理器

   （2）spring配置文件增加后置处理器配置

   ```xml
      <bean id="orders" class="org.example.spring.bean.Orders" init-method="initMethod" destroy-method="destoryMethod">
           <property name="oname" value="手机"></property>
       </bean>
       <!--配置后置处理器,当前文件中的所有bean都会添加-->
       <bean id="myBeanPost" class="org.example.spring.bean.MyBeanPost"></bean>
   ```

   ```java
   package org.example.spring.bean;
   
   import org.springframework.beans.BeansException;
   import org.springframework.beans.factory.config.BeanPostProcessor;
   
   public class MyBeanPost implements BeanPostProcessor {
       @Override
       public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
           System.out.println("在初始化之前执行的方法");
           return bean;
       }
   
       @Override
       public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
           System.out.println("在初始化之后执行的方法");
           return bean;
       }
   }
   
   ```

   ```java
    @Test
       public void testBean(){
           //1.加载配置文件
           ApplicationContext context = new ClassPathXmlApplicationContext("bean9.xml");
           //2.获取配置创建的对象
           Orders orders = context.getBean("orders", Orders.class);
           System.out.println("第四步：获取创建bean实例对象");
           System.out.println(orders);
           //手动让bean实例销毁,close()方法是applicationContext实现类ClassPathXmlApplicationContext的方法。
           ((ClassPathXmlApplicationContext)context).close();
       }
   ```

   <img src="C:\Users\Sammi\Desktop\面试\Java学习\Spring\bean完整的生命周期.png" alt="bean完整的生命周期" style="zoom:50%;" />

6.IOC操作Bean管理（xml自动装配）

（1）什么是自动装配：根据指定装配规则（属性名称或者属性类型），Spring自动将匹配的属性值进行注入

（2）演示自动装配过程

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="
	http://www.springframework.org/schema/beans
	http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
	http://www.springframework.org/schema/context
	http://www.springframework.org/schema/context/spring-context-3.0.xsd">
    <!--手动装配
    <bean id="emp" class="org.example.spring.autowire.Emp">-->
        <!--注入bean对象-->
    <!--  <property name="dept" ref="dept"></property>
    </bean>
    手动装配-->


    <!--自动装配
    bean标签属性autowire，配置自动装配
    autowire属性常用两个值：
    byName根据属性名称注入，注入值bean的id值和类属性名称一样
    byType根据属性类型注入，注意byType的话如果文件中存在多个同类型的bean，会报错
    -->
    <bean id="emp" class="org.example.spring.autowire.Emp" autowire="byType">
    </bean>
    <bean id="dept" class="org.example.spring.autowire.Dept"></bean>
</beans>
```

```java
Emp.java==
package org.example.spring.autowire;

public class Emp {
    private Dept dept;

    public void setDept(Dept dept) {
        this.dept = dept;
    }

    @Override
    public String toString() {
        return "Emp{" +
                "dept=" + dept +
                '}';
    }
}
```

```java
Dept.java===
package org.example.spring.autowire;

public class Dept {
    @Override
    public String toString() {
        return "Dept{}";
    }
}

```

```java
    @Test
    public void testAutoWire(){
        ApplicationContext context = new ClassPathXmlApplicationContext("bean10.xml");
        Emp emp = context.getBean("emp",Emp.class);
        System.out.println(emp.toString());
    }
```

7.IOC操作Bean管理（外部属性文件）

1. 直接配置数据库信息

   ​	①配置德鲁伊数据库连接池

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <beans xmlns="http://www.springframework.org/schema/beans"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="
   	http://www.springframework.org/schema/beans
   	http://www.springframework.org/schema/beans/spring-beans-3.0.xsd"> 
   <!--直接配置连接池-->
       <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
          <!-- #com.mysql.jdbc.Driver 是 mysql 5.0 的驱动
           #mysql6.0以上请用：com.mysql.cj.jdbc.Driver-->
           <property name="driverClassName" value="com.mysql.cj.jdbc.Driver"></property>
           <property name="url" value="jdbc:mysql://192.168.0.113:3306/mydatabase"></property>
           <property name="username" value="root"></property>
           <property name="password" value="lsm153"></property>
       </bean>
   </beans>
   ```

   ​	②引入德鲁伊依赖包

   ```
   <!-- https://mvnrepository.com/artifact/com.alibaba/druid -->
   <dependency>
       <groupId>com.alibaba</groupId>
       <artifactId>druid</artifactId>
       <version>1.1.22</version>
   </dependency>
   ```

2. 引入外部配置文件配置数据库的连接池

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <beans xmlns="http://www.springframework.org/schema/beans"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xmlns:context="http://www.springframework.org/schema/context"
          xsi:schemaLocation="
   	http://www.springframework.org/schema/beans
   	http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
   	http://www.springframework.org/schema/context
   	http://www.springframework.org/schema/context/spring-context-3.0.xsd">
       <!--引入外部属性文件-->
       <context:property-placeholder location="classpath:jdbc.properties"></context:property-placeholder>
       <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
           <!-- #com.mysql.jdbc.Driver 是 mysql 5.0 的驱动
            #mysql6.0以上请用：com.mysql.cj.jdbc.Driver-->
           <property name="driverClassName" value="${prop.driverClassName}"></property>
           <property name="url" value="${prop.url}"></property>
           <property name="username" value="${prop.username}"></property>
           <property name="password" value="${prop.password}"></property>
       </bean>
   </beans>
   ```

   ```properties
   prop.driverClassName=com.mysql.cj.jdbc.Driver
   prop.url=jdbc:mysql://192.168.0.113:3306/mydatabase
   prop.username=root
   prop.password=lsm153
   ```

   

**IOC操作Bean管理（基于注解）**

1.什么是注解

（1）注解是代码特殊符号标记，格式：@注解名称（属性名称=属性值，属性名称=属性值…）

（2）使用注解，注解作用在类上面、方法上面、属性上面、

（3）使用注解目的：简化xml配置

2.Spring针对Bean管理中创建对象提供注解

（1）@Component

（2）@Service

（3）@Controller

（4）@Repository

**上面四个注解功能是一样的，都可以用来创建bean实例**

3.基于注解方式实现对象创建

​	第一步：引入依赖 aop

```xml
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-aop</artifactId>
            <version>5.2.2.RELEASE</version>
        </dependency>
```

​	第二步：开启组件扫描

```xml
bean12.xml=====
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="
	http://www.springframework.org/schema/beans
	http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
	http://www.springframework.org/schema/context
	http://www.springframework.org/schema/context/spring-context-3.0.xsd">
   <!--开启组件扫描
   1.扫描多个包，逗号隔开
       <context:component-scan base-package="org.example.spring.dao,org.example.spring.service"></context:component-scan>
   2.扫描包上级目录
   -->
    <context:component-scan base-package="org.example.spring"></context:component-scan>
</beans>
```

第三步：创建类，在类上面添加创建对象注解

```java
package org.example.spring.service;

import org.example.spring.dao.IUserDao;
import org.springframework.stereotype.Component;
//注解里面value属性值可以省略不屑
//默认值是类名称，首字母小写
@Component(value = "userService") //类似于<bean id="userService" class=""/>
public class UserService {
    public void add(){
        System.out.println("service add ....");

    }
}
```

测试：

```
@Test
public void testService(){
    ApplicationContext context = new ClassPathXmlApplicationContext("bean12.xml");
    UserService  userService = context.getBean("userService", UserService.class);
    System.out.println(userService);
    userService.add();
}
```

4.开启组件扫描细节配置

```xml
 <!--示例1
   use-default-filters="false" 表示现在不使用默认filter，自己配置filter
   context:include-filter , 设置扫描哪些内容,示例1中表示只扫描org.example包中@Controller注解的类
    -->
    <context:component-scan base-package="org.example" use-default-filters="false">
        <context:include-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
    </context:component-scan>
    <!--示例2
  use-default-filters="false" 表示现在不使用默认filter，自己配置filter
  context:exclude-filter , 设置哪些内容不扫描,示例2中表示不扫描org.example包中用@Controller注解的类
   -->
    <context:component-scan base-package="org.example" >
        <context:exclude-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
    </context:component-scan>
```

5.基于注解方式实现属性注入

（1）@Autowired：根据属性类型进行自动装配

第一步：把service和dao对象创建，在service 和 dao类添加创建对象注解

第二步：在service注入dao对象，在service类添加dao类型属性，在属性上面使用注解

```java
package org.example.spring.service;

import org.example.spring.dao.IUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//注解里面value属性值可以省略不写
//默认值是类名称，首字母小写
//还可以用@Component、@Controller、@Repository,功能是一样的
@Service(value = "userService") //类似于<bean id="userService" class=""/>
public class UserService {
     //不需要set方法
    @Autowired //根据类型注入
    private IUserDao userDao;
    public void add(){
        userDao.add();
        System.out.println("service add ....");

    }

}
```

（2）@Qualifier：根据属性名称进行注入

这个@Qualifier注解的使用，和上面@Autowired一起使用

```java
//注解里面value属性值可以省略不写
//默认值是类名称，首字母小写
//还可以用@Component、@Controller、@Repository,功能是一样的
@Service(value = "userService") //类似于<bean id="userService" class=""/>
public class UserService {
//不需要set方法 
@Autowired //根据类型注入
    @Qualifier(value = "userDao")
    private IUserDao userDao;
    public void add(){
        userDao.add();
        System.out.println("service add ....");
    }
}
```

（3）@Resource：可以根据类型注入，也可以根据名称注入

```java
@Service(value = "userService") //类似于<bean id="userService" class=""/>
public class UserService {   
@Resource(name = "userDao1")  //包：javax.annotation.Resource，是属于java的注解；userDao实现类需要添加@Repository(value = "userDao1")
    private IUserDao userDao;
    public void add(){
        userDao.add();
        System.out.println("service add ....");

    }
}
```

（4）@Value：注入普通类型

```java
  @Value(value = "abc")
    private String name;
```



6.完全注解开发

（1）创建配置类，替换xml配置文件

```java
@Configuration  // 作为配置类，替换xml配置文件
@ComponentScan(basePackages = {"org.example"})
public class SpringConfig {
}
```

（2）编写测试类

```java
 @Test
    public void testService2(){
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        UserService  userService = context.getBean("userService", UserService.class);
        System.out.println(userService);
        userService.add();
    }
```







#### AOP

**AOP概念**

1. 什么是AOP

   （1）面向切面编程（方面），利用AOP对业务逻辑的各个部分进行隔离，从而使得业务逻辑各部分之间的**耦合度降低**，提高程序的可重用性，同时提高了开发的效率。

   （2）通俗描述：不通过修改源代码方式，在主干功能里面添加新的功能

**AOP底层原理**

1. AOP底层使用动态代理

   （1）有两种情况动态代理

   第一种，有接口情况，使用JDK动态代理：创建接口实现类代理对象，通过代理对象增强类的方法

   第二种，没有接口情况，使用CGLIB动态代理：创建当前类子类的代理对象，通过代理对象增强类的方法

2. AOP（JDK动态代理）

   ①使用JDK动态代理，使用Proxy类里面的方法创建代理对象

   （1）调用newProxyInstance方法

   ![](C:\Users\Sammi\Desktop\面试\Java学习\Spring\newProxyInstance.png)

   方法有三个参数：

   参数1：类加载器

   参数2：增强方法所在的类，这个类实现的接口，支持多个接口

   参数3：实现这个接口InvocationHandler，创建代理对象，写增强的方法

   ②JDK动态代码实现

   （1）创建接口，定义方法

   （2）创建接口实现类，实现方法

   ③使用Proxy类创建接口代理对象

   ```
   public class JDKProxy {
       public static void main(String[] args) {
           //创建接口实现类代理对象
           Class[] interfaces = {UserDao.class};
   //        Proxy.newProxyInstance(JDKProxy.class.getClassLoader(), interfaces, new InvocationHandler((Object proxy, Method method, Object[] args)
   //                -> System.out.println("aaa")));
          UserDao dao = (UserDao)Proxy.newProxyInstance(JDKProxy.class.getClassLoader(), interfaces, new UserDaoProxy(new UserDaoImpl()));
          int result = dao.add(1,2);
          System.out.println("result:"+result);
   
       }
   
   }
   //创建代理对象的代码
   class UserDaoProxy implements InvocationHandler{
       //1.传递代理的实现类，通过构造函数
       private Object obj;
       public UserDaoProxy(Object obj){
           this.obj = obj;
       }
   
       //增强的逻辑
       @Override
       public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
           System.out.println("方法执行前："+method.getName()+":"+ Arrays.toString(args));
   
           Object res = (int) method.invoke(obj,args);
           System.out.println("方法执行后:"+obj);
           return res;
       }
   }
   ```

   

   

**AOP术语**

1. 连接点：类里面哪些方法可以被增强，这些方法称之为连接点

2. 切入点：实际被真正增强的方法，称之为切入点

3. 通知（增强）

   （1）实际增强的逻辑部分称之为通知（增强）

   （2）通知很多种类型

     *  前置通知
     *  后置通知
     *  环绕通知
     *  异常通知
     *  最终通知

4. 切面：是动作，把通知应用到切入点过程

**AOP操作（准备）**

1. Spring框架一般都是基于AspectJ实现AOP操作

什么是AspectJ：AspectJ不是Spring组成部分，独立AOP框架，一般把AspectJ和Spring框架一起使用，进行AOP操作

2. 基于AspectJ实现AOP操作

   （1）基于xml配置文件实现

   （2）基于注解方式实现（使用）

3. 在项目工程里面引入AOP相关依赖

   <img src="C:\Users\Sammi\Desktop\面试\Java学习\Spring\AspectJ依赖.png" style="zoom:50%;" />



4. 切入点表达式

   （1）切入点表达式作用：指定对哪个类里面的哪个方法进行增强

   （2）语法结构：

   execution([权限修饰符] [返回类型] [类全路径] [方法名称] ([参数列表]))

   举例1：对org.example.spring.dao.BookDao类里面的add进行增强

   execution(* org.example.spring.dao.BookDao.add(..))

   举例2：对org.example.spring.dao.BookDao类里面的所有方法进行增强

   execution(* org.example.spring.dao.BookDao. *(..))

   举例3：对org.example.spring.dao包里面的所有类进行增强

   execution(* org.example.spring.dao.*. *(..))

**AOP操作(AspectJ注解-重点掌握)**

方法一：

1. 创建类，在类里面定义方法

   ```java
   package org.example.spring.aspectj;
   
   public class User {
       public void add(){
           System.out.println("add()……");
       }
   }
   
   ```

2. 创建增强类（编写增强）

   （1）在增强类中创建方法，让不同方法代表不同通知类型

3. 进行通知的配置

   （1）在Spring配置文件中，开启注解扫描

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <beans xmlns="http://www.springframework.org/schema/beans"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xmlns:context="http://www.springframework.org/schema/context"
          xmlns:aop="http://www.springframework.org/schema/aop"
          xsi:schemaLocation="
   	http://www.springframework.org/schema/beans
   	http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
   	http://www.springframework.org/schema/context
   	http://www.springframework.org/schema/context/spring-context-3.0.xsd
       http://www.springframework.org/schema/aop
   	http://www.springframework.org/schema/aop/spring-aop-3.0.xsd">
       <!--开启注解扫描-->
       <context:component-scan base-package="org.example.spring.aspectj"></context:component-scan>
   </beans>
   ```

   （2）使用注解创建User 和 UserProxy对象

   ```java
   @Component
   public class User {
   ```

   （3）在增强类上面添加注解@Aspect

   ```java
   @Component
   @Aspect
   public class UserProxy {
   ```

   （4）在Spring配置文件中开启生成代理对象

   ```xml
     <!--开启AspectJ生成代理对象-->
       <aop:aspectj-autoproxy></aop:aspectj-autoproxy>
   ```

4. 配置不同类型通知

   ```java
   package org.example.spring.aspectj;
   
   import org.aspectj.lang.ProceedingJoinPoint;
   import org.aspectj.lang.annotation.*;
   import org.springframework.context.annotation.Configuration;
   import org.springframework.stereotype.Component;
   @Configuration
   @Component
   @Aspect
   public class UserProxy {
       @Before(value = "execution(* org.example.spring.aspectj.User.add(..))")
       public void before(){
           System.out.println("前置通知before……");
       }
   
       @AfterThrowing(value ="execution(* org.example.spring.aspectj.User.add(..))" )
       public void afterThrowing(){
           System.out.println("异常通知AfterThrowing……");
       }
       
       @Around(value ="execution(* org.example.spring.aspectj.User.add(..))" )
       public void around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
           System.out.println("环绕通知around before……");
           proceedingJoinPoint.proceed();
           System.out.println("环绕通知around after……");
       }
       @After(value ="execution(* org.example.spring.aspectj.User.add(..))" )
       public void after(){
           System.out.println("最终通知 After……");
       }
       @AfterReturning(value ="execution(* org.example.spring.aspectj.User.add(..))" )
       public void afterReturning(){
           System.out.println("后置通知 afterReturning……");
       }
   }
   
   ```

5. 相同切入点抽取

   ```java
   @Configuration
   @Component
   @Aspect
   public class UserProxy {
       //相同切入点抽取
       @Pointcut(value = "execution(* org.example.spring.aspectj.User.add(..))")
       public void pointdemo(){
   
       }
   //    @Before(value = "execution(* org.example.spring.aspectj.User.add(..))")
       @Before(value = "pointdemo()")
       public void before(){
           System.out.println("前置通知before……");
       }
   ```

6. 多个增强类对同一个方法进行增强，设置增强类优先级

   （1）在增强类上面添加注解@Order(数字类型值)，数字类型值越小优先级越高

   ```java
   @Component
   @Aspect
   @Order(1)//从0开始
   public class PersonProxy {
   ```

7. 完全注解

   ```java
   @Configuration  // 作为配置类，替换xml配置文件
   @ComponentScan(basePackages = {"org.example"}) //开启注解扫描
   @EnableAspectJAutoProxy  //开启AspectJ生成代理对象
   public class SpringConfig {
   }
   ```

8. 测试

   ```java
     @Test
       public void TestAspectJ(){
   		ApplicationContext context = new ClassPathXmlApplicationContext("bean13.xml");
           //  ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class); 完全注解
           User user = context.getBean("user", User.class);
           user.add();
       }
   ```

   PS：AnnotationConfigApplicationContext是如何实现注册bean到IOC容器的？【待扩展】

    BeanDefinitionRegistry



**AOP操作(AspectJ配置文件)**

1. 创建两个类，增强类和被增强类，创建方法

   ```java
   public class BookProxy {
       public void before(){
           System.out.println("before……");
       }
   }
   ```

   ```java
   public class Book {
       public void buy(){
           System.out.println("buy……");
       }
   }
   ```

2. 在Sping配置文件中创建两个类对象

   ```xml
   <!--创建对象-->
   <bean id="book" class="org.example.spring.aspectj.Book">
   </bean>
   <bean id="bookProxy" class="org.example.spring.aspectj.BookProxy">
   </bean>
   ```

3. 在Spring配置文件中配置

   ```xml
   <!--配置aop增强-->
   <aop:config>
       <!--切入点-->
       <aop:pointcut id="p" expression="execution(*org.example.spring.aspectj.Book.buy(..))"/>
       <!--配置切面-->
       <aop:aspect ref="bookProxy">
           <!--增强作用在具体的方法上-->
           <aop:before method="before" pointcut-ref="p"/>
       </aop:aspect>
   </aop:config>
   ```

   

**JdbcTemplate**

**概念和准备**

1. 什么是JdbcTemplate：Spring框架对JDBC进行封装，使用JDBCTemplate方便实现对数据库操作

2. 准备工作

   （1）引入相关Jar包

   ```java
     <!--jdbcTemplate-->
           <dependency>
               <groupId>mysql</groupId>
               <artifactId>mysql-connector-java</artifactId>
               <version>8.0.24</version>
           </dependency>
           <dependency>
               <groupId>org.springframework</groupId>
               <artifactId>spring-jdbc</artifactId>
               <version>5.2.14.RELEASE</version>
           </dependency>
           <dependency>
               <groupId>org.springframework</groupId>
               <artifactId>spring-orm</artifactId>
               <version>5.2.14.RELEASE</version>
           </dependency>
           <dependency>
               <groupId>org.springframework</groupId>
               <artifactId>spring-tx</artifactId>
               <version>5.2.14.RELEASE</version>
           </dependency>
           <!--jdbcTemplate-->
   ```

   （2）在Spring配置文件配置数据库连接池

   ```xml
     <!--直接配置连接池-->
       <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
          <!-- #com.mysql.jdbc.Driver 是 mysql 5.0 的驱动
           #mysql6.0以上请用：com.mysql.cj.jdbc.Driver-->
           <property name="driverClassName" value="com.mysql.cj.jdbc.Driver"></property>
           <property name="url" value="jdbc:mysql://192.168.0.113:3306/mydatabase"></property>
           <property name="username" value="root"></property>
           <property name="password" value="root"></property>
       </bean>
   ```

   （3） 配置JdbcTemplate对象，注入DataSource

   ```xml
   <!--JdbcTemplate对象-->
   <bean id="JdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
           <property name="dataSource" ref="dataSource"></property>
   </bean>
   ```

   （4）创建service类、dao类，在dao注入jdbcTemlate对象

   ```
   <!--组件扫描-->
   <context:component-scan base-package="org.example"></context:component-scan>
   ```

   ```
   @Service
   public class BookService {
       @Autowired
       private IBookDao bookDao;
   }
   ```

   ```
   @Repository
   public class BookDaoImpl implements IBookDao {
     @Autowired
       private JdbcTemplate jdbcTemplate;
   }
   ```

3. JdbcTemplate操作数据库（添加）

   （1）对应数据库创建实体类

   ```
   public class Book {
       private String bookId;
       private String bookname;
       private String bookStatus;
   
   //get set方法省略
   }
   ```

   （2）编写service和dao

   1. 在dao进行数据库添加操作

   2. 调用JdbcTemplate对象里面update方法实现添加操作

      ```
      update(String sql,Object... args)
      两个参数：
      1:sql语句
      2:可变参数，设置sql语句值
      ```

      ```java
      @Repository
      public class BookDaoImpl implements IBookDao {
        @Autowired
          private JdbcTemplate jdbcTemplate;
      
          @Override
          public void add(Book book) {
              //1.创建sql语句
              String sql ="insert into book values(?,?,?)";
              //2.调用方法实现
               int update =  jdbcTemplate.update(sql,book.getBookId(),book.getBookname(),book.getBookStatus());
              System.out.println(update);
          }
      }
      ```

   （3）测试类

   ```
   public class test4 {
       @Test
       public void testJdbcTemplate(){
           ApplicationContext context = new ClassPathXmlApplicationContext("bean11.xml");
           BookService bookService = context.getBean("bookService", BookService.class);
           Book book = new Book();
           book.setBookId("1");
           book.setBookname("Java");
           book.setBookStatus("已读");
           bookService.addBook(book);
       }
   
   }
   ```

4. JdbcTemplate操作数据库（修改和删除）

   ```java
   //修改
       @Override
       public void update(Book book) {
           //1.创建sql语句
           String sql ="update book set bookname=? ,bookStatus=? where bookId=?";
           //2.调用方法实现
           int update =  jdbcTemplate.update(sql,book.getBookname(),book.getBookStatus(),book.getBookId());
           System.out.println(update);
       }
   //删除
       @Override
       public void delete(String id) {
           //1.创建sql语句
           String sql ="delete from  book  where bookId=?";
           int update = jdbcTemplate.update(sql,id);
           System.out.println(update);
       }
   ```

   测试类

   ```java
    @Test
       public void testJdbcTemplate2(){
           ApplicationContext context = new ClassPathXmlApplicationContext("bean11.xml");
           BookService bookService = context.getBean("bookService", BookService.class);
           Book book = new Book();
           book.setBookId("1");
           book.setBookname("Java");
           book.setBookStatus("已读未总结");
           bookService.updateBook(book);
       }
       @Test
       public void testJdbcTemplate3(){
           ApplicationContext context = new ClassPathXmlApplicationContext("bean11.xml");
           BookService bookService = context.getBean("bookService", BookService.class);
           String id = "1";
           bookService.deleteBook(id);
       }
   ```

5. JdbcTemplate操作数据库（查询返回某个值）

   （1）查询表里面有多个条记录，返回某个值

   （2）使用JdbcTemplate实现查询返回某个值代码

   ```
   @Override
   public int count() {
       //1.创建sql语句
       String sql ="select count(*) from  book";
       Integer num = jdbcTemplate.queryForObject(sql,Integer.class);
       return  num;
   }
   ```

   测试类

   ```java
      @Test
       public void testJdbcTemplate4(){
           ApplicationContext context = new ClassPathXmlApplicationContext("bean11.xml");
           BookService bookService = context.getBean("bookService", BookService.class);
          System.out.println(bookService.selectCount());
       }
   ```

6. JdbcTemplate操作数据库（查询返回对象）

   （1）场景：查询图书详细

   （2）JdbcTemplate实现查询返回对象

   ```
   queryForObject(String sql, RowMapper<T> rowMapper, @Nullable Object... args)
   参数1：sql语句
   参数2：rowMapper是接口，针对返回不同类型数据，使用这个接口里面实现类完成数据封装
   参数3：sql语句值
   ```

   ```java
     @Override
       public Book findById(String id) {
           String sql ="select * from  book where bookId =? ";
           Book  book= jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Book>(Book.class),id);
           return  book;
       }
   ```

   测试

   ```java
    @Test
       public void testJdbcTemplate5(){
           ApplicationContext context = new ClassPathXmlApplicationContext("bean11.xml");
           BookService bookService = context.getBean("bookService", BookService.class);
           System.out.println(bookService.selectOne("1"));
       }
   ```

7. JdbcTemplate操作数据库（查询返回集合）

   （1）场景：查询图书列表分页

   （2）JdbcTemplate实现查询返回集合

   ```java
   query(String sql, RowMapper<T> rowMapper, @Nullable Object... args)
   参数1：sql语句
   参数2：rowMapper是接口，针对返回不同类型数据，使用这个接口里面实现类完成数据封装
   参数3：sql语句值,可省略
   ```

   ```java
     @Override
       public List<Book> findAll() {
           String sql ="select * from  book ";
           List<Book> list= jdbcTemplate.query(sql, new BeanPropertyRowMapper<Book>(Book.class));
           return list;
       }
   ```

   测试

   ```java
    @Test
       public void testJdbcTemplate6(){
           ApplicationContext context = new ClassPathXmlApplicationContext("bean11.xml");
           BookService bookService = context.getBean("bookService", BookService.class);
           System.out.println(bookService.selectAll());
       }
   ```

8. JdbcTemplate操作数据库（批量操作）

   （1）批量操作：操作表里面多条操作

   （2）JdbcTemplate实现批量添加操作

   ```java
     @Override
       public void batchAddBook(List<Object[]> args) {
           String sql ="insert into book values(?,?,?)";
           int[] ints = jdbcTemplate.batchUpdate(sql,args);
           System.out.println(Arrays.toString(ints));
       }
   ```

   测试

   ```java
   @Test
   public void testJdbcTemplate7(){
       ApplicationContext context = new ClassPathXmlApplicationContext("bean11.xml");
       BookService bookService = context.getBean("bookService", BookService.class);
       Object[] o1 = {"2","Test","未读"};
       Object[] o2 = {"3","Redis","初略了解"};
       Object[] o3 = {"4","docker","初略了解"};
       List<Object[]> list = new ArrayList<>();
       list.add(o1);
       list.add(o2);
       list.add(o3);
       bookService.batchAdd(list);
   }
   ```

   （3）JdbcTemplate实现批量修改操作

   ```java
   @Override
   public void batchUpdateBook(List<Object[]> args) {
       String sql ="update book set bookname=? ,bookStatus=? where bookId=?";
       int[] ints = jdbcTemplate.batchUpdate(sql,args);
       System.out.println(Arrays.toString(ints));
   }
   ```

   测试

   ```java
   @Test
   public void testJdbcTemplate8(){
       ApplicationContext context = new ClassPathXmlApplicationContext("bean11.xml");
       BookService bookService = context.getBean("bookService", BookService.class);
       Object[] o1 = {"Test","未读","3"};
       Object[] o2 = {"docker001","已读","4"};
       List<Object[]> list = new ArrayList<>();
       list.add(o1);
       list.add(o2);
       bookService.batchUpdate(list);
   }
   ```

​        （4）JdbcTemplate实现批量删除操作

