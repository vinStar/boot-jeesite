package com.thinkgem.jeesite.common.utils;

/**
 * <p>Title: Get Path Util </p>
 * <p>Description: this class can getWebClassesPath,getWebInfPath and getWebRoot</p>
 *
 * @author <a style='color:red' href='http://blog.csdn.net/sigangjun'>sigangjun</a>
 * @version 1.0
 * @since 2013-8-12 下午4:09:00
 */
public class PathUtils {


    public String getWebClassesPath() {
        String path = getClass().getProtectionDomain().getCodeSource()
                .getLocation().getPath();
        path = trim(path);
        return path;

    }

    public String getWebInfPath() throws IllegalAccessException {
        String path = getWebClassesPath();
        if (path.indexOf("WEB-INF") > 0) {
            path = path.substring(0, path.indexOf("WEB-INF") + 8);
            path = trim(path);
        } else {
            throw new IllegalAccessException("路径获取错误");
        }
        return path;
    }

    public String getWebRoot() throws IllegalAccessException {
        String path = getWebClassesPath();
        if (path.indexOf("WEB-INF") > 0) {
            path = path.substring(0, path.indexOf("WEB-INF/classes"));
            path = trim(path);
        } else {
            throw new IllegalAccessException("路径获取错误");
        }
        return path;
    }

    private String trim(String s) {
        if (s.startsWith("/") || s.startsWith("\\")) {
            s = s.substring(1);
            trim(s);
        }
        return s;
    }

    ;

    public static void main(String[] args) throws Exception {
        PathUtils p = new PathUtils();
        System.out.println(p.getWebClassesPath());
        try {
            //Users/liutong/DevDemo/boot-jeesite/boot-jeesite-common/target/classes/
            System.out.println(p.getWebInfPath());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            System.out.println(p.getWebRoot());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        //项目服务器脚本文件路径
        ///Users/liutong/DevDemo/boot-jeesite
        System.out.println(System.getProperty("user.dir"));
        // 获取所有的类路径 包括jar包的路径
        ///Library/Java/JavaVirtualMachines/jdk1.8.0_131.jdk/Contents/Home/jre/lib/charsets.jar
        //:/Library/Java/JavaVirtualMachines/jdk1.8.0_131.jdk/Contents/Home/jre/lib/deploy.jar:
        // /Library/Java/JavaVirtualMachines/jdk1.8.0_131.jdk/Contents/Home/jre/lib/ext/cldrdata.jar:
        // /Library/Java/JavaVirtualMachines/jdk1.8.0_131.jdk/Contents/Home/jre/lib/ext/dnsns.jar:
        // /Library/Java/JavaVirtualMachines/jdk1.8.0_131.jdk/Contents/Home/jre/lib/ext/jaccess.jar:
        // /Library/Java/JavaVirtualMachines/jdk1.8.0_131.jdk/Contents/Home/jre/lib/ext/jfxrt.jar:
        // /Library/Java/JavaVirtualMachines/jdk1.8.0_131.jdk/Contents/Home/jre/lib/ext/localedata.jar:
        // /Library/Java/JavaVirtualMachines/jdk1.8.0_131.jdk/Contents/Home/jre/lib/ext/nashorn.jar:
        // /Library/Java/JavaVirtualMachines/jdk1.8.0_131.jdk/Contents/Home/jre/lib/ext/sunec.jar:
        // /Library/Java/JavaVirtualMachines/jdk1.8.0_131.jdk/Contents/Home/jre/lib/ext/sunjce_provider.jar:
        // /Library/Java/JavaVirtualMachines/jdk1.8.0_131.jdk/Contents/Home/jre/lib/ext/sunpkcs11.jar
        System.out.println(System.getProperty("java.class.path"));
    }
}
