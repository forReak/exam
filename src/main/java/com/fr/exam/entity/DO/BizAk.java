package com.fr.exam.entity.DO;

import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

/**
 * @author furao
 * @desc
 * @date 2021/2/21
 * @package com.fr.exam.entity
 */
@TableName("biz_ak")
public class BizAk {
    private String id;
    private String ak;
    private Date updateTime;

    public BizAk() {
    }

    public BizAk(String id, String ak, Date updateTime) {
        this.id = id;
        this.ak = ak;
        this.updateTime = updateTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAk() {
        return ak;
    }

    public void setAk(String ak) {
        this.ak = ak;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
