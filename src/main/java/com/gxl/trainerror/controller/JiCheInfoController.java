package com.gxl.trainerror.controller;

import com.gxl.trainerror.bean.AllTemplate;
import com.gxl.trainerror.bean.EventChange;
import com.gxl.trainerror.bean.JiCheInfo;
import com.gxl.trainerror.bean.StepShunXu;
import com.gxl.trainerror.service.AllTemplateService;
import com.gxl.trainerror.service.EventChangeService;
import com.gxl.trainerror.service.JiCheInfoService;
import com.gxl.trainerror.service.StepShunXuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class JiCheInfoController {
    @Autowired
    private JiCheInfoService jiCheInfoService;
    @Autowired
    private StepShunXuService stepShunXuService;
    @Autowired
    private EventChangeService eventChangeService;
    @Autowired
    private AllTemplateService allTemplateService;
    @RequestMapping("/insertJiChe")
    public String insertJiChe(JiCheInfo jiCheInfo){
        //机车应该是默认的。
        jiCheInfoService.insertJiChe(jiCheInfo);
        return"redirect:/ji_che_info";
    }
    @RequestMapping("/jiche")
    public String jiche(Model model){
        List<JiCheInfo> jiCheInfos = jiCheInfoService.selectAll();
        List<StepShunXu> stepShunXu = stepShunXuService.selectAll();
        List<EventChange> eventChanges = eventChangeService.selectAll();
        model.addAttribute("jiches",jiCheInfos);
        model.addAttribute("eventChange",eventChanges);
        model.addAttribute("stepShunXu",stepShunXu);
        return "ji_che_info";
    }
    @RequestMapping("/deleteJiChe")
    public String deleteJiChe(@RequestParam("id") Integer id){
        jiCheInfoService.deleteById(id);
        return"redirect:/ji_che_info";
    }
    public String stepShunXuIndex(@RequestParam("id") Integer id,
                                  @RequestParam("shunxuId") Integer shunxuId,Model model){
    StepShunXu stepShunXu = stepShunXuService.selectByid(shunxuId);
    model.addAttribute("steps",stepShunXu);
    List<AllTemplate> allTemplates = allTemplateService.index();
    model.addAttribute("alls",allTemplates);
    return "step_shunxu";
    }
    public String eventChangeIndex(@RequestParam("id") Integer id,
                                  @RequestParam("eventId") Integer eventId,
                                   Model model){
        EventChange eventChange = eventChangeService.selectByID(eventId);
        model.addAttribute("eventChange",eventChange);
        return "event_change";
    }
    @RequestMapping("/updatestep")
    public String updateStepshunxu(StepShunXu stepShunXu){
        //StepShunXu 应该是重新添加一个，而不是
        return null;
    }
}
