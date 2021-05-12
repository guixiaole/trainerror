package com.gxl.trainerror.mapper;

import com.gxl.trainerror.bean.StepSelect;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

public interface StepSelectMapper {
    @Select("select * from step_select where template_id = #{templateId} and stress_name = #{stressName}")
    @Results(id = "stepSelect",value = {
            @Result(id=true,property = "id",column = "id"),
            @Result(property = "templateId",column = "template_id"),
            @Result(property = "continueTime",column = "continue_time"),
            @Result(property = "startNumber",column = "start_number"),
            @Result(property = "endNumber",column = "end_number"),
            @Result(property = "prior",column = "prior"),
            @Result(property = "stressName",column = "stress_name"),
            @Result(property = "selectId",column = "select_id"),
            @Result(property = "select",column = "select_id",one = @One(select = "com.gxl.trainerror.mapper.StepSelectMapper.selectById",fetchType = FetchType.EAGER))
    })
    public List<StepSelect> selectByTemplateIdAndName(StepSelect select);
    @Select("select * from step_select where id = #{id}")
    public StepSelect selectById(Integer id);
}
