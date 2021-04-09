package com.gxl.trainerror.mapper;

import com.gxl.trainerror.bean.FileInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

@Mapper
public interface FileInfoMapper {
    @Insert("insert into file_info (file_name, is_save,upload_time) values(#{fileName},#{isSave},#{uploadTime})")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    public Integer insertFileInfo(FileInfo fileInfo);
    @Select("select * from file_info where file_name = #{fileName}")
    public FileInfo selectFileInfoByName(String fileName);
    @Select("select * from file_info where upload_time>= #{date}")
    public List<FileInfo> selectAllFile(Date date);
}
