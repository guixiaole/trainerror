package com.gxl.trainerror.service.imp;

import com.gxl.trainerror.bean.XiangDian;
import com.gxl.trainerror.mapper.XiangDianMapper;
import com.gxl.trainerror.service.XiangDianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class XiangDianServiceImpl implements XiangDianService {
    @Autowired
    private XiangDianMapper xiangDianMapper;
    @Override
    public Integer insertXiangDian(XiangDian xiangDian) {
        return xiangDianMapper.insertXiangDian(xiangDian);
    }
}
