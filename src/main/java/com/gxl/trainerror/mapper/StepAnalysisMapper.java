package com.gxl.trainerror.mapper;

import com.gxl.trainerror.bean.StepAnalysis;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

@Mapper
public interface StepAnalysisMapper {
    @Select("select * from step_analysis where file_id = #{fileId}")
    public StepAnalysis selectByFileId(Integer fileId);
    public Integer updateAnyStep(StepAnalysis stepAnalysis);
    @Insert("insert into step_analysis (file_id) values (#{fileId})")
    public Integer insertOnlyFileID(StepAnalysis stepAnalysis);
    @Select("select * from step_analysis where file_id = #{fileId}")
    @Results(id="stepInfoSelect",value = {
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "fileId",column = "file_id"),
            @Result(property = "jiXing",column = "ji_xing"),
            @Result(property = "oneStep",column = "one_step"),
            @Result(property = "twoStep",column = "two_step"),
            @Result(property = "threeStep",column = "three_step"),
            @Result(property = "fourStep",column = "four_step"),
            @Result(property = "fiveStep",column = "five_step"),
            @Result(property = "one",column = "one_step",one = @One(select = "com.gxl.trainerror.mapper.StepInfoMapper.selectById",fetchType = FetchType.EAGER)),
            @Result(property = "two",column = "two_step",one = @One(select = "com.gxl.trainerror.mapper.StepInfoMapper.selectById",fetchType = FetchType.EAGER)),
            @Result(property = "three",column = "three_step",one = @One(select = "com.gxl.trainerror.mapper.StepInfoMapper.selectById",fetchType = FetchType.EAGER)),
            @Result(property = "four",column = "four_step",one = @One(select = "com.gxl.trainerror.mapper.StepInfoMapper.selectById",fetchType = FetchType.EAGER)),
            @Result(property = "five",column = "five_step",one = @One(select = "com.gxl.trainerror.mapper.StepInfoMapper.selectById",fetchType = FetchType.EAGER))
         })
    public StepAnalysis selectStepByFileId(Integer fileId);
}
