package com.gxl.trainerror;

import com.gxl.trainerror.bean.QuanCheng;
import com.gxl.trainerror.util.CSVRead;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

public class TestUnit {
    @Test
    void testCsv() throws IOException {
        String realPath = "E:\\test.xls";
        List<QuanCheng> quanChengs = CSVRead.CscReader(realPath,1218);
        for (QuanCheng quanCheng : quanChengs) {
            System.out.println(quanCheng);
        }
    }
}
