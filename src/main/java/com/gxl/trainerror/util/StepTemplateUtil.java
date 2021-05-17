package com.gxl.trainerror.util;

import com.gxl.trainerror.bean.FileInfo;
import com.gxl.trainerror.bean.FiveStepTemplate;
import com.gxl.trainerror.bean.QuanCheng;
import com.gxl.trainerror.bean.StepSelect;

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
    public static List<FiveStepTemplate> GuanYaTemplateAnalysis(List<QuanCheng> quanChengs){
        //传入的数据为已经格式化的时间，然后进行划分
        //划分的按照
        /*
        管压 缸压 均缸1  均缸2
         */
        int flag_first_show = 1;
        List<FiveStepTemplate> resGuanYa = new ArrayList<>();
        FiveStepTemplate fiveStepTemplate = new FiveStepTemplate();
        FiveStepTemplate nextfiveStep = new FiveStepTemplate();
        fiveStepTemplate.setStart(quanChengs.get(0).getXuHao());
        fiveStepTemplate.setStartStress(quanChengs.get(0).getGuanYa().intValue());
        fiveStepTemplate.setStartTime(quanChengs.get(0).getDateTime());
        fiveStepTemplate.setIsStable(0);
        int last_guanya = quanChengs.get(0).getGuanYa();
        int length = quanChengs.size();
        int last_pos = 0;
        //初始化的时候，可以往前走三秒，看是稳定还是下降
//        if (Math.abs(quanChengs.get(0).getGuanYa()-quanChengs.get(1).getGuanYa())<20)
//        if ()
        for (int i = 0;i<length-3;i++) {

            if (Math.abs(quanChengs.get(i).getGuanYa()-last_guanya)>20 && flag_first_show == 1){
                //假设误差在20以上
                //这里代表的是节点
                fiveStepTemplate.setEndTime(quanChengs.get(i-1).getDateTime());
                fiveStepTemplate.setEnd(quanChengs.get(i-1).getXuHao());
                fiveStepTemplate.setEndStress(quanChengs.get(i-1).getGuanYa().intValue());
                if (last_guanya>quanChengs.get(i).getGuanYa()){
                    //下一段相对于自己来说是升还是降，1为升，-1为降。
                    fiveStepTemplate.setNextStart(1);
                }else {
                    fiveStepTemplate.setNextStart(-1);
                }
                nextfiveStep = new FiveStepTemplate();
                fiveStepTemplate.setNext(nextfiveStep);
                fiveStepTemplate.setContinueTime((int) ((fiveStepTemplate.getEndTime().getTime()-fiveStepTemplate.getStartTime().getTime())/1000));

                resGuanYa.add(fiveStepTemplate);
                nextfiveStep.setStartStress(quanChengs.get(i).getGuanYa().intValue());
                //向前走三步，看是升，还是降。
                if(quanChengs.get(i).getGuanYa()>quanChengs.get(i+1).getGuanYa() &&
                        quanChengs.get(i+1).getGuanYa()>quanChengs.get(i+2).getGuanYa()){
                    nextfiveStep.setIsStable(-1);
                }else {
                    nextfiveStep.setIsStable(1);
                }
                flag_first_show = 0;
                nextfiveStep.setStart(quanChengs.get(i).getXuHao());
                nextfiveStep.setStartTime(quanChengs.get(i).getDateTime());

            }else{
                //遇到稳定的时候，就开始往前走一步开始修改
                //在下降或者上升的时候，序号不应该相等，这个时候就可以进行一个序号的判断。
                if(Math.abs(quanChengs.get(i).getGuanYa()-last_guanya)<20 && flag_first_show==0
                && Math.abs(quanChengs.get(i-2).getGuanYa()-quanChengs.get(i+1).getGuanYa())>20 &&
                        Math.abs(quanChengs.get(i+1).getGuanYa()-quanChengs.get(i).getGuanYa())<20 &&
                        Math.abs(quanChengs.get(i+2).getGuanYa()-quanChengs.get(i+1).getGuanYa())<20){
                    nextfiveStep.setEnd(quanChengs.get(i-2).getXuHao());
                    nextfiveStep.setEndTime(quanChengs.get(i-2).getDateTime());
                    nextfiveStep.setEndStress(quanChengs.get(i-2).getGuanYa().intValue());
                    flag_first_show = 1;
                    fiveStepTemplate = new FiveStepTemplate();
                    fiveStepTemplate.setIsStable(0);
                    fiveStepTemplate.setStartTime(quanChengs.get(i-1).getDateTime());
                    fiveStepTemplate.setStart(quanChengs.get(i-1).getXuHao());
                    fiveStepTemplate.setStartStress(quanChengs.get(i-1).getGuanYa().intValue());
                    nextfiveStep.setNext(fiveStepTemplate);
                    nextfiveStep.setContinueTime((int) ((nextfiveStep.getEndTime().getTime()-nextfiveStep.getStartTime().getTime())/1000));
                    resGuanYa.add(nextfiveStep);
                }
            }
            last_guanya = quanChengs.get(i).getGuanYa();
        }
        return resGuanYa;
    }

    public static List<FiveStepTemplate> GangYaTemplateAnalysis(List<QuanCheng> quanChengs){
        //传入的数据为已经格式化的时间，然后进行划分
        //划分的按照
        /*
        管压 缸压 均缸1  均缸2
         */
        int flag_first_show = 1;
        List<FiveStepTemplate> resGangYa = new ArrayList<>();
        FiveStepTemplate fiveStepTemplate = new FiveStepTemplate();
        FiveStepTemplate nextfiveStep = new FiveStepTemplate();
        fiveStepTemplate.setStart(quanChengs.get(0).getXuHao());
        fiveStepTemplate.setStartStress(quanChengs.get(0).getGangYa().intValue());
        fiveStepTemplate.setStartTime(quanChengs.get(0).getDateTime());
        int last_guanya = quanChengs.get(0).getGangYa();
        int length = quanChengs.size();
        fiveStepTemplate.setIsStable(0);//初始化为稳定，后面再看。
        //初始化的时候，可以往前走三秒，看是稳定还是下降
//        if (Math.abs(quanChengs.get(0).getGuanYa()-quanChengs.get(1).getGuanYa())<20)
        for (int i = 0;i<length;i++) {

            if (Math.abs(quanChengs.get(i).getGangYa()-last_guanya)>20 && flag_first_show == 1){
                //假设误差在20以上
                //这里代表的是节点
                fiveStepTemplate.setEndTime(quanChengs.get(i-1).getDateTime());
                fiveStepTemplate.setEnd(quanChengs.get(i-1).getXuHao());
                fiveStepTemplate.setEndStress(quanChengs.get(i-1).getGangYa().intValue());
                if (last_guanya>quanChengs.get(i).getGangYa()){
                    //下一段相对于自己来说是升还是降，1为升，-1为降。
                    fiveStepTemplate.setNextStart(1);
                }else {
                    fiveStepTemplate.setNextStart(-1);
                }
                nextfiveStep = new FiveStepTemplate();
                fiveStepTemplate.setNext(nextfiveStep);
                fiveStepTemplate.setContinueTime((int) ((fiveStepTemplate.getEndTime().getTime()-fiveStepTemplate.getStartTime().getTime())/1000));

                resGangYa.add(fiveStepTemplate);
                nextfiveStep.setStartStress(quanChengs.get(i).getGangYa().intValue());
                //向前走三步，看是升，还是降。
                if(quanChengs.get(i).getGangYa()>quanChengs.get(i+1).getGangYa() &&
                        quanChengs.get(i+1).getGangYa()>quanChengs.get(i+2).getGangYa()){
                    nextfiveStep.setIsStable(-1);
                }else {
                    nextfiveStep.setIsStable(1);
                }
                flag_first_show = 0;
                nextfiveStep.setStart(quanChengs.get(i).getXuHao());
                nextfiveStep.setStartTime(quanChengs.get(i).getDateTime());

            }else{
                //遇到稳定的时候，就开始往前走一步开始修改
                //在下降或者上升的时候，序号不应该相等，这个时候就可以进行一个序号的判断。
                if(Math.abs(quanChengs.get(i).getGangYa()-last_guanya)<20 && flag_first_show==0 && !quanChengs.get(i).getXuHao().equals(quanChengs.get(i-1).getXuHao())){
                    nextfiveStep.setEnd(quanChengs.get(i-2).getXuHao());
                    nextfiveStep.setEndTime(quanChengs.get(i-2).getDateTime());
                    nextfiveStep.setEndStress(quanChengs.get(i-2).getGangYa().intValue());
                    flag_first_show = 1;
                    fiveStepTemplate = new FiveStepTemplate();
                    fiveStepTemplate.setIsStable(0);
                    fiveStepTemplate.setStartTime(quanChengs.get(i-1).getDateTime());
                    fiveStepTemplate.setStart(quanChengs.get(i-1).getXuHao());
                    fiveStepTemplate.setStartStress(quanChengs.get(i-1).getGangYa().intValue());
                    nextfiveStep.setNext(fiveStepTemplate);
                    nextfiveStep.setContinueTime((int) ((nextfiveStep.getEndTime().getTime()-nextfiveStep.getStartTime().getTime())/1000));
                    resGangYa.add(nextfiveStep);
                }
            }
            last_guanya = quanChengs.get(i).getGangYa();
        }
        return resGangYa;
    }

    public static List<FiveStepTemplate> JunGang1TemplateAnalysis(List<QuanCheng> quanChengs){
        //传入的数据为已经格式化的时间，然后进行划分
        //划分的按照
        /*
        管压 缸压 均缸1  均缸2
         */
        int flag_first_show = 1;
        List<FiveStepTemplate> resJunGang1 = new ArrayList<>();
        FiveStepTemplate fiveStepTemplate = new FiveStepTemplate();
        FiveStepTemplate nextfiveStep = new FiveStepTemplate();
        fiveStepTemplate.setStart(quanChengs.get(0).getXuHao());
        fiveStepTemplate.setStartStress(quanChengs.get(0).getJunGang1().intValue());
        fiveStepTemplate.setStartTime(quanChengs.get(0).getDateTime());
        int last_guanya = quanChengs.get(0).getJunGang1();
        int length = quanChengs.size();
        fiveStepTemplate.setIsStable(0);//初始化为稳定，后面再看。
        //初始化的时候，可以往前走三秒，看是稳定还是下降
//        if (Math.abs(quanChengs.get(0).getGuanYa()-quanChengs.get(1).getGuanYa())<20)
        for (int i = 0;i<length;i++) {

            if (Math.abs(quanChengs.get(i).getJunGang1()-last_guanya)>20 && flag_first_show == 1){
                //假设误差在20以上
                //这里代表的是节点
                fiveStepTemplate.setEndTime(quanChengs.get(i-1).getDateTime());
                fiveStepTemplate.setEnd(quanChengs.get(i-1).getXuHao());
                fiveStepTemplate.setEndStress(quanChengs.get(i-1).getJunGang1().intValue());
                if (last_guanya>quanChengs.get(i).getJunGang1()){
                    //下一段相对于自己来说是升还是降，1为升，-1为降。
                    fiveStepTemplate.setNextStart(1);
                }else {
                    fiveStepTemplate.setNextStart(-1);
                }
                nextfiveStep = new FiveStepTemplate();
                fiveStepTemplate.setNext(nextfiveStep);
                fiveStepTemplate.setContinueTime((int) ((fiveStepTemplate.getEndTime().getTime()-fiveStepTemplate.getStartTime().getTime())/1000));

                resJunGang1.add(fiveStepTemplate);
                nextfiveStep.setStartStress(quanChengs.get(i).getJunGang1().intValue());
                //向前走三步，看是升，还是降。
                if(quanChengs.get(i).getJunGang1()>quanChengs.get(i+1).getJunGang1() &&
                        quanChengs.get(i+1).getJunGang1()>quanChengs.get(i+2).getJunGang1()){
                    nextfiveStep.setIsStable(-1);
                }else {
                    nextfiveStep.setIsStable(1);
                }
                flag_first_show = 0;
                nextfiveStep.setStart(quanChengs.get(i).getXuHao());
                nextfiveStep.setStartTime(quanChengs.get(i).getDateTime());

            }else{
                //遇到稳定的时候，就开始往前走一步开始修改
                //在下降或者上升的时候，序号不应该相等，这个时候就可以进行一个序号的判断。
                if(Math.abs(quanChengs.get(i).getJunGang1()-last_guanya)<20 && flag_first_show==0 && !quanChengs.get(i).getXuHao().equals(quanChengs.get(i-1).getXuHao())){
                    nextfiveStep.setEnd(quanChengs.get(i-2).getXuHao());
                    nextfiveStep.setEndTime(quanChengs.get(i-2).getDateTime());
                    nextfiveStep.setEndStress(quanChengs.get(i-2).getJunGang1().intValue());
                    flag_first_show = 1;
                    fiveStepTemplate = new FiveStepTemplate();
                    fiveStepTemplate.setIsStable(0);
                    fiveStepTemplate.setStartTime(quanChengs.get(i-1).getDateTime());
                    fiveStepTemplate.setStart(quanChengs.get(i-1).getXuHao());
                    fiveStepTemplate.setStartStress(quanChengs.get(i-1).getJunGang1().intValue());
                    nextfiveStep.setNext(fiveStepTemplate);
                    nextfiveStep.setContinueTime((int) ((nextfiveStep.getEndTime().getTime()-nextfiveStep.getStartTime().getTime())/1000));
                    resJunGang1.add(nextfiveStep);
                }
            }
            last_guanya = quanChengs.get(i).getJunGang1();
        }
        return resJunGang1;
    }
    public static List<FiveStepTemplate> JunGang2TemplateAnalysis(List<QuanCheng> quanChengs){
        //传入的数据为已经格式化的时间，然后进行划分
        //划分的按照
        /*
        管压 缸压 均缸1  均缸2
         */
        int flag_first_show = 1;
        List<FiveStepTemplate> resJunGang2 = new ArrayList<>();
        FiveStepTemplate fiveStepTemplate = new FiveStepTemplate();
        FiveStepTemplate nextfiveStep = new FiveStepTemplate();
        fiveStepTemplate.setStart(quanChengs.get(0).getXuHao());
        fiveStepTemplate.setStartStress(quanChengs.get(0).getJunGang2().intValue());
        fiveStepTemplate.setStartTime(quanChengs.get(0).getDateTime());
        int last_guanya = quanChengs.get(0).getJunGang2();
        int length = quanChengs.size();
        fiveStepTemplate.setIsStable(0);//初始化为稳定，后面再看。
        //初始化的时候，可以往前走三秒，看是稳定还是下降
//        if (Math.abs(quanChengs.get(0).getGuanYa()-quanChengs.get(1).getGuanYa())<20)
        for (int i = 0;i<length;i++) {

            if (Math.abs(quanChengs.get(i).getJunGang2()-last_guanya)>20 && flag_first_show == 1){
                //假设误差在20以上
                //这里代表的是节点
                fiveStepTemplate.setEndTime(quanChengs.get(i-1).getDateTime());
                fiveStepTemplate.setEnd(quanChengs.get(i-1).getXuHao());
                fiveStepTemplate.setEndStress(quanChengs.get(i-1).getJunGang2().intValue());
                if (last_guanya>quanChengs.get(i).getJunGang2()){
                    //下一段相对于自己来说是升还是降，1为升，-1为降。
                    fiveStepTemplate.setNextStart(1);
                }else {
                    fiveStepTemplate.setNextStart(-1);
                }
                nextfiveStep = new FiveStepTemplate();
                fiveStepTemplate.setNext(nextfiveStep);
                fiveStepTemplate.setContinueTime((int) ((fiveStepTemplate.getEndTime().getTime()-fiveStepTemplate.getStartTime().getTime())/1000));
                resJunGang2.add(fiveStepTemplate);
                nextfiveStep.setStartStress(quanChengs.get(i).getJunGang2().intValue());
                //向前走三步，看是升，还是降。
                if(quanChengs.get(i).getJunGang2()>quanChengs.get(i+1).getJunGang2() &&
                        quanChengs.get(i+1).getJunGang2()>quanChengs.get(i+2).getJunGang2()){
                    nextfiveStep.setIsStable(-1);
                }else {
                    nextfiveStep.setIsStable(1);
                }
                flag_first_show = 0;
                nextfiveStep.setStart(quanChengs.get(i).getXuHao());
                nextfiveStep.setStartTime(quanChengs.get(i).getDateTime());

            }else{
                //遇到稳定的时候，就开始往前走一步开始修改
                //在下降或者上升的时候，序号不应该相等，这个时候就可以进行一个序号的判断。
                if(Math.abs(quanChengs.get(i).getJunGang2()-last_guanya)<20 && flag_first_show==0 && !quanChengs.get(i).getXuHao().equals(quanChengs.get(i-1).getXuHao())){
                    nextfiveStep.setEnd(quanChengs.get(i-2).getXuHao());
                    nextfiveStep.setEndTime(quanChengs.get(i-2).getDateTime());
                    nextfiveStep.setEndStress(quanChengs.get(i-2).getJunGang2().intValue());
                    flag_first_show = 1;
                    fiveStepTemplate = new FiveStepTemplate();
                    fiveStepTemplate.setIsStable(0);
                    fiveStepTemplate.setStartTime(quanChengs.get(i-1).getDateTime());
                    fiveStepTemplate.setStart(quanChengs.get(i-1).getXuHao());
                    fiveStepTemplate.setStartStress(quanChengs.get(i-1).getJunGang2().intValue());

                    nextfiveStep.setNext(fiveStepTemplate);
                    nextfiveStep.setContinueTime((int) ((nextfiveStep.getEndTime().getTime()-nextfiveStep.getStartTime().getTime())/1000));
                    resJunGang2.add(nextfiveStep);
                }
            }
            last_guanya = quanChengs.get(i).getJunGang2();
        }
        return resJunGang2;
    }

    public static FiveStepTemplate stepFinder(List<QuanCheng>quanChengs, List<List<FiveStepTemplate>> fiveStepTemplates, List<List<StepSelect>> stepSelects){
        /*
        步骤寻找，
        输入：quancheng 全程文件
             fiveStepTemplate 全程文件板块
             stepSelect 步骤的板块
             如果输出结果是-1 则表示没有找到结果
         */
        List<FiveStepTemplate> guanyaTemplate =fiveStepTemplates.get(0);
        List<FiveStepTemplate> gangyaTemplate =fiveStepTemplates.get(1);
        List<FiveStepTemplate> jungang1Template =fiveStepTemplates.get(2);
        List<FiveStepTemplate> jungang2Template =fiveStepTemplates.get(0);
        List<StepSelect> guanyaSelect = stepSelects.get(0);
        List<StepSelect> gangyaSelect = stepSelects.get(1);
        List<StepSelect> jungangSelect = stepSelects.get(2);
        //挨个进行匹配。最后进行比较，然后返回结果。
        List<FiveStepTemplate> guanyares= new ArrayList<>();
        List<FiveStepTemplate> gangyares= new ArrayList<>();
        List<FiveStepTemplate> jungangres= new ArrayList<>();
        if (guanyaSelect.size()>0){
            int i = 0;
            for (int j=0;j<guanyaTemplate.size();j++){
                //首先判断第一个是不是一样，然后后面再继续向下比较
                int temp = j;
                //这里没有考虑到同步的问题
                //同步问题暂时还不考虑。下一阶段开始考虑
                while (i<guanyaSelect.size() && temp<guanyaTemplate.size()){
                    if (guanyaSelect.get(i).getIsStable()==guanyaTemplate.get(temp).getIsStable()
                    && Math.abs( guanyaSelect.get(i).getStartNumber()-guanyaTemplate.get(temp).getStartStress())<=20
                            && Math.abs( guanyaSelect.get(i).getEndNumber()-guanyaTemplate.get(temp).getEndStress())<=20
                    && guanyaTemplate.get(temp).getContinueTime()>=guanyaSelect.get(i).getContinueTime()){
                        i++;
                        temp++;
                    }else {
                        i = 0;
                        break;
                    }
                }
                if (i>=guanyaSelect.size()){
                    guanyares.add(guanyaTemplate.get(temp-1));
                    i=0;
                    j = temp;
                }
            }
        }
        if (gangyaSelect.size()>0){
            int i = 0;
            for (int j=0;j<gangyaTemplate.size();j++){
                //首先判断第一个是不是一样，然后后面再继续向下比较
                int temp = j;
                //这里没有考虑到同步的问题
                //同步问题暂时还不考虑。下一阶段开始考虑
                while (i<gangyaSelect.size() && temp<gangyaTemplate.size()){
                    if (gangyaSelect.get(i).getIsStable()==gangyaTemplate.get(temp).getIsStable()
                            && Math.abs( gangyaSelect.get(i).getStartNumber()-gangyaTemplate.get(temp).getStartStress())<=20
                            && Math.abs( gangyaSelect.get(i).getEndNumber()-gangyaTemplate.get(temp).getEndStress())<20
                            && gangyaTemplate.get(temp).getContinueTime()>=gangyaSelect.get(i).getContinueTime()){
                        i++;
                        temp++;
                    }else {
                        i = 0;
                        break;
                    }
                }
                if (i>=gangyaSelect.size()){
                    gangyares.add(gangyaTemplate.get(temp));
                    j = temp;
                }
            }
        }
        if (jungangSelect.size()>0){
            //均缸这里有问题还是需要修改的。
            int i = 0;
            for (int j=0;j<jungang1Template.size();j++){
                //首先判断第一个是不是一样，然后后面再继续向下比较
                int temp = j;
                //这里没有考虑到同步的问题
                //同步问题暂时还不考虑。下一阶段开始考虑
                while (i<jungangSelect.size() && temp<jungang1Template.size()){
                    if (jungangSelect.get(i).getIsStable()==jungang1Template.get(temp).getIsStable()
                            && Math.abs( jungangSelect.get(i).getStartNumber()-jungang1Template.get(temp).getStartStress())<=20
                            && Math.abs( jungangSelect.get(i).getEndNumber()-jungang1Template.get(temp).getEndStress())<20
                            && jungang1Template.get(temp).getContinueTime()>=jungangSelect.get(i).getContinueTime()){
                        i++;
                        temp++;
                    }else {
                        i = 0;
                        break;
                    }
                }
                if (i>=jungangSelect.size()){
                    jungangres.add(jungang1Template.get(temp));
                    j = temp;
                }
            }
        }
        if (jungangres.size()==0){
            if (jungangSelect.size()>0){
                //均缸这里有问题还是需要修改的。
                int i = 0;
                for (int j=0;j<jungang2Template.size();j++){
                    //首先判断第一个是不是一样，然后后面再继续向下比较
                    int temp = j;
                    //这里没有考虑到同步的问题
                    //同步问题暂时还不考虑。下一阶段开始考虑
                    while (i<jungangSelect.size() && temp<jungang2Template.size()){
                        if (jungangSelect.get(i).getIsStable()==jungang2Template.get(temp).getIsStable()
                                && Math.abs( jungangSelect.get(i).getStartNumber()-jungang2Template.get(temp).getStartStress())<=20
                                && Math.abs( jungangSelect.get(i).getEndNumber()-jungang2Template.get(temp).getEndStress())<20
                                && jungang2Template.get(temp).getContinueTime()>=jungangSelect.get(i).getContinueTime()){
                            i++;
                            temp++;
                        }else {
                            i = 0;
                            break;
                        }
                    }
                    if (i>=jungangSelect.size()){
                        jungangres.add(jungang2Template.get(temp));
                        j = temp;
                    }
                }
            }
        }
        //当所有res的都找到了。进行匹配。
        List <Integer> res = new ArrayList<>();
        List<FiveStepTemplate> TemplateRes = new ArrayList<>();
        if((guanyaSelect.size()>0&&guanyares.size()==0)||(gangyaSelect.size()>0 &&guanyares.size()==0)||(jungangSelect.size()>0&&jungangres.size()==0)){
                //当有条件没找到的时候，进行返回。
//                res.add(-1);
                return null;
        }
        //进行比较,最好时间不相差过3S
        if (guanyaSelect.size()>0){
            for (int i = 0;i<guanyares.size();i++){
                if (gangyaSelect.size()>0){
                    for (int j = 0;j<=gangyares.size();j++){
                       if (Math.abs((guanyares.get(i).getStartTime().getTime()-gangyares.get(j).getStartTime().getTime())/1000)<=3){
                            if (jungangSelect.size()>0){
                                for(int q= 0;q<=jungangres.size();q++){
                                    if (Math.abs((guanyares.get(i).getStartTime().getTime()-jungangres.get(q).getStartTime().getTime())/1000)<=3){
                                        TemplateRes.add(guanyares.get(i));
                                        break;
                                    }
                                }
                            }else {
                                TemplateRes.add(guanyares.get(i));
                                break;
                            }
                       }
                    }
                }else {
                    if (jungangSelect.size()>0){
                        for(int q= 0;q<=jungangres.size();q++){
                            if (Math.abs((guanyares.get(i).getStartTime().getTime()-jungangres.get(q).getStartTime().getTime())/1000)<=3){
                                TemplateRes.add(guanyares.get(i));
                                break;
                            }
                        }
                    }else {
                        TemplateRes.add(guanyares.get(i));
                    }
                }
            }
        }else if(gangyaSelect.size()>0){
            for (int i = 0;i<gangyares.size();i++){
                if (jungangSelect.size()>0){
                    for(int q= 0;q<=jungangres.size();q++){
                        if (Math.abs((guanyares.get(i).getStartTime().getTime()-jungangres.get(q).getStartTime().getTime())/1000)<=3){
                            TemplateRes.add(gangyares.get(i));
                            break;
                        }
                    }
                }else{
                    TemplateRes.add(gangyares.get(i));
                }
            }
        }else {
            for(int i = 0;i<jungangres.size();i++){
                TemplateRes.add(jungangres.get(i));
            }
        }
        if (TemplateRes.size()>0)
            return TemplateRes.get(TemplateRes.size()-1);
        else
            return null;
    }
}
