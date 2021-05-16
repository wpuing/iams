package com.iams.core.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iams.common.constant.IamsConstants;
import com.iams.common.exception.ParameterException;
import com.iams.common.util.LayResult;
import com.iams.common.util.ResultGenerator;
import com.iams.common.util.Utils;

import java.util.List;

/**
 * @author Wei yz
 * @ClassName: BaseService
 * @Description:   基础业务类
 * @date 2021/2/5 12:14
 */
public class BaseService {

    //邮箱验证

    /**
     * 日期范围查询
     * @param wrapper  查询器
     * @param startTime  开始日期
     * @param endTime  结束日期
     */
    public static void dateRange(LambdaQueryWrapper wrapper, String startTime, String endTime){
        //创建日期
        if (Utils.isEmpty(startTime)) {//开始时间
            wrapper.apply("date_format(create_time,'%Y-%m-%d')>={0}", startTime);
        }
        if (Utils.isEmpty(endTime)) {//结束
            wrapper.apply("date_format(create_time,'%Y-%m-%d')<={0}", endTime);
        }
    }


    /**
     * 检查分页参数，为空或为0时赋默认值
     * @param pageNum
     * @param pageSize
     */
    public static void checkPage(Integer pageNum,Integer pageSize){
        //当前页
        if(!Utils.isEmpty(pageNum)){//空或为0
            pageNum= IamsConstants.PAGE_NUM;
        }
        //每页数
        if(!Utils.isEmpty(pageSize)){//空或为0
            pageSize= IamsConstants.PAGE_SIZE;
        }

    }

    /**
     * 批量删除
     * @param ids id串，以,分隔
     * @param baseMapper 数据mapper
     * @return
     */
    public static int deleteByIds(String ids, BaseMapper baseMapper){
        List<Integer> idList = Utils.getIds(ids);
        if(idList==null || idList.size()<1){
           throw new ParameterException("批量删除失败，id集合为空！");
        }
        return baseMapper.deleteBatchIds(idList);
    }

    public static <T> LayResult findIds(List<Integer> ids, Integer pageNum, Integer pageSize, BaseMapper<T> baseMapper){
        if(ids==null || ids.size()<1){
            throw new ParameterException("查询的数据为空！");
        }
        Page<T> page = new Page<>(pageNum,pageSize,true);
        QueryWrapper<T> wrapper = Wrappers.<T>query();
        wrapper.in("id",ids);
        IPage<T> iPage = baseMapper.selectPage(page,wrapper);
        return ResultGenerator.getData(iPage.getRecords(),iPage.getTotal());
    }








}
