package io.icw.test;

import java.util.HashMap;
import java.util.Map;

import io.icw.core.basic.Result;
import io.icw.core.constant.ErrorCode;
import io.icw.v2.NulsSDKBootStrap;
import io.icw.v2.model.dto.RestFulResult;
import io.icw.v2.util.RestFulUtil;

public class TestNetwork {

	public static void main(String[] args) {
		String main = "http://8.210.21.144:8004/";
		NulsSDKBootStrap.initMain(main);
		
		Map<String, Object> params = new HashMap<>();
        params.put("assetChainId", 1);
        params.put("assetId", 1);

        String address = "";
        Result result;
        RestFulResult restFulResult = RestFulUtil.post("api/accountledger/balance/" + address, params);
        if (restFulResult.isSuccess()) {
            result = Result.getSuccess(restFulResult.getData());
        } else {
            ErrorCode errorCode = ErrorCode.init(restFulResult.getError().getCode());
            result = Result.getFailed(errorCode).setMsg(restFulResult.getError().getMessage());
        }
	}

}
