package test;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import com.westward.estore.utils.UploadUtils;

public class TestSeperator {

	@Test
	public void test() {
		System.out.println("[[[[["+ File.separator+ "]]]");
	}
	
	@Test
	public void test1() {
		System.out.println(UploadUtils.subFileName("a.txt"));
	}

}
