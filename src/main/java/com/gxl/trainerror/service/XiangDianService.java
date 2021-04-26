package com.gxl.trainerror.service;

import com.gxl.trainerror.bean.XiangDian;

public interface XiangDianService {
    public Integer insertXiangDian(XiangDian xiangDian);
    public XiangDian selectById(Integer id);
}
