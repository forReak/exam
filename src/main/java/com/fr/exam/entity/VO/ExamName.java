package com.fr.exam.entity.VO;

/**
 * @author furao
 * @desc
 * @date 2021/2/25
 * @package com.fr.exam.entity.VO
 */
public class ExamName {
    private String id;
    private String workTypeName;
    private Integer time;
    private Integer score;
    private Integer num;

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWorkTypeName() {
        return workTypeName;
    }

    public void setWorkTypeName(String workTypeName) {
        this.workTypeName = workTypeName;
    }
}
