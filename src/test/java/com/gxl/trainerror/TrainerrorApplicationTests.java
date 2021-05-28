package com.gxl.trainerror;

import com.gxl.trainerror.bean.*;
import com.gxl.trainerror.service.FileInfoService;
import com.gxl.trainerror.service.QuanChengService;
import com.gxl.trainerror.service.StepSelectService;
import com.gxl.trainerror.util.StepTemplateUtil;
import com.gxl.trainerror.util.TimeCal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
class TrainerrorApplicationTests {
    @Autowired
    private FileInfoService fileInfoService;
    @Autowired
    private QuanChengService quanChengService;
    @Autowired
    private StepSelectService stepSelectService;
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

        List<FiveStepTemplate> guanYaTemplateAnalysis = StepTemplateUtil.GuanYaTemplateTest(quanChengs1);
        List<FiveStepTemplate>  gangYaTemplateAnalysis= StepTemplateUtil.GangYaTemplateTest(quanChengs1);
        List<FiveStepTemplate> junGang1TemplateAnalysis = StepTemplateUtil.JunGang1TemplateTest(quanChengs1);
        List<FiveStepTemplate> junGang2TemplateAnalysis = StepTemplateUtil.JunGang2TemplateTest(quanChengs1);
        List<List<FiveStepTemplate>> fiveStep = new ArrayList<>();
        fiveStep.add(guanYaTemplateAnalysis);
        fiveStep.add(gangYaTemplateAnalysis);
        fiveStep.add(junGang1TemplateAnalysis);
        fiveStep.add(junGang2TemplateAnalysis);
        List<StepSelect> guanya = stepSelectService.selectByIdAndName(1,"管压");
        List<StepSelect> gangya = stepSelectService.selectByIdAndName(1,"缸压");
        List<StepSelect> jungang = stepSelectService.selectByIdAndName(1,"均缸");
        List<List<StepSelect>> stepSelects = new ArrayList<>();
        stepSelects.add(guanya);
        stepSelects.add(gangya);
        stepSelects.add(jungang);
//        FiveStepTemplate fiveStepTemplate = StepTemplateUtil.stepFinder(quanChengs1,fiveStep,stepSelects);
//        System.out.println(fiveStepTemplate);
    }
    @Test
    void TestStepUtil(){
        List<QuanCheng>quanChengs = quanChengService.selectByFileAscXuhao(1237);
        List<QuanCheng> quanChengs1 =  StepTemplateUtil.TimeTemplate(quanChengs);
        List<StepSelect> guanya = stepSelectService.selectByIdAndName(1,"管");
        List<StepSelect> gangya = stepSelectService.selectByIdAndName(1,"列");
        List<StepSelect> jungang = stepSelectService.selectByIdAndName(1,"均");
        List<List<StepSelect>> stepSelects = new ArrayList<>();
        stepSelects.add(guanya);
        stepSelects.add(gangya);
        stepSelects.add(jungang);
        List<List<ZhuanDian>> res = StepTemplateUtil.stepFinder("管均列",stepSelects,quanChengs1);
        for (List<ZhuanDian> re : res) {
            for (ZhuanDian zhuanDian : re) {
                System.out.println(zhuanDian);
            }
        }
    }
}
