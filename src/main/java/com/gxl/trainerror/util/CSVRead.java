package com.gxl.trainerror.util;

import com.gxl.trainerror.bean.QuanCheng;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ooxml.POIXMLDocument;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.FileMagic;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CSVRead {
    /*
        CSVread
     */
    public static List<QuanCheng> CscReader(String realPath, Integer fileId) throws IOException, ParseException {
        List<QuanCheng> resQuanCheng = new ArrayList<>();
        File fileDes = new File(realPath);
        InputStream str = new FileInputStream(fileDes);
//        Workbook book = new HSSFWorkbook(str);
        Workbook book = null;
        try {
            book = new XSSFWorkbook(fileDes);
        } catch (Exception ex) {
            try {
                book = new HSSFWorkbook(new FileInputStream(fileDes));
            }catch (Exception ex1) {
                book = WorkbookFactory.create(str);
            }
        }

//        XSSFWorkbook book = new XSSFWorkbook(str);
        Sheet sheet = book.getSheetAt(0);
        List<List<String>> res = new ArrayList<>();
        int rows = sheet.getLastRowNum();//总行数
        int i = 1;
        if (String.valueOf(sheet.getRow(0).getCell(0)).equals("1")) {
            i = 0;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
        for (; i < rows; i++) {
            QuanCheng quanCheng = new QuanCheng();
            String temp = String.valueOf(sheet.getRow(i).getCell(0));
            System.out.println(temp);
            quanCheng.setXuHao((int) Double.parseDouble((String.valueOf(sheet.getRow(i).getCell(0)))));
            quanCheng.setEvent(String.valueOf(sheet.getRow(i).getCell(1)));
            String temp1 = String.valueOf(sheet.getRow(i).getCell(2));
            Date date;
            try {
                date = sdf.parse(temp1);
            } catch (Exception e) {
                try{
                    date = sdf1.parse(temp1);
                }catch (Exception e1){
                    date = sheet.getRow(i).getCell(2).getDateCellValue();
                }


            }
            System.out.println(temp1);
            System.out.println(date);
            quanCheng.setDateTime(date);
            quanCheng.setGongLiBiao(String.valueOf(sheet.getRow(i).getCell(3)));
            quanCheng.setOther(String.valueOf(sheet.getRow(i).getCell(4)));
            if (String.valueOf(sheet.getRow(i).getCell(5)) != null
                    && !String.valueOf(sheet.getRow(i).getCell(5)).equals("")
                    && !String.valueOf(sheet.getRow(i).getCell(5)).equals("null")) {
                String temp2 = String.valueOf(sheet.getRow(i).getCell(5));
                quanCheng.setDistance((int) Double.parseDouble(String.valueOf(sheet.getRow(i).getCell(5))));

            }
            quanCheng.setSignalMachine(String.valueOf(sheet.getRow(i).getCell(6)));
            quanCheng.setXinHao(String.valueOf(sheet.getRow(i).getCell(7)));
            if (String.valueOf(sheet.getRow(i).getCell(8)) != null
                    && !String.valueOf(sheet.getRow(i).getCell(8)).equals("")
                    && !String.valueOf(sheet.getRow(i).getCell(8)).equals("null")) {
                quanCheng.setSpeed((int) Double.parseDouble(String.valueOf(sheet.getRow(i).getCell(8))));
            }

            if (String.valueOf(sheet.getRow(i).getCell(9)) != null
                    && !String.valueOf(sheet.getRow(i).getCell(9)).equals("")
                    && !String.valueOf(sheet.getRow(i).getCell(9)).equals("null")) {
                quanCheng.setRestrictSpeed((int) Double.parseDouble(String.valueOf(sheet.getRow(i).getCell(9))));
            }

            quanCheng.setLingWei(String.valueOf(sheet.getRow(i).getCell(10)));
            quanCheng.setQianYin(String.valueOf(sheet.getRow(i).getCell(11)));
            quanCheng.setQianHou(String.valueOf(sheet.getRow(i).getCell(12)));
            if (String.valueOf(sheet.getRow(i).getCell(13)) != null
                    && !String.valueOf(sheet.getRow(i).getCell(13)).equals("")
                    && !String.valueOf(sheet.getRow(i).getCell(13)).equals("null")) {
                quanCheng.setGuanYa((int) Double.parseDouble(String.valueOf(sheet.getRow(i).getCell(13))));
            }

            if (String.valueOf(sheet.getRow(i).getCell(14)) != null
                    && !String.valueOf(sheet.getRow(i).getCell(14)).equals("")
                    && !String.valueOf(sheet.getRow(i).getCell(14)).equals("null")) {
                quanCheng.setGangYa((int) Double.parseDouble(String.valueOf(sheet.getRow(i).getCell(14))));

            }
            if (String.valueOf(sheet.getRow(i).getCell(15)) != null
                    && !String.valueOf(sheet.getRow(i).getCell(15)).equals("")
                    && !String.valueOf(sheet.getRow(i).getCell(15)).equals("null")) {
                quanCheng.setZhuanSuDianLiu((int) Double.parseDouble(String.valueOf(sheet.getRow(i).getCell(15))));
            }

            if (String.valueOf(sheet.getRow(i).getCell(16)) != null
                    && !String.valueOf(sheet.getRow(i).getCell(16)).equals("")
                    && !String.valueOf(sheet.getRow(i).getCell(16)).equals("null")) {
                quanCheng.setJunGang1((int) Double.parseDouble(String.valueOf(sheet.getRow(i).getCell(16))));
            }

            if (String.valueOf(sheet.getRow(i).getCell(17)) != null
                    && !String.valueOf(sheet.getRow(i).getCell(17)).equals("")
                    && !String.valueOf(sheet.getRow(i).getCell(17)).equals("null")) {
                quanCheng.setJunGang2((int) Double.parseDouble(String.valueOf(sheet.getRow(i).getCell(17))));
            }

            quanCheng.setFileId(fileId);
            resQuanCheng.add(quanCheng);
        }
        return resQuanCheng;
    }

}

