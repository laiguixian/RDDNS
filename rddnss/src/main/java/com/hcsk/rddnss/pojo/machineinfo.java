/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: machineinfo
 * Author:   Administrator
 * Date:     2019/6/28 11:27
 * Description: 用于描述机器（服务器）信息
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.hcsk.rddnss.pojo;

import java.util.Date;
import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈用于描述机器（服务器）信息〉
 *
 * @author Administrator
 * @create 2019/6/28
 * @since 1.0.0
 */
public class machineinfo {
    public String serveraddr;//服务端地址
    public String serverport;//服务端端口
    public String machinename;//机器名称，用于服务端识别机器
    public String ipaddress;//IP地址
    public int mstatus;//机器状态，1：所有客户端运行正常，2：部分客户端运行正常，3：所有客户端都运行不正常
    public Date lastcon;//最后通信时间
    public int timeout;//超时时间
    public List<clientinfo> clientlist;//客户端信息列表

    public String getServeraddr() {
        return serveraddr;
    }

    public void setServeraddr(String serveraddr) {
        this.serveraddr = serveraddr;
    }

    public String getServerport() {
        return serverport;
    }

    public void setServerport(String serverport) {
        this.serverport = serverport;
    }
    public String getMachinename() {
        return machinename;
    }

    public void setMachinename(String machinename) {
        this.machinename = machinename;
    }

    public int getMstatus() {
        return mstatus;
    }

    public void setMstatus(int mstatus) {
        this.mstatus = mstatus;
    }

    public Date getLastcon() {
        return lastcon;
    }

    public void setLastcon(Date lastcon) {
        this.lastcon = lastcon;
    }

    public List<clientinfo> getClientlist() {
        return clientlist;
    }

    public void setClientlist(List<clientinfo> clientlist) {
        this.clientlist = clientlist;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }
}
