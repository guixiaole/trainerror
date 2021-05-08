package com.gxl.trainerror.util;

import com.gxl.trainerror.bean.FileInfo;
import com.gxl.trainerror.bean.FiveStepTemplate;
import com.gxl.trainerror.bean.QuanCheng;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class StepTemplateUtil {
    /*
        五步闸分析模板化
     */
    public static List<FiveStepTemplate> FiveStepTemplateAnalysis(List<QuanCheng> quanChengs){
        //进行五步闸模板化具体方法,传入的数据是全程记录
        //将传入的时间进行模板化
        Date lastDate = null;
        for (int i = 1;i<quanChengs.size();i++){
            //首先获取的是时间
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            if(i>1){
                int timeMinus = (int) ((quanChengs.get(i).getDateTime().getTime()-lastDate.getTime())/1000);
            }else {
                //数据初始化
            lastDate = quanChengs.get(i).getDateTime();
            }
        }
        return null;
    }
}
