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
    @RequestMapping("addTemplateSelect")
   public String addTemplateSelect(StepSelect select,
                                    Model model){
        if(select.getIsDepend()!=-1 ){
            String dependStress = "管";
            if(select.getIsDepend()==2){
                dependStress = "缸";
            }else if(select.getIsDepend()==3){
                dependStress = "均";
            }
            if (dependStress.equals(select.getStressName())){
                select.setIsDepend(-1);
            }else {
                int allCount = stepSelectService.selectCountPrior(select.getTemplateId(),select.getStressName());
                if (select.getStartId()>select.getEndID()){
                    int temp= select.getStartId();
                    select.setStartId(select.getEndID());
                    select.setEndID(temp);
                }
                if (select.getStartId()>allCount || select.getEndID()>allCount){
                    select.setStartId(-1);
                    select.setEndID(-1);
                }
            }
        }
        AllTemplate allTemplate = allTemplateService.selectById(select.getTemplateId());
        select.setGuanSort(allTemplate.getGuanSort());
        Integer prior = stepSelectService.selectCountPrior(select.getTemplateId(),select.getStressName());
        select.setPriorNumber(prior+1);
        if(select.getMaxTime()==null){
            //如果没有设置就设置成 一个特别大的值
            select.setMaxTime(100000);
        }
        if(select.getMinTime()==null){
            select.setMinTime(0);
        }
        if(select.getMaxStress()<select.getMinStress()){
            int max = select.getMaxStress();
            int min = select.getMinStress();
            select.setMaxStress(min);
            select.setMinStress(max);
        }
        stepSelectService.insertStepSelect(select);
//        List<StepSelect> guanya = stepSelectService.selectByIdAndName(select.getTemplateId(),"管压");
//        List<StepSelect> gangya = stepSelectService.selectByIdAndName(select.getTemplateId(),"缸压");
//        List<StepSelect> jungang = stepSelectService.selectByIdAndName(select.getTemplateId(),"均缸");
//        model.addAttribute("guanyas",guanya);
//        model.addAttribute("gangyas",gangya);
//        model.addAttribute("jungangs",jungang);
//        model.addAttribute("templateId",select.getTemplateId());

        return  "redirect:/modefiyTemplate?id="+select.getTemplateId();
    }

}
