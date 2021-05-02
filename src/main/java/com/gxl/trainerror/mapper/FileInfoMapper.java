package com.gxl.trainerror.mapper;

import com.gxl.trainerror.bean.FileInfo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.web.bind.annotation.ResponseStatus;

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
    @ResultMap(value = "fileinfoIndex")
    public List<FileInfo> selectAllFile(Date date);
    @Update("update file_info set is_save = 1 where id =#{id}")
    public Integer updateIsSaveFileInfo(FileInfo fileInfo);
    public Integer updateWhenInsertQuancheg(FileInfo fileInfo);
    //首页方法中，查找所有可能的文件
    //resultType="com.gxl.trainerror.bean.FileInfo"
    public List<FileInfo> selectIndexFileInfo(FileInfo fileInfo);
    //首先进入首页中进行查找。
    @Select("select * from file_info order by upload_time desc limit 20")
    @Results(id="fileinfoIndex",value = {
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "fileName",column = "file_name"),
            @Result(property = "isSave",column = "is_save"),
            @Result(property = "zhuangBeiDian",column = "zhuang_bei_dian"),
            @Result(property = "fileStartTime",column = "file_start_time"),
            @Result(property = "cheCi",column = "che_ci"),
            @Result(property = "jiXing",column = "ji_xing"),
            @Result(property = "cheHao",column = "che_hao"),
            @Result(property = "siJiName",column = "si_ji_name"),
            @Result(property = "fuSiJiName",column = "fu_si_ji_name"),
            @Result(property = "fileState",column = "file_state"),
            @Result(property = "testScore",column = "test_score"),
            @Result(property = "uploadTime",column = "upload_time"),
            @Result(property = "oldFileName",column = "old_file_name"),
            @Result(property = "jiCheHao",column = "ji_che_hao"),
            @Result(property = "jiaoLuHao",column = "jiao_lu_hao"),
            @Result(property = "jiChang",column = "ji_chang"),
            @Result(property = "startStation",column = "start_station"),
            @Result(property = "banBen",column = "ban_ben"),
            @Result(property = "stepAnalysis",column = "id",one = @One(select = "com.gxl.trainerror.mapper.StepAnalysisMapper.selectStepByFileId",fetchType = FetchType.EAGER))
    })
    public List<FileInfo> selectIndexFileInfoByIndex();
    @Select("select * from file_info where id = #{id}")
    @ResultMap(value = "fileinfoIndex")
    public FileInfo selectTestById(Integer id);
}
