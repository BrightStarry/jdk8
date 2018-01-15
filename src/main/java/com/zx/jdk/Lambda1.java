package com.zx.jdk;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * author:ZhengXing
 * datetime:2018/1/15 0015 11:10
 * Lambda表达式
 */
public class Lambda1 {
	/**
	 * jdk8 list sort
	 * 其中的(o1, o2) -> o1 - o2 表示一个匿名函数,o1和o2是其两个参数, -> 后面的是函数体.
	 */
	private static void jdk8ListSort() {

		List<Integer> list = Arrays.asList(334, 545, 342, 4, 345, 45446, 34345, 34, 545);
//		list.sort((o1, o2) ->{ return o1 - o2;});
		//当代码只有一行时,可省略{},和return
		list.sort((o1, o2) -> o1 - o2);
		//当然,对于一些常用类型(int,long,double)的比较,已经有定义好的实现.
//		list.sort(Comparator.comparingInt(o -> o));
		System.out.println(list);
	}

	/**
	 * old list sort
	 */
	private static void oldListSort() {

		List<Integer> list = Arrays.asList(334, 545, 342, 4, 345, 45446, 34345, 34, 545);
		Collections.sort(list, new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return o1 - o2;
			}
		});
		System.out.println(list);
	}
}

/**
 * 自定义接口,以使用Lambda表达式实现它
 */
//该注解在编译时限制该接口必须有且只有一个抽象方法
@FunctionalInterface
interface CustomLambdaInterface{
	String test(String a, String b);
	//不能有一个以上的抽象方法
//	void test2(String a);
	default void test3(){
		//有默认实现方法是可以通过编译的
	}
}

class CustomLambdaInterfaceTest {
	/**
	 * 使用lambda创建 匿名内部类
	 */
	public static void main(String[] args) {
		new CustomLambdaInterfaceTest((a,b) -> a + b)
				.test("123","456");
	}

	private CustomLambdaInterface customLambdaInterface;
	public CustomLambdaInterfaceTest(CustomLambdaInterface customLambdaInterface) {
		this.customLambdaInterface = customLambdaInterface;
	}
	void test(String a, String b) {
		System.out.println(customLambdaInterface.test(a,b));
	}
}