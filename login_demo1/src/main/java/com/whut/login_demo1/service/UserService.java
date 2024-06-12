package com.whut.login_demo1.service;

import com.whut.login_demo1.domain.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    User loginService(String username, String password);
    User registService(User user);

    User getInfoService(String username);
    User changePasswordService(String username, String old_password, String new_password);
}