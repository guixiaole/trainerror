package com.gxl.trainerror;

import com.gxl.trainerror.bean.QuanCheng;
import com.gxl.trainerror.util.CSVRead;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class TestUnit {
    @Test
    void testCsv() throws IOException, ParseException {
        String realPath = "D:\\test\\6027-8018-5079999-5031894H0.0418F.xls";
        List<QuanCheng> quanChengs = CSVRead.CscReader(realPath,1218);
        for (QuanCheng quanCheng : quanChengs) {
            System.out.println(quanCheng);
        }
    }
}
