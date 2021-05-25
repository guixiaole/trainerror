package com.gxl.trainerror.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllTemplate {
    private Integer id;
    private String templateName;
    //压力排序
    private String guanSort;
}
