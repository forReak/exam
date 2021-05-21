package com.fr.exam.entity.DO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 考试记录
 * @Author: jeecg-boot
 * @Date:   2021-02-15
 * @Version: V1.0
 */
@Data
@TableName("biz_exam_list")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class BizExamList implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    private String id;
	/**创建人*/
    private String createBy;
	/**考试日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
	/**更新人*/
    private String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
	/**选择题列表*/
    private String switchList;
	/**判断题列表*/
    private String judgeList;
	/**选择题答案*/
    private String switchAnswer;
	/**判断题答案*/
    private String judgeAnswer;
	/**分数*/
    private Integer score;
	/**报名id*/
    private String signId;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getSwitchList() {
        return switchList;
    }

    public void setSwitchList(String switchList) {
        this.switchList = switchList;
    }

    public String getJudgeList() {
        return judgeList;
    }

    public void setJudgeList(String judgeList) {
        this.judgeList = judgeList;
    }

    public String getSwitchAnswer() {
        return switchAnswer;
    }

    public void setSwitchAnswer(String switchAnswer) {
        this.switchAnswer = switchAnswer;
    }

    public String getJudgeAnswer() {
        return judgeAnswer;
    }

    public void setJudgeAnswer(String judgeAnswer) {
        this.judgeAnswer = judgeAnswer;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getSignId() {
        return signId;
    }

    public void setSignId(String signId) {
        this.signId = signId;
    }
}
