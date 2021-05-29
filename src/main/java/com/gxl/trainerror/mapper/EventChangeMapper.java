package com.gxl.trainerror.mapper;

import com.gxl.trainerror.bean.EventChange;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EventChangeMapper {
    @Select("select * from event_change ")
    public List<EventChange> selectAll();
    @Select("select * from event_change where id = #{id}")
    public EventChange selectById(Integer id);
}
