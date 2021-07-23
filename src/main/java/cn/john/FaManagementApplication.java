package cn.john;

import cn.hutool.json.JSONUtil;
import cn.john.model.Dict;
import cn.john.oss.OssClient;
import cn.john.oss.OssInterface;
import cn.john.service.AssetClassService;
import cn.john.service.DictService;
import cn.john.token.TokenConfig;
import cn.john.utils.RedisUtil;
import cn.john.utils.TokenUtil;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.mybatis.spring.annotation.MapperScan;
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
@MapperScan("cn.john.dao")
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
    public InitializingBean initializingBean(RedisTemplate<Serializable, Serializable> redisTemplate, DictService dictService,
                                             AssetClassService assetClassService, OssInterface ossInterface, TokenConfig tokenConfig) {
        return () -> {
            RedisUtil.setRedisTemplate(redisTemplate);
            Map<String, List<Dict>> dictMap = dictService.getAllDict();
            RedisUtil.set("dict", JSONUtil.parse(dictMap));
            dictMap.forEach((k,v)->RedisUtil.hPut("dictMap",k, JSONUtil.parse(v)));
            assetClassService.initCache();
            OssClient.setOssInterface(ossInterface);
            TokenUtil.setTokenConfig(tokenConfig);
        };
    }






}
