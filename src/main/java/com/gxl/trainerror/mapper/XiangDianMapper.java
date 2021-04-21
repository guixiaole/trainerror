package com.gxl.trainerror.mapper;

import com.gxl.trainerror.bean.XiangDian;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface XiangDianMapper {
    @Insert("insert into xiang_dian (step,info) values (#{step},#{info})")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    public Integer insertXiangDian(XiangDian xiangDian);
}
