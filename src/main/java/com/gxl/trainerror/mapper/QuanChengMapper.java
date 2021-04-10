package com.gxl.trainerror.mapper;

import com.gxl.trainerror.bean.QuanCheng;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuanChengMapper  {
    public Integer insertQuanCheng(QuanCheng quanCheng);
    @Select("select * from quan_cheng where file_id =#{id} order by xu_hao asc")
    public List<QuanCheng> selectByFileAscXuhao(Integer id);
}
