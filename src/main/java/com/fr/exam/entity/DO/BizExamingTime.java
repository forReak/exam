package com.fr.exam.entity.DO;

import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

/**
 * 记录每次考试的考试时长
 * 页面打开按秒计算
 * @author furao
 * @desc
 * @date 2021/4/3
 * @package com.fr.exam.entity.DO
 */
@TableName("biz_examing_time")
public class BizExamingTime {

    private String id;

    private Integer time;

    private Date createTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }
}
