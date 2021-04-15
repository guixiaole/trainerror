package com.gxl.trainerror.service.imp;

import com.gxl.trainerror.bean.StepInfo;
import com.gxl.trainerror.mapper.StepInfoMapper;
import com.gxl.trainerror.service.StepInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StepInfoServiceImpl implements StepInfoService {
    @Autowired
    private StepInfoMapper stepInfoMapper;
    @Override
    public Integer insertStartEnd(StepInfo stepInfo) {
        return stepInfoMapper.insertStepInfo(stepInfo);
    }

    @Override
    public StepInfo selectById(Integer id) {
        return stepInfoMapper.selectById(id);
    }

    @Override
    public Integer updateXiangDianId(StepInfo stepInfo) {
        return stepInfoMapper.updateXiangDian(stepInfo);
    }
}
