package com.thinkgem.jeesite.test;

public class CommonEnum {

    public enum EnumTest {
        MON, TUE, WED, THU, FRI, SAT, SUN;
    }

    public enum DataNum {
        // A=65
        A(65),
        // B=66
        B(6);

        private DataNum(Integer value) {
            this.value = value;
        }

        private Integer value;

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }
    }

    public enum DataSourceType {

        // 主库
        Master("masterA"),

        // 从库
        Slave("slave");

        private DataSourceType(String name) {
            this.name = name;
        }

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public enum FileType {
        VIDEO(1, "视频");

        int value;
        String name;

        FileType(int value, String name) {
            this.value = value;
            this.name = name;
        }

        public int getValue() {
            return value;
        }

        public String getName() {
            return name;
        }

        public FileType getByValue(int value) {
            for (FileType fileType : FileType.values()) {
                if (fileType.value == value) {
                    return fileType;
                }
            }
            throw new IllegalArgumentException("No element matches " + value);
        }

    }

}
