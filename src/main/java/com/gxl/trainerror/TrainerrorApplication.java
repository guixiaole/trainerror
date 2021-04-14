package com.gxl.trainerror;


import com.gxl.trainerror.util.FileCreateMonitor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;

@EnableScheduling
@MapperScan("com.gxl.trainerror.mapper")
@SpringBootApplication
public class TrainerrorApplication {
//    @Autowired
//    private FileCreateController fileCreateController;
//    public TrainerrorApplication trainerrorApplication;
//
    public static void main(String[] args)  {
        SpringApplication.run(TrainerrorApplication.class, args);
//        FileCreateMonitor m=null;
//        try {
//            m = new FileCreateMonitor(1000);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        m.monitor("D:\\input",new FileCreateController());
//        try {
//            m.start();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

}
