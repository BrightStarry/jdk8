package com.zx.jdk;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;

/**
 * author:ZhengXing
 * datetime:2018/1/16 0016 09:59
 * 新的日期时间 API
 */
public class DateApi {

	public static void main(String[] args) {
		//提供对当前时区的时间和日期的访问
		Clock clock  = Clock.systemDefaultZone();
		//获取当前毫秒数
		System.out.println(clock.millis());
		//当前时间
		Instant instant = clock.instant();
		System.out.println(instant);
		//转为data
		Date date = Date.from(instant);

		//Zone,对时区的操作
		//输出所有时区
		System.out.println(ZoneId.getAvailableZoneIds());
		//获取指定时区
		ZoneId zone1 = ZoneId.of("Europe/Berlin");
		ZoneId zone2 = ZoneId.of("Brazil/East");
		//该时区规则(相对于格林尼治时间(应该是它把)加减多少个小时)
		System.out.println(zone1.getRules());
		System.out.println(zone2.getRules());

		//本地时间,表示一个没有指定时区的时间
		LocalTime now1 = LocalTime.now(zone1);
		LocalTime now2 = LocalTime.now(zone2);
		//比较时间
		System.out.println(now1.isBefore(now2));
		//两者相差多少小时
		System.out.println(ChronoUnit.HOURS.between(now1, now2));
		System.out.println(ChronoUnit.MINUTES.between(now1, now2));

		//根据时分秒创建LocalTime
		LocalTime time1 = LocalTime.of(14, 32, 3);
		System.out.println(time1);
		//转为字符.根据FormatStyle的格式.Locale语言
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter
				.ofLocalizedTime(FormatStyle.SHORT)
				.withLocale(Locale.CHINESE);



	}
}
