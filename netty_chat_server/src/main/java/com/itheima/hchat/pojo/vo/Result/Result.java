package com.itheima.hchat.pojo.vo.Result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: mou
 * @date: 2019/10/13
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Result {

    private boolean success;

    private String message;

    private Object result;

    public Result(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
