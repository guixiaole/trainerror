package com.gxl.trainerror.service.imp;

import com.gxl.trainerror.bean.StepShunXu;
import com.gxl.trainerror.mapper.StepShunXuMapper;
import com.gxl.trainerror.service.StepShunXuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class StepShunXuServiceImpl implements StepShunXuService {
    @Autowired
    private StepShunXuMapper stepShunXuMapper;
    @Override
    public List<StepShunXu> selectAll() {
        return stepShunXuMapper.selectAll();
    }

    @Override
    public StepShunXu selectByid(Integer id) {
        return stepShunXuMapper.selectById(id);
    }

    @Override
    public Integer insertStepShunXu(StepShunXu stepShunXu) {
        return stepShunXuMapper.insertStepShunXu(stepShunXu);
    }
}
