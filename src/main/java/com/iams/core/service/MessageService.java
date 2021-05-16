package com.iams.core.service;

import com.iams.common.util.LayResult;
import com.iams.core.dto.MessageUserDto;
import com.iams.core.pojo.Message;

/**
 * <p>
 * 留言 服务类
 * </p>
 *
 * @author Wei yz
 * @since 2021-02-04
 */
public interface MessageService {

    Message find(Integer id);

    /**
     * 查询留言列表
     * @param condition 模糊条件 标题/内容
     * @param status 状态 未解决 已解决
     * @param type 用户类型 学生student 教师teacher
     * @param userId 用户id
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param pageNum 当前页
     * @param pageSize 每页数
     * @return
     */
    LayResult find(String condition, String status,String type,Integer userId, String startTime, String endTime, Integer pageNum, Integer pageSize);

    /**
     * 查询留言人信息
     * @param role 角色
     * @param id 编号
     * @return
     */
    MessageUserDto getUserInfo(String role, Integer id);

    int insert(Message message);

    int update(Message message);

    int delete(Integer id);

}
