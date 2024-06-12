package com.whut.login_demo1.service;

import com.whut.login_demo1.domain.Us;
import com.whut.login_demo1.domain.User;
import com.whut.login_demo1.utils.Result;

import java.util.List;

public interface UsService {
    List<Us> getInfoService();
    Us addUsService(Us user);
    Us updateUsService(Us us);
    Us deleteUsService(Long id);
}