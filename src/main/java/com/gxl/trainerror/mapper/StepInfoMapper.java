package com.gxl.trainerror.mapper;

import com.gxl.trainerror.bean.StepInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface StepInfoMapper {
    @Insert("insert into step_info (start_xiang_dian,end_xiang_dian) values(#{startXiangDian},#{endXiangDian})")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    public Integer insertStepInfo(StepInfo stepInfo);
}
