package com.fjp.exceptions;

import com.fjp.enums.ErrorCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BusinessException extends RuntimeException {
    private ErrorCode code;

    public BusinessException(ErrorCode code) {
        super(code.getMsg());
        this.code = code;
    }
}
