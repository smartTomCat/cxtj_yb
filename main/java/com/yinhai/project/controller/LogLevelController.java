package com.yinhai.project.controller;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.yinhai.core.app.ta3.web.controller.BaseController;
import com.yinhai.core.common.ta3.dto.TaParamDto;
import com.yinhai.modules.codetable.api.util.CodeTableUtil;
import com.yinhai.project.config.TaLogBackConfig;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 在线更改框架-logback的日志级别
 */
@Controller
@RequestMapping("logLevel")
public class LogLevelController extends BaseController {

    @RequestMapping("logLevelController.do")
    public String execute(){
        List<Map<String,String>> LoggerList = getLoggerList();
        setList("dgLog",LoggerList);
        return "loglevel/logLevelMg";
    }

    @RequestMapping("logLevelController!modify.do")
    public String modify(){
        TaParamDto dto = getTaDto();
        String logId = dto.getAsString("logId");
        String logLevelCode = (String) dto.get("logLevel");
        String logLevel = CodeTableUtil.getDesc("LOGLEVEL",logLevelCode);
        TaLogBackConfig.loggerMaps.get(logId).setLevel(Level.toLevel(logLevel));
        List<Map<String,String>> LoggerList = getLoggerList();
        setList("dgLog",LoggerList);

        return JSON;
    }

    public List<Map<String,String>> getLoggerList(){
        ConcurrentHashMap<String,Logger> loggerMaps = TaLogBackConfig.loggerMaps;
        List<Map<String,String>> LoggerList = new ArrayList<Map<String,String>>();
        for(Map.Entry<String,Logger> entry:loggerMaps.entrySet()){
            Map<String,String> logger = new HashMap<String,String>();
            logger.put("logId",entry.getKey());
            logger.put("logLevel",entry.getValue().getLevel().levelStr);
            LoggerList.add(logger);
        }
        return LoggerList;
    }
}
