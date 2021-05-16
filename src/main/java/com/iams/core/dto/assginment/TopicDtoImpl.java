package com.iams.core.dto.assginment;

/**
 * @author Wei yz
 * @ClassName: TopicDtoImpl
 * @Description:
 * @date 2021/5/7 13:16
 */
public class TopicDtoImpl extends TopicDto {

    /**
     * 答案号
     */
    private Integer childId;

    /**
     * 学生答案关系id
     */
    private Integer studentResultId;

    public Integer getChildId() {
        return childId;
    }

    public TopicDtoImpl setChildId(Integer childId) {
        this.childId = childId;
        return this;
    }

    public Integer getStudentResultId() {
        return studentResultId;
    }

    public TopicDtoImpl setStudentResultId(Integer studentResultId) {
        this.studentResultId = studentResultId;
        return this;
    }

    @Override
    public String toString() {
        return "TopicDtoImpl{" +
                "childId=" + childId +
                ", studentResultId=" + studentResultId +
                ", relationId=" + relationId +
                ", assignmentId=" + assignmentId +
                ", typeId=" + typeId +
                ", topicId=" + topicId +
                ", title='" + title + '\'' +
                ", score=" + score +
                ", result='" + result + '\'' +
                ", turnout=" + turnout +
                ", remark='" + remark + '\'' +
                '}';
    }
}
