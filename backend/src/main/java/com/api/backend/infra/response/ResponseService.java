package com.api.backend.infra.response;

import com.api.backend.infra.response.model.CommonResult;
import com.api.backend.infra.response.model.MultiResult;
import com.api.backend.infra.response.model.SingleResult;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResponseService {

    @AllArgsConstructor @Getter
    public enum CommonResponse{
        SUCCESS(1, "성공"),
        FAIL(0, "실패");

        int code;
        String msg;
    }

    public <T> SingleResult<T> getSingleResult(T data){
        SingleResult<T> result = new SingleResult<>();
        result.setData(data);
        setSuccessResult(result);

        return result;
    }

    public <T> MultiResult<T> getMultiResult(List<T> list){
        MultiResult<T> result = new MultiResult<>();
        result.setList(list);
        setSuccessResult(result);

        return result;
    }

    public CommonResult getSuccessResult(){
        CommonResult result = new CommonResult();
        setSuccessResult(result);

        return result;
    }

    public CommonResult getFailResult(int code, String msg){
        CommonResult result = new CommonResult();
        setFailResult(result, code, msg);

        return result;
    }

    private void setSuccessResult(CommonResult result) {
        result.setSuccess(true);
        result.setCode(CommonResponse.SUCCESS.getCode());
        result.setMsg(CommonResponse.SUCCESS.getMsg());
    }

    private void setFailResult(CommonResult result, int code, String msg) {
        result.setSuccess(false);
        result.setCode(code);
        result.setMsg(msg);
    }
}
