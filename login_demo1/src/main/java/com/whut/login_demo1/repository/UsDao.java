package com.whut.login_demo1.repository;
import com.whut.login_demo1.domain.Us;
import com.whut.login_demo1.domain.User;
import com.whut.login_demo1.utils.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsDao extends JpaRepository<Us, Long> {
    List<Us> findAll(); //通过用户名uname查找用户，注意要按照JPA的格式使用驼峰命名法
    //Us findByUsernameAndPassword(String username, String password);//通过用户名uname和密码查找用户
    Us save(Us user);
}

