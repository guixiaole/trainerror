package com.gxl.trainerror;

import com.sun.jna.*;
import com.sun.jna.ptr.PointerByReference;
import com.sun.jna.win32.StdCallLibrary;
import org.apache.ibatis.javassist.bytecode.ByteArray;

import java.util.ArrayList;
import java.util.List;

public class TestDllService {
    public interface TestDll extends StdCallLibrary {

        @Structure.FieldOrder({"firstevent", "seconevent", "event_type", "event_type2", "starttime", "endtime", "index0", "index", "name", "name2", "group1", "group2", "weizhang", "driver_no", "driver_no2", "proc_dir", "speed", "segment_num", "kilemile", "czdigit", "czchar", "cench", "data6A", "reserved", "reserved2"})
        public static class EVENT_ITEM2 extends Structure {
            public static class ByReference extends EVENT_ITEM2 implements Structure.ByReference {
            }

            public static class ByValue extends EVENT_ITEM2 implements Structure.ByValue {

            }

            public short firstevent; //项点ID //相点事件字母（与《新增及变更事件定义.doc》事件代码一致）
            public short seconevent; //项点ID //并发相点事件字母， 可以无事件为‘0’
            public int event_type;
            public int event_type2;
            public int starttime;            //相点事件开始时间（相对当天0点的秒数）
            public int endtime;              //相点事件结束时间（相对当天0点的秒数）
            public int index0;                //相点事件视频通道，通道1~16对应0~15
            public int index;                 //相点事件在本次查询中 该视频通道下的第几个文件,数字从0开始
            public byte[] name = new byte[32];             //项点名称
            public byte[] name2 = new byte[32];            //项点名称2
            public short group1;     //组序号1
            public short group2;     //组序号2
            public int weizhang;              //违章情况
            public int driver_no;             //司机号
            public int driver_no2;            //副司机号
            public int proc_dir;              //司机室状态（0-没占用，1-一端占用，2-二端占用，0xff-无效)
            public int speed;                 //速度
            public int segment_num;           //实际交路号
            public int kilemile;              //公里标（单位：米）
            public int czdigit;               //车次数字部分
            public byte[] czchar = new byte[4];            //车次字母部分（例如：LY501次的字母存放顺序为[空格][空格][L][Y]）
            public byte[] cench = new byte[20];            //11中央处理平台车号（共16个字符，由低到高依次存放，不足时补充空格）
            public byte[] data6A = new byte[256]; //6A数据
            public byte[] reserved = new byte[16];         //保留
            public byte[] reserved2 = new byte[16];        //保留

            public EVENT_ITEM2() {

            }

            public EVENT_ITEM2(Pointer ptr) {
                super(ptr);
                super.read();
            }
        }

        public static class FILEHEAD extends Structure {
            public byte[] filename;
            public byte[] siji;
            public byte[] fsiji;
            public String jiwuduan;
            public String checi;
            public String jichehao;
            public String jichexing;
            public String qidianzhan;
            public String zhongdianzhan;
            public String jiaoluhao;
            public String kaicheriqi;

            public String heji;      //合计
            public String huanchang; //计长
            public String lunjing;   //轮径
            public String jklx;      //监控类型
            public String zzh;       //装置号
            public String jiankongbanben;
            public String shujubanben;
            public String zongzhong; //总重
            public String zhaizhong; //载重

            public String keche;      //客车
            public String zhongche;   //重车
            public String kongche;    //空车
            public String feiyunyong; //非运用车
            public String daikeche;   //代客车
            public String shouche;    //守车
        }

        TestDll INSTANCE = (TestDll) Native.load("GY_LKJ_EVENT", TestDll.class);

        public NativeLong GY_LKJ_EVENT_Read(String lkjpathname, String outpath, int stime,
                                            int etime, Pointer prevent, int maxenvent);

        public int GY_LKJ_EVENT_Init();

        public int openqcjl();

    }
    public TestDllService(){
        System.out.println("已经启动了");
    }

    public static void main(String[] args) {


        System.out.println("开始");
        System.out.println(TestDll.INSTANCE);
        String lkjpathname ="G:/2021JAVA/16-8018-5079913-7112391H0.0911F";
//        String lkjpathname ="Z53-7120316.0401";
        String output="G:";
        int stime=0;
        int etime = 0;
//        Pointer ptr;
        int maxevent =1024;
//        Pointer ptr = TestDll.INSTANCE.createEVENT_ITEM2();
//        List <TestDll.EVENT_ITEM2.ByReference> event_item2 = new ArrayList<>();
        TestDll.EVENT_ITEM2[] event_item2 = new TestDll.EVENT_ITEM2[maxevent];
        event_item2[0] = new TestDll.EVENT_ITEM2();
        Memory memory = new Memory(event_item2.length*event_item2[0].size());
        Pointer pointer=event_item2[0].getPointer();
        pointer= memory.getPointer(0);
       // Pointer pointer = event_item2[0].getPointer();

        //event_item2.write();
       // Pointer p=new Memory((event_item2.size()*maxevent)+1);
       // p.write(0,event_item2.getPointer().getByteArray(0,event_item2.size()),0,event_item2.size()*maxevent);

//        p.write(0,event_item2.getByteArray(0,alarmMsg.alarmInfoLen),0, alarmMsg.alarmInfoLen);
//        TestDll.EVENT_ITEM2 [] event_item2s = (TestDll.EVENT_ITEM2[]) event_item2.toArray(maxevent);
//        Memory memory =new Memory(maxevent*200);
//        event_item2.
//            Pointer[] pointers = new Pointer[];
//          PointerByReference reference = new PointerByReference(event_item2.getPointer());
//          Memory memory =new Memory(event_item2.size()*1000);
//          int length = event_item2.size();
//          memory.write(0,event_item2.getPointer(),0,length);
          //        memory.write(0,event_item2,0,200);
//        event_item2.endtime=1;
//        System.out.println("1111::::"+event_item2);
//        event_item2.write();
//        System.out.println(TestDll.INSTANCE.GY_LKJ_EVENT_Init());
        NativeLong res =TestDll.INSTANCE.GY_LKJ_EVENT_Read(
                lkjpathname,output,stime,etime,pointer,maxevent);
        TestDll.EVENT_ITEM2 s = new TestDll.EVENT_ITEM2();
        for (TestDll.EVENT_ITEM2 eventItem2 : event_item2) {
            System.out.println(eventItem2);
        }
        int size = s.size();
        System.out.println("res==="+res);
        System.out.println("1111");
        System.out.println("结束");
    }
}
