package com.fr.exam.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fr.exam.entity.DO.BizExamingTime;
import com.fr.exam.mapper.BizExamingTimeMapper;
import com.fr.exam.service.IBizExamingTimeService;
import org.springframework.stereotype.Service;

/**
 * @author furao
 * @desc
 * @date 2021/4/3
 * @package com.fr.exam.service.impl
 */
@Service
public class BizExamingTimeServiceImpl extends ServiceImpl<BizExamingTimeMapper, BizExamingTime> implements IBizExamingTimeService {
}
