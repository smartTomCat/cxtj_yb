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
                    request.getSession().removeAttribute(IConstants.CHECKCODE_KEY);//???????????????????????????
                    if (ValidateUtil.isEmpty(captcha)) {
                        setMessage("???????????????", "error");
                        return JSON;
                    }
                    if (!captcha.equals(getTaDto().getAsString("checkCodePass"))) {
                        setMessage("???????????????", "error");
                        return JSON;
                    }
                }
                //???????????????
                else if(IConstants.CHECKCODE_TYPE_SLIDE.equals(checkCodeType)){
                    Boolean slideCheck = (Boolean) request.getSession().getAttribute(IConstants.SLIDE_CHECKCODE_KEY_RESULT);
                    request.getSession().removeAttribute(IConstants.SLIDE_CHECKCODE_KEY_RESULT);//???????????????????????????
                    if (ValidateUtil.isEmpty(slideCheck)) {
                        setMessage("???????????????", "error");
                        return JSON;
                    }
                    if(!slideCheck){
                        // ?????????????????????
                        setMessage("???????????????", "error");
                        return JSON;
                    }
                }
            }
        }
        String loginid = getTaDto().getAsString("loginId");
        UserVo vo = null;
        if (ValidateUtil.isEmpty(loginid)) {//??????????????????
            Long userId = getTaDto().getUser().getUserid();
            vo = userBpo.getUserByUserId(userId);
        } else {//?????????????????????
            vo = userBpo.getUserByLoginId(loginid);
        }
        //??????????????????????????????????????????????????????????????????
        if (vo == null) {
            setMessage("?????????????????????????????????????????????", "error");
            return JSON;
        }
        if (ValidateUtil.isEmpty(vo)) {
            setMessage("??????????????????", "error");
            return JSON;
        }
        loginid = vo.getLoginid();
        String oldPass = getTaDto().getAsString("oldPass");
        boolean rsa = SysConfig.getSysConfigToBoolean("passwordRSA", false);
        if (rsa) {
            oldPass = RSAUtils.descryptWidthEncode(oldPass);
        }
        if (!passwordEncrypter.encodePassword(oldPass, loginid).equals(vo.getPassword())) {
            setMessage("??????????????????", "error");
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
        setMessage("?????????????????????", "success");
        return JSON;
    }

    /**
     * ??????????????????
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
     * ???????????????????????????tab??????
     * ?????????tab?????????,????????????tab??????id???
     */
    @RequestMapping("commonAction.do")
    public String commonAction() {
        return JSON;
    }

    /**
     * ??????????????????????????????????????????????????????????????????????????????????????????security.properties???????????????????????????????????????????????????url
     * @param username ???????????????
     * @param password ??????????????????
     * @param request
     * @return
     *
     * ???????????????http://localhost:8080/ta3/mockLogin.do?username=developer&password=1
     */
    @ResponseBody
    @RequestMapping("/mockLogin.do")
    public Result mockLogin(String username, String password, HttpServletRequest request,HttpServletResponse response){
        if(ValidateUtil.isNotEmpty(username) && ValidateUtil.isNotEmpty(password)){
            UserVo userVo = userBpo.getUserByLoginId(username);
            if(userVo == null){
                return new Result("0","??????????????????");
            }else{
                if (!passwordEncrypter.encodePassword(password, username).equals(userVo.getPassword().toString())) {
                    return new Result("0","????????????");
                }else{
                    return mockLoginWithHttpClient(username,password,request,response);
                }
            }
        }else{
            return new Result("0","???????????????????????????");
        }
    }

    /**
     * ????????????HttpClient??????
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
                return new Result("1","??????????????????");
            }
        }catch(Exception ex){
            LOGGER.error(ex.getMessage(),ex);
            return new Result("0",ex.getMessage());
        }
        return null;
    }

    /**
     * ????????????
     * @param sessionIdName  ???????????????SessionID?????????????????????JSESSIONID
     * @param sessionIdValue ???????????????sessionId
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
                    return new Result("1",sessionIdValue+" ????????????");
                }
            }catch(Exception ex){
                LOGGER.error(ex.getMessage(),ex);
                return new Result("0",ex.getMessage());
            }
        }
        return new Result("0","????????????");
    }


    /**
     * ???????????????????????????????????????
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
