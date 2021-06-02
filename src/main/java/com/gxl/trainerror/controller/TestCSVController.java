package com.gxl.trainerror.controller;

import com.gxl.trainerror.bean.FileInfo;
import com.gxl.trainerror.bean.QuanCheng;
import com.gxl.trainerror.bean.StepAnalysis;
import com.gxl.trainerror.service.FileInfoService;
import com.gxl.trainerror.service.QuanChengService;
import com.gxl.trainerror.service.StepAnalysisService;
import com.gxl.trainerror.util.CSVRead;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
public class TestCSVController {
    @Autowired
    private FileInfoService fileInfoService;
    @Autowired
    private QuanChengService quanChengService;
    @Autowired
    private StepAnalysisService stepAnalysisService;
    public void testCsvStepAnalysis() throws IOException {
        /*
            此方法只是为了读取excel文件，然后进行存储。
            然后进行读取文件。
         */
        String filePath = "D:\\output\\";
        File[] files = DLLCaptureController.getCurFilesList(filePath);
        if(files.length>0){
            for (File file1 : files) {

                if(file1.getName().contains(".xls")){
                    String filiReadName = filePath+file1.getName();
                    FileInfo fileInfo = new FileInfo();
                    fileInfo.setOldFileName(file1.getName());
                    fileInfo.setIsSave(0);
                    fileInfo.setUploadTime(new Date());
                    fileInfoService.insertFileInfoStart(fileInfo);
                    int id = fileInfo.getId();
                    if(id>=0){
                        List<QuanCheng> quanChengs = CSVRead.CscReader(filiReadName,id);
                        quanChengService.insertQuanChengByList(quanChengs);
                        StepAnalysis stepAnalysis = stepAnalysisService.selectStepInfoByFileId(id);
                        FileInfo newFileInfo = fileInfoService.selectFileInfoById(id);
                    }
                }
            }
        }

    }
}
