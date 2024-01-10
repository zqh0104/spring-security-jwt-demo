package zz.demo.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloSecurityController {

    @GetMapping("/hello")
    @PreAuthorize("hasAuthority('hello')")
    public String hello() {
        return "hello security";
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('system:user:list')")
    public String getUsers() {
        return "get users";
    }

    @GetMapping("/users1")
    @PreAuthorize("@ss.hasPermi('system:user:list')")
    public String getUsers1() {
        return "get users 1";
    }

    @GetMapping("/users2")
    @PreAuthorize("@ss.hasRole('common')")
    public String getUsers2() {
        return "get users 2";
    }
}
