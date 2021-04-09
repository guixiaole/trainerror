package com.gxl.trainerror.mapper;

import com.gxl.trainerror.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("select * from user where userid=#{userid} and password=#{password} ")
    public User selectUserByLogin(User user);
}
