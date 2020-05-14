package com.yinhai.cxtj.admin.service;

import com.yinhai.core.common.ta3.dto.TaParamDto;
import com.yinhai.cxtj.admin.base.service.CxtjBaseService;
import com.yinhai.sysframework.persistence.PageBean;

import java.util.List;
import java.util.Map;

public interface ResultSetManageService extends CxtjBaseService {


    /**
     * 查询数据集
     * @param dto
     * @return
     * @throws Exception
     */
    PageBean queryResultSets(TaParamDto dto) throws Exception;

    /**
     * 查询对应数据源下的实体表
     * @param yzb670 数据源id
     * @param yzb673 数据源名称
     * @return
     * @throws Exception
     */
    List<Map> genTableTree(String yzb670,String yzb673) throws Exception;

    /**
     * 查询表字段
     * @param yzb670 数据源id
     * @param tableNames 表名数组
     * @return
     * @throws Exception
     */
    List<Map> queryTableColumns(String yzb670,String[] tableNames) throws Exception;

    /**
     * 保存数据集
     * @param dto
     * @param columnsList
     * @throws Exception
     */
    void saveResultSet(TaParamDto dto,List<Map> columnsList) throws Exception;

    /**
     *  删除数据集
     * @param list
     * @throws Exception
     */
    void deleteResultSets(List<Map> list) throws Exception;

    /**
     *
     * @param yzb690
     * @return
     * @throws Exception
     */
    List<Map> queryZb69ByYzb690(String yzb690) throws Exception;

    /**
     *
     * @param yzb690
     * @return
     * @throws Exception
     */
    List<Map> queryZb89ByYzb690(String yzb690) throws Exception;



}
