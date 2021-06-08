package com.gxl.trainerror;

import com.gxl.trainerror.bean.*;
import com.gxl.trainerror.controller.DLLCaptureController;
import com.gxl.trainerror.service.*;
import com.gxl.trainerror.util.FileUtil;
import com.gxl.trainerror.util.StepTemplateUtil;
import com.gxl.trainerror.util.TimeCal;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
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
    @Autowired
    private ZhuanDianService zhuanDianService;
    @Autowired
    private JiCheInfoService jiCheInfoService;
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
        List<QuanCheng>quanChengs = quanChengService.selectByFileAscXuhao(1242);
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
        List<QuanCheng>quanChengs = quanChengService.selectByFileAscXuhao(1297);
        List<QuanCheng> quanChengs1 =  StepTemplateUtil.TimeTemplate(quanChengs);
        List<StepSelect> guanya = stepSelectService.selectByIdAndName(1,"管");
        List<StepSelect> gangya = stepSelectService.selectByIdAndName(1,"列");
        List<StepSelect> jungang = stepSelectService.selectByIdAndName(1,"均");
        List<List<StepSelect>> stepSelects = new ArrayList<>();
        stepSelects.add(guanya);
        stepSelects.add(gangya);
        stepSelects.add(jungang);
        List<List<ZhuanDian>> res = StepTemplateUtil.stepFinder("均管列",stepSelects,quanChengs1);
        for (List<ZhuanDian> re : res) {
            for (ZhuanDian zhuanDian : re) {
               zhuanDianService.insertZhuanDian(zhuanDian);
            }
        }
    }
    @Test
    void LoadJiCheInfo() throws IOException {
        /*
            主要为了加载机车信息，一次性使用
         */
        String filePath = "D\\test\\";
        File[] files = FileUtil.getCurFilesList(filePath);
        if (files!=null&&files.length>0){
            for (File file : files) {
                if (file.getName().contains(".xls")){
                    InputStream str = new FileInputStream(file);
//        Workbook book = new HSSFWorkbook(str);
                    Workbook book = null;
                    try {
                        book = new XSSFWorkbook(file);
                    } catch (Exception ex) {
                        try {
                            book = new HSSFWorkbook(new FileInputStream(file));
                        }catch (Exception ex1) {
                            book = WorkbookFactory.create(str);
                        }
                    }

//        XSSFWorkbook book = new XSSFWorkbook(str);
                    Sheet sheet = book.getSheetAt(0);
                    List<List<String>> res = new ArrayList<>();
                    int rows = sheet.getLastRowNum();//总行数
                    for (int i= 1;i<rows;i++){
                        JiCheInfo jiCheInfo = new JiCheInfo();
                        String  jiXing =String.valueOf(sheet.getRow(i).getCell(0));
                        Integer jiXingHao =Integer.valueOf(String.valueOf(sheet.getRow(i).getCell(1)));
                        Integer jiCheHao =Integer.valueOf(String.valueOf(sheet.getRow(i).getCell(2)));
                        Integer danShuangDuan =Integer.valueOf(String.valueOf(sheet.getRow(i).getCell(3)));
                        Integer otherJiCheHao = Integer.valueOf(String.valueOf(sheet.getRow(i).getCell(4)));
                        String isHeGe = String.valueOf(sheet.getRow(i).getCell(5));
                        String zhiDongJiName = String.valueOf(sheet.getRow(i).getCell(6));
                        Integer zhiDongJiHao= Integer.valueOf(String.valueOf(sheet.getRow(i).getCell(7)));
                        Double lieZhiRatio= Double.valueOf(String.valueOf(sheet.getRow(i).getCell(8)));
                        jiCheInfo.setJiCheHao(jiCheHao);
                        jiCheInfo.setJiXingHao(jiXingHao);
                        jiCheInfo.setJiXing(jiXing);
                        jiCheInfo.setDanShuangDuan(danShuangDuan);
                        jiCheInfo.setOtherJiCheHao(otherJiCheHao);
                        jiCheInfo.setIsHeGe(isHeGe);
                        jiCheInfo.setZhiDongJiName(zhiDongJiName);
                        jiCheInfo.setZhiDongJiHao(zhiDongJiHao);
                        jiCheInfo.setLieZhiRatio(lieZhiRatio);
                        jiCheInfo.setEventChangeId(1);
                        jiCheInfo.setStepShunXuId(1);
                        jiCheInfoService.insertJiChe(jiCheInfo);
                    }
                }
            }
        }

    }

}
