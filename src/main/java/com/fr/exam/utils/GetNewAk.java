package com.fr.exam.utils;

import com.alibaba.fastjson.JSON;
import com.fr.exam.entity.DTO.BaiduAkResult;
import com.fr.exam.entity.DO.BizAk;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.fr.exam.config.Constants.TOKEN_URL;
import static com.fr.exam.utils.HttpRequestUtil.*;
import static com.fr.exam.utils.HttpRequestUtil.BODY_CONTENT_TYPE_JSON;

/**
 * @author furao
 * @desc
 * @date 2021/2/24
 * @package com.fr.exam.utils
 */
public class GetNewAk {

    public static BizAk getNewAk(){
        BizAk ak = new BizAk();
        Map<String,String> map = new HashMap<>(2);
        map.put(CONTENT_TYPE,BODY_CONTENT_TYPE_JSON);
        String result = commonHttpRequest(TOKEN_URL, "", POST, map);
        BaiduAkResult baiDuAkResult = JSON.parseObject(result, BaiduAkResult.class);
        String accessToken = baiDuAkResult.getAccessToken();
        if(accessToken != null && accessToken.length() > 10){
            return new BizAk("1",accessToken,new Date());
        }else{
            return null;
        }
    }
}
