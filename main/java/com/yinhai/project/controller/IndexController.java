package com.yinhai.project.controller;

import com.yinhai.core.app.ta3.web.controller.BaseController;
import com.yinhai.core.common.api.base.IConstants;
import com.yinhai.core.common.api.config.impl.SysConfig;
import com.yinhai.core.common.api.util.RSAUtils;
import com.yinhai.core.common.api.util.ValidateUtil;
import com.yinhai.modules.authority.api.vo.MenuTreeNode;
import com.yinhai.modules.authority.api.vo.MenuVo;
import com.yinhai.modules.codetable.api.util.CodeTableUtil;
import com.yinhai.modules.org.api.bpo.IUserBpo;
import com.yinhai.modules.org.api.password.PasswordEncrypter;
import com.yinhai.modules.org.api.vo.org.ResetPasswordParamVo;
import com.yinhai.modules.org.api.vo.user.UserVo;
import com.yinhai.modules.security.api.util.SecurityUtil;
import com.yinhai.modules.security.api.vo.UserAccountInfo;
import com.yinhai.modules.sysapp.domain.po.SignRecordPo;
import com.yinhai.ta3.sysapp.signrecord.service.SignRecordService;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);
    @Resource
    private SignRecordService signRecordService;

    @Resource
    private IUserBpo userBpo;

    @Resource
    private PasswordEncrypter passwordEncrypter;

    @RequestMapping("indexAction.do")
    public String index(HttpServletRequest request) {
        List<MenuVo> list = SecurityUtil.getCurrentUserPermissionMenus(request.getSession());
        if (ValidateUtil.isNotEmpty(list)) {
            MenuTreeNode treeNode = MenuTreeNode.createTreeWithFilter(list,true);
            List secondMenuList = treeNode.getChildNode();
            request.setAttribute("menuList", secondMenuList==null?new ArrayList():secondMenuList);
        } else {
            request.setAttribute("menuList", new ArrayList());
        }
        SignRecordPo srd = signRecordService.queryLastSign(getTaDto());
        request.getSession().setAttribute("signstate", srd == null ? "0" : srd.getSignstate());
        request.setAttribute("signstate", request.getSession().getAttribute("signstate"));
        return "index";
    }

    /**
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("indexAction!changePassword.do")
    public String changePasswordWidthCurrent(HttpServletRequest request) throws Exception {
        if (ValidateUtil.isEmpty(getTaDto().getAsString("oldPass"))) {
            return JSON;
        }
        if (!"1".equals(request.getParameter("indexChangePass"))) {
            if(SysConfig.getSysConfigToBoolean("useCheckCode", false)) {
                String checkCodeType = SysConfig.getSysConfig("checkCodeType", IConstants.CHECKCODE_TYPE_NUMBER);
                if (IConstants.CHECKCODE_TYPE_NUMBER.equals(checkCodeType)) {
                    String captcha = (String) request.getSession().getAttribute(IConstants.CHECKCODE_KEY);
                    request.getSession().removeAttribute(IConstants.CHECKCODE_KEY);//获取之后删除验证码
                    if (ValidateUtil.isEmpty(captcha)) {
                        setMessage("验证码错误", "error");
                        return JSON;
                    }
                    if (!captcha.equals(getTaDto().getAsString("checkCodePass"))) {
                        setMessage("验证码错误", "error");
                        return JSON;
                    }
                }
                //滑动验证码
                else if(IConstants.CHECKCODE_TYPE_SLIDE.equals(checkCodeType)){
                    Boolean slideCheck = (Boolean) request.getSession().getAttribute(IConstants.SLIDE_CHECKCODE_KEY_RESULT);
                    request.getSession().removeAttribute(IConstants.SLIDE_CHECKCODE_KEY_RESULT);//获取之后删除验证码
                    if (ValidateUtil.isEmpty(slideCheck)) {
                        setMessage("验证码错误", "error");
                        return JSON;
                    }
                    if(!slideCheck){
                        // 输入验证码错误
                        setMessage("验证码错误", "error");
                        return JSON;
                    }
                }
            }
        }
        String loginid = getTaDto().getAsString("loginId");
        UserVo vo = null;
        if (ValidateUtil.isEmpty(loginid)) {//表示已经登录
            Long userId = getTaDto().getUser().getUserid();
            vo = userBpo.getUserByUserId(userId);
        } else {//登录页修改密码
            vo = userBpo.getUserByLoginId(loginid);
        }
        //当缓存和数据库中没有查询出此人，表示没有此人
        if (vo == null) {
            setMessage("查无此人，请检查登录号是否正确", "error");
            return JSON;
        }
        if (ValidateUtil.isEmpty(vo)) {
            setMessage("不存在此用户", "error");
            return JSON;
        }
        loginid = vo.getLoginid();
        String oldPass = getTaDto().getAsString("oldPass");
        boolean rsa = SysConfig.getSysConfigToBoolean("passwordRSA", false);
        if (rsa) {
            oldPass = RSAUtils.descryptWidthEncode(oldPass);
        }
        if (!passwordEncrypter.encodePassword(oldPass, loginid).equals(vo.getPassword())) {
            setMessage("原始密码错误", "error");
            return JSON;
        }
        ResetPasswordParamVo paramVo = new ResetPasswordParamVo();
        paramVo.setFirstFlag("true");
        paramVo.setNewPassword(getTaDto().getAsString("newPass"));
        if (rsa) {
            paramVo.setNewPassword(RSAUtils.descryptWidthEncode(getTaDto().getAsString("newPass")));
        }
        List<Long> userIds = new ArrayList<>();
        userIds.add(vo.getUserid());
        paramVo.setUserIds(userIds);
        userBpo.resetPassword(paramVo, 1L, 1L);
        setMessage("修改密码成功！", "success");
        return JSON;
    }

    /**
     * 获取码表数据
     * add by zzb
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("indexAction!getCacheByCollection.do")
    public String getCacheByCollection(String collection, HttpServletRequest request){
        String orgid = null;
        UserAccountInfo user = SecurityUtil.getUserAccountInfo(request);
        if (user != null) {
            orgid = user.getOrgId();
        }
        String codeListJson = CodeTableUtil.getCodeListJson(collection, orgid);
        setData("codeListJson", codeListJson);
        return JSON;
    }

    /**
     * 切换当前使用菜单的tab页面
     * （切换tab页面时,切换当前tab页的id）
     */
    @RequestMapping("commonAction.do")
    public String commonAction() {
        return JSON;
    }

    /**
     * 框架模拟登录接口，框架默认不提供模拟登录接口，如需使用需要在security.properties的中配置不登录可访问此模拟登录接口url
     * @param username 登录用户名
     * @param password 登录用户密码
     * @param request
     * @return
     *
     * 使用示例：http://localhost:8080/ta3/mockLogin.do?username=developer&password=1
     */
    @ResponseBody
    @RequestMapping("/mockLogin.do")
    public Result mockLogin(String username, String password, HttpServletRequest request,HttpServletResponse response){
        if(ValidateUtil.isNotEmpty(username) && ValidateUtil.isNotEmpty(password)){
            UserVo userVo = userBpo.getUserByLoginId(username);
            if(userVo == null){
                return new Result("0","用户名不存在");
            }else{
                if (!passwordEncrypter.encodePassword(password, username).equals(userVo.getPassword().toString())) {
                    return new Result("0","密码错误");
                }else{
                    return mockLoginWithHttpClient(username,password,request,response);
                }
            }
        }else{
            return new Result("0","登录账户或密码为空");
        }
    }

    /**
     * 模拟登录HttpClient请求
     * @param username
     * @param password
     * @param request
     * @return
     */
    private Result mockLoginWithHttpClient(String username, String password, HttpServletRequest request, HttpServletResponse response){
        try{
            HttpClient httpClient = HttpClientBuilder.create().build();
            String url = request.getRequestURL().toString();
            String contextPath = request.getContextPath();
            HttpPost httpPost = new HttpPost(url.substring(0,url.indexOf(contextPath)+contextPath.length())+"/"+"j_spring_security_check");
            List<NameValuePair> list = new ArrayList<>();
            list.add(new BasicNameValuePair("username",username));
            list.add(new BasicNameValuePair("password",password));
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, Charset.forName("UTF-8"));
            httpPost.setEntity(entity);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            for(Header header: httpResponse.getAllHeaders()){
                response.setHeader(header.getName(),header.getValue());
            }
            if(httpResponse != null){
                return new Result("1","模拟登录成功");
            }
        }catch(Exception ex){
            LOGGER.error(ex.getMessage(),ex);
            return new Result("0",ex.getMessage());
        }
        return null;
    }

    /**
     * 模拟退出
     * @param sessionIdName  当前应用的SessionID的名称，默认为JSESSIONID
     * @param sessionIdValue 需要退出的sessionId
     * @param path Cookie Path
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("mockLogout.do")
    public Result mockLogout(String sessionIdName,String sessionIdValue,String path,HttpServletRequest request){
        if(ValidateUtil.isNotEmpty(sessionIdName) && ValidateUtil.isNotEmpty(sessionIdValue) && ValidateUtil.isNotEmpty(path)){
            try{
                HttpClient httpClient = HttpClientBuilder.create().build();
                String url = request.getRequestURL().toString();
                String contextPath = request.getContextPath();
                HttpPost httpPost = new HttpPost(url.substring(0,url.indexOf(contextPath)+contextPath.length())+"/"+"logout");
                httpPost.setHeader("Cookie",sessionIdName+"="+sessionIdValue);
                HttpResponse response = httpClient.execute(httpPost);
                if(response != null){
                    return new Result("1",sessionIdValue+" 退出成功");
                }
            }catch(Exception ex){
                LOGGER.error(ex.getMessage(),ex);
                return new Result("0",ex.getMessage());
            }
        }
        return new Result("0","参数为空");
    }


    /**
     * 用于模拟登录的返回的结果类
     */
    class Result{
        private String code;
        private String data;

        public Result(String code, String data) {
            this.code = code;
            this.data = data;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }
}
