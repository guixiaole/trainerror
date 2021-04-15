package com.gxl.trainerror.controller;

import com.gxl.trainerror.bean.*;
import com.gxl.trainerror.service.QuanChengService;
import com.gxl.trainerror.service.StepAnalysisService;
import com.gxl.trainerror.service.StepInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
public class StepAnalysisController {
    @Autowired
    private QuanChengService quanChengService;
    @Autowired
    private StepInfoService stepInfoService;
    @Autowired
    private StepAnalysisService stepAnalysisService;
    @ResponseBody
    @RequestMapping("/insertStep")
    public String insertStep(@RequestParam("id")Integer id,
                             @RequestParam("step")Integer step,
                             @RequestParam("start")Integer start,
                             @RequestParam("end")Integer end,
                             @RequestParam("info")String info){
        StepInfo stepInfo =new StepInfo();
        stepInfo.setStartXiangDian(start);
        stepInfo.setEndXiangDian(end);
        //插入五步闸的位置
        if(stepInfoService.insertStartEnd(stepInfo)>0){
            StepAnalysis stepAnalysis = stepAnalysisService.selectByFileID(id);
            if (step==1){
                stepAnalysis.setOneStep(step);
            }else if(step==2){
                stepAnalysis.setTwoStep(step);
            }else if (step==3){
                stepAnalysis.setThreeStep(step);
            }else if (step==4){
                stepAnalysis.setFourStep(step);
            }else {
                stepAnalysis.setFiveStep(step);
            }
            stepAnalysisService.updateAnyStep(stepAnalysis);
        }

        return null;

    }
    @RequestMapping("/loginDesign")
    public String loginDesign(@RequestParam("id")Integer id,
                            Model model,
                            HttpSession session){
        User user =(User) session.getAttribute("user");
        if (!user.getUserid().equals("gxl")){
            return "redirect:/index.html";
        }else {
            List<QuanCheng> quanChengs=quanChengService.selectByFileAscXuhao(id);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            model.addAttribute("quanChengs",quanChengs);
            List<String> Times = new ArrayList<>();
            if (quanChengs.size()>1){
                Integer fileId=  quanChengs.get(0).getFileId();
                model.addAttribute("fileid",fileId);
            }

            for (QuanCheng quanCheng : quanChengs) {
                Times.add(sdf.format(quanCheng.getDateTime()));
            }
            model.addAttribute("times",Times);
            return "step_design";
        }
    }
}
