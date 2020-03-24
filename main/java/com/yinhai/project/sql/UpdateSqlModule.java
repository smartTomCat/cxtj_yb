package com.yinhai.project.sql;

import com.yinhai.core.common.api.config.IConfig;
import com.yinhai.core.common.ta3.module.AbstractModule;
import com.yinhai.core.common.ta3.module.annotation.Module;
import com.yinhai.modules.cluster.api.IMessageAdapter;
import org.springframework.context.annotation.DependsOn;

import javax.annotation.Resource;

/**
 * User: 马兴平 Date: 2017/10/11
 */
@Module("updateSqlModule")
@DependsOn({"updateSqlAdapter"})
public class UpdateSqlModule extends AbstractModule {
    @Override
    public void setConfig(IConfig config) {

    }
    @Resource(name="updateSqlAdapter")
    private IMessageAdapter updateSqlAdapter;

    @Override
    public void moduleInit() {
        updateSqlAdapter.getChannel().connect();
    }

    @Override
    public void moduleDestroy() {
        updateSqlAdapter.getChannel().close();
    }
}
