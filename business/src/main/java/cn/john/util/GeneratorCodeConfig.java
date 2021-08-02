package cn.john.util;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.builder.GeneratorBuilder;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.fill.Column;
import com.baomidou.mybatisplus.generator.keywords.MySqlKeyWordsHandler;


/**
 * @Author John Yan
 * @Description GeneratorCodeConfig
 * @Date 2021/7/24
 **/
public class GeneratorCodeConfig {

    public static void main(String[] args) {
        //数据源配置
        DataSourceConfig dsc = new DataSourceConfig
                .Builder("jdbc:mysql://127.0.0.1:3306/log_center?useUnicode=true&characterEncoding=utf8&autoReconnect=true&failOverReadOnly=false&allowMultiQueries=true&rewriteBatchedStatements=TRUE&serverTimezone=GMT%2B8&useSSL=false",
                "dbName","dbPwd")
                .typeConvert(new MySqlTypeConvert())
                .keyWordsHandler(new MySqlKeyWordsHandler())
                .dbQuery(new MySqlQuery()).schema("log_center")
                .build();
        // 全局配置
        GlobalConfig gc = GeneratorBuilder.globalConfigBuilder()
                .openDir(true)
                .outputDir("/opt/john")
                .author("John Yan").dateType(DateType.TIME_PACK).commentDate("yyyy-MM-dd").build();


        // 包配置
        PackageConfig packageConfig =new PackageConfig.Builder()
                .parent("cn.john.log").entity("model").mapper("dao").build();

        //激活所有默认模板
        TemplateConfig templateConfig = new TemplateConfig.Builder().build();

        StrategyConfig strategyConfig = new StrategyConfig.Builder()
                // 实体配置构建者
                .entityBuilder()
                // 开启lombok模型
                .enableLombok()
                //乐观锁数据库表字段
                .versionColumnName("revision")
                // 数据库表映射到实体的命名策略
                .naming(NamingStrategy.underline_to_camel)
                //基于数据库字段填充
                .addTableFills(new Column("created_time", FieldFill.INSERT))
                .addTableFills(new Column("updated_time", FieldFill.INSERT_UPDATE))
                //控制器属性配置构建
                .controllerBuilder()
                .build();


        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {

        };

        ConfigBuilder configBuilder = new ConfigBuilder(packageConfig,dsc,strategyConfig,templateConfig,gc,cfg);
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator(dsc);
        mpg.config(configBuilder);

        mpg.execute();
    }


}
