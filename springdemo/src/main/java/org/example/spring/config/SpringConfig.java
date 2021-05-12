package org.example.spring.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration  // 作为配置类，替换xml配置文件
@ComponentScan(basePackages = {"org.example"})
@EnableAspectJAutoProxy(proxyTargetClass = true) //proxyTargetClass 默认是true，开启Aspect生成代理对象
public class SpringConfig {
}
