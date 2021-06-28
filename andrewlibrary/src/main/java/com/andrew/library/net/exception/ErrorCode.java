
package com.andrew.library.net.exception;

/**
 * Created by zhpan on 2018/3/27.
 */
public class ErrorCode {
    /**
     * request success
     */
    public static final int SUCCESS = 0;
    /**
     * Wrong verify code
     */
    public static final int VERIFY_CODE_ERROR = 110011;
    /**
     * Verify code is invalid
     */
    public static final int VERIFY_CODE_EXPIRED = 110010;
    /**
     * User is not register
     */
    public static final int ACCOUNT_NOT_REGISTER = 110009;
    /**
     * Wrong password or username
     */
    public static final int PASSWORD_ERROR = 110012;

    /**
     * Wrong old password
     */
    public static final int OLD_PASSWORD_ERROR = 110015;

    public static final int USER_REGISTERED = 110006;

    public static final int PARAMS_ERROR = 19999;
    /**
     * 异地登录
     */
    public static final int REMOTE_LOGIN = 91011;

    /**
     * get error message with error code
     *
     * @param errorCode error code
     * @return error message
     */
    public static String getErrorMessage(int errorCode) {
        String message;
        switch (errorCode) {
            case VERIFY_CODE_ERROR:
                message = "输入验证码有误";
                break;
            case VERIFY_CODE_EXPIRED:
                message = "验证码已过期";
                break;
            case ACCOUNT_NOT_REGISTER:
                message = "该账户还未注册";
                break;
            case PASSWORD_ERROR:
                message = "用户名或密码错误";
                break;
            case USER_REGISTERED:
                message = "该账户已存在";
                break;
            case OLD_PASSWORD_ERROR:
                message = "密码错误";
                break;
            case PARAMS_ERROR:
                message = "Parameter exception";
                break;
            case REMOTE_LOGIN:
                message = "您的账号已在其它设备上登录，如非本人操作，请及时修改密码！";
                break;
            default:
                message = "Failed to request server,error code：" + errorCode;
                break;
        }
        return message;
    }

}
