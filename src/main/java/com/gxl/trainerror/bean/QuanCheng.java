package com.gxl.trainerror.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("quan_cheng")
public class QuanCheng {
        private Integer id;
        private Integer fileId;
        private Integer xuHao;
        private String event;
        private Date dateTime;
        private String gongLiBiao;
        private String  other;
        private  Integer distance;
        private  String signalMachine;
        private  String xinHao;
        private Integer speed;
        private Integer restrictSpeed;
        private String lingWei;
        private String  qianYin;
        private String  qianHou;
        private Integer guanYa;
        private Integer gangYa;
        private Integer zhuanSuDianLiu;
        private Integer junGang1;
        private Integer junGang2;

        public QuanCheng(Integer fileId, Integer xuHao, String event, Date dateTime, String gongLiBiao, String other, Integer distance, String signalMachine, String xinHao, Integer speed, Integer restrictSpeed, String lingWei, String qianYin, String qianHou, Integer guanYa, Integer gangYa, Integer zhuanSuDianLiu, Integer junGang1, Integer junGang2) {
                this.fileId = fileId;
                this.xuHao = xuHao;
                this.event = event;
                this.dateTime = dateTime;
                this.gongLiBiao = gongLiBiao;
                this.other = other;
                this.distance = distance;
                this.signalMachine = signalMachine;
                this.xinHao = xinHao;
                this.speed = speed;
                this.restrictSpeed = restrictSpeed;
                this.lingWei = lingWei;
                this.qianYin = qianYin;
                this.qianHou = qianHou;
                this.guanYa = guanYa;
                this.gangYa = gangYa;
                this.zhuanSuDianLiu = zhuanSuDianLiu;
                this.junGang1 = junGang1;
                this.junGang2 = junGang2;
        }

       public QuanCheng(Date dateTime,int guanYa,int  gangYa,int junGang1,int junGang2,int xuHao){
                this.gangYa = gangYa;
                this.junGang1 = junGang1;
                this.junGang2 =junGang2;
                this.guanYa = guanYa;
                this.dateTime = dateTime;
                this.xuHao = xuHao;
       }
}
