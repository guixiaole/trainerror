package com.gxl.trainerror.mapper;

import com.gxl.trainerror.bean.StepSelect;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;
@Mapper
public interface StepSelectMapper {
    //根据优先级排序，然后查询名字和id
    @Select("select * from step_select where template_id = #{templateId} and stress_name = #{stressName} order by prior_number asc")
    public List<StepSelect> selectByTemplateIdAndName(Integer templateId,String stressName);
    @Select("select * from step_select where id = #{id}")
    public StepSelect selectById(Integer id);
    @Select("select * from step_select where template_id =#{templateId} and stress_name = #{stressName} and prior_number=#{priorNumber}")
    public StepSelect selectByIdPriorName(Integer templateId,String stressName,Integer priorNumber);
    public Integer insertStepSelect(StepSelect select);
    @Select("select count(*) from step_select where template_id =#{templateId} and stress_name = #{stressName}")
    public Integer selectCount(Integer templateId,String stressName);
}
