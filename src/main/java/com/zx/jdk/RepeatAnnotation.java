package com.zx.jdk;

import java.lang.annotation.*;

/**
 * author:ZhengXing
 * datetime:2018/1/15 0015 13:58
 * 重复注解
 */
@RepeatAnnotation.Intro(name = "xx")
@RepeatAnnotation.Intro(name = "xx")
public class RepeatAnnotation {

	//注解目标
	@Target(ElementType.TYPE)
	//作用域
	@Retention(RetentionPolicy.RUNTIME)
	//可以重复
	@Repeatable(Intros.class)
	public @interface Intro {
		//名字 String型 默认值为""
		String name() default "";

		//血量 int型 默认值为-1
		int HP() default -1;

		//移速 int型 默认值为-1
		int speed() default -1;
	}

	/**
	 * 用来让注解可以重复，搭配@Repeatable使用
	 */
	//注解目标
	@Target(ElementType.TYPE)
	//作用域
	@Retention(RetentionPolicy.RUNTIME)
	@interface Intros {
		Intro[] value();
	}
}
