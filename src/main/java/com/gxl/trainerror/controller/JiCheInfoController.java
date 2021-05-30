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
    @RequestMapping("/stepShunXuIndex")
    public String stepShunXuIndex(@RequestParam("id") Integer id,
                                  @RequestParam("shunxuId") Integer shunxuId,Model model){
    StepShunXu stepShunXu = stepShunXuService.selectByid(shunxuId);
    model.addAttribute("steps",stepShunXu);
    List<AllTemplate> allTemplates = allTemplateService.index();
    model.addAttribute("alls",allTemplates);
    model.addAttribute("jicheid",id);
    return "step_shunxu";
    }
    @RequestMapping("eventChangeIndex")
    public String eventChangeIndex(@RequestParam("id") Integer id,
                                  @RequestParam("eventId") Integer eventId,
                                   Model model){
        EventChange eventChange = eventChangeService.selectByID(eventId);
        model.addAttribute("eventChange",eventChange);
        return "event_change";
    }
    @RequestMapping("/updatestep")
    public String updateStepshunxu(StepShunXu stepShunXu,
                                   @RequestParam("jicheId")Integer jicheid){
        //修改顺序的时候，就新添加一个。
        //StepShunXu 应该是重新添加一个，而不是
        if (stepShunXu.getOneStep()==0){
            stepShunXu.setOneStep(null);
        }
        if (stepShunXu.getTwoStep()==0){
            stepShunXu.setTwoStep(null);
        }
        if (stepShunXu.getThreeStep()==0){
            stepShunXu.setThreeStep(null);
        }
        if (stepShunXu.getFourStep()==0){
            stepShunXu.setFourStep(null);
        }
        if (stepShunXu.getFiveStep()==0){
            stepShunXu.setFiveStep(null);
        }
        if (stepShunXu.getSixStep()==0){
            stepShunXu.setSixStep(null);
        }
        if (stepShunXu.getSevenStep()==0){
            stepShunXu.setSevenStep(null);
        }
        if (stepShunXu.getEightStep()==0){
            stepShunXu.setEightStep(null);
        }
        if (stepShunXu.getNineStep()==0){
            stepShunXu.setNineStep(null);
        }
        Integer newShunXuId = stepShunXuService.insertStepShunXu(stepShunXu);
        jiCheInfoService.updateStepShunXuById(jicheid,newShunXuId);
        //返回到相对应的界面去
        return "redirect:/stepShunXuIndex?id="+jicheid+"&shunxuId="+newShunXuId;
    }

    public String updateEventChange(EventChange eventChange,
                                    Model model,
                                    @RequestParam("oldEventId")Integer oldEventId,
                                    @RequestParam("jicheId")Integer jicheid){
        int temp[] = new int[18];
        temp[eventChange.getEvent()]++;
        temp[eventChange.getDateTime()]++;
        temp[eventChange.getGongLiBiao()]++;
        temp[eventChange.getOther()]++;
        temp[eventChange.getDistance()]++;
        temp[eventChange.getSignalMachine()]++;
        temp[eventChange.getXinHao()]++;
        temp[eventChange.getSpeed()]++;
        temp[eventChange.getRestrictSpeed()]++;
        temp[eventChange.getLingWei()]++;
        temp[eventChange.getQianYin()]++;
        temp[eventChange.getQianHou()]++;
        temp[eventChange.getGuanYa()]++;
        temp[eventChange.getGangYa()]++;
        temp[eventChange.getZhuanSuDianLiu()]++;
        temp[eventChange.getJunGang1()]++;
        temp[eventChange.getJunGang2()]++;
        for(int i=1;i<18;i++){
            if(temp[i]>1){
                model.addAttribute("jicheId",jicheid);
                model.addAttribute("jicheId",oldEventId);
                return "messege";
            }
        }
        
        Integer neweventChangeId = eventChangeService.insertEventChange(eventChange);

        jiCheInfoService.updateEventChangeId(jicheid,neweventChangeId);
        return "redirect:/eventChangeIndex?id="+jicheid+"&eventId="+neweventChangeId;
    }
}
