#### JDK8学习

#### 奇淫巧技 http://www.importnew.com/27777.html
* IDEA,在Project窗口右击,选择不使用Docked Mode,该窗口在未获得焦点时自动隐藏,或者按ESC隐藏
* IDEA,在Project窗口,直接输入类名,可查找类,并可用上下方向键在匹配的类中上下跳转.
* IDEA,在Run或Debug窗口,选择左边的一个Dump Threads按钮,可以输出当前应用的所有线程状态.
* 对于动态代理类中的bug,可通过dumpclass(github)工具,获取其字节码反编译查看问题原因.

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

#### 内置函数式接口
* 这些接口可通过Lambda生成匿名函数实现.
>
    例如,进行如下过滤时,需要对list的每个元素进行单独计算,
    //item -> xxx这个表达式的 输入类型是T,输出类型也是boolean(表示是否过滤),所以,它其实就是Predicate<T>接口的实现函数.
    list.stream().filter(item -> xxx);
>
* Predicate<T>: 传入T类型参数,返回boolean结果,常用于list.filter().可调用or()/and()等方法拼接多个该接口表达式

* Supplier<T>: 用于返回一个给定类型的结果,没有输入参数.常用于方法引用.

* Consumer<T>: 接收单个参数,没有返回结果.常用于Collection.foreach(),消费每个元素.
    可使用andThen()连接多个Consumer
    
* Function<T, R> : 接收单个参数T,返回结果R.也可用andThen(下一个)|compose(前一个)连接.

* BinaryOperator<T> : 接收两个T的参数,并返回T类型的结果

* BiFunction<T, U, R>: 接收任意类型的两个参数,返回任意类型的结果

* Comparator<T> : java8扩展了该类,用于比较两个对象.

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

#### Stream
* 如下
>
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
    //循环,!!!注意,一个流只能被foreach一次.foreach第二次将抛出异常输出每个元素,使用了方法引用表达式
    stream.forEach(System.out::println);
>


#### Map集合操作
>
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



#### Concurrent包
* LongAccumulator: 比LongAdder强大,在于初始值不是必须为0,并且可提供例如累乘操作等.
* LongAdder: 是LongAccumulator的一个特例,比AtomicInteger在高并发下性能更好.更适合用于计数器操作
* DoubleAccumulator
* DoubleAdder

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


#### 日期时间Api
* 此处只做记录,需要时搜索即可.
* 参考:https://zhuanlan.zhihu.com/p/28133858
* ZoneId: 时区ID，用来确定Instant和LocalDateTime互相转换的规则
* Instant: 用来表示时间线上的一个点
* LocalDate: 表示没有时区的日期, LocalDate是不可变并且线程安全的
* LocalTime: 表示没有时区的时间, LocalTime是不可变并且线程安全的
* LocalDateTime: 表示没有时区的日期时间, LocalDateTime是不可变并且线程安全的
* Clock: 用于访问当前时刻、日期、时间，用到时区
* Duration: 用秒和纳秒表示时间的数量

* LocalDate
>
    LocalDate代表一个IOS格式(yyyy-MM-dd)的日期，可以存储 生日、纪念日等日期。
    获取当前的日期：
    LocalDate localDate = LocalDate.now();
    System.out.println("localDate: " + localDate);
    输出
    
    localDate: 2017-07-20
    LocalDate可以指定特定的日期，调用of或parse方法返回该实例：
    
    LocalDate.of(2017, 07, 20);
    LocalDate.parse("2017-07-20");
    当然它还有一些其他方法，我们一起来看看：
    
    为今天添加一天，也就是获取明天
    
    LocalDate tomorrow = LocalDate.now().plusDays(1);
    从今天减去一个月
    
    LocalDate prevMonth = LocalDate.now().minus(1, ChronoUnit.MONTHS);
    下面写两个例子，分别解析日期 2017-07-20，获取每周中的星期和每月中的日：
    
    DayOfWeek thursday = LocalDate.parse("2017-07-20").getDayOfWeek();
    System.out.println("周四: " + thursday);
    int twenty = LocalDate.parse("2017-07-20").getDayOfMonth();
    System.out.println("twenty: " + twenty);
    试试今年是不是闰年:
    
    boolean leapYear = LocalDate.now().isLeapYear();
    System.out.println("是否闰年: " + leapYear);
    判断是否在日期之前或之后:
    
    boolean notBefore = LocalDate.parse("2017-07-20")
                    .isBefore(LocalDate.parse("2017-07-22"));
    System.out.println("notBefore: " + notBefore);
    boolean isAfter = LocalDate.parse("2017-07-20").isAfter(LocalDate.parse("2017-07-22"));
    System.out.println("isAfter: " + isAfter);
    获取这个月的第一天:
    
    LocalDate firstDayOfMonth = LocalDate.parse("2017-07-20")
                    .with(TemporalAdjusters.firstDayOfMonth());
    System.out.println("这个月的第一天: " + firstDayOfMonth);
    firstDayOfMonth = firstDayOfMonth.withDayOfMonth(1);
    System.out.println("这个月的第一天: " + firstDayOfMonth);
    判断今天是否是我的生日，例如我的生日是 2009-07-20
    
    LocalDate birthday = LocalDate.of(2009, 07, 20);
    MonthDay birthdayMd = MonthDay.of(birthday.getMonth(), birthday.getDayOfMonth());
    MonthDay today = MonthDay.from(LocalDate.now());
    System.out.println("今天是否是我的生日: " + today.equals(birthdayMd));
>

* LocalTime
>
    LocalTime表示一个时间，而不是日期，下面介绍一下它的使用方法。
    
    获取现在的时间，输出15:01:22.144
    LocalTime now = LocalTime.now();
    System.out.println("现在的时间: " + now);
    将一个字符串时间解析为LocalTime，输出15:02
    
    LocalTime nowTime = LocalTime.parse("15:02");
    System.out.println("时间是: " + nowTime);
    使用静态方法of创建一个时间
    
    LocalTime nowTime = LocalTime.of(15, 02);
    System.out.println("时间是: " + nowTime);
    使用解析字符串的方式并添加一小时，输出16:02
    
    LocalTime nextHour = LocalTime.parse("15:02").plus(1, ChronoUnit.HOURS);
    System.out.println("下一个小时: " + nextHour);
    获取时间的小时、分钟
    
    int hour = LocalTime.parse("15:02").getHour();
    System.out.println("小时: " + hour);
    int minute = LocalTime.parse("15:02").getMinute();
    System.out.println("分钟: " + minute);
    我们也可以通过之前类似的API检查一个时间是否在另一个时间之前、之后
    
    boolean isBefore = LocalTime.parse("15:02").isBefore(LocalTime.parse("16:02"));
    boolean isAfter = LocalTime.parse("15:02").isAfter(LocalTime.parse("16:02"));
    System.out.println("isBefore: " + isBefore);
    System.out.println("isAfter: " + isAfter);
    输出 isBefore: true, isAfter: false。
    
    在LocalTime类中也将每天的开始和结束作为常量供我们使用:
    System.out.println(LocalTime.MAX);
    System.out.println(LocalTime.MIN);
    输出:
    23:59:59.999999999
    00:00
>

* LocalDateTime
>    
    LocalDateTime是用来表示日期和时间的，这是一个最常用的类之一。
    获取当前的日期和时间:
    LocalDateTime now = LocalDateTime.now();
    System.out.println("现在: " + now);
    输出
    现在: 2017-07-20T15:17:19.926
    
    下面使用静态方法和字符串的方式分别创建 LocalDateTime 对象
    LocalDateTime.of(2017, Month.JULY, 20, 15, 18);
    LocalDateTime.parse("2017-07-20T15:18:00");
    
    同时`LocalDateTime`也提供了相关API来对日期和时间进行增减操作:
    LocalDateTime tomorrow = now.plusDays(1);
    System.out.println("明天的这个时间: " + tomorrow);
    LocalDateTime minusTowHour = now.minusHours(2);
    System.out.println("两小时前: " + minusTowHour);
    这个类也提供一系列的get方法来获取特定单位:
    Month month = now.getMonth();
    System.out.println("当前月份: " + month);
    
    日期格式化
    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    System.out.println("默认格式化: " + now);
    System.out.println("自定义格式化: " + now.format(dateTimeFormatter));
    LocalDateTime localDateTime = LocalDateTime.parse("2017-07-20 15:27:44", dateTimeFormatter);
    System.out.println("字符串转LocalDateTime: " + localDateTime);
    
    也可以使用DateTimeFormatter的format方法将日期、时间格式化为字符串
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String dateString = dateTimeFormatter.format(LocalDate.now());
    System.out.println("日期转字符串: " + dateString);
    
    日期周期
    Period类用于修改给定日期或获得的两个日期之间的区别。
    
    给初始化的日期添加5天:
    LocalDate initialDate = LocalDate.parse("2017-07-20");
    LocalDate finalDate   = initialDate.plus(Period.ofDays(5));
    System.out.println("初始化日期: " + initialDate);
    System.out.println("加日期之后: " + finalDate);
    
    周期API中提供给我们可以比较两个日期的差别，像下面这样获取差距天数:
    long between = ChronoUnit.DAYS.between(initialDate, finalDate);
    System.out.println("差距天数: " + between);
    
    上面的代码会返回5，当然你想获取两个日期相差多少小时也是简单的。
    与遗留代码转换
    
    Date和Instant互相转换
    Date date = Date.from(Instant.now());
    Instant instant = date.toInstant();
    Date转换为LocalDateTime
    
    LocalDateTime localDateTime = LocalDateTime.from(new Date());
    System.out.println(localDateTime);
    LocalDateTime转Date
    
    Date date =
        Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    LocalDate转Date
    
    Date date =
        Date.from(LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
>

#### Base64 编解码内置
>
		Base64.getEncoder().encode(new byte[]{});
		Base64.getDecoder().decode(new byte[]{});    
>

#### 通过Nashorn引擎执行js 

#### 类依赖分析器jdeps

#### PermGen(永生代)被Metaspace(元空间)取代

