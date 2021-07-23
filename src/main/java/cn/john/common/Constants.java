package cn.john.common;

/**
 * 常量表
 * 
 * @author ShenHuaJie
 * @version $Id: Constants.java, v 0.1 2014-2-28 上午11:18:28 ShenHuaJie Exp $
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

	interface  QyCode{
		int SUCCESS_CODE = 200;

		int DEL_SUCCESS_CODE = 204;

		int UPLOAD_SUCCESS_CODE = 201;
	}

}
