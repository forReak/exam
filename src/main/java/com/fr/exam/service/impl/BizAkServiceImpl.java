package com.fr.exam.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fr.exam.entity.DO.BizAk;
import com.fr.exam.mapper.BizAkMapper;
import com.fr.exam.service.IBizAkService;
import org.springframework.stereotype.Service;

/**
 * @author furao
 * @desc
 * @date 2021/2/21
 * @package com.fr.exam.service.impl
 */
@Service
public class BizAkServiceImpl extends ServiceImpl<BizAkMapper, BizAk> implements IBizAkService {
}
