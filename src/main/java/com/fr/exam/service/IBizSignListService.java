package com.fr.exam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fr.exam.entity.DO.BizSignList;

import java.util.List;


/**
 * @Description: 报名信息
 * @Author: jeecg-boot
 * @Date:   2021-02-15
 * @Version: V1.0
 */
public interface IBizSignListService extends IService<BizSignList> {

    /**
     * 根据身份证号获取考试信息
     * @param idCard
     * @return
     */
    List<BizSignList> getBizSignListByIdCard(String idCard);

    /**
     * 检查身份信息是否存在
     * @param idCard
     * @return
     */
    boolean checkIdCardExist(String idCard);

    /**
     *
     * @param idCard
     * @param workTypeId
     * @return
     */
    BizSignList getBizSignListByIdCardAndWorkType(String idCard, String workTypeId);
}
