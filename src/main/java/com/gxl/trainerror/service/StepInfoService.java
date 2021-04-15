package com.gxl.trainerror.service;

import com.gxl.trainerror.bean.StepInfo;

public interface StepInfoService {
    public Integer insertStartEnd(StepInfo stepInfo);
    public StepInfo selectById(Integer id);
    public  Integer updateXiangDianId(StepInfo stepInfo);
}
