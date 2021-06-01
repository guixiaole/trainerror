package com.gxl.trainerror.controller;

import com.gxl.trainerror.bean.FileInfo;
import com.gxl.trainerror.bean.QuanCheng;
import com.gxl.trainerror.bean.StepAnalysis;
import com.gxl.trainerror.bean.StepInfo;
import com.gxl.trainerror.service.FileInfoService;
import com.gxl.trainerror.service.QuanChengService;
import com.gxl.trainerror.service.StepAnalysisService;
import com.gxl.trainerror.service.StepInfoService;
import com.gxl.trainerror.util.StepTemplateUtil;
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
    @Autowired
    private StepInfoService stepInfoService;
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
        List<String> finalTimes = new ArrayList<>();

        List<QuanCheng> timeQuanCheng = StepTemplateUtil.TimeTemplate(quanCheng);
        for (QuanCheng cheng : timeQuanCheng) {
            String timeNow=sdf.format(cheng.getDateTime());
            finalTimes.add(timeNow);
            times.add(sdf.format(cheng.getDateTime()));
            guanya.add(cheng.getGuanYa());
            gangya.add(cheng.getGangYa());
            jungang1.add(cheng.getJunGang1());
            jungang2.add(cheng.getJunGang2());
            restrictSpeed.add(cheng.getRestrictSpeed());
            speed.add(cheng.getSpeed());
            zhuanSuDianLiu.add(cheng.getZhuanSuDianLiu());
        }
//        for (QuanCheng cheng : quanCheng) {
//            String timeNow=sdf.format(cheng.getDateTime());
//            finalTimes.add(timeNow);
//            if (!timeNow.equals(timeLast)){
//                if (cheng.getGangYa()!=null&&cheng.getGuanYa()!=null
//                ){
//                    times.add(sdf.format(cheng.getDateTime()));
//                    guanya.add(cheng.getGuanYa());
//                    gangya.add(cheng.getGangYa());
//                    jungang1.add(cheng.getJunGang1());
//                    jungang2.add(cheng.getJunGang2());
//                    restrictSpeed.add(cheng.getRestrictSpeed());
//                    speed.add(cheng.getSpeed());
//                    zhuanSuDianLiu.add(cheng.getZhuanSuDianLiu());
//                }
//

//
//            }
//            timeLast = timeNow;
//        }
        model.addAttribute("finalTimes",quanCheng.size());
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
        StepAnalysis stepAnalysis = stepAnalysisService.selectByFileID(id);
        if (stepAnalysis.getOneStep()!=null){
            StepInfo one = stepInfoService.selectById(stepAnalysis.getOneStep());
            String one_times = finalTimes.get(one.getStartXiangDian());
            model.addAttribute("one_times",one_times);
            model.addAttribute("one",one);
        }
        if (stepAnalysis.getTwoStep()!=null){
            StepInfo two = stepInfoService.selectById(stepAnalysis.getTwoStep());
            String two_times = finalTimes.get(two.getStartXiangDian());
            model.addAttribute("two_times",two_times);
            model.addAttribute("two",two);
        }
        if (stepAnalysis.getThreeStep()!=null){
            StepInfo three = stepInfoService.selectById(stepAnalysis.getThreeStep());
            String three_times = finalTimes.get(three.getStartXiangDian());
            model.addAttribute("three_times",three_times);
            model.addAttribute("three",three);
        }
        if (stepAnalysis.getFourStep()!=null){
            StepInfo four = stepInfoService.selectById(stepAnalysis.getFourStep());
            String four_times = finalTimes.get(four.getStartXiangDian());
            model.addAttribute("four_times",four_times);
            model.addAttribute("four",four);
        }
        if (stepAnalysis.getFiveStep()!=null){
            StepInfo five = stepInfoService.selectById(stepAnalysis.getFiveStep());
            String five_times = finalTimes.get(five.getStartXiangDian());
            model.addAttribute("five_times",five_times);
            model.addAttribute("five",five);
        }
        if (stepAnalysis.getSixStep()!=null){
            StepInfo five = stepInfoService.selectById(stepAnalysis.getSixStep());
            String five_times = finalTimes.get(five.getStartXiangDian());
            model.addAttribute("six_times",five_times);
            model.addAttribute("six",five);
        }
        if (stepAnalysis.getSevenStep()!=null){
            StepInfo five = stepInfoService.selectById(stepAnalysis.getSevenStep());
            String five_times = finalTimes.get(five.getStartXiangDian());
            model.addAttribute("seven_times",five_times);
            model.addAttribute("seven",five);
        }
        if (stepAnalysis.getEightStep()!=null){
            StepInfo five = stepInfoService.selectById(stepAnalysis.getEightStep());
            String five_times = finalTimes.get(five.getStartXiangDian());
            model.addAttribute("eight_times",five_times);
            model.addAttribute("eight",five);
        }
        if (stepAnalysis.getNineStep()!=null){
            StepInfo five = stepInfoService.selectById(stepAnalysis.getNineStep());
            String five_times = finalTimes.get(five.getStartXiangDian());
            model.addAttribute("nine_times",five_times);
            model.addAttribute("nine",five);
        }
        if (stepAnalysis.getShuangOneStep()!=null){
            StepInfo five = stepInfoService.selectById(stepAnalysis.getShuangOneStep());
            String five_times = finalTimes.get(five.getStartXiangDian());
            model.addAttribute("shaung_one_times",five_times);
            model.addAttribute("shuang_one",five);
        }
        if (stepAnalysis.getShuangTwoStep()!=null){
            StepInfo five = stepInfoService.selectById(stepAnalysis.getShuangTwoStep());
            String five_times = finalTimes.get(five.getStartXiangDian());
            model.addAttribute("shuang_two_times",five_times);
            model.addAttribute("shuang_two",five);
        }
        if (stepAnalysis.getShuangThreeStep()!=null){
            StepInfo five = stepInfoService.selectById(stepAnalysis.getShuangThreeStep());
            String five_times = finalTimes.get(five.getStartXiangDian());
            model.addAttribute("shuang_three_times",five_times);
            model.addAttribute("shuang_three",five);
        }
        if (stepAnalysis.getShuangFourStep()!=null){
            StepInfo five = stepInfoService.selectById(stepAnalysis.getShuangFourStep());
            String five_times = finalTimes.get(five.getStartXiangDian());
            model.addAttribute("shuang_four_times",five_times);
            model.addAttribute("shuang_four",five);
        }
        if (stepAnalysis.getShuangFiveStep()!=null){
            StepInfo five = stepInfoService.selectById(stepAnalysis.getShuangFiveStep());
            String five_times = finalTimes.get(five.getStartXiangDian());
            model.addAttribute("shuang_five_times",five_times);
            model.addAttribute("shuang_five",five);
        }
        if (stepAnalysis.getShuangSixStep()!=null){
            StepInfo five = stepInfoService.selectById(stepAnalysis.getShuangSixStep());
            String five_times = finalTimes.get(five.getStartXiangDian());
            model.addAttribute("shuang_six_times",five_times);
            model.addAttribute("shuang_six",five);
        }
        if (stepAnalysis.getShuangSevenStep()!=null){
            StepInfo five = stepInfoService.selectById(stepAnalysis.getShuangSevenStep());
            String five_times = finalTimes.get(five.getStartXiangDian());
            model.addAttribute("shuang_seven_times",five_times);
            model.addAttribute("shuang_seven",five);
        }
        if (stepAnalysis.getShuangEightStep()!=null){
            StepInfo five = stepInfoService.selectById(stepAnalysis.getShuangEightStep());
            String five_times = finalTimes.get(five.getStartXiangDian());
            model.addAttribute("shuang_eight_times",five_times);
            model.addAttribute("shuang_eight",five);
        }
        if (stepAnalysis.getShuangNineStep()!=null){
            StepInfo five = stepInfoService.selectById(stepAnalysis.getShuangNineStep());
            String five_times = finalTimes.get(five.getStartXiangDian());
            model.addAttribute("shuang_nine_times",five_times);
            model.addAttribute("shuang_nine",five);
        }

        return "quxian";
    }

}
