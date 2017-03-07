package com.aliex.commonlib.net.func;

import com.aliex.commonlib.net.exception.ApiException;
import com.aliex.commonlib.net.mode.ApiResult;

import rx.functions.Func1;

/**
 * @Description: ApiResult<T>转T
 */
public class ApiDataFunc<T> implements Func1<ApiResult<T>, T> {
    public ApiDataFunc() {
    }

    @Override
    public T call(ApiResult<T> response) {
        if (ApiException.isSuccess(response)) {
            return response.getData();
        } else {
            return (T) new ApiException(new Throwable(response.getMsg()), response.getCode());
        }
    }
}
