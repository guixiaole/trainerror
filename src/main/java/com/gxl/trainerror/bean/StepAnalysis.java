package com.gxl.trainerror.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("step_analysis")
public class StepAnalysis {
    /*
    五步闸分析类。
     */
    //五步闸单端
    private Integer id;
    private Integer fileId;
    private String jiXing;
    private Integer oneStep;
    private Integer twoStep;
    private Integer threeStep;
    private Integer fourStep;
    private Integer fiveStep;
    private Integer sixStep;
    private Integer sevenStep;
    private Integer eightStep;
    private Integer nineStep;

    private StepInfo one;
    private StepInfo two;
    private StepInfo three;
    private StepInfo four;
    private StepInfo five;
    private StepInfo six;
    private StepInfo seven;
    private StepInfo eight;
    private StepInfo nine;
    //五步闸双端检测
    private Integer shuangOneStep;
    private Integer shuangTwoStep;
    private Integer shuangThreeStep;
    private Integer shuangFourStep;
    private Integer shuangFiveStep;
    private Integer shuangSixStep;
    private Integer shuangSevenStep;
    private Integer shuangEightStep;
    private Integer shuangNineStep;

    private StepInfo shuangOne;
    private StepInfo shuangTwo;
    private StepInfo shuangThree;
    private StepInfo shuangFour;
    private StepInfo shuangFive;
    private StepInfo shuangSix;
    private StepInfo shuangSeven;
    private StepInfo shuangEight;
    private StepInfo shuangNine;
    public StepAnalysis(Integer fileId){
        this.fileId = fileId;
    }
}
