package com.fr.exam.entity.DO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;


import java.io.Serializable;

/**
 * @Description: biz_exam_set
 * @Author: jeecg-boot
 * @Date:   2021-02-10
 * @Version: V1.0
 */
@TableName("biz_exam_set")
public class BizExamSet implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    private String id;
	/**所属工种id*/

    private String workTypeId;
	/**考试时长*/

    private Integer time;
	/**试卷总分*/

    private Integer totalScore;
	/**分数线*/

    private Integer passScore;
	/**选择题分值*/

    private Integer switchScore;
	/**选择题数量*/

    private Integer switchNum;
	/**判断题分值*/

    private Integer judgeScore;
	/**判断题数量*/

    private Integer judgeNum;
	/**开始考试时间 HH:MM:ss*/

    private String beginExamTime;
	/**结束考试时间*/

    private String endExamTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWorkTypeId() {
        return workTypeId;
    }

    public void setWorkTypeId(String workTypeId) {
        this.workTypeId = workTypeId;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    public Integer getPassScore() {
        return passScore;
    }

    public void setPassScore(Integer passScore) {
        this.passScore = passScore;
    }

    public Integer getSwitchScore() {
        return switchScore;
    }

    public void setSwitchScore(Integer switchScore) {
        this.switchScore = switchScore;
    }

    public Integer getSwitchNum() {
        return switchNum;
    }

    public void setSwitchNum(Integer switchNum) {
        this.switchNum = switchNum;
    }

    public Integer getJudgeScore() {
        return judgeScore;
    }

    public void setJudgeScore(Integer judgeScore) {
        this.judgeScore = judgeScore;
    }

    public Integer getJudgeNum() {
        return judgeNum;
    }

    public void setJudgeNum(Integer judgeNum) {
        this.judgeNum = judgeNum;
    }

    public String getEndExamTime() {
        return endExamTime;
    }

    public void setEndExamTime(String endExamTime) {
        this.endExamTime = endExamTime;
    }

    public String getBeginExamTime() {
        return beginExamTime;
    }

    public void setBeginExamTime(String beginExamTime) {
        this.beginExamTime = beginExamTime;
    }
}
