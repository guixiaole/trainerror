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
        首页目前为最新的20个
         */
//        Date date = TimeCal.backTime(5);
        List<FileInfo> fileInfos = fileInfoService.selectIndexFileInfoByIndex();
        model.addAttribute("fileInfos",fileInfos);
        /*
        第一个五步闸
         */
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

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
