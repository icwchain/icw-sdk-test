package io.icw.test;

/**
 * Redis常量key
 */
public interface RedisKeyType {

    String DAY_WITHDRAW_COUNT = "DAY_WITHDRAW_COUNT_"; // 单人单日最大提币次数

    String CNY_RATE = "CNY/";   // CNY汇率

    String USDT_RATE = "USDT/"; // USDT汇率

    String CHANGES_COIN = "CHANGES/"; // 涨跌幅

    String ETH_BLOCK_NUMBER = "ETH_BLOCK_NUMBER";   // ETH块
    String USDT_BLOCK_NUMBER = "USDT_BLOCK_NUMBER"; // USDT块

    String TRC_USDT_BLOCK_NUMBER = "TRC_USDT_BLOCK_NUMBER"; // TRC_USDT块

    String LC_BLOCK_NUMBER = "LC_BLOCK_NUMBER";

    String TEST_BLOCK_NUMBER = "TEST_BLOCK_NUMBER";

    String APPROVE_TODAY = "APPROVE_TODAY"; // 单日实名认证次数

    String LIKE_ID = "LIKE_ID_"; //点赞记录

    String ADDRESS_LIST = "addressList";



    String RECEIVE_TIMEOUT = "RECEIVE_TIMEOUT";

    String REGISTER_WAIT_GETE_TIMEOUT = "RECEIVE_TIMEOUT";
}
