package io.icw.test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.http.HttpRequest;
import io.icw.base.basic.AddressTool;
import io.icw.base.data.Address;
import io.icw.core.basic.Result;
import io.icw.core.constant.BaseConstant;
import io.icw.core.crypto.ECKey;
import io.icw.core.crypto.HexUtil;
import io.icw.core.parse.SerializeUtils;
import io.icw.v2.NulsSDKBootStrap;
import io.icw.v2.model.dto.CoinFromDto;
import io.icw.v2.model.dto.CoinToDto;
import io.icw.v2.model.dto.TransferDto;
import io.icw.v2.model.dto.TransferTxFeeDto;
import io.icw.v2.util.NulsSDKTool;

public class TIcw3 {

	public static String main = "http://47.104.86.175:8004/";

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
											  BigDecimal fee, String privateKey, String accountPass) throws Exception {
		WithdrawResult withdrawResult = new WithdrawResult();
		withdrawResult.setSuccess(false);

		List<CoinFromDto> inputs = new ArrayList<>();
		List<CoinToDto> outputs = new ArrayList<>();
		TransferDto transferDto = new TransferDto();
		CoinFromDto coinFromDto = new CoinFromDto();
		CoinToDto coinToDto = new CoinToDto();

		BigInteger gasPrice = getGas();

        if (StringUtils.isBlank(privateKey)) {
            privateKey = getPrivateKey(fromAddress, accountPass); //"13e11486bcf6b0d1d72c61c3e90d7c22dd3d2b515ddb63e322424bd7512a6d75";
        }
        
        Map<String, Object> chainInfo = getInfo();
        
		
		BigInteger value = NumberUtil.mul(amount, 100000000).toBigInteger();

		coinFromDto.setAssetChainId(1);
		coinFromDto.setAssetId(1);
		coinFromDto.setAddress(fromAddress);
		coinFromDto.setAmount(value);
		coinFromDto.setNonce(nonce);
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
//		System.out.println("resultSing:" + JSON.toJSONString(resultSing));
		// Map<String, String> sign = (Map)
		// NulsSDKTool.sign(transferTxOffline.get("txHex"), fromAddress,
		// privateKey).getData();
		JSONObject jsonObject = JSONObject.parseObject(resultSing);
		JSONObject jsonObject1 = (JSONObject) jsonObject.get("data");
		Result<Map<String, Object>> result = NulsSDKTool.broadcast(jsonObject1.get("txHex").toString());
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
	
	public static void createAddress() {
		 ECKey key = new ECKey();
         Address address = new Address(1, "ICW", BaseConstant.DEFAULT_ADDRESS_TYPE, SerializeUtils.sha256hash160(key.getPubKey()));
         System.out.println("address   :" + AddressTool.getStringAddressByBytes(address.getAddressBytes(), address.getPrefix()));
         System.out.println("privateKey:" + key.getPrivateKeyAsHex());
         System.out.println("privateKey:" + new BigInteger(key.getPrivKey().toString(16), 16));
         System.out.println("privateKey:" + key.getPrivKey());
         System.out.println(ECKey.fromPrivate(HexUtil.decode(key.getPrivateKeyAsHex())).getPrivateKeyAsHex());
         
         System.out.println(ECKey.fromPrivate(new BigInteger(key.getPrivKey().toString(16), 16)).getPrivateKeyAsHex());
         
	}

	public static void main(String args[]) throws Exception {
		main = "http://8.210.4.74:8004/";
		
		NulsSDKBootStrap.initMain(main);
		
		createAddress();
		/*
		System.out.println(NulsSDKTool.createAccount(1, "Joker999&*("));

		Result result = NulsSDKTool.getTx("8e54d5afb5a5c0aabdc47e7f9029a8bb38c4f365327a36c52e51ce402b26c718");
		System.out.println(result);
		Result contractResult = NulsSDKTool.getContractResult("8e54d5afb5a5c0aabdc47e7f9029a8bb38c4f365327a36c52e51ce402b26c718");
		System.out.println(contractResult);
		nonce = getNonce("EDAOd6Hgbso2LaCwpHLstrW4JcNmRNJhfgqvt");
		*/
//		String address = FileUtils.readFileToString(new File("C:\\Users\\Lenovo\\Desktop\\a.txt"));
//		List addresses = new ArrayList();
//		
//		String[] adds = address.split("privateKey:");
//		for (int i = 1; i < adds.length; i++) {
//			if (adds[i].split("address   :").length == 2) {
////				if (adds[i].length() == 64) {
//					addresses.add(adds[i].split("address   :")[1].replaceAll("\r\n", ""));
////				}
//			}
//		}
//		
////		for (int i = 0 ; i < addresses.size(); i++) {
//		for (int i = 0 ; i < 10; i++) {
//			System.out.println(addresses.get(i).toString());
//			final int ii = i;
//			System.out.println(ii);
////			try {
////				WithdrawResult transfer = TNuls.transferCopy("EDAOd6Hgbso2LaCwpHLstrW4JcNmRNJhfgqvt",
////						addresses.get(ii).toString(), CoinFinals.PLATFORM_COIN, new BigDecimal(1), null, null,
////						"nuls123456");
////			} catch (Exception e) {
////				e.printStackTrace();
////				System.out.println(i);
////			}
//			new Thread() {
//				public void run() {
//					try {
//						WithdrawResult transfer = TNuls.transferCopy(
//								 "EDAOd6Hgbso2LaCwpHLstrW4JcNmRNJhfgqvt", addresses.get(ii).toString(), CoinFinals.PLATFORM_COIN, new BigDecimal(1), null, null,
//								"nuls123456");
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//			}.start();
//		}
		
		Thread.sleep(1111000000000l);
//		WithdrawResult transfer = transferCopy("EDAOd6HgePvvSvfspdE1CStjPuqBqTmQ26ajp",
//				"EDAOd6HgjZXVJrTYDvPn8eeKW4eA5wFyQVvHe", CoinFinals.PLATFORM_COIN, new BigDecimal(1), null, null,
//				"Joker123!");
	}
}
