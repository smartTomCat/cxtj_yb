package com.yinhai.project.springextend;

import com.yinhai.core.common.api.util.ValidateUtil;
import com.yinhai.core.common.ta3.bean.AbstractDynamicBean;
import com.yinhai.core.common.ta3.bean.AbstractDynamicReaderBean;
import com.yinhai.core.common.ta3.bean.DynamicResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.ResourceEntityResolver;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.web.context.support.XmlWebApplicationContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 扩展spring加载xml配置的bean,加载框架自定义的xml-bean
 * @maxp 2017年11月10日1
 */
public class TaXmlApplicationContext extends XmlWebApplicationContext {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaXmlApplicationContext.class);

    @Override
    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) throws BeansException, IOException {
        // Create a new XmlBeanDefinitionReader for the given BeanFactory.
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);

        // Configure the bean definition reader with this context's
        // resource loading environment.
        beanDefinitionReader.setEnvironment(this.getEnvironment());
        beanDefinitionReader.setResourceLoader(this);
        beanDefinitionReader.setEntityResolver(new ResourceEntityResolver(this));

        // Allow a subclass to provide custom initialization of the reader,
        // then proceed with actually loading the bean definitions.
        initBeanDefinitionReader(beanDefinitionReader);
        //所有动态bean集合
        List<AbstractDynamicBean> allDynamicBeans= new ArrayList<>();
        /**
         * 自定义加载事件总线配置的bean
         */
        loadDynamicBeans(allDynamicBeans,"com.yinhai.core.common.ta3.event.dynamic.EventBusDynamicReaderBean");
        /**
         * 自定义加载核心配置的bean
         */
        loadDynamicBeans(allDynamicBeans,"com.yinhai.core.app.api.dynamic.CoreAppDynamicReaderBean");

        /**
         * 自定义加载数据源配置的bean
         */
        loadDynamicBeans(allDynamicBeans,"com.yinhai.modules.datasource.base.dynamic.DataSourceDynamicReaderBean");

        /**
         * 自定义加载缓存配置的bean
         */
        loadDynamicBeans(allDynamicBeans,"com.yinhai.modules.cache.api.dynamic.CacheDynamicReaderBean");

        /**
         * 自定义加载EH缓存配置的bean
         */
        loadDynamicBeans(allDynamicBeans,"com.yinhai.modules.cache.ehcache.dynamic.EhCacheDynamicReaderBean");
        /**
         * 自定义加载Redis缓存配置的bean
         */
        loadDynamicBeans(allDynamicBeans,"com.yinhai.modules.cache.redis.dynamic.RedisCacheDynamicReaderBean");
        /**
         * 自定义加载coherence缓存配置的bean
         */
        loadDynamicBeans(allDynamicBeans,"com.yinhai.modules.cache.coherence.dynamic.CoherenceCacheDynamicReaderBean");

        /**
         * 自定义加载核心域配置的bean
         */
        loadDynamicBeans(allDynamicBeans,"com.yinhai.core.service.ta3.domain.dynamic.CoreDomainDynamicReaderBean");
        /**
         * 自定义加载码表配置的bean
         */
        loadDynamicBeans(allDynamicBeans,"com.yinhai.modules.codetable.domain.dynamic.CodeTableDynamicReaderBean");
        /**
         * 自定义加载组织机构配置的bean
         */
        loadDynamicBeans(allDynamicBeans,"com.yinhai.modules.org.ta3.domain.dynamic.OrgDynamicReaderBean");
        /**
         * 自定义加载系统管理-登陆日志配置的bean
         */
        loadDynamicBeans(allDynamicBeans,"com.yinhai.ta3.sysapp.signrecord.dynamic.SignRecordDynamicReaderBean");
        /**
         * 自定义加载系统管理-日志同步配置的bean
         */
        loadDynamicBeans(allDynamicBeans,"com.yinhai.ta3.sysapp.synclog.dynamic.SyncLogDynamicReaderBean");
        /**
         * 自定义加载系统管理-基础服务配置的bean
         */
        loadDynamicBeans(allDynamicBeans,"com.yinhai.modules.sysapp.domain.dynamic.SysappDynamicReaderBean");
        /**
         * 自定义加载资源管理配置的bean
         */
        loadDynamicBeans(allDynamicBeans,"com.yinhai.modules.authority.ta3.domain.dynamic.AuthorityDynamicReaderBean");

        /**
         * 自定义加载Spring-security的配置
         */
        loadDynamicBeans(allDynamicBeans,"com.yinhai.modules.security.spring.domain.dynamic.SecurityDynamicReaderBean");
        /**
         * 自定义加载spring-template的配置(模板管理)
         */
        loadDynamicBeans(allDynamicBeans,"com.yinhai.modules.template.domain.dynamic.TemplateDynamicReaderBean");
        /**
         * 自定义加载spring-datasource的配置(数据源管理)
         */
        loadDynamicBeans(allDynamicBeans,"com.yinhai.modules.datasource.ta3.domain.dynamic.DataSourceMgDynamicReaderBean");
        /**
         * 自定义加载spring-xquery的配置(自定义查询)
         */
        loadDynamicBeans(allDynamicBeans,"com.yinhai.modules.xquery.ta3.domain.dynamic.XQueryDynamicReaderBean");
        /**
         * 自定义加载spring-portal的配置(工作台模块)
         */
        loadDynamicBeans(allDynamicBeans,"com.yinhai.modules.portal.domain.dynamic.PortalDynamicReaderBean");
        /**
         * 自定义加载spring-limiter的配置(限流模块)
         */
        loadDynamicBeans(allDynamicBeans,"com.yinhai.modules.limiter.domain.dynamic.LimiterDynamicReaderBean");
        /**
         * 自定义加载spring-poi的配置(excel导入导出模块)
         */
        loadDynamicBeans(allDynamicBeans,"com.yinhai.modules.poi.domain.dynamic.PoiDynamicReaderBean");
        /**
         * 自定义加载spring-runqian的配置(报表模块)
         */
        loadDynamicBeans(allDynamicBeans,"com.yinhai.modules.runqian.dynamic.RunqianDynamicReaderBean");
        /**
         * 自定义加载spring-message的配置(消息通知模块)
         */
        loadDynamicBeans(allDynamicBeans,"com.yinhai.modules.message.domain.dynamic.MessageDynamicReaderBean");
        /**
         * 自定义加载spring-demo的配置(示例模块)
         */
        loadDynamicBeans(allDynamicBeans,"com.yinhai.modules.demo.domain.dynamic.DemoDynamicReaderBean");
        /**
         * 自定义加载spring-cluster的配置(集群模块)
         */
        loadDynamicBeans(allDynamicBeans,"com.yinhai.modules.cluster.domian.dynamic.ClusterDynamicReaderBean");
        /**
         * 自定义加载spring-fileimport的配置(报盘导入模块)
         */
        loadDynamicBeans(allDynamicBeans,"com.yinhai.modules.fileimport.domain.dynamic.FileimportDynamicReaderBean");
        /**
         * 自定义加载spring-elasticjob的配置(elasticJob模块)
         */
        loadDynamicBeans(allDynamicBeans,"com.yinhai.modules.elasticjob.domain.dynamic.ElasticJobDynamicReaderBean");
        /**
         * 自定义加载spring-schedule的配置(定时任务模块)
         */
        loadDynamicBeans(allDynamicBeans,"com.yinhai.modules.schedul.domain.dynamic.ScheduleDynamicReaderBean");
        /**
         * 自定义加载spring-pagereview的配置(页面还原模块)
         */
        loadDynamicBeans(allDynamicBeans,"com.yinhai.modules.pagereview.domain.dynamic.PageReviewDynamicReaderBean");
        //加载所有的动态bean
        if(ValidateUtil.isNotEmpty(allDynamicBeans)){
            for(AbstractDynamicBean dynamicBean : allDynamicBeans){
                if(LOGGER.isDebugEnabled()){
                    LOGGER.debug(dynamicBean.getClass().getName()+"加载中...");
                }
                beanDefinitionReader.loadBeanDefinitions(new DynamicResource(dynamicBean));
            }
        }
        loadBeanDefinitions(beanDefinitionReader);
    }

    private void loadDynamicBeans(List<AbstractDynamicBean> allDynamicBeans,String classpathName) {
        try {
            if(LOGGER.isDebugEnabled()){
                LOGGER.debug(classpathName + " 读取中...");
            }
            AbstractDynamicReaderBean readerBean = (AbstractDynamicReaderBean)Class.forName(classpathName).newInstance();
            List<AbstractDynamicBean> dynamicBeans= readerBean.loadBeans();
            allDynamicBeans.addAll(dynamicBeans);
        } catch (InstantiationException e) {
            LOGGER.error(classpathName + " 初始化失败..."+e.getMessage());
        } catch (IllegalAccessException e) {
            LOGGER.error(classpathName + " 初始化失败..."+e.getMessage());
        } catch (ClassNotFoundException e) {
            LOGGER.error(classpathName + " 读取失败，如果未引入该模块，请忽略该信息");
        }
    }
}

