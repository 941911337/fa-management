package cn.john;

import cn.hutool.json.JSONUtil;
import cn.john.model.TDict;
import cn.john.oss.OssClient;
import cn.john.oss.OssInterface;
import cn.john.service.ITAssetClassService;
import cn.john.service.ITDictService;
import cn.john.token.TokenConfig;
import cn.john.utils.RedisUtil;
import cn.john.utils.TokenUtil;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author John Yan
 */
@EnableScheduling
@SpringBootApplication(scanBasePackages = "cn.john")
@EnableTransactionManagement
@EnableCaching
@EnableAspectJAutoProxy
@NacosPropertySource(dataId = "jdbc", autoRefreshed = true)
@NacosPropertySource(dataId = "redis", autoRefreshed = true)
@NacosPropertySource(dataId = "common", autoRefreshed = true)
public class FaManagementApplication {


    public static void main(String[] args) {
        SpringApplication.run(FaManagementApplication.class, args);
    }


    @Bean
    public InitializingBean initializingBean(RedisTemplate<Serializable, Serializable> redisTemplate,
                                             ITDictService dictService,
                                             ITAssetClassService assetClassService,
                                             OssInterface ossInterface, TokenConfig tokenConfig) {
        return () -> {
            RedisUtil.setRedisTemplate(redisTemplate);
            Map<String, List<TDict>> dictMap = dictService.getAllDict();
            RedisUtil.set("dict", JSONUtil.parse(dictMap));
            dictMap.forEach((k,v)->RedisUtil.hPut("dictMap",k, JSONUtil.parse(v)));
            assetClassService.initCache();
            OssClient.setOssInterface(ossInterface);
            TokenUtil.setTokenConfig(tokenConfig);
        };
    }






}
