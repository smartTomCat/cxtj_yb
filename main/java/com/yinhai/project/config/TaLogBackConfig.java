package com.yinhai.project.config;

import ch.qos.logback.classic.AsyncAppender;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.layout.TTLLLayout;
import ch.qos.logback.classic.spi.Configurator;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.encoder.LayoutWrappingEncoder;
import ch.qos.logback.core.spi.ContextAwareBase;
import com.yinhai.core.common.api.config.IKafkaAppender;
import com.yinhai.core.common.api.util.ValidateUtil;
import org.springframework.core.io.support.ResourcePropertySource;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

/**
 * maxp 2017年11月21日09:08:432
 * java配置日志代替logback.xml
 */
public class TaLogBackConfig extends ContextAwareBase implements Configurator {

    /**
     * 所有的logger，方便在线修改logger的level
     */
    public static final ConcurrentHashMap<String,Logger> loggerMaps = new ConcurrentHashMap<>();
    private static final String APPENDER_CONSOLE="console";
    private static final String APPENDER_KAFKA="kafka";
    private static final String APPENDER_ASYNC_KAFKA="asyncKafka";

    @Override
    public void configure(LoggerContext loggerContext) {
        addInfo("加载Ta+3Cloud的的Logback配置");
        //读取log.properties文件
        Map<String,String> logPropsMap =  loadLogProps();
        Map<String,String> loggerPropsMaps= loadLoggerProps(logPropsMap);
        //实例化consoleAppender
        ConsoleAppender<ILoggingEvent> ca = new ConsoleAppender<ILoggingEvent>();
        ca.setContext(loggerContext);
        ca.setName("STDOUT");
        LayoutWrappingEncoder<ILoggingEvent> encoder = new LayoutWrappingEncoder<ILoggingEvent>();
        encoder.setContext(loggerContext);
        // layout.setPattern("%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n");
        //实例化日志输出格式
        TTLLLayout layout = new TTLLLayout();
        layout.setContext(loggerContext);
        layout.start();
        encoder.setLayout(layout);
        ca.setEncoder(encoder);
        ca.start();//开启
        String appenders = logPropsMap.get("appender");
        if(appenders.contains(APPENDER_KAFKA)){
            /*KafkaAppender<ILoggingEvent> kafkaAppender = new KafkaAppender<>();
            kafkaAppender.setTopic(logPropsMap.get("kafka.topic"));
            kafkaAppender.setKeyingStrategy(new RoundRobinKeyingStrategy());
            kafkaAppender.setDeliveryStrategy(new BlockingDeliveryStrategy());
            kafkaAppender.addProducerConfig(logPropsMap.get("kafka.producerConfig"));
            kafkaAppender.addAppender(ca);
            kafkaAppender.start();*/
            ServiceLoader<IKafkaAppender> kafkaAppenders = ServiceLoader.load(IKafkaAppender.class);
            for (IKafkaAppender kafkaAppender:kafkaAppenders) {
                kafkaAppender.kafkaAppender(logPropsMap.get("kafka.topic"),logPropsMap.get("kafka.producerConfig"),ca);
            }
            if(appenders.contains(APPENDER_ASYNC_KAFKA)){
                AsyncAppender asyncKafkaAppender = new AsyncAppender();
                //不丢失日志.默认的,如果队列的80%已满,则会丢弃TRACT、DEBUG、INFO级别的日志
                asyncKafkaAppender.setDiscardingThreshold(0);
                // 更改默认的队列的深度,该值会影响性能.默认值为256
                asyncKafkaAppender.setQueueSize(512);
                asyncKafkaAppender.addAppender(ca);
                asyncKafkaAppender.start();
            }
        }
        Logger logger = null;
        for(Map.Entry<String,String> loggerEntry:loggerPropsMaps.entrySet()){
            logger = loggerContext.getLogger(loggerEntry.getKey());
            logger.setAdditive(false);
            logger.setLevel(Level.toLevel(loggerEntry.getValue()));//设置level
            logger.addAppender(ca);
            loggerMaps.put(loggerEntry.getKey(),logger);
        }
        //根logger
        Logger rootLogger = loggerContext.getLogger(Logger.ROOT_LOGGER_NAME);
        rootLogger.setLevel(Level.ERROR);
        rootLogger.addAppender(ca);
    }

    /**
     * 加载所有的logger
     * @param logProps
     * @return
     */
    private  Map<String,String> loadLoggerProps(Map<String, String> logProps) {
        Map<String,String> loggerPropsMaps = new HashMap<>();
        if(!ValidateUtil.isEmpty(logProps)){
            logProps.keySet()
                    .stream()
                    .filter(key -> key.startsWith("logger"))
                    .forEach(key ->loggerPropsMaps.put(key.substring(7),logProps.get(key)));
        }
        return loggerPropsMaps;
    }

    /**
     * 加载log.properties
     * @return
     */
    private Map<String,String> loadLogProps(){
        Map<String,String> logProps = new HashMap<>();
        try{
            ResourcePropertySource propertySource = new ResourcePropertySource("log.properties");
            for(String propKey: propertySource.getPropertyNames()) {
                logProps.put(propKey, (String) propertySource.getProperty(propKey));
            }
        }catch (IOException ioe){
            addError(ioe.getMessage());
        }
        return logProps;
    }

}
