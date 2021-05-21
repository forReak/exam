package com.fr.exam.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fr.exam.entity.DO.BizSignList;
import com.fr.exam.mapper.BizSignListMapper;
import com.fr.exam.service.IBizSignListService;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class BizSignListServiceImpl extends ServiceImpl<BizSignListMapper, BizSignList> implements IBizSignListService {

    @Override
    public List<BizSignList> getBizSignListByIdCard(String idCard) {
        return this.lambdaQuery().eq(BizSignList::getHasExam,0).eq(BizSignList::getIdCard,idCard).between(BizSignList::getExamTime,getStartToDay(),getEndToDay()).list();
    }

    @Override
    public boolean checkIdCardExist(String idCard) {
        List<BizSignList> list = getBizSignListByIdCard(idCard);
        return list != null && list.size() > 0;
    }

    @Override
    public BizSignList getBizSignListByIdCardAndWorkType(String idCard, String workTypeId) {
        List<BizSignList> list = this.lambdaQuery()
                .eq(BizSignList::getIdCard, idCard)
                .eq(BizSignList::getWorkTypeId,workTypeId)
                .eq(BizSignList::getHasExam,0)
                .between(BizSignList::getExamTime, getStartToDay(), getEndToDay())
                .list();
        if(list.size()==0){
            throw new RuntimeException("没有您的报名信息！");
        }else{
            //todo 不能重复报名
            return list.get(0);
        }
    }

    //今日零点
    public static Date getStartToDay(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }
    //今日23点59分59秒
    public static Date getEndToDay(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

}
