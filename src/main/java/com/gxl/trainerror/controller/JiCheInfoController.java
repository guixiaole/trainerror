package com.gxl.trainerror.controller;

import com.gxl.trainerror.bean.JiCheInfo;
import com.gxl.trainerror.service.JiCheInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class JiCheInfoController {
    @Autowired
    private JiCheInfoService jiCheInfoService;
    @RequestMapping("/insertJiChe")
    public String insertJiChe(JiCheInfo jiCheInfo){
        jiCheInfoService.insertJiChe(jiCheInfo);
        return"ji_che_info";
    }
    @RequestMapping("/jiche")
    public String jiche(Model model){
        List<JiCheInfo> jiCheInfos = jiCheInfoService.selectAll();
        model.addAttribute("jiche",jiCheInfos);
        return "ji_che_info";
    }

}
