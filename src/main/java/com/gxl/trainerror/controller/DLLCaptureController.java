package com.gxl.trainerror.controller;

import com.gxl.trainerror.bean.FileInfo;
import com.gxl.trainerror.bean.QuanCheng;
import com.gxl.trainerror.service.FileInfoService;
import com.gxl.trainerror.service.QuanChengService;
import com.gxl.trainerror.util.ExplaceSql;
import org.apache.catalina.User;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Configuration
@EnableScheduling
public class DLLCaptureController {
    @Autowired
    private QuanChengService quanChengService;
    @Autowired
    private FileInfoService fileInfoService;
    /*
    读取该文件下的所有文件，并且获得txt文件，然后将其移动到该天下面的文件夹下面去
     */
    public static File[] getCurFilesList(String filePath) {
        File path = new File(filePath);
        File[] listFiles = path.listFiles(new java.io.FileFilter() {

            @Override
            public boolean accept(File pathname) {
                if (pathname.isFile())
                    return true;
                else
                    return false;
            }
        });

        return listFiles;
    }
    //判断是不是txt文件
    public static boolean  isTxt(String fileName){
        String txtName = ".txt";
        int fileFlag = fileName.length();
        int minus = 4;
        int txtflag  = 0;
        while (txtflag<txtName.length()){
            if (txtName.charAt(txtflag)!=fileName.charAt(fileFlag-minus)){
                    return false;

            }
            txtflag++;
            minus--;
        }
        return true;
    }
    //调用c++动态库读取
    //
    @Scheduled(cron = "0/10 * * * * ?")
        public void readDLL() throws IOException, ParseException {
        String fileName="D:/Release/VSread.exe";
        Runtime rt = Runtime.getRuntime();
        String exePath =  fileName;
        try {
            rt.exec(exePath);
            System.out.println("do-------------");
        } catch (IOException e) {
        // TODO Auto-generated catch block
            e.printStackTrace();
        }
        /*
           解析该文件之后，读取该文件的txt
         */


        String fileOldPath = "D:\\output";
        File [] files =DLLCaptureController.getCurFilesList(fileOldPath);
        if (files.length>0){ //当大于0 的时候,才创建
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String filePath = "D:\\output\\"+sdf.format(date);
            File file = new File(filePath);
            if(!file.exists()){
                file.mkdir();
//
//            System.out.println("文件创建成功");
            }
            for (File file1 : files) {


                if (file1.getName().contains(".txt")){
//                System.out.println("this file is txt:"+file1.getName());
                    //读取txt文件
                    ArrayList<String>[]  lists=ExplaceSql.read(fileOldPath+"\\"+file1.getName());
                    //在这之前还需要进行一个存储，就是把文件存储进去，设计一个独立的表格。
                            if(lists.length==18){
                                //不仅仅要总的行数大于18，也要里面的数大于一定的数才行。
                                if(lists[0].size()>10 && lists[10].size()>10 && lists[17].size()>10){
                                    if (fileInfoService.selectFileInfoByName(file1.getName())==null){
                                        FileInfo fileInfo =new FileInfo();
                                        fileInfo.setFileName(file1.getName());
                                        fileInfo.setIsSave(0);
                                        fileInfo.setUploadTime(new Date());
                                        Integer id = fileInfoService.insertFileInfoStart(fileInfo);
                                        //进行插入操作
                                        if (id>=0)
                                            //当主键插入进去的时候，再进行插入。
                                            quanChengService.insertQuanCheng(lists,id);
                                    }
                                }
                            }

                            //插入之后就好了，下次直接可以查找
                    //在这里的时候都是需要存储到Sql中去的
                }
                Files.move(Paths.get(fileOldPath+"\\"+file1.getName()),Paths.get(filePath+"\\"+file1.getName()));
            }
        }
    }
}
