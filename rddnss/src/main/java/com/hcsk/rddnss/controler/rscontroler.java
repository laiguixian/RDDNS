/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: rscontroler
 * Author:   Administrator
 * Date:     2019/6/28 9:19
 * Description: 主要用于http服务
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.hcsk.rddnss.controler;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.hcsk.rddnss.pojo.clientinfo;
import com.hcsk.rddnss.pojo.machineinfo;
import com.hcsk.rddnss.pojo.responseinfo;
import com.hcsk.rddnss.pub.pubclass;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.hcsk.rddnss.pub.pubclass.printerrormsg;

/**
 * 〈一句话功能简述〉<br> 
 * 〈主要用于http服务〉
 *
 * @author Administrator
 * @create 2019/6/28
 * @since 1.0.0
 */
@RestController
public class rscontroler {
    static List<machineinfo> mnowlist=new ArrayList<machineinfo>();//此刻的机器列表
    static String mlastlist="";//上一次的机器列表简略信息，主要用于判断nginx有用信息是否发生变化
    static boolean mlistinwrite=false;//当前机器列表是否正处于写的状态，默认为否

    //将机器列表置于写的状态
    public boolean setmlistwrite(){
        while (mlistinwrite){

        }
        mlistinwrite=true;
        return true;
    }

    @RequestMapping(value="/clientinfo")
    //有时间加上校验，@RequestParam("ctocken") String ctocken,
    public responseinfo submitminfo(@RequestParam("minfo") String minfo, HttpServletRequest request){

        responseinfo resultrs=new responseinfo();
        if(minfo.length()<=0) {
            resultrs.setInfocode("1998");
            resultrs.setInfomessage("没有参数");
            return resultrs;
        }
        List<machineinfo> mlist=mnowlist;
        pubclass pcs = new pubclass();
        String nowip=pcs.getcreip(pcs.getIpAddress(request));

        /*Json序列化
        ObjectMapper是JSON操作的核心，Jackson的所有JSON操作都是在ObjectMapper中实现。
        ObjectMapper有多个JSON序列化的方法，可以把JSON字符串保存File、OutputStream等不同的介质中。
        writeValue(File arg0, Object arg1)把arg1转成json序列，并保存到arg0文件中。
        writeValue(OutputStream arg0, Object arg1)把arg1转成json序列，并保存到arg0输出流中。
        writeValueAsBytes(Object arg0)把arg0转成json序列，并把结果输出成字节数组。
        writeValueAsString(Object arg0)把arg0转成json序列，并把结果输出成字符串。*/
        //将字符串转化成json
        ObjectMapper mapper = new ObjectMapper();
        try {
            machineinfo mi = mapper.readValue(minfo, machineinfo.class);
            if(mi.mstatus!=3){
                //List<machineinfo> tempmlist=new ArrayList<machineinfo>();
                //List<clientinfo> clist=mi.clientlist;
                //printerrormsg("解析的客户端列表："+clist);
                //for(int i=0;i<clist.size();i++){
                    //printerrormsg("解析的客户端列表："+clist.get(i).webname);
                    int mlistindex=-1;
                    for(int j=0;j<mlist.size();j++){
                        if(mi.machinename.equals(mlist.get(j).machinename)) {
                            mlistindex = j;
                            break;
                        }
                    }

                    machineinfo tempmi=null;
                    if(mlistindex>-1){//如果机器在列表中存在
                        //设置机器的值
                        tempmi=mlist.get(mlistindex);
                    }else{
                        tempmi=new machineinfo();
                    }
                    //printerrormsg("机器："+mi.machinename+","+mi.mstatus+","+mi.timeout);
                    tempmi.setMachinename(mi.machinename);
                    tempmi.setTimeout(mi.timeout);
                    tempmi.setMstatus(mi.mstatus);
                    tempmi.setLastcon(new Date());

                    if(tempmi.getIpaddress()==null || !tempmi.getIpaddress().equals(nowip)){
                        printerrormsg("机器："+tempmi.machinename+"的IP变了，变化前："+(tempmi.getIpaddress()==null?"无":tempmi.getIpaddress())+"，变化后："+nowip);
                    }
                    tempmi.setIpaddress(nowip);
                    List<clientinfo> tempcil= mi.clientlist;
                    //printerrormsg("客户端列表："+tempcil);
                    //printerrormsg("客户端列表："+tempcil.size());
                    for(int j=0;j<tempcil.size();j++){
                        int clistindex=-1;
                        if(tempmi.clientlist!=null) {
                            for (int k = 0; k < tempmi.clientlist.size(); k++) {
                                if ((tempcil.get(j).clientname).equals(tempmi.clientlist.get(k).clientname)) {
                                    clistindex = k;
                                    break;
                                }
                            }
                        }else{
                            tempmi.clientlist=new ArrayList<clientinfo>();
                        }
                        clientinfo tempci=null;
                        if(clistindex>-1){//如果客户端存在
                            //设置客户端的值
                            tempci=tempmi.clientlist.get(clistindex);
                        }else{
                            tempci=new clientinfo();
                        }
                        //printerrormsg("客户端值："+nowip+","+tempcil.get(j).port+","+tempcil.get(j).timeout+","+tempcil.get(j).tomat+","+tempcil.get(j).nginxp);
                        tempci.setIpadress(nowip);
                        tempci.setLastcon(new Date());
                        tempci.setPort(tempcil.get(j).port);
                        tempci.setTimeout(tempcil.get(j).timeout);
                        tempci.setTomat(tempcil.get(j).tomat);
                        tempci.setClientname(tempcil.get(j).clientname);
                        tempci.setNginxp(tempcil.get(j).nginxp);
                        //printerrormsg("客户端赋值完毕");
                        if(clistindex<0) {//
                            tempmi.clientlist.add(tempci);
                        }//printerrormsg("新增客户端");
                    }
                    if(mlistindex<0){//如果机器在列表中不存在
                        mlist.add(tempmi);
                    }else{
                        mlist.set(mlistindex,tempmi);
                    }
                    //tempmlist.add(tempmi);
                //}
                //写入列表信息
                if(setmlistwrite()){//先上锁
                    mnowlist=mlist;
                    //mnowlist=tempmlist;
                    mlistinwrite=false;
                }

            }
            resultrs.setInfocode("0001");
            resultrs.setIpadress(nowip);
            resultrs.setInfomessage("处理成功");
            return resultrs;
        }catch (Exception ee){
            printerrormsg("解析机器信息出错："+ee.getMessage()+",,,"+minfo);
        }
        resultrs.setInfocode("1999");
        resultrs.setInfomessage("出现错误");
        return resultrs;
    }
}
