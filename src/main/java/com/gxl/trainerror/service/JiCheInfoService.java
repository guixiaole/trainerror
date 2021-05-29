package com.gxl.trainerror.service;

import com.gxl.trainerror.bean.JiCheInfo;

import java.util.List;

public interface JiCheInfoService {
    public Integer insertJiChe(JiCheInfo jiCheInfo);
    public List<JiCheInfo> selectAll();
}
