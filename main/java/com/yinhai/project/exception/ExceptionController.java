package com.yinhai.project.exception;

import com.yinhai.core.app.api.util.WebUtil;
import com.yinhai.core.app.ta3.web.controller.BaseController;
import com.yinhai.core.common.api.config.impl.SysConfig;
import com.yinhai.core.common.api.exception.AppException;
import com.yinhai.core.common.api.exception.IllegalInputAppException;
import com.yinhai.core.common.api.exception.PrcException;
import com.yinhai.core.common.api.util.NetUtils;
import com.yinhai.core.common.ta3.dto.TaParamDto;
import com.yinhai.core.common.ta3.event.TaEvent.EVENTTYPE;
import com.yinhai.core.common.ta3.event.TaEventPublisher;
import com.yinhai.modules.security.api.util.SecurityUtil;
import com.yinhai.ta3.sysapp.synclog.eventresource.SyncLogEventResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.HandlerMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 通用异常处理
 *
 * @author aolei
 */
@ControllerAdvice
public class ExceptionController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(ExceptionController.class);
    private String port = String.valueOf(NetUtils.getServerPort());
    private String host = NetUtils.getLocalHost();
    private String errorCode;
    {
        if(null!=host){
            StringBuilder code = new StringBuilder();
            code.append("-")
                    .append(host.replace(".","-"))
                    .append("-")
                    .append(port).reverse();
            errorCode = code.toString();
        }
    }
    @Resource(name = "taEventPublisher")
    private TaEventPublisher taEventPublisher;

    @ExceptionHandler
    public String resolveException(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod, Exception ex, Model model) throws Exception {

        TaParamDto taDto = new TaParamDto();
        taDto.put("request", request);
        taDto.put("exception", ex);
        taDto.put("handlerMethod", handlerMethod);
//        taDto.put("host", InetAddress.getLocalHost());
        taDto.put("currentMenu", SecurityUtil.getCurrentMenu(request));
        taDto.put("host",host);
        taDto.put("port",port);
        String logId="";
        if(!(ex  instanceof AppException)){
            // 不是app 异常 推送异常事件，记录日志信息
            logId = UUID.randomUUID().toString();
            taDto.put("logId", logId);
            taEventPublisher.publish(new SyncLogEventResource(EVENTTYPE.LOG_SYNC, taDto), EVENTTYPE.LOG_SYNC);
        }
        Map<String, String> rs = getExceptionTypeAndDetailMessage(ex, logId);
        String detail = rs.get("exceptionStack");
        String exceptionType = rs.get("exceptionType");
        String jsonMsg = rs.get("formateMessage");
        String message = rs.get("message");
        String defaultMessage = message;
        String defaultJsonMessage="{\"success\":\"false\",\"msg\":\""+message+"\",\"jsonperror\":\"true\"}";
        if ("true".equals(SysConfig.getSysConfig("developMode"))){
            ex.printStackTrace();
        }
        //ta:fileupload 上传异常 优先处理
        if("fileupload".equals(request.getParameter("ta_tag"))||"uploader".equals(request.getParameter("ta_tag"))){
            response.setContentType("text/json; charset=UTF-8"); //解决从服务器返回中文乱码问题
            PrintWriter writer = response.getWriter();
            String jsopStr = "{\"success\":false" +
                    ",\"msg\":\"" + message +"\"" +
                    ",\"jsonperror\":true" +
                    ",\"errorDetail\":\"" + detail + "\"}";
            if (null != exceptionType && "1".equals(exceptionType)) {//框架提供的业务异常
                response.setStatus(418);
                if(SysConfig.getSysConfigToBoolean("developMode",true)){
                    writer.write(jsopStr);
                }else{
                    writer.write(defaultJsonMessage);
                }
                writer.flush();
            } else if (null != exceptionType && "2".equals(exceptionType)) {//RuntimeException
                response.setStatus(500);
                if(SysConfig.getSysConfigToBoolean("developMode",true)){
                    writer.write(jsopStr);
                }else{
                    writer.write(defaultJsonMessage);
                }
                writer.flush();
            }
            setSuccess(false);
            return null;
        }
        // 异步异常
        else if (WebUtil.isAjaxRequest(request)) {
            LOG.error(handleException(ex), ex);
            response.setContentType("text/json; charset=UTF-8"); //解决从服务器返回中文乱码问题
            PrintWriter writer = response.getWriter();
            if (null != exceptionType && "1".equals(exceptionType)) {//框架提供的业务异常
                response.setStatus(418);
                if(SysConfig.getSysConfigToBoolean("developMode",true)){
                    writer.write(jsonMsg);
                }else{
                    writer.write(defaultJsonMessage);
                }
                writer.flush();
            } else if (null != exceptionType && "2".equals(exceptionType)) {//RuntimeException
                response.setStatus(500);
                if(SysConfig.getSysConfigToBoolean("developMode",true)){
                    writer.write(jsonMsg);
                }else{
                    writer.write(defaultJsonMessage);
                }
                writer.flush();
            }
            setSuccess(false);
            return null;
        }
        //jsonp调用异常
        else if(request.getParameter("callbackparam")!=null && "jsonpCallback".equals(request.getParameter("callbackparam"))){
            LOG.error(handleException(ex), ex);
            String jsopStr = "{\"success\":false" +
                             ",\"msg\":\"" + message +"\"" +
                             ",\"jsonperror\":true" +
                             ",\"errorDetail\":\"" + detail + "\"}";
            if(SysConfig.getSysConfigToBoolean("developMode",true)) {
                request.setAttribute("jsonMsg", jsopStr);
            }else{
                request.setAttribute("jsonMsg",defaultJsonMessage);
            }
            return "JsonpException";
        }
        // 同步异常
        else {
            StringBuffer jspStr = new StringBuffer();
            if (null != exceptionType && "1".equals(exceptionType)) {//框架提供的业务异常
                jspStr.append("ta/418.jsp");
                if ("true".equals(SysConfig.getSysConfig("developMode"))){
                    //jspStr.append("?exceptionJsonMsg="+URLEncoder.encode(message+":"+detail,"UTF-8"));//开发模式信息太多，容易超出浏览器或服务器的url长度限制
                    request.getSession().setAttribute("exceptionJsonMsg",message+":"+detail);
                }else{
                    jspStr.append("?exceptionJsonMsg="+ URLEncoder.encode(defaultMessage,"UTF-8"));
                }
                response.setStatus(418);
            } else if (null != exceptionType && "2".equals(exceptionType)) {//RuntimeException
                jspStr.append("ta/500.jsp");
                jspStr.append("?errorMessage="+ URLEncoder.encode(errorCode+logId,"UTF-8"));
                if ("true".equals(SysConfig.getSysConfig("developMode"))){
                    request.getSession().setAttribute("exceptionJsonMsg",message+":"+detail);
                }else{
                    jspStr.append("&exceptionJsonMsg="+ URLEncoder.encode(defaultMessage,"UTF-8"));
                }
                response.setStatus(500);
            }
            request.getSession().getServletContext().getRequestDispatcher("/"+jspStr.toString()).forward(request, response);
//            response.sendRedirect(request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/" +jspStr.toString());
        	return null;
        }
    }

    private String handleException(final Throwable ex) {
        String msg = ex.getMessage();
        return msg;
    }

    /**
     * 获取 exception 的 详细信息
     *
     * @param e
     * @return
     */
    private Map<String, String> getExceptionTypeAndDetailMessage(final Throwable e, String logid) {
        String detail = "";
        String exceptionType = "";
        String message = "";
        StackTraceElement[] trace = e.getStackTrace();
        for (StackTraceElement s : trace) {
            detail += "\n\t " + s + "\r\n";
        }
        if (e instanceof PrcException) {
            exceptionType = "1";
            message = handleWithSpecialCharator(((PrcException) e).getMessage());
        } else if (e instanceof IllegalInputAppException) {
            exceptionType = "1";
            IllegalInputAppException ia = (IllegalInputAppException) e;
            message = handleWithSpecialCharator(ia.getMessage());
        }else if (e instanceof AppException) {
            exceptionType = "1";
            AppException ae = (AppException) e;
            message = handleWithSpecialCharator(ae.getMessage());
        }else {
            exceptionType = "2";
            message = handleWithSpecialCharator(e.getMessage());
        }
        if(!(e  instanceof AppException)) {
            message += " |[errorCode: " + errorCode + logid + "]"; //错误信息统一加上errorCode和logid
        }
        detail = message + handleWithSpecialCharator(detail);
        Map<String, String> result = new HashMap<String, String>();
        result.put("exceptionType", exceptionType);
        result.put("formateMessage", "{\"success\":\"false\",\"msg\":\"" + message + "\"" + ",\"errorDetail\":\"" + detail + "\"}");
        result.put("exceptionStack", detail);
        result.put("message",message);
        return result;
    }

    private String handleWithSpecialCharator(String string) {
        if (string != null) {
            string = org.springframework.util.StringUtils.replace(string, "\\", "");
            string = org.springframework.util.StringUtils.replace(string, "\r", "");
            string = org.springframework.util.StringUtils.replace(string, "\t", "");
            string = org.springframework.util.StringUtils.replace(string, "\b", "");
            string = org.springframework.util.StringUtils.replace(string, "\f", "");
            string = org.springframework.util.StringUtils.replace(string, "\n", "");
            string = org.springframework.util.StringUtils.replace(string, "\"", "");
        }
        return string;
    }
}

