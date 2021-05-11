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
    private String templateId;
    //持续的时间
    private Integer continueTime;
    //开始的压力
    private Integer startNumber;
    //结束的压力
    private Integer endNumber;
    //优先权的处理
    private Integer prior;
    //需要的压力名字
    private String stressName;
}
