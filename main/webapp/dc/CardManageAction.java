package ka.com.yinhai.common.action;

import com.yinhai.comm.service.WebServiceConnUtilService;
import com.yinhai.comm.util.xmlutils2;
import com.yinhai.sysframework.dto.ParamDTO;
import com.yinhai.sysframework.util.ValidateUtil;
import com.yinhai.webframework.BaseAction;

/**
 * 卡管理调用交易平台
 * @author luosy	
 * @data 2017年5月29日14:57:33
 */
public class CardManageAction extends BaseAction {
	
	private WebServiceConnUtilService webservice = (WebServiceConnUtilService) super.getService("webServiceConnUtil");
	
	public String toWebService() throws Exception{
		ParamDTO dto = getDto();
		String serviceFlag = dto.getAsString("serviceFlag"); // 认证标识
		if (ValidateUtil.isEmpty(serviceFlag)) {
			setData("code", "-1");	
			setData("message", "获取认证标识失败!");
			return JSON;
		}
		
		/* 获取参数 */
		String keyWord = dto.getAsString("aab301")+"|"+ // 发卡地
						 dto.getAsString("aac002")+"|"+ // 社会保障号（身份证）
				         dto.getAsString("aaz500")+"|"+ // 卡号
				         dto.getAsString("aaz501")+"|"+ // 卡识别码
				         dto.getAsString("aac003")+"|"+ // 姓名
				         dto.getAsString("aaz507")+"|"+ // 卡复位信息
				         dto.getAsString("sfbs")+"|"+   // 算法标识
				         dto.getAsString("mydz")+"|"+   // 密钥地址
				         dto.getAsString("sjs1")+"|"+   // 随机数：过程密钥分散因之
				         dto.getAsString("sjs2");       // 随机数：244：鉴别所需原始信息 246：命令明文数据;
		if (ValidateUtil.areEqual("246", serviceFlag)) { // 内部认证
			keyWord = dto.getAsString("keyword"); // 卡信息
			keyWord = keyWord.substring(0, keyWord.lastIndexOf('|'));
		}
		
		/* 拼接XML */
		String inputXml= ""+
		"<inputxml>"+
			"<keyword>"+keyWord+"</keyword>"+
			"<aab301>"+dto.getAsString("aab301")+"</aab301>"+ // 发卡地 
			"<typemode>"+serviceFlag+"</typemode>"+ // 认证标识
			"<aac002>"+dto.getAsString("aac002")+"</aac002>"+ // 社会保障号（身份证）
			"<aaz500>"+dto.getAsString("aaz500")+"</aaz500>"+ // 卡号
			"<aae013>"+dto.getAsString("aae013")+"</aae013>"+ // 备注
			"<BussinessType>"+dto.getAsString("BussinessType")+"</BussinessType>"+ // 业务类型
			"<zdbm>"+dto.getAsString("zdbm")+"</zdbm>"+ // 终端编码
		"</inputxml>";
		
		/* 调用交易平台 */
		String jybh = "C500"; // 交易编号
		String result = webservice.getC400Webservice(inputXml, jybh);
		xmlutils2 xmlutils = new xmlutils2();
		String code = xmlutils.getValueFromXML(result, "result.code");
		String message = xmlutils.getValueFromXML(result, "result.message");
		
		/* 结果处理 */
		if(ValidateUtil.areEqual("1", code)){ // 成功
			String inst = xmlutils.getValueFromXML(result, "result.output.inst");
			setData("inst", inst);
		}
		setData("code", code);	
		setData("message", message);
		return JSON;
	}
}
