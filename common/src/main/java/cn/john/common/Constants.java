package cn.john.common;

/**
 * <p>
 * 常量
 * </p>
 *
 * @author John Yan
 * @since 2021-07-24
 */
public interface Constants {

	/** 缓存命名空间 */
	String CACHE_NAMESPACE = "FA:";

	/** 缓存命名空间 */
	String LOGIN_CACHE_NAMESPACE = CACHE_NAMESPACE+"login:";

	/**
	 * token 过期时间 30天
	 */
	long TOKEN_EXPIRE_TIME = 30 * 60 * 60 * 1000;

	/**
	 * 青云返回值
	 */
	interface  QyCode{
		/**
		 * 成功
		 */
		int SUCCESS_CODE = 200;

		/**
		 * 删除成功
		 */
		int DEL_SUCCESS_CODE = 204;

		/**
		 * 上传成功
		 */
		int UPLOAD_SUCCESS_CODE = 201;
	}

	/**
	 * 删除标识常量
	 */
	interface IsDel{
		//删除
		int YES = 1;
		//未删除
		int NO = 0;
	}

	/**
	 * 限制只查一条
	 */
	String LIMIT_ONE = "limit 1";

	/**
	 * 查询ip地址接口
	 */
	String IP_INTERFACE_URL = "http://whois.pconline.com.cn/";


	/**
	 * 本地ip
	 */
	String IP = "127.0.0.1";

	/**
	 * 位置主机名
	 */
	String UNKNOWN = "unknown";

}
