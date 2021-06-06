package com.gxl.trainerror.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StepSelect {
    /*
      模板选择
     */
    //模板选择的id
    private Integer id;
    //模板id
    private Integer templateId;
    //压力名字
    private String stressName;
    //高低范围
    private Integer maxStress;
    private Integer minStress;
    //长短范围
    private Integer maxTime;
    private Integer minTime;
    //压力排序
    private String guanSort;
    //是否结束
    private Integer isEnd;
    //以什么样的状态进入与出去
    //1是高。-1是低
    private Integer stateEnter;
    private Integer stateOut;
    //该线段处于什么状态
    private Integer isStable;
    //关于在同类中的优先权
    private Integer priorNumber;
    //是否依赖其它压力 都默认为-1
    private Integer isDepend;
    private Integer startId;
    private Integer endId;
    //设置各管的优先序列
    private Integer sortNumber;
}
