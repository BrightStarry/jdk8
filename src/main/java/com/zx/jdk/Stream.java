package com.zx.jdk;

import com.sun.deploy.util.StringUtils;

import java.util.*;

/**
 * author:ZhengXing
 * datetime:2018/1/15 0015 17:13
 * 流操作
 */
public class Stream {
	public static void main(String[] args) {
		List<String> list = getList();
		//获取stream
		java.util.stream.Stream<String> stream = list.stream();
		//过滤 , 获取所有长度大于3的元素的流
		stream = stream.filter(item -> item.length() > 3);
		//排序, 并获取排序后的流
		stream = stream.sorted(((o1, o2) -> o1.indexOf(0) - o2.indexOf(0)));//可将表达式替换为 Comparator.comparingInt(o -> o.indexOf(0))
		//map,对每个元素进行映射,例如转为大写
		stream = stream.map(String::toUpperCase);
		//匹配,任何一个元素匹配,则为true
		System.out.println(list.stream().anyMatch(item -> item.length() < 4));
		//匹配,全部元素匹配,才为true
		System.out.println(list.stream().allMatch(item -> item.length() < 4));
		//匹配,没有元素匹配,才为true
		System.out.println(list.stream().noneMatch(item -> item.length() < 2));
		//统计元素个数,长度大于3的元素个数
		System.out.println(list.stream().filter(item -> item.length() > 3).count());
		//reduce,对所有元素进行归并删减操作,可以理解为 元素1和2进行操作,用12的结果和3操作,如此循环
		//用#连接所有元素
		Optional<String> reduceResult1 = list.stream().reduce((s1, s2) -> s1 + "#" + s2);
		System.out.println(reduceResult1.orElse("null"));
		//并行多线程流,如下的排序,Comparator.comparingInt内部返回了Comparator.
		System.out.println("sort----------");
		list.parallelStream().sorted(Comparator.comparingInt(o -> o.indexOf(0))).forEach(System.out::println);
		// TODO http://www.importnew.com/10360.html


		//循环,!!!注意,一个流只能被foreach一次.输出每个元素,使用了方法引用表达式
		stream.forEach(System.out::println);



	}

	private static List<String>  getList() {
		List<String> stringCollection = new ArrayList<>();
		stringCollection.add("ddd2");
		stringCollection.add("aaa2");
		stringCollection.add("bbb1");
		stringCollection.add("aaa1");
		stringCollection.add("bbb3");
		stringCollection.add("ccc");
		stringCollection.add("bbb2");
		stringCollection.add("ddd1");
		return stringCollection;
	}


}
