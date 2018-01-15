package com.zx.jdk;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

/**
 * author:ZhengXing
 * datetime:2018/1/15 0015 13:26
 * 方法引用
 */
public class MethodCite {
	/**
	 * 模拟类
	 * Supplier表示一个结果,只有@FunctionalInterface注解和 T get()方法
	 */
	public static class User {
		//用于接收构造器引用
		public static User create(final Supplier<User> supplier) {
			return supplier.get();
		}
		//用于静态方法引用
		public static void a(final User user) {
			System.out.println("Collided " + user.toString());
		}
		//用于特定类的任意方法引用
		public void b() {
			System.out.println( "Repaired " + this.toString() );
		}
		//用于特定对象的任意方法引用'
		public void c(final User user) {
			System.out.println(user);
		}
	}

	public static void main(String[] args) {
		//构造器引用,语法是Class<T>::new,构造函数必须无参
		//使用User::new创建出Supplier对象,传入.
		User user = User.create(User::new);
		List<User> users = Arrays.asList(user);

		//静态方法引用,语法是Class::static_method,方法必须接收一个item(被循环的元素)参数
		//此处,该User::collide返回了一个 Consumer<T>类,用于消费每个元素.
		users.forEach(User::a);

		//特定类的任意方法引用,语法是Class::method,被引用的方法必须无参,也返回一个Consumer<T>,
		users.forEach(User::b);

		//特定对象的任意方法引用,语法是instance::method,方法必须接收一个item(被循环的元素)参数
		User tempUser = User.create(User::new);
		users.forEach(tempUser::c);

	}
}
