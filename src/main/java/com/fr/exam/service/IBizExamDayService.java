package com.fr.exam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fr.exam.entity.DO.BizExamDay;

/**
 * @author furao
 * @desc
 * @date 2021/4/11
 * @package com.fr.exam.service
 */
public interface IBizExamDayService extends IService<BizExamDay> {
    /**
     * 获取今天的考试密码
     * @return
     */
    String getTodayCode();

    /**
     * 验证授权码
     * @return
     */
    boolean checkLoginCode(String code);
}
