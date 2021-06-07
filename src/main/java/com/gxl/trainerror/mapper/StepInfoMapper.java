package com.gxl.trainerror.mapper;

import com.gxl.trainerror.bean.StepInfo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

@Mapper
public interface StepInfoMapper {
    @Insert("insert into step_info (start_xiang_dian,end_xiang_dian,start_pos,end_pos) values (#{startXiangDian},#{endXiangDian},#{startPos},#{endPos})")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    public Integer insertStepInfo(StepInfo stepInfo);
    @Select("select * from step_info where id = #{id}")
    @Results(id = "stepinfoXiangdian",value = {
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "startXiangDian",column = "start_xiang_dian"),
            @Result(property = "startPos",column = "start_pos"),
            @Result(property = "endXiangDian",column = "end_xiang_dian"),
            @Result(property = "endPos",column = "end_pos"),
            @Result(property = "xiangDianId",column = "xiang_dian_id"),
            @Result(property = "xiangDian",column = "xiang_dian_id",one = @One(select = "com.gxl.trainerror.mapper.XiangDianMapper.selectById",fetchType = FetchType.EAGER)),
    })
    public StepInfo selectById(Integer id);
    @Update("update step_info set xiang_dian_id = #{xiangDianId} where id =#{id}")
    public Integer updateXiangDian(StepInfo stepInfo);
}
