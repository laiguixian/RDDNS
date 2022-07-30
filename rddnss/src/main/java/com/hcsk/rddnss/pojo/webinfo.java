/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: webinfo
 * Author:   Administrator
 * Date:     2019/7/1 8:58
 * Description: 站点信息数据结构
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.hcsk.rddnss.pojo;

/**
 * 〈一句话功能简述〉<br> 
 * 〈站点信息数据结构〉
 *
 * @author Administrator
 * @create 2019/7/1
 * @since 1.0.0
 */
public class webinfo {
    public String tomat;//站点名称，必须唯一
    public String servername;//对应nginx的server_name
    public String webport;//站点端口

    public String getTomat() {
        return tomat;
    }

    public void setTomat(String tomat) {
        this.tomat = tomat;
    }

    public String getServername() {
        return servername;
    }

    public void setServername(String servername) {
        this.servername = servername;
    }

    public String getWebport() {
        return webport;
    }

    public void setWebport(String webport) {
        this.webport = webport;
    }
}
