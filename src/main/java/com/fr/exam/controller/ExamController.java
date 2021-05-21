package com.fr.exam.controller;

import com.alibaba.fastjson.JSON;
import com.fr.exam.entity.DO.*;
import com.fr.exam.entity.DTO.SubjectAnswer;
import com.fr.exam.entity.DTO.UserTime;
import com.fr.exam.entity.VO.*;
import com.fr.exam.service.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author furao
 * @desc
 * @date 2021/2/24
 * @package com.fr.exam.controller
 */
@Controller
@RequestMapping("/exam")
public class ExamController {
    private static final Log logger = LogFactory.getLog(ExamController.class);

    @Autowired
    IBizSignListService bizSignListService;
    @Autowired
    IBizWorkTypeService bizWorkTypeService;
    @Autowired
    IBizExamListService bizExamListService;
    @Autowired
    IBizSubjectService bizSubjectService;
    @Autowired
    IBizExamingTimeService bizExamingTimeService;
    @Autowired
    IBizExamSetService bizExamSetService;

    @RequestMapping("/list")
    public String examList(Model model){
        Object principal = SecurityUtils.getSubject().getPrincipal();
        if(principal instanceof String){
            String idCard = (String)principal;
            //根据身份证获取考试信息
            List<BizSignList> bizSignListByIdCard = bizSignListService.getBizSignListByIdCard(idCard);
            //返回的实体类
            List<ExamName> examList = new ArrayList<>();
            //工种类型
            Map<String, String> workTypeMap = bizWorkTypeService.getWorkTypeMap();
            bizSignListByIdCard.forEach(e -> {
                ExamName exam = new ExamName();
                exam.setId(e.getWorkTypeId());
                exam.setWorkTypeName(workTypeMap.get(e.getWorkTypeId()) + " 考试题");
                BizExamSet set = bizExamSetService.getExamSetByWorkTypeId(e.getWorkTypeId());
                Integer time = set.getTime();
                Integer totalScore = set.getTotalScore();
                exam.setNum(set.getSwitchNum()+set.getJudgeNum());
                exam.setTime(time);
                exam.setScore(totalScore);
                examList.add(exam);
            });
            model.addAttribute("examList",examList);
        }else{
            return "error";
        }
        return "examList";
    }

    /**
     * 考试开始readme Page
     * @param workTypeId 工种
     * @param model
     * @return
     */
    @RequestMapping("/readmePage")
    public String readmePage(String workTypeId,Model model){
        model.addAttribute("workTypeId",workTypeId);
        return "readme";
    }

    /**
     * 我同意考试纪律，生成试卷
     * @param workTypeId
     * @param model
     * @return
     */
    @RequestMapping("/examQuestionsPage")
    public String examQuestaions(String workTypeId,Model model){
        model.addAttribute("workTypeId",workTypeId);
        //获取考试信息
        Object principal = SecurityUtils.getSubject().getPrincipal();
        String idCard = (String) principal;
        List<BizSignList> bizSignListByIdCard = bizSignListService.getBizSignListByIdCard(idCard);
        Map<String, String> workTypeMap = bizWorkTypeService.getWorkTypeMap();
        //如果有报名信息，则进入判断，否则登出
        if(bizSignListByIdCard!=null && bizSignListByIdCard.size()>0){
            BizSignList bizSignList = bizSignListByIdCard.get(0);
            //如果已经交卷，则不允许再次考试
            if(bizSignList.getHasExam()!=0){
                return "redirect:list";
            }
            //否则就是有一个/多个报名信息
            ExamUserInfo examUserInfo = new ExamUserInfo();
            examUserInfo.setIdCard(bizSignList.getIdCard());
            examUserInfo.setUserName(bizSignList.getUserName());
            examUserInfo.setUserPic(bizSignList.getUserPic());
            examUserInfo.setExamName(workTypeMap.get(workTypeId));
            model.addAttribute("userInfo",examUserInfo);
            //生成试题
            ExamInfo examInfo = bizExamListService.generatorExamList(idCard, workTypeId);
            //更新初次考试时间
            bizSignList.setFirstJoinTime(new Date());
            bizSignListService.updateById(bizSignList);
            //向前台传递考试题目
            model.addAttribute("examInfo",examInfo);
            //进入考试页面
            return "examQuestions";
        }else{
            return "redirect:logout";
        }
    }

    @RequestMapping("/examResult")
    public String examResult(String score,String eid,Model model){
        BizExamList elist = bizExamListService.getById(eid);
        String signId = elist.getSignId();
        BizSignList signList = bizSignListService.getById(signId);
        String userName = signList.getUserName();
        model.addAttribute("userName",userName);
        model.addAttribute("score",score);
        return "examResult";

    }

    @RequestMapping("/userBack")
    public String userBack(){
        Object principal = SecurityUtils.getSubject().getPrincipal();
        String idCard = (String) principal;
        List<BizSignList> bizSignListByIdCard = bizSignListService.getBizSignListByIdCard(idCard);
        if(bizSignListByIdCard!=null && bizSignListByIdCard.size()>0){
            return "redirect:list";
        }else{
            return "redirect:logout";
        }
    }

    @RequestMapping("/logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "index";
    }

    //*********************************action*******************************************************

    /**
     * 计算时间
     * @return
     */
    @RequestMapping("/flushTime")
    @ResponseBody
    public Result sumTime(String eid,String workTypeId){
        if(StringUtils.isEmpty(eid) || StringUtils.isEmpty(workTypeId) ){
           return new Result().errResult("没有当前正在进行的考试！",null);
        }
        //获取考试的考试时间
        BizExamingTime examingTime = bizExamingTimeService.getById(eid);
        //获取考试设置的考试时间
        BizExamSet examSetByWorkTypeId = bizExamSetService.getExamSetByWorkTypeId(workTypeId);
        Integer curSetTime = examSetByWorkTypeId.getTime();
        //将分钟算成秒
        curSetTime = curSetTime*60;
        UserTime ut = new UserTime();
        ut.setTotalTime(curSetTime);

        if(examingTime==null){
            examingTime = new BizExamingTime();
            examingTime.setId(eid);
            examingTime.setTime(1);
            examingTime.setCreateTime(new Date());
            bizExamingTimeService.save(examingTime);
        }else if(examingTime.getTime().equals(curSetTime)) {
            ut.setPassTime(examingTime.getTime());
            return new Result().okResult("1",ut);
        }
        else{
            examingTime.setTime(examingTime.getTime() +1);
            bizExamingTimeService.updateById(examingTime);
        }
        ut.setPassTime(examingTime.getTime());
        return new Result().okResult("0",ut);
    }


    @RequestMapping("/getSubjectById")
    @ResponseBody
    public Result getSubjectById(String subjectId){
        BizSubject subject = bizSubjectService.getById(subjectId);
        subject.setSwitchResult("");
        subject.setJudgeResult("");
        return new Result().okResult("查询成功！",subject);
    }

    @RequestMapping("/saveAnswer")
    @ResponseBody
    public Result saveAnswer(String answer,String sid,String examId,Integer subjectType){
        Object principal = SecurityUtils.getSubject().getPrincipal();
        String idCard = (String)principal;
        if(answer==null || "".equals(answer)){
            return new Result().errResult("请选择答案！",null);
        }
        List<String> answerResult = new ArrayList<>();
        answerResult.add("a");
        answerResult.add("b");
        answerResult.add("c");
        answerResult.add("y");
        answerResult.add("n");
        if(!answerResult.contains(answer)){
            return new Result().errResult("请选择正确试题！",null);
        }
        if(sid==null){
            return new Result().errResult("未选择试题！",null);
        }
        if(examId==null || "".equals(examId)){
            return new Result().errResult("没有您的考试套题！",null);
        }
        if(subjectType == null || (subjectType!=1 && subjectType!=2)){
            return new Result().errResult("题型不正确！",null);
        }

        //todo 根据idCard获取考试信息
        bizExamListService.saveAnswer(answer,sid,examId,subjectType);
        return new Result().okResult("选择成功!",null);
    }

    @RequestMapping("/flushItem")
    @ResponseBody
    public Result flushItem(String eid){
        BizExamList byId = bizExamListService.getById(eid);
        String switchAnswerStr = byId.getSwitchAnswer();
        String judgeAnswerStr = byId.getJudgeAnswer();
        List<SubjectAnswer> switchList = JSON.parseArray(switchAnswerStr,SubjectAnswer.class);
        List<SubjectAnswer> judgeList = JSON.parseArray(judgeAnswerStr,SubjectAnswer.class);
        ExamItemInfo examItemInfo = new ExamItemInfo(switchList,judgeList);
        return new Result().okResult("查询成功！",examItemInfo);
    }

    /**
     * 计算得分
     * @param eid
     * @return
     */
    @RequestMapping("/commit")
    @ResponseBody
    public Result commit(String eid){
        BizExamList exam = bizExamListService.getById(eid);

        String judgeAnswerStr = exam.getJudgeAnswer();
        List<SubjectAnswer> judgeAnswerList = JSON.parseArray(judgeAnswerStr,SubjectAnswer.class);
        String switchAnswerStr = exam.getSwitchAnswer();
        List<SubjectAnswer> switchAnswerList = JSON.parseArray(switchAnswerStr,SubjectAnswer.class);

        List<SubjectAnswer> userAnswerList = new ArrayList<>();
        userAnswerList.addAll(switchAnswerList);
        userAnswerList.addAll(judgeAnswerList);

        List<String> subjectIdList = userAnswerList.stream().map(SubjectAnswer::getSubjectId).collect(Collectors.toList());

        List<BizSubject> answerList = bizSubjectService.lambdaQuery()
                .select(BizSubject::getId,BizSubject::getSwitchResult,BizSubject::getJudgeResult,BizSubject::getSubType)
                .in(BizSubject::getId, subjectIdList).list();

        Map<String,String> answerMap = new HashMap<>(8);
        for (BizSubject subject : answerList) {
            if(subject.getSubType()==1){
                answerMap.put(subject.getId(),subject.getSwitchResult());
            }else{
                answerMap.put(subject.getId(),subject.getJudgeResult());
            }
        }
        int score = 0 ;
        for (SubjectAnswer userAnswer : userAnswerList) {
            String subjectId = userAnswer.getSubjectId();
            String answer = answerMap.get(subjectId);
            if(userAnswer.getAnswer()!=null && userAnswer.getAnswer().toLowerCase().equals(answer.toLowerCase())){
                score++;
            }
        }
        //设置已经考完试
        exam.setScore(score);
        bizExamListService.updateById(exam);
        String signId = exam.getSignId();
        BizSignList sign = bizSignListService.getById(signId);
        sign.setHasExam(1);
        bizSignListService.updateById(sign);
        return new Result().okResult("提交成功！",score);
    }
}
