#### JDK8学习

#### 奇淫巧技
* 在Project窗口右击,选择不使用Docked Mode,该窗口在未获得焦点时自动隐藏,或者按ESC隐藏
* 在Project窗口,直接输入类名,可查找类,并可用上下方向键在匹配的类中上下跳转.
* 在Run或Debug窗口,选择左边的一个Dump Threads按钮,可以输出当前应用的所有线程状态.

#### 内置函数式接口
* Supplier<T>: 用于返回一个给定类型的结果,没有输入参数.常用于方法引用.

* Consumer<T>: 接收单个参数,没有返回结果.常用于Collection.foreach(),消费每个元素.
    可使用andThen()连接多个Consumer
    
* Function<T, R> : 接收单个参数T,返回结果R.也可用andThen(下一个)|compose(前一个)连接.

* BinaryOperator<T> : 接收两个T的参数,并返回T类型的结果

* BiFunction<T, U, R>: 接收任意类型的两个参数,返回任意类型的结果

* Comparator<T> : java8扩展了该类,用于比较两个对象.

#### Stream


#### 接口的默认实现
* 例子如下,此外,默认实现的方法也可以重写
>
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
>
*  其设计最初目的,是为了让已经存在的接口可以添加新方法,而无需在原本已经实现的接口上做任何改变.  
例如jdk8中,使用接口的默认实现,扩展了其内部集合,例如Collection<E>类的default boolean removeIf(Predicate<? super E> filter)方法等.
* 但目前接口无法替代抽象类,因为接口不能有状态(非静态属性)

#### Lambda 闭包  
* 如下例子,list排序,其中List的 default void sort(Comparator<? super E> c)也是jdk8提供的实现
* 当函数内容只有一行时,去掉
>
    private static void jdk8ListSort() {
            /**
             * jdk8 list sort
             * 其中的(o1, o2) -> o1 - o2 表示一个匿名函数,o1和o2是其两个参数, -> 后面的是函数体.
             */
            List<Integer> list = Arrays.asList(334, 545, 342, 4, 345, 45446, 34345, 34, 545);
    //		list.sort((o1, o2) ->{ return o1 - o2;});
            //当代码只有一行时,可省略{},和return
            list.sort((o1, o2) -> o1 - o2);
            //当然,对于一些常用类型(int,long,double)的比较,已经有定义好的实现.
    //		list.sort(Comparator.comparingInt(o -> o));
            System.out.println(list);
    	}
    
    	private static void oldListSort() {
    		/**
    		 * old list sort
    		 */
    		List<Integer> list = Arrays.asList(334, 545, 342, 4, 345, 45446, 34345, 34, 545);
    		Collections.sort(list, new Comparator<Integer>() {
    			@Override
    			public int compare(Integer o1, Integer o2) {
    				return o1 - o2;
    			}
    		});
    		System.out.println(list);
    	}
>

* 自定义一个接口,然后定义匿名内部类实现该接口
>
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
>

#### 方法引用
* 引用已有的类或对象的方法,与Lambda联合使用,较少冗余代码.
>
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
>

#### 重复注解
* 定义一个Intros注解,属性为Intro注解数组.
* 然后在Intro注解上增加@Repeatable(Intros.class),
* 即可重复使用Intro注解.对于使用者来说,Intros注解是不可见的(可以当它不存在)
>
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
>

#### Optional 解决NPE(NullPointException)
* 全方法介绍
>
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
>

#### 参数名
* 编译时加入–parameters参数,可在反射中,用Method对象获取方法参数的真实名字,而不是转换为arg0/arg1这样.
* 可通过maven插件如下指定
>
    <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-compiler-plugin</artifactId>
        <version>3.1</version>
        <configuration>
            <compilerArgument>-parameters</compilerArgument>
            <source>1.8</source>
            <target>1.8</target>
        </configuration>
    </plugin>
>

#### Base64 编解码内置
>
		Base64.getEncoder().encode(new byte[]{});
		Base64.getDecoder().decode(new byte[]{});    
>

#### 通过Nashorn引擎执行js 

#### 类依赖分析器jdeps

#### PermGen(永生代)被Metaspace(元空间)取代


