package com.gxl.trainerror.service;

import com.gxl.trainerror.bean.EventChange;

import java.util.List;

public interface EventChangeService {
    public List<EventChange> selectAll();
    public EventChange selectByID(Integer ID);
    public Integer insertEventChange(EventChange eventChange);
}
