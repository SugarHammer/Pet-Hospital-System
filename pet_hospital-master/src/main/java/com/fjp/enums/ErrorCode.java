package com.fjp.enums;

public enum ErrorCode {
    USER_OR_PASSWORD_INVALID("用户名或密码错误", 401),
    USER_ID_NOT_FOUND("用户id不存在", 500),
    NOTICE_ID_NOT_FOUND("通知id不存在", 500),
    NOTICE_NOT_FOUND("不能为空", 500),
    PAGE_SEARCH_EXCEPTION("分页查询异常", 500),
    GUIDE_ID_NOT_FOUND("指南id不存在", 500),
    GUIDE_ID_FOUND("不能为空", 500),
    PET_ID_NOT_FOUND("宠物id不存在", 500),
    ROLE_ID_NOT_FOUND("角色id不存在", 500),
    USERNAME_ALREADY_EXIST("用户名已存在", 500),
    PHONE_ALREADY_USED("电话已使用", 500),
    IDCARD_ALREADY_USED("身份证已被使用", 500),
    ROLE_NAME_ALREADY_EXIST("角色名已存在", 500),
    AUTHORITY_NOT_FOUND("没有该权限", 403),
    DOCTOR_ID_NOT_FOUND("医生ID不存在",500),
    EVERYDAY_APPOINTMENT_COUNT_EXCEED_20("每日医生预约人数不能超过20", 500),
    APPOINTMENT_ID_NOT_FOUND("预约id不存在", 500),
    OLD_PASSWORD_NOT_FOUND("原密码错误", 500),
    MEDICINE_NOT_FIND("没有这药",500),
    MEDICINE_NUMBER_WRONG("药品数量错误",500);

    private String msg;
    private int code;

    ErrorCode(String msg, int code) {
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
