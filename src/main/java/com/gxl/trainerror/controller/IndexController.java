package com.gxl.trainerror.controller;

import com.gxl.trainerror.bean.FileInfo;
import com.gxl.trainerror.bean.StepAnalysis;
import com.gxl.trainerror.bean.StepInfo;
import com.gxl.trainerror.bean.XiangDian;
import com.gxl.trainerror.service.FileInfoService;
import com.gxl.trainerror.service.StepAnalysisService;
import com.gxl.trainerror.service.StepInfoService;
import com.gxl.trainerror.service.XiangDianService;
import com.gxl.trainerror.util.DownloadUtil;
import com.gxl.trainerror.util.TimeCal;
import com.mysql.cj.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
                                Model model,HttpSession session){
        /*
           显示时间：有一个日期，显示多久以前的文件可以显示。
           （一个小时，5个小时，12个小时，24个小时，3天，7天，
             一个月，全部。默认显示5个小时）
         */
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = null;
        session.setAttribute("index",time);
        if (time ==1||time==12||time==24){
             date = TimeCal.backTime(time);
        }else {
             date =TimeCal.backDate(time);
        }
        List<FileInfo> fileInfos = fileInfoService.selectAllFileInfo(date);
        model.addAttribute("fileInfos",fileInfos);

        List<String> startTime = new ArrayList<>();
        List<String> uploadTime = new ArrayList<>();
        for (FileInfo fileInfo : fileInfos) {
            if (fileInfo.getFileStartTime()!=null){
                startTime.add(sdf.format(fileInfo.getFileStartTime()));
            }else
                startTime.add(null);
            if (fileInfo.getUploadTime()!=null){
                uploadTime.add(sdf.format(fileInfo.getUploadTime()));
            }else
                uploadTime.add(null);
        }
        model.addAttribute("startTime",startTime);
        model.addAttribute("uploadTime",uploadTime);

        return"index";
    }
    @PostMapping("/searchFile")
    public String searchFile(FileInfo fileInfo1,Model model){
        /*
            根绝文件搜索查找fileinfos
         */
        System.out.println(fileInfo1.getSiJiName());
        if (fileInfo1.getSiJiName().equals(",")){
            fileInfo1.setSiJiName("");
        }
        if(!fileInfo1.getFuSiJiName().equals("")){
            fileInfo1.setSiJiName(fileInfo1.getFuSiJiName());
            fileInfo1.setFuSiJiName("");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        List<FileInfo> fileInfos = fileInfoService.selectIndexFileInfo(fileInfo1);
        model.addAttribute("fileInfos",fileInfos);

        List<String> startTime = new ArrayList<>();
        List<String> uploadTime = new ArrayList<>();
        for (FileInfo fileInfo : fileInfos) {
            if (fileInfo.getFileStartTime()!=null){
                startTime.add(sdf.format(fileInfo.getFileStartTime()));
            }else
                startTime.add(null);
            if (fileInfo.getUploadTime()!=null){
                uploadTime.add(sdf.format(fileInfo.getUploadTime()));
            }else
                uploadTime.add(null);

        }
        model.addAttribute("startTime",startTime);
        model.addAttribute("uploadTime",uploadTime);

        return "index";
    }
    @RequestMapping("oldNameDownload")
    public String oldNameDownload(@RequestParam("filename") String oldName
            , @RequestParam("id") Integer fileId, HttpServletRequest request, HttpServletResponse response){
        FileInfo fileInfo = fileInfoService.selectFileInfoById(fileId);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String filePath = sdf.format(fileInfo.getUploadTime());
        String allPath = "D:\\output\\"+filePath+"\\"+oldName;
        DownloadUtil.downloadFile(allPath,oldName,response,request);
        return null;
    }
    @ResponseBody
    @RequestMapping("guideAndTishi")
    public StepAnalysis guideAndTishi(@RequestParam("id")Integer id,Model model){
        StepAnalysis stepAnalysis = stepAnalysisService.selectStepInfoByFileId(id);
//        model.addAttribute("stepAnalysis1",stepAnalysis);


        return stepAnalysis;
    }
    @RequestMapping("returnIndex")
    public String returnIndex(HttpSession session,Model model){
       Integer indexid = (Integer) session.getAttribute("index");
        if (indexid==0){
            return "redirect:/index.html";
        }else {
            Integer time = indexid;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date date = null;
            if (time ==1||time==12||time==24){
                date = TimeCal.backTime(time);
            }else {
                date =TimeCal.backDate(time);
            }
            List<FileInfo> fileInfos = fileInfoService.selectAllFileInfo(date);
            model.addAttribute("fileInfos",fileInfos);

            List<String> startTime = new ArrayList<>();
            List<String> uploadTime = new ArrayList<>();
            for (FileInfo fileInfo : fileInfos) {
                if (fileInfo.getFileStartTime()!=null){
                    startTime.add(sdf.format(fileInfo.getFileStartTime()));
                }else
                    startTime.add(null);
                if (fileInfo.getUploadTime()!=null){
                    uploadTime.add(sdf.format(fileInfo.getUploadTime()));
                }else
                    uploadTime.add(null);
            }
            model.addAttribute("startTime",startTime);
            model.addAttribute("uploadTime",uploadTime);

            return"index";
        }
    }
}
