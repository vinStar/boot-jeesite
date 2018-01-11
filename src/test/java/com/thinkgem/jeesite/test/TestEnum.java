package com.thinkgem.jeesite.test;

import com.thinkgem.jeesite.test.CommonEnum.*;

public class TestEnum {
	public void test() {
		// 赋值
		EnumTest test = EnumTest.SUN;
		// index
		System.out.println(test.ordinal());
		// 属性值
		System.out.println(DataSourceType.Master.getName());
		// name
		System.out.println(DataSourceType.Master.toString());
		// getValue
		System.out.println(DataSourceType.Master.toString());

		for (EnumTest e : EnumTest.values()) {
			System.out.println(e.toString());
		}

		//
        DataNum num = DataNum.A;
        System.out.println(num);
        Integer value = num.getValue();
        System.out.println(value);
        //
        FileType type = FileType.VIDEO;
        FileType typeByValue = type.getByValue(1);
        System.out.println(typeByValue);



    }

	public static void main(String[] args) {
		TestEnum testEnum = new TestEnum();
		testEnum.test();
	}
}
