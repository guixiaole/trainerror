package com.gxl.trainerror.controller;

import com.gxl.trainerror.bean.FileInfo;
import com.gxl.trainerror.bean.QuanCheng;
import com.gxl.trainerror.service.FileInfoService;
import com.gxl.trainerror.service.QuanChengService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
public class QuanChengController {
    @Autowired
    private QuanChengService quanChengService;
    @Autowired
    private FileInfoService fileInfoService;
    @RequestMapping("/quancheng")
    public String getQuanCheng(Integer id, Model model){
        List<QuanCheng> quanChengs = quanChengService.selectByFileAscXuhao(id);
//        System.out.println(quanChengs.size());
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        model.addAttribute("quanChengs",quanChengs);
        if (quanChengs.size()>0){
            Integer fileid = quanChengs.get(0).getFileId();
            model.addAttribute("fileid",fileid);
            FileInfo fileInfo=fileInfoService.selectFileInfoById(id);
            model.addAttribute("fileInfo",fileInfo);
        }
        List<String> Times = new ArrayList<>();
        for (QuanCheng quanCheng : quanChengs) {
           Times.add(sdf.format(quanCheng.getDateTime()));
        }
        model.addAttribute("times",Times);

        return "allrecord";
    }
}
