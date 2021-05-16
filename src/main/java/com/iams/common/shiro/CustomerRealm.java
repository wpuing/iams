package com.iams.common.shiro;


import com.alibaba.fastjson.JSON;
import com.iams.common.util.IamsUtils;
import com.iams.core.dto.UserInfo;
import com.iams.core.pojo.*;
import com.iams.core.service.EmployeeService;
import com.iams.core.service.PermissionService;
import com.iams.core.service.StudentService;
import com.iams.core.service.TeacherService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wei yz
 * @ClassName: CustomerRealm
 * @Description:
 * @date 2021/4/16 20:08
 */
public class CustomerRealm extends AuthorizingRealm {

    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private PermissionService permissionService;

    /**
     * 原始用户信息
     */
    private Object originalInfo;

    /**
     * 用户输入的信息
     */
    private UserInfo userInfo;

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获得身份信息
        String primaryPrincipal = (String) principalCollection.getPrimaryPrincipal();
        System.out.println("授权用户数据："+primaryPrincipal);
        //根据username查询信息
        //this.userInfo = (UserInfo) getUserInfo(primaryPrincipal, true);
        System.out.println("===>>> 调用授权操作");
        System.out.println("授权时的用户数据："+userInfo);
        //获得角色和权限
        if (!ObjectUtils.isEmpty(userInfo)) {
            SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
            simpleAuthorizationInfo.addRole(userInfo.getRole());//添加角色
            //添加权限
            List<Permission> permissions = permissionService.findRolePermission(RoleEnum.getRoleId(userInfo.getRole()));
            if (!CollectionUtils.isEmpty(permissions)) {
                permissions.forEach(p -> {
                    simpleAuthorizationInfo.addStringPermission(p.getPerCode());
                });
            }
            return simpleAuthorizationInfo;
        }
        return null;
    }

    /**
     * 认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String principal = (String) authenticationToken.getPrincipal();//得到用户信息
        this.userInfo = (UserInfo) getUserInfo(principal, true);
        //存入session
        this.originalInfo = getUserInfo(principal, false);
        this.setSession("userInfo",getUserInfo(principal,false));
        System.out.println("认证用户数据 userInfo：" + this.userInfo);
        System.out.println("认证用户数据 originalInfo：" + this.originalInfo);
        if (!ObjectUtils.isEmpty(this.originalInfo)) {//判断
            return new SimpleAuthenticationInfo(this.userInfo.getUsername(), this.userInfo.getPassword(), ByteSource.Util.bytes(this.userInfo.getSalt()), this.getName());
        }
        return null;
    }


    /**
     * 将一些数据放到ShiroSession中,以便于其它地方使用
     * 比如Controller,使用时直接用HttpSession.getAttribute(key)就可以取到
     */
    private void setSession(Object key, Object value) {
        Subject currentUser = SecurityUtils.getSubject();
        if (null != currentUser) {
            Session session = currentUser.getSession();
            System.out.println("Session默认超时时间为[" + session.getTimeout() + "]毫秒");
            System.out.println("Session是[" + session.getId() + "]");
            if (null != session) {
                session.setAttribute(key, value);
            }
        }
    }

    private Object getUserInfo(String principal, boolean flag) {
        UserInfo principalUserInfo = JSON.parseObject(principal, UserInfo.class);//转换
        System.out.println("用户传入的数据：" + principalUserInfo);
        List<Student> studentList = new ArrayList<>();
        List<Teacher> teacherList = new ArrayList<>();
        List<Employee> employeeList = new ArrayList<>();
        //查询用户信息
        if (principalUserInfo.getRole().equals("student")) {//学生
            studentList = studentService.find(principalUserInfo.getUsername());
        }
        if (principalUserInfo.getRole().equals("teacher")) {//教师
            teacherList = teacherService.find(principalUserInfo.getUsername());
        }
        if (principalUserInfo.getRole().equals("admin") || principalUserInfo.getRole().equals("superAdmin")) {//员工
            employeeList = employeeService.find(principalUserInfo.getUsername());
        }
        return IamsUtils.extract(principalUserInfo, studentList, teacherList, employeeList, flag);
    }
}
