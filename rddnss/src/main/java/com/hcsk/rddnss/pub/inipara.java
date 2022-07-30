package com.hcsk.rddnss.pub;

import org.springframework.stereotype.Component;

@Component
public class inipara {

    /*//是否可以显示账号信息
    static public boolean canshowaccount=pubfuncs.getparafromprofile("canshowaccount").equals("1");

    //运行环境，暂时不用
    //static public String runtype="release";//真实环境
    //static public String runtype="debug";//编译环境

    //操作系统
    static public String osname= pubfuncs.getosname();//linux演化来的直接返回“linux”，其他操作系统直接返回系统名称

    //tomcat临时目录，此配置在打包成jar或war包时要用，运行时加入参数里，
    // 如：java -Djava.io.tmpdir=D:/ProgramFiles/springboot/tomcattempdir -jar itwebisb-1.0.0.war
    // 或java -Djava.io.tmpdir=/usr/local/springboot/tomcattempdir -jar itwebisb-1.0.0.war
    //static public String tomcattempdir="D:/ProgramFiles/springboot/tomcattempdir";
    //static public String tomcattempdir=(osname.equals("linux"))?"/usr/local/springboot/tomcattempdir":"D:/ProgramFiles/springboot/tomcattempdir";

    //上传文件根目录
    static public String uploadrootpath=(osname.equals("linux"))?pubfuncs.getparafromprofile("uploadrootpathlinux"):pubfuncs.getparafromprofile("uploadrootpathwindows");

    //取得的文章列表语言的条件，0：获取所有语言的文章，1：只获取中文的文章，2：只获取英文的文章
    static public String galanguage=pubfuncs.getparafromprofile("galanguage");

    //网站默认语言，1：中文，2：英文
    static public String defaultlan=pubfuncs.getparafromprofile("defaultlan");*/

    //rediskey前缀，lujishu.net:lujishunet，itdk.net:itdknet，itweb.com:itwebcom
    public static String redisfontstr="rddnss";

    /*//当前端口，itweb.com:8788,lujishu.net:8688,itdk.net:8888
    public static String currentport=pubfuncs.getparafromprofile("server.port");

    //当前站点域名，itweb.com,lujishu.net,itdk.net
    public static String currentdomain=pubfuncs.getparafromprofile("currentdomain");

    //当前站点名称，itweb,Lujishu,ITDK
    public static String currentwebname=pubfuncs.getparafromprofile("currentwebname");

    //文章类型，0-所有，1-转载，2-原创，3-不确定
    public static int reprintdot=Integer.parseInt(pubfuncs.getparafromprofile("reprintdot"));

    //文件服务地址
    public static String fileserverurl=pubfuncs.getparafromprofile("fileserverurl");

    //首页单次启动访问计数
    public static long indexvisiti=0;
    //文章浏览单次启动访问计数
    public static long articleviewvisiti=0;*/

}
