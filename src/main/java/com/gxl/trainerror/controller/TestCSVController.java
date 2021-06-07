package com.gxl.trainerror.controller;

import com.gxl.trainerror.bean.*;
import com.gxl.trainerror.service.*;
import com.gxl.trainerror.util.CSVRead;
import com.gxl.trainerror.util.StepTemplateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Configuration
@EnableScheduling
public class TestCSVController {
    @Autowired
    private FileInfoService fileInfoService;
    @Autowired
    private QuanChengService quanChengService;
    @Autowired
    private StepAnalysisService stepAnalysisService;
    @Autowired
    private JiCheInfoService jiCheInfoService;
    @Autowired
    private StepSelectService stepSelectService;
    @Autowired
    private StepInfoService stepInfoService;
    @Scheduled(cron = "0/10 * * * * ?")
    public void testCsvStepAnalysis() throws IOException, ParseException {
        /*
            此方法只是为了读取excel文件，然后进行存储。
            然后进行读取文件。
         */
        String filePath = "D:\\test\\";
        File[] files = DLLCaptureController.getCurFilesList(filePath);
        if(files.length>0){
            for (File file1 : files) {

                if(file1.getName().contains(".xls")){
                    String filiReadName = filePath+file1.getName();
                    FileInfo fileInfo = new FileInfo();
                    fileInfo.setOldFileName(file1.getName());
                    fileInfo.setIsSave(0);
                    fileInfo.setUploadTime(new Date());
                    fileInfoService.insertFileInfoStart(fileInfo);
                    int id = fileInfo.getId();
                    if(id>=0){
                        StepAnalysis stepAnalysis1 =new StepAnalysis(id);
                        stepAnalysisService.insertOnlyFileID(stepAnalysis1);
                        List<QuanCheng> quanChengsList = CSVRead.CscReader(filiReadName,id);
                        quanChengService.insertQuanChengByList(quanChengsList,fileInfo);
                        StepAnalysis stepAnalysis = stepAnalysisService.selectStepInfoByFileId(id);
                        FileInfo newFileInfo = fileInfoService.selectFileInfoById(id);
                        if (newFileInfo.getJiXing()!=null && newFileInfo.getJiCheHao()!=null){
                            List<QuanCheng> quanChengs = quanChengService.selectByFileAscXuhao(id);
                            List<QuanCheng> quanChengsTemplate = StepTemplateUtil.TimeTemplate(quanChengs);
                            //写一个检测单双端的方法，然后通过单双端分别写入。

                            //分别插入，无论是单端还是双端。
                            JiCheInfo jiCheInfo = jiCheInfoService.selectByJiXingJiChe(newFileInfo.getJiXing(),newFileInfo.getJiCheHao());
                            if(jiCheInfo==null){
                                jiCheInfo = jiCheInfoService.selectByJiXingHaoJiCheHao(newFileInfo.getJiXing(),newFileInfo.getJiCheHao());
                            }
                            StepShunXu stepShunXu = jiCheInfo.getStepShunXu();
                            //单双端怎么检测啊？
                            if(jiCheInfo.getDanShuangDuan()==1){
                                storeStep(quanChengsTemplate,stepShunXu,1,stepAnalysis,0);
                            }else {
                                List<List<QuanCheng>> shuangQuanchengs = StepTemplateUtil.spliteQuanCheng(quanChengsTemplate);
                                if (shuangQuanchengs.size()>=2){
                                    storeStep(shuangQuanchengs.get(0),stepShunXu,1,stepAnalysis,0);
                                    storeStep(shuangQuanchengs.get(1),stepShunXu,2,stepAnalysis,shuangQuanchengs.get(0).size());
                                }else {
                                    storeStep(quanChengsTemplate,stepShunXu,1,stepAnalysis,0);
                                }
                            }

                            //找到全程文件，然后再进行操作。
                        }
                    }
                }
                Files.move(Paths.get("D:\\test" + "\\" + file1.getName()), Paths.get("D:\\test\\newFile" + "\\" + file1.getName()));
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
                //取的话一般取的是1,2,3,4 分别为起始序号，结束序号，标准化起始位置，标准化结束位置
                List<ZhuanDian> last = res.get(res.size()-1);
                if(last.size()>1){
                    startAndEnd.add(last.get(0).getStartXuHao());
                    startAndEnd.add(last.get(last.size()-1).getEndXuHao());
                    startAndEnd.add(last.get(0).getStartPos());
                    startAndEnd.add(last.get(last.size()-1).getEndPos());
                }else {
                    startAndEnd.add(last.get(0).getStartXuHao());
                    startAndEnd.add(last.get(0).getEndXuHao());
                    startAndEnd.add(last.get(0).getStartPos());
                    startAndEnd.add(last.get(0).getEndPos());
                }
                return startAndEnd;
            }else {
                return null;
            }
        }else {
            return null;
        }
    }
    public void storeStep(List<QuanCheng>quanChengs,StepShunXu stepShunXu,Integer danShuangDuan,StepAnalysis stepAnalysis,int lastSize){
        //存储step按照单双端分开来存储。
        if(danShuangDuan==1){
            if(stepShunXu.getOne()!=null){

                List<Integer> one =  stepFinder(quanChengs,stepShunXu.getOne());
                if (one!=null){
                    Integer stepinfoId = insertStepInfo(one,lastSize);
                    stepAnalysis.setOneStep(stepinfoId);

                }

            }
            if (stepShunXu.getTwo()!=null){
                List<Integer> one = stepFinder(quanChengs,stepShunXu.getTwo());
                if (one!=null){
                    Integer StepinfoId = insertStepInfo(one,lastSize);
                    stepAnalysis.setTwoStep(StepinfoId);

                }
            }
            if (stepShunXu.getThree()!=null){
                List<Integer> one = stepFinder(quanChengs,stepShunXu.getThree());
                if (one!=null){
                    Integer StepinfoId = insertStepInfo(one,lastSize);
                    stepAnalysis.setThreeStep(StepinfoId);

                }
            }
            if (stepShunXu.getFour()!=null){
                List<Integer> one = stepFinder(quanChengs,stepShunXu.getFour());
                if (one!=null){
                    Integer StepinfoId = insertStepInfo(one,lastSize);
                    stepAnalysis.setFourStep(StepinfoId);

                }
            }
            if (stepShunXu.getFive()!=null){
                List<Integer> one = stepFinder(quanChengs,stepShunXu.getFive());
                if (one!=null){
                    Integer StepinfoId = insertStepInfo(one,lastSize);
                    stepAnalysis.setFiveStep(StepinfoId);

                }
            }
            if (stepShunXu.getSix()!=null){
                List<Integer> one = stepFinder(quanChengs,stepShunXu.getSix());
                if (one!=null){
                    Integer StepinfoId = insertStepInfo(one,lastSize);
                    stepAnalysis.setSixStep(StepinfoId);

                }
            }
            if (stepShunXu.getSeven()!=null){
                List<Integer> one = stepFinder(quanChengs,stepShunXu.getSeven());
                if (one!=null){
                    Integer StepinfoId = insertStepInfo(one,lastSize);
                    stepAnalysis.setSevenStep(StepinfoId);

                }
            }
            if (stepShunXu.getEight()!=null){
                List<Integer> one = stepFinder(quanChengs,stepShunXu.getEight());
                if (one!=null){
                    Integer StepinfoId = insertStepInfo(one,lastSize);
                    stepAnalysis.setEightStep(StepinfoId);

                }
            }
            if (stepShunXu.getNine()!=null){
                List<Integer> one = stepFinder(quanChengs,stepShunXu.getNine());
                if (one!=null){
                    Integer StepinfoId = insertStepInfo(one,lastSize);
                    stepAnalysis.setNineStep(StepinfoId);
                }
            }
            stepAnalysisService.updateAnyStep(stepAnalysis);
        }else {
            if(stepShunXu.getOne()!=null){
                List<Integer> one =  stepFinder(quanChengs,stepShunXu.getOne());
                if (one!=null){
                    Integer stepinfoId = insertStepInfo(one,lastSize);
                    stepAnalysis.setShuangOneStep(stepinfoId);

                }

            }
            if (stepShunXu.getTwo()!=null){
                List<Integer> one = stepFinder(quanChengs,stepShunXu.getTwo());
                if (one!=null){
                    Integer StepinfoId = insertStepInfo(one,lastSize);
                    stepAnalysis.setShuangTwoStep(StepinfoId);

                }
            }
            if (stepShunXu.getThree()!=null){
                List<Integer> one = stepFinder(quanChengs,stepShunXu.getThree());
                if (one!=null){
                    Integer StepinfoId = insertStepInfo(one,lastSize);
                    stepAnalysis.setShuangThreeStep(StepinfoId);

                }
            }
            if (stepShunXu.getFour()!=null){
                List<Integer> one = stepFinder(quanChengs,stepShunXu.getFour());
                if (one!=null){
                    Integer StepinfoId = insertStepInfo(one,lastSize);
                    stepAnalysis.setShuangFourStep(StepinfoId);

                }
            }
            if (stepShunXu.getFive()!=null){
                List<Integer> one = stepFinder(quanChengs,stepShunXu.getFive());
                if (one!=null){
                    Integer StepinfoId = insertStepInfo(one,lastSize);
                    stepAnalysis.setShuangFiveStep(StepinfoId);

                }
            }
            if (stepShunXu.getSix()!=null){
                List<Integer> one = stepFinder(quanChengs,stepShunXu.getSix());
                if (one!=null){
                    Integer StepinfoId = insertStepInfo(one,lastSize);
                    stepAnalysis.setShuangSixStep(StepinfoId);

                }
            }
            if (stepShunXu.getSeven()!=null){
                List<Integer> one = stepFinder(quanChengs,stepShunXu.getSeven());
                if (one!=null){
                    Integer StepinfoId = insertStepInfo(one,lastSize);
                    stepAnalysis.setShuangSevenStep(StepinfoId);

                }
            }
            if (stepShunXu.getEight()!=null){
                List<Integer> one = stepFinder(quanChengs,stepShunXu.getEight());
                if (one!=null){
                    Integer StepinfoId = insertStepInfo(one,lastSize);
                    stepAnalysis.setShuangEightStep(StepinfoId);

                }
            }
            if (stepShunXu.getNine()!=null){
                List<Integer> one = stepFinder(quanChengs,stepShunXu.getNine());
                if (one!=null){
                    Integer StepinfoId = insertStepInfo(one,lastSize);
                    stepAnalysis.setShuangNineStep(StepinfoId);
                }
            }
            stepAnalysisService.updateAnyStep(stepAnalysis);
        }

    }
    public Integer insertStepInfo(List<Integer> one,int lastSize){
        StepInfo stepInfo = new StepInfo();
        stepInfo.setStartXiangDian(one.get(0));
        stepInfo.setEndXiangDian(one.get(1));
        stepInfo.setStartPos(one.get(2)+lastSize);
        stepInfo.setEndPos(one.get(3)+lastSize);
        Integer id = stepInfoService.insertStartEnd(stepInfo);
        return stepInfo.getId();
    }
}
