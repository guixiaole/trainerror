package com.gxl.trainerror.service.imp;

import com.gxl.trainerror.bean.ZhuanDian;
import com.gxl.trainerror.mapper.ZhuanDianMapper;
import com.gxl.trainerror.service.ZhuanDianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ZhuanDianServiceImpl implements ZhuanDianService {
    @Autowired
    private ZhuanDianMapper zhuanDianMapper;
    @Override
    public Integer insertZhuanDian(ZhuanDian zhuanDian) {
        return zhuanDianMapper.insertZhuanDian(zhuanDian);
    }
}
