package com.hong.generator.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 读取代码生成相关配置
 *
 * @author HongYi@10004580
 * @createTime 2021年05月26日 14:08:00
 */
@Component
@ConfigurationProperties(prefix = "gen")
@PropertySource(value = {"classpath:generator.yml"})
public class GenConfig {
    /**
     * 作者
     */
    public static String AUTHOR;

    /**
     * 生成包路径
     */
    public static String PACKAGE_NAME;

    /**
     * 自动去除表前缀，默认是true
     */
    public static String AUTO_REMOVE_PRE;
    /**
     * 表前缀(类名不会包含表前缀)
     */
    public static String TABLE_PREFIX;

    @Value("${author}")
    private  String author;
    @Value("${packageName}")
    private  String packageName;
    @Value("${autoRemovePre}")
    private String autoRemovePre;
    @Value("${tablePrefix}")
    private String tablePrefix;

    @PostConstruct
    public void init() {
        AUTHOR = this.author;
        PACKAGE_NAME = this.packageName;
        AUTO_REMOVE_PRE = this.autoRemovePre;
        TABLE_PREFIX = this.tablePrefix;
    }
}
