package com.gxl.trainerror.service;

import com.gxl.trainerror.bean.JiCheInfo;

import java.util.List;

public interface JiCheInfoService {
    public Integer insertJiChe(JiCheInfo jiCheInfo);
    public List<JiCheInfo> selectAll();
    public Integer deleteById(Integer id);
    public Integer updateStepShunXuById(Integer id,Integer stepShunXu);
    public Integer updateEventChangeId(Integer id,Integer eventChange);
    public JiCheInfo selectByJiXingHaoJiCheHao(String  jiXingHao,String jiChe);
    public JiCheInfo selectByJiXingJiChe(String  jiXing,String jiChe);
}
