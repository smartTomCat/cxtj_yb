package com.yinhai.ta3.project.ta3.test.api;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

/**
 * 测试基础类设计:测试框架Service,bpo之類,事務回滾
 * @author maxp
 * 2017年2月28日11:56:21
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:app-context.xml","classpath:spring/spring-mvc.xml"})
@TestExecutionListeners(listeners = {TransactionalTestExecutionListener.class})
@Transactional(transactionManager = "ta3DataSourceProxyTransactionManager")
public class BaseTest extends AbstractJUnit4SpringContextTests{

       protected MockHttpServletRequest request;
       protected MockHttpServletResponse response;

        @Before
        public void before(){
                request = new MockHttpServletRequest();
                response = new MockHttpServletResponse();
        }

        @After
        public void after(){
                request = null;
                response = null;
        }
}
