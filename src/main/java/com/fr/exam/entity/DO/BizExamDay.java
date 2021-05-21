package com.fr.exam.entity.DO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author furao
 * @desc
 * @date 2021/4/11
 * @package com.fr.exam.entity.DO
 */
@TableName("biz_exam_day")
public class BizExamDay {
    /**主键*/
    @TableId(type = IdType.ASSIGN_ID)
    private java.lang.String id;
    /**创建人*/
    private java.lang.String createBy;
    /**创建日期*/
    private java.util.Date createTime;
    /**更新人*/
    private java.lang.String updateBy;
    /**更新日期*/
    private java.util.Date updateTime;
    /**考试日*/
    private java.util.Date examDay;

    private java.lang.String dayPassword;

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

    public Date getExamDay() {
        return examDay;
    }

    public void setExamDay(Date examDay) {
        this.examDay = examDay;
    }

    public String getDayPassword() {
        return dayPassword;
    }

    public void setDayPassword(String dayPassword) {
        this.dayPassword = dayPassword;
    }
}
