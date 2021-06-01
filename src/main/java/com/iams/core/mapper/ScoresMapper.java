package com.iams.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iams.core.dto.scores.StudentScoresDetails;
import com.iams.core.pojo.Scores;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Wei yz
 * @since 2021-02-04
 */
@Repository
public interface ScoresMapper extends BaseMapper<Scores> {

    List<StudentScoresDetails> selectScoresByNumber(Map<String, Object> paramsMap);

//    /**
//     * 查询该作业的最大值和最小值
//     * @param assignmentId
//     * @return
//     */
//    List<Map<String,Object>> selectMaxAndMin(Integer assignmentId);

//    /**
//     * 查询总分
//     * @param assignmentId
//     * @return
//     */
//    Float selectSumScore(Integer assignmentId);

}
