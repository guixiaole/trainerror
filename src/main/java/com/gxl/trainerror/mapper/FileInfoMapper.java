package com.gxl.trainerror.mapper;

import com.gxl.trainerror.bean.FileInfo;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface FileInfoMapper {
    @Insert("insert into file_info (file_name, is_save,upload_time,old_file_name) values(#{fileName},#{isSave},#{uploadTime},#{oldFileName})")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    public Integer insertFileInfo(FileInfo fileInfo);
    @Select("select * from file_info where id=#{id}")
    public FileInfo selectFileInfoByid(Integer id);
    @Select("select * from file_info where file_name = #{fileName}")
    public FileInfo selectFileInfoByName(String fileName);
    @Select("select * from file_info where upload_time>= #{date}")
    public List<FileInfo> selectAllFile(Date date);
    @Update("update file_info set is_save = 1 where id =#{id}")
    public Integer updateIsSaveFileInfo(FileInfo fileInfo);
    public Integer updateWhenInsertQuancheg(FileInfo fileInfo);
    //首页方法中，查找所有可能的文件
    public List<FileInfo> selectIndexFileInfo(FileInfo fileInfo);
}
