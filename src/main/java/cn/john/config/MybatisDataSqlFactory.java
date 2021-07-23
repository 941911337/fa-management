package cn.john.config;

import cn.john.utils.RsaUtil;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
/**
 * @author John Yan
 */
@Configuration
@MapperScan(basePackages = "cn.john.dao", sqlSessionTemplateRef  = "sqlSessionTemplate")
public class MybatisDataSqlFactory {

    @Value("${mybatis.mapper-locations}")
    private String mapperLocation;

    @NacosValue(value = "${jdbc.username}")
    private String username;

    @NacosValue("${jdbc.password}")
    private String password;

    @NacosValue("${jdbc.url}")
    private String dbUrl;

    @NacosValue("${jdbc.key}")
    private String key;



    @Bean(name="dataSource")
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    public HikariDataSource dataSource() {
        HikariDataSource hikariDataSource = DataSourceBuilder.create().type(HikariDataSource.class).build();
        String pwd = RsaUtil.pubDecrypt(password,key);
        hikariDataSource.setUsername(username);
        hikariDataSource.setPassword(pwd);
        hikariDataSource.setJdbcUrl(dbUrl);
        return hikariDataSource;
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory mysqlSessionFactory(@Qualifier("dataSource")  DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources(mapperLocation);
        sqlSessionFactoryBean.setMapperLocations(resources);
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "sqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
