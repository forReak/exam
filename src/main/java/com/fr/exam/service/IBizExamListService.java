package com.fr.exam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fr.exam.entity.DO.BizExamList;
import com.fr.exam.entity.VO.ExamInfo;

/**
 * @Description: 考试记录
 * @Author: jeecg-boot
 * @Date:   2021-02-15
 * @Version: V1.0
 */
public interface IBizExamListService extends IService<BizExamList> {
    /**
     * 生成考试试卷
     */
    ExamInfo generatorExamList(String idCard, String workTypeId);

    /**
     * 保存答案
     * @param answer 答案
     * @param sid 题目id
     * @param examId 考试题
     * @param subjectType 题型
     */
    void saveAnswer(String answer, String sid, String examId, Integer subjectType);
}
