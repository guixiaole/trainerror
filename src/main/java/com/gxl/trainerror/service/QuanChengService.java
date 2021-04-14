package com.gxl.trainerror.service;

import com.gxl.trainerror.bean.QuanCheng;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

//@Transactional
public interface QuanChengService {
    //将获取的list放入service中，然后进行存储。不用在controller中储存业务。

    public  Integer insertQuanCheng(ArrayList<String>[] lists,Integer fileId) throws ParseException;
    public List<QuanCheng> selectByFileAscXuhao(Integer id);
}
