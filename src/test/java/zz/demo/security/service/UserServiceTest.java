package zz.demo.security.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import zz.demo.security.common.domain.entity.User;
import zz.demo.security.mapper.UserMapper;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void findUserByUserName() {
        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery(User.class).eq(User::getUserName, "admin");
        User user = userMapper.selectOne(wrapper);
        System.out.println(user);
    }

    @Test
    public void passwordEncoder(){
        String encode = passwordEncoder.encode("123");
        // $2a$10$yYTXL3FfhIteo4UWfLFsEu7DIw.1DUL4k8lq3n.ZST3pmhVqTDkOe
        System.out.println(encode);
        System.out.println(passwordEncoder.matches("123", encode));
    }
}
