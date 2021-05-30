package com.gxl.trainerror.service.imp;

import com.gxl.trainerror.bean.JiCheInfo;
import com.gxl.trainerror.mapper.JiCheInfoMapper;
import com.gxl.trainerror.service.JiCheInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JiCheInfoServiceImpl implements JiCheInfoService {
    @Autowired
    private JiCheInfoMapper jiCheInfoMapper;
    @Override
    public Integer insertJiChe(JiCheInfo jiCheInfo) {
        return jiCheInfoMapper.insertJiChe(jiCheInfo);
    }

    @Override
    public List<JiCheInfo> selectAll() {
        return jiCheInfoMapper.selectAll();
    }

    @Override
    public Integer deleteById(Integer id) {
        return jiCheInfoMapper.deleteById(id);
    }

    @Override
    public Integer updateStepShunXuById(Integer id, Integer stepShunXu) {
        return jiCheInfoMapper.updateStepShunXuById(id,stepShunXu);
    }

    @Override
    public Integer updateEventChangeId(Integer id, Integer eventChange) {
        return jiCheInfoMapper.updateEventChangeById(id,eventChange);
    }
}
