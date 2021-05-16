package com.iams.core.service;

import com.iams.core.pojo.Subjective;

/**
 * <p>
 *   主观题答案 服务类
 * </p>
 *
 * @author Wei yz
 * @since 2021-02-04
 */
public interface SubjectiveService{

    Subjective find(Integer id);

    int update(Subjective subjective);

    int insert(Subjective subjective);

    int delete(Integer id);
}
