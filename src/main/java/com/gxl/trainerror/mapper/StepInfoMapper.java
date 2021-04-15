package com.gxl.trainerror.mapper;

import com.gxl.trainerror.bean.StepInfo;
import org.apache.ibatis.annotations.*;

@Mapper
public interface StepInfoMapper {
    @Insert("insert into step_info (start_xiang_dian,end_xiang_dian) values(#{startXiangDian},#{endXiangDian})")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    public Integer insertStepInfo(StepInfo stepInfo);
    @Select("select * from step_info where id = #{id}")
    public StepInfo selectById(Integer id);
    @Update("update step_info set xiang_dian_id = #{xiangDianId} where id =#{id}")
    public Integer updateXiangDian(StepInfo stepInfo);
}
