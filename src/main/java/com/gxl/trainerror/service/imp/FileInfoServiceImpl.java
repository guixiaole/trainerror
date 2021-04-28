package com.gxl.trainerror.service.imp;

import com.gxl.trainerror.bean.FileInfo;
import com.gxl.trainerror.mapper.FileInfoMapper;
import com.gxl.trainerror.service.FileInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class FileInfoServiceImpl implements FileInfoService {
    @Autowired
    private FileInfoMapper fileInfoMapper;
    @Override
    public Integer insertFileInfoStart(FileInfo fileInfo) {
        //返回的是自增id
        Integer id = fileInfoMapper.insertFileInfo(fileInfo);
        return id;
    }

    @Override
    public Integer updateIsSaveFileInfo(FileInfo fileInfo) {
        return fileInfoMapper.updateIsSaveFileInfo(fileInfo);
    }

    @Override
    public FileInfo selectFileInfoByName(String fileName) {
        return fileInfoMapper.selectFileInfoByName(fileName);
    }

    @Override
    public List<FileInfo> selectAllFileInfo(Date date) {
        return fileInfoMapper.selectAllFile(date);
    }

    @Override
    public FileInfo selectFileInfoById(Integer id) {
        return fileInfoMapper.selectFileInfoByid(id);
    }
    /*
    首页需要查找全部的文件信息
     */
    @Override
    public List<FileInfo> selectIndexFileInfo(FileInfo fileInfo) {
        return fileInfoMapper.selectIndexFileInfo(fileInfo);
    }

    @Override
    public List<FileInfo> selectIndexFileInfoByIndex() {
        return fileInfoMapper.selectIndexFileInfoByIndex();
    }

    @Override
    public FileInfo selectTestById(Integer id) {
        return fileInfoMapper.selectTestById(id);
    }
}
