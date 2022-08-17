package io.icw.test;


import java.util.HashMap;
import java.util.Map;


/**
 * 常量
 *
 * @author luorc
 *
 */
public interface KConstants {


	public static final String PAGE_INDEX = "0";
	public static final String PAGE_SIZE = "15";

	public static final int MOENY_ADD = 1; //金钱增加
	public static final int MOENY_REDUCE = 2; //金钱减少
	public static final double LBS_KM=111.01;
	public static final int LBS_DISTANCE=50;

	/**
	* @Description: TODO(设备标识)
	* @author lidaye
	* @date 2018年8月20日
	 */
	public interface DeviceKey{
		public static final String Android= "android";
		public static final String IOS= "ios";
		public static final String WEB= "web";
		public static final String PC= "pc";
		public static final String MAC="mac";
	}
	/**
	* @Description: TODO(推送平台)
	* @author lidaye
	* @date 2018年8月20日
	 */
	public interface PUSHSERVER{
		//apns 推送
		public static final String APNS= "apns";

		public static final String APNS_VOIP= "apns_voip";
		//百度 推送
		public static final String BAIDU= "baidu";
		//小米 推送
		public static final String XIAOMI= "xiaomi";
		//华为 推送
		public static final String HUAWEI= "huawei";
		//极光 推送
		public static final String JPUSH= "Jpush";
	}

	// 消费类型
	public interface ConsumeType {
		public static final int USER_RECHARGE = 1;// 用户充值
		public static final int PUT_RAISE_CASH = 2;// 用户提现
		public static final int SYSTEM_RECHARGE = 3;// 后台充值
		public static final int SEND_REDPACKET = 4;// 发红包
		public static final int RECEIVE_REDPACKET = 5;// 领取红包
		public static final int REFUND_REDPACKET = 6;// 红包退款
	}

	// 集群配置标识
	public interface CLUSTERKEY{
		public static final int XMPP=1;// xmpp服务器
		public static final int HTTP=2;// http服务器
		public static final int VIDEO=3;// 视频服务器
		public static final int LIVE=4;// 直播服务器
	}

	//订单状态
	public interface OrderStatus {
		public static final int CREATE = 0;// 创建
		public static final int END = 1;// 成功
		public static final int DELETE = -1;// 删除
	}
	//支付方式
	public interface PayType {
		public static final int ALIPAY = 1;// 支付宝支付
		public static final int WXPAY = 2;// 微信支付
		public static final int BALANCEAY = 3;// 余额支付
		public static final int SYSTEMPAY = 4;// 系统支付
	}
	public interface Key {
		public static final String OEPN189_ACCESS_TOKEN = "KSMSService:access_token";
		public static final String RANDCODE = "KSMSService:randcode:%s";
		public static final String IMGCODE = "KSMSService:imgcode:%s";
		public static final String USERREG = "KSMSService:userreg:%s";
		public static final String SIGNIN = "KSMSService:signin:%s";
		public static final String MSGADD = "KSMSService:msgadd:%s";
		public static final String ADADD = "KSMSService:adadd:%s";
		public static final String LCTRAN = "KSMSService:lctran:%s";
		public static final String LKTRAN = "KSMSService:lktran:%s";
		public static final String LKTRANFORADDRESS = "KSMSService:lktranforaddress:%s";
		public static final String LKREDOUT = "KSMSService:lkredout:%s";
		public static final String LKREDOUTNUM = "KSMSService:lkredoutnum:%s";
		public static final String LKREDIN = "KSMSService:lkredin:%s";
		public static final String LCTRANIN = "KSMSService:lctranin:%s";
		public static final String LKETRAN = "KSMSService:lketran:%s";
		public static final String LKREWARD = "KSMSService:lkreward:%s";
		public static final String LKDRAW = "KSMSService:lkdraw:%s";
		public static final String LKCONVERT = "KSMSService:lkconvert:%s";
		public static final String LKCONVERTNUM = "KSMSService:lkconvertnum:%s";
		public static final String LKTRAN_NUM = "KSMSService:lktrannum:%s";
		public static final String LCTRAN_NUM = "KSMSService:lctrannum:%s";
		public static final String MSGGIFT_NUM = "KSMSService:msggiftnum:%s";
		public static final String EXTRAN = "KSMSService:extran:%s";

	}

	//public static final KServiceException InternalException = new KServiceException(KConstants.ErrCode.InternalException,KConstants.ResultMsg.InternalException);

	public interface Expire {
		static final int DAY1 = 86400;
		static final int DAY7 = 604800;
		static final int HOUR12 = 43200;
		static final int HOUR=3600;
	}


	public interface SystemNo{
		static final int System=10000;//系统号码
		static final int NewKFriend=10001;//新朋友
		static final int Circle=10002;//商务圈
		static final int AddressBook=10003;//通讯录
		static final int Notice=10006;//系统通知

	}
	/**
	* @Description: TODO(举报原因)
	* @author lidaye
	* @date 2018年8月9日
	 */
	public interface ReportReason{
		static final Map<Integer,String> reasonMap=new HashMap<Integer, String>() {
            {
                put(100, "发布不适当内容对我造成骚扰");
                put(101, "发布色情内容对我造成骚扰");
                put(102, "发布违法违禁内容对我造成骚扰");
                put(103, "发布赌博内容对我造成骚扰");
                put(104, "发布政治造谣内容对我造成骚扰");
                put(105, "发布暴恐血腥内容对我造成骚扰");
                put(106, "发布其他违规内容对我造成骚扰");

                put(120, "存在欺诈骗钱行为");
                put(130, "此账号可能被盗用了");
                put(140, "存在侵权行为");
                put(150, "发布仿冒品信息");

                put(200, "群成员存在赌博行为");
                put(210, "群成员存在欺诈骗钱行为");
                put(220, "群成员发布不适当内容对我造成骚扰");
                put(230, "群成员传播谣言信息");
            }
        };

	}

	public interface Result {
//		static final JSONMessage InternalException = new JSONMessage(1020101, "接口内部异常");
//		static final JSONMessage ParamsAuthFail = new JSONMessage(1010101, "请求参数验证失败，缺少必填参数或参数错误");
//		static final JSONMessage TokenEillegal = new JSONMessage(1030101, "缺少访问令牌");
//		static final JSONMessage TokenInvalid = new JSONMessage(1030102, "访问令牌过期或无效");
//		static final JSONMessage AUTH_FAILED = new JSONMessage(1030103, "权限验证失败");
	}
	public interface ResultCode {

		//接口调用成功
		static final int Success = 1;

		//接口调用失败
		static final int Failure = 0;

		//请求参数验证失败，缺少必填参数或参数错误
		static final int ParamsAuthFail = 1010101;

		//缺少请求参数：
		static final int ParamsLack = 1010102;

		//接口内部异常
		static final int InternalException = 1020101;

		//链接已失效
		static final int Link_Expired = 1020102;

		//缺少访问令牌
		static final int TokenEillegal = 1030101;

		//访问令牌过期或无效
		static final int TokenInvalid = 1030102;

		//权限验证失败
		static final int AUTH_FAILED = 1030103;

		//帐号不存在
		static final int AccountNotExist = 1040101;

		//帐号或密码错误
		static final int AccountOrPasswordIncorrect = 1040102;

		//原密码错误
		static final int OldPasswordIsWrong = 1040103;

		//短信验证码错误或已过期
		static final int VerifyCodeErrOrExpired = 1040104;

		//发送验证码失败,请重发!
		static final int SedMsgFail = 1040105;

		//请不要频繁请求短信验证码，等待{0}秒后再次请求
		static final int ManySedMsg = 1040106;

		//手机号码已注册!
		static final int PhoneRegistered = 1040107;

		//余额不足
		static final int InsufficientBalance = 1040201;


		//请输入图形验证码
		static final int NullImgCode=1040215;

		//图形验证码错误
		static final int ImgCodeError=1040216;

		//没有选择支付方式!
		static final int NotSelectPayType = 1040301;

		//支付宝支付后回调出错：
		static final int AliPayCallBack_FAILED = 1040302;

		//你没有权限删除!
		static final int NotPermissionDelete = 1040303;

		//账号被锁定
		static final int ACCOUNT_IS_LOCKED = 1040304;

	}


	public interface ResultMsgs {

		static final Map<String,String> InternalException =new HashMap<String,String>(){
			{
				put("zh", "接口内部异常");
				put("en", "An exception occurs to internal interface.");
			}
		};
		static final Map<String,String> ParamsAuthFail =new HashMap<String,String>(){
			{
				put("zh", "请求参数验证失败，缺少必填参数或参数错误");
				put("en", "Request for parameter verification failed due to lack of required parameters or wrong parameters");
			}
		};
		static final Map<String,String> TokenEillegal =new HashMap<String,String>(){
			{
				put("zh", "缺少访问令牌");
				put("en", "Lack of access token");
			}
		};
		static final Map<String,String> TokenInvalid =new HashMap<String,String>(){
			{
				put("zh", "访问令牌过期或无效");
				put("en", "Access token gets expired or invalid");
			}
		};
		static final Map<String,String> AUTH_FAILED =new HashMap<String,String>(){
			{
				put("zh", "权限验证失败");
				put("en", "Permission verification failed");
			}
		};

		static final Map<String,String> AliPayCallBack_FAILED =new HashMap<String,String>(){
			{
				put("zh", "支付宝支付后回调出错：");
				put("en", "Retracement error occurs after payment through Alipay");
			}
		};

		static final Map<String,String> AccountNotExist =new HashMap<String,String>(){
			{
				put("zh", "帐号不存在");
				put("en", "The account isn't existed.");
			}
		};
		static final Map<String,String> AccountOrPasswordIncorrect =new HashMap<String,String>(){
			{
				put("zh", "帐号或密码错误");
				put("en", "The account or password is wrong");
			}
		};
		static final Map<String,String> OldPasswordIsWrong =new HashMap<String,String>(){
			{
				put("zh", "原密码错误");
				put("en", "The original password is wrong");
			}
		};
		static final Map<String,String> VerifyCodeErrOrExpired =new HashMap<String,String>(){
			{
				put("zh", "短信验证码错误或已过期");
				put("en", "The verification code is wrong or expired");
			}
		};
		static final Map<String,String> InsufficientBalance =new HashMap<String,String>(){
			{
				put("zh", "余额不足");
				put("en", "Insufficient balance");
			}
		};

	}

	public interface ErrCodes {
		static final String InternalException ="InternalException";
		static final String ParamsAuthFail ="ParamsAuthFail";
		static final String TokenEillegal ="TokenEillegal";
		static final String TokenInvalid ="TokenInvalid";
		static final String AUTH_FAILED ="AUTH_FAILED";
		static final String PublishVerify_FAILED ="PublishVerify_FAILED";
		static final String AliPayCallBack_FAILED="AliPayCallBack_FAILED";
		static final String NotExistSendResume_FAILED="NotExistSendResume_FAILED";
		static final String NotCreateResume="NotCreateResume";
		static final String NotSelectPayType="NotSelectPayType";
		static final String PhoneRegistered="PhoneRegistered";
		static final String AccountNotExist="AccountNotExist";
		static final String AccountOrPasswordIncorrect="AccountOrPasswordIncorrect";
		static final String OldPasswordIsWrong="OldPasswordIsWrong";
		static final String VerifyCodeErrOrExpired="VerifyCodeErrOrExpired";
		static final String InsufficientBalance="InsufficientBalance";
		static final String OpenTalkResumeNotDetailed="OpenTalkResumeNotDetailed";
		static final String Resume_ConditionNotSatisfied="Resume_ConditionNotSatisfied";
		static final String NotTalk_Oneself="NotTalk_Oneself";
	}

	/**
	 * 执行任务完后的操作
	 */
	public interface ScheduleJobType {
		/**
		 * 执行完后删除任务
		 */
		static final int del =3;
		/**
		 * 执行完后暂停任务
		 */
		static final int stop =2;
		/**
		 * 执行完后继续任务
		 */
		static final int goon =1;
	}
	/**
	 * 执行任务完后的操作
	 */
	public interface NODE_JSON_RPC_PATH {
		/**
		 * 公链余额
		 */
		static final String GET_ACCOUNT_BALANCE ="getAccountBalance";
		/**
		 * token账户余额
		 */
		static final String GET_ACCOUNT_TOKENS ="getAccountTokens";
		/**
		 * 查询token交易列表
		 */
		static final String GET_TOKEN_TRANSFERS ="getTokenTransfers";
		/**
		 * 地址转账记录
		 */
		static final String GET_ACCOUNT_TXS ="getAccountTxs";
		/**
		 * 根据hash 获取交易详情
		 */
		static final String GET_TX ="getTx";
	}



}
