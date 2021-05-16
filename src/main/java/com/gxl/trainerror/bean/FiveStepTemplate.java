package com.gxl.trainerror.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FiveStepTemplate {
    /*
    意为五步闸分析的模板化
     */

    //起始的压力数值
    private Integer startStress;
    private Integer endStress;
    //意为这一块的起始和结束。
    private Integer start;
    private Integer end;

    private Date startTime;
    private Date endTime;
    //意为上一模板和下一模板是高还是低，相对于自身来说。
    // 其中1为高，-1为低

    private Integer nextStart;
    //0为这段稳定，1为这段上升，-1为这段下降。
    private Integer isStable;
    //使用链表进行构成
    private FiveStepTemplate next;
    //持续的时间
    private Integer continueTime;
}
