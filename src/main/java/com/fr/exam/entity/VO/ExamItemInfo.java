package com.fr.exam.entity.VO;

import com.fr.exam.entity.DTO.SubjectAnswer;

import java.util.List;

/**
 * @author furao
 * @desc
 * @date 2021/3/1
 * @package com.fr.exam.entity.VO
 */
public class ExamItemInfo {
    private List<SubjectAnswer> switchList;
    private List<SubjectAnswer> judgeList;

    public ExamItemInfo() {
    }

    public ExamItemInfo(List<SubjectAnswer> switchList, List<SubjectAnswer> judgeList) {
        this.switchList = switchList;
        this.judgeList = judgeList;
    }

    public List<SubjectAnswer> getSwitchList() {
        return switchList;
    }

    public void setSwitchList(List<SubjectAnswer> switchList) {
        this.switchList = switchList;
    }

    public List<SubjectAnswer> getJudgeList() {
        return judgeList;
    }

    public void setJudgeList(List<SubjectAnswer> judgeList) {
        this.judgeList = judgeList;
    }
}
