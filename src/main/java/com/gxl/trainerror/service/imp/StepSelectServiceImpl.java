package com.gxl.trainerror.service.imp;

import com.gxl.trainerror.bean.StepSelect;
import com.gxl.trainerror.mapper.StepSelectMapper;
import com.gxl.trainerror.service.StepSelectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class StepSelectServiceImpl implements StepSelectService {
    @Autowired
    private StepSelectMapper stepSelectMapper;
    @Override
    public List<StepSelect> selectByIdAndName(Integer templateId,String stressName) {
        return stepSelectMapper.selectByTemplateIdAndName(templateId,stressName);
    }

    @Override
    public StepSelect selectIdPriorName(Integer templateId, String stressName, Integer prior) {
        return stepSelectMapper.selectByIdPriorName(templateId,stressName,prior);
    }

    @Override
    public Integer insertStepSelect(StepSelect select) {
        return stepSelectMapper.insertStepSelect(select);
    }

    @Override
    public Integer selectCountPrior(Integer templateId, String stressName) {
        return stepSelectMapper.selectCount(templateId,stressName);
    }
}
