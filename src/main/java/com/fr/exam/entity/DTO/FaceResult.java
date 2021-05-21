package com.fr.exam.entity.DTO;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * @author furao
 * @desc 人脸识别结果
 * @date 2021/2/21
 * @package com.fr.exam.entity
 */
public class FaceResult {
    private Integer score;
    @JSONField(name = "face_list")
    private List<FaceToken> faceList;

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public List<FaceToken> getFaceList() {
        return faceList;
    }

    public void setFaceList(List<FaceToken> faceList) {
        this.faceList = faceList;
    }
}

class FaceToken{
    @JSONField(name="face_token")
    private String faceToken;

    public String getFaceToken() {
        return faceToken;
    }

    public void setFaceToken(String faceToken) {
        this.faceToken = faceToken;
    }
}
