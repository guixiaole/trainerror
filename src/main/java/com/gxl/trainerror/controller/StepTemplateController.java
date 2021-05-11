package com.gxl.trainerror.controller;

import com.gxl.trainerror.bean.AllTemplate;
import com.gxl.trainerror.service.AllTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class StepTemplateController {
    @Autowired
    private AllTemplateService allTemplateService;
    @RequestMapping("templateIndex")
    public String TmeplateIndex(Model model){
        List<AllTemplate> allTemplates =allTemplateService.index();
        model.addAttribute("all_templates",allTemplates);
        return "template_index";
    }
    @RequestMapping("addTemplate")
    public String addTemplate(AllTemplate allTemplate){
        allTemplateService.insert(allTemplate);
        return "redirect:/templateIndex";
    }
    @RequestMapping("modefiyTemplate")
    public  String modefiyTemplate(@RequestParam("id")Integer id){

        return"modefiy_template";
    }
}
