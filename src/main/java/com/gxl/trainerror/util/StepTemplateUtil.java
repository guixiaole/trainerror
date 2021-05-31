package com.gxl.trainerror.util;

import com.gxl.trainerror.bean.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StepTemplateUtil {
    /*
        五步闸分析模板化
     */

    /*
    需不需要将时间进行标准化，标准化后的数据才能进行使用。
    应该是需要的。需要进行标准化。
     */
    public static List<QuanCheng> TimeTemplate(List<QuanCheng> quanChengs){
        /*
        将时间进行标准化，标准化后才能够进行运算。
         */
        List<QuanCheng> resQuanCheng = new ArrayList<>();
        Date lastDate = null;
        int firstTimes = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 1;i<quanChengs.size();i++){

            if (null !=quanChengs.get(i).getGuanYa() && null != quanChengs.get(i).getGangYa() && null!= quanChengs.get(i).getJunGang2() && null!= quanChengs.get(i).getJunGang1()){
                //首先把空的剔除掉。
                if (firstTimes==0){
                    resQuanCheng.add(quanChengs.get(i));
                    //第一次添加进去。
                    lastDate = quanChengs.get(i).getDateTime();
                    firstTimes = 1;
                }else{
                    int timeMinus = (int) ((quanChengs.get(i).getDateTime().getTime()-lastDate.getTime())/1000);
                    if (timeMinus==0){
                        //如果时间一致的话，就更改其中不一致的地方。
                        if (!resQuanCheng.get(resQuanCheng.size()-1).getGuanYa().equals(quanChengs.get(i).getGuanYa())){
                            resQuanCheng.get(resQuanCheng.size()-1).setGuanYa(quanChengs.get(i).getGuanYa());
                        }
                        if (!resQuanCheng.get(resQuanCheng.size()-1).getGangYa().equals(quanChengs.get(i).getGangYa())){
                            resQuanCheng.get(resQuanCheng.size()-1).setGangYa(quanChengs.get(i).getGangYa());
                        }
                        if (!resQuanCheng.get(resQuanCheng.size()-1).getJunGang1().equals(quanChengs.get(i).getJunGang1())){
                            resQuanCheng.get(resQuanCheng.size()-1).setJunGang1(quanChengs.get(i).getJunGang1());
                        }
                        if (!resQuanCheng.get(resQuanCheng.size()-1).getJunGang2().equals(quanChengs.get(i).getJunGang2())){
                            resQuanCheng.get(resQuanCheng.size()-1).setJunGang2(quanChengs.get(i).getJunGang2());
                        }
                    }
                    else if(timeMinus==1){
                        //只差一秒
                        resQuanCheng.add(quanChengs.get(i));
                        lastDate = quanChengs.get(i).getDateTime();
                    }else {
                        //假设时间上多很多的时候
                        Date tempdate = lastDate;
                        while (timeMinus>1){

                            //等于上一秒的时候
//                            tempdate.setTime(tempdate);
                            if (i>386){
                                System.out.println(i);
                            }
                            Date temp = null;
                            temp = (Date) lastDate.clone();
                            temp.setTime(temp.getTime()+1000);
                            QuanCheng lastQuanCheng = resQuanCheng.get(resQuanCheng.size()-1);
                            QuanCheng quanChengTemp = new QuanCheng(temp,lastQuanCheng.getGuanYa().intValue(),lastQuanCheng.getGangYa().intValue(),lastQuanCheng.getJunGang1().intValue(),lastQuanCheng.getJunGang2().intValue(),lastQuanCheng.getXuHao().intValue());
                            resQuanCheng.add(quanChengTemp);
                            timeMinus--;
                            lastDate = (Date) temp.clone();
                        }
                        resQuanCheng.add(quanChengs.get(i));
                        lastDate = quanChengs.get(i).getDateTime();
                    }
                }
            }
        }
        return resQuanCheng;
    }

//    public static FiveStepTemplate stepFinder(List<QuanCheng>quanChengs, List<List<FiveStepTemplate>> fiveStepTemplates, List<List<StepSelect>> stepSelects){
//        /*
//        步骤寻找，
//        输入：quancheng 全程文件
//             fiveStepTemplate 全程文件板块
//             stepSelect 步骤的板块
//             如果输出结果是-1 则表示没有找到结果
//         */
//        List<FiveStepTemplate> guanyaTemplate =fiveStepTemplates.get(0);
//        List<FiveStepTemplate> gangyaTemplate =fiveStepTemplates.get(1);
//        List<FiveStepTemplate> jungang1Template =fiveStepTemplates.get(2);
//        List<FiveStepTemplate> jungang2Template =fiveStepTemplates.get(0);
//        List<StepSelect> guanyaSelect = stepSelects.get(0);
//        List<StepSelect> gangyaSelect = stepSelects.get(1);
//        List<StepSelect> jungangSelect = stepSelects.get(2);
//        //挨个进行匹配。最后进行比较，然后返回结果。
//        List<FiveStepTemplate> guanyares= new ArrayList<>();
//        List<FiveStepTemplate> gangyares= new ArrayList<>();
//        List<FiveStepTemplate> jungangres= new ArrayList<>();
//        if (guanyaSelect.size()>0){
//            int i = 0;
//            for (int j=0;j<guanyaTemplate.size();j++){
//                //首先判断第一个是不是一样，然后后面再继续向下比较
//                int temp = j;
//                //这里没有考虑到同步的问题
//                //同步问题暂时还不考虑。下一阶段开始考虑
//                while (i<guanyaSelect.size() && temp<guanyaTemplate.size()){
//                    if (guanyaSelect.get(i).getIsStable()==guanyaTemplate.get(temp).getIsStable()
//                    && Math.abs( guanyaSelect.get(i).getStartNumber()-guanyaTemplate.get(temp).getStartStress())<=20
//                            && Math.abs( guanyaSelect.get(i).getEndNumber()-guanyaTemplate.get(temp).getEndStress())<=20
//                    && guanyaTemplate.get(temp).getContinueTime()>=guanyaSelect.get(i).getMinTime()
//                    &&guanyaTemplate.get(temp).getContinueTime()<=guanyaSelect.get(i).getMaxTime()){
//                        i++;
//                        temp++;
//                    }else {
//                        i = 0;
//                        break;
//                    }
//                }
//                if (i>=guanyaSelect.size()){
//                    guanyares.add(guanyaTemplate.get(temp-1));
//                    i=0;
//                    j = temp;
//                }
//            }
//        }
//        if (gangyaSelect.size()>0){
//            int i = 0;
//            for (int j=0;j<gangyaTemplate.size();j++){
//                //首先判断第一个是不是一样，然后后面再继续向下比较
//                int temp = j;
//                //这里没有考虑到同步的问题
//                //同步问题暂时还不考虑。下一阶段开始考虑
//                while (i<gangyaSelect.size() && temp<gangyaTemplate.size()){
//                    if (gangyaSelect.get(i).getIsStable()==gangyaTemplate.get(temp).getIsStable()
//                            && Math.abs( gangyaSelect.get(i).getStartNumber()-gangyaTemplate.get(temp).getStartStress())<=20
//                            && Math.abs( gangyaSelect.get(i).getEndNumber()-gangyaTemplate.get(temp).getEndStress())<20
//                            && gangyaTemplate.get(temp).getContinueTime()>=gangyaSelect.get(i).getMinTime()
//                    && gangyaTemplate.get(temp).getContinueTime()<=gangyaSelect.get(i).getMaxTime()){
//                        i++;
//                        temp++;
//                    }else {
//                        i = 0;
//                        break;
//                    }
//                }
//                if (i>=gangyaSelect.size()){
//                    gangyares.add(gangyaTemplate.get(temp));
//                    j = temp;
//                }
//            }
//        }
//        if (jungangSelect.size()>0){
//            //均缸这里有问题还是需要修改的。
//            int i = 0;
//            for (int j=0;j<jungang1Template.size();j++){
//                //首先判断第一个是不是一样，然后后面再继续向下比较
//                int temp = j;
//                //这里没有考虑到同步的问题
//                //同步问题暂时还不考虑。下一阶段开始考虑
//                while (i<jungangSelect.size() && temp<jungang1Template.size()){
//                    if (jungangSelect.get(i).getIsStable()==jungang1Template.get(temp).getIsStable()
//                            && Math.abs( jungangSelect.get(i).getStartNumber()-jungang1Template.get(temp).getStartStress())<=20
//                            && Math.abs( jungangSelect.get(i).getEndNumber()-jungang1Template.get(temp).getEndStress())<20
//                            && jungang1Template.get(temp).getContinueTime()>=jungangSelect.get(i).getMinTime()
//                    && jungang1Template.get(temp).getContinueTime()<=jungangSelect.get(i).getMaxTime()){
//                        i++;
//                        temp++;
//                    }else {
//                        i = 0;
//                        break;
//                    }
//                }
//                if (i>=jungangSelect.size()){
//                    jungangres.add(jungang1Template.get(temp));
//                    j = temp;
//                }
//            }
//        }
//        if (jungangres.size()==0){
//            if (jungangSelect.size()>0){
//                //均缸这里有问题还是需要修改的。
//                int i = 0;
//                for (int j=0;j<jungang2Template.size();j++){
//                    //首先判断第一个是不是一样，然后后面再继续向下比较
//                    int temp = j;
//                    //这里没有考虑到同步的问题
//                    //同步问题暂时还不考虑。下一阶段开始考虑
//                    while (i<jungangSelect.size() && temp<jungang2Template.size()){
//                        if (jungangSelect.get(i).getIsStable()==jungang2Template.get(temp).getIsStable()
//                                && Math.abs( jungangSelect.get(i).getStartNumber()-jungang2Template.get(temp).getStartStress())<=20
//                                && Math.abs( jungangSelect.get(i).getEndNumber()-jungang2Template.get(temp).getEndStress())<20
//                                && jungang2Template.get(temp).getContinueTime()>=jungangSelect.get(i).getMinTime()
//                                && jungang2Template.get(temp).getContinueTime()<=jungangSelect.get(i).getMaxTime()){
//                            i++;
//                            temp++;
//                        }else {
//                            i = 0;
//                            break;
//                        }
//                    }
//                    if (i>=jungangSelect.size()){
//                        jungangres.add(jungang2Template.get(temp));
//                        j = temp;
//                    }
//                }
//            }
//        }
//        //当所有res的都找到了。进行匹配。
//        List <Integer> res = new ArrayList<>();
//        List<FiveStepTemplate> TemplateRes = new ArrayList<>();
//        if((guanyaSelect.size()>0&&guanyares.size()==0)||(gangyaSelect.size()>0 &&guanyares.size()==0)||(jungangSelect.size()>0&&jungangres.size()==0)){
//                //当有条件没找到的时候，进行返回。
////                res.add(-1);
//                return null;
//        }
//        //进行比较,最好时间不相差过3S
//        if (guanyaSelect.size()>0){
//            for (int i = 0;i<guanyares.size();i++){
//                if (gangyaSelect.size()>0){
//                    for (int j = 0;j<=gangyares.size();j++){
//                       if (Math.abs((guanyares.get(i).getStartTime().getTime()-gangyares.get(j).getStartTime().getTime())/1000)<=3){
//                            if (jungangSelect.size()>0){
//                                for(int q= 0;q<=jungangres.size();q++){
//                                    if (Math.abs((guanyares.get(i).getStartTime().getTime()-jungangres.get(q).getStartTime().getTime())/1000)<=3){
//                                        TemplateRes.add(guanyares.get(i));
//                                        break;
//                                    }
//                                }
//                            }else {
//                                TemplateRes.add(guanyares.get(i));
//                                break;
//                            }
//                       }
//                    }
//                }else {
//                    if (jungangSelect.size()>0){
//                        for(int q= 0;q<=jungangres.size();q++){
//                            if (Math.abs((guanyares.get(i).getStartTime().getTime()-jungangres.get(q).getStartTime().getTime())/1000)<=3){
//                                TemplateRes.add(guanyares.get(i));
//                                break;
//                            }
//                        }
//                    }else {
//                        TemplateRes.add(guanyares.get(i));
//                    }
//                }
//            }
//        }else if(gangyaSelect.size()>0){
//            for (int i = 0;i<gangyares.size();i++){
//                if (jungangSelect.size()>0){
//                    for(int q= 0;q<=jungangres.size();q++){
//                        if (Math.abs((guanyares.get(i).getStartTime().getTime()-jungangres.get(q).getStartTime().getTime())/1000)<=3){
//                            TemplateRes.add(gangyares.get(i));
//                            break;
//                        }
//                    }
//                }else{
//                    TemplateRes.add(gangyares.get(i));
//                }
//            }
//        }else {
//            for(int i = 0;i<jungangres.size();i++){
//                TemplateRes.add(jungangres.get(i));
//            }
//        }
//        if (TemplateRes.size()>0)
//            return TemplateRes.get(TemplateRes.size()-1);
//        else
//            return null;
//    }
    public static List<List<ZhuanDian>> stepFinder(String sortNumber,List<List<StepSelect>> stepSelectList,List<QuanCheng> quanChengs){
        List <StepSelect> guanya = stepSelectList.get(0);
        List<StepSelect> gangya = stepSelectList.get(1);
        List<StepSelect> jungang = stepSelectList.get(2);
//        int i = 0;
        int length = quanChengs.size();
        List<List<ZhuanDian>> resZhuanDian = new ArrayList<>();
        List<List<Integer>> resAll = new ArrayList<>();
        //首先要找到合适的地方,首先看管压
        if(sortNumber.charAt(0)=='管'){
            if(guanya.size()>0){
                List<List<Integer>> guanyaStep = FirstShaiXuan(quanChengs,guanya.get(0));
                if (guanyaStep.size()>0 && guanya.size()>1){
                   List<List<Integer>>res = getStartAndEnd(quanChengs,guanyaStep,guanya.get(1));
                   int flag = 2;
                   while (flag<guanya.size()){
                      res = getStartAndEnd(quanChengs,res,guanya.get(flag));
                      flag++;
                   }
                   resAll = res;
                }
                if (resAll.size()>0){
                    resZhuanDian = getZhuanDian(resAll,quanChengs,guanya);
                }
            }
        }else if (sortNumber.charAt(0)=='均'){
            if(jungang.size()>0){
                List<List<Integer>> jungangStep = FirstShaiXuan(quanChengs,jungang.get(0));
                if (jungangStep.size()>0 && jungang.size()>1){
                    List<List<Integer>>res = getStartAndEnd(quanChengs,jungangStep,jungang.get(1));
                    int flag = 1;
                    while (flag<guanya.size()){
                        res = getStartAndEnd(quanChengs,res,jungang.get(flag));
                        flag++;
                    }
                    resAll = res;
                }
                if (resAll.size()>0){
                    resZhuanDian = getZhuanDian(resAll,quanChengs,jungang);
                }
            }
        }else {
            //接下来就只剩列管了。
            if (gangya.size()>0){
                List<List<Integer>> gangyaStep = FirstShaiXuan(quanChengs,gangya.get(0));
                if (gangyaStep.size()>0 && jungang.size()>1){
                    List<List<Integer>>res = getStartAndEnd(quanChengs,gangyaStep,gangya.get(1));
                    int flag = 1;
                    while (flag<guanya.size()){
                        res = getStartAndEnd(quanChengs,res,gangya.get(flag));
                        flag++;
                    }
                    resAll = res;
                }
                if (resAll.size()>0){
                    resZhuanDian = getZhuanDian(resAll,quanChengs,gangya);
                    //这里得到的转点都是反方向的。

                }
            }
        }
        //因为之前是反转的。所以再将其反转回来。
        List<List<ZhuanDian>> resTemp = new ArrayList<>();
        for(int i = 0;i<resZhuanDian.size();i++){
            List<ZhuanDian> temp = new ArrayList<>();
            for (int j=resZhuanDian.get(i).size()-1;j>=0;j--){
                temp.add(resZhuanDian.get(i).get(j));
            }
            resTemp.add(temp);
        }
        resZhuanDian = resTemp;
        List<List<ZhuanDian>> secondZhuanDian = new ArrayList<>();
        if(resAll.size()>0){
            //假设第一步已经获得了一些步骤。此时可以通过进行回溯找到相对应的步骤。已解决
            //此时已经获得一些步骤。
            //A+1+B的长度怎么解决呢？已经解决了。通过引入新的类。
            //新建一个类，用来存储相对应的数据。这样也可以保存
            //暂时理解为只有两个类。后面再进行相对应的扩充.

            if(sortNumber.charAt(1)=='管'){
                //暂且默认只有一个压力。xxx不能默认只有一个压力。
                if (guanya.size()>0){
                    for (int pos = 0;pos<resZhuanDian.size();pos++){
                        int flag = 1;
                        for (StepSelect guanyaTemp : guanya) {
                            if (!isSecondconform(quanChengs,guanyaTemp,resZhuanDian.get(pos))){
                                flag = 0;
                                break;
                            }
                        }
                        if (flag==1){
                            secondZhuanDian.add(resZhuanDian.get(pos));
                        }
                    }
                }
            }else if(sortNumber.charAt(1)=='均'){
                if (jungang.size()>0){
                    for (int pos = 0;pos<resZhuanDian.size();pos++){
                        int flag = 1;
                        for (StepSelect jungangTemp : jungang) {
                            if (!isSecondconform(quanChengs,jungangTemp,resZhuanDian.get(pos))){
                                flag = 0;
                                break;
                            }
                        }
                        if (flag==1){
                            secondZhuanDian.add(resZhuanDian.get(pos));
                        }
                    }
                }
            }else{
                if (gangya.size()>0){
                    for (int pos = 0;pos<resZhuanDian.size();pos++){
                        int flag = 1;
                        for (StepSelect gangyaTemp : gangya) {
                            if (!isSecondconform(quanChengs,gangyaTemp,resZhuanDian.get(pos))){
                                flag = 0;
                                break;
                            }
                        }
                        if (flag==1){
                            secondZhuanDian.add(resZhuanDian.get(pos));
                        }
                    }
                }
            }
            //todo 暂时只考虑了两个管，而没有考虑第三个管。这样是有问题的。应当努力解决。
        }
        //在这里的时候其实已经获得了首先的第一步。
        return secondZhuanDian;
    }
    public static List<FiveStepTemplate> GuanYaTemplateTest(List<QuanCheng> quanChengs){
        /*
          按照一般的来说就是肯定是先平，或者先升来着。
          平与升或降交替变换。
          其次刚开始设置为默认的为平。
          当平或者升都需要通过3s来验证。
         */
        int flag_float = 0;
        List<FiveStepTemplate> resGuanYa = new ArrayList<>();
        //两个进行交替
        FiveStepTemplate stepTemplate = new FiveStepTemplate();
        FiveStepTemplate nextStepTemplate = new FiveStepTemplate();
        //0设置为平。
        stepTemplate.setIsStable(0);
        stepTemplate.setStart(quanChengs.get(0).getXuHao());
        stepTemplate.setStartTime(quanChengs.get(0).getDateTime());
        stepTemplate.setStartStress(quanChengs.get(0).getGuanYa().intValue());
        int length = quanChengs.size();
        for (int i =1;i<length-3;i++){
            //分为两种情况，一种是出于平的时候，一种是出于升降的时候。
            if (flag_float==0){
                //当出于平的时候，flag_float是为0，而这个时候寻找到连续的几个升降点。
                if (Math.abs(quanChengs.get(i).getGuanYa()-quanChengs.get(i+1).getGuanYa())>=20
                    &&Math.abs(quanChengs.get(i).getGuanYa()-quanChengs.get(i+2).getGuanYa())>=30
                    &&Math.abs(quanChengs.get(i).getGuanYa()-quanChengs.get(i+3).getGuanYa())>=30 ){
                    //连续的时候超出了一定的数值，就开始判断

                    stepTemplate.setEndTime(quanChengs.get(i-1).getDateTime());
                    stepTemplate.setEnd(quanChengs.get(i-1).getXuHao());
                    stepTemplate.setEndStress(quanChengs.get(i-1).getGuanYa().intValue());
                    stepTemplate.setContinueTime((int) ((stepTemplate.getEndTime().getTime()-stepTemplate.getStartTime().getTime())/1000));

                    if (quanChengs.get(i).getGuanYa()>quanChengs.get(i).getGuanYa()){
                        //下一段相对于自己来说是升还是降，1为升，-1为降。
                        stepTemplate.setNextStart(1);
                    }else {
                        stepTemplate.setNextStart(-1);
                    }
                    //设置下一个节点
                    nextStepTemplate = new FiveStepTemplate();
                    stepTemplate.setNext(nextStepTemplate);
                    resGuanYa.add(stepTemplate);
                    nextStepTemplate.setStartStress(quanChengs.get(i).getGuanYa().intValue());
                    //向前走三步，看是升，还是降。
                    if(quanChengs.get(i).getGuanYa()>quanChengs.get(i+1).getGuanYa() &&
                            quanChengs.get(i+1).getGuanYa()>quanChengs.get(i+2).getGuanYa()){
                        nextStepTemplate.setIsStable(-1);
                    }else {
                        nextStepTemplate.setIsStable(1);
                    }
                    flag_float = 1;
                    nextStepTemplate.setStart(quanChengs.get(i-1).getXuHao());
                    nextStepTemplate.setStartTime(quanChengs.get(i-1).getDateTime());
                }
            }else if (flag_float==1){
                //当出于上升或者下降的时候。
                if (Math.abs(quanChengs.get(i).getGuanYa()-quanChengs.get(i+1).getGuanYa())<=20
                        &&Math.abs(quanChengs.get(i).getGuanYa()-quanChengs.get(i+2).getGuanYa())<=20
                        &&Math.abs(quanChengs.get(i).getGuanYa()-quanChengs.get(i+3).getGuanYa())<=20 ){
                    //设置该节点
                    nextStepTemplate.setEnd(quanChengs.get(i).getXuHao());
                    nextStepTemplate.setEndTime(quanChengs.get(i).getDateTime());
                    nextStepTemplate.setEndStress(quanChengs.get(i).getGuanYa().intValue());
                    flag_float = 0;
                    nextStepTemplate.setContinueTime((int) ((nextStepTemplate.getEndTime().getTime()-nextStepTemplate.getStartTime().getTime())/1000));
                    stepTemplate = new FiveStepTemplate();
                    stepTemplate.setIsStable(0);
                    stepTemplate.setStartTime(quanChengs.get(i).getDateTime());
                    stepTemplate.setStart(quanChengs.get(i).getXuHao());
                    stepTemplate.setStartStress(quanChengs.get(i).getGuanYa().intValue());
                    nextStepTemplate.setNext(stepTemplate);
                    resGuanYa.add(nextStepTemplate);
                }
            }
        }
        //把最后一段加上
        if(nextStepTemplate.getEnd()==null){
            stepTemplate.setEndTime(quanChengs.get(length-1).getDateTime());
            stepTemplate.setEnd(quanChengs.get(length-1).getXuHao());
            stepTemplate.setEndStress(quanChengs.get(length-1).getGuanYa().intValue());
            stepTemplate.setContinueTime((int) ((stepTemplate.getEndTime().getTime()-stepTemplate.getStartTime().getTime())/1000));
            resGuanYa.add(stepTemplate);
        }else {
            nextStepTemplate.setEndTime(quanChengs.get(length-1).getDateTime());
            nextStepTemplate.setEnd(quanChengs.get(length-1).getXuHao());
            nextStepTemplate.setEndStress(quanChengs.get(length-1).getGuanYa().intValue());
            nextStepTemplate.setContinueTime((int) ((nextStepTemplate.getEndTime().getTime()-nextStepTemplate.getStartTime().getTime())/1000));
            resGuanYa.add(nextStepTemplate);
        }
        return resGuanYa;

    }
    public static List<FiveStepTemplate> GangYaTemplateTest(List<QuanCheng> quanChengs){
        /*
          按照一般的来说就是肯定是先平，或者先升来着。
          平与升或降交替变换。
          其次刚开始设置为默认的为平。
          当平或者升都需要通过3s来验证。
         */
        int flag_float = 0;
        List<FiveStepTemplate> resGangYa = new ArrayList<>();
        //两个进行交替
        FiveStepTemplate stepTemplate = new FiveStepTemplate();
        FiveStepTemplate nextStepTemplate = new FiveStepTemplate();
        //0设置为平。
        stepTemplate.setIsStable(0);
        stepTemplate.setStart(quanChengs.get(0).getXuHao());
        stepTemplate.setStartTime(quanChengs.get(0).getDateTime());
        stepTemplate.setStartStress(quanChengs.get(0).getGangYa().intValue());
        int length = quanChengs.size();
        for (int i =1;i<length-3;i++){
            //分为两种情况，一种是出于平的时候，一种是出于升降的时候。
            if (flag_float==0){
                //当出于平的时候，flag_float是为0，而这个时候寻找到连续的几个升降点。
                if (Math.abs(quanChengs.get(i).getGangYa()-quanChengs.get(i+1).getGangYa())>=20
                        &&Math.abs(quanChengs.get(i).getGangYa()-quanChengs.get(i+2).getGangYa())>=30
                        &&Math.abs(quanChengs.get(i).getGangYa()-quanChengs.get(i+3).getGangYa())>=30 ){
                    //连续的时候超出了一定的数值，就开始判断

                    stepTemplate.setEndTime(quanChengs.get(i-1).getDateTime());
                    stepTemplate.setEnd(quanChengs.get(i-1).getXuHao());
                    stepTemplate.setEndStress(quanChengs.get(i-1).getGangYa().intValue());
                    stepTemplate.setContinueTime((int) ((stepTemplate.getEndTime().getTime()-stepTemplate.getStartTime().getTime())/1000));

                    if (quanChengs.get(i).getGangYa()>quanChengs.get(i).getGangYa()){
                        //下一段相对于自己来说是升还是降，1为升，-1为降。
                        stepTemplate.setNextStart(1);
                    }else {
                        stepTemplate.setNextStart(-1);
                    }
                    //设置下一个节点
                    nextStepTemplate = new FiveStepTemplate();
                    stepTemplate.setNext(nextStepTemplate);
                    resGangYa.add(stepTemplate);
                    nextStepTemplate.setStartStress(quanChengs.get(i).getGangYa().intValue());
                    //向前走三步，看是升，还是降。
                    if(quanChengs.get(i).getGangYa()>quanChengs.get(i+1).getGangYa() &&
                            quanChengs.get(i+1).getGangYa()>quanChengs.get(i+2).getGangYa()){
                        nextStepTemplate.setIsStable(-1);
                    }else {
                        nextStepTemplate.setIsStable(1);
                    }
                    flag_float = 1;
                    nextStepTemplate.setStart(quanChengs.get(i-1).getXuHao());
                    nextStepTemplate.setStartTime(quanChengs.get(i-1).getDateTime());
                }
            }else if (flag_float==1){
                //当出于上升或者下降的时候。
                if (Math.abs(quanChengs.get(i).getGangYa()-quanChengs.get(i+1).getGangYa())<=20
                        &&Math.abs(quanChengs.get(i).getGangYa()-quanChengs.get(i+2).getGangYa())<=20
                        &&Math.abs(quanChengs.get(i).getGangYa()-quanChengs.get(i+3).getGangYa())<=20 ){
                    //设置该节点
                    nextStepTemplate.setEnd(quanChengs.get(i).getXuHao());
                    nextStepTemplate.setEndTime(quanChengs.get(i).getDateTime());
                    nextStepTemplate.setEndStress(quanChengs.get(i).getGangYa().intValue());
                    flag_float = 0;
                    nextStepTemplate.setContinueTime((int) ((nextStepTemplate.getEndTime().getTime()-nextStepTemplate.getStartTime().getTime())/1000));
                    stepTemplate = new FiveStepTemplate();
                    stepTemplate.setIsStable(0);
                    stepTemplate.setStartTime(quanChengs.get(i).getDateTime());
                    stepTemplate.setStart(quanChengs.get(i).getXuHao());
                    stepTemplate.setStartStress(quanChengs.get(i).getGangYa().intValue());
                    nextStepTemplate.setNext(stepTemplate);
                    resGangYa.add(nextStepTemplate);
                }
            }
        }
        //把最后一段加上
        if(nextStepTemplate.getEnd()==null){
            stepTemplate.setEndTime(quanChengs.get(length-1).getDateTime());
            stepTemplate.setEnd(quanChengs.get(length-1).getXuHao());
            stepTemplate.setEndStress(quanChengs.get(length-1).getGangYa().intValue());
            stepTemplate.setContinueTime((int) ((stepTemplate.getEndTime().getTime()-stepTemplate.getStartTime().getTime())/1000));
            resGangYa.add(stepTemplate);
        }else {
            nextStepTemplate.setEndTime(quanChengs.get(length-1).getDateTime());
            nextStepTemplate.setEnd(quanChengs.get(length-1).getXuHao());
            nextStepTemplate.setEndStress(quanChengs.get(length-1).getGangYa().intValue());
            nextStepTemplate.setContinueTime((int) ((nextStepTemplate.getEndTime().getTime()-nextStepTemplate.getStartTime().getTime())/1000));
            resGangYa.add(nextStepTemplate);
        }
        return resGangYa;

    }
    public static List<FiveStepTemplate> JunGang1TemplateTest(List<QuanCheng> quanChengs){
        /*
          按照一般的来说就是肯定是先平，或者先升来着。
          平与升或降交替变换。
          其次刚开始设置为默认的为平。
          当平或者升都需要通过3s来验证。
         */
        int flag_float = 0;
        List<FiveStepTemplate> resJunGang1 = new ArrayList<>();
        //两个进行交替
        FiveStepTemplate stepTemplate = new FiveStepTemplate();
        FiveStepTemplate nextStepTemplate = new FiveStepTemplate();
        //0设置为平。
        stepTemplate.setIsStable(0);
        stepTemplate.setStart(quanChengs.get(0).getXuHao());
        stepTemplate.setStartTime(quanChengs.get(0).getDateTime());
        stepTemplate.setStartStress(quanChengs.get(0).getJunGang1().intValue());
        int length = quanChengs.size();
        for (int i =1;i<length-3;i++){
            //分为两种情况，一种是出于平的时候，一种是出于升降的时候。
            if (flag_float==0){
                //当出于平的时候，flag_float是为0，而这个时候寻找到连续的几个升降点。
                if (Math.abs(quanChengs.get(i).getJunGang1()-quanChengs.get(i+1).getJunGang1())>=20
                        &&Math.abs(quanChengs.get(i).getJunGang1()-quanChengs.get(i+2).getJunGang1())>=30
                        &&Math.abs(quanChengs.get(i).getJunGang1()-quanChengs.get(i+3).getJunGang1())>=30 ){
                    //连续的时候超出了一定的数值，就开始判断

                    stepTemplate.setEndTime(quanChengs.get(i-1).getDateTime());
                    stepTemplate.setEnd(quanChengs.get(i-1).getXuHao());
                    stepTemplate.setEndStress(quanChengs.get(i-1).getJunGang1().intValue());
                    stepTemplate.setContinueTime((int) ((stepTemplate.getEndTime().getTime()-stepTemplate.getStartTime().getTime())/1000));

                    if (quanChengs.get(i).getJunGang1()>quanChengs.get(i).getJunGang1()){
                        //下一段相对于自己来说是升还是降，1为升，-1为降。
                        stepTemplate.setNextStart(1);
                    }else {
                        stepTemplate.setNextStart(-1);
                    }
                    //设置下一个节点
                    nextStepTemplate = new FiveStepTemplate();
                    stepTemplate.setNext(nextStepTemplate);
                    resJunGang1.add(stepTemplate);
                    nextStepTemplate.setStartStress(quanChengs.get(i).getJunGang1().intValue());
                    //向前走三步，看是升，还是降。
                    if(quanChengs.get(i).getJunGang1()>quanChengs.get(i+1).getJunGang1() &&
                            quanChengs.get(i+1).getJunGang1()>quanChengs.get(i+2).getJunGang1()){
                        nextStepTemplate.setIsStable(-1);
                    }else {
                        nextStepTemplate.setIsStable(1);
                    }
                    flag_float = 1;
                    nextStepTemplate.setStart(quanChengs.get(i-1).getXuHao());
                    nextStepTemplate.setStartTime(quanChengs.get(i-1).getDateTime());
                }
            }else if (flag_float==1){
                //当出于上升或者下降的时候。
                if (Math.abs(quanChengs.get(i).getJunGang1()-quanChengs.get(i+1).getJunGang1())<=20
                        &&Math.abs(quanChengs.get(i).getJunGang1()-quanChengs.get(i+2).getJunGang1())<=20
                        &&Math.abs(quanChengs.get(i).getJunGang1()-quanChengs.get(i+3).getJunGang1())<=20 ){
                    //设置该节点
                    nextStepTemplate.setEnd(quanChengs.get(i).getXuHao());
                    nextStepTemplate.setEndTime(quanChengs.get(i).getDateTime());
                    nextStepTemplate.setEndStress(quanChengs.get(i).getJunGang1().intValue());
                    flag_float = 0;
                    nextStepTemplate.setContinueTime((int) ((nextStepTemplate.getEndTime().getTime()-nextStepTemplate.getStartTime().getTime())/1000));
                    stepTemplate = new FiveStepTemplate();
                    stepTemplate.setIsStable(0);
                    stepTemplate.setStartTime(quanChengs.get(i).getDateTime());
                    stepTemplate.setStart(quanChengs.get(i).getXuHao());
                    stepTemplate.setStartStress(quanChengs.get(i).getJunGang1().intValue());
                    nextStepTemplate.setNext(stepTemplate);
                    resJunGang1.add(nextStepTemplate);
                }
            }
        }
        //把最后一段加上
        if(nextStepTemplate.getEnd()==null){
            stepTemplate.setEndTime(quanChengs.get(length-1).getDateTime());
            stepTemplate.setEnd(quanChengs.get(length-1).getXuHao());
            stepTemplate.setEndStress(quanChengs.get(length-1).getJunGang1().intValue());
            stepTemplate.setContinueTime((int) ((stepTemplate.getEndTime().getTime()-stepTemplate.getStartTime().getTime())/1000));
            resJunGang1.add(stepTemplate);
        }else {
            nextStepTemplate.setEndTime(quanChengs.get(length-1).getDateTime());
            nextStepTemplate.setEnd(quanChengs.get(length-1).getXuHao());
            nextStepTemplate.setEndStress(quanChengs.get(length-1).getJunGang1().intValue());
            nextStepTemplate.setContinueTime((int) ((nextStepTemplate.getEndTime().getTime()-nextStepTemplate.getStartTime().getTime())/1000));
            resJunGang1.add(nextStepTemplate);
        }
        return resJunGang1;

    }
    public static List<FiveStepTemplate> JunGang2TemplateTest(List<QuanCheng> quanChengs){
        /*
          按照一般的来说就是肯定是先平，或者先升来着。
          平与升或降交替变换。
          其次刚开始设置为默认的为平。
          当平或者升都需要通过3s来验证。
         */
        int flag_float = 0;
        List<FiveStepTemplate> resJunGang2 = new ArrayList<>();
        //两个进行交替
        FiveStepTemplate stepTemplate = new FiveStepTemplate();
        FiveStepTemplate nextStepTemplate = new FiveStepTemplate();
        //0设置为平。
        stepTemplate.setIsStable(0);
        stepTemplate.setStart(quanChengs.get(0).getXuHao());
        stepTemplate.setStartTime(quanChengs.get(0).getDateTime());
        stepTemplate.setStartStress(quanChengs.get(0).getJunGang2().intValue());
        int length = quanChengs.size();
        for (int i =1;i<length-3;i++){
            //分为两种情况，一种是出于平的时候，一种是出于升降的时候。
            if (flag_float==0){
                //当出于平的时候，flag_float是为0，而这个时候寻找到连续的几个升降点。
                if (Math.abs(quanChengs.get(i).getJunGang2()-quanChengs.get(i+1).getJunGang2())>=20
                        &&Math.abs(quanChengs.get(i).getJunGang2()-quanChengs.get(i+2).getJunGang2())>=30
                        &&Math.abs(quanChengs.get(i).getJunGang2()-quanChengs.get(i+3).getJunGang2())>=30 ){
                    //连续的时候超出了一定的数值，就开始判断

                    stepTemplate.setEndTime(quanChengs.get(i-1).getDateTime());
                    stepTemplate.setEnd(quanChengs.get(i-1).getXuHao());
                    stepTemplate.setEndStress(quanChengs.get(i-1).getJunGang2().intValue());
                    stepTemplate.setContinueTime((int) ((stepTemplate.getEndTime().getTime()-stepTemplate.getStartTime().getTime())/1000));

                    if (quanChengs.get(i).getJunGang2()>quanChengs.get(i).getJunGang2()){
                        //下一段相对于自己来说是升还是降，1为升，-1为降。
                        stepTemplate.setNextStart(1);
                    }else {
                        stepTemplate.setNextStart(-1);
                    }
                    //设置下一个节点
                    nextStepTemplate = new FiveStepTemplate();
                    stepTemplate.setNext(nextStepTemplate);
                    resJunGang2.add(stepTemplate);
                    nextStepTemplate.setStartStress(quanChengs.get(i).getJunGang2().intValue());
                    //向前走三步，看是升，还是降。
                    if(quanChengs.get(i).getJunGang2()>quanChengs.get(i+1).getJunGang2() &&
                            quanChengs.get(i+1).getJunGang2()>quanChengs.get(i+2).getJunGang2()){
                        nextStepTemplate.setIsStable(-1);
                    }else {
                        nextStepTemplate.setIsStable(1);
                    }
                    flag_float = 1;
                    nextStepTemplate.setStart(quanChengs.get(i-1).getXuHao());
                    nextStepTemplate.setStartTime(quanChengs.get(i-1).getDateTime());
                }
            }else if (flag_float==1){
                //当出于上升或者下降的时候。
                if (Math.abs(quanChengs.get(i).getJunGang2()-quanChengs.get(i+1).getJunGang2())<=20
                        &&Math.abs(quanChengs.get(i).getJunGang2()-quanChengs.get(i+2).getJunGang2())<=20
                        &&Math.abs(quanChengs.get(i).getJunGang2()-quanChengs.get(i+3).getJunGang2())<=20 ){
                    //设置该节点
                    nextStepTemplate.setEnd(quanChengs.get(i).getXuHao());
                    nextStepTemplate.setEndTime(quanChengs.get(i).getDateTime());
                    nextStepTemplate.setEndStress(quanChengs.get(i).getJunGang2().intValue());
                    flag_float = 0;
                    nextStepTemplate.setContinueTime((int) ((nextStepTemplate.getEndTime().getTime()-nextStepTemplate.getStartTime().getTime())/1000));
                    stepTemplate = new FiveStepTemplate();
                    stepTemplate.setIsStable(0);
                    stepTemplate.setStartTime(quanChengs.get(i).getDateTime());
                    stepTemplate.setStart(quanChengs.get(i).getXuHao());
                    stepTemplate.setStartStress(quanChengs.get(i).getJunGang2().intValue());
                    nextStepTemplate.setNext(stepTemplate);
                    resJunGang2.add(nextStepTemplate);
                }
            }
        }
        //把最后一段加上
        if(nextStepTemplate.getEnd()==null){
            stepTemplate.setEndTime(quanChengs.get(length-1).getDateTime());
            stepTemplate.setEnd(quanChengs.get(length-1).getXuHao());
            stepTemplate.setEndStress(quanChengs.get(length-1).getJunGang2().intValue());
            stepTemplate.setContinueTime((int) ((stepTemplate.getEndTime().getTime()-stepTemplate.getStartTime().getTime())/1000));
            resJunGang2.add(stepTemplate);
        }else {
            nextStepTemplate.setEndTime(quanChengs.get(length-1).getDateTime());
            nextStepTemplate.setEnd(quanChengs.get(length-1).getXuHao());
            nextStepTemplate.setEndStress(quanChengs.get(length-1).getJunGang2().intValue());
            nextStepTemplate.setContinueTime((int) ((nextStepTemplate.getEndTime().getTime()-nextStepTemplate.getStartTime().getTime())/1000));
            resJunGang2.add(nextStepTemplate);
        }
        return resJunGang2;

    }
    public static List<List<Integer>> getStartAndEnd(List<QuanCheng>quanChengs,List<List<Integer>>startEnd,StepSelect stepSelect){
        int length = quanChengs.size();
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0;i<startEnd.size();i++){
            List<Integer> startAndEnd = startEnd.get(i);
            int start = startAndEnd.get(0);
            int end = startAndEnd.get(1)+1;
            while (end<length){
                if (quanChengs.get(end).getGuanYa()>=stepSelect.getMinStress()
                &&quanChengs.get(end).getGuanYa()<=stepSelect.getMaxStress()){
                    end++;
                }
                else {
                    //需不需要再往前走走看看。以防是
                    //todo 往前走走
                    break;
                }
            }
            if (end-startAndEnd.get(1)>=stepSelect.getMinTime()
                &&end-startAndEnd.get(1)<=stepSelect.getMaxTime()){
                List<Integer> temps = new ArrayList<>();
                temps.add(startAndEnd.get(1)+1);
                temps.add(end-1);
                res.add(temps);
            }
        }
        return res;
    }
    public static List<List<Integer>> FirstShaiXuan(List<QuanCheng> quanChengs,StepSelect first){
        /*
            首次
         */
        int i = 0;
        int length = quanChengs.size();
        List<List<Integer>> res  = new ArrayList<>();
        if (first.getStressName().equals("管")){

            while (i<length){
                if (quanChengs.get(i).getGuanYa()<=first.getMaxStress() &&
                        quanChengs.get(i).getGuanYa()>=first.getMinStress()){
                    //假设在这个区间
                    int flag = i+1;
                    while (flag<length){
                        if (quanChengs.get(flag).getGuanYa()>first.getMaxStress() ||
                                quanChengs.get(flag).getGuanYa()<first.getMinStress()){
                            break;
                        }
                        flag++;
                    }
                    int time = flag-i;
                    if (time>=first.getMinTime()&& time<=first.getMaxTime()){
                        //假设时间上也满足的话。那么就开始进入下一步。

                        List<Integer> temps = new ArrayList<>();
                        temps.add(i);
                        temps.add(flag);
                        res.add(temps);
                        i= flag;
                    }
                }
                i++;
            }
        }else if(first.getStressName().equals("均")){
            while (i<length){
                if (quanChengs.get(i).getJunGang1()<=first.getMaxStress() &&
                        quanChengs.get(i).getJunGang1()>=first.getMinStress()){
                    //假设在这个区间
                    int flag = i+1;
                    while (flag<length){
                        if (quanChengs.get(flag).getJunGang1()>first.getMaxStress() ||
                                quanChengs.get(flag).getJunGang1()<first.getMinStress()){
                            break;
                        }
                        flag++;
                    }
                    int time = flag-i;
                    if (time>=first.getMinTime()&& time<=first.getMaxTime()){
                        //假设时间上也满足的话。那么就开始进入下一步。

                        List<Integer> temps = new ArrayList<>();
                        temps.add(i);
                        temps.add(flag);
                        res.add(temps);
                        i= flag;
                    }
                }
                i++;
            }
            if (res.size()==0){
                //如果均缸1为空，就用均缸2试试
                while (i<length){
                    if (quanChengs.get(i).getJunGang2()<=first.getMaxStress() &&
                            quanChengs.get(i).getJunGang2()>=first.getMinStress()){
                        //假设在这个区间
                        int flag = i+1;
                        while (flag<length){
                            if (quanChengs.get(flag).getJunGang2()>first.getMaxStress() ||
                                    quanChengs.get(flag).getJunGang2()<first.getMinStress()){
                                break;
                            }
                            flag++;
                        }
                        int time = flag-i;
                        if (time>=first.getMinTime()&& time<=first.getMaxTime()){
                            //假设时间上也满足的话。那么就开始进入下一步。

                            List<Integer> temps = new ArrayList<>();
                            temps.add(i);
                            temps.add(flag);
                            res.add(temps);
                            i= flag;
                        }
                    }
                    i++;
                }
            }
        }else {
            while (i<length){
                if (quanChengs.get(i).getGangYa()<=first.getMaxStress() &&
                        quanChengs.get(i).getGangYa()>=first.getMinStress()){
                    //假设在这个区间
                    int flag = i+1;
                    while (flag<length){
                        if (quanChengs.get(flag).getGangYa()>first.getMaxStress() ||
                                quanChengs.get(flag).getGangYa()<first.getMinStress()){
                            break;
                        }
                        flag++;
                    }
                    int time = flag-i;
                    if (time>=first.getMinTime()&& time<=first.getMaxTime()){
                        //假设时间上也满足的话。那么就开始进入下一步。

                        List<Integer> temps = new ArrayList<>();
                        temps.add(i);
                        temps.add(flag);
                        res.add(temps);
                        i= flag;
                    }
                }
                i++;
            }
        }
        return res;
    }
    public static List<List<ZhuanDian>>getZhuanDian(List<List<Integer>>res,List<QuanCheng>quanChengs,List<StepSelect> stepSelects){
        /*
            此类主要是为了寻找到相对应的点，并且一一对应上。
         */
        List<List<ZhuanDian>> zhuanDianRes = new ArrayList<>();
        for (int i = 0;i<res.size();i++){
            List<ZhuanDian>zhuanDians = new ArrayList<>();
            //获取结束的标记
            Integer end = res.get(i).get(1);
            for (int j = stepSelects.size()-1;j>=0;j--){
                //从后往前开始推。
                StepSelect stepSelect = stepSelects.get(j);
                ZhuanDian zhuanDian = new ZhuanDian();
                if (j!=stepSelects.size()-1){
                    //当他不等于最后一个的时候，就可以进行相对应的截取的点
                    zhuanDian.setEndTime(quanChengs.get(end).getDateTime());
                    zhuanDian.setEndPos(end);
                    zhuanDian.setRightStress(getStressNumber(quanChengs.get(end),stepSelect));
                    zhuanDian.setStepSelectId(stepSelect.getId());
                }
                int maxStress = getStressNumber(quanChengs.get(end),stepSelect);
                Date maxTime = quanChengs.get(end).getDateTime();
                int minStress = maxStress;
                Date minTime = quanChengs.get(end).getDateTime();
                while (end>0){
                    if (isStressSection(quanChengs.get(end),stepSelect)){
                        if (getStressNumber(quanChengs.get(end),stepSelect)>maxStress){
                            maxStress = getStressNumber(quanChengs.get(end),stepSelect);
                            maxTime = quanChengs.get(end).getDateTime();
                        }

                        if (getStressNumber(quanChengs.get(end),stepSelect)<minStress){
                            minStress = getStressNumber(quanChengs.get(end),stepSelect);
                            minTime = quanChengs.get(end).getDateTime();
                        }
                        end--;
                    }
                    else
                        break;
                }
                int start = end +1;
                if (j==stepSelects.size()-1){
                    int startFirst = start+stepSelect.getMinTime();
                    zhuanDian.setEndTime(quanChengs.get(startFirst).getDateTime());
                    zhuanDian.setRightStress(getStressNumber(quanChengs.get(startFirst),stepSelect));
                    zhuanDian.setStepSelectId(stepSelect.getId());
                    zhuanDian.setEndPos(startFirst);
               //要在这里加一个startpos 和endpos
                }
                zhuanDian.setStartTime(quanChengs.get(start).getDateTime());
                zhuanDian.setStartPos(start);
                zhuanDian.setLeftStress(getStressNumber(quanChengs.get(start),stepSelect));
                zhuanDian.setMaxTime(maxTime);
                zhuanDian.setMinTime(minTime);
                zhuanDian.setMaxStress(maxStress);
                zhuanDian.setMinStress(minStress);
                //将其进行存储，带出去。
                //还有关于全程文件的fileid
                zhuanDian.setFileId(quanChengs.get(0).getFileId());
                zhuanDians.add(zhuanDian);
            }
            zhuanDianRes.add(zhuanDians);
        }
        //在每个转点这里应该设置一个优先级。以此后面可进行判断。
        //数字越小，优先级越高
        int duan_prior = 0;
        for(int i=0;i<zhuanDianRes.size();i++){
            int flag_prior = 1;
            duan_prior++;
            for (int j=0;j<zhuanDianRes.get(i).size();j++){
                zhuanDianRes.get(i).get(j).setPriorNumber(flag_prior);
                zhuanDianRes.get(i).get(j).setDuanPrior(duan_prior);
                flag_prior++;
            }
        }
        return zhuanDianRes;
    }
    public static Integer getStressNumber(QuanCheng quanCheng,StepSelect stepSelect){
        if(stepSelect.getStressName().equals("管")){
            return quanCheng.getGuanYa();
        }else if (stepSelect.getStressName().equals("均")){
            return quanCheng.getJunGang1();
        }else {
            return quanCheng.getGangYa();
        }
    }
    public static boolean isStressSection(QuanCheng quanCheng,StepSelect select){
        /*
        判断是否属于这个区间之内。
        */
        if(select.getStressName().equals("管")){
            if (quanCheng.getGuanYa()>=select.getMinStress()&&quanCheng.getGuanYa()<=select.getMaxStress()){
                return true;
            }
        }else if (select.getStressName().equals("均")){
            if (quanCheng.getJunGang1()>=select.getMinStress()&&quanCheng.getJunGang1()<=select.getMaxStress()){
                return true;
            }
        }else {
            if (quanCheng.getGangYa()>=select.getMinStress()&&quanCheng.getGangYa()<=select.getMaxStress()){
                return true;
            }
        }
        return false;
    }
    public static boolean isSecondconform(List<QuanCheng> quanChengs,StepSelect select,List<ZhuanDian> zhuanDians){
        if (select.getIsDepend()!=-1){
            //如果有依靠
            int start = select.getStartId();
            int end = select.getEndID();
            ZhuanDian startZhuanDian =zhuanDians.get(start-1);
            ZhuanDian endZhuanDian = zhuanDians.get(end-1);
//            for (ZhuanDian zhuanDian : zhuanDians) {
//                if (start==zhuanDian.getStepSelectId()){
//                    startZhuanDian = zhuanDian;
//                }
//                if (end==zhuanDian.getStepSelectId()){
//                    endZhuanDian=zhuanDian;
//                }
//            }
            int flag = 0;
            for(int p = startZhuanDian.getStartPos();p<=endZhuanDian.getEndPos();p++){
                if (getStressNumber(quanChengs.get(p),select)>select.getMaxStress()
                        || getStressNumber(quanChengs.get(p),select)<select.getMinStress()) {
                    //如果在这里面就没关系。
                    if (Math.abs(getStressNumber(quanChengs.get(p),select)-select.getMaxStress())<20||
                            Math.abs(select.getMinStress()-getStressNumber(quanChengs.get(p),select))<20){
                        flag=1;
                    }else {
                        flag = 0;
                        break;
                    }

                }else {
                    flag=1;

                }
            }
            if (flag==1){
                return true;
            }else
                return false;
        }
        return false;
    }

    public static List<List<QuanCheng>> spliteQuanCheng(List<QuanCheng> quanChengs){
        /*
            分割全程记录,根据单双端检测。
         */
        List<List<QuanCheng>>  res = new ArrayList<>();
        //在前面进来的时候是确实要判断是不是双端。
        int length = res.size();
        //取一半左右。
        int flag = length/2 - 1;
        for (int i = length/2;i<length;i++){
            if (quanChengs.get(i).getEvent().contains("巡检")){
                flag = i;
                break;
            }
        }
        if (flag != (length/2-1)){
            List<QuanCheng> firstQuanCheng = new ArrayList<>();
            List<QuanCheng> secondQuanCheng = new ArrayList<>();
            for (int j = 0;j<length;j++ ){
                if (j<flag+10){
                    firstQuanCheng.add(quanChengs.get(j));
                }else {
                    secondQuanCheng.add(quanChengs.get(j));
                }
            }
            res.add(firstQuanCheng);
            res.add(secondQuanCheng);
            return res;
        }else {
            res.add(quanChengs);
            return res;
        }
    }
}
