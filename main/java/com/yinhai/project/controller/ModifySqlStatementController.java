package com.yinhai.project.controller;

import com.yinhai.core.app.api.util.ServiceLocator;
import com.yinhai.core.app.ta3.web.controller.BaseController;
import com.yinhai.core.common.api.exception.AppException;
import com.yinhai.project.sql.UpdateSqlAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.ibatis.SqlMapClientFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * User: 马兴平 Date: 2017/10/11
 * 项目运行过程中修改sqlmap中statementId的内容
 */
@Controller
@RequestMapping("modifySqlStatement")
public class ModifySqlStatementController extends BaseController {

    private final String DEFAULT_DATASOURCE="ta3";

    private static final Logger LOGGER = LoggerFactory.getLogger(ModifySqlStatementController.class);

    @RequestMapping("modifySqlStatementController.do")
    public String execute(){
        return "modifySqlStatement/modifySqlStatement";
    }

    /**
     * 文件上传SqlMapFile
     * @param sqlMapFile
     * @return
     */
    @RequestMapping("modifySqlStatementController!uploadSqlMapFile.do")
    public String uploadSqlMapFile(@RequestPart("sqlMapFile") MultipartFile sqlMapFile){
        String dataSource = getTaDto().getAsString("dataSource");
        SqlMapClientFactoryBean sqlMapClient = null;
        try{
            if(DEFAULT_DATASOURCE.equals(dataSource)){
                sqlMapClient = (SqlMapClientFactoryBean) ServiceLocator.getService("&sqlMapClient");
            }else{
                sqlMapClient = (SqlMapClientFactoryBean) ServiceLocator.getService("&"+dataSource+"SqlMapClient");
            }
        }catch(Exception e){
            throw new AppException("请填写正确的数据源");
        }
        if(sqlMapFile != null){
            if(sqlMapFile.getOriginalFilename().endsWith("xml")){//文件上传限制后缀xml文件
                UpdateSqlAdapter updateSqlAdapter = (UpdateSqlAdapter) ServiceLocator.getService("updateSqlAdapter");
                try{
                    sqlMapClient.updateSql(sqlMapFile.getInputStream());
                    //集群同步
                    updateSqlAdapter.getChannel().send(sqlMapFile.getBytes());
                    setMessage("热修改"+sqlMapFile.getOriginalFilename()+"成功");
                }catch (IOException ioe) {
                    LOGGER.error(ioe.getMessage(),ioe);
                    setMessage("热修改失败");
                }
            }
        }
        return JSON;
    }

    /**
     * 修改粘贴的sqlmap内容
     * @return
     */
    @RequestMapping("modifySqlStatementController!updateSql.do")
    public String updateSql() throws Exception{
        String dataSource = getTaDto().getAsString("dataSource");
        SqlMapClientFactoryBean sqlMapClient = null;
        try{
            if(DEFAULT_DATASOURCE.equals(dataSource)){
                sqlMapClient = (SqlMapClientFactoryBean) ServiceLocator.getService("&sqlMapClient");
            }else{
                sqlMapClient = (SqlMapClientFactoryBean) ServiceLocator.getService("&"+dataSource+"SqlMapClient");
            }
        }catch(Exception e){
            throw new AppException("请填写正确的数据源");
        }
        UpdateSqlAdapter updateSqlAdapter = (UpdateSqlAdapter) ServiceLocator.getService("updateSqlAdapter");
       byte[] xmlByte = getTaDto().getAsString("sqlStr").getBytes();
       if(xmlByte!=null && xmlByte.length != 0){
           sqlMapClient.updateSql(new ByteArrayInputStream(xmlByte));
           //集群同步
           updateSqlAdapter.getChannel().send(xmlByte);
           setMessage("热修改成功");
       }else{
           setMessage("文件解析出错");
       }
       return JSON;
    }
}
