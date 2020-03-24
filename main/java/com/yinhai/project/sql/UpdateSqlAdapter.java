package com.yinhai.project.sql;

import com.yinhai.core.common.api.config.impl.SysConfig;
import com.yinhai.modules.cluster.api.IMessageAdapter;
import com.yinhai.modules.cluster.api.IMessageChannel;
import com.yinhai.modules.cluster.domian.jgroups.JMessageChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.springframework.context.annotation.DependsOn;
import org.springframework.orm.ibatis.SqlMapClientFactoryBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.Serializable;

/**
 * User: 马兴平 Date: 2017/10/11
 */
@Component("updateSqlAdapter")
@DependsOn({"&sqlMapClient"})
public class UpdateSqlAdapter implements IMessageAdapter {

    public IMessageChannel<Serializable> getChannel() {
        return updateSqlChannel;
    }

    @Resource(name="&sqlMapClient")
    SqlMapClientFactoryBean sqlMapClient;

    /**
     * 获取其他节点通知
     */
    private final IMessageChannel<Serializable> updateSqlChannel = new JMessageChannel(SysConfig.getSysConfig("clusterName","TaCluster")+"updateSql",new ReceiverAdapter(){
        @Override
        public void receive(Message msg) {
            byte[] bytes = msg.getBuffer();
            if(bytes!=null && bytes.length !=0){
                sqlMapClient.updateSql(new ByteArrayInputStream(bytes));
            }
        }
    });

    private UpdateSqlAdapter(){}
}
