package com.iams.common.interceptor;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Wei yz
 * @ClassName: MyMetaObjectHandler
 * @Description:   拦截器，在往数据库执行的时候拦截SQL并修改
 * @date 2021/2/2 17:52
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    /***
     * 使用mp实现添加操作,这个方法会执行,metaObject元数据(表中的名字,表中的字段)
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        //根据名称设置属性值
        this.setFieldValByName("createTime", new Date(), metaObject);
        //this.setFieldValByName("updateTime", new Date(), metaObject);
    }

    /***
     * 使用mp实现修改操作,这个方法会执行
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        //this.setFieldValByName("updateTime", new Date(), metaObject);
    }

}
