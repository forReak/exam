package com.fr.exam.entity.VO;

import java.util.List;

/**
 * @author furao
 * @desc
 * @date 2021/2/28
 * @package com.fr.exam.entity.VO
 */
public class ExamInfo {
    private List<String> switchIdList;
    private List<String> switchAnswerList;
    private List<String> judgeIdList;
    private List<String> judgeAnswerList;
    private List<String> allIdList;
    private String examListId;

    public String getExamListId() {
        return examListId;
    }

    public void setExamListId(String examListId) {
        this.examListId = examListId;
    }

    public List<String> getAllIdList() {
        return allIdList;
    }

    public void setAllIdList(List<String> allIdList) {
        this.allIdList = allIdList;
    }

    public List<String> getSwitchIdList() {
        return switchIdList;
    }

    public void setSwitchIdList(List<String> switchIdList) {
        this.switchIdList = switchIdList;
    }

    public List<String> getSwitchAnswerList() {
        return switchAnswerList;
    }

    public void setSwitchAnswerList(List<String> switchAnswerList) {
        this.switchAnswerList = switchAnswerList;
    }

    public List<String> getJudgeIdList() {
        return judgeIdList;
    }

    public void setJudgeIdList(List<String> judgeIdList) {
        this.judgeIdList = judgeIdList;
    }

    public List<String> getJudgeAnswerList() {
        return judgeAnswerList;
    }

    public void setJudgeAnswerList(List<String> judgeAnswerList) {
        this.judgeAnswerList = judgeAnswerList;
    }
}
