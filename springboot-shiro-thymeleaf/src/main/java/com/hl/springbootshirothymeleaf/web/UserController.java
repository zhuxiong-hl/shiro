package com.hl.springbootshirothymeleaf.web;

import com.hl.springbootshirothymeleaf.entity.User;
import com.hl.springbootshirothymeleaf.service.UserService;
import com.hl.springbootshirothymeleaf.utils.VerifyCodeUtils;
import lombok.SneakyThrows;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("logins")
    public String login(){
        return "login";
    }

    @RequestMapping("login")
    public String login(String username, String password, String code,HttpSession session){
        String sessionCode = (String) session.getAttribute("code");
        try {
            if (sessionCode.equalsIgnoreCase(code)) {
                Subject subject = SecurityUtils.getSubject();
                UsernamePasswordToken token = new UsernamePasswordToken(username,password);
                subject.login(token);
                return "index";
            } else {
                throw new RuntimeException("验证码错误!");
            }
        } catch (IncorrectCredentialsException e) {
            e.printStackTrace();
            System.out.println("密码错误");
        } catch (UnknownAccountException e){
            e.printStackTrace();
            System.out.println("用户名错误");
        }
        return "redirect:/user/logins";
    }

    @RequestMapping("logout")
    public String logout(){
        SecurityUtils.getSubject().logout();
        return "redirect:/login";

    }

    @RequestMapping("registerView")
    public String register(){
        return "register";
    }

    @RequestMapping("register")
    public String register(User user){
        try {
            userService.register(user);
            return "redirect:/login";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/user/registerView";
        }

    }

    @SneakyThrows
    @RequestMapping("getImage")
    public void getImage(HttpSession session, HttpServletResponse response){
        String code = VerifyCodeUtils.generateVerifyCode(4);
        session.setAttribute("code",code);
        response.setContentType("image/png");
        ServletOutputStream os = response.getOutputStream();
        VerifyCodeUtils.outputImage(220,60,os,code);
    }

}
