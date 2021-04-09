package com.gxl.trainerror.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ExplaceSql {
//    public static void main(String[] args) {
//        String filePath = ExplaceSql.class.getResource("").getPath()+"aaa.txt"; // 文件路径
//        read(filePath);
//    }

    /**
     * 读取内容
     */
    public static ArrayList<String>[] read(String filePath){
        BufferedReader br = null;
        String line =null;
        //StringBuffer buf = new StringBuffer();
        ArrayList<String>[]  quancheng = new ArrayList[18];
        for (int i =0;i<quancheng.length;i++) {
                quancheng[i]=new ArrayList();
        }
        // 使用这样进行储存
        try {
            //根据文件路径创建缓冲输入流
            br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath),"GBK"));//filePath中是aaa.txt文件
            String str = "";

            //循环读取文件的每一行，对需要修改的行进行修改，放入缓冲对象中
            while ((line = br.readLine()) != null) {
                //设置正则将多余空格都转为一个空格
                str=line+"\r\n";
//                String[] dictionary =  str.split("\\s{2,}|\t");
                String[] dictionary =  str.split("\t");
                for(int i=0;i<dictionary.length;i++){
//                    str = "insert into tablename values("+ dictionary[i]+",'"+dictionary[1]+"',"+dictionary[2]+"')";
                    if (i== dictionary.length-1){
//                        System.out.println(dictionary[i]);
                       String temp= dictionary[i].substring(0,dictionary[i].length()-2);
//                        System.out.println(temp);
                        quancheng[i].add(temp);
                    }else
                        quancheng[i].add(dictionary[i]);
                }
                if  (dictionary.length<18){
                    //其它的添加空的数组
                    for  (int i = dictionary.length;i<18;i++){
                        quancheng[i].add("");//添加为空
                    }
                }
//                System.out.println("");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (br != null) {// 关闭流
                try {
                    br.close();
                } catch (IOException e) {
                    br = null;
                }
            }
        }
        //获取了全程记录之后，接下来是该怎么做呢？
        //进行数据库存储。

        return quancheng;
    }

}


