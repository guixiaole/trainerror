package com.gxl.trainerror.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JiCheInfo {
    private Integer id;
    private String jiXing;
    private String jiXingHao;
    private Integer jiCheHao;
    private Integer DanShuangDuan;
    private Integer otherJiCheHao;
    private String zhiDongJiName;
    private Integer zhiDongJiHao;
    private Double lieZhiRatio;
    private Integer StepCiXuId;
    private Integer eventChangeId;
}
