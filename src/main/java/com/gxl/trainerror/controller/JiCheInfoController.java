package com.gxl.trainerror.controller;

import com.gxl.trainerror.bean.AllTemplate;
import com.gxl.trainerror.bean.EventChange;
import com.gxl.trainerror.bean.JiCheInfo;
import com.gxl.trainerror.bean.StepShunXu;
import com.gxl.trainerror.service.AllTemplateService;
import com.gxl.trainerror.service.EventChangeService;
import com.gxl.trainerror.service.JiCheInfoService;
import com.gxl.trainerror.service.StepShunXuService;
import com.gxl.trainerror.util.FileUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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
        stepShunXu.setName("新"+stepShunXu.getName());
        stepShunXuService.insertStepShunXu(stepShunXu);
        jiCheInfoService.updateStepShunXuById(jicheid,stepShunXu.getId());
        //返回到相对应的界面去
        return "redirect:/stepShunXuIndex?id="+jicheid+"&shunxuId="+stepShunXu.getId();
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
        eventChange.setName("新"+eventChange.getName());
        Integer neweventChangeId = eventChangeService.insertEventChange(eventChange);

        jiCheInfoService.updateEventChangeId(jicheid,neweventChangeId);
        return "redirect:/eventChangeIndex?id="+jicheid+"&eventId="+neweventChangeId;
    }
    @RequestMapping("/loadjiche")
    public String DownloadJiChe() throws IOException {
        String filePath = "D:\\jiche\\";
        File[] files = FileUtil.getCurFilesList(filePath);
        if (files!=null&&files.length>0){
            for (File file : files) {
                if (file.getName().contains(".xls")){
                    InputStream str = new FileInputStream(file);
//        Workbook book = new HSSFWorkbook(str);
                    Workbook book = null;
                    try {
                        book = new XSSFWorkbook(file);
                    } catch (Exception ex) {
                        try {
                            book = new HSSFWorkbook(new FileInputStream(file));
                        }catch (Exception ex1) {
                            book = WorkbookFactory.create(str);
                        }
                    }

//        XSSFWorkbook book = new XSSFWorkbook(str);
                    Sheet sheet = book.getSheetAt(0);
                    List<List<String>> res = new ArrayList<>();
                    int rows = sheet.getLastRowNum();//总行数
                    for (int i= 1;i<rows;i++){
                        JiCheInfo jiCheInfo = new JiCheInfo();
                        try{
                            String  jiXing =String.valueOf(sheet.getRow(i).getCell(0));
                            Integer jiXingHao =Integer.valueOf(String.valueOf(sheet.getRow(i).getCell(1)));
                            Integer jiCheHao =Integer.valueOf(String.valueOf(sheet.getRow(i).getCell(2)));
                            Integer danShuangDuan =Integer.valueOf(String.valueOf(sheet.getRow(i).getCell(3)));
                            String tempOther = String.valueOf(sheet.getRow(i).getCell(4));
                            Integer otherJiCheHao = null;

                            if (!tempOther.equals("无")){
                                try{
                                    otherJiCheHao = (int)Double.parseDouble(tempOther);
                                }catch (Exception er){
                                    otherJiCheHao = Integer.valueOf(tempOther);
                                }
                            }
                            String isHeGe = String.valueOf(sheet.getRow(i).getCell(5));
                            String zhiDongJiName = String.valueOf(sheet.getRow(i).getCell(6));
//                            Integer zhiDongJiHao= Integer.valueOf(String.valueOf(sheet.getRow(i).getCell(7)));
                            String tempZhiDongJiHao = String.valueOf(sheet.getRow(i).getCell(7));
                            Integer zhiDongJiHao = null;
                            if (!tempOther.equals("无")){
                                try{
                                    zhiDongJiHao = (int)Double.parseDouble(tempZhiDongJiHao);
                                }catch (Exception er){
                                    zhiDongJiHao = Integer.valueOf(tempZhiDongJiHao);
                                }
                            }
                            Double lieZhiRatio= Double.valueOf(String.valueOf(sheet.getRow(i).getCell(8)));
                            jiCheInfo.setJiCheHao(jiCheHao);
                            jiCheInfo.setJiXingHao(jiXingHao);
                            jiCheInfo.setJiXing(jiXing);
                            jiCheInfo.setDanShuangDuan(danShuangDuan);
                            jiCheInfo.setOtherJiCheHao(otherJiCheHao);
                            jiCheInfo.setIsHeGe(isHeGe);
                            jiCheInfo.setZhiDongJiName(zhiDongJiName);
                            jiCheInfo.setZhiDongJiHao(zhiDongJiHao);
                            jiCheInfo.setLieZhiRatio(lieZhiRatio);
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                        jiCheInfo.setEventChangeId(1);
                        jiCheInfo.setStepShunXuId(1);
                        jiCheInfoService.insertJiChe(jiCheInfo);
                    }
                }

            }
        }
        return"load_jiche";
    }

}
