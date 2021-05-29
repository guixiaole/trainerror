package com.gxl.trainerror.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventChange {
    /*
        当改变某个相对应的列的时候，而进行相对应的改变。
     */
    private Integer id;
    private String name;
    private Integer event;
    private Integer dateTime;
    private Integer gongLiBiao;
    private Integer other;
    private Integer distance;
    private Integer signalMachine;
    private Integer xinHao;
    private Integer speed;
    private Integer restrictSpeed;
    private Integer lingWei;
    private Integer qianYin;
    private Integer qianHou;
    private Integer guanYa;
    private Integer gangYa;
    private Integer zhuanSuDianLiu;
    private Integer junGang1;
    private Integer junGang2;
}
