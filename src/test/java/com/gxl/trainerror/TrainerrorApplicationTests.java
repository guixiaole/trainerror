package com.gxl.trainerror;

import com.gxl.trainerror.bean.FileInfo;
import com.gxl.trainerror.bean.FiveStepTemplate;
import com.gxl.trainerror.bean.QuanCheng;
import com.gxl.trainerror.service.FileInfoService;
import com.gxl.trainerror.service.QuanChengService;
import com.gxl.trainerror.util.StepTemplateUtil;
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
    @Autowired
    private QuanChengService quanChengService;
    @Test
    void contextLoads() {
//        Date date = TimeCal.backDate(30);
//        FileInfo fileInfo = new FileInfo();
//        fileInfo.setCheCi("8018");
////        FileInfo fileInfos = fileInfoService.selectTestById(7);
//        List<FileInfo>fileInfos = fileInfoService.selectIndexFileInfo(fileInfo);
//        //没有错
//        for (FileInfo info : fileInfos) {
//            System.out.println(info);
//        }
        List<QuanCheng>quanChengs = quanChengService.selectByFileAscXuhao(1216);
        List<QuanCheng> quanChengs1 =  StepTemplateUtil.TimeTemplate(quanChengs);
        for (QuanCheng quanCheng : quanChengs1) {
            System.out.println(quanCheng.getDateTime());
            System.out.println(quanCheng.getGuanYa());
        }
        List<FiveStepTemplate> fiveStepTemplates = StepTemplateUtil.GuanYaTemplateAnalysis(quanChengs1);
        for (FiveStepTemplate fiveStepTemplate : fiveStepTemplates) {
            System.out.println(fiveStepTemplate);
        }
    }   

}
