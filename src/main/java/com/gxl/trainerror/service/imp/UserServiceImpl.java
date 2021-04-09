package com.gxl.trainerror.service.imp;

import com.gxl.trainerror.bean.User;
import com.gxl.trainerror.mapper.UserMapper;
import com.gxl.trainerror.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public User loginUser(User user){
        User user1 = userMapper.selectUserByLogin(user);
            if (user1!=null){
                return user1;
            }

            return  null;
    }
}
