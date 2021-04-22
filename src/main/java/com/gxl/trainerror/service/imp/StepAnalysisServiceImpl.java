package com.gxl.trainerror.service.imp;

import com.gxl.trainerror.bean.StepAnalysis;
import com.gxl.trainerror.mapper.StepAnalysisMapper;
import com.gxl.trainerror.service.StepAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StepAnalysisServiceImpl implements StepAnalysisService {
    @Autowired
    private StepAnalysisMapper stepAnalysisMapper;
    @Override
    public StepAnalysis selectByFileID(Integer filedId) {
        return stepAnalysisMapper.selectByFileId(filedId);
    }

    @Override
    public Integer updateAnyStep(StepAnalysis stepAnalysis) {
        return stepAnalysisMapper.updateAnyStep(stepAnalysis);
    }

    @Override
    public Integer insertOnlyFileID(StepAnalysis stepAnalysis) {
        return stepAnalysisMapper.insertOnlyFileID(stepAnalysis);
    }

    @Override
    public StepAnalysis selectStepInfoByFileId(Integer fileId) {
        return  stepAnalysisMapper.selectStepByFileId(fileId);
    }
}
