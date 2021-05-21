package com.fr.exam.entity.VO;

/**
 * @author furao
 * @desc
 * @date 2021/2/19
 * @package com.fr.exam.entity
 */
public class User {

    private String idCard;

    private String password;

    private String userIdCardPic;

    private String userLivePic;

    private String loginCode;

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getUserIdCardPic() {
        return userIdCardPic;
    }

    public void setUserIdCardPic(String userIdCardPic) {
        this.userIdCardPic = userIdCardPic;
    }

    public String getUserLivePic() {
        return userLivePic;
    }

    public void setUserLivePic(String userLivePic) {
        this.userLivePic = userLivePic;
    }
}
