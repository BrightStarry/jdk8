package com.zx.jdk;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * author:ZhengXing
 * datetime:2018/1/15 0015 13:06
 */
public class Lambda2 {
	public static void main(String[] args) {
		String a = "xx";
		List<String> list = Arrays.asList("30", "34", "1", "67", "2", "3", "4", "5");
		//循环
		list.forEach(item -> System.out.println(item));

		//用Consumer.andThen连接若干消费者,连续输出两次
		Consumer<String> consumer = item -> System.out.println(item);
		consumer = consumer.andThen(item -> System.out.println(item));
		//循环
		list.forEach(consumer);

		//如上,可表示为
		list.forEach(System.out::println);

		//此处的a被隐式的声明为final
		list.forEach(item -> System.out.println(item + a));
	}
}
