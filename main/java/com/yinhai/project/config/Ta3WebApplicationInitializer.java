package com.yinhai.project.config;

import com.yinhai.core.common.api.config.impl.SysConfig;
import com.yinhai.modules.configcenter.ta3.api.IConfigManager;
import com.yinhai.project.listener.StartupListener;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate5.support.OpenSessionInViewFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import java.util.Iterator;
import java.util.ServiceLoader;


/**
 * 框架Listener,Filter,Servlet配置类- 结合web.xml类一起使用
 * User: 马兴平 Date: 2017/9/18
 */
public class Ta3WebApplicationInitializer  implements WebApplicationInitializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(Ta3WebApplicationInitializer.class);

    /**
     * 启动函数
     * @param servletContext
     * @throws ServletException
     */
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        System.setProperty("org.jboss.logging.provider", "slf4j");
        LOGGER.info("初始化配置");
        initConfig();
        LOGGER.info("初始化Listener");
        initListener(servletContext);
        LOGGER.info("初始化Filter");
        initFilter(servletContext);
        LOGGER.info("初始化Servlet");
        initServlet(servletContext);
    }

    /**
     * 初始化配置
     */
    private void initConfig(){
        IConfigManager.loadConfig("config");
        //配置文件是否分布式化,存放在zookeeper中
        if(SysConfig.getSysConfigToBoolean("isConfigDistribute",false)){
            ServiceLoader<IConfigManager> serviceLoader = ServiceLoader.load(IConfigManager.class);
            Iterator<IConfigManager> mangers = serviceLoader.iterator();
            if (mangers.hasNext()) {
                IConfigManager configManager = mangers.next();
                configManager.init();
            }
        }
    }

    /**
     * 初始化listener
     * @param servletContext
     */
    private void initListener(ServletContext servletContext){
        initSpringContextListener(servletContext);
        initRequestContextListener(servletContext);
        intiHttpSessionEventPublisher(servletContext);
    }

    /**
     * 初始化Filter,其中注意配置Filter的代码顺序
     * @param servletContext
     */
    private void initFilter(ServletContext servletContext){
        if(SysConfig.getSysConfigToBoolean("isUseSpringSession",false)){
            initSpringSessionFilter(servletContext);
        }
        initCharacterEncodingFilter(servletContext);
        initOpenSessionInViewFilter(servletContext);
        initSpringSecurityFilterChain(servletContext);
        initRunqianFilter(servletContext);
    }

    /**
     * 加载SpringSessionFilter
     * @param servletContext
     */
    private void initSpringSessionFilter(ServletContext servletContext){
        FilterRegistration.Dynamic spsFilter = servletContext.addFilter("springSessionRepositoryFilter",DelegatingFilterProxy.class);
        spsFilter.addMappingForUrlPatterns(null,false,"/*");
        LOGGER.info("SpringSessionFilter加载完成");
    }

    /**
     * HibernateSession过滤器
     * 特殊设置，只针对框架持久层hibernate的url，.do结尾，并不需要过滤全部的请求
     * @param servletContext
     */
    private void initOpenSessionInViewFilter(ServletContext servletContext) {
        FilterRegistration.Dynamic osivFilter = servletContext.addFilter("osivFilter",OpenSessionInViewFilter.class);
        osivFilter.setInitParameter("singleSession","true");
        osivFilter.addMappingForUrlPatterns(null,false,"*.do");
        LOGGER.info("OpenSessionInViewFilter加载完成");
    }

    /**
     * 加载SpringSecurityFilterChain
     * @param servletContext
     */
    private void initSpringSecurityFilterChain(ServletContext servletContext) {
        FilterRegistration.Dynamic registration = servletContext.addFilter("springSecurityFilterChain",DelegatingFilterProxy.class);
        registration.addMappingForUrlPatterns(null,false,"/*");
        LOGGER.info("SpringSecurityFilterChain加载完成");
    }

    /**
     * 初始化Servlet
     * @param servletContext
     */
    private void initServlet(ServletContext servletContext){
        initSpringMVCDispatcherServlet(servletContext);
        initSreingCXFServlet(servletContext);
        /*initRunqianServlet(servletContext);*/
    }

    /**
     * 初始化Spring监听器
     * @param servletContext
     */
    private void initSpringContextListener(ServletContext servletContext){
        servletContext.setInitParameter("contextConfigLocation","classpath:app-context.xml");
        /**
         * 设置自定义的contextClass选项
         */
        servletContext.setInitParameter("contextClass","com.yinhai.project.springextend.TaXmlApplicationContext");
        servletContext.addListener(StartupListener.class);
        LOGGER.info("SpringContextListener加载完成");
    }

    /**
     * 初始化RequestContextListener
     * @param servletContext
     */
    private void initRequestContextListener(ServletContext servletContext){
        servletContext.addListener(RequestContextListener.class);
        LOGGER.info("RequestContextListener加载完成");
    }

    /**
     * 初始化HttpSessionEventPublisher
     * @param servletContext
     */
    private void intiHttpSessionEventPublisher(ServletContext servletContext){
        servletContext.addListener(HttpSessionEventPublisher.class);
        LOGGER.info("HttpSessionEventPublisher加载完成");
    }


    /**
     * 字符编码过滤器
     * @param servletContext
     */
    private void initCharacterEncodingFilter(ServletContext servletContext){
        FilterRegistration.Dynamic registration = servletContext.addFilter("CharacterEncodingFilter",CharacterEncodingFilter.class);
        registration.setInitParameter("encoding","UTF-8");
        registration.setInitParameter("forceEncoding","true");
        registration.addMappingForUrlPatterns(null,false,"/*");
        LOGGER.info("CharacterEncodingFilter加载完成");
    }

    /**
     * 加载SpringMVC的DispatcherServlet
     * @param servletContext
     */
    private void initSpringMVCDispatcherServlet(ServletContext servletContext){
        ServletRegistration.Dynamic registration = servletContext.addServlet("DispatcherServlet",DispatcherServlet.class);
        registration.setInitParameter("contextConfigLocation","classpath:spring/spring-mvc.xml");
        registration.setLoadOnStartup(1);
        registration.addMapping("*.do");
        //websocket
        registration.addMapping("/webSocketServer/*");
        registration.addMapping("/sockjs/*");
        LOGGER.info("SpringMVC-DispatcherServlet加载完成");
    }

    /**
     * java配置CXF的CXFServlet
     * @param servletContext
     */
    private void initSreingCXFServlet(ServletContext servletContext){
        ServletRegistration.Dynamic registration = servletContext.addServlet("CXFServlet", CXFServlet.class);
        registration.setLoadOnStartup(2);
        registration.addMapping("/services/*");
        LOGGER.info("CXFServlet加载完成");
    }

    //以下为润乾的初始化 add by zzb 2018/4/9
    /**
     *  润乾servlet初始化
     *  @param servletContext
     */
/*    private void initRunqianServlet(ServletContext servletContext){
        ServletRegistration.Dynamic registration1 = servletContext.addServlet("SetContextServlet", com.runqian.util.webutil.SetContextServlet.class);
        registration1.setLoadOnStartup(2);
        LOGGER.info("SetContextServlet加载完成");

        ServletRegistration.Dynamic registration = servletContext.addServlet("reportServlet", com.runqian.report4.view.ReportServlet.class);
        registration.setLoadOnStartup(1);
        registration.setInitParameter("configFile","/WEB-INF/classes/runqian/reportConfig.xml");
        registration.addMapping("/reportServlet");
        LOGGER.info("reportServlet加载完成");
    }*/

    /**
     * 润乾filter初始化
     * @param servletContext
     */
    private void initRunqianFilter(ServletContext servletContext){
/*        FilterRegistration.Dynamic registration = servletContext.addFilter("runqianPrintFilter",com.yinhai.ta3.runqian.filter.RunqianPrintFilter.class);
        registration.setInitParameter("includeServlets","pagedPrintServer,reportServlet");
        registration.addMappingForUrlPatterns(null,false,"/reportServlet");
        registration.addMappingForUrlPatterns(null,false,"/pagedPrintServer");
        LOGGER.info("CharacterEncodingFilter加载完成");*/
    }
}
