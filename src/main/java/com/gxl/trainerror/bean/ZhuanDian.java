package com.gxl.trainerror.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ZhuanDian {
    private Integer id;
    //开始时间和结束时间
    private Date startTime;
    private Date endTime;
    //最大最小压力值以及相对应的时间
    private Date maxTime;
    private Integer maxStress;

    private Integer minStress;
    private Date minTime;
    //左边与右边的压力值
    private Integer leftStress;
    private Integer rightStress;
    //自己是哪个步骤的。
    private Integer stepSelectId;
    //自己是哪个文件的也要储存啊。
    private Integer fileId;
    //关于是哪个管道的压力
    private String stressName;
    //在格式化之后的全程文件的位子。
    private Integer startPos;
    private Integer endPos;
    //关于优先级，数字越小，优先级越高。
    private Integer priorNumber;
    //关于单双端的优先级，数字越小，优先级越高。
    private Integer duanPrior;
}
