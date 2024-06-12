package com.whut.login_demo1.service.serviceImpl;

import com.whut.login_demo1.domain.Picture;
import com.whut.login_demo1.repository.PicDao;
import com.whut.login_demo1.service.PicService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class PicServiceImpl implements PicService {
    @Resource
    private PicDao picDao;
    @Override
    public Picture registPicService(MultipartFile file){
        Picture pic = new Picture();
        //pic = null;
        try {
            pic.setAvatar(file.getBytes());
            picDao.save(pic);
        }catch (Exception e){
            e.printStackTrace();
        }
        return pic;
    }

    @Override
    public Picture getAvatarService(Long uid){
        Optional<Picture> pic = picDao.findById(uid);
        Picture p = null;
        if(pic.isPresent()){
            p = pic.get();
        }

        return p;
    }
}
