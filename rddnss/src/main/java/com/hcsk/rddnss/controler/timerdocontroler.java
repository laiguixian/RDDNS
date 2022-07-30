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
package com.hcsk.rddnss.controler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.hcsk.rddnss.pojo.clientinfo;
import com.hcsk.rddnss.pojo.machineinfo;
import com.hcsk.rddnss.pojo.rddnsset;
import com.hcsk.rddnss.pojo.webinfo;
import com.hcsk.rddnss.pub.RedisUtil;
import com.hcsk.rddnss.pub.htmlpar;
import com.hcsk.rddnss.pub.pubclass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.hcsk.rddnss.pub.pubclass.printerrormsg;

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
    @Autowired
    public RedisUtil rds;
    /**
     * 读取输出流数据
     *
     * @param p 进程
     * @return 从输出流中读取的数据
     * @throws IOException
     */
    public String getShellOut(Process p) throws IOException{

        StringBuilder sb = new StringBuilder();
        BufferedInputStream in = null;
        BufferedReader br = null;

        try {

            in = new BufferedInputStream(p.getInputStream());
            br = new BufferedReader(new InputStreamReader(in));
            String s;

            while ((s = br.readLine()) != null) {
                // 追加换行符
                //sb.append(ConstantUtil.LINE_SEPARATOR);

                sb.append(s);
            }

        } catch (IOException e) {
            throw e;
        } finally {
            br.close();
            in.close();
        }

        return sb.toString();
    }
    //确认服务是否开启
    public boolean checkserviceactive(String servicename){
        boolean resultvalue=false;
        try{
            Process p = Runtime.getRuntime().exec("service --status-all");
            //Process p = Runtime.getRuntime().exec("ifconfig");
            p.waitFor();
            String resstr = getShellOut(p);
            p.destroy();
            //printerrormsg("获取的服务列表："+resstr);
            if (resstr.indexOf(servicename) > -1) {
                //printerrormsg(servicename+"已经开启");
                resultvalue = true;
            }
            else {
                //printerrormsg(servicename+"没有开启");
                resultvalue = false;
            }
        } catch (Exception ioe) {
            printerrormsg("获取服务"+servicename+"状态时出错："+ioe.getMessage());
        }
        return resultvalue;
    }
    //启动服务
    public boolean startservice(String servicename){
        boolean resultvalue=false;
        if(checkserviceactive(servicename)) {//服务已经开启，此时进行重启
            try {
                printerrormsg("服务已经启动，此时重启服务");
                Process p = Runtime.getRuntime().exec("service "+servicename+" restart");
                p.waitFor();
                p.destroy();
            } catch (Exception ioe) {
                printerrormsg("重启服务" + servicename + "时出错：" + ioe.getMessage());
            }
        }else{//服务还没有开启，此时开启服务就可以了
            try {
                printerrormsg("服务还没启动，此时启动服务");
                Process p = Runtime.getRuntime().exec("service "+servicename+" start");
                p.waitFor();
                p.destroy();
            } catch (Exception ioe) {
                printerrormsg("启动服务" + servicename + "时出错：" + ioe.getMessage());
            }
        }
        if(checkserviceactive(servicename)) {//服务已经开启
            resultvalue = true;
        }else{//服务还没有开启
            resultvalue = false;
        }
        return resultvalue;
    }
    static boolean taskding = false;//定时任务是否正在执行
    @Scheduled(fixedRate=5000)//每隔5秒（5000毫秒）执行一次
    private void configureTasks() {
        if(taskding)//如果定时任务正在执行则直接跳过
            return;
        taskding=true;//将定时任务执行标志设为正在执行
        //printerrormsg("执行静态定时任务时间: " + new Date());
        //获取跟目录
        String projectpath="";
        try {
            File path = new File(ResourceUtils.getURL("classpath:").getPath());
            if (!path.exists()) path = new File("");
            projectpath=path.getAbsolutePath()+"/";
        }catch (Exception ee){
            printerrormsg(new Date()+"：获取项目根目录出错: " + ee.getMessage());
        }
        pubclass pcs = new pubclass();
        htmlpar hp= new htmlpar();
        //printerrormsg("项目根目录: " + projectpath);


        String milinfo="";
        int clientcount=0;//有效客户端数量
        List<machineinfo> mil=rscontroler.mnowlist;
        List<machineinfo> newmil=new ArrayList<machineinfo>();
        for(int i=0;i<mil.size();i++){
            machineinfo mitemp=mil.get(i);
            List<clientinfo> cil=mitemp.clientlist;
            List<clientinfo> newcil=new ArrayList<clientinfo>();
            String onemachinec="";
            for(int j=0;j<cil.size();j++){
                clientinfo citemp=cil.get(j);
                //printerrormsg("时间差："+(new Date().getTime()-(citemp.lastcon==null?new Date().getTime():citemp.lastcon.getTime()))+","+(citemp.timeout*1000));
                if(new Date().getTime()-(citemp.lastcon==null?new Date().getTime():citemp.lastcon.getTime())<=citemp.timeout*1000) {
                    //printerrormsg("累加客户端");
                    newcil.add(citemp);
                    onemachinec=onemachinec+",("+citemp.clientname+","+citemp.tomat+","+citemp.ipadress+","+citemp.port+","+citemp.nginxp+")";//+","+citemp.lastcon+","+citemp.timeout
                    clientcount = clientcount + 1;
                }
            }
            if(newcil.size()>0) {
                machineinfo newmitemp=mitemp;
                newmitemp.clientlist.clear();
                newmitemp.setClientlist(newcil);
                //printerrormsg("本机器客户端数量：" + newcil.size());

                if (new Date().getTime() - (newmitemp.lastcon == null ? new Date().getTime() : newmitemp.lastcon.getTime()) <= newmitemp.timeout * 1000) {
                    newmil.add(newmitemp);
                    milinfo = milinfo + ";(" + newmitemp.machinename + "," + newmitemp.mstatus + ",[" + onemachinec + "])";//+","+mitemp.lastcon
                }
            }
        }
        //printerrormsg("机器列表简略信息: " + milinfo);
        //printerrormsg("新机器列表数量: " + newmil.size());
        //printerrormsg("有效客户端数量: " + clientcount);
        //if(newmil.size()>0){//如果机器列表里面有值
            if(!milinfo.equals(rscontroler.mlastlist)){//如果机器列表简略信息发生变化
                //载入nginx模板
                File rddnsset=new File(projectpath+"rddnsset.txt");
                if(!rddnsset.exists())
                    pcs.resourcetofile("resource/rddnsset.txt",projectpath+"rddnsset.txt");
                String winfo=hp.readTxtFilebyencode(projectpath+"rddnsset.txt","UTF-8");
                //printerrormsg("配置信息: " + winfo);
                if(winfo.length()>0) {
                    //载入nginx模板
                    File nginxbasef=new File(projectpath+"nginx-base.conf");
                    if(!nginxbasef.exists())
                        pcs.resourcetofile("resource/nginx-base.conf",projectpath+"nginx-base.conf");
                    File nginxserverf=new File(projectpath+"nginx-server.conf");
                    if(!nginxserverf.exists())
                        pcs.resourcetofile("resource/nginx-server.conf",projectpath+"nginx-server.conf");
                    String nginxbasestr=hp.readTxtFilebyencode(projectpath+"nginx-base.conf","UTF-8");
                    //printerrormsg("获取的nginx-base内容："+nginxbasestr);
                    String nginxserverstr=hp.readTxtFilebyencode(projectpath+"nginx-server.conf","UTF-8");

                    //载入配置文件并转化成json
                    List<webinfo> wil=null;//站点信息
                    String desfilepath="";//配置目标文件
                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        rddnsset rds = mapper.readValue(winfo, rddnsset.class);
                        wil=rds.webinfo;
                        desfilepath=rds.desfile;
                    }catch (Exception ee){
                        printerrormsg(new Date()+"：解析配置文件出错："+ee.getMessage());
                    }

                    //最终的nginx.conf
                    String nginxconf=nginxbasestr;

                    //tomat字符串
                    String[] tomatlist=new String[wil.size()];
                    //各站点机器列表
                    List<List<String>> webclientlist=new ArrayList<List<String>>();
                    for(int i=0;i<wil.size();i++){
                        webclientlist.add(new ArrayList<String>());
                    }

                    //组装nginx-base
                    //遍历机器
                    for(int i=0;i<newmil.size();i++) {
                        machineinfo tempmi=newmil.get(i);
                        //写入新的ip地址
                        //根据机器名来替换
                        nginxconf=pcs.replaceAll(nginxconf, "<rdddns:machine:"+tempmi.machinename+">",tempmi.ipaddress);
                        //根据固定标记来替换
                        nginxconf=pcs.replaceAll(nginxconf, "<rdddns:machine:all>",tempmi.ipaddress);
                        //遍历客户端
                        List<clientinfo> tempcil=tempmi.clientlist;
                        for(int j=0;j<tempcil.size();j++){
                            clientinfo tempci=tempcil.get(j);
                            //遍历站点组装每个站点自己的tomat
                            for(int k=0;k<wil.size();k++){
                                webinfo tempwi=wil.get(k);
                                //printerrormsg("站点："+tempwi.servername);
                                if(tempwi.tomat.equals(tempci.tomat)){
                                    //printerrormsg("符合："+tempci.port);
                                    tomatlist[k]=(tomatlist[k]==null?"":tomatlist[k]+"\n")+"server "+tempci.ipadress+":"+tempci.port+(tempci.nginxp.length()>0?" "+tempci.nginxp:"")+";";
                                    //累加站点机器列表(只累加不为backup的机器)
                                    if(!tempci.nginxp.equals("backup")) {
                                        webclientlist.get(k).add(tempci.ipadress+":"+tempci.port);
                                    }
                                }
                            }
                        }
                    }
                    //遍历站点，组装最终的nginx.conf
                    String tomatliststr="";//组装的tomat字符串
                    String serverliststr="";//组装的server字符串
                    for(int i=0;i<wil.size();i++){//printerrormsg("生成列表");
                        webinfo tempwi=wil.get(i);
                        //将站点机器列表写入redis供其他系统调用
                        rds.set(tempwi.tomat+"hosts",new Gson().toJson(webclientlist.get(i)));
                        //累加tomat字符串
                        //printerrormsg("tomat字符串"+i+"="+tomatlist[i]);
                        if(tomatlist[i]!=null && tomatlist[i].length()>0) {
                            tomatliststr = tomatliststr + "upstream " + tempwi.tomat + " {\n" + tomatlist[i] + "}\n";
                            //按网站写入tomat
                            nginxconf = pcs.replaceAll(nginxconf, "<rdddns:tomatlist:" + tempwi.tomat + ">", tomatlist[i]);
                        }
                        //组装server字符串
                        //获取nginxserver模板
                        String nginxserver = nginxserverstr;
                        //替换端口
                        nginxserver = pcs.replaceAll(nginxserver, "<rdddns:webport>", tempwi.webport);
                        //替换servername
                        nginxserver = pcs.replaceAll(nginxserver, "<rdddns:servername>", tempwi.servername);
                        //替换tomat
                        nginxserver = pcs.replaceAll(nginxserver, "<rdddns:tomat>", tempwi.tomat);
                        //累加server字符串
                        serverliststr = serverliststr + nginxserver + "\n";
                    }

                    //组装nginx-base成最终的nginx
                    //if(tomatliststr.length()>0 && serverliststr.length()>0) {
                        //写入tomat
                        nginxconf = pcs.replaceAll(nginxconf, "<rdddns:tomatlist>", tomatliststr);
                        //写入server
                        nginxconf = pcs.replaceAll(nginxconf, "<rdddns:serverlist>", serverliststr);
                        //去除残余的rddns特定标签
                        nginxconf=pcs.removerddnstag(nginxconf);
                        //将最终的配置写入目标文件
                        hp.writeTxtFileutf8(nginxconf,desfilepath);
                        printerrormsg("配置文件改变了");
                        try {
                            printerrormsg("开始重载nginx");
                            //Runtime.getRuntime().exec("service nginx reload;");
                            if (startservice("nginx")) {
                                printerrormsg("nginx重启成功");
                            }else{
                                printerrormsg("nginx重启失败");
                            }
                        }catch (Exception exece){
                            printerrormsg("执行命令出错："+exece.getMessage());
                        }

                    //}
                }else{
                    printerrormsg("配置文件("+projectpath+"rddnsset.txt"+")不存在");
                }
                //写入机器列表简略信息新值
                rscontroler.mlastlist=milinfo;
                //写入
                if(new rscontroler().setmlistwrite()){//先上锁
                    //printerrormsg("独占机器列表变量并更新");
                    rscontroler.mnowlist=newmil;
                    rscontroler.mlistinwrite=false;
                }
            }
        //}

        taskding=false;//将定时任务执行标志设为执行完毕
    }
}
