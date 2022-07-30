/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: timerdocontroler
 * Author:   Administrator
 * Date:     2019/6/28 14:32
 * Description: 定时任务
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.hcsk.rddnsc.controler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcsk.rddnsc.pojo.clientinfo;
import com.hcsk.rddnsc.pojo.machineinfo;
/*import com.hcsk.rddnss.pojo.rddnsset;
import com.hcsk.rddnss.pojo.webinfo;*/
import com.hcsk.rddnsc.pojo.responseinfo;
import com.hcsk.rddnsc.pub.htmlpar;
import com.hcsk.rddnsc.pub.pubclass;
import com.hcsk.rddnsc.pub.webdo;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈定时任务〉
 *
 * @author Administrator
 * @create 2019/6/28
 * @since 1.0.0
 */
@Component
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class timerdocontroler {
    static boolean taskding = false;//定时任务是否正在执行
    static String lastip="";
    @Scheduled(fixedRate=5000)//每隔5秒（5000毫秒）执行一次
    private void configureTasks() {
        if(taskding)//如果定时任务正在执行则直接跳过
            return;
        taskding=true;//将定时任务执行标志设为正在执行
        //System.out.println("执行静态定时任务时间: " + new Date());
        //获取跟目录
        String projectpath="";
        try {
            File path = new File(ResourceUtils.getURL("classpath:").getPath());
            if (!path.exists()) path = new File("");
            projectpath=path.getAbsolutePath()+"/";
        }catch (Exception ee){
            System.out.println("获取项目根目录出错: " + ee.getMessage());
        }
        pubclass pcs = new pubclass();
        htmlpar hp= new htmlpar();
        //System.out.println("项目根目录: " + projectpath);

        //载入本地设置
        File machineinfof=new File(projectpath+"machineinfo.txt");
        if(!machineinfof.exists())
            pcs.resourcetofile("resource/machineinfo.txt",projectpath+"machineinfo.txt");
        String machineinfostr=hp.readTxtFilebyencode(projectpath+"machineinfo.txt","UTF-8");
        machineinfostr=machineinfostr.substring(machineinfostr.indexOf("{"));
        //将字符串转化成json
        ObjectMapper mapper = new ObjectMapper();
        machineinfo mi =null;
        try {
            mi = mapper.readValue(machineinfostr, machineinfo.class);
        }catch (Exception ee){
            System.out.println(new Date()+"：解析机器信息出错："+ee.getMessage()+",,,"+machineinfostr);
        }
        String machineinfostrhttpen="";
        try {
            machineinfostrhttpen=URLEncoder.encode(machineinfostr,"UTF-8");
        }catch (Exception ee){
            System.out.println(new Date()+"：http加密出错："+ee.getMessage()+",,,"+machineinfostr);
        }
        if(mi != null && machineinfostrhttpen.length()>0) {
            String backmsg=new webdo().gethtmlfromurl("http://"+mi.serveraddr+":"+mi.serverport+"/clientinfo?minfo="+machineinfostrhttpen ).trim();
            responseinfo ri=null;
            try{
                 ri=mapper.readValue(backmsg, responseinfo.class);
            }catch (Exception ee){
                System.out.println(new Date()+"：解析返回结果出错："+ee.getMessage()+",,,"+ri);
            }
            if(ri!=null){
                if(!ri.infomessage.equals("处理成功")) {
                    System.out.println(new Date() + "：上传信息出错：" + ri.infomessage);
                }else {
                    String nowip=ri.ipadress;
                    if(!lastip.equals(nowip)){
                        System.out.println(new Date()+"：IP改变了，改变前："+lastip+"，改变后："+nowip);
                        lastip=nowip;
                    }
                }
            }
        }
        taskding=false;//将定时任务执行标志设为执行完毕
    }
}
