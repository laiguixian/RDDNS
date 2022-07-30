package com.hcsk.rddnss.pub;

import ch.qos.logback.core.joran.conditional.ElseAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URLDecoder;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;


//import org.jboss.weld.context.ApplicationContext;
@Component
public class pubclass {
    //是否可以显示账号信息
	public boolean canshowaccount=true;
	//取得的文章列表语言
	//public String galanguage="0";//不限语言，即所有的都查询
	public String galanguage="1";//中文，当前lujishu.net用这个
	//public String galanguage="2";//英文，当前itdk.net用这个 
	public int runenvironment=0;//测试运行环境
	//public int runenvironment=1;//实际运行环境

	//打印错误信息
	public static void printerrormsg(String inmsg){
		Logger logger = LoggerFactory.getLogger(pubclass.class);
		logger.debug(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+":"+inmsg);
	}
	public String getosname() {//获取操作系统
		//获取操作系统系统信息
		Properties prop = System.getProperties();
		String osname = prop.getProperty("os.name");
		//printerrormsg("操作系统："+osname);
		if (osname != null && osname.toLowerCase().indexOf("linux") > -1) {
			return "linux";
		}else
			return osname;
	}
	
	public String getapppath() {// 获取项目目录
		
		String path = "";
		try {
			path = URLDecoder.decode(this.getClass().getResource("/").getPath(), "gb2312");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 获取class存放目录
		path = path.toUpperCase();
		if (path.substring(0, 1).equals("/"))
			path = path.substring(1);
		path = path.substring(0, path.lastIndexOf("CLASSES") - 1);
		path = path.substring(0, path.lastIndexOf("WEB-INF") - 1);
		/*if (path.substring(path.length() - 1).equals("/") == false) {
			path = path + "/";
		}*/
		if(getosname().equals("linux"))
			path="/"+path.toLowerCase()+"/";
		else
			path=path.toLowerCase()+"\\";
		//printerrormsg("项目路径："+path);
		return path;
	}

	public String getstringfromresource(String resourcepath){
		String resultstr="";
		ClassPathResource resource = new ClassPathResource(resourcepath);
		try {
			InputStream inputStream= resource.getInputStream();
			byte[] bytes = new byte[0];
			bytes = new byte[inputStream.available()];
			inputStream.read(bytes);
			resultstr = new String(bytes);
			//printerrormsg("获取的内容："+resultstr);
		}catch (Exception e){
			printerrormsg("获取的内容出错："+e.getMessage());
		}
		return resultstr;
	}

	public boolean resourcetofile(String resourcepath,String filepath){
		ClassPathResource resource = new ClassPathResource(resourcepath);
		File file=new File(filepath);
		try {
			InputStream inputStream= resource.getInputStream();
			java.nio.file.Files.copy(
					inputStream,
				file.toPath(),
				StandardCopyOption.REPLACE_EXISTING);
		}catch (Exception e){
			printerrormsg("获取的内容出错："+e.getMessage());
		}
		if(file.exists())
			return true;
		else
			return false;
	}

	public String getprorootpath() {// 获取项目�?��的根目录
		String path = "";
		try {
			path = URLDecoder.decode(this.getClass().getResource("/").getPath(), "gb2312");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 获取class存放目录
		path = path.toUpperCase();
		if (path.substring(0, 1).equals("/"))
			path = path.substring(1);
		String returnpath = path.substring(0, path.indexOf("/")) + "\\";
		return returnpath;
	}

	public String getinipath() {// 获取tomcat的web目录
		String path = getapppath();
		String inipath = path.substring(0, path.indexOf("/WEBAPPS/")) + "/webapps/";
		return inipath;
	}

	/**
	 * 递归删除目录下的�?��文件及子目录下所有文�?
	 * 
	 * @param dir 将要删除的文件目�?
	 * @return boolean Returns "true" if all deletions were successful. If a
	 *         deletion fails, the method stops attempting to delete and returns
	 *         "false".
	 */
	public boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			// 递归删除目录中的子目录下
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		// 目录此时为空，可以删�?
		return dir.delete();
	}

	public boolean fileallowupload(String upfilename) {
		String notallowext = ",exe,dll,jsp,asp,php,java,js,xml,html,htm,shtml,perl,cgi,c,h,class,dhtml,xhtml,shtm,xhtm,";
		String upfileext = upfilename.substring(upfilename.lastIndexOf(".") + 1);
		if (notallowext.indexOf("," + upfileext + ",") > -1)
			return false;
		else
			return true;
	}

	public String toChinese(String str) { // 进行转码操作的方�?
		if (str == null)
			str = "";
		try {
			str = new String(str.getBytes("ISO-8859-1"), "gb2312");
		} catch (UnsupportedEncodingException e) {
			str = "";
			e.printStackTrace();
		}
		return str;
	}

	public String decodeUTF8(String str) { // 进行转码操作的方�?
		if (str == null)
			str = "";
		try {
			if (!(str.equals(""))) {
				str = str.trim();
			}
			if (str.equals(new String(str.getBytes("ISO-8859-1"), "ISO-8859-1"))) {
				str = new String(str.getBytes("ISO-8859-1"), "utf-8");
			}
		} catch (UnsupportedEncodingException e) {
			str = "";
			e.printStackTrace();
		}
		return str;
	}

	// 将null转换为空，并去左右空�?
	public String toStr(String str) { // 将null转换�?"的方�?
		if (str == null)
			str = "";
		else
			str = str.trim();
		return str;
	}

	// 将Object类型的null转换为空，并去左右空�?
	public String toObjStr(Object str) { // 将null转换�?"的方�?
		String newstr = "";
		if (str == null)
			newstr = "";
		else
			newstr = str.toString().trim();
		return newstr;
	}

	public void deletefile(String filename) {
		File file = new File(filename);
		if (file.exists()) {
			file.delete();
		}
	}

	/**
	* 字符串替换，从头到尾查询一次，替换后的字符串不检查
	* @param str     源字符串
	* @param oldStr  目标字符串
	* @param newStr  替换字符串
	* @return        替换后的字符串
	*/
	public String replaceAll(String str, String oldStr, String newStr){
		int i = str.indexOf(oldStr);
		while(i != -1){
			str = str.substring(0, i) + newStr + str.substring(i + oldStr.length());
			i = str.indexOf(oldStr, i + newStr.length());
		}
		return str;
	}
	/**
	* 字符串替换，左边第一个。
	* @param str     源字符串
	* @param oldStr  目标字符串
	* @param newStr  替换字符串
	* @return        替换后的字符串
	*/
	public String replaceFirst(String str, String oldStr, String newStr){
		int i = str.indexOf(oldStr);
		if (i == -1)
			return str;
		str = str.substring(0, i) + newStr + str.substring(i + oldStr.length());
		return str;
	}


	public String readTxtFile(String filePath) {
		String readstr = "";
		try {
			String encoding = "gb2312";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					readstr = readstr + lineTxt;
				}
				read.close();
			} else {
				// printerrormsg("找不到指定的文件");
			}
		} catch (Exception e) {
			printerrormsg("读取文件内容出错:"+e.getMessage());
		}
		return readstr;
	}

	/**
	 * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址, 参考文章：
	 * http://developer.51cto.com/art/201111/305181.htm
	 * 
	 * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？
	 * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。
	 * 
	 * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130, 192.168.1.100
	 * 
	 * 用户真实IP为： 192.168.1.110
	 * 
	 * @param request
	 * @return
	 */
	public String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;

	}

	//获取正确的ip
	public static String getcreip(String inipstr) {
		if(inipstr.equals("0:0:0:0:0:0:0:1"))
			return "127.0.0.1";
		else if(inipstr.equals("127.0.0.1"))
			return "127.0.0.1";
		String returnvalue = "";
		int strlen = inipstr.length();
		String oneip="";
		for (int i = 0; i < strlen; i++) {
			String tempstr = inipstr.substring(i, i + 1);
			if ("0123456789.".indexOf(tempstr) > -1) {
				oneip = oneip + tempstr;
			}
			if((oneip.length()>0)&&((i==strlen-1)||("0123456789.".indexOf(tempstr) <0))){
				//System.out.println("地址："+oneip+","+oneip.split("[.]").length);
				if((oneip.split("[.]").length==4)&&(!oneip.equals("127.0.0.1"))//点号时特殊字符，必须用[]包起来
						&&(!oneip.startsWith("192.168"))&&(!oneip.startsWith("10."))
						&&(!oneip.startsWith("172."))) {//符合ipv4的条件，且不为局域网ip
					return oneip;
				}else{
					oneip="";
				}
			}
		}
		printerrormsg("没有找到ip地址："+inipstr);
		return "127.0.0.1";//如果没有找到ip地址则返回本地地址
	}

	//是否正确的手机号，直接根据运营商已经开放的手机号段来判断
	public boolean isvalidcell(String cellphone) {
		if((cellphone.length()==11)) {
			if(
				//移动号段
				cellphone.startsWith("134") || cellphone.startsWith("135") || cellphone.startsWith("136") 
				 || cellphone.startsWith("137") || cellphone.startsWith("138") || cellphone.startsWith("139")
				 || cellphone.startsWith("147") || cellphone.startsWith("148") || cellphone.startsWith("150")
				 || cellphone.startsWith("151") || cellphone.startsWith("152") || cellphone.startsWith("157")
				 || cellphone.startsWith("158") || cellphone.startsWith("159") || cellphone.startsWith("165")
				 || cellphone.startsWith("172") || cellphone.startsWith("178") || cellphone.startsWith("182")
				 || cellphone.startsWith("183") || cellphone.startsWith("184") || cellphone.startsWith("187")
				 || cellphone.startsWith("188") || cellphone.startsWith("198")
				//联通号段
				 || cellphone.startsWith("130") || cellphone.startsWith("131") || cellphone.startsWith("132")
				 || cellphone.startsWith("145") || cellphone.startsWith("146") || cellphone.startsWith("155")
				 || cellphone.startsWith("156") || cellphone.startsWith("166") || cellphone.startsWith("171")
				 || cellphone.startsWith("175") || cellphone.startsWith("176") || cellphone.startsWith("185")
				 || cellphone.startsWith("186")
				//电信号段
				 || cellphone.startsWith("133") || cellphone.startsWith("149") || cellphone.startsWith("153")
				 || cellphone.startsWith("173") || cellphone.startsWith("174") || cellphone.startsWith("177")
				 || cellphone.startsWith("180") || cellphone.startsWith("181") || cellphone.startsWith("189")
				 || cellphone.startsWith("199")) {
				return true;
			}else {
				return false;
			}
		}else {
			return false;
		}
	}
	//简单的换位加密（确切的说算不上加密，只能说混淆吧）
	public String getnewstr(String instr) {
		String returnvalue="";
		for(int i = 0;i<instr.length();i++) {
		  String tempstr=instr.substring(i, i+1);
		  if (tempstr.equals("0"))
			  returnvalue=returnvalue+"5";
		  else if (tempstr.equals("1"))
			  returnvalue=returnvalue+"7";
		  else if (tempstr.equals("2"))
			  returnvalue=returnvalue+"1";
		  else if (tempstr.equals("3"))
			  returnvalue=returnvalue+"6";
		  else if (tempstr.equals("4"))
			  returnvalue=returnvalue+"2";
		  else if (tempstr.equals("5"))
			  returnvalue=returnvalue+"3";
		  else if (tempstr.equals("6"))
			  returnvalue=returnvalue+"9";
		  else if (tempstr.equals("7"))
			  returnvalue=returnvalue+"4";
		  else if (tempstr.equals("8"))
			  returnvalue=returnvalue+"0";
		  else if (tempstr.equals("9"))
			  returnvalue=returnvalue+"8";
		}
		return returnvalue;
	}

	//判断传入的是否是IP地址
	public boolean isip(String instr) {
		int len=instr.length();
		for(int i=0;i<len;i++)
			if("0123456789.".indexOf(instr.substring(i, i+1))<0)
				return false;
		return true;
	}
	//从传入的request获取顶级域名
	/*public String gettopdomainfq(HttpServletRequest request) {
		StringBuffer inurl = request.getRequestURL();
		String indomain = inurl.delete(inurl.length() - request.getRequestURI().length(), inurl.length()).toString();
		indomain=indomain.substring(indomain.indexOf("://")+3);
		if(isip(indomain))
			//return indomain;
			return "lujishu.net";
		else {
			//将获取的域名用点隔开生成字符串数组，前面加两个反斜杠是指不转义，直接就只是点
			String[] indomainarr=indomain.split("\\.");
			int indomainarrlen=indomainarr.length;
			String topdomain="";
			if(indomainarr.length>1) {
				topdomain=indomainarr[indomainarrlen-2]+"."+indomainarr[indomainarrlen-1];
			}else {
				topdomain=indomain;
			}
			return topdomain;
		}
	}*/
	//是否显示跟账号有关的元素，因为在中国对这种很敏感，如果在类似中国这样的地方设置为false就不会显示了
	/*public boolean showaccount(String topdomain) {
		//if(topdomain.equals("itdk.net"))
			return true;
		else if(topdomain.equals("lujishu.net"))
			return false;
		else 
			return false;
	}*/
	//判断输入的字符串是否全部由数字组成
	public boolean isallnum(String instr) {
		boolean resultvalue=true;
		int instrlen=instr.length()-1;
		for(int i=0;i<instrlen;i++) {
			if("0123456789".indexOf(instr.substring(i, i+1))<0) {
				resultvalue=false;
				break;
			}
		}
		return resultvalue;
	}
	//从传入的request获取末尾数字（比如文章识别码，用户识别码等等）
	public String getlastnumfq(HttpServletRequest request) {
		StringBuffer inurl = request.getRequestURL();
		String returnvalue=inurl.substring(inurl.lastIndexOf("/")+1);
		if(isallnum(returnvalue))
			return returnvalue;
		else
			return "";
	}
	/**
     * 截取字符串str中指定字符 strStart、strEnd之间的字符串
     * 
     * @param str
     * @param strStart
     * @param strEnd
     * @return
     */
    public String subString(String str, String strStart, String strEnd) {
    	String returnvalue="";
        /* 找出指定的2个字符在 该字符串里面的 位置 */
        int strStartIndex = str.indexOf(strStart);
        int strEndIndex = str.indexOf(strEnd);
 
        /* index 为负数 即表示该字符串中 没有该字符 */
        if (strStartIndex > -1 && strEndIndex > -1) {
        	returnvalue = str.substring(strStartIndex, strEndIndex).substring(strStart.length());
        }

        return returnvalue;
    }

    /*
    去除残余的rddns特定标签
     */
	public String removerddnstag(String instr){
		String newinstr=instr;
		String rddnstagsfontstr="<rdddns:";
		while (newinstr.indexOf(rddnstagsfontstr)>-1){
			String backhalfinstr=newinstr.substring(newinstr.indexOf(rddnstagsfontstr)+1);
			newinstr=newinstr.substring(0,newinstr.indexOf(rddnstagsfontstr))+backhalfinstr.substring(backhalfinstr.indexOf(">")+1);
		}
		return newinstr;
	}
	public static void main(String[] args) {
		System.out.println("获得的ip地址："+getcreip("125.77.90.117"));
	}
}
