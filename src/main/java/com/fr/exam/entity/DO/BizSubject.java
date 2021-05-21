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
 * @Description: biz_subject
 * @Author: jeecg-boot
 * @Date:   2021-02-10
 * @Version: V1.0
 */
@Data
@TableName("biz_subject")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class BizSubject implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    private String id;
	/**所属工种id*/
    private String workTypeId;
	/**题型*/
    private Integer subType;
	/**题干*/
    private String subDesc;
	/**选择题答案*/
    private String switchResult;
	/**判断题答案(y,n)*/
    private String judgeResult;
	/**a选项*/
    private String resultA;
	/**b选项*/
    private String resultB;
	/**c选项*/
    private String resultC;
    /**a图片*/
    private String picA;
    /**b图片*/
    private String picB;
    /**c图片*/
    private String picC;
	/**正确选项*/
    private String resultYes;
	/**错误选项*/
    private String resultNo;
	/**createTime*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createTime;
	/**createBy*/
    private String createBy;
	/**updateTime*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date updateTime;
	/**updateBy*/
    private String updateBy;

    public String getPicA() {
        return picA;
    }

    public void setPicA(String picA) {
        this.picA = picA;
    }

    public String getPicB() {
        return picB;
    }

    public void setPicB(String picB) {
        this.picB = picB;
    }

    public String getPicC() {
        return picC;
    }

    public void setPicC(String picC) {
        this.picC = picC;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

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

    public Integer getSubType() {
        return subType;
    }

    public void setSubType(Integer subType) {
        this.subType = subType;
    }

    public String getSubDesc() {
        return subDesc;
    }

    public void setSubDesc(String subDesc) {
        this.subDesc = subDesc;
    }

    public String getSwitchResult() {
        return switchResult;
    }

    public void setSwitchResult(String switchResult) {
        this.switchResult = switchResult;
    }

    public String getJudgeResult() {
        return judgeResult;
    }

    public void setJudgeResult(String judgeResult) {
        this.judgeResult = judgeResult;
    }

    public String getResultA() {
        return resultA;
    }

    public void setResultA(String resultA) {
        this.resultA = resultA;
    }

    public String getResultB() {
        return resultB;
    }

    public void setResultB(String resultB) {
        this.resultB = resultB;
    }

    public String getResultC() {
        return resultC;
    }

    public void setResultC(String resultC) {
        this.resultC = resultC;
    }

    public String getResultYes() {
        return resultYes;
    }

    public void setResultYes(String resultYes) {
        this.resultYes = resultYes;
    }

    public String getResultNo() {
        return resultNo;
    }

    public void setResultNo(String resultNo) {
        this.resultNo = resultNo;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }
}
