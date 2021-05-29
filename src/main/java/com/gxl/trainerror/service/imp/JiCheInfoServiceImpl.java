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
}
