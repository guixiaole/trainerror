package com.gxl.trainerror.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("step_info")
public class StepInfo {
    /*
     为五步闸具体的信息。
     分别为起始，结束，以及项点的id；
     */
    private Integer id;
    private Integer startXiangDian;
    private Integer endXiangDian;
    //关于标准化后的文件的起始和结束。
    private Integer startPos;
    private Integer endPos;
    private Integer xiangDianId;
    private XiangDian xiangDian;
}
