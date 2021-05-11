package com.gxl.trainerror.service;

import com.gxl.trainerror.bean.AllTemplate;
import org.apache.xmlbeans.impl.xb.xsdschema.All;


import java.util.List;

public interface AllTemplateService {
    public List<AllTemplate> index();
    public Integer insert(AllTemplate allTemplate);
}
