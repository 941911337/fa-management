package cn.john.log.config;

import cn.john.log.task.LogCondition;
import cn.john.utils.RsaUtil;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.toolkit.GlobalConfigUtils;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * @Author John Yan
 * @Description MybatisPlusConfig
 * @Date 2021/7/24
 **/
@Configuration
@MapperScan(basePackages = "cn.john.log.dao", sqlSessionTemplateRef  = "logSqlSessionTemplate")
@Conditional(value = {LogCondition.class})
public class MybatisPlusLogConfig {

    @Value("${mybatis-plus.log.mapper-locations}")
    private String mapperLocation;

    @Value("${mybatis-plus.log.type-aliases-package}")
    private String aliasPackage;


    @NacosValue(value = "${jdbc.log.username}")
    private String username;

    @NacosValue("${jdbc.log.password}")
    private String password;

    @NacosValue("${jdbc.log.url}")
    private String dbUrl;

    @NacosValue("${jdbc.log.key}")
    private String key;


    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }


    @Bean(name="logDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    public HikariDataSource dataSource() {
        HikariDataSource hikariDataSource = DataSourceBuilder.create().type(HikariDataSource.class).build();
        String pwd = RsaUtil.pubDecrypt(password,key);
        hikariDataSource.setUsername(username);
        hikariDataSource.setPassword(pwd);
        hikariDataSource.setJdbcUrl(dbUrl);
        return hikariDataSource;
    }

    @Bean(name = "logSqlSessionFactory")
    public SqlSessionFactory mysqlSessionFactory(@Qualifier("logDataSource") DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setCacheEnabled(false);
        sqlSessionFactory.setTypeAliasesPackage(aliasPackage);
        sqlSessionFactory.setConfiguration(configuration);
        sqlSessionFactory.setPlugins(new Interceptor[]{
                mybatisPlusInterceptor()
        });
        GlobalConfig globalConfig = GlobalConfigUtils.defaults();
        sqlSessionFactory.setGlobalConfig(globalConfig);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources(mapperLocation);
        sqlSessionFactory.setMapperLocations(resources);
        return sqlSessionFactory.getObject();
    }

    @Bean(name = "logSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("logSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }








}
