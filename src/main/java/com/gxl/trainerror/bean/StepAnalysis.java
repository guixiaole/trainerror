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
    private Integer id;
    private Integer fileId;
    private String JiXing;
    private Integer oneStep;
    private Integer twoStep;
    private Integer threeStep;
    private Integer fourStep;
    private Integer fiveStep;
    public StepAnalysis(Integer fileId){
        this.fileId = fileId;
    }
}