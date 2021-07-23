package cn.john.utils;

public interface ApiCode {
	
	public static final String SUCCESS_CODE = "0000";//数据返回成功
	
	public static final String PARAMNULL_CODE = "1001";//入参为空
	
	public static final String ACCOUNTERROR_CODE = "1002";//账号不存在
	
	public static final String TOKENERROR_CODE = "1003";//token校验不通过
	
	public static final String DATEERROR_CODE = "1004";//年月格式不正确yyyyMM
	
	public static final String DECRYPTERROR_CODE = "1005";//参数解密失败
	
	public static final String PDNULL_CODE = "1006";//指定月份综合pd无数据
	
	public static final String QUALPDNULL_CODE = "1007";//指定月份定性 PD信息不存在 
	
	public static final String TODAYACCESSLIMIT_CODE = "1008";//今天访问次数达到上限
	
	public static final String TOTALACCESSLIMIT_CODE = "1009";//总访问次数达到上限
	
	public static final String APIERROR_CODE = "9001";//接口调用失败
	
	public static final String FILEERROR_CODE = "9002";//文件解析失败
	
	public static final String ADDCOMPANYERROR_CODE = "1010";//添加评估公司信息失败 
	
	public static final String ADDTASKERROR_CODE = "1011";//创建任务失败(原因)
	
	public static final String NOCOMPANY_CODE = "9003";//查询公司列表无数据
	
	public static final String PHONECODE_CODE = "9004";//验证码发送失败
	
	public static final String EXPIREPHONE_CODE = "9005";//改手机已被占用
	
	public static final String EXPIREMESSAGECODE_CODE = "9006";//验证码过期
	
	public static final String ERRORMESSAGECODE_CODE = "9007";//验证码校验失败
	
	public static final String EXPIREACCOUNT_CODE = "9008";//用户已经存在
	
	public static final String NOLOGIN_CODE = "9009";//用户已经存在


	String INF_RES_SUCCESS_CODE = "200";//成功

	String INF_RES_NO_ACCESS_CODE = "10010";//无权限

	String INF_RES_ERROR_CODE = "999";//错误

	String OVER_LIMIT_CODE = "10011";//超过接口访问限制

	String INF_FORBID_CODE = "10012";//接口被禁用


	String THIRD_RES_SUCCESS_CODE = "20000";//成功
	String THIRD_TOKEN_EXPIRE_CODE = "30001";//token过期
	String THIRD_NO_ACCESS_CODE = "10001";//授权权限不足
	String THIRD_NO_PARAM_CODE = "10005";//缺少参数

}
