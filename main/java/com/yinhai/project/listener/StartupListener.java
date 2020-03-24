package com.yinhai.project.listener;

import com.yinhai.core.common.api.base.IConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoaderListener;

import javax.servlet.ServletContextEvent;
/**
 * 框架启动监听器
 * @maxp 2017年9月25日15:16:17 修改日志使用slf4j
 */
public class StartupListener extends ContextLoaderListener{

	private final static Logger LOGGER = LoggerFactory.getLogger(StartupListener.class);

	@Override
	public void contextInitialized(ServletContextEvent event) {
		LOGGER.info("开始启动系统");
		super.contextInitialized(event);
		IConstants.APP_IS_START_FINISH = true;
		LOGGER.info("启动系统完成");
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		LOGGER.info("系统开始关闭");
		super.contextDestroyed(event);
		LOGGER.info("系统成功关闭");
	}
}