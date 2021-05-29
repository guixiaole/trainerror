package com.gxl.trainerror.mapper;

import com.gxl.trainerror.bean.StepShunXu;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;
@Mapper
public interface StepShunXuMapper {
    @Select("select * from step_shun_xu ")
    public List<StepShunXu> selectAll();
    @Select("select * from step_shun_xu where id = #{id}")
    @Results(id = "selectByID",value = {
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "name",column = "name"),
            @Result(property = "oneStep",column = "one_step"),
            @Result(property = "twoStep",column = "two_step"),
            @Result(property = "threeStep",column = "three_step"),
            @Result(property = "fourStep",column = "four_step"),
            @Result(property = "fiveStep",column = "five_step"),
            @Result(property = "sixStep",column = "six_step"),
            @Result(property = "sevenStep",column = "seven_step"),
            @Result(property = "eightStep",column = "eight_step"),
            @Result(property = "nineStep",column = "nine_step"),
            @Result(property = "one",column = "one_step",one = @One(select = "com.gxl.trainerror.mapper.AllTemplateMapper.selectById",fetchType = FetchType.EAGER)),
            @Result(property = "two",column = "two_step",one = @One(select = "com.gxl.trainerror.mapper.AllTemplateMapper.selectById",fetchType = FetchType.EAGER)),
            @Result(property = "three",column = "three_step",one = @One(select = "com.gxl.trainerror.mapper.AllTemplateMapper.selectById",fetchType = FetchType.EAGER)),
            @Result(property = "four",column = "four_step",one = @One(select = "com.gxl.trainerror.mapper.AllTemplateMapper.selectById",fetchType = FetchType.EAGER)),
            @Result(property = "five",column = "five_step",one = @One(select = "com.gxl.trainerror.mapper.AllTemplateMapper.selectById",fetchType = FetchType.EAGER)),
            @Result(property = "six",column = "six_step",one = @One(select = "com.gxl.trainerror.mapper.AllTemplateMapper.selectById",fetchType = FetchType.EAGER)),
            @Result(property = "seven",column = "seven_step",one = @One(select = "com.gxl.trainerror.mapper.AllTemplateMapper.selectById",fetchType = FetchType.EAGER)),
            @Result(property = "eight",column = "eight_step",one = @One(select = "com.gxl.trainerror.mapper.AllTemplateMapper.selectById",fetchType = FetchType.EAGER)),
            @Result(property = "nine",column = "nine_step",one = @One(select = "com.gxl.trainerror.mapper.AllTemplateMapper.selectById",fetchType = FetchType.EAGER))
    })
    public StepShunXu selectById(Integer id);
}
