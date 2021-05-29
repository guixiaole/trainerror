package com.gxl.trainerror.mapper;

import com.gxl.trainerror.bean.ZhuanDian;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ZhuanDianMapper {
    public Integer insertZhuanDian(ZhuanDian zhuanDian);
}
