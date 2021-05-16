package com.iams.common.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Wei yz
 * @ClassName: MybatisPlusConfig
 * @Description: mybatis-plus 配置类
 * @date 2021/1/11 18:20
 */
@Configuration
public class MybatisPlusConfig {

    /**
     *  分页插件
     *  用法
     * @RequestMapping("/list1")
     * public void selectPage() {
     *
     *     QueryWrapper<User> wrapper = new QueryWrapper<>();
     *     wrapper.ge("age",0);
     *     Page<User> page = new Page<>(1, 2);
     *     IPage<User> userIPage = userMapper.selectPage(page, wrapper);
     *     System.out.println("总条数"+userIPage.getTotal());
     *     System.out.println("总页数"+userIPage.getPages());
     *
     * }
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {


        return new PaginationInterceptor();
    }



}
