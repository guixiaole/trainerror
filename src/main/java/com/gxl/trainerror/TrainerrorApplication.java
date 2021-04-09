package com.gxl.trainerror;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@MapperScan("com.gxl.trainerror.mapper")
@SpringBootApplication
public class TrainerrorApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrainerrorApplication.class, args);
    }

}
