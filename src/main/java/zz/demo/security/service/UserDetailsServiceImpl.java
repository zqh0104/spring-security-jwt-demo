package zz.demo.security.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import zz.demo.security.common.domain.entity.User;
import zz.demo.security.common.domain.model.LoginUser;
import zz.demo.security.common.enums.UserStatus;
import zz.demo.security.common.exception.ServiceException;
import zz.demo.security.mapper.UserMapper;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户验证处理
 *
 * @author zz
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 查询用户信息
        User user = userMapper.selectUserByUserName(username);

        // 没有查询到用户或者用户已删除、已锁定
        if (Objects.isNull(user)) {
            log.info("登录用户：{} 不存在.", username);
            throw new ServiceException("user.not.exists");
        } else if (UserStatus.DELETED.getCode().equals(user.getDelFlag())) {
            log.info("登录用户：{} 已被删除.", username);
            throw new ServiceException("user.password.delete");
        } else if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
            log.info("登录用户：{} 已被停用.", username);
            throw new ServiceException("user.blocked");
        }
//
//        passwordService.validate(user);

        // 封装成UserDetails
        return createLoginUser(user);
    }

    public UserDetails createLoginUser(User user) {
//        Set<String> menuPermission = permissionService.getMenuPermission(user);
//        List<SimpleGrantedAuthority> authorityList = menuPermission.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        return new LoginUser(user.getUserId(), user, permissionService.getMenuPermission(user));
    }
}
