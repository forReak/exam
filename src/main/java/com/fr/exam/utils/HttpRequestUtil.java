package com.fr.exam.utils;

import com.fr.exam.Exception.CustomException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

/*
 * @author furao
 * @desc
 * @date 2021/1/25
 * @package com.dfzt.proxy.util
 */
public class HttpRequestUtil {
    private static final Log logger = LogFactory.getLog(HttpRequestUtil.class);


    /* 设置连接主机服务器超时时间：15000毫秒 */
    private static final int CONNECT_TIME_OUT = 15000;
    /* 设置读取远程返回的数据时间：60000毫秒 */
    private static final int READ_TIME_OUT = 60000;
    /* 设置通用的请求属性 */
    private static final Map<String,String> REQUEST_PROPERTY_MAP = new HashMap<String,String>(8);


    public static final String POST = "POST";
    public static final String GET = "GET";

    public static final String CONTENT_TYPE = "Content-Type";

    public static final String BODY_CONTENT_TYPE_X_WWW = "application/x-www-form-urlencoded";
    public static final String BODY_CONTENT_TYPE_JSON = "application/json";
    public static final String BODY_CONTENT_TYPE_XML = "application/xml";

    public static final List<String> requestContentTypeList = new ArrayList<>();


    {
        requestContentTypeList.add(BODY_CONTENT_TYPE_X_WWW);
        requestContentTypeList.add(BODY_CONTENT_TYPE_JSON);
        requestContentTypeList.add(BODY_CONTENT_TYPE_XML);

        REQUEST_PROPERTY_MAP.put("accept","*/*");
        REQUEST_PROPERTY_MAP.put("connection","Keep-Alive");
        REQUEST_PROPERTY_MAP.put("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        REQUEST_PROPERTY_MAP.put(CONTENT_TYPE,BODY_CONTENT_TYPE_X_WWW);
    }

    /**
     * 通用http请求
     * @param requestUrl 请求url
     * @param paramMap 请求参数
     * @param requestType 请求类型
     * @param requestPropertyMap 请求头参数
     * @return String 返回值
     */
    public static String commonHttpRequest(String requestUrl, String paramMap, String requestType,
                                           Map<String,String> requestPropertyMap) {
        HttpURLConnection connection = null;
        InputStream is = null;
        OutputStream os = null;
        BufferedReader br = null;
        String returnResult = null;
        String requestContentType = BODY_CONTENT_TYPE_X_WWW;
        String requestUrlParam = "";

        /*获取请求Content-Type*/
        if(requestPropertyMap!=null
                && requestPropertyMap.containsKey(CONTENT_TYPE)
                && requestContentTypeList.contains(requestPropertyMap.get("Content-Type"))){
            requestContentType = requestPropertyMap.get("Content-Type");
        }
        /*重组用户参数为指定格式*/
//        requestUrlParam = switchParamType(paramMap, requestContentType, requestUrlParam);
        requestUrlParam = paramMap;
        try {
            URL url = getUrl(requestUrl, requestType, requestUrlParam);
            /*判断请求方式是http还是https，生成连接*/
            if(requestUrl.toLowerCase(Locale.ENGLISH).startsWith("https")){
                connection = (HttpsURLConnection) url.openConnection();
            }else{
                connection = (HttpURLConnection) url.openConnection();
            }
            connection.setConnectTimeout(CONNECT_TIME_OUT);
            connection.setReadTimeout(READ_TIME_OUT);
            connection.setDoOutput(true);
            /*设置请求Content-Type*/
            REQUEST_PROPERTY_MAP.forEach(connection::setRequestProperty);
            if(requestPropertyMap!=null){
                requestPropertyMap.forEach(connection::setRequestProperty);
            }
            /*选择发送方式*/
            switch (requestType){
                case GET:
                    connection.setRequestMethod(GET);
                    connection.connect();
                    break;
                case POST:
                    connection.setRequestMethod(POST);
                    /* 通过连接对象获取一个输出流 */
                    os = connection.getOutputStream();
                    // 发送请求参数
                    os.write(requestUrlParam.getBytes(StandardCharsets.UTF_8));
                    // flush输出流的缓冲
                    os.flush();
                    break;
                default:throw new CustomException("没有http请求类型！");
            }
            /* 请求成功：返回码为200 */
            if (connection.getResponseCode() == 200) {
                /* 通过connection连接，获取输入流 */
                is = connection.getInputStream();
                /* 封装输入流is，并指定字符集 */
                br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
                /* 存放数据 */
                StringBuilder sbf = new StringBuilder();
                String line = null;
                while ((line = br.readLine()) != null) {
                    sbf.append(line);
                    sbf.append("\r\n");
                }
                returnResult = sbf.toString();
//                logger.info("remote provider return : "+returnResult);
            }else{
                System.out.println(connection.getResponseMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("请求远程接口失败！",e);
        } finally {
            /* 关闭资源 */
            try {
                if (null != br) {
                    br.close();
                }
                if (null != is) {
                    is.close();
                }
                if (null != os) {
                    os.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            /* 关闭远程连接 */
            // 断开连接，最好写上，disconnect是在底层tcp socket链接空闲时才切断。如果正在被其他线程使用就不切断。
            // 固定多线程的话，如果不disconnect，链接会增多，直到收发不出信息。写上disconnect后正常一些
            assert connection != null;
            connection.disconnect();
                logger.info("--------->>> "+requestType+" request end <<<----------");
        }
        return returnResult;
    }

    /**
     * 获取url，根据类型拼接url请求参数
     * @param requestUrl 用户的url
     * @param requestType 用户的请求方式
     * @param requestUrlParam 生成的请求参数
     * @return
     * @throws MalformedURLException
     */
    private static URL getUrl(String requestUrl, String requestType, String requestUrlParam) throws MalformedURLException {
        URL url;
        if (POST.equals(requestType)) {
            url = new URL(requestUrl);
        } else {
            //默认情况下，先拼接参数到url中
            if( requestUrl.endsWith("/")){
                url = new URL(requestUrl.substring(0,requestUrl.length()-1) + "?" + requestUrlParam);
            }else{
                url = new URL(requestUrl + "?" + requestUrlParam);
            }

        }
        return url;
    }

    /**
     * 设置参数格式 这一步应该在客户端做，不应该在服务端做
     * @param paramMap 用户参数
     * @param requestContentType 请求类型
     * @param requestUrlParam 请求参数
     * @return
     */
    private static String switchParamType(JsonObject paramMap, String requestContentType, String requestUrlParam) {
        StringBuffer sb = new StringBuffer();
        Gson gson = new Gson();
        switch (requestContentType){
            case BODY_CONTENT_TYPE_JSON:
                //将参数转为json格式
                if(paramMap!=null && paramMap.size()>0){
                    requestUrlParam = gson.toJson(paramMap);
                }
                break;
            case BODY_CONTENT_TYPE_XML:

                break;
            default:
                //默认为 application/x-www-form-urlencoded
                //将参数转为"id=123&"格式
                if(paramMap!=null && paramMap.size()>0){
                    /*paramMap.forEach((k,object) -> {
                        try {
                            String objToStr = "";
                            if(object instanceof String){
                                objToStr = (String)object;
                            }else if (object instanceof Boolean){
                                objToStr = ((Boolean)object).toString();
                                //todo 各个类型的数据转为字符串
                            }
                            objToStr = gson.toJson(object);
                            sb.append(k).append("=").append(URLEncoder.encode(objToStr,"UTF-8")).append("&");
                        } catch (UnsupportedEncodingException e) {
                            throw new CustomException("URL encode 出错！",e);
                        }
                    });*/
                    requestUrlParam = sb.toString();
                }
        }
        return requestUrlParam;
    }


    /**
     *
     * @param requestUrl 请求url
     * @param paramObject 请求参数
     * @param requestType 请求类型
     * @param requestPropertyMap 请求头参数
     * @return String 返回值
     */
    public static String commonHttpRequest(String requestUrl,Object paramObject,String requestType,
                                            Map<String,String> requestPropertyMap){
//        JsonObject paramMap = new HashMap<>(2);
        JsonObject paramMap = null;
        /*if (paramObject != null){
            Class<?> clazz = paramObject.getClass();
            for (Field declaredField : clazz.getDeclaredFields()) {
                String name = declaredField.getName();
                if("serialVersionUID".equals(name)) continue;
                Object o = null;
                try {
                    declaredField.setAccessible(true);
                    o = declaredField.get(paramObject);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    throw new CustomException("无法通过【"+name+"】获取对应的值");
                }
                paramMap.put(name,o);
            }
        }*/
        return commonHttpRequest(requestUrl,paramMap,requestType,requestPropertyMap);


    }
}
