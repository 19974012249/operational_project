package com.zw.annotation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author zhouwei
 * @date 2020-14-20:54
 */
@Component
public class SpringScheduled {

    @Scheduled(initialDelay = 2000, fixedDelay = 5000)
    public void doSomething() {

        System.out.println("Spring自带的Scheduled执行了=======================");
    }
}

//下面是开启
@SpringBootApplication
@EnableScheduling
class DemoApplication {

    public static void main(String[] args) throws InterruptedException {

        SpringApplication application = new SpringApplication(DemoApplication.class);
//        application.addListeners(new ContextRefreshedEventListener());
        application.run(args);
    }
}
