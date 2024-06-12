package com.whut.login_demo1.service;

import com.whut.login_demo1.domain.Picture;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface PicService {
    Picture registPicService(MultipartFile file);
    Picture getAvatarService(Long uid);
}
