package com.iams.core.service;

import com.iams.core.pojo.Completion;

/**
 * <p>
 *   填空题答案 服务类
 * </p>
 *
 * @author Wei yz
 * @since 2021-02-04
 */
public interface CompletionService{

    Completion find(Integer id);

    int update(Completion completion);

    int insert(Completion completion);

    int delete(Integer id);

}
