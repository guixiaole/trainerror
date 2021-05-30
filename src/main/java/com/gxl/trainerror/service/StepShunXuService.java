package com.gxl.trainerror.service;

import com.gxl.trainerror.bean.StepShunXu;

import java.util.List;

public interface StepShunXuService {
    public List<StepShunXu> selectAll();
    public StepShunXu selectByid(Integer id);
    public Integer insertStepShunXu(StepShunXu stepShunXu);
}
