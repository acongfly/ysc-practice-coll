package com.acongfly.studyjava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync
@EnableScheduling
public class StudyjavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudyjavaApplication.class, args);
    }
//    @Component
//    public class AspCheckValueConfig extends CheckValueAspect {
//    }
//    @Component
//    public class AspLogConfig extends LogAspect {
//    }

}
