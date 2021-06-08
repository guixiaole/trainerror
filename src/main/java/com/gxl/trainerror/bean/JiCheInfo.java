package com.gxl.trainerror.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JiCheInfo {
    //返回的是哪个id;
    private Integer id;
    private String jiXing;
    private Integer jiXingHao;
    private Integer jiCheHao;
    //单双端还是要看1还是2
    private Integer danShuangDuan;
    private Integer otherJiCheHao;
    private String zhiDongJiName;
    private Integer zhiDongJiHao;
    private Double lieZhiRatio;
    private Integer stepShunXuId;
    private Integer eventChangeId;
    private StepShunXu stepShunXu;
    private EventChange eventChange;
    private String isHeGe;
}
