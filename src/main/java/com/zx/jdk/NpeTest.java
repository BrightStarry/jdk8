package com.zx.jdk;

import jdk.nashorn.api.scripting.NashornScriptEngine;
import jdk.nashorn.api.scripting.NashornScriptEngineFactory;

import java.util.Base64;
import java.util.Optional;

/**
 * author:ZhengXing
 * datetime:2018/1/15 0015 14:15
 * 使用Optional解决NPE
 */
public class NpeTest {
	//用于orElseGet,返回其他默认值
	public static String test() {
		return "zzzz";
	}
	//用于orElseThrow,抛出异常
	public static RuntimeException test2() {
		return new RuntimeException("null");
	}

	public static void main(String[] args) {
		//创建空的
		Optional<Object> a = Optional.empty();
		//使用非空值创建,如果为空,会报错
		Optional<String> b = Optional.of("xxx");
		//创建,可传入任意值
		Optional<String> c = Optional.ofNullable(null);
		Optional<String> d = Optional.ofNullable("xxxx");
		//获取值,如果为null,抛出NoSuchElementException
		String s1 = b.get();
		//是否有值
		System.out.println(d.isPresent());
		//如果有值,调用指定Consumer,否则什么都不做
		d.ifPresent(item -> System.out.println(d.get()));
		//如果有值,返回值,否则返回自定义值other
		System.out.println(c.orElse("它将返回自定义值"));
		//如果有值,返回值,否则返回方法引用调用的结果
		System.out.println(c.orElseGet(NpeTest::test));
		//如果有值,返回值,否则抛出方法引用调用返回的一个异常类
		d.orElseThrow(NpeTest::test2);
		//如果有值,进行过滤,如果表达式返回false,则为null,为true,则返回原值
		System.out.println(d.filter(item -> item.length() > 1));
		//如果有值,进行表达式操作,返回表达式返回的结果,最终返回值可为null
		System.out.println(d.map(item -> null));
		//如果有值,进行表达式操作,返回表达式返回的结果,最终返回值不能为null,和map的区别在于,表达式的返回值也是Optional
		System.out.println(d.flatMap(item -> Optional.of("zzxzxx")));



	}
}
