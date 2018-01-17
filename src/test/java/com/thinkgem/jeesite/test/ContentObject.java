package com.thinkgem.jeesite.test;

import java.util.List;

public class ContentObject {
    public String Version;
    public List<String> Drivers;
    public boolean ResetEnable;
    public int ResetCount;
    public String JavaVMName;
    public String JavaVersion;
    public String JavaClassPath;
    public String StartTime;

    public String getVersion() {
        return Version;
    }

    public void setVersion(String version) {
        Version = version;
    }

    public List<String> getDrivers() {
        return Drivers;
    }

    public void setDrivers(List<String> drivers) {
        Drivers = drivers;
    }

    public boolean isResetEnable() {
        return ResetEnable;
    }

    public void setResetEnable(boolean resetEnable) {
        ResetEnable = resetEnable;
    }

    public int getResetCount() {
        return ResetCount;
    }

    public void setResetCount(int resetCount) {
        ResetCount = resetCount;
    }

    public String getJavaVMName() {
        return JavaVMName;
    }

    public void setJavaVMName(String javaVMName) {
        JavaVMName = javaVMName;
    }

    public String getJavaVersion() {
        return JavaVersion;
    }

    public void setJavaVersion(String javaVersion) {
        JavaVersion = javaVersion;
    }

    public String getJavaClassPath() {
        return JavaClassPath;
    }

    public void setJavaClassPath(String javaClassPath) {
        JavaClassPath = javaClassPath;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

}
