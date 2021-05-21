package com.fr.exam.controller;

import com.alibaba.fastjson.JSON;
import com.fr.exam.Exception.CustomException;
import com.fr.exam.entity.DO.BizSignList;
import com.fr.exam.entity.DTO.BaiDuResult;
import com.fr.exam.entity.DTO.FaceEntity;
import com.fr.exam.entity.VO.Result;
import com.fr.exam.entity.VO.User;
import com.fr.exam.service.IBizAkService;
import com.fr.exam.service.IBizExamDayService;
import com.fr.exam.service.IBizExamSetService;
import com.fr.exam.service.IBizSignListService;
import com.fr.exam.utils.RSAUtils;
import com.google.gson.Gson;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.fr.exam.config.Constants.FACE_URL;
import static com.fr.exam.utils.HttpRequestUtil.*;
import static com.fr.exam.utils.HttpRequestUtil.BODY_CONTENT_TYPE_JSON;

/**
 * @author furao
 * @desc
 * @date 2021/2/18
 * @package com.fr.exam.controller
 */
@Controller
public class LoginController {
    private static final Log logger = LogFactory.getLog(LoginController.class);

    @Autowired
    IBizExamSetService bizExamSetService;
    @Autowired
    IBizSignListService bizSignListService;
    @Autowired
    IBizAkService bizAkService;
    @Autowired
    IBizExamDayService bizExamDayService;


    //*********************************page*******************************************************


    @RequestMapping(value = {"/index","/"})
    public String loginPage(Model model){
        Object principal = SecurityUtils.getSubject().getPrincipal();
        String idCard = (String) principal;
        if(idCard != null && idCard.length()>0){
            return "redirect:exam/list";
        }
        model.addAttribute("n", RSAUtils.getModulus());
        model.addAttribute("e", RSAUtils.getPublicExponent());
        return "index";
    }

    @RequestMapping("/codeLogin")
    public String codeLogin(){
        Object principal = SecurityUtils.getSubject().getPrincipal();
        String idCard = (String) principal;
        if(idCard != null && idCard.length()>0){
            return "redirect:exam/list";
        }
        return "indexCodeLogin";
    }


    @RequestMapping("/facePage")
    public String facePage(String idCard, Model model){
        Object principal = SecurityUtils.getSubject().getPrincipal();
        String principalStr = (String) principal;
        if(principalStr != null && principalStr.length()>0){
            return "redirect:exam/list";
        }
        model.addAttribute("idCard",idCard);
        boolean b = bizSignListService.checkIdCardExist(idCard);
        if(!b){
            return "index";
        }
        return "face";
    }



    //*********************************action*******************************************************

    @RequestMapping("/checkIdCard")
    @ResponseBody
    public Result checkIdCard(String idCard){
        try {
            idCard = RSAUtils.decryptPrivate(idCard);
        } catch (Exception e) {
            return new Result().errResult("身份证信息不存在！",null);
        }
        boolean b = bizSignListService.checkIdCardExist(idCard);
        if(b){
            return new Result().okResult("身份证信息存在",null);
        }else{
            return new Result().errResult("身份证信息不存在！",null);
        }
    }

    @RequestMapping("/login")
    @ResponseBody
    public Result login(User user){
        Result result = new Result().errResult("请输入身份证号！",null);
        if(StringUtils.isEmpty(user.getIdCard())){
            return result;
        }
        user.setPassword("123456");
        String livePic = user.getUserLivePic().replace("data:image/png;base64,","");
        List<BizSignList> list = bizSignListService.getBizSignListByIdCard(user.getIdCard());
        if(list == null || list.size()==0){
            return new Result().errResult("没有报名信息!",null);
        }

        if(StringUtils.isEmpty(list.get(0).getUserPic())){
            return new Result().errResult("没有人脸信息!",null);
        }
        String userPic = list.get(0).getUserPic();

        try {
            faceCheck(livePic, userPic);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result().errResult(e.getMessage(),null);
        }
        //登陆成功
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(
                user.getIdCard(),
                user.getPassword()
        );
        try {
            subject.login(usernamePasswordToken);
        } catch (AuthenticationException e){
            if(e instanceof UnknownAccountException){
                result = result.errResult("无法找到账户信息！",null);
            }
            if(e instanceof IncorrectCredentialsException){
                result = result.errResult("密码不正确！",null);
            }
            return result;
        }
        result = result.okResult("登录成功！",null);
        return result;
    }


    @RequestMapping("/checkIdCardAndCode")
    @ResponseBody
    public Result checkIdCardAndCode(User user){
        Result result = new Result().errResult("请输入身份证号！",null);
        if(StringUtils.isEmpty(user.getIdCard())){
            return result;
        }
//        List<BizSignList> list = bizSignListService.getBizSignListByIdCard(user.getIdCard());
//        if(list == null || list.size()==0){
//            return new Result().errResult("没有报名信息!",null);
//        }
        if(StringUtils.isEmpty(user.getLoginCode())){
            return new Result().errResult("请输入授权码！",null);
        }
        boolean b = false;
        try {
            b = bizExamDayService.checkLoginCode(user.getLoginCode());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result().errResult("登陆失败！"+e.getMessage(),null);
        }
        if(b){
            //登陆成功
            user.setPassword("123456");
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(
                    user.getIdCard(),
                    user.getPassword()
            );
            try {
                subject.login(usernamePasswordToken);
            } catch (AuthenticationException e){
                if(e instanceof UnknownAccountException){
                    result = result.errResult("无法找到账户信息！",null);
                }
                else if(e instanceof IncorrectCredentialsException){
                    result = result.errResult("密码不正确！",null);
                }else{
                    result = result.errResult("无法找到账户信息！",null);
                }
                return result;
            }
            result = result.okResult("登录成功！",null);
            return result;
        }else{
            result = result.okResult("登陆失败！授权码不正确！",null);
            return result;
        }
    }


    /**
     * 人脸识别
     * @param livePic
     * @param userPic
     * @return
     */
    public boolean faceCheck(String livePic,String userPic){
        FaceEntity f1 = new FaceEntity();
        FaceEntity f2 = new FaceEntity();
        List<FaceEntity> param = new ArrayList<>();
        f1.setImage(livePic);
        f1.setImage_type("BASE64");
        f2.setImage(userPic);
        f2.setImage_type("BASE64");
        param.add(f1);
        param.add(f2);

        String paramStr = new Gson().toJson(param);
//        System.out.println("【"+paramStr+"】");
        Map<String,String> map = new HashMap<>(2);
        map.put(CONTENT_TYPE,BODY_CONTENT_TYPE_JSON);
        String ak = bizAkService.getById(1).getAk();

        String result = commonHttpRequest(FACE_URL+ak, paramStr, POST, map);
        BaiDuResult baiDuResult = JSON.parseObject(result, BaiDuResult.class);
        if(!StringUtils.isEmpty(baiDuResult)){
            if("0".equals(baiDuResult.getErrorCode())){
                if((baiDuResult.getResult().getScore()>=80)){
                    return true;
                }else{
                    throw new CustomException("人脸不匹配！，分数："+baiDuResult.getResult().getScore());
                }
            }else if("222202".equals(baiDuResult.getErrorCode())){
                throw new CustomException("没有人脸信息！");

            }
            else {
                throw new CustomException("人脸识别接口返回异常码【"+baiDuResult.getErrorCode()+"】，信息【"+baiDuResult.getErrorMsg()+"】");
            }
        }else{
            throw new CustomException("人脸识别接口返回空信息！");
        }
    }
}
