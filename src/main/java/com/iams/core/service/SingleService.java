package com.iams.core.service;

import com.iams.core.pojo.Single;

/**
 * <p>
 * 单选题答案 服务类
 * </p>
 *
 * @author Wei yz
 * @since 2021-02-04
 */
public interface SingleService {

    Single find(Integer id);

    int update(Single single);

    int insert(Single single);

    int delete(Integer id);

}
