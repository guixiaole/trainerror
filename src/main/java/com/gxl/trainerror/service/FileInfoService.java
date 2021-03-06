package com.gxl.trainerror.service;

import com.gxl.trainerror.bean.FileInfo;

import java.util.Date;
import java.util.List;

public interface FileInfoService {
    public Integer insertFileInfoStart(FileInfo fileInfo);
    public Integer updateIsSaveFileInfo(FileInfo fileInfo);
    public FileInfo selectFileInfoByName(String fileName);
    public List<FileInfo> selectAllFileInfo(Date date);
    public FileInfo selectFileInfoById(Integer id);
    //首页里查找搜索需要的文件
    public List<FileInfo> selectIndexFileInfo(FileInfo fileInfo);
    //首页显示所需要的文件
    public List<FileInfo> selectIndexFileInfoByIndex();
    public FileInfo selectTestById(Integer id);
}
