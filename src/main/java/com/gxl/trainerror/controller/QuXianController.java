package com.gxl.trainerror.controller;

import com.gxl.trainerror.bean.FileInfo;
import com.gxl.trainerror.bean.QuanCheng;
import com.gxl.trainerror.service.FileInfoService;
import com.gxl.trainerror.service.QuanChengService;
import com.gxl.trainerror.service.StepAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.swing.plaf.LabelUI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
public class QuXianController {
    @Autowired
    private QuanChengService quanChengService;
    @Autowired
    private StepAnalysisService stepAnalysisService;
    @Autowired
    private FileInfoService fileInfoService;
    @RequestMapping("/quxianIndex")
    public String quXianIndex(@RequestParam("id") Integer id, Model model){
        List<QuanCheng> quanCheng= quanChengService.selectByFileAscXuhao(id);
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        List<String> times = new ArrayList<>();
        List<Integer> guanya = new ArrayList<>();
        List<Integer> gangya = new ArrayList<>();
        List<Integer> jungang1 = new ArrayList<>();
        List<Integer> jungang2 = new ArrayList<>();
        List<Integer> restrictSpeed = new ArrayList<>();
        List<Integer> speed = new ArrayList<>();
        List<Integer> zhuanSuDianLiu = new ArrayList<>();
        String timeLast=sdf.format(quanCheng.get(0).getDateTime());
        for (QuanCheng cheng : quanCheng) {
            String timeNow=sdf.format(cheng.getDateTime());
            if (!timeNow.equals(timeLast)){
                if (cheng.getGangYa()!=null&&cheng.getGuanYa()!=null
                ){
                    times.add(sdf.format(cheng.getDateTime()));
                    guanya.add(cheng.getGuanYa());
                    gangya.add(cheng.getGangYa());
                    jungang1.add(cheng.getJunGang1());
                    jungang2.add(cheng.getJunGang2());
                    restrictSpeed.add(cheng.getRestrictSpeed());
                    speed.add(cheng.getSpeed());
                    zhuanSuDianLiu.add(cheng.getZhuanSuDianLiu());
                }


            }
            timeLast = timeNow;
        }
        model.addAttribute("quanCheng",quanCheng);
        model.addAttribute("times",times);
        model.addAttribute("guanya",guanya);
        model.addAttribute("gangya",gangya);
        model.addAttribute("jungang1",jungang1);
        model.addAttribute("jungang2",jungang2);
        model.addAttribute("restrictSpeed",restrictSpeed);
        model.addAttribute("speed",speed);
        model.addAttribute("zhuanSuDianLiu",zhuanSuDianLiu);
        FileInfo fileInfo =fileInfoService.selectFileInfoById(id);
        model.addAttribute("fileInfo",fileInfo);
        return "quxian";
    }

}
