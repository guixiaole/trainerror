package com.gxl.trainerror.controller;

import com.gxl.trainerror.bean.*;
import com.gxl.trainerror.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
public class QuanChengController {
    @Autowired
    private QuanChengService quanChengService;
    @Autowired
    private FileInfoService fileInfoService;
    @Autowired
    private StepAnalysisService stepAnalysisService;
    @Autowired
    private StepInfoService stepInfoService;
    @Autowired
    private XiangDianService xiangDianService;
    @RequestMapping("/quancheng")
    public String getQuanCheng(Integer id, Model model){
        List<QuanCheng> quanChengs = quanChengService.selectByFileAscXuhao(id);

        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        StepAnalysis stepAnalysis = stepAnalysisService.selectStepInfoByFileId(id);
        model.addAttribute("stepAnalysis",stepAnalysis);

        model.addAttribute("quanChengs",quanChengs);
        if (quanChengs.size()>0){
            Integer fileid = quanChengs.get(0).getFileId();
            model.addAttribute("fileid",fileid);
            FileInfo fileInfo=fileInfoService.selectFileInfoById(id);
            model.addAttribute("fileInfo",fileInfo);
        }
        List<String> Times = new ArrayList<>();
        for (QuanCheng quanCheng : quanChengs) {
           Times.add(sdf.format(quanCheng.getDateTime()));
        }
        model.addAttribute("times",Times);

        return "allrecord";
    }
    @RequestMapping("/jumpToQuancheng")
    public String jumpToQuancheng(@RequestParam("id") Integer id,
                                  @RequestParam("step") String step,
                                  Model model){
        List<QuanCheng> quanChengs = quanChengService.selectByFileAscXuhao(id);

        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        StepAnalysis stepAnalysis = stepAnalysisService.selectStepInfoByFileId(id);
        model.addAttribute("stepAnalysis",stepAnalysis);

        model.addAttribute("quanChengs",quanChengs);
        if (quanChengs.size()>0){
            Integer fileid = quanChengs.get(0).getFileId();
            model.addAttribute("fileid",fileid);
            FileInfo fileInfo=fileInfoService.selectFileInfoById(id);
            model.addAttribute("fileInfo",fileInfo);
        }
        List<String> Times = new ArrayList<>();
        for (QuanCheng quanCheng : quanChengs) {
            Times.add(sdf.format(quanCheng.getDateTime()));
        }
        model.addAttribute("times",Times);
        if (step.equals("one")){
            model.addAttribute("step",stepAnalysis.getOne());
        }else if(step.equals("two")){
            model.addAttribute("step",stepAnalysis.getTwo());
        }else if(step.equals("three")){
            model.addAttribute("step",stepAnalysis.getThree());
        }else if(step.equals("four")){
            model.addAttribute("step",stepAnalysis.getFour());
        }else {
            model.addAttribute("step",stepAnalysis.getFive());
        }
        return "allrecord";
    }
}
