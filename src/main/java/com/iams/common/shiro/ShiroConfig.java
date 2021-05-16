package com.iams.common.shiro;


import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Wei yz
 * @ClassName: ShiroConfig
 * @Description:
 * @date 2021/4/16 19:53
 */
@Configuration
public class ShiroConfig {

    /**
     * 配置shiro标签方言,界面需引入标签 xmlns:shiro="http://www.pollix.at/thymeleaf/shiro"
     * @return
     */
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

    /**
     *  1.创建shiroFiler,负责拦截所有请求
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //给filter工厂设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
        //配置系统受限资源
        Map<String,String> map = new HashMap<>();
        map.put("/login.html","anon");//放行登录界面
        map.put("/login","anon");//放行用户信息验证
        map.put("/register.html","anon");//放行注册界面
        map.put("/register","anon");//放行用户注册信息验证
        map.put("/teacher/add","anon");//放行教师注册信息验证
        map.put("/student/add","anon");//放行学生注册信息验证
        map.put("/check","anon");//放行邮箱验证码验证
        map.put("/email/send","anon");//放行邮箱发送
        map.put("/logout","anon");//放行退出登录
        map.put("/kaptcha","anon");//放行验证码
        //配置系统公共资源
        map.put("/static/**","anon");//放行静态资源
        // 需要验证的 /**
//        map.put("/**","authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        shiroFilterFactoryBean.setLoginUrl("/login.html");//默认认证界面路径
        return shiroFilterFactoryBean;
    }

    /**
     * 2.创建安全管理器
     * @param realm
     * @return
     */
    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("getRealm") Realm realm){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        //给安全管理器设置realm
        defaultWebSecurityManager.setRealm(realm);
        defaultWebSecurityManager.setSessionManager(sessionManager());
        return defaultWebSecurityManager;
    }

    /**
     * 3.创建自定义realm
     * @return
     */
    @Bean
    public Realm getRealm(){
        CustomerRealm realm  = new CustomerRealm();
        //修改凭证匹配器
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
        hashedCredentialsMatcher.setHashIterations(49);
        realm.setCredentialsMatcher(hashedCredentialsMatcher);
        //开启缓存管理
        realm.setCacheManager(new EhCacheManager());
        realm.setCachingEnabled(true);//开启全局缓存
        realm.setAuthorizationCachingEnabled(true);//开启认证缓存
        realm.setAuthorizationCacheName("AuthorizationCache");
        realm.setAuthenticationCachingEnabled(true);//开启授权缓存
        realm.setAuthenticationCacheName("AuthenticationCache");
        return realm;
    }

    /**
     * @Description: 开启session管理
     * @return
     */
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setGlobalSessionTimeout(1800000);//设置session的失效时长，单位毫秒
        sessionManager.setDeleteInvalidSessions(true);//删除失效的session
        return sessionManager;
    }

    /**
     *  开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator和AuthorizationAttributeSourceAdvisor)即可实现此功能
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }


}
