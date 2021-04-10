package com.gxl.trainerror.util;

import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;

public class FileCreateMonitor {
    FileAlterationMonitor monitor=null;
    public FileCreateMonitor(long interval) throws Exception{
        monitor = new FileAlterationMonitor(interval);
    }
    public void monitor(String path, FileAlterationListener listener) {
        FileAlterationObserver observer = new FileAlterationObserver(new File(path));
        monitor.addObserver(observer);
        observer.addListener(listener);
    }
    //监控停止
    public void stop() throws Exception{
        monitor.stop();
    }
    //监控开始
    public void start() throws Exception {
        monitor.start();
    }
}
