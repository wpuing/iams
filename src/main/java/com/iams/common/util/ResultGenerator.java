package com.iams.common.util;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  @author: Wei yz
 *  @Date: 2021/2/1 17:13
 *  @Description: 返回类型生成器
 */
public class ResultGenerator {

	public static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";

    public static Result genSuccessResult() {
        return new Result()
                .setCode(ResultCode.SUCCESS)
                .setMessage(DEFAULT_SUCCESS_MESSAGE);
    }

    public static Result genSuccessResult(Object data) {
        return new Result()
                .setCode(ResultCode.SUCCESS)
                .setMessage(DEFAULT_SUCCESS_MESSAGE)
                .setData(data);
    }

    public static Result genFailResult(String message) {
        return new Result()
                .setCode(ResultCode.FAIL)
                .setMessage(message);
    }

    public static LayResult getData(List list,long total){
        String msg = "数据为空";//消息
        Integer code = 1;//code为0则成功
        if(list!=null && list.size()>0){
            msg = "查询成功";
            code = 0;
        }
        return new LayResult().setMsg(msg).setData(list).setCode(code).setCount(total);
    }

    public static LayImageResult getImgData(String url,String imgName){
        String msg = "数据为空";//消息
        Integer code = 1;//code为0则成功
        Map<String,String> map= new HashMap<>();
        if(Utils.isEmpty(imgName)&&Utils.isEmpty(url)){
            msg = "查询成功";
            code = 0;
            map.put("src",url);
            map.put("title",imgName);
        }
        return new LayImageResult().setMsg(msg).setData(map).setCode(code);
    }
}
