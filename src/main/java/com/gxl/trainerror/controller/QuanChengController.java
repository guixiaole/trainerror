package com.gxl.trainerror.controller;

import com.gxl.trainerror.bean.QuanCheng;
import com.gxl.trainerror.service.QuanChengService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class QuanChengController {
    @Autowired
    private QuanChengService quanChengService;
    @RequestMapping("/quancheng")
    public String getQuanCheng(Integer id, Model model){
        List<QuanCheng> quanChengs = quanChengService.selectByFileAscXuhao(id);
        model.addAttribute("quanChengs",quanChengs);
        return "allrecord";
    }
}
