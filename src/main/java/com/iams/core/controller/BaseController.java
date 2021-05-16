package com.iams.core.controller;

import com.alibaba.fastjson.JSON;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.iams.common.exception.ParameterException;
import com.iams.common.util.IamsUtils;
import com.iams.common.util.Result;
import com.iams.common.util.ResultGenerator;
import com.iams.common.util.Utils;
import com.iams.core.dto.UserInfo;
import com.iams.core.pojo.RoleEnum;
import com.iams.core.service.MailService;
import com.iams.core.service.StudentService;
import com.iams.core.service.TeacherService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

/**
 * @author Wei yz
 * @ClassName: BaseController
 * @Description:
 * @date 2021/4/23 19:26
 */
@Controller
public class BaseController {

    /**
     * 注入验证码
     */
    @Autowired
    private DefaultKaptcha captchaProducer;

    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private MailService mailService;

    /**
     * 登录页面
     *
     * @return
     */
    @RequestMapping("/login.html")
    public String loginPage() {
        return "login";
    }

    /**
     * 注册页面
     *
     * @return
     */
    @RequestMapping("/register.html")
    public String registerPage() {
        return "register";
    }

    /**
     * 登录操作
     *
     * @param userInfo 用户输入的信息
     * @param session  会话，用于取到验证码
     * @return
     */
    @ResponseBody
    @RequestMapping("/login")
    public Result login(@Valid UserInfo userInfo, HttpSession session) {
        Subject subject = SecurityUtils.getSubject();
        try {
            //toLowerCase() 统一转小写比较是否一致
            if (userInfo.getCaptcha().toLowerCase().equals(((String) session.getAttribute("verifyCode")).toLowerCase())) {
                String username = JSON.toJSONString(userInfo);
                subject.login(new UsernamePasswordToken(username, userInfo.getPassword()));
                System.out.println("是否拥有 - student:list:page - 权限：" + subject.isPermitted("student:list:page"));
                System.out.println("认证状态：" + subject.isAuthenticated());
                if (userInfo.getRole().equals("student")) {
                    return ResultGenerator.genSuccessResult("/message/list/student");
                }
                if (userInfo.getRole().equals("teacher")) {
                    return ResultGenerator.genSuccessResult("/message/list/teacher");
                }
                if (userInfo.getRole().equals("admin")) {
                    return ResultGenerator.genSuccessResult("/student/list");
                }
                if (userInfo.getRole().equals("superAdmin")) {
                    return ResultGenerator.genSuccessResult("/employee/list");
                }
            } else {
                return ResultGenerator.genFailResult("验证码错误！");
            }
        } catch (UnknownAccountException e) {
            System.out.println("用户名错误");
            return ResultGenerator.genFailResult("认证失败，用户不存在或用户名错误！");
        } catch (IncorrectCredentialsException e) {
            System.out.println("密码错误");
            return ResultGenerator.genFailResult("密码错误");
        } catch (UnauthorizedException e) {
            System.out.println("没有权限");
            return ResultGenerator.genFailResult("您没有此权限");
        } catch (ExpiredCredentialsException e) {
            System.out.println("账号已过期！");
            return ResultGenerator.genFailResult("账号已过期！");
        } catch (ExcessiveAttemptsException e) {
            System.out.println("登录失败次数过多！");
            return ResultGenerator.genFailResult("登录失败次数过多！");
        }
        return ResultGenerator.genFailResult("系统异常！！！！！");
    }


    /**
     * 发送验证码2
     * @return
     */
    @RequestMapping("/email/send")
    @ResponseBody
    public Result sendMail(String email,HttpSession session) {
        String randomId = IamsUtils.generateShortUuid();//生成随机码
        session.setAttribute("emailCode", randomId);//验证码存入seesion
        if (Utils.isEmpty(email)&&sendEmail(email, randomId) == 1) {//成功
            return ResultGenerator.genSuccessResult();
        }
        return ResultGenerator.genFailResult("操作失败，数据为空或者邮箱发送失败，请检查！！！");
    }

    /**
     * 校验验证码
     *
     * @return
     */
    @RequestMapping("/check")
    @ResponseBody
    public Result checkResultCode2(String code,HttpSession session) {
        String randomId = (String)session.getAttribute("emailCode");
        if (!Utils.isEmpty(code)&&!Utils.isEmpty(randomId)) {
            throw new ParameterException("验证码为空，是否输入或请检查系统是否异常");
        }
        if (code.equals(randomId)) {//成功
            return ResultGenerator.genSuccessResult();
        }
        return ResultGenerator.genFailResult("注册失败！！！数据为空或者系统验证码不一致！");
    }


    /**
     * 退出系统
     */
    @RequestMapping("/logout")
    public String logout() {
        SecurityUtils.getSubject().logout();
        System.out.println(" ------------- ");
        System.out.println(" -  用户退出  - ");
        System.out.println(" ------------- ");
        return "login";
    }


    /**
     * 验证码
     */
    @GetMapping("/kaptcha")
    public void defaultKaptcha(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        byte[] captchaOutputStream = null;
        ByteArrayOutputStream imgOutputStream = new ByteArrayOutputStream();
        try {
            //生产验证码字符串并保存到session中
            String verifyCode = captchaProducer.createText();
            httpServletRequest.getSession().setAttribute("verifyCode", verifyCode);
            BufferedImage challenge = captchaProducer.createImage(verifyCode);
            ImageIO.write(challenge, "jpg", imgOutputStream);
        } catch (IllegalArgumentException e) {
            httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        captchaOutputStream = imgOutputStream.toByteArray();
        httpServletResponse.setHeader("Cache-Control", "no-store");
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setDateHeader("Expires", 0);
        httpServletResponse.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream = httpServletResponse.getOutputStream();
        responseOutputStream.write(captchaOutputStream);
        responseOutputStream.flush();
        responseOutputStream.close();
    }

    private Object findUserInfoByRole(Integer id, Integer roleId) {
        if (RoleEnum.getRole(roleId).getName().equals("学生")) {
            return studentService.find(id);
        }
        if (RoleEnum.getRole(roleId).getName().equals("教师")) {
            return teacherService.find(id);
        }
        return null;
    }

    private int sendEmail(String email, String randomId) {
        String title = "网络作业管理系统发来一份验证码套餐，请查收~";
        String context = "请验证您的邮箱，该验证码验证码只有30分钟有效哦，验证码：" + randomId;
        return mailService.sendSimpleMail(email, title, context);
    }

}
