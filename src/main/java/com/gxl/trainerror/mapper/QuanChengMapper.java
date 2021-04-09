package com.gxl.trainerror.mapper;

import com.gxl.trainerror.bean.QuanCheng;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QuanChengMapper  {
    public Integer insertQuanCheng(QuanCheng quanCheng);
}
