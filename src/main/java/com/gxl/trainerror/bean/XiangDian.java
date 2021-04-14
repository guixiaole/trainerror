package com.gxl.trainerror.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("xiang_dian")
public class XiangDian {
    private Integer id;
    private String jiXing;
    private Integer step;
    private String info;
}
