package com.gxl.trainerror.controller;

import com.gxl.trainerror.bean.ExcelFile;
import com.gxl.trainerror.bean.FileInfo;
import com.gxl.trainerror.bean.User;
import com.gxl.trainerror.service.FileInfoService;
import com.gxl.trainerror.service.UserService;
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
import java.util.Date;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private FileInfoService fileInfoService;

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
        return "login";
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
                return "login";
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
        return"index";
    }
}
