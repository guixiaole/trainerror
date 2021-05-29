package com.gxl.trainerror.service.imp;

import com.gxl.trainerror.bean.EventChange;
import com.gxl.trainerror.mapper.EventChangeMapper;
import com.gxl.trainerror.service.EventChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventChangeServiceImpl implements EventChangeService {
    @Autowired
    private EventChangeMapper eventChangeMapper;
    @Override
    public List<EventChange> selectAll() {
        return eventChangeMapper.selectAll();
    }

    @Override
    public EventChange selectByID(Integer ID) {
        return eventChangeMapper.selectById(ID);
    }
}
