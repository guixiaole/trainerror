package com.gxl.trainerror.controller;

import com.gxl.trainerror.bean.*;
import com.gxl.trainerror.service.*;
import com.gxl.trainerror.util.FindError;
import com.gxl.trainerror.util.TimeCal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private FileInfoService fileInfoService;
    @Autowired
    private StepInfoService stepInfoService;
    @Autowired
    private StepAnalysisService stepAnalysisService;
    @Autowired
    private XiangDianService xiangDianService;

//    @RequestMapping(value = {"/","/index"})
//    public String index(HttpServletRequest request) throws IOException, ParseException {
//        String XlsPath = "G:\\2021JAVA\\1.xls";
//        FindError findError=new FindError();
////        List<List<String>> file = findError.write_excel(XlsPath);
//        List<ExcelFile> excelFiles =findError.writeExeclFile(XlsPath);
//
//        request.setAttribute("excelFiles",excelFiles);
////        for (ExcelFile excelFile : excelFiles) {
////            System.out.println(excelFile);
////        }
//        System.out.println(excelFiles.size());
////        for (List<String> strings : file) {
////            System.out.println(strings);
////        }
//        return "index1";
//    }
    @RequestMapping(value={"/","login"})
    public String index(){
        return "login1";
    }
    @RequestMapping("/loginUser")
    public String loginUser(User user,
                            Model model,
                            HttpSession session){
            User user1 = userService.loginUser(user);
            if(user1!=null){
                session.setAttribute("user",user1);
                return "redirect:/index.html";
            }
            else {
                model.addAttribute("msg","账号密码错误");
                return "login1";
            }
    }
    @RequestMapping(value = {"index.html","index"})
    public String indexFile(Model model){
        /*
        还需要设置一个时间
         */
        Date date = TimeCal.backTime(5);
        List<FileInfo> fileInfos =  fileInfoService.selectAllFileInfo(date);
        model.addAttribute("fileInfos",fileInfos);
        /*
        第一个五步闸
         */
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        if (fileInfos.size()>0){
            FileInfo fileInfo = fileInfos.get(0);
            StepAnalysis stepAnalysis= stepAnalysisService.selectByFileID(fileInfo.getId());
            model.addAttribute("stepAnalysis",stepAnalysis);
            model.addAttribute("fileinfo0",fileInfos.get(0));
        }
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
        if (fileInfos.size()>0){
            //在size大于0的时候，根据五步闸来
            FileInfo fileInfo = fileInfos.get(0);
            Integer id = fileInfo.getId();
            StepAnalysis stepAnalysis= stepAnalysisService.selectByFileID(id);
            //没有用mybatis的缓存
            if (stepAnalysis.getOneStep()!=null){
                StepInfo one = stepInfoService.selectById(stepAnalysis.getOneStep());
                if(one.getXiangDianId()!=null){
                    XiangDian oneXiangDian = xiangDianService.selectById(one.getXiangDianId());
                    model.addAttribute("oneXiangDian",oneXiangDian);
                }
                model.addAttribute("one",one);
            }
            if (stepAnalysis.getTwoStep()!=null){
                StepInfo two = stepInfoService.selectById(stepAnalysis.getTwoStep());
                model.addAttribute("two",two);
                if(two.getXiangDianId()!=null){
                    XiangDian twoXiangDian = xiangDianService.selectById(two.getXiangDianId());
                    model.addAttribute("twoXiangDian",twoXiangDian);
                }
            }
            if (stepAnalysis.getThreeStep()!=null){
                StepInfo three = stepInfoService.selectById(stepAnalysis.getThreeStep());
                model.addAttribute("three",three);
                if(three.getXiangDianId()!=null){
                    XiangDian oneXiangDian = xiangDianService.selectById(three.getXiangDianId());
                    model.addAttribute("threeXiangDian",oneXiangDian);
                }
            }
            if (stepAnalysis.getFourStep()!=null){
                StepInfo four = stepInfoService.selectById(stepAnalysis.getFourStep());
                model.addAttribute("four",four);
                if(four.getXiangDianId()!=null){
                    XiangDian oneXiangDian = xiangDianService.selectById(four.getXiangDianId());
                    model.addAttribute("fourXiangDian",oneXiangDian);
                }
            }
            if (stepAnalysis.getFiveStep()!=null){
                StepInfo five = stepInfoService.selectById(stepAnalysis.getFiveStep());
                model.addAttribute("five",five);
                if(five.getXiangDianId()!=null){
                    XiangDian oneXiangDian = xiangDianService.selectById(five.getXiangDianId());
                    model.addAttribute("fiveXiangDian",oneXiangDian);
                }
            }
        }
        return"index";
    }
}
