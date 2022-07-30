package com.hcsk.rddnsc.pub;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

public class webdo {
	public String gethtmlfromurl(String inurl) {
		String returnvalue="";
		//构建一URL对象  
        try {
			URL url = new URL(inurl);
			//使用openStream得到一输入流并由此构造一个BufferedReader对象  
            //BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(),"UTF-8"));
			HttpURLConnection newcon=(HttpURLConnection) url.openConnection();
			newcon.setConnectTimeout(20000);//连接超时：2000毫秒
			newcon.setReadTimeout(1200000);//读取超时：:120000毫秒，即2分钟
			newcon.setRequestMethod("GET");
			BufferedReader in = new BufferedReader(new InputStreamReader(newcon.getInputStream(),"UTF-8"));
            while(!in.ready()){ 
            	//阻塞，等待一段时间，直到Stream准备完毕
            }
            String line="";
            //读取www资源  
            while ((line = in.readLine()) != null) {  
            	returnvalue=returnvalue+line+"\r\n";
            }  
            in.close();
            newcon.disconnect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println(new Date()+"状态发送出错："+e.getMessage());
		}
        
        return returnvalue;
	}
}
