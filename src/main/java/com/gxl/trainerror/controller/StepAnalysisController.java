package com.gxl.trainerror.controller;

import com.gxl.trainerror.bean.FileInfo;
import com.gxl.trainerror.bean.QuanCheng;
import com.gxl.trainerror.bean.StepInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class StepAnalysisController {
    @ResponseBody
    @RequestMapping("")
    public String insertStep(@RequestParam("id")Integer id,
                             @RequestParam("step")Integer step,
                             @RequestParam("start")Integer start,
                             @RequestParam("end")Integer end,
                             @RequestParam("info")String info){
        StepInfo stepInfo =new StepInfo();
        FileInfo fileInfo = new FileInfo();
        return null;

    }
}
