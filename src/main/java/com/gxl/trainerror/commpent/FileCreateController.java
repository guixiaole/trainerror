package com.gxl.trainerror.commpent;

import com.gxl.trainerror.bean.FileInfo;
import com.gxl.trainerror.service.FileInfoService;
import com.gxl.trainerror.service.QuanChengService;
import com.gxl.trainerror.util.ExplaceSql;
import com.gxl.trainerror.util.FileUtil;
import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
@Component
public class FileCreateController implements FileAlterationListener {
    @Autowired
    private QuanChengService quanChengService;
    @Autowired
    private FileInfoService fileInfoService;
   public static FileCreateController fileCreateController;

    /**
     *通过@PostConstruct实现初始化bean之前进行的操作
     */
    @PostConstruct
    public void init(){
        fileCreateController = this;
        fileCreateController.fileInfoService=this.fileInfoService;
        fileCreateController.quanChengService=this.quanChengService;
    }
    @Override
    public void onStart(FileAlterationObserver fileAlterationObserver) {

    }

    /**
     * 监控目录中创建一个目录时触发
     * @param file
     */
    @Override
    public void onDirectoryCreate(File file) {

    }
    /**
     * 监控目录中目录发生改变触发
     * @param file
     */
    @Override
    public void onDirectoryChange(File file) {

    }
    /**
     * 监控目录中目录发生删除触发
     * @param file
     */
    @Override
    public void onDirectoryDelete(File file) {

    }
    /**
     * 监控目录中创建文件时触发
     * @param file2
     */
    @Override
    public void onFileCreate(File file2) {
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
        File [] files = FileUtil.getCurFilesList(fileOldPath);
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
                    ArrayList<String>[]  lists= ExplaceSql.read(fileOldPath+"\\"+file1.getName());
                    //在这之前还需要进行一个存储，就是把文件存储进去，设计一个独立的表格。
                    if(lists.length==18){

                        //不仅仅要总的行数大于18，也要里面的数大于一定的数才行。
                        if(lists[0].size()>10 && lists[10].size()>10 && lists[17].size()>10){
                            if (fileCreateController.fileInfoService.selectFileInfoByName(file1.getName())==null){
                                FileInfo fileInfo =new FileInfo();
                                fileInfo.setFileName(file1.getName());
                                fileInfo.setIsSave(0);
                                fileInfo.setUploadTime(new Date());
                                Integer id = fileCreateController.fileInfoService.insertFileInfoStart(fileInfo);
                                //进行插入操作
                                if (id>=0){
                                    //全程记录存储完毕之后，
                                    try {
                                        fileCreateController.quanChengService.insertQuanCheng(lists,id);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    fileInfo.setIsSave(1);
                                    fileCreateController.fileInfoService.updateIsSaveFileInfo(fileInfo);
                                    //当主键插入进去的时候，再进行插入。
                                }

                            }
                        }
                    }

                    //插入之后就好了，下次直接可以查找
                    //在这里的时候都是需要存储到Sql中去的
                }
                try {
                    Files.move(Paths.get(fileOldPath+"\\"+file1.getName()),Paths.get(filePath+"\\"+file1.getName()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @Override
    public void onFileChange(File file) {

    }

    @Override
    public void onFileDelete(File file) {

    }

    @Override
    public void onStop(FileAlterationObserver fileAlterationObserver) {

    }
}
