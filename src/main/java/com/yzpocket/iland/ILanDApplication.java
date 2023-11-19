package com.yzpocket.iland;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ILanDApplication {

    public static void main(String[] args) {
        SpringApplication.run(ILanDApplication.class, args);
    }

}
