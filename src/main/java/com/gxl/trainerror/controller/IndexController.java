package com.gxl.trainerror.controller;

import com.gxl.trainerror.bean.FileInfo;
import com.gxl.trainerror.bean.StepAnalysis;
import com.gxl.trainerror.bean.StepInfo;
import com.gxl.trainerror.service.FileInfoService;
import com.gxl.trainerror.service.StepAnalysisService;
import com.gxl.trainerror.service.StepInfoService;
import com.gxl.trainerror.service.XiangDianService;
import com.gxl.trainerror.util.DownloadUtil;
import com.gxl.trainerror.util.TimeCal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private FileInfoService fileInfoService;
    @Autowired
    private StepInfoService stepInfoService;
    @Autowired
    private StepAnalysisService stepAnalysisService;
    @Autowired
    private XiangDianService xiangDianService;
    //在此类中为首页所有的方法
    @RequestMapping("/showTimeIndex")
    public String showTimeIndex(@RequestParam("time")Integer time,
                                Model model){
        /*
           显示时间：有一个日期，显示多久以前的文件可以显示。
           （一个小时，5个小时，12个小时，24个小时，3天，7天，
             一个月，全部。默认显示5个小时）
         */
        Date date = null;
        if (time ==1||time==12||time==24){
             date = TimeCal.backTime(time);
        }else {
             date =TimeCal.backDate(time);
        }
        List<FileInfo> fileInfos = fileInfoService.selectAllFileInfo(date);
        model.addAttribute("fileInfos",fileInfos);
        if (fileInfos.size()>0){
            FileInfo fileInfo = fileInfos.get(0);
            StepAnalysis stepAnalysis= stepAnalysisService.selectByFileID(fileInfo.getId());
            model.addAttribute("stepAnalysis",stepAnalysis);
        }
//        if (fileInfos.size()>0){
//            //在size大于0的时候，根据五步闸来
//            FileInfo fileInfo = fileInfos.get(0);
//            Integer id = fileInfo.getId();
//            StepAnalysis stepAnalysis= stepAnalysisService.selectByFileID(id);
//            //没有用mybatis的缓存
//            if (stepAnalysis.getOneStep()!=null){
//                StepInfo one  = stepInfoService.selectById(stepAnalysis.getOneStep());
//                model.addAttribute("one",one);
//            }
//            if (stepAnalysis.getTwoStep()!=null){
//                StepInfo two  = stepInfoService.selectById(stepAnalysis.getTwoStep());
//                model.addAttribute("two",two);
//            }
//            if (stepAnalysis.getThreeStep()!=null){
//                StepInfo three  = stepInfoService.selectById(stepAnalysis.getThreeStep());
//                model.addAttribute("three",three);
//            }
//            if (stepAnalysis.getFourStep()!=null){
//                StepInfo four  = stepInfoService.selectById(stepAnalysis.getFourStep());
//                model.addAttribute("four",four);
//            }
//            if (stepAnalysis.getFiveStep()!=null){
//                StepInfo five  = stepInfoService.selectById(stepAnalysis.getFiveStep());
//                model.addAttribute("five",five);
//            }
//        }
        return "index";
    }
    @PostMapping("/searchFile")
    public String searchFile(FileInfo fileInfo,Model model){
        System.out.println(fileInfo);
        List<FileInfo> fileInfos = fileInfoService.selectIndexFileInfo(fileInfo);
        model.addAttribute("fileInfos",fileInfos);
        return "index";
    }
    @RequestMapping("oldNameDownload")
    public String oldNameDownload(@RequestParam("filename") String oldName
            , @RequestParam("id") Integer fileId, HttpServletRequest request, HttpServletResponse response){
        FileInfo fileInfo = fileInfoService.selectFileInfoById(fileId);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String filePath = sdf.format(fileInfo.getUploadTime());
        String allPath = "D:\\output\\"+filePath+"\\"+fileInfo.getOldFileName();
        DownloadUtil.downloadFile(allPath,fileInfo.getOldFileName(),response,request);
        return null;
    }
}
