package com.gxl.trainerror.mapper;

import com.gxl.trainerror.bean.StepAnalysis;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface StepAnalysisMapper {
    @Select("select * from step_analysis where file_id = #{fileId}")
    public StepAnalysis selectByFileId(Integer fileId);
    public Integer updateAnyStep(StepAnalysis stepAnalysis);
    @Insert("insert into step_analysis (file_id) values (#{fileId})")
    public Integer insertOnlyFileID(StepAnalysis stepAnalysis);
}
