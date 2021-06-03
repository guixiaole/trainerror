package com.gxl.trainerror.service.imp;

import com.gxl.trainerror.bean.FileInfo;
import com.gxl.trainerror.bean.QuanCheng;
import com.gxl.trainerror.mapper.FileInfoMapper;
import com.gxl.trainerror.mapper.QuanChengMapper;
import com.gxl.trainerror.service.QuanChengService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class QuanChengServiceImpl implements QuanChengService {
    @Autowired
    private QuanChengMapper quanChengMapper;
    @Autowired
    private FileInfoMapper fileInfoMapper;

    @Override
    @Transactional
    public Integer insertQuanCheng(ArrayList<String>[] lists,
                                   Integer fileId) throws ParseException {
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        FileInfo fieldInfo=fileInfoMapper.selectFileInfoByid(fileId);
        //代表没有进行储存的。
        Integer flag =1;
        Integer flagCheci =1;
        if (lists.length==18 && fieldInfo.getIsSave()==0){
            for (int i =0;i<lists[0].size();i++) {
                    Integer xuhao;
                    Date date1;
                    Integer distance;
                    Integer speed;
                    Integer restrictSpeed;
                    Integer guanYa;
                    Integer gangYa;
                    Integer zhuanSuDianLiu;
                    Integer junGang1;
                    Integer junGang2;

                    if (lists[0].get(i).equals(""))
                        xuhao = null;
                    else
                        xuhao = Integer.valueOf(lists[0].get(i));
                    if (lists[2].get(i).equals(""))
                        date1 = null;
                    else
                    {
                        date1 = sdf.parse(lists[2].get(i));
                        if(flag==1){
                            fieldInfo.setFileStartTime(date1);
                            flag=0;
                        }
                    }
                    if (lists[5].get(i).equals(""))
                        distance = null;
                    else
                        distance = Integer.valueOf(lists[5].get(i));
                    if (lists[8].get(i).equals(""))
                        speed = null;
                    else
                        speed = Integer.valueOf(lists[8].get(i));

                    if (lists[9].get(i).equals(""))
                        restrictSpeed = null;
                    else
                        restrictSpeed = Integer.valueOf(lists[9].get(i));
                    if (lists[13].get(i).equals(""))
                        guanYa = null;
                    else
                        guanYa = Integer.valueOf(lists[13].get(i));
                    if (lists[14].get(i).equals(""))
                        gangYa = null;
                    else
                        gangYa = Integer.valueOf(lists[14].get(i));
                    if (lists[15].get(i).equals(""))
                        zhuanSuDianLiu = null;
                    else
                        zhuanSuDianLiu = Integer.valueOf(lists[15].get(i));
                    if (lists[16].get(i).equals(""))
                        junGang1 = null;
                    else
                        junGang1 = Integer.valueOf(lists[16].get(i));
                    if (lists[17].get(i).equals(""))
                        junGang2 = null;
                    else
                        junGang2 = Integer.valueOf(lists[17].get(i));
                    String file1;
                    String file3;
                    String file4;
                    String file6;
                    String file7;
                    String file10;
                    String file11;
                    String file12;
                    if (lists[1].get(i).equals(""))
                        file1 = null;
                    else{
                        file1 = lists[1].get(i);
                        if (file1.equals("车次")){
                            fieldInfo.setCheCi(lists[4].get(i));
                        }
                        //机型没有找到,版本号也没有修改
                        if (file1.equals("机车号")){
                            fieldInfo.setCheHao(lists[4].get(i));
                        }
                        if (file1.equals("司机号1")|| file1.equals("司机名")){
                            fieldInfo.setSiJiName(lists[4].get(i));
                        }
                        if (file1.equals("司机号2")|| file1.equals("副司机")){
                            fieldInfo.setFuSiJiName(lists[4].get(i));
                        }
                        if (file1.equals("计长")){
                            fieldInfo.setJiChang(lists[4].get(i));
                        }
                        if (file1.equals("数据交路号")){
                            fieldInfo.setJiaoLuHao(lists[4].get(i));
                        }
                        if (file1.equals("车站号")){
                            fieldInfo.setStartStation(lists[4].get(i));
                        }
                        if (file1.contains("机") && file1.contains("型")){
                            fieldInfo.setJiXing(lists[4].get(i));
                        }
                        if (file1.equals("机车号")){
                            fieldInfo.setJiCheHao(lists[4].get(i));
                        }

                    }

                    if (lists[3].get(i).equals(""))
                        file3 = null;
                    else
                        file3 = lists[3].get(i);
                    if (lists[4].get(i).equals(""))
                        file4 = null;
                    else
                        file4 = lists[4].get(i);
                    if (lists[6].get(i).equals(""))
                        file6 = null;
                    else
                        file6 = lists[6].get(i);
                    if (lists[7].get(i).equals(""))
                        file7 = null;
                    else
                        file7 = lists[7].get(i);
                    if (lists[10].get(i).equals(""))
                        file10 = null;
                    else
                        file10 = lists[10].get(i);
                    if (lists[11].get(i).equals(""))
                        file11 = null;
                    else
                        file11 = lists[11].get(i);
                    if (lists[12].get(i).equals(""))
                        file12 = null;
                    else
                        file12 = lists[12].get(i);

                    QuanCheng quanCheng = new QuanCheng(
                            fileId,
                            xuhao,
                            file1,
                            date1,
                            file3,
                            file4,
                            distance,
                            file6,
                            file7,
                            speed,
                            restrictSpeed,
                            file10,
                            file11,
                            file12,
                            guanYa,
                            gangYa,
                            zhuanSuDianLiu,
                            junGang1,
                            junGang2
                    );
                    quanChengMapper.insertQuanCheng(quanCheng);

            }
            fileInfoMapper.updateWhenInsertQuancheg(fieldInfo);
}
        return 1;
    }

    @Override
    public List<QuanCheng> selectByFileAscXuhao(Integer id) {
        return quanChengMapper.selectByFileAscXuhao(id);
    }

    @Override
    public void insertQuanChengByList(List<QuanCheng> quanCheng,FileInfo fieldInfo) {
        for (int i = 0;i<quanCheng.size();i++) {
            quanChengMapper.insertQuanCheng(quanCheng.get(i));
            String file1 = quanCheng.get(i).getEvent();
            if (file1.equals("车次")){
                fieldInfo.setCheCi(quanCheng.get(i).getOther());
            }
            //机型没有找到,版本号也没有修改
            if (file1.equals("机车号")){
                int chehao = (int) Double.parseDouble(quanCheng.get(i).getOther());
                fieldInfo.setCheHao(String.valueOf(chehao));
            }
            if (file1.equals("司机号1") || file1.equals("司机名")){
                fieldInfo.setSiJiName(quanCheng.get(i).getOther());
            }
            if (file1.equals("司机号2")|| file1.equals("副司机")){
                fieldInfo.setFuSiJiName(quanCheng.get(i).getOther());
            }
            if (file1.equals("计长")){
                fieldInfo.setJiChang(quanCheng.get(i).getOther());
            }
            if (file1.equals("数据交路号")){
                fieldInfo.setJiaoLuHao(quanCheng.get(i).getOther());
            }
            if (file1.equals("车站号")){
                fieldInfo.setStartStation(quanCheng.get(i).getOther());
            }
            if (file1.contains("机") && file1.contains("型")){
                fieldInfo.setJiXing(quanCheng.get(i).getOther());
            }
            if (file1.equals("机车号")){
                int chehao = (int) Double.parseDouble(quanCheng.get(i).getOther());
                fieldInfo.setJiCheHao(String.valueOf(chehao));
            }
        }
        fileInfoMapper.updateWhenInsertQuancheg(fieldInfo);

    }
}
