package com.gxl.trainerror.bean;

public class OneStepTemplate {
    private Integer id;
    //压力名字
    private String StressName;
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
    private Integer stateEnter;
    private Integer stateOut;
    //该线段处于什么状态
    private Integer isStable;
}
