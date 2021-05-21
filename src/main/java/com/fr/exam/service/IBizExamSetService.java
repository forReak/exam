package com.fr.exam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fr.exam.entity.DO.BizExamSet;

/**
 * @Description: biz_exam_set
 * @Author: jeecg-boot
 * @Date:   2021-02-10
 * @Version: V1.0
 */
public interface IBizExamSetService extends IService<BizExamSet> {

    /**
     * 根据工种获取考试设置
     * @param workTypeId
     * @return
     */
    BizExamSet getExamSetByWorkTypeId(String workTypeId);

}
