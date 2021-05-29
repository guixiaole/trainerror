package com.gxl.trainerror.mapper;

import com.gxl.trainerror.bean.JiCheInfo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface JiCheInfoMapper {
    @Select("select * from ji_che_info ")
    public List<JiCheInfo> selectAll();
    public Integer insertJiChe(JiCheInfo jiCheInfo);
    @Delete("DELETE FROM ji_che_info WHERE id = #{id}")
    public void deleteById(Integer id);
}
