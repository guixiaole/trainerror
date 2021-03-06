package com.gxl.trainerror.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("file_info")
public class FileInfo {
    /*
    isSave 0为存储了，但是没有保存全程记录，1为保存了全程记录

     */
    private Integer id;
    private String fileName;//文件名
    private Integer isSave;
    private String zhuangBeiDian;//装备店
    private Date fileStartTime;//文件起始时间
    private String cheCi;//车次
    private String jiXing;//机型
    private  String cheHao;//车号
    private String siJiName;//司机名
    private  String fuSiJiName;//副司机名
    private Integer fileState;//文件状态 如果是1的话代表分析正确，-1的话代表分析错误，其它为待分析
    private Integer testScore;//作业分
    private Date uploadTime;//上传时间
    private String  oldFileName;
    private String jiCheHao;//机车号
    private String jiaoLuHao;//交路号
    private String jiChang;//计长
    private String startStation;//起始站点
    private StepAnalysis stepAnalysis;
    private String banBen;//版本
}
