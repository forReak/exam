package com.fr.exam.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fr.exam.entity.DO.BizExamDay;
import com.fr.exam.mapper.BizExamDayMapper;
import com.fr.exam.service.IBizExamDayService;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author furao
 * @desc
 * @date 2021/4/11
 * @package com.fr.exam.service.impl
 */
@Service
public class BizExamDayServiceImpl extends ServiceImpl<BizExamDayMapper, BizExamDay> implements IBizExamDayService {
    @Override
    public String getTodayCode() {
        String todayZero = getDate();
        List<BizExamDay> list = this.lambdaQuery().eq(BizExamDay::getExamDay, todayZero).list();
        if(list!=null && list.size()>0){
            return list.get(0).getDayPassword();
        }else{
            return null;
        }
    }

    private String getDate() {
        Date date = new Date();
        SimpleDateFormat sdfzoro = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        String format1 = sdfzoro.format(date);
        Date parse = null;
//        try {
//            parse = sdfzoro.parse(format1);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        return format1;
    }

    @Override
    public boolean checkLoginCode(String code) {

        if(code==null){
            throw new RuntimeException("没有输入授权码！");
        }
        code = code.trim();
        if(code.length()==0){
            throw new RuntimeException("没有输入授权码！");
        }

        String todayCode = getTodayCode();
        if(todayCode!=null){
            if(todayCode.equals(code)){
                return true;
            }else{
                return false;
            }
        }else{
            throw new RuntimeException("管理员没有设置今天为考试日！");
        }
    }
}
