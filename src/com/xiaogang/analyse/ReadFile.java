package com.xiaogang.analyse;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.Test;

public class ReadFile {
	public List<String> readList(String fileName){
		InputStream source = this.getClass().getClassLoader().getResourceAsStream(fileName);
		Scanner scanner = new Scanner(source);
		List<String> list = new ArrayList<String>();
		while(scanner.hasNextLine()){
			String line = scanner.nextLine();
			list.add(line.trim());
		}
		return list;
	}
	@Test
	public void testCompare() throws Exception {
		List<String> source = readList("oid");
		List<String> myList = readList("my");
		for (String string : myList) {
			source.remove(string);
		}
		System.out.print("订单系统不包含被考核的订单号：");
		for (String string : source) {
			System.out.println(string+",");
		}
	}
}
