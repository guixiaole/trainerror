package com.gxl.trainerror.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StepShunXu {
    /*
        该类为五步闸顺序类。其中连接的应该是关于AllTemplate中的模板
     */
    private Integer id;
    private String name;
    private Integer oneStep;
    private Integer twoStep;
    private Integer threeStep;
    private Integer fourStep;
    private Integer fiveStep;
    private Integer sixStep;
    private Integer sevenStep;
    private Integer eightStep;
    private Integer nineStep;
    private AllTemplate one;
    private AllTemplate two;
    private AllTemplate three;
    private AllTemplate four;
    private AllTemplate five;
    private AllTemplate six;
    private AllTemplate seven;
    private AllTemplate eight;
    private AllTemplate nine;

}
