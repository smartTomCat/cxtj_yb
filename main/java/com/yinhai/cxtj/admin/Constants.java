package com.yinhai.cxtj.admin;


/**
 * @Description 后台常量类
 * @author zhaohs
 * @version
 */
public class Constants {

    private Constants(){

    }

    /**
     * 数据源类型(1 jdbc数据源 2 jndi数据源)
     */
    public static final String  JDBC = "1";

    /**
     * 数据源类型(1 jdbc数据源 2 jndi数据源)
     */
    public static final String  JNDI = "2";

    /**
     * 默认框架数据源ID  (自定义)
     */
    public static final String  DEFAULT_DS_NO = "99999";

    /**
     * 创建数据源所用到的bean id 的前缀
     */
    public static final String ABSDAO_ = "absDao_";

    /**
     * 创建数据源所用到的bean id 的前缀
     */
    public static final String SQLMAPCLIENT_ = "sqlMapClient_";

    /**
     * 创建数据源所用到的bean id 的前缀
     */
    public static final String SQLEXECUTOR_ = "sqlExecutor_";


    /**
     * 创建数据源所用到的bean id 的前缀
     */
    public static final String DATASOURCEPROXY_ = "dataSourceProxy_";

    /**
     * 创建数据源所用到的bean id 的前缀
     */
    public static final String ORACLEDIALECT_ = "oracleDialect_";

    /**
     * 创建数据源所用到的bean id 的前缀
     */
    public static final String DAO_ = "dao_";

}
