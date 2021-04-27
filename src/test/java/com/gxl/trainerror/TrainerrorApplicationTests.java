package com.gxl.trainerror;

import com.gxl.trainerror.bean.FileInfo;
import com.gxl.trainerror.service.FileInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class TrainerrorApplicationTests {
    @Autowired
    private FileInfoService fileInfoService;
    @Test
    void contextLoads() {
        List<FileInfo>fileInfos = fileInfoService.selectIndexFileInfoByIndex();
        for (FileInfo fileInfo : fileInfos) {
            System.out.println(fileInfo);
        }
    }

}
