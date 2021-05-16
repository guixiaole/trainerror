package com.gxl.trainerror.mapper;

import com.gxl.trainerror.bean.StepSelect;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;
@Mapper
public interface StepSelectMapper {
    @Select("select * from step_select where template_id = #{templateId} and stress_name = #{stressName} order by prior_number asc")
    @Results(id = "stepSelect",value = {
            @Result(id=true,property = "id",column = "id"),
            @Result(property = "templateId",column = "template_id"),
            @Result(property = "continueTime",column = "continue_time"),
            @Result(property = "startNumber",column = "start_number"),
            @Result(property = "endNumber",column = "end_number"),
            @Result(property = "priorNumber",column = "prior_number"),
            @Result(property = "stressName",column = "stress_name"),
            @Result(property = "selectId",column = "select_id"),
            @Result(property = "select",column = "select_id",one = @One(select = "com.gxl.trainerror.mapper.StepSelectMapper.selectById",fetchType = FetchType.EAGER))
    })
    public List<StepSelect> selectByTemplateIdAndName(Integer templateId,String stressName);
    @Select("select * from step_select where id = #{id}")
    public StepSelect selectById(Integer id);
    @Select("select * from step_select where template_id =#{templateId} and stress_name = #{stressName} and prior_number=#{priorNumber}")
    public StepSelect selectByIdPriorName(Integer templateId,String stressName,Integer prior);
    public Integer insertStepSelect(StepSelect select);
    @Select("select count(*) from step_select where template_id =#{templateId} and stress_name = #{stressName}")
    public Integer selectCount(Integer templateId,String stressName);
}
