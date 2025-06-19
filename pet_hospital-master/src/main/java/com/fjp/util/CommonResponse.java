package com.fjp.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class CommonResponse {
    private Head head;
    private Body body;

    public static CommonResponse of(String msg, int code, Object data) {
        return of(msg, code, data, null, null, null);
    }

    public static CommonResponse of(String msg, int code, Object data, Long total, Integer page, Integer size) {
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setHead(new Head(msg, code));
        commonResponse.setBody(new Body(data, total, page, size));
        return commonResponse;
    }
}

@Data
@AllArgsConstructor
class Head {
    private String msg;
    private int code;
}

@Data
@AllArgsConstructor
class Body {
    private Object data;
    private Long total;
    private Integer page;
    private Integer size;
}
