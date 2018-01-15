package com.zx.jdk;

/**
 * author:ZhengXing
 * datetime:2018/1/15 0015 10:46
 * 有默认实现和静态方法的接口
 */
public interface DefaultMethodInterface {
	void test();

	//默认实现方法
	default  void defaultMethod() {
		System.out.println("this is default method");
	}
	//静态方法
	static void staticMethod() {
		System.out.println("this is static method");
	}
}

/**
 * 接口实现类
 */
class DefaultMethodInterfaceTest implements DefaultMethodInterface{
	@Override
	public void test() {
		// do nothing ....
	}

	/**
	 * 使用接口的默认实现
	 */
	public static void main(String[] args) {
		DefaultMethodInterfaceTest defaultMethodInterfaceTest = new DefaultMethodInterfaceTest();
		defaultMethodInterfaceTest.defaultMethod();
		DefaultMethodInterface.staticMethod();
	}
}
