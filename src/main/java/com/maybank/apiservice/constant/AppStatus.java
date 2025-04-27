package com.maybank.apiservice.constant;

public enum AppStatus {
    /* Application success 00*/
    SUCCESS("00", "success"),
    BAD_PARAMETER("01", "Bad Parameter"),
    BAD_REQUEST("02", "Bad Request"),
    APP_ERROR("03", "Application Error"),
    /* Internal server error 05 */
    INTERNAL_SERVER_ERROR("05", "Internal Server Error");

    private String code;
    private String msg;

    AppStatus(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
