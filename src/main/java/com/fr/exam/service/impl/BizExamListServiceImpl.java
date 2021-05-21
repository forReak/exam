package com.fr.exam.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fr.exam.entity.DO.BizExamList;
import com.fr.exam.entity.DO.BizExamSet;
import com.fr.exam.entity.DO.BizSignList;
import com.fr.exam.entity.DO.BizSubject;
import com.fr.exam.entity.VO.ExamInfo;
import com.fr.exam.entity.DTO.SubjectAnswer;
import com.fr.exam.mapper.BizExamListMapper;
import com.fr.exam.service.IBizExamListService;
import com.fr.exam.service.IBizExamSetService;
import com.fr.exam.service.IBizSignListService;
import com.fr.exam.service.IBizSubjectService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Description: 考试记录
 * @Author: jeecg-boot
 * @Date:   2021-02-15
 * @Version: V1.0
 */
@Service
public class BizExamListServiceImpl extends ServiceImpl<BizExamListMapper, BizExamList> implements IBizExamListService {
    private static final Log logger = LogFactory.getLog(BizExamListServiceImpl.class);

    @Autowired
    IBizSignListService bizSignListService;
    @Autowired
    IBizExamSetService bizExamSetService;
    @Autowired
    IBizSubjectService bizSubjectService;

    @Override
    public ExamInfo generatorExamList(String idCard,String workTypeId) {

        //报名信息
        BizSignList bizSignList = bizSignListService.getBizSignListByIdCardAndWorkType(idCard,workTypeId);
        //判断是否生成过试题
        ExamInfo hadExamInfo = hadExam(bizSignList.getId());
        if(hadExamInfo!=null){
            //如果不是第一次考试，则返回之前的考试信息
            return hadExamInfo;
        }

        //生成试题
        BizExamList bizExamList = new BizExamList();
        bizExamList.setCreateBy(idCard);
        bizExamList.setCreateTime(new Date());
        bizExamList.setSignId(bizSignList.getId());
        bizExamList.setScore(0);
        //生成的选择题列表
        List<String> switchList = new ArrayList<>();
        //生成的判断题列表
        List<String> judgeList = new ArrayList<>();

        //获取考试设置信息
        BizExamSet examSet = bizExamSetService.getExamSetByWorkTypeId(workTypeId);
        Integer switchNum = examSet.getSwitchNum();
        Integer judgeNum = examSet.getJudgeNum();

        //生成选择题列表
        //1.获取所有当前工种选择题序号
        List<BizSubject> allSwitchIdList = bizSubjectService.lambdaQuery().select(BizSubject::getId)
                .eq(BizSubject::getWorkTypeId, workTypeId)
                .eq(BizSubject::getSubType, 1)
                .list();
        //2.获取长度，随机长度获取结果,放入结果题目列表
        Random random = new Random();
        for (int i = 0; i < switchNum; i++) {
            int k = random.nextInt(allSwitchIdList.size());
            switchList.add(allSwitchIdList.get(k).getId());
            allSwitchIdList.remove(k);
            if(allSwitchIdList.size()==0){
                break;
            }
        }
        logger.info("生成了"+switchList.size()+"个选择题");
        bizExamList.setSwitchList(JSON.toJSONString(switchList));
        //生成判断题列表
        //3.获取所有当前工种判断题序号
        List<BizSubject> allJudgeIdList = bizSubjectService.lambdaQuery().select(BizSubject::getId)
                .eq(BizSubject::getWorkTypeId, workTypeId)
                .eq(BizSubject::getSubType, 2)
                .list();
        for (int i = 0; i < judgeNum; i++) {
            int k = random.nextInt(allJudgeIdList.size());
            judgeList.add(allJudgeIdList.get(k).getId());
            allJudgeIdList.remove(k);
            if(allJudgeIdList.size()==0){
                break;
            }
        }
        logger.info("生成了"+judgeList.size()+"个判断题");
        bizExamList.setJudgeList(JSON.toJSONString(judgeList));

        //创建判断题答案结果、顺序
        List<SubjectAnswer> judgeAnswerList = new ArrayList<>();
        for (int i = 0; i < judgeList.size(); i++) {
            String s = judgeList.get(i);
            SubjectAnswer subjectAnswer = new SubjectAnswer();
            subjectAnswer.setSubjectId(s);
            subjectAnswer.setIndex(i);
            subjectAnswer.setSubjectType(2);
            judgeAnswerList.add(subjectAnswer);
        }
        bizExamList.setJudgeAnswer(JSON.toJSONString(judgeAnswerList));

        //创建选择题答题结果、顺序
        List<SubjectAnswer> switchAnswerList = new ArrayList<>();
        for (int i = 0; i < switchList.size(); i++) {
            String s = switchList.get(i);
            SubjectAnswer subjectAnswer = new SubjectAnswer();
            subjectAnswer.setSubjectId(s);
            subjectAnswer.setIndex(i+judgeList.size());
            subjectAnswer.setSubjectType(1);
            switchAnswerList.add(subjectAnswer);
        }
        bizExamList.setSwitchAnswer(JSON.toJSONString(switchAnswerList));
        //4.保存此人的考试信息
        this.save(bizExamList);

        //返回生成的题目给前端
        ExamInfo examInfo = new ExamInfo();
        examInfo.setSwitchIdList(switchList);
        examInfo.setJudgeIdList(judgeList);
        List<String> allIdList = new ArrayList<>();
        allIdList.addAll(judgeList);
        allIdList.addAll(switchList);
        examInfo.setAllIdList(allIdList);
        examInfo.setExamListId(bizExamList.getId());
        return examInfo;
    }

    @Override
    public void saveAnswer(String answer, String sid, String examId, Integer subjectType) {
        BizExamList byId = this.getById(examId);
        if(byId!=null){
            //获取数据库答题列表
            String allAnswer;
            if(subjectType==1){
                allAnswer = byId.getSwitchAnswer();
            } else{
                allAnswer = byId.getJudgeAnswer();
            }
            List<SubjectAnswer> res = JSON.parseArray(allAnswer,SubjectAnswer.class);
            //查找当前题目
            for (SubjectAnswer subjectAnswer : res) {
                if(subjectAnswer.getSubjectId().equals(sid)){
                    //设置答案
                    subjectAnswer.setAnswer(answer);
                    break;
                }
            }
            //存储当前答题对象
            if(subjectType==1){
                byId.setSwitchAnswer(JSON.toJSONString(res));
            }else{
                byId.setJudgeAnswer(JSON.toJSONString(res));
            }
            this.updateById(byId);
        }else{
            throw new RuntimeException("当前考生没有创建考试题!");
        }
    }

    /**
     * 查看当前考生是否是第一次考试
     * @param signId
     * @return
     */
    public ExamInfo hadExam(String signId){
        List<BizExamList> list = this.lambdaQuery().eq(BizExamList::getSignId, signId).list();
        if(list!=null && list.size()>0){
            ExamInfo examInfo = new ExamInfo();
            String switchListStr = list.get(0).getSwitchList();
            String judgeListStr = list.get(0).getJudgeList();
            //选择题列表
            List<String> switchList = JSON.parseArray(switchListStr, String.class);
            //判断题列表
            List<String> judgeList = JSON.parseArray(judgeListStr, String.class);
            //综合列表
            List<String> allList = new ArrayList<>();
            allList.addAll(judgeList);
            allList.addAll(switchList);
            examInfo.setSwitchIdList(switchList);
            examInfo.setJudgeIdList(judgeList);
            examInfo.setAllIdList(allList);
            examInfo.setExamListId(list.get(0).getId());
            return examInfo;
        }else{
            return null;
        }
    }
}
