package com.gxl.trainerror.controller;

import com.gxl.trainerror.bean.AllTemplate;
import com.gxl.trainerror.bean.StepSelect;
import com.gxl.trainerror.mapper.StepSelectMapper;
import com.gxl.trainerror.service.AllTemplateService;
import com.gxl.trainerror.service.StepSelectService;
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
    @Autowired
    private StepSelectService stepSelectService;
    @RequestMapping("/templateIndex")
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
    public  String modefiyTemplate(@RequestParam("id")Integer id,Model model){
        List<StepSelect> guanya = stepSelectService.selectByIdAndName(id,"管压");
        List<StepSelect> gangya = stepSelectService.selectByIdAndName(id,"缸压");
        List<StepSelect> jungang = stepSelectService.selectByIdAndName(id,"均缸");
        model.addAttribute("guanyas",guanya);
        model.addAttribute("gangyas",gangya);
        model.addAttribute("jungangs",jungang);
        model.addAttribute("templateId",id);
        return"modefiy_template";
    }
//    @RequestMapping("addTemplateSelect")
//   public String addTemplateSelect(StepSelect select,
//                                    @RequestParam("tongbu")String tongBustressName,
//                                     @RequestParam("tongbuNumber")Integer tongBuPrior,
//                                    Model model){
//        if (!tongBustressName.equals("无")){
//            System.out.println(tongBustressName.equals("无"));
//         StepSelect tongbu =  stepSelectService.selectIdPriorName(select.getTemplateId(),tongBustressName,tongBuPrior);
//         select.setSelectId(tongbu.getTemplateId());
//        }
//        Integer prior = stepSelectService.selectCountPrior(select.getTemplateId(),select.getStressName());
//        select.setPriorNumber(prior+1);
//        if(select.getMaxTime()==null){
//            //如果没有设置就设置成 一个特别大的值
//            select.setMaxTime(100000);
//        }
//        if(select.getMinTime()==null){
//            select.setMinTime(0);
//        }
//        stepSelectService.insertStepSelect(select);
//        List<StepSelect> guanya = stepSelectService.selectByIdAndName(select.getTemplateId(),"管压");
//        List<StepSelect> gangya = stepSelectService.selectByIdAndName(select.getTemplateId(),"缸压");
//        List<StepSelect> jungang = stepSelectService.selectByIdAndName(select.getTemplateId(),"均缸");
//        model.addAttribute("guanyas",guanya);
//        model.addAttribute("gangyas",gangya);
//        model.addAttribute("jungangs",jungang);
//        model.addAttribute("templateId",select.getTemplateId());
//
//        return  "modefiy_template";
//    }

}
