package io.icw.test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.http.HttpRequest;
import io.icw.core.basic.Result;
import io.icw.v2.NulsSDKBootStrap;
import io.icw.v2.SDKContext;
import io.icw.v2.model.dto.CoinFromDto;
import io.icw.v2.model.dto.CoinToDto;
import io.icw.v2.model.dto.TransferDto;
import io.icw.v2.model.dto.TransferTxFeeDto;
import io.icw.v2.util.NulsSDKTool;

public class TIcw2 {

	public static String main = "http://192.168.110.215:18004/";

	public static Map<String, Object> getInfo() {
		return (Map<String, Object>) NulsSDKTool.getInfo().getData();
	}
	
	public static String getNonce(String address) {
		Result<Map<String, String>> addressByPriKey = NulsSDKTool.getAccountBalance(address, 1, 1);

		return addressByPriKey.getData().get("nonce");
	}

	static String nonce = "";

	public static BigInteger getGas() {
		TransferTxFeeDto dto = new TransferTxFeeDto();
		dto.setAddressCount(1);
		dto.setFromLength(1);
		dto.setToLength(1);

		return NulsSDKTool.calcTransferTxFee(dto);
	}

	public static String getPrivateKey(String address, String password) throws Exception {
		Result<Map<String, String>> result = NulsSDKTool.getPriKey(address, password);
		if (result.isSuccess()) {
			Map<String, String> map = result.getData();
			return map.get("value");
		} else {
			throw new Exception(result.getMsg());
		}
	}

	public static WithdrawResult transferCopy(String fromAddress, String toAddress, String coinName, BigDecimal amount,
											  BigDecimal fee, String privateKey) throws Exception {
		WithdrawResult withdrawResult = new WithdrawResult();
		withdrawResult.setSuccess(false);

		List<CoinFromDto> inputs = new ArrayList<>();
		List<CoinToDto> outputs = new ArrayList<>();
		TransferDto transferDto = new TransferDto();
		CoinFromDto coinFromDto = new CoinFromDto();
		CoinToDto coinToDto = new CoinToDto();

		BigInteger gasPrice = getGas();

//		if (StringUtils.isBlank(privateKey)) {
//			privateKey = getPrivateKey(fromAddress, accountPass); // "13e11486bcf6b0d1d72c61c3e90d7c22dd3d2b515ddb63e322424bd7512a6d75";
//		}

		Map<String, Object> chainInfo = getInfo();

		BigInteger value = NumberUtil.mul(amount, 100000000).toBigInteger();

		coinFromDto.setAssetChainId(1);
		coinFromDto.setAssetId(1);
		coinFromDto.setAddress(fromAddress);
		coinFromDto.setAmount(value);
		coinFromDto.setNonce(getNonce(fromAddress));
		inputs.add(coinFromDto);

		coinToDto.setAssetChainId(1);
		coinToDto.setAssetId(1);
		coinToDto.setAddress(toAddress);
		coinToDto.setAmount(value.subtract(gasPrice));
		outputs.add(coinToDto);

		transferDto.setInputs(inputs);
		transferDto.setOutputs(outputs);
		transferDto.setTime(DateUtil.currentSeconds());
		transferDto.setRemark(String.valueOf(Math.random() * 10000000));

		Map<String, String> transferTxOffline = (Map) NulsSDKTool.createTransferTxOffline(transferDto).getData();
//		System.out.println("transferTxOffline:" + JSON.toJSONString(transferTxOffline));
		JSONObject j1 = new JSONObject();
		j1.put("txHex", transferTxOffline.get("txHex"));
		j1.put("address", fromAddress);
		j1.put("priKey", privateKey);
		String resultSing = HttpRequest.post(main + "api/account/priKey/sign")
				.body(j1.toJSONString())
				.execute().body();
		System.out.println("resultSing:" + JSON.toJSONString(resultSing));
		
//		JSONObject jsonObject = JSONObject.parseObject(resultSing);
//		JSONObject jsonObject1 = (JSONObject) jsonObject.get("data");
//		Result<Map<String, Object>> result = NulsSDKTool.broadcast(jsonObject1.get("txHex").toString());
		
		SDKContext.addressPrefix = "ICW";
		Map<String, String> sign = (Map)NulsSDKTool.sign(transferTxOffline.get("txHex"), fromAddress, privateKey).getData();
		Result<Map<String, Object>> result = NulsSDKTool.broadcast(sign.get("txHex").toString());
		
		System.out.println("nonce:" + nonce + " result:" + result);
		withdrawResult.setMsg(result.getMsg());
		withdrawResult.setResultJson(JSON.toJSONString(result));
		if (result.isSuccess()) {
			Map<String, Object> data = result.getData();
			if (Boolean.valueOf(data.get("value").toString())) {
				String hash = data.get("hash").toString();
				withdrawResult.setSuccess(true);
				withdrawResult.setTxid(hash);
			}
		}
		return withdrawResult;
	}

	public static BigInteger getTokenBalance(String contractAddress, String address) {
		Map<String, Object> accountInfo = (Map) NulsSDKTool.getTokenBalance(contractAddress, address).getData();

		return new BigInteger(accountInfo.get("amount").toString());
	}

	public static WithdrawResult tokenTransfer(String fromAddress, String toAddress, String coinName, BigDecimal amount, String contractAddress, String privateKey) throws Exception {
		WithdrawResult withdrawResult = new WithdrawResult();
		withdrawResult.setSuccess(false);

		BigInteger tokenBalance = getTokenBalance(contractAddress, fromAddress);
//		String privateKey = getPrivateKey(fromAddress, accountPass);

		Long gasLimit = 100000l;
		String nonce = getNonce(fromAddress);
		BigInteger value = NumberUtil.mul(amount, 1).toBigInteger();

		JSONObject param = new JSONObject();
		param.put("fromAddress", fromAddress);
		param.put("senderBalance", tokenBalance);
		param.put("nonce", nonce);
		param.put("toAddress", toAddress);
		param.put("contractAddress", contractAddress);
		param.put("gasLimit", 17538);
		param.put("amount", value);
		String resultSing = HttpRequest.post(main + "api/contract/tokentransfer/offline")
				.body(param.toJSONString())
				.execute().body();
		JSONObject jsonObject = JSONObject.parseObject(resultSing);
		JSONObject jsonObject1 = (JSONObject) jsonObject.get("data");

		param = new JSONObject();
		param.put("txHex", jsonObject1.get("txHex"));
		param.put("address", fromAddress);
		param.put("priKey", privateKey);
		resultSing = HttpRequest.post(main + "api/account/priKey/sign")
				.body(param.toJSONString())
				.execute().body();
		JSONObject jsonObject2 = JSONObject.parseObject(resultSing);
		JSONObject jsonObject3 = (JSONObject) jsonObject2.get("data");

		Result<Map<String, Object>> result = NulsSDKTool.broadcast(jsonObject3.get("txHex").toString());

		System.out.println("代币转账result:" + result);
		if (result.isSuccess()) {
			Map<String, Object> data = result.getData();
			if (Boolean.valueOf(data.get("value").toString())) {
				String hash = data.get("hash").toString();
				withdrawResult.setSuccess(true);
				withdrawResult.setTxid(hash);
				withdrawResult.setMsg(result.getMsg());
				withdrawResult.setResultJson(JSON.toJSONString(result));
			}
		}
		return withdrawResult;
	}

	public static void main(String args[]) throws Exception {
		main = "http://192.168.110.125:8004/";

		NulsSDKBootStrap.initMain(main);
		
		WithdrawResult transfer = TIcw2.transferCopy(
				"ICWc6Hgbso2LaCwpHLstrW4JcNmRNJhfgqvt", "ICWc6Hgbso2LaCwpHLstrW4JcNmRNJhfgqvt", "icw", new BigDecimal(10000), new BigDecimal(0.001), "13e11486bcf6b0d1d72c61c3e90d7c22dd3d2b515ddb63e322424bd7512a6d75");
		System.out.println(JSON.toJSONString(transfer));
		
		transfer = tokenTransfer("ICWc6Hgbso2LaCwpHLstrW4JcNmRNJhfgqvt", "ICWc6Hgbso2LaCwpHLstrW4JcNmRNJhfgqvt", "icw", new BigDecimal(100000), "ICWc6HgmHPCxZ6vWN2CmbTN8smMdo29hbtGQ", "13e11486bcf6b0d1d72c61c3e90d7c22dd3d2b515ddb63e322424bd7512a6d75");
		System.out.println(JSON.toJSONString(transfer));
	}
}
