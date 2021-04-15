package com.gxl.trainerror.service;

import com.gxl.trainerror.bean.StepAnalysis;

public interface StepAnalysisService {
    public StepAnalysis selectByFileID(Integer filedId);
    //插入任何一个步骤
    public Integer updateAnyStep(StepAnalysis stepAnalysis);
}
