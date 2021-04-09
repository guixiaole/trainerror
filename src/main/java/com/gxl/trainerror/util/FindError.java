package com.gxl.trainerror.util;

import ch.qos.logback.core.util.FileUtil;
import com.gxl.trainerror.bean.ExcelFile;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.STIconSetType;

import javax.swing.*;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FindError {
    public  List<List<String>> write_excel(String realPath) throws IOException, ParseException {
        /*
        读取的是excel表格，返回的是几
        返回的顺序是：个时间，管压，缸压，均缸，。
         */
        File fileDes = new File(realPath);
        InputStream str = new FileInputStream(fileDes);
//        Workbook book = new HSSFWorkbook(str);
        HSSFWorkbook book = new HSSFWorkbook(str);
        Sheet sheet =  book.getSheetAt(0);
        List<List<String>> res =new ArrayList<>();
        int rows=sheet.getLastRowNum();//总行数
        List <String>times = new ArrayList<>();//时间
        List <String>guanya = new ArrayList<>();//管压
        List <String>gangya = new ArrayList<>();//缸压
        List <String>jungang1 = new ArrayList<>();//均缸1
        List <String>jungang2 = new ArrayList<>();//均缸2
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date lastDate = null;
        for (int j= 1;j<rows;j++){
            Row row_time = sheet.getRow(j);
            Cell cell = row_time.getCell(2);
            Date date1 = cell.getDateCellValue();
//            System.out.println(cell);
//            System.out.println(date1);
            String val = sdf.format(date1);
//            System.out.println(val);

//            String time_date = String.valueOf(cell);
            //对时间进行一个标准化。

            Date date = sdf.parse(val);
            if(j>1){
                //对时间差进行一个标准化。
                int time_minus = (int)((date.getTime()-lastDate.getTime())/1000);
                if (time_minus>1){
                    Date tempdate = lastDate;
                    int flag_plus = 1;
                    while (time_minus>1){

//                        tempdate=sdf.parse(times.get(times.size()-1));
//                        System.out.println(tempdate);
                        tempdate.setTime(tempdate.getTime()+1000);
                        flag_plus++;
                        times.add(sdf.format(tempdate));
//                        guanya.add(new String(String.valueOf(row_time.getCell(13))));//管压
//                        gangya.add(new String(String.valueOf(row_time.getCell(15))));//杠压
//                        jungang1.add(new String(String.valueOf(row_time.getCell(16))));//均缸1
//                        jungang2.add(new String(String.valueOf(row_time.getCell(17))));//均缸2//
                        guanya.add(guanya.get(guanya.size()-1));//管压
                        gangya.add(gangya.get(gangya.size()-1));//杠压
                        jungang1.add(jungang1.get(jungang1.size()-1));//均缸1
                        jungang2.add(jungang2.get(jungang2.size()-1));//均缸2
                        time_minus--;
                    }
                    times.add(sdf.format(date1));//时间
                    guanya.add(new String(String.valueOf(row_time.getCell(13))));//管压
                    gangya.add(new String(String.valueOf(row_time.getCell(15))));//杠压
                    jungang1.add(new String(String.valueOf(row_time.getCell(16))));//均缸1
                    jungang2.add(new String(String.valueOf(row_time.getCell(17))));//均缸2//
                    lastDate = sdf.parse(val);
                }
            }else {//初始化的时候、
                lastDate = sdf.parse(val);
//                System.out.println(val);
                times.add(sdf.format(date1));//时间
                guanya.add(new String(String.valueOf(row_time.getCell(13))));//管压
                gangya.add(new String(String.valueOf(row_time.getCell(15))));//管压
                jungang1.add(new String(String.valueOf(row_time.getCell(16))));//管压
                jungang2.add(new String(String.valueOf(row_time.getCell(17))));//管压
            }
        }
        //记住返回的顺序。
        //
        res.add(times);
        res.add(guanya);
        res.add(gangya);
        res.add(jungang1);
        res.add(jungang2);
        return res;
    }

    public List<List<Integer>> FindLieGuanError(List<List<String>> res){
        /*
        res: 输入，其中代表的是获取的相对应的数据。
        首先要有一个趋势。就是大概下降的趋势。寻找到下降的趋势。找到下降趋势的拐点。
        0: 代表没有错误。
        1：代表拥有错误。
        重要的是找闸。意味着就是有符合规则的就是闸。所以其实需要提取的就是三个点。
        所以是从所有的位置里面找到闸。思路就错了。
        首先管压的下降，从500下降到450.保压半分钟。然后再从450下降到360，然后再恢复到500.
         */
        List<String> guanya = res.get(1);//首先获取的是管压
        List<String> times = res.get(0);//获取时间。
        List<Integer> enddot = new ArrayList<>();//结束的点。
        //数据已经进行了标准化了。
        //因为管压首先是500kap的，那么找到在480-520kpa的。
        List<Integer> downdot = new ArrayList<>();//开始的点。
        int flage_five = 0;//初始化一个flag。就是找到几个点。
        for (int i=0;i<guanya.size();i++){

            if (Float.parseFloat(guanya.get(i))>=480 && Float.parseFloat(guanya.get(i))<=520){
                //首先找到下降点。
                if (flage_five == 0){
                    downdot.add(i);//每次都是开始的那个点
                    flage_five=1;
                }
            }else{
                if (flage_five==1){
                    flage_five=0;
                }
            }
        }
        //这里找到了无数的拐点。但是要进行筛选。
        //拐点的筛选方法。通过筛选拐点找到真正的闸机。
        for (int dot=0;dot<downdot.size();){
            //开始筛选拐点。

            int choose_dot =downdot.get(dot);
            for (int i=choose_dot;i<guanya.size();i++){
                if (Float.parseFloat(guanya.get(i)) < 480 || Float.parseFloat(guanya.get(i)) > 520) {
                    choose_dot = i;
                    break;//找到拐点的下一个点。
                }
            }
            int compare_dot =downdot.get(dot);
            if (dot>=downdot.size()-1) compare_dot=downdot.size();
            else compare_dot=downdot.get(dot+1);
            if (choose_dot>=compare_dot){
                downdot.remove(dot);//意味着当前就不是一个需要的闸点。
            }else {
                //TODO:需要在450的时候保压半分钟。
                int start_baoya = choose_dot;
                int end_baoya = choose_dot;
                for (int i = choose_dot;i<guanya.size();i++){
                    if(Float.parseFloat(guanya.get(i)) >=430 && Float.parseFloat(guanya.get(i)) <=470) {
                        if (start_baoya==choose_dot) start_baoya = i;
                        end_baoya=i;
                        //这里代表的是保压的那半分钟。
                        // TODO:需要思考的一点就是，保压的时间不能短于半分钟，是不是也不能太长？
                    }else {
                        if (end_baoya!=choose_dot) break;
                        //找到了那个保压的区间。
                    }
                }
                System.out.println(times.get(start_baoya));
                System.out.println(times.get(end_baoya));
                //需要认证保压的区间有没有半分钟？
                if (end_baoya-start_baoya<31){


                    //由于对每秒都有该有的数据，所以可以直接不用时间进行判断，而是直接由两个数据之间的相差。
                    downdot.remove(dot);//意味着当前就不是一个需要的闸点。
                }else{//TODO:在保压半分钟以后，进行下一个操作点即为下降到360
                    //确认是闸点之后。开始进行下一个操作点。
                    //然后继续往下一步走。下一步是到某一步的筛选条件。
                    //这一步的筛选条件为下降到360左右。
                    int start_down = end_baoya;//做一个标记，标记为在开始下降的时候。
                    int end_down = end_baoya;
                    for (int i =end_baoya;i<guanya.size();i++){
                        if (Float.parseFloat(guanya.get(i)) >=340 && Float.parseFloat(guanya.get(i)) <=380) {
                            //在这个区间之内。
                            if (start_down==end_baoya)start_down=i;//开始的区间。
                            end_down = i;
                        }//在这里获取一个区间，然后去其中间的值。
                        else {
                            if (end_down!=end_baoya)break;//找到区间时就开始暂停。
                        }
                    }//for循环结束。
                    int middle_down = (start_down+end_down)/2;//TODO:忘了是不是整数了。
                    if (middle_down-end_baoya<=3){
                        //在3s内下降到视为不是闸点  TODO:如果大于多少秒则视为不是闸点呢？有待解决。
                        //在3s以内就下降了，说明不是一个闸点。
                        downdot.remove(dot);//意味着当前就不是一个需要的闸点。
                    }else{
                        //说明可能是闸点。继续向下走。
                        //TODO: 在360需要保压一段时间，然后再进行上升。（保压时间未定。）
                        //保压之后需要上升。在上升之后，一部闸到此结束，即为一闸
                        int start_up = end_down;
                        int flag_340 = 0;
                        for(int i = end_down;i<guanya.size();i++){
                            if (Float.parseFloat(guanya.get(i)) >380) {
                                //在这个时候，其实是应该往上升得，而不是下降。所以下降得时候再做判断。
                                start_up = i;
                                break;
                            }else if (Float.parseFloat(guanya.get(i)) <340){
                                flag_340+=1;
                                if (flag_340>5){
                                    //TODO：这里我竟然没有看懂我的代码原来的意思。神奇了。为什么要设置5呢？
                                    //        downdot.remove(dot);//不能下降呢。一直下降说明就不是这个点。
                                    break;
                                }
                            }
                        }//在这里其实是没有限制的。没有说多久就上升。
                        if (flag_340>5){
                            downdot.remove(dot);
                        }else {//找到上升的点，且上升的点必须小于或者等于下一个点，否则撤销。
                            int final_dot = start_up;//最终的点
                            for(int i = start_up;i<guanya.size();i++) {
                                if (Float.parseFloat(guanya.get(i)) > 480) {
                                    final_dot = i;
                                    break;
                                }
                            }
                            //这里的next_dot 指的应该是downdot里面的，而不是管压里面的。
                            int next_dot = dot;
                            if (dot>=downdot.size()-1)  next_dot = guanya.size();
                            else next_dot = downdot.get(dot+1);
//                            if(final_dot-start_up<=3 || final_dot-next_dot>5) {
                            //TODO：对于给的原数据，这里没有理解。数据给的确实是不符合规范。
                            if(final_dot-next_dot>5){
                                //在这里的时候，说明不是闸点，就删除。
                                //不能超过下一个点太多，否则就认为是无效的点。
                                downdot.remove(dot);
                            }else {
                                //否则的话就进行保存。
                                enddot.add(final_dot);//最后结束的点。
                                dot++;//只有走到这一步，粒子才开始加，否则不加。
                            }
                        }
                    }
                }
            }

        }
        List<List<Integer>> ret =new ArrayList<>();
        ret.add(downdot);
        ret.add(enddot);
        return ret;
    }
    public List<List<Integer>> FindGangya(List<List<String>> res,List<List<Integer>>ret){
        /**
         * 先找管压，然后再从缸压里面找到合适的点。
         */
        List<Integer>downdot = ret.get(0);
        List<Integer>enddot = ret.get(1);
        List<String>gangya = res.get(2);
        for(int i =0;i<downdot.size();){
            //TODO: i 没有++，这点是需要注意的。
            //TODO: 另外需要注意一点的是输入顺序应该是 管压，缸压，均缸
            //首先缸压其实应该是0开始。
            int start_zero = downdot.get(i);//零开始的区间
            int end_zero = downdot.get(i);//零结束的点。
            int flag_zero = 1;

            for(int j = downdot.get(i);j<gangya.size();j++){
                //首先找到为0的区间。
                float temp =Float.parseFloat(gangya.get(j));
//                System.out.println(temp);
                if(Float.parseFloat(gangya.get(j))>-1 &&Float.parseFloat(gangya.get(j))<=20){
                    if (flag_zero==1){
                        start_zero = j;
                        flag_zero=0;
                    }
                }else if (Float.parseFloat(gangya.get(j))>20) {
                    if (flag_zero==0){
                        end_zero=j;
                        break;//找到了零的结束点。
                    }

                }
            }
            if (end_zero>enddot.get(i)){
                downdot.remove(i);
                enddot.remove(i);//在这里找到是不是超过了结束的点。
            }else{
                //否则的话继续往前走。
                int hundred_start = end_zero;//一百开始的点
                int hundred_end = end_zero;//保压一百结束的点
                for (int j =end_zero;j<gangya.size();j++){
                    //在100的区间上升。并且持续100至少31秒。
                    if(Float.parseFloat(gangya.get(j))>=80){
                        hundred_start = j;
                        break;
                    }
                }
                for (int j = hundred_start;j<gangya.size();j++){
                    if(Float.parseFloat(gangya.get(j))>120 ||Float.parseFloat(gangya.get(j))<80){
                        hundred_end =j;
                        break;
                    }
                }
                //TODO:这里并没有说需要保压。所以保压31s不一定确定（需要确认的点。1）
                //||hundred_start-end_zero<=3  这里也是需要确认的点。
                if (hundred_end-hundred_start<15 || hundred_end>enddot.get(i) ||Float.parseFloat(gangya.get(hundred_end+1))<80){
                    //假设不符合规定的话。
                    //TODO: 并没有说明要保压100kpa，这里需要重新思考。
                    enddot.remove(i);
                    downdot.remove(i);
                }else {
                    //在100稳定的时候，进入下一个时期。即上升期。
                    //继续进入到上升期假设没有上升，就剔除掉。
                    //保证在100kpa上升，而不是下降，且上升到一定的时候。
                    int three_hundred_start = hundred_end;
                    int flag_threehundred= 1;
                    for (int j = hundred_end;j<gangya.size();j++){
                        if (j>enddot.get(i)){
                            enddot.remove(i);
                            downdot.remove(i);
                            flag_threehundred = 0;
                            break;//上升没有达到需要的点。
                        }
                        //需要确定的一点是，在300处保压，然后开始下降
                        if(Float.parseFloat(gangya.get(j))>=280){
                            three_hundred_start = j;
                            break;
                        }else if (Float.parseFloat(gangya.get(j))<80){
                            //假设没有上升，反而下降的话，则剔除掉这个节点。
                            enddot.remove(i);
                            downdot.remove(i);
                            flag_threehundred = 0;
                            break;
                        }
                    }
                    if (flag_threehundred==1){
                        int threehundred_baoya = three_hundred_start;
                        //假设在这个可以持续下去。并没有下降。在360kpa保持一段时间
                        for (int j = three_hundred_start;j<gangya.size();j++){
                            if (Float.parseFloat(gangya.get(j))>320 ||Float.parseFloat(gangya.get(j))<280){
                                threehundred_baoya = j;
                                break;
                            }
                        }
                        //TODO: 并没有说明保压保多久。
                        if (threehundred_baoya-three_hundred_start<3){
                            //假设保压最起码需要3秒钟
                            enddot.remove(i);
                            downdot.remove(i);
                        }else {
                            //保压时间够长，那么久继续下一段，在保压结束的时候下降。
                            int flag_drop = 1;
                            int drop_start = threehundred_baoya;//下降的结束
                            for (int j = threehundred_baoya+1;j<gangya.size();j++){
                                //首先要保证下降的趋势。
                                //TODO:不知道保压的时间，以及保压的度数
                                if (Float.parseFloat(gangya.get(j))<20){
                                    drop_start = j;
                                    break;
                                }else if (Float.parseFloat(gangya.get(j))>360){
                                    flag_drop=0;
                                    break;
                                }
//                                if (Float.parseFloat(gangya.get(j))>Float.parseFloat(gangya.get(three_hundred_start))) {
//                                    flag_drop=0;
//                                    break;
//                                }
                            }//寻找结束的点的保压。
                            if (flag_drop==0){
                                enddot.remove(i);
                                downdot.remove(i);
                                //将不是的点移出
                            }else {
                                if (Math.abs(drop_start-enddot.get(i))>60){
                                    //假设前后相差一分钟的话，就判为不是相对应的点。
                                    enddot.remove(i);
                                    downdot.remove(i);
                                }else {
                                    i++;//这时候i才加一吗？
                                    //TODO: 需要注意的是其他的地方可能也需要加一。需要及时注意。
                                }
                            }
                        }
                    }
                }
            }
        }
        return ret;
    }
    public List<List<Integer>> FindJungang(List<List<String>> res,List<List<Integer>>ret){
        //均缸是在管压，缸压之后。
        //TODO：需要明确的是该找的是均缸一还是均缸二，因为有时候不确定。所以这是一个问题。
        //所以其实是不是可以均缸一，均缸二都要实验一遍？
        List<String> jungang = res.get(4);
        List<Integer> downdot = ret.get(0);
        List<Integer> enddot = ret.get(1);
        for (int i = 0;i<downdot.size();){
            //在漫长的粒子中寻找合适的粒子。
            int flag_first = 1;
            int choosedot = downdot.get(i);
            if (Float.parseFloat(jungang.get(downdot.get(i)))>520
                    ||Float.parseFloat(jungang.get(downdot.get(i)))<480){
                //首先要确定基础的点。
                int j=downdot.get(i);
                while (j<jungang.size()){
                    if (j>enddot.get(i)) {
                        flag_first = 0;
                        break;
                    }
                    if (Float.parseFloat(jungang.get(j))<=520
                            && Float.parseFloat(jungang.get(j))>=480) {
                        choosedot = j;
                    }
                    j++;
                }
            }
            if (flag_first==0){
                //假设不是的话，剔除出去。
                downdot.remove(i);
                enddot.remove(i);
            }else{
                int down_first =choosedot;
                int down_flag = 1;//做一个标记
                for (int j=choosedot;j<jungang.size();j++){
                    //首先找到开始下降的点。先从500下降。
                    if (Float.parseFloat(jungang.get(j))<480){
                        down_first=j;
                        break;
                    }else if (Float.parseFloat(jungang.get(j))>520){
                        down_flag = 0;
                        break;
                    }
                }
                if (down_flag==0 ||down_first>enddot.get(i)){
                    //标记如果不是，就剔除掉。
                    enddot.remove(i);
                    downdot.remove(i);
                }else {
                    //继续向下探索。
                    int four_hundred_baoya_start = down_first;
                    int  four_hundred_flag = 1;
                    for (int j = down_first;j<jungang.size();j++){
                        if (Float.parseFloat(jungang.get(j))<=470
                                &&Float.parseFloat(jungang.get(j))>=450){
                            four_hundred_baoya_start = j;
                            break;
                        }else if (Float.parseFloat(jungang.get(j))>500) {
                            //表示在非下降的情况下
                            four_hundred_flag = 0;
                        }
                    }
                    if (four_hundred_flag==0){
                        downdot.remove(i);
                        enddot.remove(i);
                    }else{//此处进行保压。
                        int four_hundred_baoya_end = four_hundred_baoya_start;
                        int four_hundred_baoya_flag = 1;
                        for (int j=four_hundred_baoya_start;j<jungang.size();j++){
                            if (Float.parseFloat(jungang.get(j))<430){
                                four_hundred_baoya_end = j;
                                break;
                            }else if (Float.parseFloat(jungang.get(j))>470){
                                four_hundred_baoya_flag = 0;
                                break;
                            }
                        }
                        if (four_hundred_baoya_flag==0 ||
                                four_hundred_baoya_end-four_hundred_baoya_start<31||
                                four_hundred_baoya_end>enddot.get(i)){
                            //TODO: 设定的是需要初减保压为31s，不确定是不是保压31s
                            downdot.remove(i);
                            enddot.remove(i);
                        }else{
                            //保压结束时，即继续向下减
                            int four_hundred_down_end = four_hundred_baoya_end;
                            int four_hundred_down_flag = 1;
                            for (int j = four_hundred_baoya_end;j<jungang.size();j++){
                                //下一段保压应该是在350左右？或者360.
                                if(Float.parseFloat(jungang.get(j))<370 &&
                                        Float.parseFloat(jungang.get(j))>330){
                                    four_hundred_down_end = j;
                                    break;
                                }else if(Float.parseFloat(jungang.get(j))>450){
                                    four_hundred_down_flag = 0;
                                    break;
                                }
                            }
                            if (four_hundred_down_flag==0){
                                downdot.remove(i);
                                enddot.remove(i);
                            }else{
                                //360的时候进行保压。
                                int three_hundred_baoya_end = four_hundred_down_end;
                                int three_hundred_baoya_flag = 1;
                                for (int j=four_hundred_down_end;j<jungang.size();j++){
                                    if(Float.parseFloat(jungang.get(j))>370 ||
                                            Float.parseFloat(jungang.get(j))<330){
                                        //假设没有保压了。即为保压结束。
                                        three_hundred_baoya_end = j;
                                        break;
                                    }
                                }
                                //保压的时间。暂且设定为10s钟。
                                if (three_hundred_baoya_end-four_hundred_down_end<10){
                                    downdot.remove(i);
                                    enddot.remove(i);
                                }else {
                                    //假设保压正常。那就开始升压。
                                    int up_last_end = three_hundred_baoya_end;//开始升压的最后的结果。
                                    int up_last_flag = 1;
                                    for (int j=three_hundred_baoya_end;j<jungang.size();j++){
                                        if(Float.parseFloat(jungang.get(j))<=520 &&
                                                Float.parseFloat(jungang.get(j))>=480){
                                            up_last_end = j;
                                            break;
                                        }else if(Float.parseFloat(jungang.get(j))<330){
                                            up_last_flag = 0;
                                            break;
                                        }
                                    }
                                    if (up_last_flag ==0){
                                        downdot.remove(i);
                                        enddot.remove(i);
                                        //上升时期失败。
                                    }else{
                                        if (up_last_end-three_hundred_baoya_end<5){
                                            downdot.remove(i);
                                            enddot.remove(i);
                                            //上升太快了？？？
                                        }else {
                                            i++;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        List<List<Integer>> last_res = new ArrayList<>();
        last_res.add(downdot);
        last_res.add(enddot);
        return last_res;
    }
    //从列管中找到相对应的点，然后再从列管重找到项点。
    public List<ExcelFile> writeExeclFile(String realPath) throws IOException, ParseException {
         /*
        读取的是excel表格，返回的是几
        返回的顺序是：个时间，管压，缸压，均缸，。
        2021年3月6日 16:37:12
         */
        File fileDes = new File(realPath);
        InputStream str = new FileInputStream(fileDes);
//        Workbook book = new HSSFWorkbook(str);
        HSSFWorkbook book = new HSSFWorkbook(str);
        Sheet sheet =  book.getSheetAt(0);
        List<List<String>> res =new ArrayList<>();
        int rows=sheet.getLastRowNum();//总行数
        List <String>times = new ArrayList<>();//时间
        List <String>guanya = new ArrayList<>();//管压
        List <String>gangya = new ArrayList<>();//缸压
        List <String>jungang1 = new ArrayList<>();//均缸1
        List <String>jungang2 = new ArrayList<>();//均缸2
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date lastDate = null;
        for (int j= 1;j<rows;j++){
            Row row_time = sheet.getRow(j);
            Cell cell = row_time.getCell(2);
            Date date1 = cell.getDateCellValue();
//            System.out.println(cell);
//            System.out.println(date1);
            String val = sdf.format(date1);
//            System.out.println(val);

//            String time_date = String.valueOf(cell);
            //对时间进行一个标准化。

            Date date = sdf.parse(val);
            if(j>1){
                //对时间差进行一个标准化。
                int time_minus = (int)((date.getTime()-lastDate.getTime())/1000);
                if (time_minus>1){
                    Date tempdate = lastDate;
                    int flag_plus = 1;
                    while (time_minus>1){

//                        tempdate=sdf.parse(times.get(times.size()-1));
//                        System.out.println(tempdate);
                        tempdate.setTime(tempdate.getTime()+1000);
                        flag_plus++;
                        times.add(sdf.format(tempdate));
//                        guanya.add(new String(String.valueOf(row_time.getCell(13))));//管压
//                        gangya.add(new String(String.valueOf(row_time.getCell(15))));//杠压
//                        jungang1.add(new String(String.valueOf(row_time.getCell(16))));//均缸1
//                        jungang2.add(new String(String.valueOf(row_time.getCell(17))));//均缸2//
                        guanya.add(guanya.get(guanya.size()-1));//管压
                        gangya.add(gangya.get(gangya.size()-1));//杠压
                        jungang1.add(jungang1.get(jungang1.size()-1));//均缸1
                        jungang2.add(jungang2.get(jungang2.size()-1));//均缸2
                        time_minus--;
                    }
                    times.add(sdf.format(date1));//时间
                    guanya.add(new String(String.valueOf(row_time.getCell(13))));//管压
                    gangya.add(new String(String.valueOf(row_time.getCell(15))));//杠压
                    jungang1.add(new String(String.valueOf(row_time.getCell(16))));//均缸1
                    jungang2.add(new String(String.valueOf(row_time.getCell(17))));//均缸2//
                    lastDate = sdf.parse(val);
                }
            }else {//初始化的时候、
                lastDate = sdf.parse(val);
//                System.out.println(val);
                times.add(sdf.format(date1));//时间
                guanya.add(new String(String.valueOf(row_time.getCell(13))));//管压
                gangya.add(new String(String.valueOf(row_time.getCell(15))));//管压
                jungang1.add(new String(String.valueOf(row_time.getCell(16))));//管压
                jungang2.add(new String(String.valueOf(row_time.getCell(17))));//管压
            }
        }
        //记住返回的顺序。
        //
        List <ExcelFile>excelFiles = new ArrayList<>();//
        for (int i=0;i<times.size();i++){
            ExcelFile excelFile =new ExcelFile();
            excelFile.setTimes(times.get(i));
            excelFile.setGuanya(guanya.get(i));
            excelFile.setGangya(gangya.get(i));
            excelFile.setJungang1(jungang1.get(i));
            excelFile.setJungang2(jungang2.get(i));
            excelFiles.add(excelFile);
        }
        return excelFiles;

    }

//    public static void main(String[] args) throws IOException, ParseException {
//        String realPath = "E:\\test.xls";
//        FindError findError =new FindError();
//        List<List<String>> list = findError.write_excel(realPath);
//        List<String> times = list.get(0);
//        List Lieguan_res =findError.FindLieGuanError(list);
//        List gangya_res = findError.FindGangya(list,Lieguan_res);
//        List <List<Integer>> jungang_res = findError.FindJungang(list,gangya_res);
//        List<Integer> downdot = jungang_res.get(0);
//        List<Integer> enddot = jungang_res.get(1);
//        for(int i = 0;i<downdot.size();i++){
//            System.out.println("开始的时间：");
//            System.out.println(times.get(downdot.get(i)));
//            System.out.println("结束的时间：");
//            System.out.println(times.get(enddot.get(i)));
//        }
//    }




}

