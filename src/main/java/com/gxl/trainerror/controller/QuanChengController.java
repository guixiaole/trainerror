package com.gxl.trainerror.controller;

import com.gxl.trainerror.bean.FileInfo;
import com.gxl.trainerror.bean.QuanCheng;
import com.gxl.trainerror.bean.StepAnalysis;
import com.gxl.trainerror.bean.StepInfo;
import com.gxl.trainerror.service.FileInfoService;
import com.gxl.trainerror.service.QuanChengService;
import com.gxl.trainerror.service.StepAnalysisService;
import com.gxl.trainerror.service.StepInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
    @RequestMapping("/quancheng")
    public String getQuanCheng(Integer id, Model model){
        List<QuanCheng> quanChengs = quanChengService.selectByFileAscXuhao(id);

        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        StepAnalysis stepAnalysis = stepAnalysisService.selectByFileID(id);
        model.addAttribute("stepAnalysis",stepAnalysis);
        if (stepAnalysis.getOneStep()!=null){
            StepInfo one = stepInfoService.selectById(stepAnalysis.getOneStep());
            model.addAttribute("one",one);
        }
        if (stepAnalysis.getTwoStep()!=null){
            StepInfo two = stepInfoService.selectById(stepAnalysis.getTwoStep());
            model.addAttribute("two",two);
        }
        if (stepAnalysis.getThreeStep()!=null){
            StepInfo three = stepInfoService.selectById(stepAnalysis.getThreeStep());
            model.addAttribute("three",three);
        }
        if (stepAnalysis.getFourStep()!=null){
            StepInfo four = stepInfoService.selectById(stepAnalysis.getFourStep());
            model.addAttribute("four",four);
        }
        if (stepAnalysis.getFiveStep()!=null){
            StepInfo five = stepInfoService.selectById(stepAnalysis.getFiveStep());
            model.addAttribute("five",five);
        }
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
}
