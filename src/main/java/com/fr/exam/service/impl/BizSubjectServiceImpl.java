package com.fr.exam.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fr.exam.entity.DO.BizSubject;
import com.fr.exam.mapper.BizSubjectMapper;
import com.fr.exam.service.IBizSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description: biz_subject
 * @Author: jeecg-boot
 * @Date:   2021-02-10
 * @Version: V1.0
 */
@Service
public class BizSubjectServiceImpl extends ServiceImpl<BizSubjectMapper, BizSubject> implements IBizSubjectService {

}
