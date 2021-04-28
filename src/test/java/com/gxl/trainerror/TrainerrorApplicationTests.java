package com.gxl.trainerror;

import com.gxl.trainerror.bean.FileInfo;
import com.gxl.trainerror.service.FileInfoService;
import com.gxl.trainerror.util.TimeCal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest
class TrainerrorApplicationTests {
    @Autowired
    private FileInfoService fileInfoService;
    @Test
    void contextLoads() {
        Date date = TimeCal.backDate(30);
        FileInfo fileInfo = new FileInfo();
        fileInfo.setCheCi("8018");
//        FileInfo fileInfos = fileInfoService.selectTestById(7);
        List<FileInfo>fileInfos = fileInfoService.selectIndexFileInfo(fileInfo);
        //没有错
        for (FileInfo info : fileInfos) {
            System.out.println(info);
        }
    }   

}
