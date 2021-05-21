package com.fr.exam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fr.exam.entity.DO.BizWorkType;

import java.util.Map;

/**
 * @Description: biz_work_type
 * @Author: jeecg-boot
 * @Date:   2021-02-10
 * @Version: V1.0
 */
public interface IBizWorkTypeService extends IService<BizWorkType> {

    Map<String,String> getWorkTypeMap();
}
