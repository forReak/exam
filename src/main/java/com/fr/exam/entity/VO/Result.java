package com.fr.exam.entity.VO;

/**
 * @author furao
 * @desc
 * @date 2021/2/20
 * @package com.fr.exam.entity
 */
public class Result {

    private String msg = "请求失败！";
    private Object obj;
    private boolean flag = false;

    public Result() {
    }

    public Result(String msg, Object obj, boolean flag) {
        this.msg = msg;
        this.obj = obj;
        this.flag = flag;
    }

    public Result okResult(String msg,Object obj){
        return new Result(msg,obj,true);
    }

    public Result errResult(String msg,Object obj){
        return new Result(msg,obj,false);
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
