package com.gxl.trainerror.service.imp;

import com.gxl.trainerror.bean.AllTemplate;
import com.gxl.trainerror.mapper.AllTemplateMapper;
import com.gxl.trainerror.service.AllTemplateService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AllTemplateServiceImpl implements AllTemplateService {
    private AllTemplateMapper allTemplateMapper;
    @Override
    public List<AllTemplate> index() {
        return allTemplateMapper.index();
    }
}
