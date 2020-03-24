package com.yinhai.cxtj.admin.util;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.stat.DruidDataSourceStatManager;
import com.alibaba.druid.util.DruidDataSourceUtils;
import com.yinhai.core.common.api.exception.AppException;
import com.yinhai.core.common.api.util.ValidateUtil;
import com.yinhai.cxtj.admin.Constants;
import com.yinhai.cxtj.admin.domain.Zb67Domain;
import com.yinhai.sysframework.persistence.ibatis.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.orm.ibatis.SqlMapClientFactoryBean;
import org.springframework.stereotype.Service;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhaohs
 * @date 2019/6/17  15:24
 * @describe 请填写类注释
 */
@Service
public class DynamicDataLoad extends ApplicationObjectSupport  implements InitializingBean {

    @Autowired
    private IDao dao;

    private DefaultListableBeanFactory  initBeanFactory()  {
        ConfigurableApplicationContext context = (ConfigurableApplicationContext) getApplicationContext();
        return (DefaultListableBeanFactory) context.getBeanFactory();
    }

    public void generateDao(String datasourceName){

        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(BaseDaoSupport.class);
        beanDefinitionBuilder.setParentName(Constants.ABSDAO_+datasourceName);
        DefaultListableBeanFactory beanFactory = initBeanFactory();
        beanFactory.registerBeanDefinition(Constants.DAO_ + datasourceName, beanDefinitionBuilder.getRawBeanDefinition());

    }

    public void generateAbstractDaoSupport(String datasourceName){
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(AbstractDaoSupport.class);
        beanDefinitionBuilder.setAbstract(true);
        beanDefinitionBuilder.addPropertyValue("sqlMapClient", getApplicationContext().getBean(Constants.SQLMAPCLIENT_ + datasourceName));
        beanDefinitionBuilder.addPropertyValue("sqlExecutor", getApplicationContext().getBean(Constants.SQLEXECUTOR_ + datasourceName));
        beanDefinitionBuilder.setInitMethodName("initialize");
        DefaultListableBeanFactory beanFactory = initBeanFactory();
        beanFactory.registerBeanDefinition(Constants.ABSDAO_+datasourceName, beanDefinitionBuilder.getRawBeanDefinition());
    }

    public void generateSqlMapClientFactoryBean(String datasourceName) {
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(SqlMapClientFactoryBean.class);

        List<String> list = new ArrayList<>();
        list.add("classpath:cxtj/SqlmapConfig-front.xml");
        list.add("classpath:cxtj/SqlmapConfig-admin.xml");

        beanDefinitionBuilder.addPropertyValue("configLocations", list);
        beanDefinitionBuilder.addPropertyValue("dataSource", getApplicationContext().getBean(Constants.DATASOURCEPROXY_ + datasourceName));

        DefaultListableBeanFactory beanFactory = initBeanFactory();
        beanFactory.registerBeanDefinition(Constants.SQLMAPCLIENT_+datasourceName, beanDefinitionBuilder.getRawBeanDefinition());

    }

    public void generateLimitSqlExecutor(String datasourceName) {
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(LimitSqlExecutor.class);

        beanDefinitionBuilder.addPropertyValue("dialect", getApplicationContext().getBean(Constants.ORACLEDIALECT_ + datasourceName));

        DefaultListableBeanFactory beanFactory = initBeanFactory();
        beanFactory.registerBeanDefinition(Constants.SQLEXECUTOR_+datasourceName, beanDefinitionBuilder.getRawBeanDefinition());

    }

    public void generateOracleDialect(String datasourceName,String type) {
        DefaultListableBeanFactory beanFactory = initBeanFactory();
        BeanDefinitionBuilder beanDefinitionBuilder;
        if(com.yinhai.cxtj.front.Constants.DSTYPE_ORACLE.equals(type.toUpperCase())){
            beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(OracleDialect.class);
        }else if(com.yinhai.cxtj.front.Constants.DSTYPE_MYSQL.equals(type.toUpperCase())){
            beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(MySQLDialect.class);
        }else if(com.yinhai.cxtj.front.Constants.DSTYPE_POSTGRESQL.equals(type.toUpperCase())){
            beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(PostgreSQLDialect.class);
        }else if(com.yinhai.cxtj.front.Constants.DSTYPE_GBASE8A.equals(type.toUpperCase())){
            beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(InformixDialect.class);
        }else{
            //其他则默认oracle
            beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(OracleDialect.class);
        }
        beanFactory.registerBeanDefinition(Constants.ORACLEDIALECT_+datasourceName, beanDefinitionBuilder.getRawBeanDefinition());
    }


    public void generateDataSourceProxy(String datasourceName) {
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(TransactionAwareDataSourceProxy.class);
        beanDefinitionBuilder.addPropertyValue("targetDataSource", getApplicationContext().getBean(datasourceName));
        DefaultListableBeanFactory beanFactory = initBeanFactory();
        beanFactory.registerBeanDefinition(Constants.DATASOURCEPROXY_+datasourceName, beanDefinitionBuilder.getRawBeanDefinition());
    }

    public void createDataSource(Zb67Domain zb67Domain) {
        BeanDefinitionBuilder dataSourceBuider   = BeanDefinitionBuilder.genericBeanDefinition(DruidDataSource.class);
        //添加数据源 的属性值
        if(ValidateUtil.isNotEmpty(zb67Domain.getYzb676())){
            dataSourceBuider.addPropertyValue("driverClassName",  zb67Domain.getYzb676());
        }
        dataSourceBuider.addPropertyValue("url",  zb67Domain.getYzb677());
        dataSourceBuider.addPropertyValue("username", zb67Domain.getYzb678());
        dataSourceBuider.addPropertyValue("password",  zb67Domain.getYzb679());
        //初始连接数
        dataSourceBuider.addPropertyValue("initialSize",  zb67Domain.getYzb681());
        //最小连接池数量
        dataSourceBuider.addPropertyValue("minIdle",  1);
        //最大连接池数量
        dataSourceBuider.addPropertyValue("maxActive",  zb67Domain.getYzb682());
        //最大获取连接秒数
        if( !(ValidateUtil.isEmpty(zb67Domain.getYzb684())) ){
            dataSourceBuider.addPropertyValue("maxWait",  zb67Domain.getYzb684()*1000);
        }
        dataSourceBuider.addPropertyValue("timeBetweenEvictionRunsMillis",  60000);
        dataSourceBuider.addPropertyValue("minEvictableIdleTimeMillis",  300000);
        dataSourceBuider.addPropertyValue("testWhileIdle",  "true");
        dataSourceBuider.addPropertyValue("connectionErrorRetryAttempts",  3);
        dataSourceBuider.addPropertyValue("breakAfterAcquireFailure",  true);
        DefaultListableBeanFactory beanFactory = initBeanFactory();
        beanFactory.registerBeanDefinition(String.valueOf(zb67Domain.getYzb670()), dataSourceBuider.getRawBeanDefinition());
    }

    public void createDataSourceJNDI(String yzb670,String jndiName) throws Exception{
        Context ctx = new InitialContext();
        DruidDataSource druidDataSource= (DruidDataSource) ctx.lookup(jndiName);
        BeanDefinitionBuilder dataSourceBuider   = BeanDefinitionBuilder.genericBeanDefinition(druidDataSource.getClass());
        DefaultListableBeanFactory beanFactory = initBeanFactory();
        beanFactory.registerBeanDefinition(yzb670, dataSourceBuider.getRawBeanDefinition());
    }

    public IDao getDao() {
        return dao;
    }

    public void setDao(IDao dao) {
        this.dao = dao;
    }

    public void creatAll(Zb67Domain zb67Domain) throws Exception {
        generateDataSourceProxy(String.valueOf(zb67Domain.getYzb670()));
        generateOracleDialect(String.valueOf(zb67Domain.getYzb670()),zb67Domain.getYzb674());
        generateLimitSqlExecutor(String.valueOf(zb67Domain.getYzb670()));
        generateSqlMapClientFactoryBean(String.valueOf(zb67Domain.getYzb670()));
        generateAbstractDaoSupport(String.valueOf(zb67Domain.getYzb670()));
        generateDao(String.valueOf(zb67Domain.getYzb670()));
    }

    /**
     * bean实例化后 立即会运行的方法  相当于 init-method
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception{
        try{
            //查询已经存储在数据库里面的数据源配置 dao为框架数据库的dao
            List<Zb67Domain> zb67DomainList = dao.queryForList("zb67.getList");
            if (ValidateUtil.isEmpty(zb67DomainList)) {
                return;
            }
            for(Zb67Domain zb67Domain : zb67DomainList){
                if(Constants.JDBC.equals(zb67Domain.getYzb671())){
                    //jdbc
                    //数据库密码解密
                    zb67Domain.setYzb679(Base64Util.decodeBase64(zb67Domain.getYzb679()));
                    createDataSource(zb67Domain);
                    creatAll(zb67Domain);
                    IDao dao_ = (IDao) getApplicationContext().getBean(Constants.DAO_ + String.valueOf(zb67Domain.getYzb670()));
                    logger.info("----------------------------根据数据源配置表创建的dao:" + dao_ + "-----------------------------");
                }else if("2".equals(zb67Domain.getYzb671())){
                    //jndi
                    createDataSourceJNDI(String.valueOf(zb67Domain.getYzb670()),zb67Domain.getYzb675());
                    creatAll(zb67Domain);
                    IDao dao_ = (IDao) getApplicationContext().getBean(Constants.DAO_ + String.valueOf(zb67Domain.getYzb670()));
                    logger.info("----------------------------根据数据源配置表创建的JNDI dao:" + dao_ + "-----------------------------");
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存数据源时并创建dao  调用该方法
     * @param zb67Domain
     * @return
     */
    public IDao creatDao(Zb67Domain zb67Domain) throws Exception{
        DefaultListableBeanFactory f = initBeanFactory();
        Boolean b = f.containsBean(Constants.DAO_ + String.valueOf(zb67Domain.getYzb670()));
        //判断是否已存在该dao
        if(b){
            IDao mydao = (IDao) getApplicationContext().getBean(Constants.DAO_+ String.valueOf(zb67Domain.getYzb670()));
            return mydao;
        }
        IDao resultDao;
        if(Constants.JDBC.equals(zb67Domain.getYzb671())){
            //jdbc
            createDataSource(zb67Domain);
        }else{
            //jndi
            createDataSourceJNDI(String.valueOf(zb67Domain.getYzb670()),zb67Domain.getYzb675());
        }
        creatAll(zb67Domain);
        resultDao = (IDao) getApplicationContext().getBean(Constants.DAO_+ String.valueOf(zb67Domain.getYzb670()));
        return resultDao;
    }

    /**
     * 获取dao
     * @param beanName
     * @return
     * @throws Exception
     */
    public IDao getDynamicDao(String beanName) throws AppException{
        if(Constants.DEFAULT_DS_NO.equals(beanName)){
            //为框架数据源
            return dao;
        }
        DefaultListableBeanFactory f = initBeanFactory();
        Boolean b = f.containsBean(Constants.DAO_ + beanName);
        //判断是否已存在该dao
        if(b){
            IDao mydao = (IDao) getApplicationContext().getBean(Constants.DAO_ + beanName);
            return mydao;
        }else {
            throw new AppException("未获取到"+ beanName +"数据源,请检查数据源配置");
        }
    }


    /**
     * 数据源 更改
     * @param zb67Domain
     */
    public void updateDaoDataSource(Zb67Domain zb67Domain) throws Exception{
        DefaultListableBeanFactory f = initBeanFactory();
        Boolean b = f.containsBean(Constants.DAO_ + String.valueOf(zb67Domain.getYzb670()));
        if(!b){
            throw new AppException("要修改的数据源不存在");
        }
        if(Constants.JDBC.equals(zb67Domain.getYzb671())){
            DruidDataSource ds = (DruidDataSource) getApplicationContext().getBean(String.valueOf(zb67Domain.getYzb670()));
            ds.setUrl(zb67Domain.getYzb677());
            ds.setUsername(zb67Domain.getYzb678());
            ds.setPassword(zb67Domain.getYzb679());
            ds.setInitialSize(zb67Domain.getYzb681());
            ds.setMaxActive(zb67Domain.getYzb682());
            ds.setMaxWait( zb67Domain.getYzb684()*1000);
            //先销毁
            destroyDaoDataSource(String.valueOf(zb67Domain.getYzb670()));
            //再创建
            createDataSource(zb67Domain);
            creatAll(zb67Domain);
        }else if("2".equals(zb67Domain.getYzb671())){
            //先销毁
            destroyDaoDataSource(String.valueOf(zb67Domain.getYzb670()));
            //创建
            createDataSourceJNDI(String.valueOf(zb67Domain.getYzb670()),zb67Domain.getYzb675());
            creatAll(zb67Domain);
        }

    }

    /**
     * 数据源删除
     * @param beanName
     */
    public void destroyDaoDataSource(String beanName) throws Exception{
        DefaultListableBeanFactory f = initBeanFactory();
        if(f.containsBean( beanName)){
            f.destroyBean( beanName);
        }
        if(f.containsBean(Constants.DAO_ + beanName)){
            f.destroyBean(Constants.DAO_ + beanName);
        }
        if(f.containsBean(Constants.ABSDAO_ + beanName)){
            f.destroyBean(Constants.ABSDAO_ + beanName);
        }
        if(f.containsBean(Constants.SQLMAPCLIENT_ + beanName)){
            f.destroyBean(Constants.SQLMAPCLIENT_ + beanName);
        }
        if(f.containsBean(Constants.SQLEXECUTOR_ + beanName)){
            f.destroyBean(Constants.SQLEXECUTOR_ + beanName);
        }
        if(f.containsBean(Constants.DATASOURCEPROXY_ + beanName)){
            f.destroyBean(Constants.DATASOURCEPROXY_ + beanName);
        }
        if(f.containsBean(Constants.ORACLEDIALECT_ + beanName)){
            f.destroyBean(Constants.ORACLEDIALECT_ + beanName);
        }
    }

    /**
     * 测试连接
     * @param zb67Domain
     * @return
     * @throws Exception
     */
    public Boolean testConnection (Zb67Domain zb67Domain) throws Exception{
        String sql = "SELECT 666 from dual";
        if(Constants.JDBC.equals(zb67Domain.getYzb671())){
            //创建temp后 销毁
            DruidDataSource dataSource = new DruidDataSource();
            dataSource.setName(zb67Domain.getYzb672());
            dataSource.setUrl(zb67Domain.getYzb677());
            dataSource.setDriverClassName(zb67Domain.getYzb676());
            dataSource.setUsername(zb67Domain.getYzb678());
            dataSource.setPassword(zb67Domain.getYzb679());
            dataSource.setInitialSize(1);
            dataSource.setMinIdle(1);
            dataSource.setMaxActive(5);
            dataSource.setMaxWait(4000);
            DruidDataSourceStatManager.addDataSource(dataSource,"Rtest_" + String.valueOf(zb67Domain.getYzb672()));
            dataSource.setConnectionErrorRetryAttempts(0);
            dataSource.setBreakAfterAcquireFailure(true);
            try(Connection connection1 =  dataSource.getConnection();
                PreparedStatement ps1 = connection1.prepareStatement(sql);
                ResultSet resultSet1 = ps1.executeQuery()
            ){
                DruidDataSourceStatManager.removeDataSource(dataSource);
            }catch (Exception e) {
                throw new Exception(e.getMessage());
            }
            return true;
        }else{
            Context ctx = new InitialContext();
            DruidDataSource druidDataSource= (DruidDataSource) ctx.lookup("java:comp/env/" + zb67Domain.getYzb675());
            druidDataSource.setConnectionErrorRetryAttempts(0);
            druidDataSource.setBreakAfterAcquireFailure(true);
            try(Connection connection2 =  druidDataSource.getConnection();
                PreparedStatement ps2 = connection2.prepareStatement(sql);
                ResultSet resultSet2 = ps2.executeQuery()
            ){
                druidDataSource.close();
            }catch (Exception e) {
                throw new Exception(e.getMessage());
            }
            return true;
        }

    }



}
