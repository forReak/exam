package com.fr.exam.entity.DTO;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author furao
 * @desc 百度接口结果
 * @date 2021/2/21
 * @package com.fr.exam.entity
 */
public class BaiDuResult {
    @JSONField(name = "error_code")
    private String errorCode;
    @JSONField(name = "error_msg")
    private String errorMsg;
    @JSONField(name = "log_id")
    private String logId;
    private String timestamp;
    private String cached;
    private FaceResult result;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getCached() {
        return cached;
    }

    public void setCached(String cached) {
        this.cached = cached;
    }

    public FaceResult getResult() {
        return result;
    }

    public void setResult(FaceResult result) {
        this.result = result;
    }
}
