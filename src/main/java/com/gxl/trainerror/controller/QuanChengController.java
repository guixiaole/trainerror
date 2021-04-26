package com.gxl.trainerror.controller;

import com.gxl.trainerror.bean.*;
import com.gxl.trainerror.service.*;
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
    @Autowired
    private XiangDianService xiangDianService;
    @RequestMapping("/quancheng")
    public String getQuanCheng(Integer id, Model model){
        List<QuanCheng> quanChengs = quanChengService.selectByFileAscXuhao(id);

        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        StepAnalysis stepAnalysis = stepAnalysisService.selectByFileID(id);
        model.addAttribute("stepAnalysis",stepAnalysis);
        if (stepAnalysis.getOneStep()!=null){
            StepInfo one = stepInfoService.selectById(stepAnalysis.getOneStep());
            if(one.getXiangDianId()!=null){
                XiangDian oneXiangDian = xiangDianService.selectById(one.getXiangDianId());
                model.addAttribute("oneXiangDian",oneXiangDian);
            }
            model.addAttribute("one",one);
        }
        if (stepAnalysis.getTwoStep()!=null){
            StepInfo two = stepInfoService.selectById(stepAnalysis.getTwoStep());
            model.addAttribute("two",two);
            if(two.getXiangDianId()!=null){
                XiangDian twoXiangDian = xiangDianService.selectById(two.getXiangDianId());
                model.addAttribute("twoXiangDian",twoXiangDian);
            }
        }
        if (stepAnalysis.getThreeStep()!=null){
            StepInfo three = stepInfoService.selectById(stepAnalysis.getThreeStep());
            model.addAttribute("three",three);
            if(three.getXiangDianId()!=null){
                XiangDian oneXiangDian = xiangDianService.selectById(three.getXiangDianId());
                model.addAttribute("threeXiangDian",oneXiangDian);
            }
        }
        if (stepAnalysis.getFourStep()!=null){
            StepInfo four = stepInfoService.selectById(stepAnalysis.getFourStep());
            model.addAttribute("four",four);
            if(four.getXiangDianId()!=null){
                XiangDian oneXiangDian = xiangDianService.selectById(four.getXiangDianId());
                model.addAttribute("fourXiangDian",oneXiangDian);
            }
        }
        if (stepAnalysis.getFiveStep()!=null){
            StepInfo five = stepInfoService.selectById(stepAnalysis.getFiveStep());
            model.addAttribute("five",five);
            if(five.getXiangDianId()!=null){
                XiangDian oneXiangDian = xiangDianService.selectById(five.getXiangDianId());
                model.addAttribute("fiveXiangDian",oneXiangDian);
            }
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
