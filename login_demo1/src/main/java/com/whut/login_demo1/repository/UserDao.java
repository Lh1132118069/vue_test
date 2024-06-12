package com.whut.login_demo1.repository;
import com.whut.login_demo1.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, Long> {
        User findByUsername(String username); //通过用户名uname查找用户，注意要按照JPA的格式使用驼峰命名法
        User findByUsernameAndPassword(String username, String password);//通过用户名uname和密码查找用户

}

