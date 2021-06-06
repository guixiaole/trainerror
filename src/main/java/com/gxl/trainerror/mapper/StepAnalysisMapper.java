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
            @Result(property = "sixStep",column = "six_step"),
            @Result(property = "sevenStep",column = "seven_step"),
            @Result(property = "eightStep",column = "eight_step"),
            @Result(property = "nineStep",column = "nine_step"),
            @Result(property = "shuangOneStep",column = "shuang_one_step"),
            @Result(property = "shuangTwoStep",column = "shuang_two_step"),
            @Result(property = "shuangThreeStep",column = "shuang_three_step"),
            @Result(property = "shuangFourStep",column = "shuang_four_step"),
            @Result(property = "shuangFiveStep",column = "shuang_five_step"),
            @Result(property = "shuangSixStep",column = "shuang_six_step"),
            @Result(property = "shuangSevenStep",column = "shuang_seven_step"),
            @Result(property = "shuangEightStep",column = "shuang_eight_step"),
            @Result(property = "shuangNineStep",column = "shuang_nine_step"),
            @Result(property = "one",column = "one_step",one = @One(select = "com.gxl.trainerror.mapper.StepInfoMapper.selectById",fetchType = FetchType.EAGER)),
            @Result(property = "two",column = "two_step",one = @One(select = "com.gxl.trainerror.mapper.StepInfoMapper.selectById",fetchType = FetchType.EAGER)),
            @Result(property = "three",column = "three_step",one = @One(select = "com.gxl.trainerror.mapper.StepInfoMapper.selectById",fetchType = FetchType.EAGER)),
            @Result(property = "four",column = "four_step",one = @One(select = "com.gxl.trainerror.mapper.StepInfoMapper.selectById",fetchType = FetchType.EAGER)),
            @Result(property = "five",column = "five_step",one = @One(select = "com.gxl.trainerror.mapper.StepInfoMapper.selectById",fetchType = FetchType.EAGER)),
            @Result(property = "six",column = "six_step",one = @One(select = "com.gxl.trainerror.mapper.StepInfoMapper.selectById",fetchType = FetchType.EAGER)),
            @Result(property = "seven",column = "seven_step",one = @One(select = "com.gxl.trainerror.mapper.StepInfoMapper.selectById",fetchType = FetchType.EAGER)),
            @Result(property = "eight",column = "eight_step",one = @One(select = "com.gxl.trainerror.mapper.StepInfoMapper.selectById",fetchType = FetchType.EAGER)),
            @Result(property = "nine",column = "nine_step",one = @One(select = "com.gxl.trainerror.mapper.StepInfoMapper.selectById",fetchType = FetchType.EAGER)),
            @Result(property = "shuangOne",column = "shuang_one_step",one = @One(select = "com.gxl.trainerror.mapper.StepInfoMapper.selectById",fetchType = FetchType.EAGER)),
            @Result(property = "shuangTwo",column = "shuang_two_step",one = @One(select = "com.gxl.trainerror.mapper.StepInfoMapper.selectById",fetchType = FetchType.EAGER)),
            @Result(property = "shuangThree",column = "shuang_three_step",one = @One(select = "com.gxl.trainerror.mapper.StepInfoMapper.selectById",fetchType = FetchType.EAGER)),
            @Result(property = "shuangFour",column = "shuang_four_step",one = @One(select = "com.gxl.trainerror.mapper.StepInfoMapper.selectById",fetchType = FetchType.EAGER)),
            @Result(property = "shuangFive",column = "shuang_five_step",one = @One(select = "com.gxl.trainerror.mapper.StepInfoMapper.selectById",fetchType = FetchType.EAGER)),
            @Result(property = "shuangSix",column = "shuang_six_step",one = @One(select = "com.gxl.trainerror.mapper.StepInfoMapper.selectById",fetchType = FetchType.EAGER)),
            @Result(property = "shuangSeven",column = "shuang_seven_step",one = @One(select = "com.gxl.trainerror.mapper.StepInfoMapper.selectById",fetchType = FetchType.EAGER)),
            @Result(property = "shuangEight",column = "shuang_eight_step",one = @One(select = "com.gxl.trainerror.mapper.StepInfoMapper.selectById",fetchType = FetchType.EAGER)),
            @Result(property = "shuangNine",column = "shuang_nine_step",one = @One(select = "com.gxl.trainerror.mapper.StepInfoMapper.selectById",fetchType = FetchType.EAGER))
    })
    public StepAnalysis selectStepByFileId(Integer fileId);
}
