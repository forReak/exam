package com.fr.exam.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.fr.exam.entity.DO.BizExamSet;
import com.fr.exam.mapper.BizExamSetMapper;
import com.fr.exam.service.IBizExamSetService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BizExamSetServiceImpl extends ServiceImpl<BizExamSetMapper, BizExamSet> implements IBizExamSetService {

    @Override
    public BizExamSet getExamSetByWorkTypeId(String workTypeId) {
        List<BizExamSet> list = this.lambdaQuery().eq(BizExamSet::getWorkTypeId, workTypeId).list();
        if(list.size()==0){
            BizExamSet bizExamSet = new BizExamSet();
            bizExamSet.setBeginExamTime("09:00:00");
            bizExamSet.setEndExamTime("18:00:00");
            bizExamSet.setTime(120);
            bizExamSet.setPassScore(80);
            bizExamSet.setSwitchNum(30);
            bizExamSet.setJudgeNum(70);
            bizExamSet.setSwitchScore(1);
            bizExamSet.setJudgeScore(1);
            bizExamSet.setTotalScore(100);
            return bizExamSet;
        }else{
            return list.get(0);
        }
    }
}
