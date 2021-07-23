package cn.john.common;

/**
 * @author John Yan
 */

public enum BaseMessage {

    /**
     * 成功返回
     */
    SUCCESS(0, "请求成功"),
    /**
     * 未登录错误
     */
    NO_LOGIN(1001, "请登录"),
    /**
     * 系统错误
     */
    SYSTEM_ERROR(1002, "系统错误"),
    /**
     * 请求参数错误
     */
    PARAM_ERROR(1003, "请求参数错误"),
    /**
     * 业务错误
     */
    BUSINESS_ERROR(1004, "业务错误"),
    /**
     * 没有权限
     */
    NO_PERMISSION(1005, "没有权限"),
    /**
     * 签名错误
     */
    SIGN_ERROR(1009, "验签错误"),;

    /**
     * 代码
     */
    private int code;
    /**
     * 内容
     */
    private String message;

    BaseMessage(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
