package com.iams.core.dto.assginment;

/**
 * @author Wei yz
 * @ClassName: ChiceDtoImpl
 * @Description:
 * @date 2021/5/7 12:35
 */
public class ChoiceDtoImpl extends ChoiceDto {

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

    public ChoiceDtoImpl setChildId(Integer childId) {
        this.childId = childId;
        return this;
    }

    public Integer getStudentResultId() {
        return studentResultId;
    }

    public ChoiceDtoImpl setStudentResultId(Integer studentResultId) {
        this.studentResultId = studentResultId;
        return this;
    }

    public ChoiceDtoImpl() {
        super();
    }

    @Override
    public String toString() {
        super.toString();
        return "ChiceDtoImpl{" +
                "childId=" + childId +
                ", studentResultId=" + studentResultId +
                ", relationId=" + relationId +
                ", assignmentId=" + assignmentId +
                ", typeId=" + typeId +
                ", topicId=" + topicId +
                ", multipleChoice='" + multipleChoice + '\'' +
                ", score=" + score +
                ", result='" + result + '\'' +
                ", turnout=" + turnout +
                ", remark='" + remark + '\'' +
                '}';
    }
}
