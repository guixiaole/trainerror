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
    private String fileState;//文件状态
    private Integer testScore;//作业分
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date uploadTime;//上传时间
}
