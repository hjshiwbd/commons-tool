package commons.tool.bean;

import commons.tool.utils.JsonUtil;

public class ExecuteResult {
    /**
     * 执行结果.1成功,其他失败
     */
    private String result = "0";
    /**
     * 执行结果描述,代码级消息提示
     */
    private String message = "unknown_error";
    /**
     * 执行结果描述,展示级消息提示
     */
    private String message2 = "系统异常，请稍候再试";
    /**
     * 执行结果携带返回值
     */
    private Object object;

    public ExecuteResult() {

    }

    public ExecuteResult(String result) {
        this.result = result;
    }

    public ExecuteResult(String result, String message) {
        this.result = result;
        this.message = message;
    }

    public ExecuteResult(String result, String message, Object object) {
        this.result = result;
        this.message = message;
        this.object = object;
    }

    public ExecuteResult(String result, String message, String message2, Object object) {
        this.result = result;
        this.message = message;
        this.message2 = message2;
        this.object = object;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public String getMessage2() {
        return message2;
    }

    public void setMessage2(String message2) {
        this.message2 = message2;
    }

    @Override
    public String toString() {
        return JsonUtil.toJson(this);
    }
}
