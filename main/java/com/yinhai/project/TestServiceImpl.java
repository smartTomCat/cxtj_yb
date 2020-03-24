package com.yinhai.project;

import com.yinhai.core.app.api.util.ServiceLocator;
import com.yinhai.core.domain.api.domain.service.ITimeService;

/**
 * @author lins0
 * @create 2018-03-30 15:14
 **/
public class TestServiceImpl implements ITestService {


    protected ITimeService timeAdaptedService;

    @Override
    public void doST() {
        System.out.println(ServiceLocator.getService("timeAdaptedService"));
    }

    public ITimeService getTimeAdaptedService() {
        return timeAdaptedService;
    }

    public void setTimeAdaptedService(ITimeService timeAdaptedService) {
        this.timeAdaptedService = timeAdaptedService;
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println(ServiceLocator.getService("timeAdaptedService"));
        System.out.println(timeAdaptedService);
    }
}
