package com.whut.login_demo1.service.serviceImpl;

import com.whut.login_demo1.domain.Us;
import com.whut.login_demo1.domain.User;
import com.whut.login_demo1.repository.UsDao;
import com.whut.login_demo1.service.UsService;
import com.whut.login_demo1.utils.Result;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class UsServiceImpl implements UsService {
    @Resource
    private UsDao usDao;

    @Override
    public List<Us> getInfoService(){
        List<Us> us = usDao.findAll();
        return us;
    }
    @Override
    public Us addUsService(Us user){
        Us us = usDao.save(user);
        return us;
    }
    @Override
    public Us updateUsService(Us us){
        Optional<Us> optionalUser = usDao.findById(us.getId());
        Us user = null;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
            user.setDate(us.getDate());
            user.setName(us.getName());
            user.setProvince(us.getProvince());
            user.setCity(us.getCity());
            user.setAddress(us.getAddress());
            user.setZip(us.getZip());
            usDao.save(user);
        }
        return user;
    }

    @Override
    public Us deleteUsService(Long id){
        Optional<Us> optionalUser = usDao.findById(id);
        Us user = null;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
            usDao.deleteById(id);
        }

        return user;
    }
}
