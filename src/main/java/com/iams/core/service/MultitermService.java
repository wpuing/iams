package com.iams.core.service;

import com.iams.core.pojo.Multiterm;

/**
 * <p>
 *   多选题答案 服务类
 * </p>
 *
 * @author Wei yz
 * @since 2021-02-04
 */
public interface MultitermService{

    Multiterm find(Integer id);

    int update(Multiterm multiterm);

    int insert(Multiterm multiterm);

    int delete(Integer id);
}
