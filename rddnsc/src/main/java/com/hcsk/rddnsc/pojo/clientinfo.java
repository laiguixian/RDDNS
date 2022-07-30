/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: clientinfo
 * Author:   Administrator
 * Date:     2019/6/28 10:15
 * Description: 描述客户端信息的数据结构
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.hcsk.rddnsc.pojo;

import java.util.Date;

/**
 * 〈一句话功能简述〉<br> 
 * 〈描述客户端信息的数据结构〉
 *
 * @author Administrator
 * @create 2019/6/28
 * @since 1.0.0
 */
public class clientinfo {
    public String tomat;//站点名称
    public String clientname;//客户端名称
    public String ipadress;//客户端ip
    public Date lastcon;//最后一次通信时间
    public int timeout;//超时设置，当前时间减去最后一次通信时间超过这个时间（单位：秒）自动剔除
    public String port;//客户端对应端口
    public String nginxp;//nginx参数

    public String getTomat() {
        return tomat;
    }

    public void setTomat(String tomat) {
        this.tomat = tomat;
    }

    public String getClientname() {
        return clientname;
    }

    public void setClientname(String clientname) {
        this.clientname = clientname;
    }

    public String getIpadress() {
        return ipadress;
    }

    public void setIpadress(String ipadress) {
        this.ipadress = ipadress;
    }

    public Date getLastcon() {
        return lastcon;
    }

    public void setLastcon(Date lastcon) {
        this.lastcon = lastcon;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getNginxp() {
        return nginxp;
    }

    public void setNginxp(String nginxp) {
        this.nginxp = nginxp;
    }
}
