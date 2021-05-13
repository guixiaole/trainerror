package com.gxl.trainerror.service;

import com.gxl.trainerror.bean.StepSelect;

import java.util.List;

public interface StepSelectService {
    public List<StepSelect> selectByIdAndName(Integer templateId,String  stressName);
    public StepSelect selectIdPriorName(Integer templateId,String stressName,Integer prior);
    public Integer insertStepSelect(StepSelect select);
    public Integer selectCountPrior(Integer templateId,String stressName);
}
