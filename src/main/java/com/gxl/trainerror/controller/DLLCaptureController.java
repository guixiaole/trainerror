package com.gxl.trainerror.controller;

import com.gxl.trainerror.bean.*;
import com.gxl.trainerror.service.*;
import com.gxl.trainerror.util.ExplaceSql;
import com.gxl.trainerror.util.FindeStep;
import com.gxl.trainerror.util.StepTemplateUtil;
import org.apache.catalina.User;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Configuration
@EnableScheduling
public class DLLCaptureController {
    @Autowired
    private QuanChengService quanChengService;
    @Autowired
    private FileInfoService fileInfoService;
    @Autowired
    private StepAnalysisService stepAnalysisService;
    @Autowired
    private JiCheInfoService jiCheInfoService;
    @Autowired
    private StepSelectService stepSelectService;
    @Autowired
    private StepInfoService stepInfoService;
    /*
    读取该文件下的所有文件，并且获得txt文件，然后将其移动到该天下面的文件夹下面去
     */
    public static File[] getCurFilesList(String filePath) {
        File path = new File(filePath);
        File[] listFiles = path.listFiles(new java.io.FileFilter() {

            @Override
            public boolean accept(File pathname) {
                if (pathname.isFile())
                    return true;
                else
                    return false;
            }
        });

        return listFiles;
    }
    //判断是不是txt文件
    public static boolean  isTxt(String fileName){
        String txtName = ".txt";
        int fileFlag = fileName.length();
        int minus = 4;
        int txtflag  = 0;
        while (txtflag<txtName.length()){
            if (txtName.charAt(txtflag)!=fileName.charAt(fileFlag-minus)){
                return false;

            }
            txtflag++;
            minus--;
        }
        return true;
    }
    //调用c++动态库读取
    //
    @Scheduled(cron = "0/10 * * * * ?")
    public void readDLL() throws IOException, ParseException {
        String fileName = "D:/Release/VSread.exe";
        Runtime rt = Runtime.getRuntime();
        String exePath = fileName;
        File[] inputfiles = DLLCaptureController.getCurFilesList("D:\\input");
        if (inputfiles.length > 0) {
            try {
                rt.exec(exePath);
                System.out.println("do-------------");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }


        /*
           解析该文件之后，读取该文件的txt
         */


            String fileOldPath = "D:\\output";
            File[] files = DLLCaptureController.getCurFilesList(fileOldPath);
            if (files.length > 0) { //当大于0 的时候,才创建
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String filePath = "D:\\output\\" + sdf.format(date);
                File file = new File(filePath);
                String oldNmae = null;
                Integer id = 0;
                if (!file.exists()) {
                    file.mkdir();
//
//            System.out.println("文件创建成功");
                }
                for (File file1 : files) {

//
//                    Integer flag = 1;
//                    System.out.println(fileOldName);
//                    System.out.println("len=" + fileOldName.length);
//                    if (fileOldName[fileOldName.length - 1].length() >= 4) {
//                        for (int h = 0; h < 4; h++) {
//                            if (!('0' <= fileOldName[fileOldName.length - 1].charAt(h) && fileOldName[fileOldName.length - 1].charAt(h) <= '9')) {
//                                flag = 0;
//                            }
//                        }
//                    } else {
//                        flag = 0;
//                    }
//
//                    if (flag == 1)
//                        oldNmae = file1.getName();
                    if (file1.getName().contains(".txt")) {
//                System.out.println("this file is txt:"+file1.getName());
                        //读取txt文件
                        ArrayList<String>[] lists = ExplaceSql.read(fileOldPath + "\\" + file1.getName());
                        //在这之前还需要进行一个存储，就是把文件存储进去，设计一个独立的表格。
                        if (lists.length == 18) {
                            //不仅仅要总的行数大于18，也要里面的数大于一定的数才行。
                            if (lists[0].size() > 10 && lists[10].size() > 10 && lists[17].size() > 10) {
                                if (fileInfoService.selectFileInfoByName(file1.getName()) == null) {

                                    FileInfo fileInfo = new FileInfo();
                                    fileInfo.setFileName(file1.getName());
                                    String [] fileOldName = file1.getName().split("\\.");
                                    String oldName=null;
                                    String fileTempName = file1.getName();
                                    String tempName = fileTempName.substring(0,fileTempName.length()-4);
                                    fileInfo.setOldFileName(tempName);
                                    fileInfo.setIsSave(0);
                                    fileInfo.setUploadTime(new Date());
                                    fileInfoService.insertFileInfoStart(fileInfo);
                                    id = fileInfo.getId();

                                    //进行插入操作
                                    if (id >= 0){
                                        StepAnalysis stepAnalysis1 =new StepAnalysis(id);
                                        //插入五步闸的信息
                                        stepAnalysisService.insertOnlyFileID(stepAnalysis1);
                                        quanChengService.insertQuanCheng(lists, id);
                                        //在插入的时候，就应该去
                                        //当主键插入进去的时候，再进行插入。
                                        StepAnalysis stepAnalysis = stepAnalysisService.selectStepInfoByFileId(id);
                                        FileInfo newFileInfo = fileInfoService.selectFileInfoById(id);
                                        if (newFileInfo.getJiXing()!=null && newFileInfo.getJiCheHao()!=null){
                                            List<QuanCheng> quanChengs = quanChengService.selectByFileAscXuhao(id);
                                            List<QuanCheng> quanChengsTemplate = StepTemplateUtil.TimeTemplate(quanChengs);
                                            //写一个检测单双端的方法，然后通过单双端分别写入。

                                            //分别插入，无论是单端还是双端。
                                            JiCheInfo jiCheInfo = jiCheInfoService.selectByJiXingJiChe(newFileInfo.getJiXing(),newFileInfo.getJiCheHao());
                                            StepShunXu stepShunXu = jiCheInfo.getStepShunXu();
                                            //单双端怎么检测啊？
                                            if(jiCheInfo.getDanShuangDuan()==1){
                                                storeStep(quanChengsTemplate,stepShunXu,1,stepAnalysis);
                                            }else {
                                                List<List<QuanCheng>> shuangQuanchengs = StepTemplateUtil.spliteQuanCheng(quanChengsTemplate);
                                                if (shuangQuanchengs.size()>2){
                                                    storeStep(shuangQuanchengs.get(0),stepShunXu,1,stepAnalysis);
                                                    storeStep(shuangQuanchengs.get(1),stepShunXu,2,stepAnalysis);
                                                }else {
                                                    storeStep(quanChengsTemplate,stepShunXu,1,stepAnalysis);
                                                }
                                            }

                                            //找到全程文件，然后再进行操作。
                                        }
                                    }


                                }
                            }
                        }

                        //插入之后就好了，下次直接可以查找
                        //在这里的时候都是需要存储到Sql中去的
                    }
                    Files.move(Paths.get(fileOldPath + "\\" + file1.getName()), Paths.get(filePath + "\\" + file1.getName()));
                }

            }
        }
    public  List<Integer> stepFinder (List<QuanCheng>quanChengs,AllTemplate template){
            //根据Template返回步骤的序号
            List<Integer> startAndEnd = new ArrayList<>();
            if (template!=null){
                List<StepSelect> guanya = stepSelectService.selectByIdAndName(template.getId(),"管");
                List<StepSelect> gangya = stepSelectService.selectByIdAndName(template.getId(),"列");
                List<StepSelect> jungang = stepSelectService.selectByIdAndName(template.getId(),"均");
                List<List<StepSelect>> stepSelects = new ArrayList<>();
                stepSelects.add(guanya);
                stepSelects.add(gangya);
                stepSelects.add(jungang);
                List<List<ZhuanDian>> res = StepTemplateUtil.stepFinder(template.getGuanSort(),stepSelects,quanChengs);
                if (res.size()>0){
                    //一般取最后一个
                    List<ZhuanDian> last = res.get(res.size()-1);
                    if(last.size()>1){
                        startAndEnd.add(last.get(0).getStartXuHao());
                        startAndEnd.add(last.get(last.size()-1).getEndXuHao());
                    }else {
                        startAndEnd.add(last.get(0).getStartXuHao());
                        startAndEnd.add(last.get(0).getEndXuHao());
                    }
                    return startAndEnd;
                }else {
                    return null;
                }
            }else {
                return null;
            }
        }
    public void storeStep(List<QuanCheng>quanChengs,StepShunXu stepShunXu,Integer danShuangDuan,StepAnalysis stepAnalysis){
        //存储step按照单双端分开来存储。
        if(danShuangDuan==1){
            if(stepShunXu.getOne()!=null){

                List<Integer> one =  stepFinder(quanChengs,stepShunXu.getOne());
                if (one!=null){
                    Integer stepinfoId = insertStepInfo(one);
                    stepAnalysis.setOneStep(stepinfoId);

                }

            }
            if (stepShunXu.getTwo()!=null){
                List<Integer> one = stepFinder(quanChengs,stepShunXu.getTwo());
                if (one!=null){
                    Integer StepinfoId = insertStepInfo(one);
                    stepAnalysis.setTwoStep(StepinfoId);

                }
            }
            if (stepShunXu.getThree()!=null){
                List<Integer> one = stepFinder(quanChengs,stepShunXu.getThree());
                if (one!=null){
                    Integer StepinfoId = insertStepInfo(one);
                    stepAnalysis.setThreeStep(StepinfoId);

                }
            }
            if (stepShunXu.getFour()!=null){
                List<Integer> one = stepFinder(quanChengs,stepShunXu.getFour());
                if (one!=null){
                    Integer StepinfoId = insertStepInfo(one);
                    stepAnalysis.setFourStep(StepinfoId);

                }
            }
            if (stepShunXu.getFive()!=null){
                List<Integer> one = stepFinder(quanChengs,stepShunXu.getFive());
                if (one!=null){
                    Integer StepinfoId = insertStepInfo(one);
                    stepAnalysis.setFiveStep(StepinfoId);

                }
            }
            if (stepShunXu.getSix()!=null){
                List<Integer> one = stepFinder(quanChengs,stepShunXu.getSix());
                if (one!=null){
                    Integer StepinfoId = insertStepInfo(one);
                    stepAnalysis.setSixStep(StepinfoId);

                }
            }
            if (stepShunXu.getSeven()!=null){
                List<Integer> one = stepFinder(quanChengs,stepShunXu.getSeven());
                if (one!=null){
                    Integer StepinfoId = insertStepInfo(one);
                    stepAnalysis.setSevenStep(StepinfoId);

                }
            }
            if (stepShunXu.getEight()!=null){
                List<Integer> one = stepFinder(quanChengs,stepShunXu.getEight());
                if (one!=null){
                    Integer StepinfoId = insertStepInfo(one);
                    stepAnalysis.setEightStep(StepinfoId);

                }
            }
            if (stepShunXu.getNine()!=null){
                List<Integer> one = stepFinder(quanChengs,stepShunXu.getNine());
                if (one!=null){
                    Integer StepinfoId = insertStepInfo(one);
                    stepAnalysis.setNineStep(StepinfoId);
                }
            }
            stepAnalysisService.updateAnyStep(stepAnalysis);
        }else {
            if(stepShunXu.getOne()!=null){
                List<Integer> one =  stepFinder(quanChengs,stepShunXu.getOne());
                if (one!=null){
                    Integer stepinfoId = insertStepInfo(one);
                    stepAnalysis.setShuangOneStep(stepinfoId);

                }

            }
            if (stepShunXu.getTwo()!=null){
                List<Integer> one = stepFinder(quanChengs,stepShunXu.getTwo());
                if (one!=null){
                    Integer StepinfoId = insertStepInfo(one);
                    stepAnalysis.setShuangTwoStep(StepinfoId);

                }
            }
            if (stepShunXu.getThree()!=null){
                List<Integer> one = stepFinder(quanChengs,stepShunXu.getThree());
                if (one!=null){
                    Integer StepinfoId = insertStepInfo(one);
                    stepAnalysis.setShuangThreeStep(StepinfoId);

                }
            }
            if (stepShunXu.getFour()!=null){
                List<Integer> one = stepFinder(quanChengs,stepShunXu.getFour());
                if (one!=null){
                    Integer StepinfoId = insertStepInfo(one);
                    stepAnalysis.setShuangFourStep(StepinfoId);

                }
            }
            if (stepShunXu.getFive()!=null){
                List<Integer> one = stepFinder(quanChengs,stepShunXu.getFive());
                if (one!=null){
                    Integer StepinfoId = insertStepInfo(one);
                    stepAnalysis.setShuangFiveStep(StepinfoId);

                }
            }
            if (stepShunXu.getSix()!=null){
                List<Integer> one = stepFinder(quanChengs,stepShunXu.getSix());
                if (one!=null){
                    Integer StepinfoId = insertStepInfo(one);
                    stepAnalysis.setShuangSixStep(StepinfoId);

                }
            }
            if (stepShunXu.getSeven()!=null){
                List<Integer> one = stepFinder(quanChengs,stepShunXu.getSeven());
                if (one!=null){
                    Integer StepinfoId = insertStepInfo(one);
                    stepAnalysis.setShuangSevenStep(StepinfoId);

                }
            }
            if (stepShunXu.getEight()!=null){
                List<Integer> one = stepFinder(quanChengs,stepShunXu.getEight());
                if (one!=null){
                    Integer StepinfoId = insertStepInfo(one);
                    stepAnalysis.setShuangEightStep(StepinfoId);

                }
            }
            if (stepShunXu.getNine()!=null){
                List<Integer> one = stepFinder(quanChengs,stepShunXu.getNine());
                if (one!=null){
                    Integer StepinfoId = insertStepInfo(one);
                    stepAnalysis.setShuangNineStep(StepinfoId);
                }
            }
            stepAnalysisService.updateAnyStep(stepAnalysis);
        }

    }
    public Integer insertStepInfo(List<Integer> one){
            StepInfo stepInfo = new StepInfo();
            stepInfo.setStartXiangDian(one.get(0));
            stepInfo.setEndXiangDian(one.get(1));
            Integer id = stepInfoService.insertStartEnd(stepInfo);
            return stepInfo.getId();
    }
}