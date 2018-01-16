package com.zx.jdk;

import java.util.HashMap;

/**
 * author:ZhengXing
 * datetime:2018/1/16 0016 09:33
 * 对Map集合的操作
 */
public class Map {

	public static void main(String[] args) {
		HashMap<String, String> map = getMap();
		//如果key不存在,才增加
		map.putIfAbsent("1", "a");
		//进行计算,如果key存在,用结果更新key,否则,增加新的元素
		map.compute("10",(v,k) -> k +=v);
		//进行计算,如果key存在.如果不存在,则什么也不做
		map.computeIfPresent("11",(v,k) -> k +=v);
		//进行计算,如果key不存在,如果存在,则什么也不做
		//上面的方法传入的都是BiFunction<T, U, R>,该方法需要传入Function<T, R>,传入key,返回value,并加入集合
		map.computeIfAbsent("12",k -> k + "value");
		//获取指定key的值,如果key不存在,返回自己传入的默认值
		map.getOrDefault("13", "defaultValue");
		//合并操作,如果没有指定key存在,会将结果存入;如果有,则更新; 和compute的区别在于,它使用原来的value和新传入的value进行运算.
		map.merge("20","bbb",(value,newValue) -> value + newValue);
		//循环
		map.forEach((k,v) -> System.out.println(v));

	}

	private static HashMap<String, String> getMap() {
		HashMap<String, String> map = new HashMap<>();
		map.put("1", "a");
		map.put("2", "b");
		map.put("3", "c");
		map.put("4", "d");
		map.put("5", "e");
		map.put("6", "f");
		map.put("7", "g");
		map.put("8", "h");
		return map;
	}
}
