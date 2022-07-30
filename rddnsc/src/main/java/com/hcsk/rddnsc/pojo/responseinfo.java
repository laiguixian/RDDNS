/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: responseinfo
 * Author:   Administrator
 * Date:     2019/6/28 10:52
 * Description: 用于定义返回信息
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.hcsk.rddnsc.pojo;

/**
 * 〈一句话功能简述〉<br> 
 * 〈用于定义返回信息〉
 *
 * @author Administrator
 * @create 2019/6/28
 * @since 1.0.0
 */
public class responseinfo {
    public String infocode="1001";
    public String infotype="machineinfo";
    public String infomessage="处理成功";
    public String ipadress="127.0.0.1";

    public String getInfocode() {
        return infocode;
    }

    public void setInfocode(String infocode) {
        this.infocode = infocode;
    }

    public String getInfomessage() {
        return infomessage;
    }

    public void setInfomessage(String infomessage) {
        this.infomessage = infomessage;
    }

    public String getInfotype() {
        return infotype;
    }

    public void setInfotype(String infotype) {
        this.infotype = infotype;
    }

    public String getIpadress() {
        return ipadress;
    }

    public void setIpadress(String ipadress) {
        this.ipadress = ipadress;
    }
}
