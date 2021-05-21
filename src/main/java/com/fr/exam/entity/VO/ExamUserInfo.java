package com.fr.exam.entity.VO;

/**
 * 考试页面考生信息VO
 * @author furao
 * @desc
 * @date 2021/2/28
 * @package com.fr.exam.entity.DTO
 */
public class ExamUserInfo {
    private String userName;
    private String idCard;
    private String userPic;
    private String examName;

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getUserPic() {
        return userPic;
    }

    public void setUserPic(String userPic) {
        this.userPic = userPic;
    }
}
