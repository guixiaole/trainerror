package com.gxl.trainerror.mapper;

import com.gxl.trainerror.bean.AllTemplate;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AllTemplateMapper {
    @Select("select * from all_template")
    public List<AllTemplate> index();
    @Insert("insert into all_template (template_name) values(#{templateName})")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    public Integer insertTemplate(AllTemplate allTemplate);
}
