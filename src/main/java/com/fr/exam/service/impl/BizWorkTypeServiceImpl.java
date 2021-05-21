package com.fr.exam.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fr.exam.entity.DO.BizWorkType;
import com.fr.exam.mapper.BizWorkTypeMapper;
import com.fr.exam.service.IBizWorkTypeService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: biz_work_type
 * @Author: jeecg-boot
 * @Date:   2021-02-10
 * @Version: V1.0
 */
@Service
public class BizWorkTypeServiceImpl extends ServiceImpl<BizWorkTypeMapper, BizWorkType> implements IBizWorkTypeService {


    /**
     * 获取工种map
     * @return
     */
    @Override
    public Map<String, String> getWorkTypeMap() {
        List<BizWorkType> list = this.list();
        Map<String,String> result = new HashMap<>();
        list.forEach( e -> {
            result.put(e.getId(),e.getWorkTypeName());
        });
        return result;
    }
}
