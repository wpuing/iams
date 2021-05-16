package com.iams.core.service;

import com.iams.core.pojo.Judgment;

/**
 * <p>
 *   判断题答案 服务类
 * </p>
 *
 * @author Wei yz
 * @since 2021-02-04
 */
public interface JudgmentService{

    Judgment find(Integer id);

    int update(Judgment judgment);

    int insert(Judgment judgment);

    int delete(Integer id);
}
