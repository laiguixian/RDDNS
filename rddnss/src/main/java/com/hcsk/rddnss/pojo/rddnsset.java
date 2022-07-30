/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: rddnsset
 * Author:   Administrator
 * Date:     2019/7/1 9:10
 * Description: 配置文件数据结构
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.hcsk.rddnss.pojo;

import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈配置文件数据结构〉
 *
 * @author Administrator
 * @create 2019/7/1
 * @since 1.0.0
 */
public class rddnsset {
    public String version;//配置文件版本
    public String desfile;//目标文件
    public List<webinfo> webinfo;//站点信息

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<com.hcsk.rddnss.pojo.webinfo> getWebinfo() {
        return webinfo;
    }

    public void setWebinfo(List<com.hcsk.rddnss.pojo.webinfo> webinfo) {
        this.webinfo = webinfo;
    }

    public String getDesfile() {
        return desfile;
    }

    public void setDesfile(String desfile) {
        this.desfile = desfile;
    }
}
