package com.fr.exam.job;

import com.alibaba.fastjson.JSON;
import com.fr.exam.entity.DO.BizAk;
import com.fr.exam.service.IBizAkService;
import com.fr.exam.utils.SpringContextUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static com.fr.exam.utils.GetNewAk.getNewAk;

/**
 * @author furao
 * @desc
 * @date 2021/2/24
 * @package com.fr.exam.job
 */
@Configuration
@EnableScheduling
public class TimeJob {
    private static final Log logger = LogFactory.getLog(TimeJob.class);

    IBizAkService bizAkService;


    @Scheduled(cron = "0 0 0 * * ?")
    public void checkAk(){
        logger.info("检查AK是否过期......");
        if(bizAkService == null){
            bizAkService = SpringContextUtils.getBean(IBizAkService.class);
        }
        BizAk ak = bizAkService.getById(1);
        Date updateTime = ak.getUpdateTime();
        Date now = new Date();

        LocalDateTime oldDate = updateTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime nowDate = now.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        Duration duration = java.time.Duration.between(oldDate,nowDate);
        long l = duration.toDays();
        if(Math.abs(l) > 10){
            //重新获取ak
            BizAk newAk = getNewAk();
            logger.info("获取了新的accessToken"+ JSON.toJSONString(newAk));
            //存储ak
            if(newAk!=null){
                bizAkService.updateById(newAk);
            }
        }else{
            logger.info("AK未过期......");
        }

    }
}
