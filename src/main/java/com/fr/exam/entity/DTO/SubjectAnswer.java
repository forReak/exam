package com.fr.exam.entity.DTO;

/**
 * 单个题目答题情况
 * @author furao
 * @desc
 * @date 2021/3/1
 * @package com.fr.exam.entity.VO
 */
public class SubjectAnswer {
    private Integer index;
    private String subjectId;
    private String answer;
    private Integer subjectType;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(Integer subjectType) {
        this.subjectType = subjectType;
    }
}
