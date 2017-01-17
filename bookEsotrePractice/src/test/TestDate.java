package test;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

public class TestDate {

	@Test
	public void test1() {
		Date date= new Date();
		SimpleDateFormat simpleDateFormat= new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(simpleDateFormat.format(date));
	}
	
	@Test
	public void test2() {
		Calendar calendar= Calendar.getInstance();
		SimpleDateFormat simpleDateFormat= new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(simpleDateFormat.format(calendar.getTime()));
	}
	
	@Test
	public void test3() {
		Calendar calendar= Calendar.getInstance();
		int y= calendar.get(Calendar.YEAR);
		int m= calendar.get(Calendar.MONTH)+1;
		int d= calendar.get(Calendar.DATE);
		System.out.println(y+ "-"+ m+ "-"+ d);
	}

}
